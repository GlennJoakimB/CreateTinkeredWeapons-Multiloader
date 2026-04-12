package com.ridopipolop.createtinkeredweapons.content.weapons.impact_axe;

import java.util.List;

import javax.annotation.Nonnull;

import com.simibubi.create.content.equipment.armor.BacktankUtil;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class ImpactAxeItem extends AxeItem {
  public static final float BASE_DAMAGE = 6.0F;
  public static final float BASE_ATTACK_SPEED = -3.0F;
  public static final int BASE_DURABILITY = 250;
  private static final String EXPLOSIVE_MODE_KEY = "ExplosiveMode";
  private static final double KNOCKBACK_STRENGTH = 0.2;

  public ImpactAxeItem(Properties properties) {
    super(Tiers.IRON, (int) BASE_DAMAGE, BASE_ATTACK_SPEED, properties);
  }

  @Override
  public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
    Level level = attacker.level();

    // Apply explosive damage
    if (isExplosiveMode(stack)) {
      // EXPLOSIVE MODE: Deals explosive damage
      float explosiveDamage = 10.0F; // Base explosive damage
      target.hurt(level.damageSources().explosion((LivingEntity) null, attacker), explosiveDamage);

      // Visual and sound effects
      spawnExplosiveSmokeEffect((Player) attacker, (ServerLevel) level);

      // Takes extra durability when in this mode
      stack.hurtAndBreak(2, attacker, (e) -> e.broadcastBreakEvent(EquipmentSlot.MAINHAND));
    } else {
      // KNOCKBACK MODE: Sends entities flying with increased force

      // Calculate knockback direction
      double dx = target.getX() - attacker.getX();
      double dz = target.getZ() - attacker.getZ();
      double distance = Math.sqrt(dx * dx + dz * dz);
      double knockbackStrength;

      // Apply power-boost when backtank is available
      if (BacktankUtil.canAbsorbDamage(attacker, BASE_DURABILITY)) {
        knockbackStrength = KNOCKBACK_STRENGTH + 0.2;
      } else {
        knockbackStrength = KNOCKBACK_STRENGTH;
        stack.hurtAndBreak(1, attacker, (e) -> e.broadcastBreakEvent(EquipmentSlot.MAINHAND));
      }

      // Apply knockback-enchantment effect for knockback effect
      int knockbackLevel = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.KNOCKBACK, stack);
      if (knockbackLevel > 0) {
        knockbackStrength += knockbackLevel * 0.3;
      }

      if (distance > 0) {
        // Normalize and apply knockback
        target.push(
            dx / distance * knockbackStrength,
            0.1, // Slight upward motion
            dz / distance * knockbackStrength);
        target.hurtMarked = true; // Mark for velocity sync

        spawnSmokeEffect((Player) attacker, (ServerLevel) level, true);
      }
    }
    return true;
  }

  @Override
  public boolean mineBlock(ItemStack stack, Level level, BlockState state, BlockPos pos, LivingEntity miningEntity) {
    if (!level.isClientSide && state.getDestroySpeed(level, pos) != 0.0F) {
      stack.hurtAndBreak(1, miningEntity, (e) -> {
        e.broadcastBreakEvent(EquipmentSlot.MAINHAND);
      });
    }

    return true;
  }

  @Override
  public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
    ItemStack stack = player.getItemInHand(hand);

    // Only toggle if player is crouching
    if (!player.isCrouching()) {
      return InteractionResultHolder.pass(stack);
    }

    InteractionResult result = handleModeToggle(stack, level, player);
    return new InteractionResultHolder<>(result, stack);
  }

  @Nonnull
  @Override
  public InteractionResult useOn(UseOnContext context) {
    Level level = context.getLevel();
    Player player = context.getPlayer();
    if (player == null) {
      return InteractionResult.PASS;
    }

    InteractionHand hand = context.getHand();
    ItemStack heldStack = player.getItemInHand(hand);

    // Handle mode toggle when crouching
    if (player.isCrouching()) {
      return handleModeToggle(heldStack, level, player);
    }

    if (!level.isClientSide) {
      Vec3 lookDirection = player.getLookAngle();

      // Determine power and cooldown based on backtank
      double launchStrength;
      int cooldownTicks;

      if (BacktankUtil.canAbsorbDamage(player, BASE_DURABILITY)) {
        launchStrength = 0.7F; // 4 blocks up if jumping
        cooldownTicks = 30;
      } else {
        launchStrength = 0.5F; // 2.5 blocks up if jumping
        cooldownTicks = 40;
        heldStack.hurtAndBreak(1, player, p -> p.broadcastBreakEvent(hand));
      }

      // Apply knockback-enchantment effect for eject-effect
      int knockbackLevel = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.KNOCKBACK, heldStack);
      if (knockbackLevel > 0) {
        launchStrength += knockbackLevel * 0.3;
      }

      // Apply knockback
      Vec3 velocity = lookDirection.scale(-launchStrength); // Flip value to negative
      player.push(velocity.x, velocity.y, velocity.z);
      player.setOnGround(false);
      player.resetFallDistance(); // Cancel fall damage
      player.hurtMarked = true; // For velocity synchronization
      player.getCooldowns().addCooldown(this, cooldownTicks);

      // Spawn particles
      spawnSmokeEffect(player, (ServerLevel) level, false);
    }

    return InteractionResult.sidedSuccess(level.isClientSide);
  }

  private InteractionResult handleModeToggle(ItemStack stack, Level level, Player player) {
    // Toggle knockback
    toggleMode(stack);
    var isExplosiveModeEnabled = isExplosiveMode(stack);

    // Play a sound for feedback
    level.playSound(null, player.getX(), player.getY(), player.getZ(),
        SoundEvents.LEVER_CLICK, SoundSource.PLAYERS, 0.5F,
        isExplosiveModeEnabled ? 1.0F : 0.8F);

    // Show message to player
    if (level.isClientSide) {
      player.displayClientMessage(
          Component.translatable(isExplosiveModeEnabled
              ? "item.createtinkeredweapons.impact_axe.mode.explosive"
              : "item.createtinkeredweapons.impact_axe.mode.knockback")
              .withStyle(isExplosiveModeEnabled ? ChatFormatting.DARK_RED : ChatFormatting.BLUE),
          true // Show in action bar
      );
    }

    return InteractionResult.sidedSuccess(level.isClientSide);
  }

  private void spawnSmokeEffect(Player player, ServerLevel serverLevel, boolean isKnockbackEffect) {
    // Spawn smoke particles
    Vec3 playerPos = player.position();
    Vec3 lookDirection = player.getLookAngle();
    double offset = 0.5;
    float pitch = (isKnockbackEffect) ? 1.7F : 1.9F; // Apply slight pitch difference between the two modes

    // Play sound for steam-effect
    serverLevel.playSound((Player) null, playerPos.x, playerPos.y, playerPos.z, SoundEvents.PISTON_EXTEND,
        SoundSource.PLAYERS, 0.8F, pitch);

    for (int i = 0; i < 10; i++) {
      serverLevel.sendParticles(
          ParticleTypes.POOF, // Particle type
          playerPos.x, // X position
          playerPos.y + 1, // Y position at head lvl
          playerPos.z, // Z position
          0,
          lookDirection.x * offset,
          lookDirection.y * offset,
          lookDirection.z * offset,
          0.5);
    }
  }

  private void spawnExplosiveSmokeEffect(Player player, ServerLevel serverLevel) {
    // Spawn smoke particles
    Vec3 playerPos = player.position();
    Vec3 lookDirection = player.getLookAngle();
    double offset = 0.5;

    // Play sound for steam-effect
    serverLevel.playSound((Player) null, playerPos.x, playerPos.y, playerPos.z, SoundEvents.PISTON_EXTEND,
        SoundSource.PLAYERS, 1.0F, 1.7F);
    serverLevel.playSound((Player) null, playerPos.x, playerPos.y, playerPos.z, SoundEvents.LAVA_EXTINGUISH,
        SoundSource.PLAYERS, 0.8F, 2.0F);

    for (int i = 0; i < 10; i++) {
      serverLevel.sendParticles(
          ((i & 1) == 0) ? ParticleTypes.LARGE_SMOKE : ParticleTypes.POOF, // Particle type
          playerPos.x, // X position
          playerPos.y + 1, // Y position at head lvl
          playerPos.z, // Z position
          0,
          lookDirection.x * offset,
          lookDirection.y * offset,
          lookDirection.z * offset,
          0.5);
    }
  }

  private static void toggleMode(ItemStack stack) {
    stack.getOrCreateTag().putBoolean(EXPLOSIVE_MODE_KEY, !isExplosiveMode(stack));
  }

  public static boolean isExplosiveMode(ItemStack stack) {
    return stack.hasTag() && stack.getTag().getBoolean(EXPLOSIVE_MODE_KEY);
  }

  @Override
  public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flag) {
    var isExplosiveModeEnabled = isExplosiveMode(stack);
    tooltip.add(
        Component.translatable(isExplosiveModeEnabled
            ? "item.createtinkeredweapons.impact_axe.mode.explosive"
            : "item.createtinkeredweapons.impact_axe.mode.knockback")
            .withStyle(isExplosiveModeEnabled ? ChatFormatting.DARK_RED : ChatFormatting.BLUE));

    tooltip.add(Component.translatable("item.createtinkeredweapons.impact_axe.mode.toggle_description")
        .withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
  }

  @Override
  public boolean isBarVisible(ItemStack stack) {
    if (isExplosiveMode(stack)) {
      return super.isBarVisible(stack);
    } else {
      return BacktankUtil.isBarVisible(stack, BASE_DURABILITY);
    }
  }

  @Override
  public int getBarWidth(ItemStack stack) {
    if (isExplosiveMode(stack)) {
      return super.getBarWidth(stack);
    } else {
      return BacktankUtil.getBarWidth(stack, BASE_DURABILITY);
    }
  }

  @Override
  public int getBarColor(ItemStack stack) {
    if (isExplosiveMode(stack)) {
      return super.getBarColor(stack);
    } else {
      return BacktankUtil.getBarColor(stack, BASE_DURABILITY);
    }
  }

  @Override
  public boolean canAttackBlock(BlockState state, Level level, BlockPos pos, Player player) {
    return !player.isCreative();
  }

  @Override
  public int getEnchantmentValue() {
    return 10;
  }
}

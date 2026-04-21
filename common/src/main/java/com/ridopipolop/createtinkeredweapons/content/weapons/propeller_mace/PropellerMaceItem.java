package com.ridopipolop.createtinkeredweapons.content.weapons.propeller_mace;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class PropellerMaceItem extends SwordItem {
  public static final int THROW_THRESHOLD_TIME = 10;
  public static final double LAUNCH_POWER = 1;
  public static final int BASE_DAMAGE = 4;
  public static final float BASE_ATTACK_SPEED = -3.0F;
  private static final String DEPLOYED_MODE_KEY = "DeployedMode";

  public PropellerMaceItem(Properties properties) {
    super(Tiers.IRON, BASE_DAMAGE, BASE_ATTACK_SPEED, properties);
  }

  @Override
  public void releaseUsing(ItemStack stack, Level level, LivingEntity entityLiving, int timeCharged) {
    if (!(entityLiving instanceof Player player))
      return;

    int i = this.getUseDuration(stack) - timeCharged;
    if (i < THROW_THRESHOLD_TIME)
      return;

    Vec3 look = player.getLookAngle();
    player.addDeltaMovement(look.scale(LAUNCH_POWER));
    player.resetFallDistance(); // Cancel fall damage
    player.hurtMarked = true; // For velocity synchronization
    stack.hurtAndBreak(1, player, (p) -> {
      p.broadcastBreakEvent(player.getUsedItemHand());
    });

    // Launch the player up a little to reduce ground friction
    if (player.onGround()) {
      player.move(MoverType.SELF, new Vec3(0, 1.2, 0));
    }

    // Audio and visual feedback
    setDeployedMode(stack, true);
    level.playSound(null, player.getX(), player.getY(), player.getZ(),
        SoundEvents.TRIDENT_RIPTIDE_1, SoundSource.PLAYERS, 0.5F, 0.8F);
  }

  @Override
  public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
    ItemStack itemStack = player.getItemInHand(usedHand);
    if (itemStack.getDamageValue() >= itemStack.getMaxDamage() - 1) {
      return InteractionResultHolder.fail(itemStack);
    } else {
      if (player.onGround()) {
        player.startUsingItem(usedHand);
        return InteractionResultHolder.consume(itemStack);
      } else {
        toggleMode(itemStack);
        return InteractionResultHolder.consume(itemStack);
      }
    }
  }

  private static void toggleMode(ItemStack stack) {
    setDeployedMode(stack, !isDeployedMode(stack));
  }

  public static void setDeployedMode(ItemStack stack, boolean state) {
    stack.getOrCreateTag().putBoolean(DEPLOYED_MODE_KEY, state);
  }

  public static boolean isDeployedMode(ItemStack stack) {
    return stack.hasTag() && stack.getTag().getBoolean(DEPLOYED_MODE_KEY);
  }

  @Override
  public float getDestroySpeed(ItemStack itemStack, BlockState blockState) {
    // Disable default boost for breaking cobwebs
    return blockState.is(BlockTags.SWORD_EFFICIENT) ? 1.5F : 1.0F;
  }

  @Override
  public boolean isCorrectToolForDrops(BlockState blockState) {
    return false; // Disable default for cobweb drop
  }

  @Override
  public UseAnim getUseAnimation(ItemStack stack) {
    return UseAnim.SPEAR;
  }

  @Override
  public int getUseDuration(ItemStack stack) {
    return 72000;
  }

  @Override
  public int getEnchantmentValue() {
    return 10;
  }
}

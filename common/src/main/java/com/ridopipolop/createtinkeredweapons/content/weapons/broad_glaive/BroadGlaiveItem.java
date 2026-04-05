package com.ridopipolop.createtinkeredweapons.content.weapons.broad_glaive;

import java.util.UUID;

import javax.annotation.Nullable;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.ridopipolop.createtinkeredweapons.PlatformHelper;
import com.ridopipolop.createtinkeredweapons.entity.ThrownBroadGlaive;
import com.ridopipolop.createtinkeredweapons.registry.ModItems;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow.Pickup;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.Vanishable;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class BroadGlaiveItem extends Item implements Vanishable {
  public static final int THROW_THRESHOLD_TIME = 10;
  public static final float RANGE_MODIFIER = 1.2F;
  public static final float BASE_DAMAGE = 6.0F;
  public static final float BASE_ATTACK_SPEED = -2.0F;
  public static final float SHOOT_POWER = 2.5F;
  private final Multimap<Attribute, AttributeModifier> defaultModifiers;

  public static final String GLAIVE_MARKER = "moddedBroadGlaive";
  public static final AttributeModifier rangeAttributeModifier = new AttributeModifier(
      UUID.fromString("ffe07123-760f-4174-9340-cf290be36139"), "Range modifier", RANGE_MODIFIER,
      AttributeModifier.Operation.ADDITION);
  private static final Supplier<Multimap<Attribute, AttributeModifier>> rangeModifier = Suppliers
      .memoize(() -> PlatformHelper.getRangeModifier(rangeAttributeModifier));

  // Constructor
  public BroadGlaiveItem(Properties properties) {
    super(properties);
    ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
    builder.put(Attributes.ATTACK_DAMAGE,
        new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Tool modifier", BASE_DAMAGE, Operation.ADDITION));
    builder.put(Attributes.ATTACK_SPEED,
        new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Tool modifier", BASE_ATTACK_SPEED, Operation.ADDITION));
    this.defaultModifiers = builder.build();
  }

  public static void holdingGlaiveIncreasesRange(LivingEntity entity, CompoundTag persistentData) {
    if (!(entity instanceof Player player))
      return;

    boolean holdinginMainHand = ModItems.BROAD_GLAIVE.isIn(player.getMainHandItem());
    boolean wasHolding = persistentData.contains(GLAIVE_MARKER);

    if (holdinginMainHand != wasHolding) {
      if (!holdinginMainHand) {
        player.getAttributes().removeAttributeModifiers(rangeModifier.get());
        persistentData.remove(GLAIVE_MARKER);
      } else {
        player.getAttributes().addTransientAttributeModifiers(rangeModifier.get());
        persistentData.putBoolean(GLAIVE_MARKER, true);
      }
    }
  }

  public static void addRangeToJoiningPlayersHoldingGlaive(Player player, @Nullable CompoundTag persistentData) {
    if (persistentData == null)
      return;
    if (persistentData.contains(GLAIVE_MARKER))
      player.getAttributes().addTransientAttributeModifiers(rangeModifier.get());
  }

  @Override
  public void releaseUsing(ItemStack stack, Level level, LivingEntity entityLiving, int timeCharged) {
    if (!(entityLiving instanceof Player player))
      return;

    int i = this.getUseDuration(stack) - timeCharged;
    if (i >= THROW_THRESHOLD_TIME) {
      if (!level.isClientSide) {
        stack.hurtAndBreak(1, player, (p) -> {
          p.broadcastBreakEvent(player.getUsedItemHand());
        });
        ThrownBroadGlaive thrownBroadGlaive = new ThrownBroadGlaive(level, player, stack);

        thrownBroadGlaive.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, SHOOT_POWER, 1.0F);
        if (player.getAbilities().instabuild) {
          thrownBroadGlaive.pickup = Pickup.CREATIVE_ONLY;
        }

        level.addFreshEntity(thrownBroadGlaive);
        level.playSound((Player) null, thrownBroadGlaive, SoundEvents.TRIDENT_THROW, SoundSource.PLAYERS, 1.0F, 1.0F);
        if (!player.getAbilities().instabuild) {
          player.getInventory().removeItem(stack);
        }

        player.awardStat(Stats.ITEM_USED.get(this));
      }
    }
  }

  @Override
  public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
    ItemStack itemStack = player.getItemInHand(usedHand);
    if (itemStack.getDamageValue() >= itemStack.getMaxDamage() - 1) {
      return InteractionResultHolder.fail(itemStack);
    } else {
      player.startUsingItem(usedHand);
      return InteractionResultHolder.consume(itemStack);
    }
  }

  @Override
  public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
    stack.hurtAndBreak(1, attacker, (e) -> {
      e.broadcastBreakEvent(EquipmentSlot.MAINHAND);
    });
    return true;
  }

  @Override
  public boolean mineBlock(ItemStack stack, Level level, BlockState state, BlockPos pos, LivingEntity miningEntity) {
    if ((double) state.getDestroySpeed(level, pos) != 0.0) {
      stack.hurtAndBreak(2, miningEntity, (e) -> {
        e.broadcastBreakEvent(EquipmentSlot.MAINHAND);
      });
    }

    return true;
  }

  @Override
  public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot slot) {
    return slot == EquipmentSlot.MAINHAND ? this.defaultModifiers : super.getDefaultAttributeModifiers(slot);
  }

  @Override
  public boolean canAttackBlock(BlockState state, Level level, BlockPos pos, Player player) {
    return !player.isCreative();
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

package com.ridopipolop.createtinkeredweapons.content.weapons.propeller_mace;

import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.block.state.BlockState;

public class PropellerMaceItem extends SwordItem {
  public static final int BASE_DAMAGE = 4;
  public static final float BASE_ATTACK_SPEED = -3.0F;

  public PropellerMaceItem(Properties properties) {
    super(Tiers.IRON, BASE_DAMAGE, BASE_ATTACK_SPEED, properties);
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

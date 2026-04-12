package com.ridopipolop.createtinkeredweapons.content.weapons.propeller_mace;

import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class PropellerMaceItem extends SwordItem {
  public static final int BASE_DAMAGE = 4;
  public static final float BASE_ATTACK_SPEED = -3.0F;
  private static final String DEPLOYED_MODE_KEY = "DeployedMode";


  public PropellerMaceItem(Properties properties) {
    super(Tiers.IRON, BASE_DAMAGE, BASE_ATTACK_SPEED, properties);
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
    stack.getOrCreateTag().putBoolean(DEPLOYED_MODE_KEY, !isDeployedMode(stack));
  }

  public static void disableDeployedMode(ItemStack stack) {
    stack.getOrCreateTag().putBoolean(DEPLOYED_MODE_KEY, false);
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

package com.ridopipolop.createtinkeredweapons.content.weapons.propeller_mace;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class PropellerMaceArmPoseHandler {
  public static void applyArmPose(Player player, HumanoidModel<?> model) {
    applyForHand(player, model, InteractionHand.MAIN_HAND);
    applyForHand(player, model, InteractionHand.OFF_HAND);
  }

  private static void applyForHand(Player player, HumanoidModel<?> model, InteractionHand hand) {
    ItemStack stack = player.getItemInHand(hand);
    if (!(stack.getItem() instanceof PropellerMaceItem))
      return;
    if (!PropellerMaceItem.isDeployedMode(stack))
      return;

    boolean isRightHand = (hand == InteractionHand.MAIN_HAND) == (player.getMainArm() == HumanoidArm.RIGHT);

    if (isRightHand)
      model.rightArmPose = HumanoidModel.ArmPose.THROW_SPEAR;
    else
      model.leftArmPose = HumanoidModel.ArmPose.THROW_SPEAR;
  }
}

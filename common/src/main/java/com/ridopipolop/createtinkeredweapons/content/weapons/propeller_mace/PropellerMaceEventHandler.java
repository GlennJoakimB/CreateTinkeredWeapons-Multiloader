package com.ridopipolop.createtinkeredweapons.content.weapons.propeller_mace;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

public class PropellerMaceEventHandler {

  public static void onPlayerTick(Player player) {
    ItemStack mainHand = player.getMainHandItem();
    ItemStack offHand = player.getOffhandItem();
    boolean mainHandDeployed = isDeployed(mainHand);
    boolean offHandDeployed = isDeployed(offHand);

    // Disable if player lands on the ground while deployed
    if (player.onGround()) {
      if (mainHandDeployed)
        PropellerMaceItem.setDeployedMode(mainHand, false);
      if (offHandDeployed)
        PropellerMaceItem.setDeployedMode(offHand, false);
      return;
    }

    // Applying glide effect
    if (mainHandDeployed || offHandDeployed) {
      applyGlide(player);
    }
  }

  private static boolean isDeployed(ItemStack stack) {
    return stack.getItem() instanceof PropellerMaceItem
        && PropellerMaceItem.isDeployedMode(stack);
  }

  private static void applyGlide(Player player) {
    Vec3 velocity = player.getDeltaMovement();
    if (velocity.y < -0.1) {
      double glideY = Math.max(velocity.y * 0.65, -1.0); // dampen + cap fall speed
      player.setDeltaMovement(velocity.x * 0.98, glideY, velocity.z * 0.98);
      player.resetFallDistance();
    }
  }
}

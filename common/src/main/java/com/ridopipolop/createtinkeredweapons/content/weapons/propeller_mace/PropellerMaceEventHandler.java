package com.ridopipolop.createtinkeredweapons.content.weapons.propeller_mace;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

public class PropellerMaceEventHandler {


  public static void onPlayerTick(Player player) {
    ItemStack mainHand = player.getMainHandItem();
    ItemStack offHand = player.getOffhandItem();
    boolean mainHandDeployed = mainHand.getItem() instanceof PropellerMaceItem
        && PropellerMaceItem.isDeployedMode(mainHand);
    boolean offHandDeployed = offHand.getItem() instanceof PropellerMaceItem
        && PropellerMaceItem.isDeployedMode(offHand);

    // Disable if player lands on the ground while deployed
    if (player.onGround()) {
      if (mainHandDeployed)
        PropellerMaceItem.setDeployedMode(mainHand, false);
      if (offHandDeployed)
        PropellerMaceItem.setDeployedMode(offHand, false);
      return; // no glide effect needed
    }

    // Applying glide effect
    if (mainHandDeployed || offHandDeployed) {
      Vec3 velocity = player.getDeltaMovement();

      if (velocity.y < -0.1) { // only slow a downward fall
        double glideY = Math.max(velocity.y * 0.65, -1.0); // dampen + cap fall speed
        player.setDeltaMovement(velocity.x * 0.98, glideY, velocity.z * 0.98);
        player.fallDistance = 0; // prevent fall damage while gliding
        player.resetFallDistance();
      }
    }
  }
}

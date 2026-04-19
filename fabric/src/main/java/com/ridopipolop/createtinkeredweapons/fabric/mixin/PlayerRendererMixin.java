package com.ridopipolop.createtinkeredweapons.fabric.mixin;

import com.ridopipolop.createtinkeredweapons.content.weapons.propeller_mace.PropellerMaceArmPoseHandler;
import net.minecraft.client.player.AbstractClientPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;

@Mixin(PlayerRenderer.class)
public class PlayerRendererMixin {

  @Inject(method = "renderRightHand", at = @At("HEAD"))
  private void onRenderRightHand(PoseStack poseStack, MultiBufferSource buffer,
      int combinedLight, AbstractClientPlayer player,
      CallbackInfo ci) {
    PropellerMaceArmPoseHandler.applyArmPose(player, ((PlayerRenderer) (Object) this).getModel());
  }

  @Inject(method = "setModelProperties", at = @At("TAIL"))
  private void onSetModelProperties(AbstractClientPlayer player, CallbackInfo ci) {
    PropellerMaceArmPoseHandler.applyArmPose(player, ((PlayerRenderer) (Object) this).getModel());
  }
}
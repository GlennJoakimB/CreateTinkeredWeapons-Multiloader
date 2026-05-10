package com.ridopipolop.createtinkeredweapons.content.weapons.propeller_mace;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.ridopipolop.createtinkeredweapons.CreateTinkeredWeapons;
import com.simibubi.create.foundation.item.render.CustomRenderedItemModel;
import com.simibubi.create.foundation.item.render.CustomRenderedItemModelRenderer;
import com.simibubi.create.foundation.item.render.PartialItemModelRenderer;

import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import net.createmod.catnip.animation.AnimationTickHolder;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class PropellerMaceItemRenderer extends CustomRenderedItemModelRenderer {

  protected static final PartialModel PROPELLER_1 = PartialModel
      .of(CreateTinkeredWeapons.id("item/propeller_mace/propeller_1"));
  protected static final PartialModel PROPELLER_2 = PartialModel
      .of(CreateTinkeredWeapons.id("item/propeller_mace/propeller_2"));
  protected static final PartialModel DEPLOYED_MODEL = PartialModel
      .of(CreateTinkeredWeapons.id("item/propeller_mace/item_deployed"));

  @Override
  protected void render(ItemStack stack, CustomRenderedItemModel model, PartialItemModelRenderer renderer,
      ItemDisplayContext transformType, PoseStack ms, MultiBufferSource buffer, int light, int overlay) {

    // If not deployed, render original model
    if (!PropellerMaceItem.isDeployedMode(stack)) {
      renderer.render(model.getOriginalModel(), light);
      return;
    }

    ms.pushPose();

    // 1. Apply manual offsets based on transformType
    applyDeploymentTransform(transformType, ms);

    // 2. Render the body
    renderer.render(DEPLOYED_MODEL.get(), light);

    // 3. Render animated propeller
    float angle = AnimationTickHolder.getRenderTime() * 60;
    renderRotatingPropeller(renderer, ms, PROPELLER_1, angle, light);
    renderRotatingPropeller(renderer, ms, PROPELLER_2, -angle, light);

    ms.popPose();
  }

  private void applyDeploymentTransform(ItemDisplayContext context, PoseStack ms) {
    if (context.firstPerson()) {
      boolean isLeftHand = context == ItemDisplayContext.FIRST_PERSON_LEFT_HAND
          || context == ItemDisplayContext.THIRD_PERSON_LEFT_HAND;
      float flip = isLeftHand ? -1f : 1f;

      // Apply Translation (X flipped for left hand)
      ms.translate((0.25f * flip) / 16f, 5.5f / 16f, 0);

      // Apply Rotation (Y and Z flipped for left hand)
      ms.mulPose(Axis.XP.rotationDegrees(-1.75f));
      ms.mulPose(Axis.YP.rotationDegrees(-0.25f * flip));
      ms.mulPose(Axis.ZP.rotationDegrees(-16f * flip));

    } else if (context == ItemDisplayContext.THIRD_PERSON_RIGHT_HAND
        || context == ItemDisplayContext.THIRD_PERSON_LEFT_HAND) {
      ms.translate(0, -4.0f / 16f, -4.0f / 16f);
      ms.mulPose(Axis.XP.rotationDegrees(-82f));
    }
  }

  private void renderRotatingPropeller(PartialItemModelRenderer renderer, PoseStack ms, PartialModel part, float angle,
      int light) {
    ms.pushPose();
    ms.mulPose(Axis.YP.rotationDegrees(angle));
    renderer.render(part.get(), light);
    ms.popPose();
  }
}

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

    if (!PropellerMaceItem.isDeployedMode(stack)) {
      renderer.render(model.getOriginalModel(), light);
    } else {
      renderer.render(DEPLOYED_MODEL.get(), light);

      float worldTime = AnimationTickHolder.getRenderTime() / 10;
      float angle = worldTime * 60;

      // Rotor animations
      ms.pushPose();
      ms.mulPose(Axis.YP.rotationDegrees(angle)); // Rotate one way
      renderer.render(PROPELLER_1.get(), light);
      ms.popPose();

      ms.pushPose();
      ms.mulPose(Axis.YP.rotationDegrees(-angle)); // Rotate the other way
      renderer.render(PROPELLER_2.get(), light);
      ms.popPose();
    }
  }
}

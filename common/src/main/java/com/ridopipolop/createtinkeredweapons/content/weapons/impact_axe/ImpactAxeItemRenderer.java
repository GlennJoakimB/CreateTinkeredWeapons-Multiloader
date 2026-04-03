package com.ridopipolop.createtinkeredweapons.content.weapons.impact_axe;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.ridopipolop.createtinkeredweapons.CreateTinkeredWeapons;
import com.simibubi.create.foundation.blockEntity.behaviour.scrollValue.ScrollValueHandler;
import com.simibubi.create.foundation.item.render.CustomRenderedItemModel;
import com.simibubi.create.foundation.item.render.CustomRenderedItemModelRenderer;
import com.simibubi.create.foundation.item.render.PartialItemModelRenderer;

import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import net.createmod.catnip.animation.AnimationTickHolder;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class ImpactAxeItemRenderer extends CustomRenderedItemModelRenderer {

  protected static final PartialModel GEAR = PartialModel.of(CreateTinkeredWeapons.id("item/impact_axe/gear"));

  @Override
  protected void render(ItemStack stack, CustomRenderedItemModel model, PartialItemModelRenderer renderer,
      ItemDisplayContext transformType, PoseStack ms, MultiBufferSource buffer, int light, int overlay) {

    renderer.render(model.getOriginalModel(), light);
    ms.mulPose(Axis.YP.rotationDegrees(ScrollValueHandler.getScroll(AnimationTickHolder.getPartialTicks())));
    renderer.render(GEAR.get(), light);
  }
}

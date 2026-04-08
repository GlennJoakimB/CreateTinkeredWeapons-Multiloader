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
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class ImpactAxeItemRenderer extends CustomRenderedItemModelRenderer {

  protected static final PartialModel GEAR = PartialModel.of(CreateTinkeredWeapons.id("item/impact_axe/gear"));
  protected static final PartialModel EXPLOSIVE_MODEL = PartialModel
      .of(CreateTinkeredWeapons.id("item/impact_axe/item_explosive"));

  @Override
  protected void render(ItemStack stack, CustomRenderedItemModel model, PartialItemModelRenderer renderer,
      ItemDisplayContext transformType, PoseStack ms, MultiBufferSource buffer, int light, int overlay) {

    boolean isExplosiveMode = ImpactAxeItem.isExplosiveMode(stack);
    float worldTime = AnimationTickHolder.getRenderTime() / 10;
    float angle = worldTime * (isExplosiveMode ? 30 : 20);
    BakedModel baseModel = isExplosiveMode
        ? EXPLOSIVE_MODEL.get()
        : model.getOriginalModel();

    // Main model
    renderer.render(baseModel, light);

    // Cog animation
    ms.pushPose();
    ms.mulPose(Axis.YP.rotationDegrees(angle));
    renderer.render(GEAR.get(), light);
    ms.popPose();
  }
}

package com.ridopipolop.createtinkeredweapons.client;

import com.ridopipolop.createtinkeredweapons.CreateTinkeredWeapons;
import com.ridopipolop.createtinkeredweapons.content.weapons.broad_glaive.ThrownBroadGlaiveModel;
import com.ridopipolop.createtinkeredweapons.content.weapons.broad_glaive.ThrownBroadGlaiveRenderer;
import com.ridopipolop.createtinkeredweapons.registry.ModEntityTypes;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.model.geom.ModelLayerLocation;

public class CreateTinkeredWeaponsClient implements ClientModInitializer {

  public static final ModelLayerLocation THROWN_BROAD_GLAIVE = new ModelLayerLocation(
      CreateTinkeredWeapons.id("broad_glaive"), "main");

  @Override
  public void onInitializeClient() {
    EntityRendererRegistry.register(ModEntityTypes.THROWN_BROAD_GLAIVE, (context) -> {
      return new ThrownBroadGlaiveRenderer(context);
    });

    EntityModelLayerRegistry.registerModelLayer(THROWN_BROAD_GLAIVE, ThrownBroadGlaiveModel::getTexturedModelData);
  }
}
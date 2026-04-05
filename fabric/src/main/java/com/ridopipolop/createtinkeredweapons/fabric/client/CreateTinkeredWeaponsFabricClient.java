package com.ridopipolop.createtinkeredweapons.fabric.client;

import com.ridopipolop.createtinkeredweapons.content.weapons.broad_glaive.ThrownBroadGlaiveModel;
import com.ridopipolop.createtinkeredweapons.content.weapons.broad_glaive.ThrownBroadGlaiveRenderer;
import com.ridopipolop.createtinkeredweapons.registry.ModEntityTypes;
import com.ridopipolop.createtinkeredweapons.registry.ModModelLayers;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

public class CreateTinkeredWeaponsFabricClient implements ClientModInitializer {

  @Override
  public void onInitializeClient() {
    EntityRendererRegistry.register(ModEntityTypes.THROWN_BROAD_GLAIVE, (context) -> {
      return new ThrownBroadGlaiveRenderer(context);
    });

    EntityModelLayerRegistry.registerModelLayer(
        ModModelLayers.THROWN_BROAD_GLAIVE,
        ThrownBroadGlaiveModel::getTexturedModelData);
  }
}
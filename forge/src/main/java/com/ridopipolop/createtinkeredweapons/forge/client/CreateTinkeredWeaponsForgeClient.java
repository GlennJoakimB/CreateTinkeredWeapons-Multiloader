package com.ridopipolop.createtinkeredweapons.forge.client;

import com.ridopipolop.createtinkeredweapons.content.weapons.broad_glaive.ThrownBroadGlaiveModel;
import com.ridopipolop.createtinkeredweapons.content.weapons.broad_glaive.ThrownBroadGlaiveRenderer;
import com.ridopipolop.createtinkeredweapons.forge.registry.ModEntityTypesForge;
import com.ridopipolop.createtinkeredweapons.registry.ModModelLayers;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import com.ridopipolop.createtinkeredweapons.CreateTinkeredWeapons;

@Mod.EventBusSubscriber(modid = CreateTinkeredWeapons.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class CreateTinkeredWeaponsForgeClient {

  @SubscribeEvent
  public static void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {
    event.registerEntityRenderer(
        ModEntityTypesForge.THROWN_BROAD_GLAIVE.get(),
        ThrownBroadGlaiveRenderer::new);
  }

  @SubscribeEvent
  public static void onRegisterLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
    event.registerLayerDefinition(
        ModModelLayers.THROWN_BROAD_GLAIVE,
        ThrownBroadGlaiveModel::getTexturedModelData);
  }
}
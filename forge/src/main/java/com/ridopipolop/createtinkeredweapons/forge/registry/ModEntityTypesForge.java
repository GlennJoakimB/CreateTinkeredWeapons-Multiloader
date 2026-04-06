package com.ridopipolop.createtinkeredweapons.forge.registry;

import com.ridopipolop.createtinkeredweapons.CreateTinkeredWeapons;
import com.ridopipolop.createtinkeredweapons.entity.ThrownBroadGlaive;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntityTypesForge {
  public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister
      .create(ForgeRegistries.ENTITY_TYPES, CreateTinkeredWeapons.MOD_ID);

  public static final RegistryObject<EntityType<ThrownBroadGlaive>> THROWN_BROAD_GLAIVE = ENTITY_TYPES
      .register("thrown_broad_glaive",
          () -> EntityType.Builder.<ThrownBroadGlaive>of(ThrownBroadGlaive::new, MobCategory.MISC)
              .sized(0.5f, 0.5f)
              .clientTrackingRange(4)
              .updateInterval(20)
              .build("thrown_broad_glaive"));
}

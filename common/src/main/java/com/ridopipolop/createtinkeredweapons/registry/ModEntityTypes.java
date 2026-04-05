package com.ridopipolop.createtinkeredweapons.registry;

import com.ridopipolop.createtinkeredweapons.CreateTinkeredWeapons;
import com.ridopipolop.createtinkeredweapons.entity.ThrownBroadGlaive;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public class ModEntityTypes {
  public static final EntityType<ThrownBroadGlaive> THROWN_BROAD_GLAIVE = register("thrown_broad_glaive",
      createEntityType(ThrownBroadGlaive::new));

  private static <T extends Entity> EntityType<T> register(String key, EntityType<T> entity) {
    return Registry.register(BuiltInRegistries.ENTITY_TYPE, CreateTinkeredWeapons.MOD_ID + ":" + key, entity);
  }

  private static <T extends Entity> EntityType<T> createEntityType(EntityType.EntityFactory<T> factory) {
    return EntityType.Builder.<T>of(factory, MobCategory.MISC)
        .sized(0.5f, 0.5f)
        .clientTrackingRange(4)
        .updateInterval(20)
        .build("thrown_broad_glaive");
  }

  public static void register() {
    CreateTinkeredWeapons.LOGGER.info("Registiring entities for {}.", CreateTinkeredWeapons.NAME);
  }
}

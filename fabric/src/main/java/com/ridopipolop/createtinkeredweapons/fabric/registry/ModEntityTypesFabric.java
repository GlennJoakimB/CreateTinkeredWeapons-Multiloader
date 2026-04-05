package com.ridopipolop.createtinkeredweapons.fabric.registry;

import com.ridopipolop.createtinkeredweapons.CreateTinkeredWeapons;
import com.ridopipolop.createtinkeredweapons.entity.ThrownBroadGlaive;
import com.ridopipolop.createtinkeredweapons.registry.ModEntityTypes;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;

public class ModEntityTypesFabric {
 public static void register() {
    ModEntityTypes.THROWN_BROAD_GLAIVE = Registry.register(
        BuiltInRegistries.ENTITY_TYPE,
        CreateTinkeredWeapons.MOD_ID + ":thrown_broad_glaive",
        ModEntityTypes.createEntityType(ThrownBroadGlaive::new)
    );
  } 
}

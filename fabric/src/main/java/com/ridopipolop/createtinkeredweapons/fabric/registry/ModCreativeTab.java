package com.ridopipolop.createtinkeredweapons.fabric.registry;

import static com.ridopipolop.createtinkeredweapons.CreateTinkeredWeapons.REGISTRATE;

import com.ridopipolop.createtinkeredweapons.CreateTinkeredWeapons;
import com.ridopipolop.createtinkeredweapons.registry.ModItems;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ModCreativeTab {

  public static final ResourceKey<CreativeModeTab> MAIN_KEY = ResourceKey.create(Registries.CREATIVE_MODE_TAB,
      CreateTinkeredWeapons.id("main"));
  public static final CreativeModeTab MAIN_TAB = FabricItemGroup.builder()
      .icon(() -> new ItemStack(ModItems.IMPACT_AXE))
      .title(Component.translatable("itemGroup.createtinkeredweapons.main"))
      .build();

  public static void register() {
    CreateTinkeredWeapons.LOGGER.info("Registering Item Groups for {}.", CreateTinkeredWeapons.NAME);
    Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, MAIN_KEY, MAIN_TAB);

    ItemGroupEvents.modifyEntriesEvent(MAIN_KEY).register(content -> {
      REGISTRATE.getAll(Registries.ITEM).forEach(entry -> {
        content.accept(entry.get());
      });
    });
  }
}
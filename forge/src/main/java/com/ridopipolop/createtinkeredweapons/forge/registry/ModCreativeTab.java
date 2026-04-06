package com.ridopipolop.createtinkeredweapons.forge.registry;

import com.ridopipolop.createtinkeredweapons.CreateTinkeredWeapons;
import com.ridopipolop.createtinkeredweapons.registry.ModItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeTab {
  public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister
      .create(Registries.CREATIVE_MODE_TAB, CreateTinkeredWeapons.MOD_ID);

  public static final RegistryObject<CreativeModeTab> MAIN_TAB = CREATIVE_MODE_TABS.register("main",
      () -> CreativeModeTab.builder()
          .icon(() -> new ItemStack(ModItems.IMPACT_AXE.get()))
          .title(Component.translatable("itemGroup.createtinkeredweapons.main"))
          .displayItems((params, output) -> {
          }) // populated via event below
          .build());

  public static void register(IEventBus modEventBus) {
    CreateTinkeredWeapons.LOGGER.info("Registering Item Groups for {}.", CreateTinkeredWeapons.NAME);
    CREATIVE_MODE_TABS.register(modEventBus);
    modEventBus.addListener(ModCreativeTab::addCreativeTabContents);
  }

  private static void addCreativeTabContents(BuildCreativeModeTabContentsEvent event) {
    if (event.getTabKey().equals(MAIN_TAB.getKey())) {
      CreateTinkeredWeapons.REGISTRATE.getAll(Registries.ITEM).forEach(entry -> {
        event.accept(entry.get());
      });
    }
  }
}
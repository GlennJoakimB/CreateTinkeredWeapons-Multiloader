package com.ridopipolop.createtinkeredweapons.forge.registry;

// import static net.minecraftforge.network.NetworkDirection.PLAY_TO_CLIENT;
import static net.minecraftforge.network.NetworkDirection.PLAY_TO_SERVER;

import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

import com.ridopipolop.createtinkeredweapons.CreateTinkeredWeapons;
import com.ridopipolop.createtinkeredweapons.forge.packets.BroadGlaiveInteractionPacket;
import com.simibubi.create.foundation.networking.SimplePacketBase;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import net.minecraftforge.network.NetworkEvent.Context;

public enum ModPackets {
  // Client to Server
  BROAD_GLAIVE_INTERACT(BroadGlaiveInteractionPacket.class, BroadGlaiveInteractionPacket::new, PLAY_TO_SERVER);

  // Server to client
  // Nothing here yet

  public static final ResourceLocation CHANNEL_NAME = CreateTinkeredWeapons.id("main");
  public static final int NETWORK_VERSION = 3;
  public static final String NETWORK_VERSION_STR = String.valueOf(NETWORK_VERSION);
  private static SimpleChannel channel;
  private PacketType<?> packetType;

  <T extends SimplePacketBase> ModPackets(Class<T> type, Function<FriendlyByteBuf, T> factory,
      NetworkDirection direction) {
    packetType = new PacketType<>(type, factory, direction);
  }

  public static void registerPackets() {
    channel = NetworkRegistry.ChannelBuilder.named(CHANNEL_NAME)
        .serverAcceptedVersions(NETWORK_VERSION_STR::equals)
        .clientAcceptedVersions(NETWORK_VERSION_STR::equals)
        .networkProtocolVersion(() -> NETWORK_VERSION_STR)
        .simpleChannel();

    for (ModPackets packet : values())
      packet.packetType.register();
  }

  public static SimpleChannel getChannel() {
    return channel;
  }

  private static class PacketType<T extends SimplePacketBase> {
    private static int index = 0;
    private BiConsumer<T, FriendlyByteBuf> encoder;
    private Function<FriendlyByteBuf, T> decoder;
    private BiConsumer<T, Supplier<Context>> handler;
    private Class<T> type;
    private NetworkDirection direction;

    private PacketType(Class<T> type, Function<FriendlyByteBuf, T> factory, NetworkDirection direction) {
      encoder = T::write;
      decoder = factory;
      handler = (packet, contextSupplier) -> {
        Context context = contextSupplier.get();
        if (packet.handle(context))
          context.setPacketHandled(true);
      };
      this.type = type;
      this.direction = direction;
    }

    private void register() {
      getChannel().messageBuilder(type, index++, direction)
          .encoder(encoder)
          .decoder(decoder)
          .consumerNetworkThread(handler)
          .add();
    }
  }
}
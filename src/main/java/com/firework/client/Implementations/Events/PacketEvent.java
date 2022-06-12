package com.firework.client.Implementations.Events;

import net.minecraft.network.Packet;
import net.minecraftforge.fml.common.eventhandler.Event;

public class PacketEvent
extends Event {
    private final Packet packet;

    public PacketEvent(Packet packet) {
        this.packet = packet;
    }

    public boolean isCancelable() {
        return true;
    }

    public final Packet getPacket() {
        return this.packet;
    }

    public static final class Send
    extends PacketEvent {
        public Send(Packet packet) {
            super(packet);
        }
    }

    public static final class Receive
    extends PacketEvent {
        public Receive(Packet packet) {
            super(packet);
        }
    }
}

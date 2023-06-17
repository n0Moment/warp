package io.github.copecone.warp.event;

import io.github.copecone.warp.bus.WarpEvent;
import net.minecraft.client.MinecraftClient;

public class Event extends WarpEvent {
    public MinecraftClient mc = MinecraftClient.getInstance();

    public Event() {

    }
}
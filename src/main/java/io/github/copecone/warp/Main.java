package io.github.copecone.warp;

import io.github.copecone.warp.bus.EventBus;
import io.github.copecone.warp.litsener.DeathListener;
import io.github.copecone.warp.litsener.PacketListener;
import io.github.copecone.warp.litsener.TPSListener;
import net.fabricmc.api.ClientModInitializer;

import net.minecraft.client.MinecraftClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main implements ClientModInitializer {
    public static final MinecraftClient mc = MinecraftClient.getInstance();
    public static final Logger LOGGER = LoggerFactory.getLogger("coolmod");
    public static final EventBus EVENT_BUS = new EventBus();
    public static final Boolean advancedDebug = false;

    @Override
    public void onInitializeClient() {
        LOGGER.info("Warp Mod Based Client Has been Loaded.");
        EVENT_BUS.register(TPSListener.INSTANCE);
        LOGGER.info("Listening for TPS.");
        EVENT_BUS.register(DeathListener.INSTANCE);
        LOGGER.info("Listening for deaths.");
        // EVENT_BUS.register(PacketListener.INSTANCE);
        // LOGGER.info("Listening for packets.");
    }
}
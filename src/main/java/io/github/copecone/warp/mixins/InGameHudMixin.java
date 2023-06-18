package io.github.copecone.warp.mixins;

import io.github.copecone.warp.Main;
import io.github.copecone.warp.litsener.PacketListener;
import io.github.copecone.warp.litsener.TPSListener;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Formatting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public class InGameHudMixin {

    @Inject(method = "render", at = @At("RETURN"), cancellable = true)
    private void render(DrawContext context, float tickDelta, CallbackInfo ci) {
        if (Main.mc.player != null && Main.mc.world != null && !Main.mc.options.debugEnabled) {
            // Info
            float yaw = Main.mc.player.getYaw() % 360;
            float pitch = Main.mc.player.getPitch();
            String facing = Main.mc.player.getHorizontalFacing().name().substring(0, 1).toUpperCase()
                    + Main.mc.player.getHorizontalFacing().name().substring(1).toLowerCase();
            String axis = switch (facing) {
                case "North" -> "-Z";
                case "South" -> "+Z";
                case "East" -> "+X";
                case "West" -> "-X";
                default -> "?";
            };
            String biome = Main.mc.world.getRegistryManager().get(RegistryKeys.BIOME)
                    .getId(Main.mc.world.getBiome(Main.mc.player.getBlockPos()).value()).getPath().replace("_", " ");
            String cap_biome = biome.substring(0, 1).toUpperCase() + biome.substring(1).toLowerCase();
            String weather = Main.mc.world.isThundering() ? "Thundering" : Main.mc.world.isRaining() ? "Raining" : "Clear";
            String fps = Main.mc.fpsDebugString.split(" ", 2)[0];

            String song = Main.mc.getMusicType().getSound().getKey().toString().substring(45).replace("]", "");
            PlayerListEntry playerEntry = Main.mc.player.networkHandler.getPlayerListEntry(Main.mc.player.getGameProfile().getId());
            int latency = playerEntry == null ? 0 : playerEntry.getLatency();
            String player_info = String.format("X: %.2f / Y: %.2f / Z: %.2f / Yaw: %.2f / Pitch: %.2f / Facing: %s (%s)",
                    Main.mc.player.getX(), Main.mc.player.getY(), Main.mc.player.getZ(), yaw, pitch, facing, axis);

            String world_info = String.format("Biome: %s / Weather: %s", cap_biome, weather);
            String game_info = String.format("FPS: %s / TPS: %.2f / Ping: %s", fps, TPSListener.INSTANCE.ticks,
                    Main.mc.getCurrentServerEntry() == null ? "0" : latency);
            String latest_packets = String.format("Packet sent: %s / Packet received: %s",
            PacketListener.INSTANCE.getLastReceivedPacket(),
            PacketListener.INSTANCE.getLastSentPacket());

            // Draw the text
            context.drawTextWithShadow(Main.mc.textRenderer, player_info, 1, 1, Formatting.WHITE.getColorValue());
            context.drawTextWithShadow(Main.mc.textRenderer, game_info, 1, 11, Formatting.WHITE.getColorValue());
            context.drawTextWithShadow(Main.mc.textRenderer, world_info, 1, 21, Formatting.WHITE.getColorValue());

            // Advanced
            //if (Main.advancedDebug)
            //	context.drawTextWithShadow(Main.mc.textRenderer, latest_packets, 1, 31, Formatting.WHITE.getColorValue());
        }
    }
}
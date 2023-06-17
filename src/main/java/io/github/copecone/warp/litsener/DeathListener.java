package io.github.copecone.warp.litsener;

import io.github.copecone.warp.Main;
import io.github.copecone.warp.event.events.PacketEvent;
import io.github.copecone.warp.bus.Subscribed;
import net.minecraft.network.packet.s2c.play.HealthUpdateS2CPacket;
import net.minecraft.text.Text;
import java.awt.datatransfer.StringSelection;

public class DeathListener {
    public static DeathListener INSTANCE = new DeathListener();

    @Subscribed
    public void onPacket(PacketEvent.Receive event) {
        if (Main.mc.world == null && Main.mc.player == null) return;

        if (event.getPacket() instanceof HealthUpdateS2CPacket packet) {
            if (packet.getHealth() <= 0.0F) {
                int x = (int) Main.mc.player.getPos().getX();
                int y = (int) Main.mc.player.getPos().getY();
                int z = (int) Main.mc.player.getPos().getZ();
                //Main.LOGGER.info(("You died at " + x + ", " + y + ", " + z + " in the " + getDimension()));
                Main.mc.player.sendMessage(Text.of("You died at " + x + ", " + y + ", " + z + " in the " + getDimension()));

                // Copy to clipboard
                try {
                    StringSelection stringSelection = new StringSelection(x + ", " + y + ", " + z + ", " + getDimension());
                    java.awt.datatransfer.Clipboard clipboard = java.awt.Toolkit.getDefaultToolkit().getSystemClipboard();
                    clipboard.setContents(stringSelection, null);
                    Main.mc.player.sendMessage(Text.of("Copied to clipboard!"));
                } catch (Exception e) {
                    Main.LOGGER.error("Failed to copy to clipboard: " + e);
                }
            }
        }
    }

    private String getDimension() {
        if (Main.mc.world.getDimension().respawnAnchorWorks())
            return "Nether";
        else
            return "Overworld";
    }
}
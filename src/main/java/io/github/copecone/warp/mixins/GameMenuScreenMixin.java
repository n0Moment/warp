package io.github.copecone.warp.mixins;

import java.util.List;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.fabric.api.client.screen.v1.Screens;
import net.minecraft.client.gui.screen.GameMenuScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.crash.CrashException;
import net.minecraft.util.crash.CrashReport;

@Mixin(GameMenuScreen.class)
public abstract class GameMenuScreenMixin extends Screen
{
    private ButtonWidget OptionButton;

    /*private GameMenuScreenMixin(Text text_1)
    {
        super(text_1);
    }*/

    @Inject(at = {@At("TAIL")}, method = {"initWidgets()V"})
    private void onInitWidgets(CallbackInfo ci)
    {
        addClientOptionButton();
    }

    private void addClientOptionButton()
    {
        List<ClickableWidget> buttons = Screens.getButtons(this);

        int buttonY = -1;
        int buttonI = -1;
        if(buttonY == -1 || buttonI == -1)
            throw new CrashException(
                    CrashReport.create(new IllegalStateException(),
                            "Error While Creating Client Mod exception"));

        OptionButton = ButtonWidget
                .builder(Text.literal("Warp Options"),
                        b -> System.out.println("Someone Clicked a Button! :)#"))
                .dimensions(width / 2 - 102, buttonY, 204, 20).build();
        buttons.add(OptionButton);
    }

    private boolean hasTrKey(ClickableWidget button, String key)
    {
        String message = button.getMessage().getString();
        return message != null && message.equals(I18n.translate(key));
    }

    @Inject(at = {@At("TAIL")},
            method = {"render(Lnet/minecraft/client/util/math/MatrixStack;IIF)V"})
    private void onRender(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks, CallbackInfo ci)
    {
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(false);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        RenderSystem.setShaderColor(1, 1, 1, 1);

        int x = OptionButton.getX() + 34;
        int y = OptionButton.getY() + 2;
        int w = 63;
        int h = 16;
        int fw = 63;
        int fh = 16;
        float u = 0;
        float v = 0;
        drawTexture(matrixStack, x, y, u, v, w, h, fw, fh);
    }
}
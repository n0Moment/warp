package io.github.copecone.warp.mixins;

import net.minecraft.client.util.telemetry.TelemetryManager;
import net.minecraft.client.util.telemetry.TelemetrySender;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import io.github.copecone.warp.Main;
@Mixin(TelemetryManager.class)
public class TelemetryManagerMixin {

    @Inject(at = @At("HEAD"), method = "getSender()Lnet/minecraft/client/util/telemetry/TelemetrySender;", cancellable = true)
    private void onGetSender(CallbackInfoReturnable<TelemetrySender> cir) {
        cir.setReturnValue(TelemetrySender.NOOP);
        Main.LOGGER.info("Telementary blocked. L");
    }
}
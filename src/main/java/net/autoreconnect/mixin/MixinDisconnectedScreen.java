package net.autoreconnect.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.screen.DisconnectedScreen;
import net.minecraft.client.util.Window;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static net.autoreconnect.AutoReconnect.attempt;
import static net.autoreconnect.AutoReconnect.getCountdown;

@Mixin(DisconnectedScreen.class)
public class MixinDisconnectedScreen
{
	@Inject(at = @At("RETURN"), method = "shouldCloseOnEsc", cancellable = true)
	private void shouldCloseOnEsc(CallbackInfoReturnable<Boolean> info)
	{
		info.setReturnValue(true);
	}

	@Inject(at = @At("RETURN"), method = "render")
	private void render(MatrixStack matrices, int mouseX, int mouseY, float delta, CallbackInfo info)
	{
		Window window = MinecraftClient.getInstance().getWindow();
		DrawableHelper.drawCenteredString(matrices,
			MinecraftClient.getInstance().textRenderer,
			attempt == -1 ? "Can not reconnect!" : "Reconnecting in " + getCountdown() + "...",
			window.getScaledWidth() / 2,
			window.getScaledHeight() / 4,
			0xFF4422);
	}
}
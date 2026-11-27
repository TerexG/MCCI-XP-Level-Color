package terexg.mccixplevelcolor.mixin;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.network.chat.Component;
import terexg.mccixplevelcolor.client.MCCIXPLevelColorClient;
import net.minecraft.client.gui.contextualbar.ContextualBar;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(ContextualBar.class)
public interface ContextualBarMixin {

	@Inject(method = "extractExperienceLevel", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;text{1}(Lnet/minecraft/client/gui/Font;Lnet/minecraft/network/chat/Component;IIIZ)V", ordinal = 0), cancellable = true, locals = LocalCapture.CAPTURE_FAILSOFT)
	private static void onExtractExperienceLevel(final GuiGraphicsExtractor graphics, final Font font, final int experienceLevel, CallbackInfo ci, Component str, int x, int y) {
		
		if (MCCIXPLevelColorClient.modEnabled()) {
			int experienceLevelColor = MCCIXPLevelColorClient.getExperienceLevelColor(experienceLevel)[0];
			int experienceLevelOutlineColor = MCCIXPLevelColorClient.getExperienceLevelColor(experienceLevel)[1];

			graphics.text(font, str, x + 1, y, experienceLevelOutlineColor, false);
			graphics.text(font, str, x - 1, y, experienceLevelOutlineColor, false);
			graphics.text(font, str, x, y + 1, experienceLevelOutlineColor, false);
			graphics.text(font, str, x, y - 1, experienceLevelOutlineColor, false);
			graphics.text(font, str, x, y, experienceLevelColor, false);

			ci.cancel();
		}
	}
}
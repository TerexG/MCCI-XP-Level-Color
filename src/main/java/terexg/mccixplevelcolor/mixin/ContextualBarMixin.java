package terexg.mccixplevelcolor.mixin;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.network.chat.Component;
import terexg.mccixplevelcolor.client.MCCIXPLevelColorClient;
import net.minecraft.client.gui.contextualbar.ContextualBar;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(ContextualBar.class)
public interface ContextualBarMixin {
	
	@Overwrite
	static void extractExperienceLevel(final GuiGraphicsExtractor graphics, final Font font, final int experienceLevel) {
		int experienceLevelColor = MCCIXPLevelColorClient.getExperienceLevelColor(experienceLevel);
		int experienceLevelOutlineColor = MCCIXPLevelColorClient.getExperienceLevelOutlineColor(experienceLevel);

		Component str = Component.translatable("gui.experience.level", experienceLevel);
		int x = (graphics.guiWidth() - font.width(str)) / 2;
		int y = graphics.guiHeight() - 24 - 9 - 2;
		graphics.text(font, str, x + 1, y, experienceLevelOutlineColor, false);
		graphics.text(font, str, x - 1, y, experienceLevelOutlineColor, false);
		graphics.text(font, str, x, y + 1, experienceLevelOutlineColor, false);
		graphics.text(font, str, x, y - 1, experienceLevelOutlineColor, false);
		graphics.text(font, str, x, y, experienceLevelColor, false);
	}
}
package terexg.mccixplevelcolor.config;

import dev.isxander.yacl3.api.ConfigCategory;
import dev.isxander.yacl3.api.OptionDescription;
import dev.isxander.yacl3.api.YetAnotherConfigLib;
import dev.isxander.yacl3.api.controller.ColorControllerBuilder;
import dev.isxander.yacl3.api.controller.EnumControllerBuilder;
import dev.isxander.yacl3.api.controller.TickBoxControllerBuilder;
import dev.isxander.yacl3.api.Option;
import dev.isxander.yacl3.api.NameableEnum;
import net.minecraft.network.chat.Component;
import net.minecraft.client.gui.screens.Screen;
import java.awt.Color;

public class ConfigScreen {
    public enum modModes implements NameableEnum {
        VANILLA,
        STATIC,
        DYNAMIC,
        CUSTOM;

        @Override
        public Component getDisplayName() {
            return Component.translatable("mcci-xp-level-color.mode." + toString().toLowerCase());
        }
    }

    public static Screen createConfigScreen(Screen parentScreen) {
        Config.HANDLER.load();
        return YetAnotherConfigLib.createBuilder()
        .title(Component.translatable("mcci-xp-level-color.title"))
        .save(Config.HANDLER::save)
        .category(ConfigCategory.createBuilder()
                .name(Component.translatable("mcci-xp-level-color.category.general"))
                .tooltip(Component.translatable("mcci-xp-level-color.category.general.tooltip"))
                .option(Option.<modModes>createBuilder()
                        .name(Component.translatable("mcci-xp-level-color.mode"))
                        .description(OptionDescription.of(Component.translatable("mcci-xp-level-color.mode.description")))
                        .binding(modModes.DYNAMIC, () -> modModes.valueOf(Config.HANDLER.instance().mode.toUpperCase()), newVal -> Config.HANDLER.instance().mode = newVal.toString().toLowerCase())
                        .controller(opt -> EnumControllerBuilder.create(opt)
                                .enumClass(modModes.class))
                        .build())
                .option(Option.<Color>createBuilder()
                        .name(Component.translatable("mcci-xp-level-color.custom-color"))
                        .description(OptionDescription.of(Component.translatable("mcci-xp-level-color.custom-color.description")))
                        .binding(new Color(0xFF80FF20, true), () -> new Color(Config.HANDLER.instance().customColor, true), newVal -> Config.HANDLER.instance().customColor = newVal.getRGB())
                        .controller(opt -> ColorControllerBuilder.create(opt)
                                .allowAlpha(true))
                        .build())
                .option(Option.<Color>createBuilder()
                        .name(Component.translatable("mcci-xp-level-color.custom-outline-color"))
                        .description(OptionDescription.of(Component.translatable("mcci-xp-level-color.custom-outline-color.description")))
                        .binding(new Color(0xFF000000, true), () -> new Color(Config.HANDLER.instance().customOutlineColor, true), newVal -> Config.HANDLER.instance().customOutlineColor = newVal.getRGB())
                        .controller(opt -> ColorControllerBuilder.create(opt)
                                .allowAlpha(true))
                        .build())
                .option(Option.<Boolean>createBuilder()
                        .name(Component.translatable("mcci-xp-level-color.disable-mcci-check"))
                        .description(OptionDescription.of(Component.translatable("mcci-xp-level-color.disable-mcci-check.description")))
                        .binding(false, () -> Config.HANDLER.instance().disableMCCICheck, newVal -> Config.HANDLER.instance().disableMCCICheck = newVal)
                        .controller(TickBoxControllerBuilder::create)
                        .build())
                .build())
        .build()
        .generateScreen(parentScreen);
    }
}
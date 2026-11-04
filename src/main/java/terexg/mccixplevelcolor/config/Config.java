package terexg.mccixplevelcolor.config;

import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;
import net.minecraft.resources.Identifier;
import net.fabricmc.loader.api.FabricLoader;

public class Config {
    public static ConfigClassHandler<Config> HANDLER = ConfigClassHandler.createBuilder(Config.class)
            .id(Identifier.fromNamespaceAndPath("mcci-xp-level-color", "config"))
            .serializer(config -> GsonConfigSerializerBuilder.create(config)
                    .setPath(FabricLoader.getInstance().getConfigDir().resolve("mcci-xp-level-color.json5"))
                    .setJson5(true)
                    .build())
            .build();
            
    @SerialEntry
    public String mode = "dynamic";

    @SerialEntry
    public int customColor = 0xFF80FF20;

    @SerialEntry
    public int customOutlineColor = 0xFF000000;

    @SerialEntry
    public boolean disableMCCICheck = false;
}
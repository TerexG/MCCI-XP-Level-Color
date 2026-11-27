package terexg.mccixplevelcolor.client;

import java.util.Locale;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.minecraft.client.multiplayer.ServerData;
import terexg.mccixplevelcolor.config.Config;

public class MCCIXPLevelColorClient implements ClientModInitializer {

	// MCC Island join detection taken from MCC Island Tooltips
	// https://github.com/derNiklaas/MCC-Island-Tooltips/blob/master/src/main/java/de/derniklaas/mccislandtooltips/client/MCCIslandTooltipsClient.java

	private static boolean onMCCIsland = false;

	@Override
	public void onInitializeClient() {
		Config.HANDLER.load();
		ClientPlayConnectionEvents.JOIN.register((_, _, client) -> {
			ServerData server = client.getCurrentServer();
			onMCCIsland = server != null && isMCCIslandAddress(server.ip);
		});
		ClientPlayConnectionEvents.DISCONNECT.register((_, _) -> onMCCIsland = false);
	}
	
    private static boolean isMCCIslandAddress(String address) {
        String host = address.toLowerCase(Locale.ROOT);
        int portSeparator = host.lastIndexOf(':');
        if (portSeparator != -1) {
            host = host.substring(0, portSeparator);
        }
        return host.equals("mccisland.net") || host.endsWith(".mccisland.net");
    }

	public static int[] getExperienceLevelColor(int level) {
		switch (Config.HANDLER.instance().mode) {
			case "static":
				return new int[]{0xFFFFE600, 0xFF590000};
			case "dynamic":
				return getDynamicColor(level);
			case "custom":
				return new int[]{Config.HANDLER.instance().customColor, Config.HANDLER.instance().customOutlineColor};
			default:
				return new int[]{0xFF80FF20, 0xFF000000};
		}
	}

	private static int[] getDynamicColor(int level) {
		if (level < 10) {
			return new int[]{0xFF858586, 0xFF0E0E0E};
		} else if (level < 20) {
			return new int[]{0xFFCDCDED, 0xFF282855};
		} else if (level < 30) {
			return new int[]{0xFF9DFFA2, 0xFF084300};
		} else if (level < 40) {
			return new int[]{0xFF89DAFF, 0xFF001A41};
		} else if (level < 50) {
			return new int[]{0xFFE86BFF, 0xFF12106B};
		} else if (level < 60) {
			return new int[]{0xFFFFC300, 0xFF530300};
		} else if (level < 70) {
			return new int[]{0xFFFF9F82, 0xFF7B0035};
		} else if (level < 80) {
			return new int[]{0xFF8DF0D3, 0xFF154AB5};
		} else if (level < 90) {
			return new int[]{0xFF91F1FF, 0xFF6002BB};
		} else if (level < 100) {
			return new int[]{0xFFF75BFF, 0xFF7B0035};
		} else {
			return new int[]{0xFFF278FD, 0xFF191672};
		}
	}

	public static boolean modEnabled() {
		return Config.HANDLER.instance().mode != "vanilla" && (onMCCIsland || Config.HANDLER.instance().disableMCCICheck);
	}
}
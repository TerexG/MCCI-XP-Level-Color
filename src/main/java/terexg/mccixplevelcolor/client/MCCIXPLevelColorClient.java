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

	public static int getExperienceLevelColor(int level) {
		if (onMCCIsland || Config.HANDLER.instance().disableMCCICheck) {
			switch (Config.HANDLER.instance().mode) {
				case "vanilla":
					return 0xFF80FF20;
				case "static":
					return 0xFFFFE600;
				case "dynamic":
					return getDynamicColor(level);
				case "custom":
					return Config.HANDLER.instance().customColor;
				default:
					return 0xFF80FF20;
			}
		} else {
			return 0xFF80FF20;
		}
	}

	public static int getExperienceLevelOutlineColor(int level) {
		if (onMCCIsland || Config.HANDLER.instance().disableMCCICheck) {
			switch (Config.HANDLER.instance().mode) {
				case "vanilla":
					return 0xFF000000;
				case "static":
					return 0xFF590000;
				case "dynamic":
					return getDynamicOutlineColor(level);
				case "custom":
					return Config.HANDLER.instance().customOutlineColor;
				default:
					return 0xFF000000;
			}
		} else {
			return 0xFF000000;
		}
	}

	private static int getDynamicColor(int level) {
		if (level < 10) {
			return 0xFF858586;
		} else if (level < 20) {
			return 0xFFCDCDED;
		} else if (level < 30) {
			return 0xFF9DFFA2;
		} else if (level < 40) {
			return 0xFF89DAFF;
		} else if (level < 50) {
			return 0xFFE86BFF;
		} else if (level < 60) {
			return 0xFFFFC300;
		} else if (level < 70) {
			return 0xFFFF9F82;
		} else if (level < 80) {
			return 0xFF8DF0D3;
		} else if (level < 90) {
			return 0xFF91F1FF;
		} else if (level < 100) {
			return 0xFFF75BFF;
		} else {
			return 0xFFF278FD;
		}
	}

	private static int getDynamicOutlineColor(int level) {
		if (level < 10) {
			return 0xFF0E0E0E;
		} else if (level < 20) {
			return 0xFF282855;
		} else if (level < 30) {
			return 0xFF084300;
		} else if (level < 40) {
			return 0xFF001A41;
		} else if (level < 50) {
			return 0xFF12106B;
		} else if (level < 60) {
			return 0xFF530300;
		} else if (level < 70) {
			return 0xFF7B0035;
		} else if (level < 80) {
			return 0xFF154AB5;
		} else if (level < 90) {
			return 0xFF6002BB;
		} else if (level < 100) {
			return 0xFF7B0035;
		} else {
			return 0xFF191672;
		}
	}
}
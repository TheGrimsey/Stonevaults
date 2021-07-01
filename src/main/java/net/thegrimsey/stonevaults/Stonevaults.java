package net.thegrimsey.stonevaults;

import net.fabricmc.api.ModInitializer;

public class Stonevaults implements ModInitializer {
	public static String MODID = "stonevaults";

	@Override
	public void onInitialize() {
		StonevaultStructures.registerStructures();
		StonevaultsProcessors.registerProcessors();
	}
}

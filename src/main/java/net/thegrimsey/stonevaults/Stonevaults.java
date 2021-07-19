package net.thegrimsey.stonevaults;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import net.fabricmc.api.ModInitializer;

public class Stonevaults implements ModInitializer {
    public static final String MODID = "stonevaults";
    public static StonevaultsConfig CONFIG;

    @Override
    public void onInitialize() {
        // Register config file.
        AutoConfig.register(StonevaultsConfig.class, JanksonConfigSerializer::new);
        // Get config.
        CONFIG = AutoConfig.getConfigHolder(StonevaultsConfig.class).getConfig();

        StonevaultStructures.registerStructures();
        StonevaultsProcessors.registerProcessors();
    }
}

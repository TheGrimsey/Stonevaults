package net.thegrimsey.stonevaults;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectionContext;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.biome.v1.ModificationPhase;
import net.fabricmc.fabric.api.structure.v1.FabricStructureBuilder;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.chunk.StructureConfig;
import net.minecraft.world.gen.feature.ConfiguredStructureFeature;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;
import net.thegrimsey.stonevaults.structures.DungeonStructure;
import net.thegrimsey.stonevaults.structures.IglooStructure;
import net.thegrimsey.stonevaults.structures.MageTowerStructure;

import java.util.function.Predicate;

public class StonevaultStructures {
    static final StructureFeature<DefaultFeatureConfig> MAGETOWER = new MageTowerStructure(DefaultFeatureConfig.CODEC);
    static ConfiguredStructureFeature<?, ?> CONFIGURED_MAGETOWER = MAGETOWER.configure(DefaultFeatureConfig.DEFAULT);

    static final StructureFeature<DefaultFeatureConfig> IGLOO = new IglooStructure(DefaultFeatureConfig.CODEC);
    static ConfiguredStructureFeature<?, ?> CONFIGURED_IGLOO = IGLOO.configure(DefaultFeatureConfig.DEFAULT);

    static final StructureFeature<DefaultFeatureConfig> DUNGEON = new DungeonStructure(DefaultFeatureConfig.CODEC);
    static ConfiguredStructureFeature<?, ?> CONFIGURED_DUNGEON = DUNGEON.configure(DefaultFeatureConfig.DEFAULT);

    public static void registerStructures()
    {
        registerStructure("magetower", MAGETOWER, CONFIGURED_MAGETOWER, BiomeSelectors.foundInOverworld().and(BiomeSelectors.categories(Biome.Category.FOREST, Biome.Category.JUNGLE)), new StructureConfig(20, 10, 383209018), true);
        registerStructure("igloo", IGLOO, CONFIGURED_IGLOO, BiomeSelectors.foundInOverworld().and(BiomeSelectors.categories(Biome.Category.ICY)), new StructureConfig(20, 10, 38419), true);
        registerStructure("dungeon", DUNGEON, CONFIGURED_DUNGEON, BiomeSelectors.foundInOverworld().and(BiomeSelectors.categories(Biome.Category.OCEAN).negate()), new StructureConfig(20, 10, 2238181), false);
    }

    static void registerStructure(String Id, StructureFeature<DefaultFeatureConfig> structureFeature, ConfiguredStructureFeature<?, ?> configuredStructureFeature, Predicate<BiomeSelectionContext> biomes, StructureConfig structureConfig, boolean adjustSurface)
    {
        Identifier identifier = new Identifier(Stonevaults.MODID, Id);
        Identifier configured_identifier = new Identifier(Stonevaults.MODID, "configured_" + Id);

        var structureBuilder = FabricStructureBuilder.create(identifier, structureFeature)
                .step(GenerationStep.Feature.SURFACE_STRUCTURES)
                .defaultConfig(structureConfig)
                .superflatFeature(structureFeature.configure(FeatureConfig.DEFAULT));

        if(adjustSurface)
            structureBuilder.adjustsSurface();

        structureBuilder.register();

        Registry.register(BuiltinRegistries.CONFIGURED_STRUCTURE_FEATURE, configured_identifier, configuredStructureFeature);

        // Add structures to biomes.
        BiomeModifications.create(identifier)
                .add(ModificationPhase.ADDITIONS,
                        biomes,
                        context -> context.getGenerationSettings().addBuiltInStructure(configuredStructureFeature));
    }
}

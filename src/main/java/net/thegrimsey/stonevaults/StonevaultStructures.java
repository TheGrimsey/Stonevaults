package net.thegrimsey.stonevaults;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectionContext;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.biome.v1.ModificationPhase;
import net.fabricmc.fabric.api.structure.v1.FabricStructureBuilder;
import net.minecraft.structure.PlainsVillageData;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.chunk.StructureConfig;
import net.minecraft.world.gen.feature.ConfiguredStructureFeature;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraft.world.gen.feature.StructurePoolFeatureConfig;
import net.thegrimsey.stonevaults.structures.DungeonStructure;
import net.thegrimsey.stonevaults.structures.IglooStructure;
import net.thegrimsey.stonevaults.structures.MageTowerStructure;
import net.thegrimsey.stonevaults.structures.PillagerDungeonStructure;

import java.util.function.Predicate;

public class StonevaultStructures {
    static final StructureFeature<StructurePoolFeatureConfig> MAGETOWER = new MageTowerStructure(StructurePoolFeatureConfig.CODEC);
    static final ConfiguredStructureFeature<?, ?> CONFIGURED_MAGETOWER = MAGETOWER.configure(new StructurePoolFeatureConfig(
            () -> PlainsVillageData.STRUCTURE_POOLS,
            0));

    static final StructureFeature<StructurePoolFeatureConfig> IGLOO = new IglooStructure(StructurePoolFeatureConfig.CODEC);
    static final ConfiguredStructureFeature<?, ?> CONFIGURED_IGLOO = IGLOO.configure(new StructurePoolFeatureConfig(
            () -> PlainsVillageData.STRUCTURE_POOLS,
            0));

    static final StructureFeature<StructurePoolFeatureConfig> DUNGEON = new DungeonStructure(StructurePoolFeatureConfig.CODEC);
    static final ConfiguredStructureFeature<?, ?> CONFIGURED_DUNGEON = DUNGEON.configure(new StructurePoolFeatureConfig(
            () -> PlainsVillageData.STRUCTURE_POOLS,
            0));

    static final StructureFeature<StructurePoolFeatureConfig> PILLAGER_DUNGEON = new PillagerDungeonStructure(StructurePoolFeatureConfig.CODEC);
    static final ConfiguredStructureFeature<?, ?> CONFIGURED_PILLAGER_DUNGEON = PILLAGER_DUNGEON.configure(new StructurePoolFeatureConfig(
            () -> PlainsVillageData.STRUCTURE_POOLS,
            0));

    public static void registerStructures() {
        registerStructure("magetower", MAGETOWER, CONFIGURED_MAGETOWER, BiomeSelectors.foundInOverworld().and(BiomeSelectors.categories(Biome.Category.FOREST, Biome.Category.JUNGLE)), Stonevaults.CONFIG.MAGETOWER.STRUCTURE_CONFIG, true);
        registerStructure("igloo", IGLOO, CONFIGURED_IGLOO, BiomeSelectors.foundInOverworld().and(BiomeSelectors.categories(Biome.Category.ICY)), Stonevaults.CONFIG.IGLOO.STRUCTURE_CONFIG, true);

        Predicate<BiomeSelectionContext> pillagerDungeonPredicate = BiomeSelectors.categories(Biome.Category.ICY, Biome.Category.TAIGA).or(BiomeSelectors.includeByKey(BiomeKeys.DARK_FOREST));
        registerStructure("dungeon", DUNGEON, CONFIGURED_DUNGEON, BiomeSelectors.foundInOverworld().and(BiomeSelectors.categories(Biome.Category.OCEAN, Biome.Category.BEACH, Biome.Category.ICY).negate()).and(pillagerDungeonPredicate.negate()), Stonevaults.CONFIG.DUNGEON.STRUCTURE_CONFIG, false);
        registerStructure("pillager_dungeon", PILLAGER_DUNGEON, CONFIGURED_PILLAGER_DUNGEON, pillagerDungeonPredicate.and(BiomeSelectors.foundInOverworld()), Stonevaults.CONFIG.DUNGEON.STRUCTURE_CONFIG, false);
    }

    static void registerStructure(String Id, StructureFeature<StructurePoolFeatureConfig> structureFeature, ConfiguredStructureFeature<?, ?> configuredStructureFeature, Predicate<BiomeSelectionContext> biomes, StructureConfig structureConfig, boolean adjustSurface) {
        Identifier identifier = new Identifier(Stonevaults.MODID, Id);
        Identifier configured_identifier = new Identifier(Stonevaults.MODID, "configured_" + Id);

        var structureBuilder = FabricStructureBuilder.create(identifier, structureFeature)
                .step(GenerationStep.Feature.SURFACE_STRUCTURES)
                .defaultConfig(structureConfig);
        if (adjustSurface)
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

package net.thegrimsey.stonevaults.structures;

import com.mojang.serialization.Codec;
import net.minecraft.structure.MarginedStructureStart;
import net.minecraft.structure.PoolStructurePiece;
import net.minecraft.structure.StructureManager;
import net.minecraft.structure.pool.StructurePoolBasedGenerator;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.registry.DynamicRegistryManager;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.HeightLimitView;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraft.world.gen.feature.StructurePoolFeatureConfig;
import net.thegrimsey.stonevaults.Stonevaults;

public class DungeonStructure extends StructureFeature<DefaultFeatureConfig> {
    public static Identifier START_POOL = new Identifier(Stonevaults.MODID, "startpool_dungeon");

    public DungeonStructure(Codec<DefaultFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public StructureStartFactory<DefaultFeatureConfig> getStructureStartFactory() {
        return DungeonStructure.Start::new;
    }

    public static class Start extends MarginedStructureStart<DefaultFeatureConfig> {
        private static StructurePoolFeatureConfig structurePoolFeatureConfig = null;

        public Start(StructureFeature<DefaultFeatureConfig> structureIn, ChunkPos pos, int referenceIn, long seedIn) {
            super(structureIn, pos, referenceIn, seedIn);
        }

        @Override
        public void init(DynamicRegistryManager registryManager, ChunkGenerator chunkGenerator, StructureManager manager, ChunkPos pos, Biome biome, DefaultFeatureConfig config, HeightLimitView world) {
            // Position, we don't care about Y as we will just be placed on top on the terrain.
            BlockPos blockPos = new BlockPos(pos.x << 4, 0, pos.z << 4);

            // Initialize structurePoolFeatureConfig if it is null. Doing it everytime we spawn creates garbage so we just make one.
            if (structurePoolFeatureConfig == null)
                structurePoolFeatureConfig = new StructurePoolFeatureConfig(() -> registryManager.get(Registry.STRUCTURE_POOL_KEY).get(START_POOL), 7);

            // Spawn structure.
            StructurePoolBasedGenerator.method_30419(registryManager,
                    structurePoolFeatureConfig,
                    PoolStructurePiece::new, chunkGenerator, manager, blockPos, this, this.random, false, true, world);

            this.children.forEach(structurePiece -> {
                structurePiece.translate(0, 1, 0);
            });

            this.setBoundingBoxFromChildren();
        }
    }
}

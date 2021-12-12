package net.thegrimsey.stonevaults.structures;

import com.mojang.serialization.Codec;
import net.minecraft.structure.PoolStructurePiece;
import net.minecraft.structure.StructureGeneratorFactory;
import net.minecraft.structure.StructurePiecesGenerator;
import net.minecraft.structure.pool.StructurePoolBasedGenerator;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraft.world.gen.feature.StructurePoolFeatureConfig;
import net.thegrimsey.stonevaults.Stonevaults;
import net.thegrimsey.stonevaults.mixin.StructurePoolFeatureConfigAccessor;

import java.util.Optional;

public class DungeonStructure extends StructureFeature<StructurePoolFeatureConfig> {
    public static final Identifier START_POOL = new Identifier(Stonevaults.MODID, "startpool_dungeon");
    public static Identifier START_POOL_LONG = new Identifier(Stonevaults.MODID, "startpool_dungeon_long");

    public DungeonStructure(Codec<StructurePoolFeatureConfig> codec) {
        super(codec, DungeonStructure::createPiecesGenerator);
    }

    public static Optional<StructurePiecesGenerator<StructurePoolFeatureConfig>> createPiecesGenerator(StructureGeneratorFactory.Context<StructurePoolFeatureConfig> context) {
        int x = context.chunkPos().x << 4;
        int z = context.chunkPos().z << 4;

        // Position, we don't care about Y as we will just be placed on top on the terrain.
        BlockPos blockPos = new BlockPos(x, 0, z);

        ((StructurePoolFeatureConfigAccessor)context.config()).setStructures(() -> context.registryManager().get(Registry.STRUCTURE_POOL_KEY).get(START_POOL));
        ((StructurePoolFeatureConfigAccessor)context.config()).setSize(Stonevaults.CONFIG.DUNGEON.SIZE);

        return StructurePoolBasedGenerator.generate(context, PoolStructurePiece::new, blockPos, false, true);
    }
}

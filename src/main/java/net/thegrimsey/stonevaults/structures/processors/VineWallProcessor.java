package net.thegrimsey.stonevaults.structures.processors;

import com.mojang.serialization.Codec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.VineBlock;
import net.minecraft.structure.Structure;
import net.minecraft.structure.StructurePlacementData;
import net.minecraft.structure.processor.StructureProcessor;
import net.minecraft.structure.processor.StructureProcessorType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.WorldView;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.ChunkRandom;
import net.thegrimsey.stonevaults.StonevaultsProcessors;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class VineWallProcessor extends StructureProcessor {
    public static final Codec<VineWallProcessor> CODEC = Codec.FLOAT.fieldOf("probability").xmap(VineWallProcessor::new, (vineWallProcessor) -> vineWallProcessor.probability).codec();

    private final float probability;
    public VineWallProcessor(float probability)
    {
        this.probability = probability;
    }

    @Nullable
    @Override
    public Structure.StructureBlockInfo process(WorldView world, BlockPos pos, BlockPos pivot, Structure.StructureBlockInfo structureBlockInfoLocal, Structure.StructureBlockInfo structureBlockInfoWorld, StructurePlacementData data) {
        if(structureBlockInfoWorld.state.isAir())
        {
            Random chunkRandom = new ChunkRandom();
            chunkRandom.setSeed(structureBlockInfoWorld.pos.asLong() * structureBlockInfoWorld.pos.getY());
            if(chunkRandom.nextFloat() < probability)
            {
                // This is the position we place a vine in.
                BlockState worldBlockState = world.getBlockState(structureBlockInfoWorld.pos);

                if(worldBlockState.isAir())
                {
                    BlockPos.Mutable mutPos = new BlockPos.Mutable();
                    for(Direction direction : Direction.Type.HORIZONTAL)
                    {
                        mutPos.set(structureBlockInfoWorld.pos).move(direction);
                        BlockState directionState = world.getBlockState(mutPos);

                        // Only place on full faces.
                        if(Block.isFaceFullSquare(directionState.getCollisionShape(world, pos), direction.getOpposite()) && !directionState.isAir())
                        {
                            BlockState vineBlock = Blocks.VINE.getDefaultState().with(VineBlock.getFacingProperty(direction), true);

                            Chunk chunk = world.getChunk(structureBlockInfoWorld.pos);
                            chunk.setBlockState(structureBlockInfoWorld.pos, vineBlock, false);
                            break;
                        }
                    }
                }
            }
        }


        return structureBlockInfoWorld;
    }

    @Override
    protected StructureProcessorType<?> getType() {
        return StonevaultsProcessors.VINEWALL_PROCESSOR;
    }
}

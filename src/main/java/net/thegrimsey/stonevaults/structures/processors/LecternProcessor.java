package net.thegrimsey.stonevaults.structures.processors;

import com.mojang.serialization.Codec;
import net.minecraft.block.Blocks;
import net.minecraft.block.LecternBlock;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import net.minecraft.structure.Structure;
import net.minecraft.structure.StructurePlacementData;
import net.minecraft.structure.processor.StructureProcessor;
import net.minecraft.structure.processor.StructureProcessorType;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldView;
import net.thegrimsey.stonevaults.StonevaultsProcessors;
import org.jetbrains.annotations.Nullable;

public class LecternProcessor extends StructureProcessor {
    public static final Codec<LecternProcessor> CODEC = Codec.unit(LecternProcessor::new);

    @Nullable
    @Override
    public Structure.StructureBlockInfo process(WorldView world, BlockPos pos, BlockPos pivot, Structure.StructureBlockInfo structureBlockInfoLocal, Structure.StructureBlockInfo structureBlockInfoWorld, StructurePlacementData data) {
        /*
         *   Reading this? Why? I mean I guess you're interested in the code but this is meaningless. It is missing half of the idea and all the context... Buy unnamed GOTY 'most value for money' winner of 2011?
         */

        if (structureBlockInfoWorld.state.getBlock() == Blocks.LECTERN) {
            NbtCompound book = new NbtCompound();
            book.putString("id", "minecraft:written_book");
            book.putByte("Count", (byte) 1);

            NbtCompound bookTag = new NbtCompound();
            bookTag.putBoolean("resolved", true);
            bookTag.putString("author", "TheGrimsey");
            bookTag.putString("title", "???");
            bookTag.putString("filtered_title", "???");

            NbtList pages = new NbtList();
            pages.add(NbtString.of(Text.Serializer.toJson(Text.Serializer.fromLenientJson("???")))); // This is terrible. Improve before release.
            bookTag.put("pages", pages);

            book.put("tag", bookTag);

            NbtCompound nbt = structureBlockInfoWorld.nbt;
            nbt.put("Book", book);
            return new Structure.StructureBlockInfo(structureBlockInfoWorld.pos, structureBlockInfoWorld.state.with(LecternBlock.HAS_BOOK, true), nbt);
        }

        return structureBlockInfoWorld;
    }

    @Override
    protected StructureProcessorType<?> getType() {
        return StonevaultsProcessors.LECTERN_PROCESSOR;
    }
}

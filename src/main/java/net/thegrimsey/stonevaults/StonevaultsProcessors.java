package net.thegrimsey.stonevaults;

import net.minecraft.structure.processor.StructureProcessorType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.thegrimsey.stonevaults.structures.processors.LecternProcessor;
import net.thegrimsey.stonevaults.structures.processors.NoWaterProcessor;
import net.thegrimsey.stonevaults.structures.processors.VineWallProcessor;

public class StonevaultsProcessors {
    public static final StructureProcessorType<VineWallProcessor> VINEWALL_PROCESSOR = () -> VineWallProcessor.CODEC;
    public static final StructureProcessorType<NoWaterProcessor> NOWATER_PROCESSOR = () -> NoWaterProcessor.CODEC;
    public static final StructureProcessorType<LecternProcessor> LECTERN_PROCESSOR = () -> LecternProcessor.CODEC;

    public static void registerProcessors() {
        Registry.register(Registry.STRUCTURE_PROCESSOR, new Identifier(Stonevaults.MODID, "vinewall_processor"), VINEWALL_PROCESSOR);
        Registry.register(Registry.STRUCTURE_PROCESSOR, new Identifier(Stonevaults.MODID, "nowater_processor"), NOWATER_PROCESSOR);
        Registry.register(Registry.STRUCTURE_PROCESSOR, new Identifier(Stonevaults.MODID, "lectern_processor"), LECTERN_PROCESSOR);
    }
}

package net.thegrimsey.stonevaults;

import net.minecraft.structure.processor.StructureProcessorType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.thegrimsey.stonevaults.structures.processors.NoWaterProcessor;
import net.thegrimsey.stonevaults.structures.processors.VineWallProcessor;

public class StonevaultsProcessors {
    public static StructureProcessorType<VineWallProcessor> VINEWALL_PROCESSOR = () -> VineWallProcessor.CODEC;
    public static StructureProcessorType<NoWaterProcessor> NOWATER_PROCESSOR = () -> NoWaterProcessor.CODEC;

    public static void registerProcessors()
    {
        Registry.register(Registry.STRUCTURE_PROCESSOR, new Identifier(Stonevaults.MODID, "vinewall_processor"), VINEWALL_PROCESSOR);
        Registry.register(Registry.STRUCTURE_PROCESSOR, new Identifier(Stonevaults.MODID, "nowater_processor"), NOWATER_PROCESSOR);
    }
}

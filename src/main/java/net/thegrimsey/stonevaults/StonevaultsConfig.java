package net.thegrimsey.stonevaults;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;
import net.minecraft.world.gen.chunk.StructureConfig;

@Config(name = Stonevaults.MODID)
public class StonevaultsConfig implements ConfigData {
    public static class Structure {
        public StructureConfig STUCTURECONFIG;
        public int SIZE;

        public Structure(StructureConfig structureConfig, int size) {
            this.STUCTURECONFIG = structureConfig;
            this.SIZE = size;
        }
    }

    @Comment("\nStructure Configs.\n" +
            "- Lower SPACING & SEPARATION = more structures\n" +
            "- SALT is the generation seed for structures. This is in addition to world seed. Do not change this unless you commonly see overlapping structures.\n" +
            "- Change SIZE so modify how many times the generator tries to add additional rooms. For example on mage towers this would increase the amount of floors.")

    public Structure MAGETOWER = new Structure(new StructureConfig(30, 14, 383209018), 7);
    public Structure IGLOO = new Structure(new StructureConfig(28, 10, 38419), 7);
    public Structure DUNGEON = new Structure(new StructureConfig(32, 12, 238238183), 8);
}

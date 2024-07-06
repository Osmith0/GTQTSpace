package keqing.gtqtspace;

import net.minecraftforge.common.config.Config;

@Config(modid = GTQTSpace.MODID)
public class GTQTSConfig {
    @Config.Comment("Config options for space")
    public static Space space = new Space();
    public static Multis multis = new Multis();

    public static class Multis {
        public VoidMiner voidMiner = new VoidMiner();
    }
    public static class VoidMiner {
        @Config.Comment("The maximum temperature the void miner can reach before overheating. Every second the void miner will generate 10 different ores with amount between 1 and (temperature/1000)^2 ores. default: [9000]")
        @Config.RangeInt(min = 1000)
        @Config.RequiresMcRestart
        @Config.Name("Void Miner I max temperature")
        public int maxTemp = 9000;

        @Config.Comment("The maximum temperature the void miner can reach before overheating. Every second the void miner will generate 10 different ores with amount between 1 and (temperature/1000)^2 ores. default: [9000]")
        @Config.RangeInt(min = 1000)
        @Config.RequiresMcRestart
        @Config.Name("Void Miner II max temperature")
        public int maxTempUHV = 16000;

        @Config.Comment("The maximum temperature the void miner can reach before overheating. Every second the void miner will generate 10 different ores with amount between 1 and (temperature/1000)^2 ores. default: [9000]")
        @Config.RangeInt(min = 1000)
        @Config.RequiresMcRestart
        @Config.Name("Void Miner III max temperature")
        public int maxTempUEV = 40000;

        @Config.Comment("Whether or not to add all ore variants to the Void Miner's ore table. If false only the first ore in the material's ore dictionary will be added.")
        @Config.RequiresMcRestart
        @Config.Name("Void miner ore variants")
        public boolean oreVariants = true;

        @Config.Comment("The name of the ores to blacklist for all Void Miners because there are existing recipes in the Deep Miner")
        @Config.RequiresMcRestart
        @Config.Name("Void Miner because Deep Miner Blacklist")
        public String[] oreBlackListDeepMinerConflict = new String[]{"diamond", "coal", "pyrochlore", "columbite", "bauxite", "aluminium", "rutile", "gallite", "platinum_metallic_powder", "palladium_metallic_powder", "bastnasite", "monazite", "neodymium", "nether_star", "salt", "fluorite", "lepidolite", "spodumene", "naquadric_compound", "enriched_naquadric_compound", "naquadriatic_compound", "uranium_238", "plutonium_generic", "rarest_metal_mixture", "rhodium_salt", "sodium_ruthenate"};

        @Config.Comment("The name of the ores to blacklist for all Void Miners")
        @Config.RequiresMcRestart
        @Config.Name("Universal Void Miner Blacklist")
        public String[] oreBlackListUniversal = new String[]{"naquadah", "sheldonite", "platinum", "palladium", "iridium", "osmium"};

        @Config.Comment("The name of the ores to blacklist for the MK1 Void Miner")
        @Config.RequiresMcRestart
        @Config.Name("MK1 Void Miner Blacklist")
        public String[] oreBlacklist = new String[]{"trinium", "triniite", "duranium", "tritanium", "rutherfordium", "californium", "curium", "seaborgium", "berkelium", "fermium", "einsteinium", "dubnium", "bohrium"};

        @Config.Comment("The name of the ores to blacklist for the MK2 Void Miner")
        @Config.RequiresMcRestart
        @Config.Name("MK2 Void Miner Blacklist")
        public String[] oreBlacklistUHV = new String[]{"trinium", "fermium", "bohrium", "seaborgium", "einsteinium"};

        @Config.Comment("The name of the ores to blacklist for the MK3 Void Miner")
        @Config.RequiresMcRestart
        @Config.Name("MK3 Void Miner Blacklist")
        public String[] oreBlacklistUEV = new String[]{"bohrium", "fermium"};

        @Config.Comment("The name of items you wish to add to the MK1 Void Miner. Example: \"minecraft:wool:2\"")
        @Config.RequiresMcRestart
        @Config.Name("MK1 Void Miner Whitelist")
        public String[] oreWhitelist = new String[]{""};

        @Config.Comment("The name of items you wish to add to the MK2 Void Miner")
        @Config.RequiresMcRestart
        @Config.Name("MK2 Void Miner Whitelist")
        public String[] oreWhitelistUHV = new String[]{""};

        @Config.Comment("The name of items you wish to add to the MK3 Void Miner")
        @Config.RequiresMcRestart
        @Config.Name("MK3 Void Miner Whitelist")
        public String[] oreWhitelistUEV = new String[]{""};
    }

    public static class Space{

        @Config.Comment({"List of Planet Names. These are mapped to an ID. Starting Index is 1."})
        @Config.RequiresMcRestart
        public String[] planetNames = {"Jupiter", "Saturn", "Uranus", "Neptune"};

    }
}

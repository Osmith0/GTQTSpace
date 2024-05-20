package keqing.gtqtspace.api.multiblock;

public enum SatelliteGenerators {
	EMPTY (""),
	BASIC_CHEMICAL_ENGINE("基础化学能引擎"),
	ADVANCED_CHEMICAL_ENGINE("高级化学能引擎");

	final String name;

	SatelliteGenerators(String localizedName) {
		this.name = localizedName;
	}

	public static SatelliteGenerators getGeneratorFromID(int id) {
		return SatelliteGenerators.values()[id - 1];
	}

	public String getName() {
		return this.name;
	}

	public int getID() {
		return this.ordinal() + 1;
	}
}

package keqing.gtqtspace.api.multiblock;

import keqing.gtqtspace.common.items.GTQTSMetaItems;
import net.minecraft.client.resources.I18n;

public enum SatelliteGenerators {
	EMPTY (""),
	BASIC_CHEMICAL_ENGINE(GTQTSMetaItems.COMBUSTIONENGINE.unlocalizedName),
	ADVANCED_CHEMICAL_ENGINE(GTQTSMetaItems.ADVCOMBUSTIONENGINE.unlocalizedName);

	final String translationKey;

	SatelliteGenerators(String unlocalizedName) {
		this.translationKey = unlocalizedName;
	}

	public static SatelliteGenerators getGeneratorFromID(int id) {
		return SatelliteGenerators.values()[id - 1];
	}

	public String getName() {
		return I18n.format(translationKey);
	}

	public int getID() {
		return this.ordinal() + 1;
	}
}

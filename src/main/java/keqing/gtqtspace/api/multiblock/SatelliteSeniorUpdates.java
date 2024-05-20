package keqing.gtqtspace.api.multiblock;

import keqing.gtqtspace.common.items.GTQTSMetaItems;
import net.minecraft.client.resources.I18n;

public enum SatelliteSeniorUpdates {
	EMPTY (""),
	VEIN_SENSOR(GTQTSMetaItems.SATELLITEPRIMARYFUNCTION1.unlocalizedName),
	ATMOSPHERE_SENSOR(GTQTSMetaItems.SATELLITEPRIMARYFUNCTION2.unlocalizedName),
	ORBIT_SENSOR(GTQTSMetaItems.SATELLITEPRIMARYFUNCTION3.unlocalizedName),
	DEEP_SKY_SENSOR(GTQTSMetaItems.SATELLITEPRIMARYFUNCTION4.unlocalizedName),
	COSMIC_PARTICLES_SENSOR(GTQTSMetaItems.SATELLITEPRIMARYFUNCTION5.unlocalizedName);

	final String translationKey;

	SatelliteSeniorUpdates(String unlocalizedName) {
		this.translationKey = unlocalizedName;
	}

	public static SatelliteSeniorUpdates getSeniorUpdateFromID(int id) {
		return SatelliteSeniorUpdates.values()[id - 1];
	}

	public String getName() {
		return I18n.format(translationKey);
	}

	public int getID() {
		return this.ordinal() + 1;
	}
}

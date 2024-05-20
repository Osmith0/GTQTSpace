package keqing.gtqtspace.api.multiblock;

public enum SatelliteSeniorUpdates {
	EMPTY (""),
	VEIN_SENSOR("矿脉传感器"),
	ATMOSPHERE_SENSOR("大气传感器"),
	ORBIT_SENSOR("轨道数据采集器"),
	DEEP_SKY_SENSOR("深空数据采集器"),
	COSMIC_PARTICLES_SENSOR("宇宙粒子采集器");

	final String name;

	SatelliteSeniorUpdates(String localizedName) {
		this.name = localizedName;
	}

	public static SatelliteSeniorUpdates getSeniorUpdateFromID(int id) {
		return SatelliteSeniorUpdates.values()[id - 1];
	}

	public String getName() {
		return this.name;
	}

	public int getID() {
		return this.ordinal() + 1;
	}
}

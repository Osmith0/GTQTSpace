package keqing.gtqtspace.api.multiblock;

public final class Satellite {
	private int circuit;
	private final int solarTier;
	private final String seniorTier;
	private final String generatorTier;
	private int duration;
	private boolean using;

	public Satellite(int circuit, int solarTier, String seniorTier, String generatorTier, int duration, boolean using) {
        this.circuit = circuit;
		this.solarTier = solarTier;
		this.seniorTier = seniorTier;
		this.generatorTier = generatorTier;
		this.duration = duration;
		this.using = using;
	}

	public Satellite(int circuit, int solarTier, String seniorTier, String generatorTier) {
		this(circuit, solarTier, seniorTier, generatorTier, 114514, false);
	}

	public int getCircuit() {
		return circuit;
	}

	public int getSolarTier() {
		return solarTier;
	}

	public String getSeniorTier() {
		return seniorTier;
	}

	public String getGeneratorTier() {
		return generatorTier;
	}

	public int getDuration() {
		return duration;
	}

	public boolean isUsing() {
		return using;
	}

	public void setUsing(boolean using) {
		this.using = using;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public void setCircuit(int circuit) {
		this.circuit = circuit;
	}
}

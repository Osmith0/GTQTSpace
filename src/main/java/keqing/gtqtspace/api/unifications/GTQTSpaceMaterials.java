package keqing.gtqtspace.api.unifications;

import gregtech.api.unification.material.Material;
import keqing.gtqtspace.api.unifications.materials.FirstDegreeMaterials;

public class GTQTSpaceMaterials {
	public static Material MeteoricIron;

	public static Material Desh;

	public GTQTSpaceMaterials() {
	}

	public static void register() {

		FirstDegreeMaterials.register();

	}
}

package keqing.gtqtspace.client.textures;

import codechicken.lib.texture.TextureUtils;
import gregtech.client.renderer.texture.cube.SimpleOverlayRenderer;
import net.minecraft.client.renderer.texture.TextureMap;

public class GTQTSTextures {
	public static SimpleOverlayRenderer SPACE_ELEVATOR_CASING;

	public static void init() {
		SPACE_ELEVATOR_CASING = new SimpleOverlayRenderer("multiblock/casings/support_structure_side");

	}

	public static void register(TextureMap textureMap) {

	}

	public static void preInit() {
		TextureUtils.addIconRegister(GTQTSTextures::register);
	}
}

package keqing.gtqtspace.client.textures;

import codechicken.lib.texture.TextureUtils;
import gregtech.api.gui.resources.TextureArea;
import gregtech.client.renderer.texture.cube.SimpleOverlayRenderer;
import net.minecraft.client.renderer.texture.TextureMap;

public class GTQTSTextures {

	public static final TextureArea PROGRESS_BAR_COMPONENT_AL = TextureArea.fullImage("textures/gui/progress_bar/progress_bar_component_al.png");
	public static final TextureArea PROGRESS_BAR_MINING_MODULE = TextureArea.fullImage("textures/gui/progress_bar/progress_bar_mining_module.png");
	public static final TextureArea BUTTON_ELEVATOR_EXTENSION = TextureArea.fullImage("textures/gui/widget/space_elevator_extension.png");
	public static  final TextureArea BUTTON_ELEVATOR_TELEPORT = TextureArea.fullImage("textures/gui/widget/planet_teleport.png");
	public static final TextureArea BUTTON_ENABLE_STATIC = TextureArea.fullImage("textures/gui/widget/button_power_enable_static.png");
	public static final TextureArea BUTTON_DISABLE_STATIC = TextureArea.fullImage("textures/gui/widget/button_power_disable_static.png");
	public static final TextureArea BUTTON_CYCLE = TextureArea.fullImage("textures/gui/widget/button_cycle.png");
	public static final TextureArea BUTTON_WHITE_BLACK_LIST = TextureArea.fullImage("textures/gui/widget/button_white_black_list.png");


	public static SimpleOverlayRenderer SPACE_ELEVATOR_CASING;

	public static SimpleOverlayRenderer ELEVATOR_CASING;
	public static SimpleOverlayRenderer PUMP_MODULE_OVERLAY;
	public static SimpleOverlayRenderer MINING_MODULE_OVERLAY;
	public static SimpleOverlayRenderer ASSEMBLER_MODULE_OVERLAY;

	public static void init() {
		SPACE_ELEVATOR_CASING = new SimpleOverlayRenderer("multiblock/casings/support_structure_side");
		ELEVATOR_CASING = new SimpleOverlayRenderer("multiblock/casings/elevator/elevator_base");
		PUMP_MODULE_OVERLAY = new SimpleOverlayRenderer("overlay/elevator/pump");
		MINING_MODULE_OVERLAY = new SimpleOverlayRenderer("overlay/elevator/mining");
		ASSEMBLER_MODULE_OVERLAY = new SimpleOverlayRenderer("overlay/elevator/assembler");


	}

	public static void register(TextureMap textureMap) {

	}

	public static void preInit() {
		TextureUtils.addIconRegister(GTQTSTextures::register);
	}
}

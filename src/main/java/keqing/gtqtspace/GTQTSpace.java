package keqing.gtqtspace;

import keqing.gtqtspace.api.GTQTSAPI;
import keqing.gtqtspace.api.utils.GTQTSLog;
import keqing.gtqtspace.api.world.BiomeGenBaseGC;
import keqing.gtqtspace.client.ClientProxy;
import keqing.gtqtspace.common.CommonProxy;
import keqing.gtqtspace.common.block.GTQTSMetaBlocks;
import keqing.gtqtspace.common.entity.MetaEntities;
import keqing.gtqtspace.common.items.GTQTSMetaItems;

import keqing.gtqtspace.common.metatileentities.GTQTSMetaTileEntities;
import keqing.gtqtspace.world.worldgen.GTQTSDimensionManager;
import keqing.gtqtspace.world.worldgen.GTQTSDimensionType;
import net.minecraft.block.Block;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.io.IOException;
import java.util.LinkedList;


@Mod(
		modid = "gtqtspace",
		name = "GTQTSpace",
		acceptedMinecraftVersions = "[1.12.2,1.13)",
		version = "0.0.1-beta",
		dependencies = "required-after:gregtech@[2.8.7-beta,) ;"
)
public class GTQTSpace {
	public static final String MODID = "gtqtspace";
	public static final String NAME = "GTQT Space";
	public static final String VERSION = "1.0";

	@Mod.Instance(GTQTSpace.MODID)
	public static GTQTSpace instance;

	@SidedProxy(
			clientSide = "keqing.gtqtspace.client.ClientProxy",
			serverSide = "keqing.gtqtspace.common.CommonProxy"
	)
	public static CommonProxy proxy;
	public static ClientProxy cproxy;

	public static LinkedList<BiomeGenBaseGC> biomesList      = new LinkedList<>();

	public static Block portal;
	@Mod.EventHandler
	public void init(FMLInitializationEvent event) throws IOException {
		proxy.init();
	}
	@Mod.EventHandler
	@SideOnly(Side.CLIENT)
	public void ClientpreInit(FMLPreInitializationEvent event) {
		MetaEntities.initRenderers();
	}
	@SideOnly(Side.CLIENT)
	@Mod.EventHandler
	public void Clientinit(FMLInitializationEvent event) {
		MetaEntities.initRenderers();

	}
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		GTQTSMetaTileEntities.initialization();
		GTQTSLog.init(event.getModLog());
		GTQTSMetaItems.initialization();
		GTQTSMetaBlocks.init();
		GTQTSAPI.init();
		MetaEntities.init();
		GTQTSDimensionType.init();
		GTQTSDimensionManager.init();

		proxy.preLoad();
	}

}

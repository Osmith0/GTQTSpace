package keqing.gtqtspace.common.metatileentities.multi.multiblock.standard.single;

import codechicken.lib.raytracer.CuboidRayTraceResult;
import gregtech.api.gui.ModularUI;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.TieredMetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import keqing.gtqtspace.world.SpaceStationTeleporter;
import keqing.gtqtspace.world.WorldTeleporter;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class MetaTileEntityTransport extends TieredMetaTileEntity {

    public MetaTileEntityTransport(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId,5);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity iGregTechTileEntity) {
        return new MetaTileEntityTransport(metaTileEntityId);
    }

    @Override
    protected ModularUI createUI(EntityPlayer entityPlayer) {
        return null;
    }
}

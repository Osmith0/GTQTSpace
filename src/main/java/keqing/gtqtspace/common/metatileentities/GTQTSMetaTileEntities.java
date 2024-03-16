package keqing.gtqtspace.common.metatileentities;

import keqing.gtqtspace.api.utils.GTQTSLog;
import keqing.gtqtspace.common.metatileentities.multi.multiblock.standard.MetaTileEntitySpaceElevator;

import static gregtech.common.metatileentities.MetaTileEntities.registerMetaTileEntity;
import static keqing.gtqtspace.api.GTQTSValue.gtqtspaceId;

public class GTQTSMetaTileEntities {
    public static MetaTileEntitySpaceElevator SPACE_ELEVATOR;

    public static void initialization() {
        //GTQTSLog.logger.info("Registering MetaTileEntities");
        SPACE_ELEVATOR = registerMetaTileEntity(5000, new MetaTileEntitySpaceElevator(gtqtspaceId("space_elevator")));
    }
}

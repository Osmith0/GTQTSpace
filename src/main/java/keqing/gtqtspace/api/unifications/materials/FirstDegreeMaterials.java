package keqing.gtqtspace.api.unifications.materials;

import gregtech.api.unification.material.Material;
import keqing.gtqtspace.api.unifications.GTQTSpaceMaterials;

import static gregtech.api.unification.material.info.MaterialFlags.*;
import static gregtech.api.unification.material.info.MaterialFlags.GENERATE_SMALL_GEAR;
import static gregtech.api.unification.material.info.MaterialIconSet.METALLIC;
import static gregtech.api.util.GTUtility.gregtechId;

public class FirstDegreeMaterials {
    public FirstDegreeMaterials() {
    }
    private static int startId = 18500;
    private static final int END_ID = startId + 300;
    private static int getMaterialsId() {
        if (startId < END_ID) {
            return startId++;
        }
        throw new ArrayIndexOutOfBoundsException();
    }

    public static void register() {
        //陨铁
        GTQTSpaceMaterials.MeteoricIron = new Material.Builder(getMaterialsId(), gregtechId("meteoric_iron"))
                .ingot().dust().ore()
                .color(0x8B6914).iconSet(METALLIC)
                .flags(GENERATE_PLATE, GENERATE_DENSE, GENERATE_DOUBLE_PLATE, GENERATE_ROD, GENERATE_LONG_ROD, GENERATE_RING, GENERATE_ROUND, GENERATE_BOLT_SCREW, GENERATE_FRAME, GENERATE_GEAR, GENERATE_SMALL_GEAR)
                .build();

        //陨铁
        GTQTSpaceMaterials.Desh = new Material.Builder(getMaterialsId(), gregtechId("desh"))
                .ingot().dust().ore()
                .color(0xFF7256).iconSet(METALLIC)
                .flags(GENERATE_PLATE,GENERATE_DENSE, GENERATE_DOUBLE_PLATE, GENERATE_ROD, GENERATE_LONG_ROD, GENERATE_RING, GENERATE_ROUND, GENERATE_BOLT_SCREW, GENERATE_FRAME, GENERATE_GEAR, GENERATE_SMALL_GEAR)
                .build();
    }
}

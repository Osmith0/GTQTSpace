package keqing.gtqtspace.api.unifications.ore;

import gregtech.api.unification.material.info.MaterialFlags;
import gregtech.api.unification.material.info.MaterialIconType;
import gregtech.api.unification.material.properties.PropertyKey;
import gregtech.api.unification.ore.OrePrefix;
import keqing.gtqtcore.api.unification.material.info.EPMaterialFlags;
import keqing.gtqtcore.api.unification.material.info.GTQTMaterialIconType;
import net.minecraft.client.resources.I18n;

import java.util.Collections;

import static gregtech.api.GTValues.M;
import static gregtech.api.unification.ore.OrePrefix.Conditions.hasGemProperty;
import static gregtech.api.unification.ore.OrePrefix.Flags.ENABLE_UNIFICATION;
import static keqing.gtqtcore.api.unification.material.info.EPMaterialFlags.*;

public class GTQTSOrePrefix {
    public static OrePrefix oreMoon = new OrePrefix("oreMoon", -1L, null, MaterialIconType.ore, 1L, OrePrefix.Conditions.hasOreProperty);
}

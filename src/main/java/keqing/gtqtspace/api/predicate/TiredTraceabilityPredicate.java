package keqing.gtqtspace.api.predicate;

import gregtech.api.block.VariantActiveBlock;
import gregtech.api.pattern.BlockWorldState;
import gregtech.api.pattern.PatternStringError;
import gregtech.api.pattern.TraceabilityPredicate;
import gregtech.api.util.BlockInfo;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import keqing.gtqtcore.api.pattern.TierTraceabilityPredicate;


import javax.annotation.Nullable;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.function.Predicate;
import java.util.function.Supplier;

import static keqing.gtqtspace.api.GTQTSAPI.MAP_SE_CASING;

public class TiredTraceabilityPredicate {

	public static Supplier<TierTraceabilityPredicate> CP_SE_CASING  = () -> new TierTraceabilityPredicate(MAP_SE_CASING,
			Comparator.comparing((s) -> ((keqing.gtqtcore.api.blocks.impl.WrappedIntTired) MAP_SE_CASING.get(s)).getIntTier()), "SE", null);

}
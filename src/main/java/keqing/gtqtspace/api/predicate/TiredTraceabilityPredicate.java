package keqing.gtqtspace.api.predicate;

import gregtech.api.block.VariantActiveBlock;
import gregtech.api.pattern.BlockWorldState;
import gregtech.api.pattern.PatternStringError;
import gregtech.api.pattern.TraceabilityPredicate;
import gregtech.api.util.BlockInfo;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import keqing.gtqtspace.api.blocks.impl.ITired;
import keqing.gtqtspace.api.blocks.impl.WrappedIntTired;
import keqing.gtqtspace.common.block.GTQTSMetaBlocks;
import keqing.gtqtspace.common.block.blocks.GTQTSpaceElevator;
import net.minecraft.block.state.IBlockState;

import javax.annotation.Nullable;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class TiredTraceabilityPredicate extends TraceabilityPredicate {


	static {

		MAP_SE_CASING = new Object2ObjectOpenHashMap<>();
		MAP_SEM_CASING = new Object2ObjectOpenHashMap<>();

		TiredTraceabilityPredicate.MAP_SE_CASING.put(GTQTSMetaBlocks.SPACE_ELEVATOR.getState(GTQTSpaceElevator.ElevatorCasingType.MOTOR_CASING_MK1),
				new WrappedIntTired(GTQTSpaceElevator.ElevatorCasingType.MOTOR_CASING_MK1, 1));
		TiredTraceabilityPredicate.MAP_SE_CASING.put(GTQTSMetaBlocks.SPACE_ELEVATOR.getState(GTQTSpaceElevator.ElevatorCasingType.MOTOR_CASING_MK2),
				new WrappedIntTired(GTQTSpaceElevator.ElevatorCasingType.MOTOR_CASING_MK2, 2));
		TiredTraceabilityPredicate.MAP_SE_CASING.put(GTQTSMetaBlocks.SPACE_ELEVATOR.getState(GTQTSpaceElevator.ElevatorCasingType.MOTOR_CASING_MK3),
				new WrappedIntTired(GTQTSpaceElevator.ElevatorCasingType.MOTOR_CASING_MK3, 3));
		TiredTraceabilityPredicate.MAP_SE_CASING.put(GTQTSMetaBlocks.SPACE_ELEVATOR.getState(GTQTSpaceElevator.ElevatorCasingType.MOTOR_CASING_MK4),
				new WrappedIntTired(GTQTSpaceElevator.ElevatorCasingType.MOTOR_CASING_MK4, 4));
		TiredTraceabilityPredicate.MAP_SE_CASING.put(GTQTSMetaBlocks.SPACE_ELEVATOR.getState(GTQTSpaceElevator.ElevatorCasingType.MOTOR_CASING_MK5),
				new WrappedIntTired(GTQTSpaceElevator.ElevatorCasingType.MOTOR_CASING_MK5, 5));

		TiredTraceabilityPredicate.MAP_SEM_CASING.put(GTQTSMetaBlocks.SPACE_ELEVATOR.getState(GTQTSpaceElevator.ElevatorCasingType.UPDATE_CASING_MK1),
				new WrappedIntTired(GTQTSpaceElevator.ElevatorCasingType.UPDATE_CASING_MK1, 1));
		TiredTraceabilityPredicate.MAP_SEM_CASING.put(GTQTSMetaBlocks.SPACE_ELEVATOR.getState(GTQTSpaceElevator.ElevatorCasingType.UPDATE_CASING_MK2),
				new WrappedIntTired(GTQTSpaceElevator.ElevatorCasingType.UPDATE_CASING_MK2, 2));
		TiredTraceabilityPredicate.MAP_SEM_CASING.put(GTQTSMetaBlocks.SPACE_ELEVATOR.getState(GTQTSpaceElevator.ElevatorCasingType.UPDATE_CASING_MK3),
				new WrappedIntTired(GTQTSpaceElevator.ElevatorCasingType.UPDATE_CASING_MK3, 3));

	}

	public static final Object2ObjectOpenHashMap<IBlockState, ITired> MAP_SE_CASING;
	public static final Object2ObjectOpenHashMap<IBlockState, ITired> MAP_SEM_CASING;


	public static TraceabilityPredicate CP_SE_CASING = new TiredTraceabilityPredicate(MAP_SE_CASING,
			Comparator.comparing((s) -> ((WrappedIntTired) MAP_SE_CASING.get(s)).getIntTier()), "SE", null);


	public static TraceabilityPredicate CP_SEA_CASING = new TiredTraceabilityPredicate(MAP_SEM_CASING,
			Comparator.comparing((s) -> ((WrappedIntTired) MAP_SEM_CASING.get(s)).getIntTier()), "SEA", null);
	public static TraceabilityPredicate CP_SEB_CASING = new TiredTraceabilityPredicate(MAP_SEM_CASING,
			Comparator.comparing((s) -> ((WrappedIntTired) MAP_SEM_CASING.get(s)).getIntTier()), "SEB", null);
	public static TraceabilityPredicate CP_SEC_CASING = new TiredTraceabilityPredicate(MAP_SEM_CASING,
			Comparator.comparing((s) -> ((WrappedIntTired) MAP_SEM_CASING.get(s)).getIntTier()), "SEC", null);
	public static TraceabilityPredicate CP_SED_CASING = new TiredTraceabilityPredicate(MAP_SEM_CASING,
			Comparator.comparing((s) -> ((WrappedIntTired) MAP_SEM_CASING.get(s)).getIntTier()), "SED", null);


	private final Object2ObjectOpenHashMap<IBlockState, ITired> map;
	private final String name;

	private final String errorKey;

	private Supplier<BlockInfo[]> candidatesCache;

	private final Comparator<IBlockState> comparator;


	public TiredTraceabilityPredicate(Object2ObjectOpenHashMap<IBlockState, ITired> map, String name, @Nullable String errorKey) {
		this(map, Comparator.comparing((s) -> map.get(s).getName()), name, errorKey);
	}

	public TiredTraceabilityPredicate(Object2ObjectOpenHashMap<IBlockState, ITired> map, Comparator<IBlockState> comparator, String name, @Nullable String errorKey) {
		super();
		this.map = map;
		this.name = name;
		if (errorKey == null) {
			this.errorKey = "gregtech.multiblock.pattern.error.casing";
		} else {
			this.errorKey = errorKey;
		}
		this.common.add(new SimplePredicate(predicate(), candidates()));
		this.comparator = comparator;

	}

	private Predicate<BlockWorldState> predicate() {
		return (blockWorldState) -> {
			IBlockState blockState = blockWorldState.getBlockState();
			if (map.containsKey(blockState)) {
				ITired stats = map.get(blockState);
				Object tier = stats.getTire();
				Object current = blockWorldState.getMatchContext().getOrPut(name, tier);
				if (!current.equals(tier)) {
					blockWorldState.setError(new PatternStringError(errorKey));
					return false;
				} else {
					blockWorldState.getMatchContext().getOrPut(name + "TiredStats", stats);
					if (blockState.getBlock() instanceof VariantActiveBlock) {
						(blockWorldState.getMatchContext().getOrPut("VABlock", new LinkedList<>())).add(blockWorldState.getPos());
					}
					return true;
				}
			} else {
				return false;
			}
		};
	}

	private Supplier<BlockInfo[]> candidates() {
		if (candidatesCache == null) {
			candidatesCache = () -> map.keySet().stream()
					.sorted(comparator)
					.map(type -> new BlockInfo(type, null, map.get(type).getInfo()))
					.toArray(BlockInfo[]::new);
		}
		return candidatesCache;
	}
}
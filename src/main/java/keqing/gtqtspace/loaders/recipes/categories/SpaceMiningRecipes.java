package keqing.gtqtspace.loaders.recipes.categories;

import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.ore.OrePrefix;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import keqing.gtqtspace.api.utils.GTQTSLog;
import net.minecraft.item.ItemStack;
import org.eclipse.xtext.xbase.lib.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static gregtech.api.GTValues.IV;
import static gregtech.api.GTValues.VA;
import static gregtech.api.unification.material.Materials.*;
import static keqing.gtqtcore.api.unification.GTQTMaterials.Infinity;
import static keqing.gtqtcore.api.unification.GTQTMaterials.Lignite;
import static keqing.gtqtspace.common.items.GTQTSMetaItems.*;


public class SpaceMiningRecipes {

    public static final int STICK_INPUT_STACK_SIZE = 4;
    public static final int DRILL_HEAD_INPUT_STACK_SIZE = 4;


    public static Object2ObjectMap<Integer, List<SpaceMiningRecipePartTwo>> SPACE_MINING_RECIPES = new Object2ObjectOpenHashMap<>();

    public static Object2ObjectMap<Integer, List<ItemStack>> HASH_TO_ITEMS = new Object2ObjectOpenHashMap<>();

    public static void init() {

        Material []material={Steel,Titanium,TungstenSteel,Naquadah,NaquadahAlloy,Neutronium,Infinity};

        //T1-T8 对着维度填就行，参数不要修改
        addNewRecipesForDroneAndMaterial(1, material[0],
                Arrays.asList(
                        makeSpaceMiningRecipePart(VA[IV], 1200, 1, 2, 100, 50, 200, 100, 400, Iron, 20)
                ));

        addNewRecipesForDroneAndMaterial(3, material[0],
                Arrays.asList(
                        makeSpaceMiningRecipePart(VA[IV+1], 1200, 1, 2, 200, 100, 200, 200, 400, Copper, 20)
                ));

        addNewRecipesForDroneAndMaterial(5, material[0],
                Arrays.asList(
                        makeSpaceMiningRecipePart(VA[IV+2], 1200, 2, 2, 400, 150, 200, 400, 400, Tin, 20)
                ));

        addNewRecipesForDroneAndMaterial(7, material[0],
                Arrays.asList(
                        makeSpaceMiningRecipePart(VA[IV+3], 1200, 2, 2, 600, 200, 200, 600, 400, Lead, 20)
                ));

        addNewRecipesForDroneAndMaterial(9, material[0],
                Arrays.asList(
                        makeSpaceMiningRecipePart(VA[IV+4], 1200, 2, 2, 800, 250, 200, 800, 400, Nickel,20)
                ));

        addNewRecipesForDroneAndMaterial(11, material[0],
                Arrays.asList(
                        makeSpaceMiningRecipePart(VA[IV+5], 1200, 3, 2, 1200, 300, 200, 1000, 400, Aluminium,20)
                ));

        addNewRecipesForDroneAndMaterial(13, material[0],
                Arrays.asList(
                        makeSpaceMiningRecipePart(VA[IV+6], 1200, 3, 2, 1600, 350, 200, 1200, 400, Coal,20)
                ));

        addNewRecipesForDroneAndMaterial(14, material[0],
                Arrays.asList(
                        makeSpaceMiningRecipePart(VA[IV+7], 1200, 3, 2, 2000, 400, 200, 1400, 400, Diamond,20)
                ));


        //无人机升级 材料升级 多方块不变
        for(int j=2;j<15;j++)
           addNewRecipesForDroneAndMaterial(j, material[0], upTierAllRecipes(j - 1, material[0], j));


        for(int j=2;j<=15;j++)
            for (int i = 1; i < material.length; i++)
                addNewRecipesForDroneAndMaterial(j-1, material[i], upTierAllRecipes(j-1, material[i - 1], i));

    }

    public static SpaceMiningRecipePartTwo makeSpaceMiningRecipePart(int EUt, int duration, int minModuleTier, int minDistance, int maxDistance, int minSize, int maxSize, int CWUt, int weight, Object... outputsAndWeights) {
        return new SpaceMiningRecipePartTwo(generateListOfPairs(outputsAndWeights), EUt, CWUt, weight, minSize, maxSize, duration, minDistance, maxDistance, minModuleTier);
    }

    public static void addNewRecipeForDroneAndMaterial(int droneTier, Material material, SpaceMiningRecipePartTwo recipe) {

        ItemStack droneStack = getDroneByTier(droneTier);

        if(SPACE_MINING_RECIPES.get(new SpaceMiningRecipePartOne(droneStack, material).hashCode()) == null) {
            SPACE_MINING_RECIPES.put(new SpaceMiningRecipePartOne(droneStack, material).hashCode(), new ArrayList<>());
        }

        SPACE_MINING_RECIPES.get(new SpaceMiningRecipePartOne(droneStack, material).hashCode()).add(recipe);

        if(HASH_TO_ITEMS.get(new SpaceMiningRecipePartOne(droneStack, material).hashCode()) == null)
            HASH_TO_ITEMS.put(new SpaceMiningRecipePartOne(droneStack, material).hashCode(), Arrays.asList(droneStack, OreDictUnifier.get(OrePrefix.stick, material, STICK_INPUT_STACK_SIZE), OreDictUnifier.get(OrePrefix.toolHeadDrill, material, DRILL_HEAD_INPUT_STACK_SIZE)));
    }


    public static void addNewRecipesForDroneAndMaterial(int droneTier, Material material, List<SpaceMiningRecipePartTwo> list) {

        ItemStack droneStack = getDroneByTier(droneTier);

        if(SPACE_MINING_RECIPES.get(new SpaceMiningRecipePartOne(droneStack, material).hashCode()) == null) {
            SPACE_MINING_RECIPES.put(new SpaceMiningRecipePartOne(droneStack, material).hashCode(), new ArrayList<>());
        }

        SPACE_MINING_RECIPES.get(new SpaceMiningRecipePartOne(droneStack, material).hashCode()).addAll(list);

        if(HASH_TO_ITEMS.get(new SpaceMiningRecipePartOne(droneStack, material).hashCode()) == null)
            HASH_TO_ITEMS.put(new SpaceMiningRecipePartOne(droneStack, material).hashCode(), Arrays.asList(droneStack, OreDictUnifier.get(OrePrefix.stick, material, STICK_INPUT_STACK_SIZE), OreDictUnifier.get(OrePrefix.toolHeadDrill, material, DRILL_HEAD_INPUT_STACK_SIZE)));
    }

    public static void removeRecipeForDroneAndMaterial(int droneTier, Material material, SpaceMiningRecipePartTwo recipe) {

        ItemStack droneStack = getDroneByTier(droneTier);

        if(SPACE_MINING_RECIPES.get(new SpaceMiningRecipePartOne(droneStack, material).hashCode()) == null) {
            throw new RuntimeException("No matching Drone and Material found!");
        }

        if(!SPACE_MINING_RECIPES.get(new SpaceMiningRecipePartOne(droneStack, material).hashCode()).remove(recipe)) {
            throw new RuntimeException("No matching Recipe found for Drone and Material!");
        }

        if(SPACE_MINING_RECIPES.get(new SpaceMiningRecipePartOne(droneStack, material).hashCode()).isEmpty())
            HASH_TO_ITEMS.remove(new SpaceMiningRecipePartOne(droneStack, material).hashCode());

    }

    public static void removeAllRecipesForDroneAndMaterial(int droneTier, Material material) {

        ItemStack droneStack = getDroneByTier(droneTier);

        if(SPACE_MINING_RECIPES.get(new SpaceMiningRecipePartOne(droneStack, material).hashCode()) == null) {
            throw new RuntimeException("No matching Drone and Material found!");
        }

        SPACE_MINING_RECIPES.get(new SpaceMiningRecipePartOne(droneStack, material).hashCode()).clear();

        HASH_TO_ITEMS.remove(new SpaceMiningRecipePartOne(droneStack, material).hashCode());
    }

    public static List<SpaceMiningRecipePartTwo> upTierAllRecipes(int droneTier, Material material, int tier) {

        ItemStack droneStack = getDroneByTier(droneTier);

        if(SPACE_MINING_RECIPES.get(new SpaceMiningRecipePartOne(droneStack, material).hashCode()) == null) {
            throw new RuntimeException("No matching Drone and Material found!");
        }

        List<SpaceMiningRecipePartTwo> recipes = SPACE_MINING_RECIPES.get(new SpaceMiningRecipePartOne(droneStack, material).hashCode());
        List<SpaceMiningRecipePartTwo> recipesUp = new ArrayList<>();

        for(SpaceMiningRecipePartTwo recipe : recipes) {
            recipesUp.add(recipe.copyAndUpTierRecipe(tier));
        }

        return recipesUp;
    }

    private static ItemStack getDroneByTier(int tier) {
        return switch (tier) {
            case (1) -> MINING_DRONE_LV.getStackForm();
            case (2) -> MINING_DRONE_MV.getStackForm();
            case (3) -> MINING_DRONE_HV.getStackForm();
            case (4) -> MINING_DRONE_EV.getStackForm();
            case (5) -> MINING_DRONE_IV.getStackForm();
            case (6) -> MINING_DRONE_LuV.getStackForm();
            case (7) -> MINING_DRONE_ZPM.getStackForm();
            case (8) -> MINING_DRONE_UV.getStackForm();
            case (9) -> MINING_DRONE_UHV.getStackForm();
            case (10) -> MINING_DRONE_UEV.getStackForm();
            case (11) -> MINING_DRONE_UIV.getStackForm();
            case (12) -> MINING_DRONE_UXV.getStackForm();
            case (13) -> MINING_DRONE_OpV.getStackForm();
            case (14) -> MINING_DRONE_MAX.getStackForm();
            default -> null;
        };
    }


    public static List<Pair<Material, Integer>> generateListOfPairs(Object... objects) {
        List<Pair<Material, Integer>> stuff = new ArrayList<>();
        for(int i = 0; i < objects.length; i+=2) {
            if(!(objects[i] instanceof Material mat) || !(objects[i + 1] instanceof Integer weight))
                return null;

            stuff.add(new Pair<>(mat, weight));
        }

        return stuff;
    }

    public static class SpaceMiningRecipePartOne {

        private final int drone;
        private final Material rodAndDrill;

        public SpaceMiningRecipePartOne( ItemStack drone, Material rodAndDrill) {
            this.drone = drone != null ? drone.getMetadata() : 0;
            this.rodAndDrill = rodAndDrill;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            SpaceMiningRecipePartOne that = (SpaceMiningRecipePartOne) o;
            return Objects.equals(drone, that.drone) && Objects.equals(rodAndDrill, that.rodAndDrill);
        }

        @Override
        public int hashCode() {
            return Objects.hash(drone, rodAndDrill);
        }
    }

    public static class SpaceMiningRecipePartTwo {
        private final List<Pair<Material, Integer>> outputs;
        private final int computation;
        private final int weight;
        private final int minSize;
        private final int maxSize;
        private final int minDistance;
        private final int maxDistance;
        private final long EUt;
        private final int duration;
        private final int minModuleTier;


        private SpaceMiningRecipePartTwo(List<Pair<Material, Integer>> outputs, long EUt, int computation, int weight, int minSize, int maxSize, int duration,  int minDistance, int maxDistance, int minModuleTier) {
            this.outputs = outputs;
            this.computation = computation;
            this.weight = weight;
            this.minSize = minSize;
            this.maxSize = maxSize;
            this.duration = duration;
            this.minDistance = minDistance;
            this.maxDistance = maxDistance;
            this.EUt = EUt;
            this.minModuleTier = minModuleTier;
        }

        public SpaceMiningRecipePartTwo copyAndUpTierRecipe(int tier) {
            return new SpaceMiningRecipePartTwo(this.outputs, this.EUt, this.computation, this.weight, this.minSize + tier * 4, this.maxSize + tier * 8, (int) (this.duration *(30.0-tier)/30), this.minDistance, this.maxDistance, this.minModuleTier);
        }


        public List<Pair<Material, Integer>> getOutputs() {
            return outputs;
        }

        public int getComputation() {
            return computation;
        }

        public int getWeight() {
            return weight;
        }


        public int getDuration() {
            return duration;
        }

        public int getMinSize() {
            return minSize;
        }

        public int getMaxSize() {
            return maxSize;
        }

        public int getMinDistance() {
            return minDistance;
        }

        public int getMaxDistance() {
            return maxDistance;
        }

        public long getEUt() {
            return EUt;
        }

        public int getMinModuleTier() {
            return minModuleTier;
        }
    }
}

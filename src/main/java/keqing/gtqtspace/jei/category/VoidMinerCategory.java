package keqing.gtqtspace.jei.category;


import gregtech.api.gui.GuiTextures;
import gregtech.api.util.GTStringUtils;
import gregtech.integration.jei.basic.BasicRecipeCategory;
import keqing.gtqtspace.jei.VoidMinerInfo;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;


public class VoidMinerCategory extends BasicRecipeCategory<VoidMinerInfo, VoidMinerInfo> {

    protected final IDrawable slot;
    private static final int SLOT_CENTER = 79;
    private static final int NUM_OF_SLOTS = 20;
    private static final int SLOT_WIDTH = 18;
    private static final int SLOT_HEIGHT = 18;
    protected int outputCount;
    protected int tier;

    public VoidMinerCategory(IGuiHelper guiHelper) {
        super("void_miner_ores",
                "void_miner_ores.name",
                guiHelper.createBlankDrawable(176,166),
                guiHelper);

        this.slot = guiHelper.drawableBuilder(GuiTextures.SLOT.imageLocation, 0, 0, 18, 18).setTextureSize(18, 18)
                .build();
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, VoidMinerInfo recipeWrapper,  IIngredients ingredients) {
        IGuiItemStackGroup itemStackGroup = recipeLayout.getItemStacks();
        int baseYPos = 19;


        itemStackGroup.init(0, true, SLOT_CENTER, baseYPos);
        itemStackGroup.set(ingredients);
        this.outputCount = recipeWrapper.getOutputCount();
        this.tier = recipeWrapper.getTier();

    }

    @Override
    public IRecipeWrapper getRecipeWrapper( VoidMinerInfo recipe) {
        return recipe;
    }

    @Override
    public void drawExtras( Minecraft minecraft) {
        GTStringUtils.drawCenteredStringWithCutoff(getNameByTier(this.tier), minecraft.fontRenderer, 176);

        int baseYPos = 19;

        // Selected Ore
        this.slot.draw(minecraft, SLOT_CENTER, baseYPos);

    }

    @Override
    public  String getModName() {
        return "gtqtspace";
    }

    private String getNameByTier(int tier) {
        if(tier == 0)
            return I18n.format("gtqtspace.jei.void_miner_tier_1");
        if(tier == 1)
            return I18n.format("gtqtspace.jei.void_miner_tier_2");
        if(tier == 2)
            return I18n.format("gtqtspace.jei.void_miner_tier_3");

        return null;
    }
}

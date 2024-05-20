package keqing.gtqtspace.common.items;

import gregtech.api.GregTechAPI;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class PosBindingCardItem extends Item {
	public PosBindingCardItem() {
		super();
		this.setCreativeTab(GregTechAPI.TAB_GREGTECH);
	}

	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (player.getHeldItem(hand).getItem() == GTQTSMetaItems.POS_BINDING_CARD) {
			ItemStack stack = player.getHeldItem(hand);
			if (!player.isSneaking()) {
				if (stack.hasTagCompound()) {
					NBTTagCompound compound = stack.getTagCompound();
					compound.setBoolean("hasPos", true);
					compound.setInteger("x", pos.getX());
					compound.setInteger("y", pos.getY());
					compound.setInteger("z", pos.getZ());
				} else {
					NBTTagCompound compound = new NBTTagCompound();
					compound.setBoolean("hasPos", false);
					compound.setInteger("x", pos.getX());
					compound.setInteger("y", pos.getY());
					compound.setInteger("z", pos.getZ());
					stack.setTagCompound(compound);
				}
				return EnumActionResult.SUCCESS;
			}
			return EnumActionResult.PASS;
		}
		return super.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		if (playerIn.isSneaking() && playerIn.getHeldItem(handIn).getItem() == GTQTSMetaItems.POS_BINDING_CARD) {
			ItemStack stack = playerIn.getHeldItem(handIn);
			if (stack.hasTagCompound()) {
				stack.getTagCompound().setBoolean("hasPos", false);
			} else {
				NBTTagCompound compound = new NBTTagCompound();
				compound.setBoolean("hasPos", false);
				compound.setInteger("x", Integer.MIN_VALUE);
				compound.setInteger("y", Integer.MAX_VALUE);
				compound.setInteger("z", Integer.MAX_VALUE);
				stack.setTagCompound(compound);
			}
			return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
		}
		return super.onItemRightClick(worldIn, playerIn, handIn);
	}

	@Override
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		super.addInformation(stack, worldIn, tooltip, flagIn);
		if (stack.getItem() == GTQTSMetaItems.POS_BINDING_CARD) {
			if (!stack.hasTagCompound() || !stack.getTagCompound().getBoolean("hasPos")) {
				tooltip.add(I18n.format("item.info.pos_binding.no_data"));
			} else {
				NBTTagCompound compound = stack.getTagCompound();
				StringBuilder builder = new StringBuilder();
				builder.append(I18n.format("item.info.pos_binding.stored_pos"));
				builder.append(": ");
				builder.append(compound.getInteger("x"));
				builder.append(", ");
				builder.append(compound.getInteger("y"));
				builder.append(", ");
				builder.append(compound.getInteger("z"));
				tooltip.add(builder.toString());
			}
		}
	}
}

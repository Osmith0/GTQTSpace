package keqing.gtqtspace.common.items;

import gregtech.api.items.metaitem.stats.IItemBehaviour;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class PosBindingCardBehaviors implements IItemBehaviour {
    protected PosBindingCardBehaviors() {}

    @Override
    public EnumActionResult onItemUseFirst(EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand) {
        ItemStack stack = player.getHeldItem(hand);
        if (player.isSneaking()) {
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
        } else {
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
        }
        return EnumActionResult.SUCCESS;
    }
}

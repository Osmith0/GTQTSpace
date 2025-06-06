package keqing.gtqtspace.common.entity;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EntityFirstRocket  extends Entity {
    private static final double DESCEND_SPEED = 0.45; // 下降速度
    private static final double ASCEND_SPEED = 0.45;  // 上升速度
    private boolean isRising = false; // 是否处于上升状态
    private int landingTicks = 0; // 停留计时器
    public boolean isFlyingAway;
    public EntityFirstRocket(World world,boolean flyAway) {
        super(world);
        this.setSize(1.0F, 2.0F);
        this.noClip = false;
        this.isFlyingAway=flyAway;
    }
    public EntityFirstRocket(World world) {
        super(world);
        this.setSize(1.0F, 2.0F);
        this.noClip = false;
        this.isFlyingAway=true;
    }
    Entity passenger;

    @Override
    public void onUpdate() {
        super.onUpdate();


        if (!this.isRising) {
            // 缓慢下降
            this.motionY = -DESCEND_SPEED;
            this.move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);

            if (!this.getPassengers().isEmpty()) {
                if (passenger == null) passenger = this.getPassengers().get(0);
            }

            // 检查下方 3x3 区域是否有非空气方块
            boolean isOnGround = false;
            for (int dx = -1; dx <= 1; dx++) {
                for (int dz = -1; dz <= 1; dz++) {
                    BlockPos posBelow = new BlockPos(this.posX + dx, this.posY - 1, this.posZ + dz);
                    IBlockState stateBelow = this.world.getBlockState(posBelow);

                    if (!stateBelow.getBlock().isAir(stateBelow, this.world, posBelow)) {
                        isOnGround = true;
                        break;
                    }
                }
                if (isOnGround) break;
            }

            if (isOnGround) {
                // 解除骑乘
                if (!this.getPassengers().isEmpty()) {
                    passenger.dismountRidingEntity();
                }

                // 开始停留计时
                if (this.landingTicks == 0) {
                    this.landingTicks = 60; // 3 秒（60 ticks）
                } else {
                    this.landingTicks--;
                    // 停留时间结束，切换到上升状态
                    if (this.landingTicks <= 0) {

                        if(isFlyingAway)this.isRising = true;
                        else
                        {
                            ((EntityPlayerMP)passenger).addItemStackToInventory(EntityAdvancedRocket.getNewRocketItemStack());
                            this.setDead(); // 销毁实体
                        }
                    }
                }
            }
        } else {
            this.motionY = ASCEND_SPEED;
            this.move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
            if (this.posY >= 255) {
                this.setDead(); // 销毁实体
            }
        }

        // 生成火焰特效
        if (this.world.isRemote && this.ticksExisted % 2 == 0) {
            for (int i = 0; i < 10; i++) {
                double offsetX = (this.rand.nextDouble() - 0.5) * 2.0;
                double offsetZ = (this.rand.nextDouble() - 0.5) * 2.0;

                // 火焰粒子方向
                double motionY = this.isRising ? -0.5 : 0.5;
                double motionX = (this.rand.nextDouble() - 0.5) * 0.1;
                double motionZ = (this.rand.nextDouble() - 0.5) * 0.1;

                // 生成火焰粒子
                this.world.spawnParticle(EnumParticleTypes.FLAME,
                        this.posX + offsetX,
                        this.posY - 1,
                        this.posZ + offsetZ,
                        motionX, motionY, motionZ);

                // 生成烟雾粒子
                this.world.spawnParticle(EnumParticleTypes.SMOKE_LARGE,
                        this.posX + offsetX,
                        this.posY - 1,
                        this.posZ + offsetZ,
                        motionX * 0.5, motionY * 0.5, motionZ * 0.5);
            }
        }
    }

    @Override
    protected void entityInit() {
    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound compound) {
        this.isRising = compound.getBoolean("isRising");
        this.landingTicks = compound.getInteger("landingTicks");
    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound compound) {
        compound.setBoolean("isRising", this.isRising);
        compound.setInteger("landingTicks", this.landingTicks);
    }

    @Override
    public boolean shouldRiderSit() {
        return true;
    }


}
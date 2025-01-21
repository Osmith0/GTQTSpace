package keqing.gtqtspace.common.entity;

import keqing.gtqtspace.api.utils.GTQTSLog;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;


public class EntitySpaceShip extends EntityLiving {

    private boolean isMovingUp = false;
    private boolean isMovingDown = false;
    private boolean isMovingLeft = false;
    private boolean isMovingRight = false;
    private boolean isMovingForward = false;

    public EntitySpaceShip(World worldIn) {
        super(worldIn);
        this.setNoGravity(true);
        this.setEntityInvulnerable(true);
    }

    public EntitySpaceShip(World worldIn, double x, double y, double z) {
        super(worldIn);
        this.setLocationAndAngles(x, y, z, 0.F, 0.F);
        this.setNoGravity(true);
        this.setEntityInvulnerable(true);
    }

    @Override
    public boolean hasNoGravity() {
        return true;
    }

    @Override
    public boolean canBePushed() {
        return false;
    }

    @Override
    public boolean canBeCollidedWith() {
        return true;
    }

    @Override
    public boolean canPassengerSteer() {
        return !this.getPassengers().isEmpty();
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        if (!this.world.isRemote && !this.getPassengers().isEmpty()) {
            EntityPlayer player = (EntityPlayer) this.getPassengers().get(0);
            if (player instanceof EntityPlayer) {
                this.handlePlayerInput(player);
            }
        }
    }

    @Override
    protected boolean processInteract(EntityPlayer player, EnumHand hand) {
        // 调用父类方法以确保默认行为
        boolean result = super.processInteract(player, hand);

        // 检查是否是右键点击并且玩家没有物品在手中
        if (!this.world.isRemote && hand == EnumHand.MAIN_HAND && player.getHeldItem(hand).isEmpty()) {
            // 尝试让玩家骑乘飞船
            player.startRiding(this);
            return true;
        }
        return result;
    }

    private void handlePlayerInput(EntityPlayer player) {
        if(Minecraft.getMinecraft().player==null)return;
        this.isMovingUp = Minecraft.getMinecraft().player.movementInput.moveForward > 0;
        this.isMovingDown = Minecraft.getMinecraft().player.movementInput.moveForward < 0;
        this.isMovingLeft = Minecraft.getMinecraft().player.movementInput.moveStrafe > 0;
        this.isMovingRight = Minecraft.getMinecraft().player.movementInput.moveStrafe < 0;
        this.isMovingForward = Minecraft.getMinecraft().player.movementInput.jump;

        float pitchChange = 0.0F;
        float yawChange = 0.0F;
        float forward = 0.0F;

        if (this.isMovingUp) {
            pitchChange -= 1.0F;
        }
        if (this.isMovingDown) {
            pitchChange += 1.0F;
        }
        if (this.isMovingLeft) {
            yawChange -= 1.0F;
        }
        if (this.isMovingRight) {
            yawChange += 1.0F;
        }
        if (this.isMovingForward) {
            forward += 1.0F;
        }

        this.updateOrientation(pitchChange, yawChange);
        this.moveEntityWithHeading(forward, player);
    }

    private void updateOrientation(float pitchChange, float yawChange) {
        float pitchSpeed = 5F; // 偏转速度
        float yawSpeed = 5F; // 偏转速度

        this.rotationPitch += pitchChange * pitchSpeed;
        this.rotationYaw += yawChange * yawSpeed;

        // 限制俯仰角范围
        this.rotationPitch = MathHelper.clamp(this.rotationPitch, -90.0F, 90.0F);
        this.rotationYaw = MathHelper.wrapDegrees(this.rotationYaw);
    }
    public double getSpeed() {
        return Math.sqrt(motionX * motionX + motionY * motionY + motionZ * motionZ);
    }
    private void moveEntityWithHeading(float forward, EntityPlayer player) {
        if (this.isServerWorld() && forward != 0) {
            double speed = 0.1D; // 飞船的速度
            Vec3d direction = new Vec3d(0, 0, forward);
            direction = direction.rotatePitch(-this.rotationPitch * (float) Math.PI / 180.0F);
            direction = direction.rotateYaw(-this.rotationYaw * (float) Math.PI / 180.0F);
            this.addVelocity(direction.x * speed, direction.y * speed, direction.z * speed);
        }
    }
    public float getHorizontalYaw() {
        return (float) Math.toDegrees(Math.atan2(motionX, motionZ));
    }

    public float getVerticalPitch() {
        return (float) Math.toDegrees(Math.atan2(motionY, Math.sqrt(motionX * motionX + motionZ * motionZ)));
    }
}

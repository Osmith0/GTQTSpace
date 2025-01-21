package keqing.gtqtspace.common.entity;


import keqing.gtqtspace.common.entity.EntitySpaceShip;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import static gregtech.client.shader.Shaders.mc;

@Mod.EventBusSubscriber(Side.CLIENT)
public class SpaceShipHUD {

    @SubscribeEvent
    public static void onRenderGameOverlay(RenderGameOverlayEvent.Post event) {
        if (event.getType() != RenderGameOverlayEvent.ElementType.ALL) {
            return;
        }
        Minecraft mc = Minecraft.getMinecraft();
        EntityPlayer player = mc.player;

        if (player != null && player.getRidingEntity() != null) {
            Entity ridingEntity = player.getRidingEntity();

            if (ridingEntity instanceof EntitySpaceShip spaceShip) {
                renderHUD(spaceShip, event.getResolution());
            }
        }
    }

    private static void renderHUD(EntitySpaceShip spaceShip, ScaledResolution resolution) {
        int width = resolution.getScaledWidth();
        int height = resolution.getScaledHeight();

        // 设置HUD的位置
        int x = 10;
        int y = 10;

        // 设置字体颜色
        int textColor = 0xFFFFFF;

        // 绘制HUD内容
        String speedText = "Speed: " + String.format("%.2f", spaceShip.getSpeed());
        String orientationText = "Pitch: " + String.format("%.2f",spaceShip.getVerticalPitch())
                + " Yaw: " + String.format("%.2f", spaceShip.getHorizontalYaw());

        mc.fontRenderer.drawString(speedText, x, y, textColor);
        mc.fontRenderer.drawString(orientationText, x, y + 10, textColor);


        // 绘制飞行器图像
        int textColor1 = 0x00FF00;
        int h=0;
        y+=60;
        String line1 = "-                               -";
        for(int i=10;i>spaceShip.getVerticalPitch()/10;i--)
        {
            mc.fontRenderer.drawString(line1, x+140, y+h*5, textColor1);
            h+=1;
        }

        String line2 = "-           =---*---=           -";
        mc.fontRenderer.drawString(line2, x+140, y +h*5, textColor1);
        h+=1;

        for(int i=-10;i<spaceShip.getHorizontalYaw()/10;i++)
        {
            mc.fontRenderer.drawString(line1, x+140, y +h*5, textColor1);
            h+=1;
        }


    }
}

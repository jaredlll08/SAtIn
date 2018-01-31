package com.blamejared.satin.events;

import com.blamejared.satin.Satin;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.math.*;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;

public class ClientEventHandler {
    
    @SubscribeEvent
    public void onRenderGameOverlay(RenderGameOverlayEvent.Post event) {
        
        Minecraft mc = Minecraft.getMinecraft();
        GameSettings gamesettings = mc.gameSettings;
        if(Satin.enabled) {
            if(gamesettings.attackIndicator != 0) {
                Minecraft.getMinecraft().gameSettings.attackIndicator = 0;
            }
            if(event.getType() == RenderGameOverlayEvent.ElementType.CROSSHAIRS) {
                
                if(gamesettings.thirdPersonView == 0) {
                    if(mc.playerController.isSpectator() && mc.pointedEntity == null) {
                        RayTraceResult raytraceresult = mc.objectMouseOver;
                        
                        if(raytraceresult == null || raytraceresult.typeOfHit != RayTraceResult.Type.BLOCK) {
                            return;
                        }
                        
                        BlockPos blockpos = raytraceresult.getBlockPos();
                        
                        net.minecraft.block.state.IBlockState state = mc.world.getBlockState(blockpos);
                        if(!state.getBlock().hasTileEntity(state) || !(mc.world.getTileEntity(blockpos) instanceof IInventory)) {
                            return;
                        }
                    }
                    
                }
                GL11.glPushMatrix();
                GL11.glTranslated(Satin.x, Satin.y, 0);
                GL11.glScaled(Satin.scale, Satin.scale, 1);
                if(Satin.type == 2) {
                    renderTypeTwo();
                } else if(Satin.type == 1) {
                    renderTypeOne();
                }
                GL11.glPopMatrix();
            }
        }
    }
    
    public void renderTypeOne() {
        Minecraft mc = Minecraft.getMinecraft();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.ONE_MINUS_DST_COLOR, GlStateManager.DestFactor.ONE_MINUS_SRC_COLOR, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.enableAlpha();
        
        float f = mc.player.getCooledAttackStrength(0.0F);
        boolean flag = false;
        
        if(mc.pointedEntity != null && mc.pointedEntity instanceof EntityLivingBase && f >= 1.0F) {
            flag = mc.player.getCooldownPeriod() > 5.0F;
            flag = flag & mc.pointedEntity.isEntityAlive();
        }
        
        int y = 0;
        int x = 0;
        
        if(flag) {
            this.drawTexturedModalRect(x, y, 68, 94, 16, 16);
        } else if(f < 1.0F) {
            int k = (int) (f * 17.0F);
            this.drawTexturedModalRect(x, y, 36, 94, 16, 4);
            this.drawTexturedModalRect(x, y, 52, 94, k, 4);
        }
    }
    
    public void renderTypeTwo() {
        Minecraft mc = Minecraft.getMinecraft();
        GlStateManager.enableRescaleNormal();
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        RenderHelper.enableGUIStandardItemLighting();
        GlStateManager.color(1, 1, 1, 1);
        
        float f1 = mc.player.getCooledAttackStrength(0.0F);
        
        if(f1 < 1.0F) {
            int y = 0;
            int x = 0;
            
            mc.getTextureManager().bindTexture(Gui.ICONS);
            int k1 = (int) (f1 * 19.0F);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            this.drawTexturedModalRect(x, y, 0, 94, 18, 18);
            this.drawTexturedModalRect(x, y + 18 - k1, 18, 112 - k1, 18, k1);
        }
        
        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableRescaleNormal();
        GlStateManager.disableBlend();
    }
    
    public void drawTexturedModalRect(int x, int y, int textureX, int textureY, int width, int height) {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
        bufferbuilder.pos((double) (x), (double) (y + height), (double) 500).tex((double) ((float) (textureX) * 0.00390625F), (double) ((float) (textureY + height) * 0.00390625F)).endVertex();
        bufferbuilder.pos((double) (x + width), (double) (y + height), (double) 500).tex((double) ((float) (textureX + width) * 0.00390625F), (double) ((float) (textureY + height) * 0.00390625F)).endVertex();
        bufferbuilder.pos((double) (x + width), (double) (y), (double) 500).tex((double) ((float) (textureX + width) * 0.00390625F), (double) ((float) (textureY) * 0.00390625F)).endVertex();
        bufferbuilder.pos((double) (x), (double) (y), (double) 500).tex((double) ((float) (textureX) * 0.00390625F), (double) ((float) (textureY) * 0.00390625F)).endVertex();
        tessellator.draw();
    }
}

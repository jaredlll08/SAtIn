package com.blamejared.satin.gui;

import com.blamejared.satin.Satin;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.*;
import org.lwjgl.input.*;
import org.lwjgl.opengl.GL11;

import java.io.IOException;

public class GuiSatin extends GuiScreen {
    
    
    public GuiSatin() {
    }
    
    public float progress = 0;
    
    @Override
    public void initGui() {
        super.initGui();
        Satin.writeATEnabled(true);
    }
    
    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.drawHoveringText("SAtIn", this.width / 2 - (fontRenderer.getStringWidth("SAtIn") / 2) + 1, 17);
        this.drawHoveringText("Smart Attack Indicators", this.width / 2 - (fontRenderer.getStringWidth("Smart Attack Indicators") / 2) + 1, 32);
        this.drawHoveringText("Click to change the Attack Indicator location", this.width / 2 - (fontRenderer.getStringWidth("Click to change the Attack indicator location") / 2) + 1, 47);
        this.drawHoveringText("Use the scroll wheel to change Attack Indicator type", this.width / 2 - (fontRenderer.getStringWidth("Use the scroll wheel to change Attack Indicator type") / 2) + 1, 47 + 15);
        this.drawHoveringText("Hold Left Shift and use the scroll wheel to scale the Attack Indicator", this.width / 2 - (fontRenderer.getStringWidth("Hold Left Shift and use the scroll wheel to scale the Attack Indicator") / 2) + 1, 47 + 15);
        
        GL11.glPushMatrix();
        GL11.glTranslated(Satin.x, Satin.y, 0);
        GL11.glScaled(Satin.scale, Satin.scale, 1);
        int size = 18;
        drawVerticalLine(-1, -1, size, 0xFFFF0000);
        drawVerticalLine(size, -1, size, 0xFFFF0000);
        drawHorizontalLine(-1, size, -1, 0xFFFF0000);
        drawHorizontalLine(-1, size, size, 0xFFFF0000);
        
        if(Satin.type == 1) {
            renderTypeOne();
        } else if(Satin.type == 2) {
            renderTypeTwo();
        }
        GL11.glPopMatrix();
    }
    
    
    public void renderTypeOne() {
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.ONE_MINUS_DST_COLOR, GlStateManager.DestFactor.ONE_MINUS_SRC_COLOR, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.enableAlpha();
        GL11.glDisable(GL11.GL_LIGHTING);
        GlStateManager.color(1, 1, 1, 1);
        
        
        int y = 0;
        int x = 0;
        
        if(progress >= 1) {
            progress = 0;
        }
        
        mc.getTextureManager().bindTexture(Gui.ICONS);
        int k = (int) ((progress += 0.02) * 17.0F);
        if(progress >= 1) {
            progress = 0;
        }
        this.drawTexturedModalRect(x, y, 36, 94, 16, 4);
        this.drawTexturedModalRect(x, y, 52, 94, k, 4);
        GL11.glEnable(GL11.GL_LIGHTING);
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
    }
    
    public void renderTypeTwo() {
        GlStateManager.enableRescaleNormal();
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        RenderHelper.enableGUIStandardItemLighting();
        GL11.glDisable(GL11.GL_LIGHTING);
        GlStateManager.color(1, 1, 1, 1);
        
        int y = 0;
        int x = 0;
        
        this.mc.getTextureManager().bindTexture(Gui.ICONS);
        int k1 = (int) ((progress += 0.02) * 19.0F);
        if(progress >= 0.9) {
            progress = 0;
        }
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.drawTexturedModalRect(x, y, 0, 94, 18, 18);
        this.drawTexturedModalRect(x, y + 18 - k1, 18, 112 - k1, 18, k1);
        GL11.glEnable(GL11.GL_LIGHTING);
        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableRescaleNormal();
        GlStateManager.disableBlend();
    }
    
    
    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        Satin.writeATPos(mouseX, mouseY);
    }
    
    @Override
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
        
        if(Mouse.getEventDWheel() > 0) {
            if(!Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
                int type = Satin.type;
                type++;
                if(type > 2) {
                    type = 1;
                }
                Satin.writeATType(type);
            } else {
                int scale = Satin.scale;
                scale++;
                Satin.writeATScake(scale);
            }
        }
        if(Mouse.getEventDWheel() < 0) {
            if(!Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
                int type = Satin.type;
                type--;
                if(type < 1) {
                    type = 2;
                }
                Satin.writeATType(type);
            } else {
                int scale = Satin.scale;
                scale--;
                if(scale <= 1) {
                    scale = 1;
                }
                Satin.writeATScake(scale);
            }
        }
    }
    
    @Override
    public void handleInput() throws IOException {
        super.handleInput();
        
    }
    
    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        super.actionPerformed(button);
    }
    
    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        super.keyTyped(typedChar, keyCode);
    }
}

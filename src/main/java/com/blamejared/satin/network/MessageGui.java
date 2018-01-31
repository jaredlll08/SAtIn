package com.blamejared.satin.network;

import com.blamejared.satin.gui.GuiSatin;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.simpleimpl.*;
import net.minecraftforge.fml.relauncher.*;

public class MessageGui implements IMessage, IMessageHandler<MessageGui, IMessage> {
    
    public MessageGui() {
    }
    
    
    @Override
    public void fromBytes(ByteBuf buf) {
    }
    
    @Override
    public void toBytes(ByteBuf buf) {
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public IMessage onMessage(MessageGui message, MessageContext ctx) {
        Minecraft.getMinecraft().addScheduledTask(() -> Minecraft.getMinecraft().displayGuiScreen(new GuiSatin()));
        return null;
    }
    
}

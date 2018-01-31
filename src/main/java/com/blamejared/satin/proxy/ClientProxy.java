package com.blamejared.satin.proxy;

import com.blamejared.satin.events.ClientEventHandler;
import net.minecraftforge.common.MinecraftForge;

public class ClientProxy extends CommonProxy {
    
    public void registerEvents() {
        MinecraftForge.EVENT_BUS.register(new ClientEventHandler());
    }
}

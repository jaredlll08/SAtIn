package com.blamejared.satin;

import com.blamejared.satin.commands.CommandSatin;
import com.blamejared.satin.network.PacketHandler;
import com.blamejared.satin.proxy.CommonProxy;
import com.blamejared.satin.reference.Reference;
import net.minecraftforge.fml.common.*;
import net.minecraftforge.fml.common.event.*;
import org.apache.commons.lang3.tuple.*;

import java.io.*;
import java.util.*;

@Mod(modid = Reference.MODID, name = Reference.NAME, version = Reference.VERSION, clientSideOnly = true)
public class Satin {
    
    @Mod.Instance(Reference.MODID)
    public static Satin INSTANCE;
    
    @SidedProxy(clientSide = "com.blamejared.satin.proxy.ClientProxy", serverSide = "com.blamejared.satin.proxy.CommonProxy")
    public static CommonProxy PROXY;
    
    public static File configFile;
    
    public static int x, y, type, scale;
    
    public static boolean enabled;
    
    @Mod.EventHandler
    public void onFMLPreInitialization(FMLPreInitializationEvent event) {
        PacketHandler.preInit();
        PROXY.registerEvents();
        configFile = new File(event.getModConfigurationDirectory(), "SAtIn.txt");
        if(!configFile.exists()) {
            try {
                configFile.createNewFile();
                writeATPos(-1, -1);
                writeATType(1);
                writeATEnabled(false);
                writeATScake(1);
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
        Pair<Integer, Integer> pos = getATPos();
        x = pos.getKey();
        y = pos.getValue();
        type = getATType();
        enabled = getATEnabled();
        scale = getATScale();
    }
    
    @Mod.EventHandler
    public void onFMLServerStarting(FMLServerStartingEvent event) {
        event.registerServerCommand(new CommandSatin());
    }
    
    public static Pair<Integer, Integer> getATPos() {
        int x = -1;
        int y = -1;
        try {
            Scanner scanner = new Scanner(configFile);
            while(scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if(line.startsWith("x:")) {
                    x = Integer.parseInt(line.split("x:")[1]);
                }
                if(line.startsWith("y:")) {
                    y = Integer.parseInt(line.split("y:")[1]);
                }
            }
            scanner.close();
        } catch(FileNotFoundException e) {
            e.printStackTrace();
        }
        return new ImmutablePair<>(x, y);
    }
    
    
    public static int getATType() {
        int type = -1;
        try {
            Scanner scanner = new Scanner(configFile);
            while(scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if(line.startsWith("type:")) {
                    type = Integer.parseInt(line.split("type:")[1]);
                }
            }
            scanner.close();
        } catch(FileNotFoundException e) {
            e.printStackTrace();
        }
        return type;
    }
    
    public static int getATScale() {
        int scale = 1;
        try {
            Scanner scanner = new Scanner(configFile);
            while(scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if(line.startsWith("scale:")) {
                    scale = Integer.parseInt(line.split("scale:")[1]);
                }
            }
            scanner.close();
        } catch(FileNotFoundException e) {
            e.printStackTrace();
        }
        return scale;
    }
    
    public static boolean getATEnabled() {
        boolean enabled = false;
        try {
            Scanner scanner = new Scanner(configFile);
            while(scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if(line.startsWith("enabled:")) {
                    enabled = Boolean.parseBoolean(line.split("enabled:")[1]);
                }
            }
            scanner.close();
        } catch(FileNotFoundException e) {
            e.printStackTrace();
        }
        return enabled;
    }
    
    
    public static void overwriteAT(String key, String value) {
        try {
            Scanner scanner = new Scanner(configFile);
            List<String> lines = new LinkedList<>();
            while(scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if(!line.trim().isEmpty())
                    lines.add(line);
                
            }
            scanner.close();
            PrintWriter writer = new PrintWriter(new FileWriter(configFile));
            boolean writen = false;
            for(String line : lines) {
                if(line.trim().isEmpty()) {
                    continue;
                }
                if(line.split(":")[0].equalsIgnoreCase(key)) {
                    writer.println(key + ":" + value);
                    writen = true;
                } else {
                    writer.println(line);
                }
            }
            if(!writen) {
                writer.println(key + ":" + value);
            }
            writer.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void writeATPos(int x, int y) {
        overwriteAT("x", x + "");
        overwriteAT("y", y + "");
        Satin.x = x;
        Satin.y = y;
    }
    
    public static void writeATType(int type) {
        overwriteAT("type", type + "");
        Satin.type = type;
    }
    
    public static void writeATScake(int scale) {
        overwriteAT("scale", scale + "");
        Satin.scale = scale;
    }
    
    public static void writeATEnabled(boolean enabled) {
        overwriteAT("enabled", enabled + "");
        Satin.enabled = enabled;
    }
}

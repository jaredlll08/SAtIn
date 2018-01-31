package com.blamejared.satin.commands;

import com.blamejared.satin.Satin;
import com.blamejared.satin.network.*;
import net.minecraft.command.*;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.server.command.TextComponentHelper;

import javax.annotation.Nullable;
import java.util.*;

public class CommandSatin extends CommandBase {
    
    /**
     * Gets the name of the command
     */
    @Override
    public String getName() {
        return "satin";
    }
    
    /**
     * Gets the usage string for the command.
     *
     * @param sender
     */
    @Override
    public String getUsage(ICommandSender sender) {
        return "satin <reset>";
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }
    
    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        return Collections.singletonList("reset");
    }
    
    /**
     * Callback for when the command is executed
     *
     * @param server
     * @param sender
     * @param args
     */
    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if(!(sender.getCommandSenderEntity() instanceof EntityPlayerMP)) {
            return;
        }
        if(args.length > 0) {
            if(args[0].equalsIgnoreCase("reset")) {
                Satin.writeATEnabled(false);
            } else {
                sender.sendMessage(TextComponentHelper.createComponentTranslation(sender, TextFormatting.RED + "Invalid Usage: " + TextFormatting.WHITE  + getUsage(sender)));
            }
        } else
            PacketHandler.INSTANCE.sendTo(new MessageGui(), (EntityPlayerMP) sender.getCommandSenderEntity());
    }
}

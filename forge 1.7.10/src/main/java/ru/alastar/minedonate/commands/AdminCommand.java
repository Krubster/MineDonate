package ru.alastar.minedonate.commands;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import ru.alastar.minedonate.MineDonate;

/**
 * Created by Alastar on 02.08.2017.
 */
public class AdminCommand extends CommandBase {
    @Override
    public String getCommandName() {
        return "mdadmin";
    }

    @Override
    public String getCommandUsage(ICommandSender p_71518_1_) {
        return "mdadmin <password>";
    }

    @Override
    public void processCommand(ICommandSender p_71515_1_, String[] args) {
        if (p_71515_1_ instanceof EntityPlayerMP && args.length == 1) {
            EntityPlayerMP player = (EntityPlayerMP) p_71515_1_;
            if (MineDonate.checkAdminSessionLogin(args[0], player)) {
                MineDonate.beginAdminSession(player);
                player.addChatMessage(new ChatComponentText(EnumChatFormatting.GOLD + "Admin session begin!"));
            }
            else{
                player.addChatMessage(new ChatComponentText(EnumChatFormatting.RED +"Failed to login!"));
            }
        }
    }
}

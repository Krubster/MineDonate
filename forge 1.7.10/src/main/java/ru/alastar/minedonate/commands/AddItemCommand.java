package ru.alastar.minedonate.commands;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import ru.alastar.minedonate.MineDonate;
import ru.alastar.minedonate.rtnl.AdminSessionManager;

/**
 * Created by Alastar on 02.08.2017.
 */
public class AddItemCommand  extends CommandBase {
    @Override
    public String getCommandName() {
        return "mdadditem";
    }

    @Override
    public String getCommandUsage(ICommandSender p_71518_1_) {
        return "mdadditem <name> <cost> <limit>";
    }

    @Override
    public void processCommand(ICommandSender p_71515_1_, String[] args) {
        if (p_71515_1_ instanceof EntityPlayerMP && args.length == 3) {
            EntityPlayerMP player = (EntityPlayerMP) p_71515_1_;
            if (AdminSessionManager.checkAdminSession(player))
            {
                MineDonate.AddItemToStock(player.getHeldItem(), args[0], args[1], args[2]);
                player.addChatMessage(new ChatComponentText(EnumChatFormatting.GOLD + "Item added! Check it out in shop and edit"));

            }
        }
    }
}

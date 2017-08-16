package ru.alastar.minedonate.commands;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

import ru.alastar.minedonate.rtnl.Account;
import ru.alastar.minedonate.rtnl.AdminSessionManager;

/**
 * Created by Alastar on 03.08.2017.
 */
public class AddEntityCommand extends CommandBase {
    @Override
    public String getCommandName() {
        return "mdaddentity";
    }

    @Override
    public String getCommandUsage(ICommandSender p_71518_1_) {
        return "mdaddentity <name> <cost> <limit>";
    }

    @Override
    public void processCommand(ICommandSender p_71515_1_, String[] args) {
        if (p_71515_1_ instanceof EntityPlayerMP && args.length == 3) {
            EntityPlayerMP player = (EntityPlayerMP) p_71515_1_;
            if (AdminSessionManager.checkAdminSession(player)) {
                final Account.AdminSession session = AdminSessionManager.getAdminSession(player);
                session.pending = true;
                session.params = args;
                player.addChatMessage(new ChatComponentText(EnumChatFormatting.YELLOW + "Right click on entity to add it"));

            }
        }
    }
}

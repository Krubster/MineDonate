package ru.alastar.minedonate.commands;

import com.mojang.authlib.GameProfile;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import ru.alastar.minedonate.MineDonate;
import ru.alastar.minedonate.Utils;
import ru.alastar.minedonate.mproc.AbstractMoneyProcessor;

import java.util.UUID;

/**
 * Created by Alastar on 23.08.2017.
 */
public class AddMoneyCommand extends CommandBase {
    @Override
    public String getCommandName() {
        return "mdmoney";
    }

    @Override
    public String getCommandUsage(ICommandSender iCommandSender) {
        return "mdmoney <player> <moneyType> <amount>";
    }

    @Override
    public void processCommand(ICommandSender iCommandSender, String[] strings) {
        if (iCommandSender instanceof MinecraftServer) {
            String player_name = strings[0];
            String moneyType = strings[1];
            int amount;
            try {
                amount = Integer.valueOf(strings[2]);
                GameProfile profile = MinecraftServer.getServer().func_152358_ax().func_152655_a(player_name);
                if (profile == null) {
                    System.out.println("No such player!");
                    return;
                }
                UUID uuid = profile.getId();
                AbstractMoneyProcessor processor = MineDonate.getMoneyProcessor(moneyType);
                if (processor == null) {
                    System.out.println("Wrong money type format!");
                    return;
                }
                processor.setMoney(uuid, processor.getMoneyFor(uuid) + amount);

                System.out.println("Money added!");
            } catch (Exception e) {
                System.out.println("Wrong money amount format!");
            }
        } else if (iCommandSender instanceof EntityPlayerMP) { //Эта проверка вообще нужна?
            EntityPlayerMP player = (EntityPlayerMP) iCommandSender;
            if (MineDonate.getAccount(player).canAddMoney()) {
                String player_name = strings[0];
                String moneyType = strings[1];
                int amount;
                try {
                    amount = Integer.valueOf(strings[2]);
                    GameProfile profile = MinecraftServer.getServer().func_152358_ax().func_152655_a(player_name);
                    if (profile == null) {
                        Utils.sendModMessage(player, "No such player!");
                        return;
                    }
                    UUID uuid = profile.getId();
                    AbstractMoneyProcessor processor = MineDonate.getMoneyProcessor(moneyType);
                    if (processor == null) {
                        Utils.sendModMessage(player, "Unknown money type!");
                        return;
                    }
                    processor.setMoney(uuid, processor.getMoneyFor(uuid) + amount);

                    Utils.sendModMessage(player, "Money added!");
                } catch (Exception e) {
                    Utils.sendModMessage(player, "Wrong money amount specified!");
                }
            } else {
                Utils.sendModMessage(player, "No permissions!");
                return;
            }
        }
    }
}

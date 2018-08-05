package ru.alastar.minedonate.commands;

import com.google.common.collect.Lists;
import com.mojang.authlib.GameProfile;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.FMLCommonHandler;
import ru.alastar.minedonate.MineDonate;
import ru.alastar.minedonate.Utils;
import ru.alastar.minedonate.mproc.AbstractMoneyProcessor;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Alastar on 23.08.2017.
 */
public class AddMoneyCommand implements ICommand {
    @Override
    public String getName() {
        return "/mdmoney";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "/mdmoney <player> <moneyType> <amount>";
    }

    @Override
    public List<String> getAliases() {
        List<String> aliases = Lists.<String>newArrayList();
        aliases.add("/mdmoney");
        aliases.add("/mdm");
        return  aliases;
    }


    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (sender instanceof MinecraftServer) {
            String player_name = args[0];
            String moneyType = args[1];
            int amount;
            try {
                amount = Integer.valueOf(args[2]);
                GameProfile profile = FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerProfileCache().getGameProfileForUsername(player_name);
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
        } else if (sender instanceof EntityPlayerMP) { //Эта проверка вообще нужна?
            EntityPlayerMP player = (EntityPlayerMP) sender;
            if (MineDonate.getAccount(player).canAddMoney()) {
                String player_name = args[0];
                String moneyType = args[1];
                int amount;
                try {
                    amount = Integer.valueOf(args[2]);
                    GameProfile profile = FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerProfileCache().getGameProfileForUsername(player_name);
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

    @Override
    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
        if (sender instanceof EntityPlayerMP) { //Эта проверка вообще нужна?
            EntityPlayerMP player = (EntityPlayerMP) sender;
            return MineDonate.getAccount(player).canAddMoney();
        }
        return false;
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        return null;
    }

    @Override
    public boolean isUsernameIndex(String[] args, int index) {
        return false;
    }

    @Override
    public int compareTo(ICommand o) {
        return 0;
    }
}

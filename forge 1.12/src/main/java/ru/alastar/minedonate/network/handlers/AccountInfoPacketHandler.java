package ru.alastar.minedonate.network.handlers;

import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import ru.alastar.minedonate.MineDonate;
import ru.alastar.minedonate.network.packets.AccountInfoPacket;
import ru.alastar.minedonate.network.packets.CodePacket;
import ru.alastar.minedonate.rtnl.common.Account;

/**
 * Created by Alastar on 18.07.2017.
 */
public class AccountInfoPacketHandler implements IMessageHandler<AccountInfoPacket, IMessage> {

    public AccountInfoPacketHandler() {

    }

    @Override
    public IMessage onMessage(AccountInfoPacket message, MessageContext ctx) {

        MineDonate.setAccount(new Account(message.id, message.userName, message.permissions, message.freezShopCreate, message.freezShopCreateFreezer, message.freezShopCreateReason, message.shopsCount));

        for (AccountInfoPacket.MoneySystem ms : message.mSystems) {

            MineDonate.setMoney(ms.type, ms.balance);

        }

        return new CodePacket(CodePacket.Code.MOD_ENABLED);

    }

}
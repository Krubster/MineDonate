package ru.alastar.minedonate.network.handlers;

import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import ru.alastar.minedonate.MineDonate;
import ru.alastar.minedonate.network.packets.MoneyChangedPacket;

public class MoneyChangedPacketHandler implements IMessageHandler<MoneyChangedPacket, IMessage> {

    public MoneyChangedPacketHandler() {

    }

    @Override
    public IMessage onMessage(MoneyChangedPacket message, MessageContext ctx) {

        MineDonate.setMoney(message.moneyType, message.money);

        return null;

    }

}
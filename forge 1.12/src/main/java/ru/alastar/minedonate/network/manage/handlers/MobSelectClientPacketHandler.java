package ru.alastar.minedonate.network.manage.handlers;

import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import ru.alastar.minedonate.MineDonate;
import ru.alastar.minedonate.events.MineDonateGUIHandler;
import ru.alastar.minedonate.network.manage.packets.MobSelectPacket;

public class MobSelectClientPacketHandler implements IMessageHandler<MobSelectPacket, IMessage> {

    public MobSelectClientPacketHandler() {

    }

    @Override
    public IMessage onMessage(MobSelectPacket message, MessageContext ctx) {

        MineDonate.getAccount().ms.mobSelect = true;

        MineDonate.proxy.clientOpenGui(MineDonateGUIHandler.SHOP_ID);

        return null;

    }

}
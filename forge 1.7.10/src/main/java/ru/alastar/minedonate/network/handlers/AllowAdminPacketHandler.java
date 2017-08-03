package ru.alastar.minedonate.network.handlers;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import ru.alastar.minedonate.MineDonate;
import ru.alastar.minedonate.network.packets.AllowAdminMode;

/**
 * Created by Alastar on 02.08.2017.
 */
public class AllowAdminPacketHandler      implements IMessageHandler<AllowAdminMode, IMessage> {

    public AllowAdminPacketHandler(){

    }

    @Override public IMessage onMessage(AllowAdminMode message, MessageContext ctx) {
        MineDonate.m_Admin_mode = true;
        return null;

    }
}
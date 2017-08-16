package ru.alastar.minedonate.network.handlers;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import net.minecraft.entity.player.EntityPlayerMP;
import ru.alastar.minedonate.MineDonate;
import ru.alastar.minedonate.network.packets.AdminPacket;
import ru.alastar.minedonate.rtnl.AdminSessionManager;

/**
 * Created by Alastar on 02.08.2017.
 */
public class AdminPacketHandler implements IMessageHandler<AdminPacket, IMessage> {

    public AdminPacketHandler() {

    }

    @Override
    public IMessage onMessage(AdminPacket message, MessageContext ctx) {
        if (MineDonate.m_Enabled) {
            EntityPlayerMP serverPlayer = ctx.getServerHandler().playerEntity;
            if (AdminSessionManager.checkAdminSession(serverPlayer)) {
                this.handleAdmin(message, serverPlayer);
            }
        }
        return null;

    }

    private void handleAdmin(AdminPacket message, EntityPlayerMP serverPlayer) {
        
    }
}
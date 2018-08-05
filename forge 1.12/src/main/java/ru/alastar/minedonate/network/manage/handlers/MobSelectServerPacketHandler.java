package ru.alastar.minedonate.network.manage.handlers;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import ru.alastar.minedonate.MineDonate;
import ru.alastar.minedonate.network.INetworkTask;
import ru.alastar.minedonate.network.manage.packets.ManageResponsePacket;
import ru.alastar.minedonate.network.manage.packets.ManageResponsePacket.ResponseCode;
import ru.alastar.minedonate.network.manage.packets.ManageResponsePacket.ResponseStatus;
import ru.alastar.minedonate.network.manage.packets.ManageResponsePacket.ResponseType;
import ru.alastar.minedonate.network.manage.packets.MobSelectPacket;
import ru.alastar.minedonate.rtnl.ModNetworkTaskProcessor;
import ru.alastar.minedonate.rtnl.common.Account;

public class MobSelectServerPacketHandler implements IMessageHandler<MobSelectPacket, IMessage>, INetworkTask<MobSelectPacket, IMessage> {

    public MobSelectServerPacketHandler() {

    }

    @Override
    public IMessage onMessage(MobSelectPacket message, MessageContext ctx) {

        ModNetworkTaskProcessor.processTask((INetworkTask) this, message, ctx);

        return null;

    }

    @Override
    public IMessage onMessageProcess(MobSelectPacket message, MessageContext ctx) {

        EntityPlayerMP serverPlayer = ctx.getServerHandler().player;

        Account acc = MineDonate.getAccount(serverPlayer);
        acc.ms.mobSelect = !acc.ms.mobSelect;

        return new ManageResponsePacket(ResponseType.ENTITY, ResponseCode.ADD, (acc.ms.mobSelect ? ResponseStatus.ENTITY_SELECT_START : ResponseStatus.ENTITY_SELECT_STOP));

    }

}
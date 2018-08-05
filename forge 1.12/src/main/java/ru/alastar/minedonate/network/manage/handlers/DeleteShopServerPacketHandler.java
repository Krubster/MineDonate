package ru.alastar.minedonate.network.manage.handlers;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import ru.alastar.minedonate.MineDonate;
import ru.alastar.minedonate.network.INetworkTask;
import ru.alastar.minedonate.network.manage.packets.DeleteShopPacket;
import ru.alastar.minedonate.network.manage.packets.ManageResponsePacket;
import ru.alastar.minedonate.rtnl.ModManager;
import ru.alastar.minedonate.rtnl.ModNetworkTaskProcessor;
import ru.alastar.minedonate.rtnl.common.Shop;

public class DeleteShopServerPacketHandler implements IMessageHandler<DeleteShopPacket, IMessage>, INetworkTask<DeleteShopPacket, IMessage> {

    public DeleteShopServerPacketHandler() {

    }

    @Override
    public IMessage onMessage(DeleteShopPacket message, MessageContext ctx) {

        ModNetworkTaskProcessor.processTask((INetworkTask) this, message, ctx);

        return null;

    }

    @Override
    public IMessage onMessageProcess(DeleteShopPacket message, MessageContext ctx) {

        if (!MineDonate.checkShopAndLoad(message.shopId)) {

            return new ManageResponsePacket(ManageResponsePacket.ResponseType.SHOP, ManageResponsePacket.ResponseCode.REMOVE, ManageResponsePacket.ResponseStatus.ERROR_SHOP_NOTFOUND);

        }

        EntityPlayerMP serverPlayer = ctx.getServerHandler().player;

        Shop s = MineDonate.shops.get(message.shopId);

        if (MineDonate.getAccount(serverPlayer).canDeleteShop(s.owner)) {

            if (s.isFreezed) {

                return new ManageResponsePacket(ManageResponsePacket.ResponseType.SHOP, ManageResponsePacket.ResponseCode.REMOVE, ManageResponsePacket.ResponseStatus.ERROR_SHOP_FREEZED);

            }

            ModManager.deleteShop(s);

            return new ManageResponsePacket(ManageResponsePacket.ResponseType.SHOP, ManageResponsePacket.ResponseCode.REMOVE, ManageResponsePacket.ResponseStatus.OK);

        } else {

            return new ManageResponsePacket(ManageResponsePacket.ResponseType.SHOP, ManageResponsePacket.ResponseCode.REMOVE, ManageResponsePacket.ResponseStatus.ERROR_ACCESS_DENIED);

        }

    }

}
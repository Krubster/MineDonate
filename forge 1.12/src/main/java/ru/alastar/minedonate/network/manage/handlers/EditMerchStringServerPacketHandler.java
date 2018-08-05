package ru.alastar.minedonate.network.manage.handlers;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import ru.alastar.minedonate.MineDonate;
import ru.alastar.minedonate.network.INetworkTask;
import ru.alastar.minedonate.network.manage.packets.EditMerchStringPacket;
import ru.alastar.minedonate.network.manage.packets.ManageResponsePacket;
import ru.alastar.minedonate.rtnl.ModManager;
import ru.alastar.minedonate.rtnl.ModNetworkTaskProcessor;
import ru.alastar.minedonate.rtnl.common.Account;
import ru.alastar.minedonate.rtnl.common.Shop;

public class EditMerchStringServerPacketHandler implements IMessageHandler<EditMerchStringPacket, IMessage>, INetworkTask<EditMerchStringPacket, IMessage> {

    public EditMerchStringServerPacketHandler() {

    }

    @Override
    public IMessage onMessage(EditMerchStringPacket message, MessageContext ctx) {

        ModNetworkTaskProcessor.processTask((INetworkTask) this, message, ctx);

        return null;

    }

    @Override
    public IMessage onMessageProcess(EditMerchStringPacket message, MessageContext ctx) {

        if (!MineDonate.checkShopExists(message.shopId)) {

            return new ManageResponsePacket(ManageResponsePacket.ResponseType.OBJ, ManageResponsePacket.ResponseCode.EDIT, ManageResponsePacket.ResponseStatus.ERROR_SHOP_NOTFOUND);

        }

        EntityPlayerMP serverPlayer = ctx.getServerHandler().player;

        Shop s = MineDonate.shops.get(message.shopId);

        Account acc = MineDonate.getAccount(serverPlayer);

        if (acc.canEditShop(s.owner)) {

            if (s.isFreezed) {

                return new ManageResponsePacket(ManageResponsePacket.ResponseType.OBJ, ManageResponsePacket.ResponseCode.EDIT, ManageResponsePacket.ResponseStatus.ERROR_SHOP_FREEZED);

            }

            if (message.str.length() > 140) {

                return new ManageResponsePacket(ManageResponsePacket.ResponseType.OBJ, ManageResponsePacket.ResponseCode.EDIT, ManageResponsePacket.ResponseStatus.ERROR_UNKNOWN);

            }

            if (!MineDonate.checkCatExists(s.sid, message.catId)) {

                return new ManageResponsePacket(ManageResponsePacket.ResponseType.OBJ, ManageResponsePacket.ResponseCode.EDIT, ManageResponsePacket.ResponseStatus.ERROR_CAT_NOTFOUND);

            }

            if (!s.cats[message.catId].merchExists(message.merchId)) {

                return new ManageResponsePacket(ManageResponsePacket.ResponseType.OBJ, ManageResponsePacket.ResponseCode.EDIT, ManageResponsePacket.ResponseStatus.ERROR_ENTRY_NOTFOUND);

            }

            ModManager.editShopEntryString(serverPlayer, s, message.catId, message.merchId, message.type, message.str);

            return new ManageResponsePacket(ManageResponsePacket.ResponseType.OBJ, ManageResponsePacket.ResponseCode.EDIT, ManageResponsePacket.ResponseStatus.OK);

        } else {

            return new ManageResponsePacket(ManageResponsePacket.ResponseType.OBJ, ManageResponsePacket.ResponseCode.EDIT, ManageResponsePacket.ResponseStatus.ERROR_ACCESS_DENIED);

        }

    }

}
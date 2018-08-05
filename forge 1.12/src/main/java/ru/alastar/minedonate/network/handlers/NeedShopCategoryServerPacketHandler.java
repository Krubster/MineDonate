package ru.alastar.minedonate.network.handlers;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import ru.alastar.minedonate.MineDonate;
import ru.alastar.minedonate.merch.info.ShopInfo;
import ru.alastar.minedonate.network.INetworkTask;
import ru.alastar.minedonate.network.packets.AddMerchPacket;
import ru.alastar.minedonate.network.packets.CodePacket;
import ru.alastar.minedonate.network.packets.NeedShopCategoryPacket;
import ru.alastar.minedonate.rtnl.ModNetworkRegistry;
import ru.alastar.minedonate.rtnl.ModNetworkTaskProcessor;

public class NeedShopCategoryServerPacketHandler implements IMessageHandler<NeedShopCategoryPacket, IMessage>, INetworkTask<NeedShopCategoryPacket, IMessage> {

    public NeedShopCategoryServerPacketHandler() {

    }

    @Override
    public IMessage onMessage(NeedShopCategoryPacket message, MessageContext ctx) {

        ModNetworkTaskProcessor.processTask((INetworkTask) this, message, ctx);

        return null;

    }

    @Override
    public IMessage onMessageProcess(NeedShopCategoryPacket message, MessageContext ctx) {

        EntityPlayerMP serverPlayer = ctx.getServerHandler().player;

        if (!MineDonate.shops.containsKey(message.shopId)) {

            MineDonate.loadUserShop(message.shopId);

        }

        if (!MineDonate.checkCatExists(message.shopId, message.cat)) {

            return null;

        }

        // ModNetworkRegistry . sendToCategoryPacket ( ( EntityPlayerMP ) serverPlayer,  message . shopId, message . cat ) ;

        if (MineDonate.shops.containsKey(message.shopId) && MineDonate.shops.get(message.shopId).cats.length > message.cat) {

            for (int j = 0; j < MineDonate.shops.get(message.shopId).cats[message.cat].getMerch().length; ++j) {

                AddMerchPacket packet;

                if (MineDonate.shops.get(message.shopId).cats[message.cat].getMerch()[j] instanceof ShopInfo) {

                    ShopInfo si = ((ShopInfo) MineDonate.shops.get(message.shopId).cats[message.cat].getMerch()[j]);

                    if (!si.owner.equalsIgnoreCase(serverPlayer.getName())) {

                        if (!MineDonate.getAccount(serverPlayer).canViewOtherFreezText()) {

                            (si = (ShopInfo) si.copy()).cleanFreezVisibleData();

                        }

                    }

                    packet = new AddMerchPacket(si);

                } else {

                    packet = new AddMerchPacket(MineDonate.shops.get(message.shopId).cats[message.cat].getMerch()[j]);

                }

                ModNetworkRegistry.sendTo(serverPlayer, packet);

            }

        }

        return new CodePacket(CodePacket.Code.CLIENT_RECEIVED_NEEDED_CAT_INFO);

    }

}

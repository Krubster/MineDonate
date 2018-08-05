package ru.alastar.minedonate.network.handlers;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import ru.alastar.minedonate.MineDonate;
import ru.alastar.minedonate.Utils;
import ru.alastar.minedonate.merch.Merch;
import ru.alastar.minedonate.network.INetworkTask;
import ru.alastar.minedonate.network.packets.BuyPacket;
import ru.alastar.minedonate.network.packets.BuyResponsePacket;
import ru.alastar.minedonate.rtnl.ModNetworkRegistry;
import ru.alastar.minedonate.rtnl.ModNetworkTaskProcessor;
import ru.alastar.minedonate.rtnl.ModShopLogger;

/**
 * Created by Alastar on 18.07.2017.
 */
public class BuyPacketHandler implements IMessageHandler<BuyPacket, IMessage>, INetworkTask<BuyPacket, IMessage> {

    public BuyPacketHandler() {
    }

    @Override
    public IMessage onMessage(BuyPacket message, MessageContext ctx) {

        ModNetworkTaskProcessor.processTask((INetworkTask) this, message, ctx);

        return null;

    }

    @Override
    public IMessage onMessageProcess(BuyPacket message, MessageContext ctx) {

        try {

            EntityPlayerMP serverPlayer = ctx.getServerHandler().player;

            if (message.category < MineDonate.shops.get(message.shopId).cats.length) {

                Merch info = MineDonate.shops.get(message.shopId).cats[message.category].getMerch(message.merchId);

                if (info != null) {

                    if (!MineDonate.checkShopExists(info.shopId)) {

                        return new BuyResponsePacket(BuyResponsePacket.Status.ERROR_UNKNOWN);

                    } else if (MineDonate.shops.get(info.shopId).isFreezed) {

                        return new BuyResponsePacket(BuyResponsePacket.Status.ERROR_SHOP_FREEZED);

                    } else {

                        if (info.canBuy(serverPlayer, message.amount)) {

                            int procMoney = -1;

                            if ((procMoney = MineDonate.getMoneyProcessor(info.getMoneyType()).canBuy(info, Utils.getUUIDFromPlayer(serverPlayer), message.amount)) != -1) {

                                ModShopLogger.logBuy(info, serverPlayer, message.amount, info.getMoneyType());

                                int currentMoney = info.withdrawMoney(serverPlayer.getGameProfile().getId(), procMoney);

                                ModNetworkRegistry.sendToMoneyChangedPacket((EntityPlayerMP) serverPlayer, currentMoney, info.getMoneyType());

                                MineDonate.shops.get(message.shopId).cats[message.category].giveMerch(serverPlayer, info, message.amount);

                                return new BuyResponsePacket(BuyResponsePacket.Status.SUCCESSFUL);

                            } else {

                                return new BuyResponsePacket(BuyResponsePacket.Status.ERROR_NOT_ENOUGH_MONEY);

                            }

                        } else {

                            return new BuyResponsePacket(BuyResponsePacket.Status.ERROR_CANT_BUY);

                        }

                    }

                }

            }

        } catch (Exception ex) {

            ex.printStackTrace();

        }

        return null;

    }
}
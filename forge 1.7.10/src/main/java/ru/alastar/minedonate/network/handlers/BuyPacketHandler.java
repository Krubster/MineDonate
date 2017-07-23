package ru.alastar.minedonate.network.handlers;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import net.minecraft.entity.player.EntityPlayerMP;
import ru.alastar.minedonate.MineDonate;
import ru.alastar.minedonate.merch.IMerch;
import ru.alastar.minedonate.network.MineDonateNetwork;
import ru.alastar.minedonate.network.packets.AccountInfoPacket;
import ru.alastar.minedonate.network.packets.BuyPacket;
import ru.alastar.minedonate.network.packets.BuyResponsePacket;

/**
 * Created by Alastar on 18.07.2017.
 */
public class BuyPacketHandler implements IMessageHandler<BuyPacket, IMessage> {
    // Do note that the default constructor is required, but implicitly defined in this case
    public BuyPacketHandler() {

    }

    @Override
    public IMessage onMessage(BuyPacket message, MessageContext ctx) {
        byte resp = 0;
        if (MineDonate.m_Enabled) {
            EntityPlayerMP serverPlayer = ctx.getServerHandler().playerEntity;
            //sender          ^^^^
            int category = message.category;
            int merchId = message.merchId;
            if (category < MineDonate.m_Categories.length) {
                IMerch info = MineDonate.m_Categories[category].getMerch(merchId);
                if (info != null) {
                    int playerMoney = MineDonate.getMoneyFor(serverPlayer.getDisplayName());
                    if (info.getCost() * message.amount <= playerMoney && info.canBuy(serverPlayer, message.amount)) {
                        MineDonate.logBuy(info, serverPlayer, message.amount);
                        MineDonate.WithdrawMoney(playerMoney - info.getCost() * message.amount, serverPlayer);
                        MineDonateNetwork.INSTANCE.sendTo(new AccountInfoPacket(playerMoney - info.getCost()* message.amount), (EntityPlayerMP) serverPlayer);
                        MineDonate.m_Categories[category].GiveMerch(serverPlayer, info, message.amount);
                        resp = 0;
                    } else {
                        resp = 1;
                    }
                }
            }
        }
        return new BuyResponsePacket((byte) resp);
    }
}
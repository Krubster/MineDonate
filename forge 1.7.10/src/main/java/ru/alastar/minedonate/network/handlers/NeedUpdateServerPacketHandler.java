package ru.alastar.minedonate.network.handlers;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import net.minecraft.entity.player.EntityPlayerMP;
import ru.alastar.minedonate.MineDonate;
import ru.alastar.minedonate.gui.ShopGUI;
import ru.alastar.minedonate.network.packets.AccountInfoPacket;
import ru.alastar.minedonate.network.packets.AddMerchPacket;
import ru.alastar.minedonate.network.packets.MerchInfoPacket;
import ru.alastar.minedonate.network.packets.NeedUpdatePacket;
import ru.alastar.minedonate.network.packets.SupportedFeaturesPacket;

public class NeedUpdateServerPacketHandler implements IMessageHandler<NeedUpdatePacket, IMessage> {
    
	public NeedUpdateServerPacketHandler(){

    }
    
    @Override 
    public IMessage onMessage(NeedUpdatePacket message, MessageContext ctx) {
    	
    	if ( message . r == 0 ) {
    		if (MineDonate.m_Enabled) {
                EntityPlayerMP serverPlayer = ctx.getServerHandler().playerEntity;
                if (!MineDonate.ExistsAccount(serverPlayer)) {
                    MineDonate.RegisterPlayer(serverPlayer);
                }

                SupportedFeaturesPacket features_packet = new SupportedFeaturesPacket(MineDonate.cfg.sellItems, MineDonate.cfg.sellPrivelegies, MineDonate.cfg.sellRegions, MineDonate.cfg.sellEntities, MineDonate.cfg.userShops);
                MineDonate.networkChannel.sendTo(features_packet, serverPlayer);
                AccountInfoPacket info_packet = new AccountInfoPacket(MineDonate.getInstance().getMoneyFor(serverPlayer.getDisplayName()));
                MineDonate.networkChannel.sendTo(info_packet, serverPlayer);
                
                int i = 0;
                //for (int i = 0; i < 1;/*MineDonate.m_Categories.length;*/ ++i) {
                    for (int j = 0; j <  MineDonate.shops.get(0).cats[i].getMerch().length; ++j) {
                        AddMerchPacket packet = new AddMerchPacket( MineDonate.shops.get(0).cats[i].getMerch()[j]);
                        MineDonate.networkChannel.sendTo(packet, serverPlayer);
                    }
                //}
                
                MineDonate.networkChannel.sendTo(new NeedUpdatePacket(1), serverPlayer);

            }
    			
    	}
    	

        return null;
    }
}
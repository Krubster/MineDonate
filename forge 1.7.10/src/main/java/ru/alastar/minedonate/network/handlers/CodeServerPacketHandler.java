package ru.alastar.minedonate.network.handlers;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import net.minecraft.entity.player.EntityPlayerMP;
import ru.alastar.minedonate.MineDonate;
import ru.alastar.minedonate.mproc.AbstractMoneyProcessor;
import ru.alastar.minedonate.network.packets.AccountInfoPacket;
import ru.alastar.minedonate.network.packets.AddMerchPacket;
import ru.alastar.minedonate.network.packets.CodePacket;
import ru.alastar.minedonate.network.packets.SupportedFeaturesPacket;
import ru.alastar.minedonate.rtnl.ModNetworkRegistry;

public class CodeServerPacketHandler implements IMessageHandler<CodePacket, IMessage> {
    
	public CodeServerPacketHandler ( ) {

    }
    
    @Override 
    public IMessage onMessage(CodePacket message, MessageContext ctx) {

    	if ( message . code == CodePacket . Code . CLIENT_NEED_FULL_INFO ) {

    		if ( MineDonate . m_Enabled ) {
		
                EntityPlayerMP serverPlayer = ctx . getServerHandler ( ) . playerEntity ;
                String userName = serverPlayer . getDisplayName ( ) . toLowerCase ( ) ;
                
                for ( AbstractMoneyProcessor amp : MineDonate . moneyProcessors . values ( ) ) {

                    if (!amp.existsAccount(MineDonate.getUUIDFromPlayer(serverPlayer))) {

                        amp.registerPlayer(MineDonate.getUUIDFromPlayer(serverPlayer), serverPlayer.getDisplayName(), MineDonate.moneyProcessors.values());

                	}
                	
                }

                SupportedFeaturesPacket features_packet = new SupportedFeaturesPacket ( MineDonate . cfg ) ;

                ModNetworkRegistry . sendTo ( serverPlayer, features_packet ) ;
        		ModNetworkRegistry . sendTo ( serverPlayer, new AccountInfoPacket ( serverPlayer . getGameProfile ( ) . getId ( ) . toString ( ), userName ) ) ;
                                        
                // MineDonate . networkChannel . sendTo ( new CategoryPacket ( 0, features_packet . firstCatId, MineDonate . shops . get ( 0 ) . cats [ features_packet . firstCatId ] . subCategories ), serverPlayer ) ;
                
                for ( int j = 0; j < MineDonate . shops . get ( 0 ) . cats [ features_packet . firstCatId ] . getMerch ( ) . length ; ++ j ) {
                	
                	ModNetworkRegistry . sendTo ( serverPlayer, new AddMerchPacket ( MineDonate . shops . get ( 0 ) . cats [ features_packet . firstCatId ] . getMerch ( ) [ j ] ) ) ;

                }

                return new CodePacket ( CodePacket . Code . CLIENT_RECEIVED_FULL_INFO ) ;

            }
    			
    	}
    	

        return null;
    }
    
}
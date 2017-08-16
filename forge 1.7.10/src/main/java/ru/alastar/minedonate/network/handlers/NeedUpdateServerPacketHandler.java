package ru.alastar.minedonate.network.handlers;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

import net.minecraft.entity.player.EntityPlayerMP;

import ru.alastar.minedonate.MineDonate;
import ru.alastar.minedonate.mproc.AbstractMoneyProcessor;
import ru.alastar.minedonate.network.packets.AccountInfoPacket;
import ru.alastar.minedonate.network.packets.AddMerchPacket;
import ru.alastar.minedonate.network.packets.NeedUpdatePacket;
import ru.alastar.minedonate.network.packets.SupportedFeaturesPacket;
import ru.alastar.minedonate.rtnl.ModNetwork;

public class NeedUpdateServerPacketHandler implements IMessageHandler<NeedUpdatePacket, IMessage> {
    
	public NeedUpdateServerPacketHandler(){

    }
    
    @Override 
    public IMessage onMessage(NeedUpdatePacket message, MessageContext ctx) {

    	if ( message . r == 0 ) {

    		if ( MineDonate . m_Enabled ) {
    			
                EntityPlayerMP serverPlayer = ctx . getServerHandler ( ) . playerEntity ;
                String userName = serverPlayer . getDisplayName ( ) . toLowerCase ( ) ;
                
                for ( AbstractMoneyProcessor amp : MineDonate . moneyProcessors . values ( ) ) {

                	if ( ! amp . existsAccount ( userName ) ) {
                		
                		amp . registerPlayer ( userName,  MineDonate . moneyProcessors . values ( ) ) ;

                	}
                	
                }

                SupportedFeaturesPacket features_packet = new SupportedFeaturesPacket ( MineDonate . cfg ) ;

                ModNetwork . sendTo ( serverPlayer, features_packet ) ;
        		ModNetwork . sendTo ( serverPlayer, new AccountInfoPacket ( userName ) ) ;
                                        
                // MineDonate . networkChannel . sendTo ( new CategoryPacket ( 0, features_packet . firstCatId, MineDonate . shops . get ( 0 ) . cats [ features_packet . firstCatId ] . subCategories ), serverPlayer ) ;
                
                for ( int j = 0; j < MineDonate . shops . get ( 0 ) . cats [ features_packet . firstCatId ] . getMerch ( ) . length ; ++ j ) {
                	
                	ModNetwork . sendTo ( serverPlayer, new AddMerchPacket ( MineDonate . shops . get ( 0 ) . cats [ features_packet . firstCatId ] . getMerch ( ) [ j ] ) ) ;

                }

                return new NeedUpdatePacket ( 1 ) ;

            }
    			
    	}
    	

        return null;
    }
    
}
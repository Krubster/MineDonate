package ru.alastar.minedonate.network.handlers;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import net.minecraft.entity.player.EntityPlayerMP;
import ru.alastar.minedonate.MineDonate;
import ru.alastar.minedonate.mproc.AbstractMoneyProcessor;
import ru.alastar.minedonate.network.packets.AccountInfoPacket;
import ru.alastar.minedonate.network.packets.AddMerchPacket;
import ru.alastar.minedonate.network.packets.CategoryPacket;
import ru.alastar.minedonate.network.packets.NeedUpdatePacket;
import ru.alastar.minedonate.network.packets.SupportedFeaturesPacket;

public class NeedUpdateServerPacketHandler implements IMessageHandler<NeedUpdatePacket, IMessage> {
    
	public NeedUpdateServerPacketHandler(){

    }
    
    @Override 
    public IMessage onMessage(NeedUpdatePacket message, MessageContext ctx) {
    	
    	if ( message . r == 0 ) {
    		
    		if ( MineDonate . m_Enabled ) {
    			
                EntityPlayerMP serverPlayer = ctx . getServerHandler ( ) . playerEntity ;
                String userName = serverPlayer . getDisplayName ( ) ;
                
                for ( AbstractMoneyProcessor amp : MineDonate . moneyProcessors . values ( ) ) {

                	if ( ! amp . existsAccount ( userName ) ) {
                		
                		amp . registerPlayer ( userName,  MineDonate . moneyProcessors . values ( ) ) ;

                	}
                	
                }
   
                SupportedFeaturesPacket features_packet = new SupportedFeaturesPacket ( MineDonate . cfg ) ;
                MineDonate . networkChannel . sendTo ( features_packet, serverPlayer ) ;
                
        		MineDonate . networkChannel . sendTo ( new AccountInfoPacket ( userName ), serverPlayer ) ;
                      
                MineDonate . networkChannel . sendTo ( new CategoryPacket ( 0, 0, MineDonate . shops . get ( 0 ) . cats [ 0 ] . subCategories ), serverPlayer ) ;
                
                for ( int j = 0; j < MineDonate . shops . get ( 0 ) . cats [ 0 ] . getMerch ( ) . length ; ++ j ) {
                	
                    AddMerchPacket packet = new AddMerchPacket ( MineDonate . shops . get ( 0 ) . cats [ 0 ] . getMerch ( ) [ j ] ) ;
                    MineDonate . networkChannel . sendTo ( packet, serverPlayer ) ;
                    
                }


                return new NeedUpdatePacket ( 1 ) ;

            }
    			
    	}
    	

        return null;
    }
    
}
package ru.alastar.minedonate.network.handlers;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

import net.minecraft.entity.player.EntityPlayerMP;

import ru.alastar.minedonate.MineDonate;
import ru.alastar.minedonate.merch.Merch;
import ru.alastar.minedonate.network.packets.BuyPacket;
import ru.alastar.minedonate.network.packets.BuyResponsePacket;
import ru.alastar.minedonate.rtnl.ModNetwork;
import ru.alastar.minedonate.rtnl.ShopLogger;

/**
 * Created by Alastar on 18.07.2017.
 */
public class BuyPacketHandler implements IMessageHandler<BuyPacket, IMessage> {

	public BuyPacketHandler ( ) { }

    @Override
    public IMessage onMessage(BuyPacket message, MessageContext ctx) {

        byte resp = 0;
        
        if ( MineDonate . m_Enabled ) {
        	
            try {
            	
            	EntityPlayerMP serverPlayer = ctx . getServerHandler ( ) . playerEntity ;
                //sender          ^^^^
                int category = message . category ;
                int merchId = message . merchId ;

                if ( category < MineDonate . shops . get ( message . shopId ) . cats . length ) {
                	
                    Merch info = MineDonate. shops . get ( message . shopId ) . cats [ category ] . getMerch ( merchId ) ;
                    
                    if ( info != null ) {
                    	
                        //int playerMoney = MineDonate.getMoneyFor(serverPlayer.getDisplayName());
                    	if ( ! MineDonate . checkShopExists ( info . shopId ) ) {
                    		
                    		resp = 2 ;
                    		
                    	} else if ( MineDonate . shops . get ( info . shopId ) . isFreezed  ) {
                    	
                    		resp = 3 ;

                    	} else {
                    		
	                        if ( info . canBuy ( serverPlayer, message . amount ) ) { // info . getCost ( ) * message.amount <= playerMoney && 
	                        	
	                        	int procMoney = -1 ;
	                        	
	                        	if ( ( procMoney = MineDonate . getMoneyProcessor ( info . getMoneyType ( ) ) . canBuy ( info, serverPlayer . getDisplayName ( ), message . amount ) ) != -1 ) {
	                        		
		                            ShopLogger . logBuy ( info, serverPlayer, message . amount, info . getMoneyType ( ) ) ;
		                            int currentMoney = info . withdrawMoney ( serverPlayer . getDisplayName ( ), procMoney ) ;
		                               
		                            ModNetwork . sendToMoneyChangedPacket ( ( EntityPlayerMP ) serverPlayer, currentMoney, info . getMoneyType ( ) ) ;
	
		                            MineDonate . shops . get ( message . shopId ) . cats [ category ] . giveMerch ( serverPlayer, info, message . amount ) ;
		                            
		                            resp = 0;
		                            
	                        	} else {
	                        		
	                        		resp = 1 ;
	                        		
	                        	}
	                        	
	                        } else {
	                        	
	                            resp = 1;
	                            
	                        }
	                        
                    	}
                    }
                }
            
            
            } catch ( Exception ex ) {
            	
            	ex . printStackTrace ( ) ;
            	
            }
            
        }
        
        return new BuyResponsePacket ( ( byte ) resp ) ;
        
    }
}
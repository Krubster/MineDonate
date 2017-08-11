package ru.alastar.minedonate.network.handlers;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

import net.minecraft.entity.player.EntityPlayerMP;

import ru.alastar.minedonate.MineDonate;
import ru.alastar.minedonate.merch.info.ShopInfo;
import ru.alastar.minedonate.network.packets.AddMerchPacket;
import ru.alastar.minedonate.network.packets.NeedShopCategoryPacket;
import ru.alastar.minedonate.network.packets.NeedUpdatePacket;
import ru.alastar.minedonate.rtnl.ModNetwork;

public class NeedShopCategoryServerPacketHandler implements IMessageHandler<NeedShopCategoryPacket, IMessage> {
    
	public NeedShopCategoryServerPacketHandler ( ) {

    }
    
    @Override 
    public IMessage onMessage(NeedShopCategoryPacket message, MessageContext ctx) {
    	
		if ( MineDonate . m_Enabled ) {
			
            EntityPlayerMP serverPlayer = ctx . getServerHandler ( ) . playerEntity ;
            
            if ( ! MineDonate . shops . containsKey ( message . shopId ) ) {
            	
            	MineDonate . loadUserShop ( message . shopId ) ;
            	
            }
            
            if ( ! MineDonate . checkCatExists ( message . shopId, message . cat ) ) {
            	
            	return null ;
            	
            }

            ModNetwork . sendToCategoryPacket ( ( EntityPlayerMP ) serverPlayer,  message . shopId, message . cat ) ;

            if ( MineDonate . shops . containsKey ( message . shopId ) && MineDonate . shops . get ( message . shopId ) . cats . length > message . cat ) {
                         
            	for ( int j = 0; j < MineDonate . shops . get ( message . shopId ) . cats [ message . cat ] . getMerch ( ) . length ; ++ j ) {
                	
                    AddMerchPacket packet = new AddMerchPacket ( MineDonate . shops . get ( message . shopId ) . cats [ message . cat ] . getMerch ( ) [ j ] ) ;
                    
                    if ( packet . info instanceof ShopInfo ) {
                    	
                    	ShopInfo si = ( ( ShopInfo ) packet . info ) ;
                    	if ( ! si . owner . equalsIgnoreCase ( serverPlayer . getDisplayName ( ) ) ) {
                    		
                    		if ( ! MineDonate . getAccount ( serverPlayer . getDisplayName ( ) . toLowerCase ( ) ) . canViewOtherFreezText ( ) ) {
                    			
                    			si . cleanFreezVisibleData ( ) ; 
                    			
                    		}
                    		
                    	}
                    	
                    }
                    
                    ModNetwork . sendTo ( serverPlayer, packet ) ;

                }
            
            }
            
            return new NeedUpdatePacket ( 2 ) ;
            
        }
		
        return null;
        
    }
    
}

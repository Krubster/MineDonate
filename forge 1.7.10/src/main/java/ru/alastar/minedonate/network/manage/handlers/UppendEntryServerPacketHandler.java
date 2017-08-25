package ru.alastar.minedonate.network.manage.handlers;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import net.minecraft.entity.player.EntityPlayerMP;
import ru.alastar.minedonate.MineDonate;
import ru.alastar.minedonate.network.manage.packets.UppendEntryPacket;
import ru.alastar.minedonate.network.manage.packets.ManageResponsePacket;
import ru.alastar.minedonate.rtnl.Account;
import ru.alastar.minedonate.rtnl.INetworkTask;
import ru.alastar.minedonate.rtnl.Manager;
import ru.alastar.minedonate.rtnl.NetworkMessageProcessor;
import ru.alastar.minedonate.rtnl.Shop;

public class UppendEntryServerPacketHandler implements IMessageHandler < UppendEntryPacket, IMessage >, INetworkTask < UppendEntryPacket, IMessage > {
	
    public UppendEntryServerPacketHandler ( ) {

    }

    @Override
    public IMessage onMessage ( UppendEntryPacket message, MessageContext ctx ) {
    	
    	NetworkMessageProcessor . processTask ( ( INetworkTask ) this, message, ctx ) ;

    	return null ;
    	
    }
    
    @Override
    public IMessage onMessageProcess ( UppendEntryPacket message, MessageContext ctx ) {

    	if ( ! MineDonate . checkShopExists ( message . shopId ) ) {
    		
			return new ManageResponsePacket ( ManageResponsePacket.ResponseType.OBJ, ManageResponsePacket.ResponseCode.UPPEND, ManageResponsePacket.ResponseStatus.ERROR_SHOP_NOTFOUND ) ;

    	}
    	
		EntityPlayerMP serverPlayer = ctx . getServerHandler ( ) . playerEntity ;
		
		Shop s = MineDonate . shops . get ( message . shopId ) ;
		
		Account acc = MineDonate . getAccount ( serverPlayer ) ;

		if ( acc . canEditShop ( s . owner ) ) {
			
			if ( s . isFreezed ) {

				return new ManageResponsePacket ( ManageResponsePacket.ResponseType.OBJ, ManageResponsePacket.ResponseCode.UPPEND, ManageResponsePacket.ResponseStatus.ERROR_SHOP_FREEZED ) ;

			}
			
			if ( ! MineDonate . checkCatExists ( s . sid, message . catId ) ) {
				
				return new ManageResponsePacket ( ManageResponsePacket.ResponseType.OBJ, ManageResponsePacket.ResponseCode.UPPEND, ManageResponsePacket.ResponseStatus.ERROR_CAT_NOTFOUND ) ;

			}
			
			switch ( s . cats [ message . catId ] . getCatType ( ) ) {
				
				case ITEMS :
					
					if ( acc . ms . currentItemStack == null ) {
						
						return new ManageResponsePacket ( ManageResponsePacket.ResponseType.OBJ, ManageResponsePacket.ResponseCode.UPPEND, ManageResponsePacket.ResponseStatus.ERROR_UNKNOWN ) ;

					}
					
					int merchId = Manager . canUppendAnotherItemInShop ( acc, s, message . catId ) ;
					
					if ( merchId == -1 ) {
						
				        return new ManageResponsePacket ( ManageResponsePacket.ResponseType.OBJ, ManageResponsePacket.ResponseCode.UPPEND, ManageResponsePacket.ResponseStatus.ERROR_ENTRY_NOTFOUND ) ;
		
					}		
					
					Manager . uppendItemInShop ( acc, s, message . catId, merchId ) ;

				break ;		
				
			}
						
	        return new ManageResponsePacket ( ManageResponsePacket.ResponseType.OBJ, ManageResponsePacket.ResponseCode.UPPEND, ManageResponsePacket.ResponseStatus.OK ) ;

		} else {
		
	        return new ManageResponsePacket ( ManageResponsePacket.ResponseType.OBJ, ManageResponsePacket.ResponseCode.UPPEND, ManageResponsePacket.ResponseStatus.ERROR_ACCESS_DENIED ) ;

		}
		        
    }
    
}
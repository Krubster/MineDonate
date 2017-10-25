package ru.alastar.minedonate.network.manage.handlers;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

import net.minecraft.entity.player.EntityPlayerMP;

import ru.alastar.minedonate.MineDonate;
import ru.alastar.minedonate.network.INetworkTask;
import ru.alastar.minedonate.network.manage.packets.EditMerchFieldPacket;
import ru.alastar.minedonate.network.manage.packets.ManageResponsePacket;
import ru.alastar.minedonate.rtnl.ModShopManager;
import ru.alastar.minedonate.rtnl.ModNetworkTaskProcessor;
import ru.alastar.minedonate.rtnl.common.Account;
import ru.alastar.minedonate.rtnl.common.Shop;

public class EditMerchFieldServerPacketHandler implements IMessageHandler < EditMerchFieldPacket, IMessage >, INetworkTask < EditMerchFieldPacket, IMessage > {
	
    public EditMerchFieldServerPacketHandler ( ) {

    }

    @Override
    public IMessage onMessage ( EditMerchFieldPacket message, MessageContext ctx ) {
    	
    	ModNetworkTaskProcessor . processTask ( ( INetworkTask ) this, message, ctx ) ;

    	return null ;
    	
    }
    
    @Override
    public IMessage onMessageProcess ( EditMerchFieldPacket message, MessageContext ctx ) {
    	
    	if ( ! MineDonate . checkShopExists ( message . shopId ) ) {
    		
			return new ManageResponsePacket ( ManageResponsePacket.ResponseType.OBJ, ManageResponsePacket.ResponseCode.EDIT, ManageResponsePacket.ResponseStatus.ERROR_SHOP_NOTFOUND ) ;

    	}
    	
		EntityPlayerMP serverPlayer = ctx . getServerHandler ( ) . playerEntity ;
		
		Shop s = MineDonate . shops . get ( message . shopId ) ;
		
		Account acc = MineDonate . getAccount ( serverPlayer ) ;
		
		if ( acc . canEditShop ( s . owner ) ) {
			
			if ( s . isFreezed ) {

				return new ManageResponsePacket ( ManageResponsePacket.ResponseType.OBJ, ManageResponsePacket.ResponseCode.EDIT, ManageResponsePacket.ResponseStatus.ERROR_SHOP_FREEZED ) ;

			}
			
			switch ( message . type ) {
			
				case STRING: 
					
					String str = ( String ) message . data ;
					
					if ( str . length ( ) > 140 ) {
		
						return new ManageResponsePacket ( ManageResponsePacket.ResponseType.OBJ, ManageResponsePacket.ResponseCode.EDIT, ManageResponsePacket.ResponseStatus.ERROR_UNKNOWN ) ;
		
					}
					
				break;
			
				case INTEGER:
				
					if ( message . name == EditMerchFieldPacket . FieldName . LIMIT ) {
						
						switch ( s . cats [ message . catId ] . getCatType ( ) ) {
							
							case ITEMS:
								
								if ( ! acc . canUnlimitedItems ( ) ) {
									
									return new ManageResponsePacket ( ManageResponsePacket.ResponseType.OBJ, ManageResponsePacket.ResponseCode.EDIT, ManageResponsePacket.ResponseStatus.ERROR_ACCESS_DENIED ) ;

								}
								
							break;
							
							case ENTITIES:
								
								if ( ! acc . canUnlimitedEntities ( ) ) {
									
									return new ManageResponsePacket ( ManageResponsePacket.ResponseType.OBJ, ManageResponsePacket.ResponseCode.EDIT, ManageResponsePacket.ResponseStatus.ERROR_ACCESS_DENIED ) ;

								}
								
							break;
							
							default: break ;
							
						}		
						
					}
					
				break;
				
		    	default: break;

			}
		
			if ( ! MineDonate . checkCatExists ( s . sid, message . catId ) ) {
				
				return new ManageResponsePacket ( ManageResponsePacket.ResponseType.OBJ, ManageResponsePacket.ResponseCode.EDIT, ManageResponsePacket.ResponseStatus.ERROR_CAT_NOTFOUND ) ;

			}
			
			if ( ! s . cats [ message . catId ] . merchExists ( message . merchId ) ) {
				
		        return new ManageResponsePacket ( ManageResponsePacket.ResponseType.OBJ, ManageResponsePacket.ResponseCode.EDIT, ManageResponsePacket.ResponseStatus.ERROR_ENTRY_NOTFOUND ) ;

			}
			
			ModShopManager . editShopEntryField ( serverPlayer, s, message . catId, message . merchId, message . type, message . name, message . data ) ;
			
	        return new ManageResponsePacket ( ManageResponsePacket.ResponseType.OBJ, ManageResponsePacket.ResponseCode.EDIT, ManageResponsePacket.ResponseStatus.OK ) ;

		} else {
		
	        return new ManageResponsePacket ( ManageResponsePacket.ResponseType.OBJ, ManageResponsePacket.ResponseCode.EDIT, ManageResponsePacket.ResponseStatus.ERROR_ACCESS_DENIED ) ;

		}
		
    }
    
}
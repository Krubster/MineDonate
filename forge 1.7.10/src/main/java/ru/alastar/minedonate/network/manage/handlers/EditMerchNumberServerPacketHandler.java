package ru.alastar.minedonate.network.manage.handlers;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import net.minecraft.entity.player.EntityPlayerMP;
import ru.alastar.minedonate.MineDonate;
import ru.alastar.minedonate.network.manage.packets.EditMerchNumberPacket;
import ru.alastar.minedonate.network.manage.packets.ManageResponsePacket;
import ru.alastar.minedonate.rtnl.ModManager;
import ru.alastar.minedonate.rtnl.common.Account;
import ru.alastar.minedonate.rtnl.common.Shop;

public class EditMerchNumberServerPacketHandler implements IMessageHandler < EditMerchNumberPacket, IMessage > {
	
    public EditMerchNumberServerPacketHandler ( ) {

    }

    @Override
    public IMessage onMessage ( EditMerchNumberPacket message, MessageContext ctx ) {
        
    	if ( ! MineDonate . checkShopAndLoad ( message . shopId ) ) {
    		
			return new ManageResponsePacket ( ManageResponsePacket.ResponseType.OBJ, ManageResponsePacket.ResponseCode.EDIT, ManageResponsePacket.ResponseStatus.ERROR_SHOP_NOTFOUND ) ;

    	}
    	
		EntityPlayerMP serverPlayer = ctx . getServerHandler ( ) . playerEntity ;
		
		Shop s = MineDonate . shops . get ( message . shopId ) ;
		
		Account acc = MineDonate . getAccount ( serverPlayer ) ;
		
		if ( acc . canEditShop ( s . owner ) ) {
			
			if ( s . isFreezed ) {

				return new ManageResponsePacket ( ManageResponsePacket.ResponseType.OBJ, ManageResponsePacket.ResponseCode.EDIT, ManageResponsePacket.ResponseStatus.ERROR_SHOP_FREEZED ) ;

			}
			
			if ( ! MineDonate . checkCatExists ( s . sid, message . catId ) ) {
				
				return new ManageResponsePacket ( ManageResponsePacket.ResponseType.OBJ, ManageResponsePacket.ResponseCode.EDIT, ManageResponsePacket.ResponseStatus.ERROR_CAT_NOTFOUND ) ;

			}
			
			if ( ! s . cats [ message . catId ] . merchExists ( message . merchId ) ) {
				
		        return new ManageResponsePacket ( ManageResponsePacket.ResponseType.OBJ, ManageResponsePacket.ResponseCode.EDIT, ManageResponsePacket.ResponseStatus.ERROR_ENTRY_NOTFOUND ) ;

			}
			
			if ( message . type == EditMerchNumberPacket . Type . LIMIT ) {
	
				switch ( s . cats [ message . catId ] . getCatType ( ) ) {
					
					case ITEMS :
						
						if ( ! acc . canUnlimitedItems ( ) ) {
							
							return new ManageResponsePacket ( ManageResponsePacket.ResponseType.OBJ, ManageResponsePacket.ResponseCode.EDIT, ManageResponsePacket.ResponseStatus.ERROR_ACCESS_DENIED ) ;

						}
						
					break ;
					
					case ENTITIES :
						
						if ( ! acc . canUnlimitedEntities ( ) ) {
							
							return new ManageResponsePacket ( ManageResponsePacket.ResponseType.OBJ, ManageResponsePacket.ResponseCode.EDIT, ManageResponsePacket.ResponseStatus.ERROR_ACCESS_DENIED ) ;

						}
						
					break ;
					
				}		
				
			}
			
			ModManager . editShopEntryNumber ( serverPlayer, s, message . catId, message . merchId, message . type, message . number ) ;
			
	        return new ManageResponsePacket ( ManageResponsePacket.ResponseType.OBJ, ManageResponsePacket.ResponseCode.EDIT, ManageResponsePacket.ResponseStatus.OK ) ;

		} else {
		
	        return new ManageResponsePacket ( ManageResponsePacket.ResponseType.OBJ, ManageResponsePacket.ResponseCode.EDIT, ManageResponsePacket.ResponseStatus.ERROR_ACCESS_DENIED ) ;

		}
		
    }
    
}
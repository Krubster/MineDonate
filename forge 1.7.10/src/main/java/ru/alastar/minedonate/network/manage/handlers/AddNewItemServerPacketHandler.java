package ru.alastar.minedonate.network.manage.handlers;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import net.minecraft.entity.player.EntityPlayerMP;
import ru.alastar.minedonate.MineDonate;
import ru.alastar.minedonate.network.manage.packets.AddNewItemPacket;
import ru.alastar.minedonate.network.manage.packets.ManageResponsePacket;
import ru.alastar.minedonate.rtnl.Account;
import ru.alastar.minedonate.rtnl.Manager;
import ru.alastar.minedonate.rtnl.Shop;

public class AddNewItemServerPacketHandler implements IMessageHandler < AddNewItemPacket, IMessage > {
	
    public AddNewItemServerPacketHandler ( ) {

    }

    @Override
    public IMessage onMessage ( AddNewItemPacket message, MessageContext ctx ) {
    	
    	if ( ! MineDonate . checkShopAndLoad ( message . shopId ) ) {
    		
			return new ManageResponsePacket ( ManageResponsePacket.ResponseType.ITEM, ManageResponsePacket.ResponseCode.ADD, ManageResponsePacket.ResponseStatus.ERROR_SHOP_NOTFOUND ) ;

    	}
    	
		EntityPlayerMP serverPlayer = ctx . getServerHandler ( ) . playerEntity ;
		
		Shop s = MineDonate . shops . get ( message . shopId ) ;
		
		Account acc = MineDonate . getAccount ( serverPlayer . getDisplayName ( ) . toLowerCase ( ) ) ;
		
		if ( acc . canEditShop ( s . owner ) ) {
			
			if ( s . isFreezed ) {

				return new ManageResponsePacket ( ManageResponsePacket.ResponseType.ITEM, ManageResponsePacket.ResponseCode.ADD, ManageResponsePacket.ResponseStatus.ERROR_SHOP_FREEZED ) ;

			}
			
			if ( message . name == null || message . name . length ( ) > 140 || acc . ms . currentItemStack == null || message . cost < 1 ) {

				return new ManageResponsePacket ( ManageResponsePacket.ResponseType.ITEM, ManageResponsePacket.ResponseCode.ADD, ManageResponsePacket.ResponseStatus.ERROR_UNKNOWN ) ;

			}
			
			if ( ! MineDonate . checkCatExists ( s . sid, message . catId ) ) {
				
				return new ManageResponsePacket ( ManageResponsePacket.ResponseType.ITEM, ManageResponsePacket.ResponseCode.ADD, ManageResponsePacket.ResponseStatus.ERROR_CAT_NOTFOUND ) ;

			}
			
			if ( message . limit == -1 && ! acc . canUnlimitedItems ( ) ) {
				
		        return new ManageResponsePacket ( ManageResponsePacket.ResponseType.ITEM, ManageResponsePacket.ResponseCode.ADD, ManageResponsePacket.ResponseStatus.ERROR_ACCESS_DENIED ) ;

			}
			
			/*
			if ( acc . ms . currentItemStack . stackSize != message . limit && ! acc . canUnlimitedItems ( ) ) {
				
		        return new ManageResponsePacket ( ManageResponsePacket.ResponseType.ITEM, ManageResponsePacket.ResponseCode.ADD, ManageResponsePacket.ResponseStatus.ERROR_ACCESS_DENIED ) ;

			}
			
			if ( message . limit > 0 ) {
			
				acc . ms . currentItemStack . stackSize = message . limit ;
		
			}*/
			
			Manager . addItemToShop ( acc, s, message . catId, message . limit, message . cost, message . name ) ;
			
	        return new ManageResponsePacket ( ManageResponsePacket.ResponseType.ITEM, ManageResponsePacket.ResponseCode.ADD, ManageResponsePacket.ResponseStatus.OK ) ;

		} else {
		
	        return new ManageResponsePacket ( ManageResponsePacket.ResponseType.ITEM, ManageResponsePacket.ResponseCode.ADD, ManageResponsePacket.ResponseStatus.ERROR_ACCESS_DENIED ) ;

		}
		        
    }
    
}
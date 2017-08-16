package ru.alastar.minedonate.network.manage.handlers;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

import net.minecraft.entity.player.EntityPlayerMP;

import ru.alastar.minedonate.MineDonate;

import ru.alastar.minedonate.network.manage.packets.EditMerchStringPacket;
import ru.alastar.minedonate.network.manage.packets.ManageResponsePacket;

import ru.alastar.minedonate.rtnl.Account;
import ru.alastar.minedonate.rtnl.Manager;
import ru.alastar.minedonate.rtnl.Shop;

public class EditMerchStringServerPacketHandler implements IMessageHandler < EditMerchStringPacket, IMessage > {
	
    public EditMerchStringServerPacketHandler ( ) {

    }

    @Override
    public IMessage onMessage ( EditMerchStringPacket message, MessageContext ctx ) {
    
    	if ( ! MineDonate . checkShopAndLoad ( message . shopId ) ) {
    		
			return new ManageResponsePacket ( ManageResponsePacket.ResponseType.ENTRIES, ManageResponsePacket.ResponseCode.EDIT, ManageResponsePacket.ResponseStatus.ERROR_SHOP_NOTFOUND ) ;

    	}
    	
		EntityPlayerMP serverPlayer = ctx . getServerHandler ( ) . playerEntity ;
		
		Shop s = MineDonate . shops . get ( message . shopId ) ;
		
		Account acc = MineDonate . getAccount ( serverPlayer . getDisplayName ( ) . toLowerCase ( ) ) ;
		
		if ( acc . canEditShop ( s . owner ) ) {
			
			if ( s . isFreezed ) {

				return new ManageResponsePacket ( ManageResponsePacket.ResponseType.ENTRIES, ManageResponsePacket.ResponseCode.EDIT, ManageResponsePacket.ResponseStatus.ERROR_SHOP_FREEZED ) ;

			}
			
			if ( message . str . length ( ) > 140 ) {

				return new ManageResponsePacket ( ManageResponsePacket.ResponseType.ENTRIES, ManageResponsePacket.ResponseCode.EDIT, ManageResponsePacket.ResponseStatus.ERROR_UNKNOWN ) ;

			}
			
			if ( ! MineDonate . checkCatExists ( s . sid, message . catId ) ) {
				
				return new ManageResponsePacket ( ManageResponsePacket.ResponseType.ENTRIES, ManageResponsePacket.ResponseCode.EDIT, ManageResponsePacket.ResponseStatus.ERROR_CAT_NOTFOUND ) ;

			}
			
			if ( ! s . cats [ message . catId ] . merchExists ( message . merchId ) ) {
				
		        return new ManageResponsePacket ( ManageResponsePacket.ResponseType.ENTRIES, ManageResponsePacket.ResponseCode.EDIT, ManageResponsePacket.ResponseStatus.ERROR_ENTRY_NOTFOUND ) ;

			}
			System.err.println( message . str);
			Manager . editShopEntryString ( serverPlayer, s, message . catId, message . merchId, message . type, message . str ) ;
			
	        return new ManageResponsePacket ( ManageResponsePacket.ResponseType.ENTRIES, ManageResponsePacket.ResponseCode.EDIT, ManageResponsePacket.ResponseStatus.OK ) ;

		} else {
		
	        return new ManageResponsePacket ( ManageResponsePacket.ResponseType.ENTRIES, ManageResponsePacket.ResponseCode.EDIT, ManageResponsePacket.ResponseStatus.ERROR_ACCESS_DENIED ) ;

		}
		
    }
    
}
package ru.alastar.minedonate.network.manage.handlers;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

import net.minecraft.entity.player.EntityPlayerMP;

import ru.alastar.minedonate.MineDonate;
import ru.alastar.minedonate.network.manage.packets.FreezeShopPacket;
import ru.alastar.minedonate.network.manage.packets.ManageResponsePacket;
import ru.alastar.minedonate.rtnl.Manager;
import ru.alastar.minedonate.rtnl.Shop;

public class FreezeShopServerPacketHandler implements IMessageHandler < FreezeShopPacket, IMessage > {
	
    public FreezeShopServerPacketHandler ( ) {

    }

    @Override
    public IMessage onMessage ( FreezeShopPacket message, MessageContext ctx ) {
    	
    	if ( ! MineDonate . checkShopAndLoad ( message . shopId ) ) {
    		
			return new ManageResponsePacket ( ManageResponsePacket.ResponseType.SHOP, ManageResponsePacket.ResponseCode.FREEZ, ManageResponsePacket.ResponseStatus.ERROR_SHOP_NOTFOUND ) ;

    	}
    	
		EntityPlayerMP serverPlayer = ctx . getServerHandler ( ) . playerEntity ;
		
		Shop s = MineDonate . shops . get ( message . shopId ) ;
		
		if ( MineDonate . getAccount ( serverPlayer . getDisplayName ( ) . toLowerCase ( ) ) . canFreezeShop ( s . owner ) ) {
			
			if ( s . isFreezed ) {

				return new ManageResponsePacket ( ManageResponsePacket.ResponseType.SHOP, ManageResponsePacket.ResponseCode.FREEZ, ManageResponsePacket.ResponseStatus.ERROR_SHOP_ALREADY_FREEZED ) ;

			}
			
			if ( message . reason == null || message . reason . isEmpty ( ) || message . reason . length ( ) > 140 ) {

				return new ManageResponsePacket ( ManageResponsePacket.ResponseType.SHOP, ManageResponsePacket.ResponseCode.FREEZ, ManageResponsePacket.ResponseStatus.ERROR_UNKNOWN ) ;

			}
			
			Manager . freezeShop ( s, serverPlayer . getDisplayName ( ), message . reason ) ;
			
	        return new ManageResponsePacket ( ManageResponsePacket.ResponseType.SHOP, ManageResponsePacket.ResponseCode.FREEZ, ManageResponsePacket.ResponseStatus.OK ) ;

		} else {
		
	        return new ManageResponsePacket ( ManageResponsePacket.ResponseType.SHOP, ManageResponsePacket.ResponseCode.FREEZ, ManageResponsePacket.ResponseStatus.ERROR_ACCESS_DENIED ) ;

		}
		
        //eturn new ManageResponsePacket ( ManageResponsePacket.ResponseType.SHOP, ManageResponsePacket.ResponseCode.UNFREEZ, ManageResponsePacket.ResponseStatus.OK ) ;
        
    }
    
}
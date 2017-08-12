package ru.alastar.minedonate.network.handlers.manage;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import net.minecraft.entity.player.EntityPlayerMP;
import ru.alastar.minedonate.MineDonate;
import ru.alastar.minedonate.network.packets.manage.DeleteShopPacket;
import ru.alastar.minedonate.network.packets.manage.ManageResponsePacket;
import ru.alastar.minedonate.rtnl.Manager;
import ru.alastar.minedonate.rtnl.Shop;

public class DeleteShopServerPacketHandler implements IMessageHandler < DeleteShopPacket, IMessage > {
	
    public DeleteShopServerPacketHandler ( ) {

    }

    @Override
    public IMessage onMessage ( DeleteShopPacket message, MessageContext ctx ) {
    	
    	if ( ! MineDonate . checkShopAndLoad ( message . shopId ) ) {
    		
			return new ManageResponsePacket ( ManageResponsePacket.ResponseType.SHOP, ManageResponsePacket.ResponseCode.REMOVE, ManageResponsePacket.ResponseStatus.ERROR_SHOP_NOTFOUND ) ;

    	}
    	
		EntityPlayerMP serverPlayer = ctx . getServerHandler ( ) . playerEntity ;
		
		Shop s = MineDonate . shops . get ( message . shopId ) ;
		
		if ( MineDonate . getAccount ( serverPlayer . getDisplayName ( ) . toLowerCase ( ) ) . canDeleteShop ( s . owner ) ) {
			
			if ( s . isFreezed ) {

				return new ManageResponsePacket ( ManageResponsePacket.ResponseType.SHOP, ManageResponsePacket.ResponseCode.REMOVE, ManageResponsePacket.ResponseStatus.ERROR_SHOP_FREEZED ) ;

			}
			
			Manager . deleteShop ( s ) ;
			
	        return new ManageResponsePacket ( ManageResponsePacket.ResponseType.SHOP, ManageResponsePacket.ResponseCode.REMOVE, ManageResponsePacket.ResponseStatus.OK ) ;

		} else {
		
	        return new ManageResponsePacket ( ManageResponsePacket.ResponseType.SHOP, ManageResponsePacket.ResponseCode.REMOVE, ManageResponsePacket.ResponseStatus.ERROR_ACCESS_DENIED ) ;

		}
		        
    }
    
}
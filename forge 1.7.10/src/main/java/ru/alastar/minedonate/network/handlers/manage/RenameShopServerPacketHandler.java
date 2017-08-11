package ru.alastar.minedonate.network.handlers.manage;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import net.minecraft.entity.player.EntityPlayerMP;
import ru.alastar.minedonate.network.packets.manage.RenameShopPacket;
import ru.alastar.minedonate.rtnl.Manager;
import ru.alastar.minedonate.rtnl.Shop;
import ru.alastar.minedonate.MineDonate;
import ru.alastar.minedonate.network.packets.manage.ManageResponsePacket;

public class RenameShopServerPacketHandler implements IMessageHandler < RenameShopPacket, IMessage > {
	
    public RenameShopServerPacketHandler ( ) {

    }

    @Override
    public IMessage onMessage ( RenameShopPacket message, MessageContext ctx ) {
    	
    	
    	if ( ! MineDonate . checkShopExists ( message . shopId ) ) {
    		
			return new ManageResponsePacket ( ManageResponsePacket.ResponseType.SHOP, ManageResponsePacket.ResponseCode.RENAME, ManageResponsePacket.ResponseStatus.ERROR_UNKNOWN ) ;

    	}
    	
		EntityPlayerMP serverPlayer = ctx . getServerHandler ( ) . playerEntity ;
		
		Shop s = MineDonate . shops . get ( message . shopId ) ;
		
		if ( MineDonate . getAccount ( serverPlayer . getDisplayName ( ) ) . canRenameShop ( s . owner ) ) {
			
			if ( message . name == null || message . name . length ( ) > 140 ) {

				return new ManageResponsePacket ( ManageResponsePacket.ResponseType.SHOP, ManageResponsePacket.ResponseCode.RENAME, ManageResponsePacket.ResponseStatus.ERROR_UNKNOWN ) ;

			}
			
			Manager . renameShop ( s, message . name ) ;
			
	        return new ManageResponsePacket ( ManageResponsePacket.ResponseType.SHOP, ManageResponsePacket.ResponseCode.RENAME, ManageResponsePacket.ResponseStatus.OK ) ;

		} else {
		
	        return new ManageResponsePacket ( ManageResponsePacket.ResponseType.SHOP, ManageResponsePacket.ResponseCode.RENAME, ManageResponsePacket.ResponseStatus.ERROR_ACCESS_DENIED ) ;

		}
		
        //return new ManageResponsePacket ( ManageResponsePacket.ResponseType.SHOP, ManageResponsePacket.ResponseCode.RENAME, ManageResponsePacket.ResponseStatus.OK ) ;
        
    }
    
}
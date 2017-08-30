package ru.alastar.minedonate.network.manage.handlers;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

import net.minecraft.entity.player.EntityPlayerMP;

import ru.alastar.minedonate.MineDonate;
import ru.alastar.minedonate.network.INetworkTask;
import ru.alastar.minedonate.network.manage.packets.DeleteShopMerchPacket;
import ru.alastar.minedonate.network.manage.packets.ManageResponsePacket;
import ru.alastar.minedonate.rtnl.ModManager;
import ru.alastar.minedonate.rtnl.ModNetworkTaskProcessor;
import ru.alastar.minedonate.rtnl.common.Account;
import ru.alastar.minedonate.rtnl.common.Shop;

public class DeleteShopMerchServerPacketHandler implements IMessageHandler < DeleteShopMerchPacket, IMessage >, INetworkTask < DeleteShopMerchPacket, IMessage > {
	
    public DeleteShopMerchServerPacketHandler ( ) {

    }

    @Override
    public IMessage onMessage ( DeleteShopMerchPacket message, MessageContext ctx ) {
    	
    	ModNetworkTaskProcessor . processTask ( ( INetworkTask ) this, message, ctx ) ;

    	return null ;
    	
    }
    
    @Override
    public IMessage onMessageProcess ( DeleteShopMerchPacket message, MessageContext ctx ) {
    	
    	if ( ! MineDonate . checkShopExists ( message . shopId ) ) {
    		
			return new ManageResponsePacket ( ManageResponsePacket.ResponseType.OBJ, ManageResponsePacket.ResponseCode.REMOVE, ManageResponsePacket.ResponseStatus.ERROR_SHOP_NOTFOUND ) ;

    	}
    	
		EntityPlayerMP serverPlayer = ctx . getServerHandler ( ) . playerEntity ;
		
		Shop s = MineDonate . shops . get ( message . shopId ) ;
		
		Account acc = MineDonate . getAccount ( serverPlayer ) ;
		
		if ( acc . canEditShop ( s . owner ) ) {
			
			if ( s . isFreezed ) {

				return new ManageResponsePacket ( ManageResponsePacket.ResponseType.OBJ, ManageResponsePacket.ResponseCode.REMOVE, ManageResponsePacket.ResponseStatus.ERROR_SHOP_FREEZED ) ;

			}
			
			if ( ! MineDonate . checkCatExists ( s . sid, message . catId ) ) {
				
				return new ManageResponsePacket ( ManageResponsePacket.ResponseType.OBJ, ManageResponsePacket.ResponseCode.REMOVE, ManageResponsePacket.ResponseStatus.ERROR_CAT_NOTFOUND ) ;

			}
			
			if ( ! s . cats [ message . catId ] . merchExists ( message . merchId ) ) {
				
		        return new ManageResponsePacket ( ManageResponsePacket.ResponseType.OBJ, ManageResponsePacket.ResponseCode.REMOVE, ManageResponsePacket.ResponseStatus.ERROR_ENTRY_NOTFOUND ) ;

			}
			
			ModManager . removeEntryFromShop ( serverPlayer, s, message . catId, message . merchId ) ;
			
	        return new ManageResponsePacket ( ManageResponsePacket.ResponseType.OBJ, ManageResponsePacket.ResponseCode.REMOVE, ManageResponsePacket.ResponseStatus.OK ) ;

		} else {
		
	        return new ManageResponsePacket ( ManageResponsePacket.ResponseType.OBJ, ManageResponsePacket.ResponseCode.REMOVE, ManageResponsePacket.ResponseStatus.ERROR_ACCESS_DENIED ) ;

		}
	
    }
    
}
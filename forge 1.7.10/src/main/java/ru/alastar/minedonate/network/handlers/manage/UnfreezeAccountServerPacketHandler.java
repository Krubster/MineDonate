package ru.alastar.minedonate.network.handlers.manage;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

import net.minecraft.entity.player.EntityPlayerMP;

import ru.alastar.minedonate.MineDonate;
import ru.alastar.minedonate.network.packets.manage.ManageResponsePacket;
import ru.alastar.minedonate.network.packets.manage.UnfreezeAccountPacket;
import ru.alastar.minedonate.rtnl.Account;
import ru.alastar.minedonate.rtnl.Manager;

public class UnfreezeAccountServerPacketHandler implements IMessageHandler < UnfreezeAccountPacket, IMessage > {
	
    public UnfreezeAccountServerPacketHandler ( ) {

    }

    @Override
    public IMessage onMessage ( UnfreezeAccountPacket message, MessageContext ctx ) {
    	
    	EntityPlayerMP serverPlayer = ctx . getServerHandler ( ) . playerEntity ;
    	
		if ( MineDonate . getAccount ( serverPlayer . getDisplayName ( ) . toLowerCase ( ) ) . canUnFreezeOtherAccount ( ) ) {
			
			Account acc = MineDonate . getAccount ( message . player . toLowerCase ( ) ) ;
			
			if ( acc == null ) {

				return new ManageResponsePacket ( ManageResponsePacket.ResponseType.ACCOUNT, ManageResponsePacket.ResponseCode.UNFREEZ, ManageResponsePacket.ResponseStatus.ERROR_UNKNOWN ) ;
	
			}
			
			if ( ! acc . freezedShopCreate ( ) ) {

				return new ManageResponsePacket ( ManageResponsePacket.ResponseType.ACCOUNT, ManageResponsePacket.ResponseCode.UNFREEZ, ManageResponsePacket.ResponseStatus.ERRROR_ACCOUNT_NO_FREEZED ) ;

			}
		
			Manager . unFreezePlayer ( acc, serverPlayer . getDisplayName ( ) ) ;
			
	        return new ManageResponsePacket ( ManageResponsePacket.ResponseType.ACCOUNT, ManageResponsePacket.ResponseCode.UNFREEZ, ManageResponsePacket.ResponseStatus.OK ) ;

		} else {
		
	        return new ManageResponsePacket ( ManageResponsePacket.ResponseType.ACCOUNT, ManageResponsePacket.ResponseCode.UNFREEZ, ManageResponsePacket.ResponseStatus.ERROR_ACCESS_DENIED ) ;

		}
		
    }
    
}
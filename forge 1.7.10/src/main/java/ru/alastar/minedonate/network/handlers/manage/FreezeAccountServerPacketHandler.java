package ru.alastar.minedonate.network.handlers.manage;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

import net.minecraft.entity.player.EntityPlayerMP;

import ru.alastar.minedonate.MineDonate;
import ru.alastar.minedonate.network.packets.manage.FreezeAccountPacket;
import ru.alastar.minedonate.network.packets.manage.ManageResponsePacket;
import ru.alastar.minedonate.rtnl.Account;
import ru.alastar.minedonate.rtnl.Manager;

public class FreezeAccountServerPacketHandler implements IMessageHandler < FreezeAccountPacket, IMessage > {
	
    public FreezeAccountServerPacketHandler ( ) {

    }

    @Override
    public IMessage onMessage ( FreezeAccountPacket message, MessageContext ctx ) {
    	
    	EntityPlayerMP serverPlayer = ctx . getServerHandler ( ) . playerEntity ;
    	
		if ( MineDonate . getAccount ( serverPlayer . getDisplayName ( ) . toLowerCase ( ) ) . canFreezeOtherAccount ( ) ) {
			
			Account acc = MineDonate . getAccount ( message . player . toLowerCase ( ) ) ;
			
			if ( acc == null ) {

				return new ManageResponsePacket ( ManageResponsePacket.ResponseType.ACCOUNT, ManageResponsePacket.ResponseCode.FREEZ, ManageResponsePacket.ResponseStatus.ERROR_UNKNOWN ) ;
	
			}
			
			if ( acc . freezedShopCreate ( ) ) {

				return new ManageResponsePacket ( ManageResponsePacket.ResponseType.ACCOUNT, ManageResponsePacket.ResponseCode.FREEZ, ManageResponsePacket.ResponseStatus.ERRROR_ACCOUNT_ALREADY_FREEZED ) ;

			}
			
			if ( message . reason == null || message . reason . isEmpty ( ) || message . reason . length ( ) > 140 ) {

				return new ManageResponsePacket ( ManageResponsePacket.ResponseType.ACCOUNT, ManageResponsePacket.ResponseCode.FREEZ, ManageResponsePacket.ResponseStatus.ERROR_UNKNOWN ) ;

			}
			
			Manager . freezePlayer ( acc, serverPlayer . getDisplayName ( ), message . reason ) ;
			
	        return new ManageResponsePacket ( ManageResponsePacket.ResponseType.ACCOUNT, ManageResponsePacket.ResponseCode.FREEZ, ManageResponsePacket.ResponseStatus.OK ) ;

		} else {
		
	        return new ManageResponsePacket ( ManageResponsePacket.ResponseType.ACCOUNT, ManageResponsePacket.ResponseCode.FREEZ, ManageResponsePacket.ResponseStatus.ERROR_ACCESS_DENIED ) ;

		}
		
    }
    
}
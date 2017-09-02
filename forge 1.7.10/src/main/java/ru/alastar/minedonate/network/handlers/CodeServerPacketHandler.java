package ru.alastar.minedonate.network.handlers;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

import net.minecraft.entity.player.EntityPlayerMP;

import ru.alastar.minedonate.MineDonate;
import ru.alastar.minedonate.Utils;
import ru.alastar.minedonate.network.INetworkTask;
import ru.alastar.minedonate.network.packets.AccountInfoPacket;
import ru.alastar.minedonate.network.packets.AddMerchPacket;
import ru.alastar.minedonate.network.packets.CodePacket;
import ru.alastar.minedonate.network.packets.SupportedFeaturesPacket;
import ru.alastar.minedonate.rtnl.ModNetworkRegistry;
import ru.alastar.minedonate.rtnl.ModNetworkTaskProcessor;
import ru.alastar.minedonate.rtnl.common.Account;

public class CodeServerPacketHandler implements IMessageHandler<CodePacket, IMessage>, INetworkTask < CodePacket, IMessage > {
    
	public CodeServerPacketHandler ( ) {

    }
    
    @Override 
    public IMessage onMessage ( CodePacket message, MessageContext ctx ) {
    	
    	ModNetworkTaskProcessor . processTask ( ( INetworkTask ) this, message, ctx ) ;

    	return null ;
    	
    }
    
    @Override
    public IMessage onMessageProcess ( CodePacket message, MessageContext ctx ) {

    	if ( message . code == CodePacket . Code . CLIENT_NEED_FULL_INFO ) {

            EntityPlayerMP serverPlayer = ctx . getServerHandler ( ) . playerEntity ;
            String userName = serverPlayer . getDisplayName ( ) . toLowerCase ( ) ;

            Account acc = MineDonate . getAccountWithRegister ( serverPlayer . getGameProfile ( ) . getId ( ) ) ;

            SupportedFeaturesPacket features_packet = new SupportedFeaturesPacket ( MineDonate . cfg ) ;

            ModNetworkRegistry . sendTo ( serverPlayer, features_packet ) ;

    		ModNetworkRegistry . sendTo ( serverPlayer, new AccountInfoPacket ( serverPlayer . getGameProfile ( ) . getId ( ) . toString ( ), userName, acc ) ) ;
                      
            for ( int j = 0; j < MineDonate . shops . get ( 0 ) . cats [ features_packet . firstCatId ] . getMerch ( ) . length ; ++ j ) {
            	
            	ModNetworkRegistry . sendTo ( serverPlayer, new AddMerchPacket ( MineDonate . shops . get ( 0 ) . cats [ features_packet . firstCatId ] . getMerch ( ) [ j ] ) ) ;

            }

            return new CodePacket ( CodePacket . Code . CLIENT_RECEIVED_FULL_INFO ) ;
			
    	}

        return null ;
        
    }
    
}
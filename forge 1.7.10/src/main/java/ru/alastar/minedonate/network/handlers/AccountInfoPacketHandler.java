package ru.alastar.minedonate.network.handlers;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import net.minecraft.client.Minecraft;
import ru.alastar.minedonate.Account;
import ru.alastar.minedonate.MineDonate;
import ru.alastar.minedonate.network.packets.AccountInfoPacket;

/**
 * Created by Alastar on 18.07.2017.
 */
public class AccountInfoPacketHandler implements IMessageHandler<AccountInfoPacket, IMessage> {

    public AccountInfoPacketHandler(){

    }

    @Override 
    public IMessage onMessage(AccountInfoPacket message, MessageContext ctx) {
    	
    	MineDonate . setAccount ( new Account ( Minecraft . getMinecraft ( ) . thePlayer . getDisplayName ( ) . toLowerCase ( ), message . permissions ) ) ;

    	for ( AccountInfoPacket . MoneySystem ms : message.mSystems ) {
    		
    		MineDonate . setMoney ( ms . type, ms . balance ) ;
    		
    	}
        
        return null ;
        
    }
}
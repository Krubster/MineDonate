package ru.alastar.minedonate.network.handlers;

import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import ru.alastar.minedonate.network.packets.MoneyChangedPacket;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import ru.alastar.minedonate.MineDonate;

public class MoneyChangedPacketHandler implements IMessageHandler<MoneyChangedPacket, IMessage> {

    public MoneyChangedPacketHandler(){

    }

    @Override 
    public IMessage onMessage(MoneyChangedPacket message, MessageContext ctx) {
    	
        MineDonate . setMoney ( message . moneyType, message . money ) ;
        
        return null ;
        
    }

}
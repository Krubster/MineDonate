package ru.alastar.minedonate.network.handlers;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import ru.alastar.minedonate.MineDonate;
import ru.alastar.minedonate.network.packets.AccountInfoPacket;

/**
 * Created by Alastar on 18.07.2017.
 */
public class AccountInfoPacketHandler     implements IMessageHandler<AccountInfoPacket, IMessage> {

    public AccountInfoPacketHandler(){

    }

    @Override public IMessage onMessage(AccountInfoPacket message, MessageContext ctx) {
    	
        MineDonate.SetMoney(message.money);
        
        // Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage(new ChatComponentText("Your money: " + message.money));

        return null;
        
    }
}
package ru.alastar.minedonate.network.handlers;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

import ru.alastar.minedonate.network.packets.BuyResponsePacket;


/**
 * Created by Alastar on 18.07.2017.
 */
public class BuyResponsePacketHandler implements IMessageHandler<BuyResponsePacket, IMessage> {
    public BuyResponsePacketHandler(){

    }

    @Override @SideOnly(Side.CLIENT)
    public IMessage onMessage ( BuyResponsePacket message, MessageContext ctx ) {

    	Minecraft . getMinecraft ( ) . thePlayer . addChatMessage ( new ChatComponentText ( EnumChatFormatting . AQUA + " [MineDonate] " + EnumChatFormatting.RESET + message . status ) ) ;

        return null ;
        
    }
    
}
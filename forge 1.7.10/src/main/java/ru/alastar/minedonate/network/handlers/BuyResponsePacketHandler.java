package ru.alastar.minedonate.network.handlers;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import ru.alastar.minedonate.network.packets.BuyResponsePacket;
import ru.alastar.minedonate.proxies.ClientProxy;

/**
 * Created by Alastar on 18.07.2017.
 */
public class BuyResponsePacketHandler implements IMessageHandler<BuyResponsePacket, IMessage> {
    public BuyResponsePacketHandler(){

    }

    @Override @SideOnly(Side.CLIENT)
    public IMessage onMessage(BuyResponsePacket message, MessageContext ctx) {
        byte resp = message.response;
        if (resp == 0) {
            Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage(new ChatComponentText("You're successfully bought a merch!"));
            ClientProxy.playCash();
        }
        else {
            Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage(new ChatComponentText("Buy failed!"));
        }
        return null;
    }
}
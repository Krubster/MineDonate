package ru.alastar.minedonate.network.handlers;

import net.minecraft.client.Minecraft;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import ru.alastar.minedonate.network.packets.BuyResponsePacket;


/**
 * Created by Alastar on 18.07.2017.
 */
public class BuyResponsePacketHandler implements IMessageHandler<BuyResponsePacket, IMessage> {
    public BuyResponsePacketHandler() {

    }

    @Override
    public IMessage onMessage(BuyResponsePacket message, MessageContext ctx) {

        Minecraft.getMinecraft().player.sendChatMessage(TextFormatting.AQUA + " [MineDonate] " + TextFormatting.RESET + message.status);

        return null;

    }

}
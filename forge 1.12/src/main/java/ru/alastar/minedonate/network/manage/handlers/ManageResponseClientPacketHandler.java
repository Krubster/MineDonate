package ru.alastar.minedonate.network.manage.handlers;

import net.minecraft.client.Minecraft;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import ru.alastar.minedonate.gui.ShopGUI;
import ru.alastar.minedonate.network.manage.packets.ManageResponsePacket;

public class ManageResponseClientPacketHandler implements IMessageHandler<ManageResponsePacket, IMessage> {

    public ManageResponseClientPacketHandler() {

    }

    @Override
    public IMessage onMessage(ManageResponsePacket message, MessageContext ctx) {

        ShopGUI.instance.setLoading(false);
        ShopGUI.instance.initGui();

        Minecraft.getMinecraft().player.sendChatMessage(TextFormatting.AQUA + " [MineDonate] " + TextFormatting.RESET + message.type + "> " + message.code + "> " + message.status);

        return null;

    }

}
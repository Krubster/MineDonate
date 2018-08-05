package ru.alastar.minedonate.network;


import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public interface INetworkTask<REQ extends IMessage, REPLY extends IMessage> {
    public REPLY onMessageProcess(REQ message, MessageContext ctx);
}
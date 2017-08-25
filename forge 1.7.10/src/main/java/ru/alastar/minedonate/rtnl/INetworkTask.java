package ru.alastar.minedonate.rtnl;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public interface INetworkTask<REQ extends IMessage, REPLY extends IMessage> {
    public REPLY onMessageProcess(REQ message, MessageContext ctx);
}
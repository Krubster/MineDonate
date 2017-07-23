package ru.alastar.minedonate.network.packets;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;

/**
 * Created by Alastar on 18.07.2017.
 */
public class BuyResponsePacket implements IMessage {

    public BuyResponsePacket(){}

    public byte response;  // 0 - success, 1 - no money

    public BuyResponsePacket(byte response) {
        this.response = response;
    }

    @Override public void toBytes(ByteBuf buf) {
        buf.writeByte(response);
    }

    @Override public void fromBytes(ByteBuf buf) {
        response = buf.readByte();
    }
}
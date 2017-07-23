package ru.alastar.minedonate.network.packets;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;

/**
 * Created by Alastar on 21.07.2017.
 */
public class RemoveMerchPacket implements IMessage {

    public RemoveMerchPacket(){}

    public int merch_id;
    public int category_id;

    public RemoveMerchPacket(int merch_id, int cat) {
        this.merch_id = merch_id;
        this.category_id =cat;
    }

    @Override public void toBytes(ByteBuf buf) {
        buf.writeByte(merch_id);
        buf.writeByte(category_id);
    }

    @Override public void fromBytes(ByteBuf buf) {
        merch_id = buf.readByte();
        category_id = buf.readByte();
    }
}
package ru.alastar.minedonate.network.packets;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;

/**
 * Created by Alastar on 21.07.2017.
 */
public class RemoveMerchPacket implements IMessage {

    public RemoveMerchPacket(){}
    public int shopId ;
    public int merch_id;
    public int category_id;

    public RemoveMerchPacket(int _shopId, int cat, int merch_id) {

    	this.shopId = _shopId;
        this.category_id =cat;
        this.merch_id = merch_id;

    }

    @Override 
    public void toBytes(ByteBuf buf) {

    	buf.writeInt(shopId);
        buf.writeInt(category_id);
        buf.writeInt(merch_id);

    }

    @Override 
    public void fromBytes(ByteBuf buf) {

    	shopId = buf.readInt();
        category_id = buf.readInt();
        merch_id = buf.readInt();

    }
}
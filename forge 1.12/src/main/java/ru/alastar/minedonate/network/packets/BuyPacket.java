package ru.alastar.minedonate.network.packets;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

/**
 * Created by Alastar on 18.07.2017.
 */
public class BuyPacket implements IMessage {
    public int shopId;
    public int merchId;
    public int category;
    public int amount = 1;
    // A default constructor is always required
    public BuyPacket() {
    }

    public BuyPacket(int _shopId, int merchId, int category, int amountToBuy) {
        this.shopId = _shopId;
        this.merchId = merchId;
        this.category = category;
        this.amount = amountToBuy;
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(shopId);
        buf.writeInt(merchId);
        buf.writeInt(category);
        buf.writeInt(amount);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        shopId = buf.readInt();
        merchId = buf.readInt();
        category = buf.readInt();
        amount = buf.readInt();
    }

}
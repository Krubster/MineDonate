package ru.alastar.minedonate.network.manage.packets;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class AppendEntryPacket implements IMessage {

    public int shopId, catId;

    public AppendEntryPacket() {
    }

    public AppendEntryPacket(int _shopId, int _catId) {

        shopId = _shopId;
        catId = _catId;

    }

    @Override
    public void toBytes(ByteBuf buf) {

        buf.writeInt(shopId);
        buf.writeInt(catId);

    }

    @Override
    public void fromBytes(ByteBuf buf) {

        shopId = buf.readInt();
        catId = buf.readInt();

    }

}
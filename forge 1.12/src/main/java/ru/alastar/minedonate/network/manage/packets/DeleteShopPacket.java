package ru.alastar.minedonate.network.manage.packets;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class DeleteShopPacket implements IMessage {

    public int shopId;

    public DeleteShopPacket() {
    }

    public DeleteShopPacket(int _shopId) {

        shopId = _shopId;

    }

    @Override
    public void toBytes(ByteBuf buf) {

        buf.writeInt(shopId);

    }

    @Override
    public void fromBytes(ByteBuf buf) {

        shopId = buf.readInt();

    }

}

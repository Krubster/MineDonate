package ru.alastar.minedonate.network.manage.packets;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class DeleteShopMerchPacket implements IMessage {

    public int shopId, catId, merchId;

    public DeleteShopMerchPacket() {
    }

    public DeleteShopMerchPacket(int _shopId, int _catId, int _merchId) {

        shopId = _shopId;
        catId = _catId;
        merchId = _merchId;

    }

    @Override
    public void toBytes(ByteBuf buf) {

        buf.writeInt(shopId);
        buf.writeInt(catId);
        buf.writeInt(merchId);

    }

    @Override
    public void fromBytes(ByteBuf buf) {

        shopId = buf.readInt();
        catId = buf.readInt();
        merchId = buf.readInt();

    }

}
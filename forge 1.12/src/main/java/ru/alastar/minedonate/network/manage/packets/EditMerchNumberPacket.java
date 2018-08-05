package ru.alastar.minedonate.network.manage.packets;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class EditMerchNumberPacket implements IMessage {

    public int shopId, catId, merchId, number;
    public Type type;

    public EditMerchNumberPacket() {
    }

    public EditMerchNumberPacket(int _shopId, int _catId, int _merchId, Type _type, int _number) {

        shopId = _shopId;
        catId = _catId;
        merchId = _merchId;
        type = _type;
        number = _number;

    }

    @Override
    public void toBytes(ByteBuf buf) {

        buf.writeInt(shopId);
        buf.writeInt(catId);
        buf.writeInt(merchId);
        buf.writeInt(type.ordinal());
        buf.writeInt(number);

    }

    @Override
    public void fromBytes(ByteBuf buf) {

        shopId = buf.readInt();
        catId = buf.readInt();
        merchId = buf.readInt();
        type = Type.values()[buf.readInt()];
        number = buf.readInt();

    }

    public enum Type {

        LIMIT, COST

    }

}

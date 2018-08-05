package ru.alastar.minedonate.network.packets;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class NeedShopCategoryPacket implements IMessage {

    public int shopId;
    public int cat;

    public NeedShopCategoryPacket() {

    }

    public NeedShopCategoryPacket(int _shopId, int _cat) {

        shopId = _shopId;
        cat = _cat;

    }

    @Override
    public void toBytes(ByteBuf buf) {

        buf.writeInt(shopId);
        buf.writeInt(cat);

    }

    @Override
    public void fromBytes(ByteBuf buf) {

        try {

            shopId = buf.readInt();
            cat = buf.readInt();

        } catch (Exception ex) {

            ex.printStackTrace();

        }


    }

}
package ru.alastar.minedonate.network.packets;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import ru.alastar.minedonate.MineDonate;
import ru.alastar.minedonate.merch.Merch;

/**
 * Created by Alastar on 18.07.2017.
 */
public class AddMerchPacket implements IMessage {

    public Merch info;
    public int m_category = 0;
    public int shopId = 0;
    public AddMerchPacket() {

    }

    public AddMerchPacket(Merch info) {

        this.info = info;
        this.m_category = info.getCategory();
        this.shopId = info.getShopId();

    }

    @Override
    public void toBytes(ByteBuf buf) {

        buf.writeInt(shopId);
        buf.writeInt(m_category);
        info.write(buf);

    }

    @Override
    public void fromBytes(ByteBuf buf) {

        shopId = buf.readInt();
        m_category = buf.readInt();
        info = MineDonate.shops.get(shopId).cats[m_category].constructMerch();
        info.read(buf);

    }
}
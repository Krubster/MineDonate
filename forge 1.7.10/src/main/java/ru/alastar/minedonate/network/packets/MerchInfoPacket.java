package ru.alastar.minedonate.network.packets;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;
import ru.alastar.minedonate.MineDonate;
import ru.alastar.minedonate.merch.IMerch;

/**
 * Created by Alastar on 23.07.2017.
 */
public class MerchInfoPacket  implements IMessage {

    public MerchInfoPacket() {

    }

    public IMerch info;
    public int m_category = 0;

    public MerchInfoPacket(IMerch info) {
        this.info = info;
        this.m_category = info.getCategory();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(m_category);
        info.write(buf);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        m_category = buf.readInt();
        info = MineDonate.m_Categories[m_category].constructMerch();
        info.read(buf);
    }
}
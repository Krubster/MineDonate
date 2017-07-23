package ru.alastar.minedonate.network.packets;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;
import ru.alastar.minedonate.MineDonate;
import ru.alastar.minedonate.merch.*;
import ru.alastar.minedonate.merch.info.EntityInfo;
import ru.alastar.minedonate.merch.info.ItemInfo;
import ru.alastar.minedonate.merch.info.PrivilegieInfo;
import ru.alastar.minedonate.merch.info.RegionInfo;

/**
 * Created by Alastar on 18.07.2017.
 */
public class AddMerchPacket implements IMessage {

    public AddMerchPacket() {

    }

    public IMerch info;
    public int m_category = 0;

    public AddMerchPacket(IMerch info) {
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
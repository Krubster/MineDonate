package ru.alastar.minedonate.network.packets;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;

/**
 * Created by Alastar on 02.08.2017.
 */
public class AllowAdminMode  implements IMessage {

    public AllowAdminMode() {

    }


    @Override
    public void toBytes(ByteBuf buf) {
    }

    @Override
    public void fromBytes(ByteBuf buf) {

    }
}
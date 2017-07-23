package ru.alastar.minedonate.network.packets;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;

/**
 * Created by Alastar on 18.07.2017.
 */
public class AccountInfoPacket  implements IMessage {
    // A default constructor is always required
    public AccountInfoPacket(){}

    public int money;

    public AccountInfoPacket(int money) {
        this.money = money;
    }

    @Override public void toBytes(ByteBuf buf) {
        buf.writeInt(money);
    }

    @Override public void fromBytes(ByteBuf buf) {
        money = buf.readInt();
    }
}
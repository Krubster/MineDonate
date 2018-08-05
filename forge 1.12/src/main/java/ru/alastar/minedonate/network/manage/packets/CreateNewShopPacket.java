package ru.alastar.minedonate.network.manage.packets;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import ru.alastar.minedonate.Utils;

public class CreateNewShopPacket implements IMessage {

    public String name;

    public CreateNewShopPacket() {
    }

    public CreateNewShopPacket(String _name) {

        name = _name;

    }

    @Override
    public void toBytes(ByteBuf buf) {

        try {

            Utils.netWriteString(buf, name);

        } catch (Exception ex) {

            ex.printStackTrace();

        }

    }

    @Override
    public void fromBytes(ByteBuf buf) {

        try {

            name = Utils.netReadString(buf);

        } catch (Exception ex) {

            ex.printStackTrace();

        }

    }

}
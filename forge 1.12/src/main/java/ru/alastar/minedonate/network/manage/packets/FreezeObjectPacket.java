package ru.alastar.minedonate.network.manage.packets;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import ru.alastar.minedonate.Utils;

public class FreezeObjectPacket implements IMessage {

    public int shopId;
    public String reason;
    public String account;
    public boolean bool;
    public Type type;

    public FreezeObjectPacket() {
    }

    public FreezeObjectPacket(int _shopId, String _reason, boolean _bool) {

        shopId = _shopId;
        reason = _reason;
        bool = _bool;

        type = Type.SHOP;

    }

    public FreezeObjectPacket(String _account, String _reason, boolean _bool) {

        account = _account;
        reason = _reason;
        bool = _bool;

        type = Type.ACCOUNT;

    }

    @Override
    public void toBytes(ByteBuf buf) {

        buf.writeInt(type.ordinal());

        try {

            if (type == Type.SHOP) {

                buf.writeInt(shopId);

            } else {

                Utils.netWriteString(buf, account);

            }

            buf.writeBoolean(reason != null);

            if (reason != null) {

                Utils.netWriteString(buf, reason);

            }

            buf.writeBoolean(bool);

        } catch (Exception ex) {

            ex.printStackTrace();

        }

    }

    @Override
    public void fromBytes(ByteBuf buf) {

        try {

            type = Type.values()[buf.readInt()];

            if (type == Type.SHOP) {

                shopId = buf.readInt();

            } else if (type == Type.ACCOUNT) {

                account = Utils.netReadString(buf);

            }

            if (buf.readBoolean()) {

                reason = Utils.netReadString(buf);

            }

            bool = buf.readBoolean();

        } catch (Exception ex) {

            ex.printStackTrace();

        }

    }

    public enum Type {

        ACCOUNT, SHOP

    }

}

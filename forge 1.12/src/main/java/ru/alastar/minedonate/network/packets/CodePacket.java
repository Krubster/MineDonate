package ru.alastar.minedonate.network.packets;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class CodePacket implements IMessage {

    public Code code;

    public CodePacket() {

    }

    // r - 0 - client need full info, 1 - client has received needed full info, 2 - client has received needed cat info
    public CodePacket(Code _code) {

        code = _code;

    }

    @Override
    public void toBytes(ByteBuf buf) {

        buf.writeByte(code.ordinal());

    }

    @Override
    public void fromBytes(ByteBuf buf) {

        try {

            code = Code.values()[buf.readByte()];

        } catch (Exception ex) {

            ex.printStackTrace();

        }

    }

    public enum Code {

        MOD_DISABLED, MOD_ENABLED, CLIENT_NEED_FULL_INFO, CLIENT_RECEIVED_FULL_INFO, CLIENT_RECEIVED_NEEDED_CAT_INFO, SERVER_ERROR_WAIT_OTHER_TASK

    }

}
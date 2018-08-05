package ru.alastar.minedonate.network.manage.packets;

import io.netty.buffer.ByteBuf;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class ItemMergedPacket implements IMessage {

    public ItemStack is;

    public ItemMergedPacket() {
    }

    public ItemMergedPacket(ItemStack _is) {

        is = _is;

    }

    @Override
    public void toBytes(ByteBuf buf) {

        try {

            buf.writeBoolean(is != null);

            if (is != null) {

                NBTTagCompound data = new NBTTagCompound();

                is.writeToNBT(data);

                ByteBufUtils.writeTag(buf, data);

                data = null;

            }

        } catch (Exception ex) {

            ex.printStackTrace();

        }

    }

    @Override
    public void fromBytes(ByteBuf buf) {

        try {

            if (buf.readBoolean()) {

                NBTTagCompound data = ByteBufUtils.readTag(buf);
                is = new ItemStack(data);

                data = null;

            }

        } catch (Exception ex) {

            ex.printStackTrace();

        }

    }

}
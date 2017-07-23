package ru.alastar.minedonate.merch.info;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import ru.alastar.minedonate.merch.IMerch;

import java.io.UnsupportedEncodingException;

/**
 * Created by Alastar on 18.07.2017.
 */
public class ItemInfo implements IMerch {

    @SideOnly(Side.CLIENT)
    public ItemStack m_stack;

    public int modified = 1; // multiplied count

    @SideOnly(Side.SERVER)
    public String m_cmds;

    public int merch_id;
    public int item_id;//graphic
    public int count;//amount
    public byte sub_id;//sub id
    public String info;//info
    public String name;//name
    public int cost;//cost for one lot
    public int limit; //limited?


    public ItemInfo(int mid, int id, int c, byte sid, int cos, String n, String inf, int lim) {
        this.merch_id = mid;
        this.item_id = id;
        this.count = c;
        this.sub_id = sid;
        this.cost = cos;
        this.name = n;
        this.info = inf;
        this.limit = lim;
    }

    @Override
    public int getId() {
        return merch_id;
    }

    @Override
    public boolean canBuy(EntityPlayerMP serverPlayer, int amount) {
        if(limit != -1 && limit < amount * count)
            return false;
        return true;
    }

    @Override
    public int getAmountToBuy() {
        return modified;
    }

    public ItemInfo() {

    }

    @Override
    public String getBoughtMessage() {
        return "bought item - " + name + "=" + item_id + "=" + count;
    }

    @Override
    public void write(ByteBuf buf) {
        buf.writeInt(merch_id);
        buf.writeInt(item_id);
        buf.writeInt(count);
        buf.writeInt(cost);
        buf.writeByte(sub_id);
        buf.writeInt(name.getBytes().length);
        buf.writeBytes(name.getBytes());
        buf.writeInt(info.getBytes().length);
        buf.writeBytes(info.getBytes());
        buf.writeInt(limit);
    }

    @Override
    public int getCategory() {
        return 0;
    }

    @Override
    public void read(ByteBuf buf) {
        merch_id = buf.readInt();
        item_id = buf.readInt();
        this.count = buf.readInt();
        this.cost = buf.readInt();
        this.sub_id = buf.readByte();
        int name_length = buf.readInt();
        try {
            this.name = new String(buf.readBytes(name_length).array(), "UTF-8");
            int info_length = buf.readInt();
            this.info = new String(buf.readBytes(info_length).array(), "UTF-8");
            Object obj = Item.itemRegistry.getObjectById(item_id);
            if (obj instanceof Block) {
                m_stack = new ItemStack((Block) obj, count, sub_id);
            } else if (obj instanceof Item) {
                m_stack = new ItemStack((Item) obj, count, sub_id);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        limit = buf.readInt();
    }
    @Override
    public void setId(int i) {
        merch_id = i;
    }
    @Override
    public int getCost() {
        return cost;
    }
}

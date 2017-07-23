package ru.alastar.minedonate.merch.info;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import ru.alastar.minedonate.merch.IMerch;

import java.io.UnsupportedEncodingException;

/**
 * Created by Alastar on 19.07.2017.
 */
public class PrivilegieInfo implements IMerch {
    public String picture_url;
    public String name;
    public String description;
    public int cost;
    public int merch_id;
    public long seconds;

    public PrivilegieInfo(int merch_id, String n, String desc, String picture_url, int cost, long seconds) {
        this.merch_id = merch_id;
        this.name = n;
        this.description = desc;
        this.cost = cost;
        this.picture_url = picture_url;
        this.seconds =seconds;
    }

    public PrivilegieInfo() {

    }
    @Override
    public void setId(int i) {
        merch_id = i;
    }
    @Override
    public int getId() {
        return merch_id;
    }

    @Override
    public boolean canBuy(EntityPlayerMP serverPlayer, int amount) {
        return true;
    }

    @Override
    public int getAmountToBuy() {
        return 1;
    }

    @Override
    public void write(ByteBuf buf) {
        buf.writeInt(merch_id);
        buf.writeInt(cost);
        buf.writeInt(name.getBytes().length);
        buf.writeBytes(name.getBytes());
        buf.writeInt(description.getBytes().length);
        buf.writeBytes(description.getBytes());
        buf.writeInt(picture_url.getBytes().length);
        buf.writeBytes(picture_url.getBytes());
    }

    @Override
    public String getBoughtMessage() {
        return "bought privelegie -" + name;
    }

    @Override
    public int getCost() {
        return cost;
    }

    @Override
    public int getCategory() {
        return 1;
    }

    @Override
    public void read(ByteBuf buf) {
        merch_id = buf.readInt();
        cost = buf.readInt();
        int name_length = buf.readInt();
        this.name = new String(buf.readBytes(name_length).array());
        int desc_length = buf.readInt();
        try {
            this.description = new String(buf.readBytes(desc_length).array(), "UTF-8");
            desc_length = buf.readInt();
            this.picture_url = new String(buf.readBytes(desc_length).array(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public long getTimeInSeconds() {
        return seconds;
    }
}

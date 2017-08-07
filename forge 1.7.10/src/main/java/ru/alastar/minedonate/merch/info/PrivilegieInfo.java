package ru.alastar.minedonate.merch.info;

import io.netty.buffer.ByteBuf;
import ru.alastar.minedonate.merch.Merch;

import java.io.UnsupportedEncodingException;

/**
 * Created by Alastar on 19.07.2017.
 */
public class PrivilegieInfo extends Merch {
    public String picture_url;
    public String name;
    public String description;
    public String[] worlds;
    public long seconds;     //length = 0 - all worlds, otherwise 

    public PrivilegieInfo(int _shopId, int _catId, int merch_id, String n, String desc, String picture_url, int cost, long seconds, String worlds) {
        super(_shopId, _catId, merch_id);
        this.name = n;
        this.description = desc;
        this.cost = cost;
        this.picture_url = picture_url;
        this.seconds = seconds;
        if (worlds != "*")
            this.worlds = worlds.split(";");
        else
            this.worlds = new String[0];
    }

    public PrivilegieInfo() {
        super();
    }

    @Override
    public int getAmountToBuy() {
        return 1;
    }

    @Override
    public void write(ByteBuf buf) {
        super.write(buf);
        buf.writeInt(cost);
        buf.writeInt(name.getBytes().length);
        buf.writeBytes(name.getBytes());
        buf.writeInt(description.getBytes().length);
        buf.writeBytes(description.getBytes());
        buf.writeInt(picture_url.getBytes().length);
        buf.writeBytes(picture_url.getBytes());
        buf.writeInt(worlds.length);
        for (int i = 0; i < worlds.length; ++i) {
            buf.writeInt(worlds[i].getBytes().length);
            buf.writeBytes(worlds[i].getBytes());
        }
    }

    @Override
    public String getBoughtMessage() {
        return "bought privelegie -" + name;
    }

    @Override
    public int getCategory() {
        return 1;
    }

    @Override
    public void read(ByteBuf buf) {
        super.read(buf);
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
        int worlds_len = buf.readInt();
        if (worlds_len > 0) {
            worlds = new String[worlds_len];
            try {
                for (int i = 0; i < worlds_len; ++i) {
                    desc_length = buf.readInt();
                    worlds[i] = new String(buf.readBytes(desc_length).array(), "UTF-8");
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        } else {
            this.worlds = new String[0];
        }
    }

    public long getTimeInSeconds() {
        return seconds;
    }
}
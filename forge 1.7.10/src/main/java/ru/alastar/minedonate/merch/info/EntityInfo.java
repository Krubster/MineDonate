package ru.alastar.minedonate.merch.info;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import ru.alastar.minedonate.merch.IMerch;

import java.io.UnsupportedEncodingException;

/**
 * Created by Alastar on 21.07.2017.
 */
public class EntityInfo implements IMerch {

    public int merch_id;
    public int cost;
    public String classpath;
    public String name;

    public EntityInfo(int merch_id, int cost, String classpath, String name) {
        this.merch_id = merch_id;
        this.cost = cost;
        this.classpath = classpath;
        this.name = name;
    }

    public EntityInfo() {
        
    }

    @Override
    public int getCategory() {
        return 3;
    }

    @Override
    public void read(ByteBuf buf) {
        merch_id = buf.readInt();
        cost = buf.readInt();
        int info_length = buf.readInt();
        try {
            this.name = new String(buf.readBytes(info_length).array(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void write(ByteBuf buf) {
        buf.writeInt(merch_id);
        buf.writeInt(cost);
        buf.writeInt(name.getBytes().length);
        buf.writeBytes(name.getBytes());
    }

    @Override
    public String getBoughtMessage() {
        return "bought entity " + name;
    }

    @Override
    public int getCost() {
        return cost;
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
    public void setId(int i) {
        merch_id = i;
    }
}

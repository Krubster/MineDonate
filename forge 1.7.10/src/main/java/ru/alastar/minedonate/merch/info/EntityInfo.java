package ru.alastar.minedonate.merch.info;

import cpw.mods.fml.common.network.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import ru.alastar.minedonate.merch.Merch;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Blob;
import java.sql.SQLException;

/**
 * Created by Alastar on 21.07.2017.
 */
public class EntityInfo extends Merch {
    
    public String classpath;
    public String name;
    public NBTTagCompound entity_data;

    public EntityInfo(int _shopId, int _catId, int merch_id, int cost, Blob data, String name) {
    	this.shopId = _shopId;
    	this.catId = _catId;
        this.merch_id = merch_id;
        this.cost = cost;
        ByteBuf buf = Unpooled.buffer();
        try {
            buf.writeBytes(data.getBinaryStream(), (int)data.length());
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ByteBufUtils.readItemStack(buf).writeToNBT(entity_data);
        this.name = name;
    }
    public EntityInfo(int _shopId, int _catId, int merch_id, int cost, Entity entity, String name) {
        this.merch_id = merch_id;
        this.cost = cost;
        entity.writeToNBT(entity_data);
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
    	shopId = buf.readInt();
    	catId = buf.readInt();
        merch_id = buf.readInt();
        cost = buf.readInt();
        int info_length = buf.readInt();
        try {
            this.name = new String(buf.readBytes(info_length).array(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        entity_data = ByteBufUtils.readTag(buf);
    }

    @Override
    public void write(ByteBuf buf) {
    	buf.writeInt(shopId);
        buf.writeInt(catId);
        buf.writeInt(merch_id);
        buf.writeInt(cost);
        buf.writeInt(name.getBytes().length);
        buf.writeBytes(name.getBytes());
        ByteBufUtils.writeTag(buf, entity_data);
    }

    @Override
    public String getBoughtMessage() {
        return "bought entity " + name;
    }



    @Override
    public int getAmountToBuy() {
        return 1;
    }
    
}

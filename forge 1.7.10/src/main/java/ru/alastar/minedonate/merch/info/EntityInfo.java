package ru.alastar.minedonate.merch.info;

import cpw.mods.fml.common.network.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import ru.alastar.minedonate.merch.Merch;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.sql.Blob;
import java.sql.SQLException;

/**
 * Created by Alastar on 21.07.2017.
 */
public class EntityInfo extends Merch {

    public String classpath;
    public String name;
    public NBTTagCompound entity_data;
    public int limit;
    public EntityLivingBase entity;

    public EntityInfo(int _shopId, int _catId, int merch_id, int cost, Blob data, String name, int lim) {

        super(_shopId, _catId, merch_id);

        this.cost = cost;

        ByteBuf buf = Unpooled.buffer();
        try {
            buf.writeBytes(data.getBinaryStream(), (int) data.length());
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        entity_data = ByteBufUtils.readTag(buf);
        this.classpath = entity_data.getString("classpath");
        this.name = name;
        this.limit = lim;

    }

    public EntityInfo(int _shopId, int _catId, int merch_id, int cost, Entity entity, String name, int limit) {

        super(_shopId, _catId, merch_id);

        this.cost = cost;
        this.entity_data = new NBTTagCompound();
        entity.writeToNBT(entity_data);
        this.name = name;
        this.classpath = entity.getClass().getName();
        entity_data.setString("classpath", this.classpath);
        this.limit = limit;
    }

    public EntityInfo() {
        super();
    }

    @Override
    public int getCategory() {
        return 3;
    }

    @Override
    public void read(ByteBuf buf) {
        super.read(buf);
        cost = buf.readInt();
        int info_length = buf.readInt();
        try {
            this.name = new String(buf.readBytes(info_length).array(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        entity_data = ByteBufUtils.readTag(buf);
        limit = buf.readInt();
        int class_length = buf.readInt();
        try {
            this.classpath = new String(buf.readBytes(class_length).array(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        try {
            entity = (EntityLivingBase) Class.forName(classpath).getDeclaredConstructor(net.minecraft.world.World.class).newInstance(Minecraft.getMinecraft().theWorld);
            entity.readFromNBT(entity_data);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void write(ByteBuf buf) {
        super.write(buf);
        buf.writeInt(cost);
        buf.writeInt(name.getBytes().length);
        buf.writeBytes(name.getBytes());
        ByteBufUtils.writeTag(buf, entity_data);
        buf.writeInt(limit);
        buf.writeInt(classpath.getBytes().length);
        buf.writeBytes(classpath.getBytes());
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

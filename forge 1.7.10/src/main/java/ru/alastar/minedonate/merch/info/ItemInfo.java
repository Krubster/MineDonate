package ru.alastar.minedonate.merch.info;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import ru.alastar.minedonate.merch.Merch;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;

/**
 * Created by Alastar on 18.07.2017.
 */
public class ItemInfo extends Merch {

    @SideOnly(Side.CLIENT)
    public ItemStack m_stack;

    public int modified = 1; // multiplied count

    @SideOnly(Side.SERVER)
    public String m_cmds;

    public NBTTagCompound stack_data;//stack data
    public String info;//info
    public String name;//name
    public int limit; //limited?


    public ItemInfo() {

    }

    @Override
    public boolean canBuy(EntityPlayerMP serverPlayer, int amount) {
        if(limit != -1 && limit < amount * stack_data.getInteger("Count"))
            return false;
        return true;
    }

    @Override
    public int getAmountToBuy() {
        return modified;
    }

    public ItemInfo(int _shopId, int _catId, int mid, int cos, String n, String inf, int lim, java.sql.Blob data) {
    	this.shopId = _shopId;
    	this.catId = _catId;
        this.merch_id = mid;
        this.cost = cos;
        this.name = n;
        this.info = inf;
        this.limit = lim;
        ByteBuf buf = Unpooled.buffer();
        try {
            buf.writeBytes(data.getBinaryStream(), (int)data.length());
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        stack_data = ByteBufUtils.readTag(buf);
    }
    public ItemInfo(int _shopId, int _catId, int mid, int cos, String n, String inf, int lim, ItemStack data) {
    	this.shopId = _shopId;
    	this.catId = _catId;
        this.merch_id = mid;
        this.cost = cos;
        this.name = n;
        this.info = inf;
        this.limit = lim;
        data.writeToNBT(stack_data);
    }
    @Override
    public String getBoughtMessage() {
        return "bought item - " + name + "=" + stack_data.getInteger("Id") + "=" + stack_data.getInteger("Count");
    }

    @Override
    public void write(ByteBuf buf) {
    	super.write(buf);
        buf.writeInt(cost);
        buf.writeInt(name.getBytes().length);
        buf.writeBytes(name.getBytes());
        buf.writeInt(info.getBytes().length);
        buf.writeBytes(info.getBytes());
        buf.writeInt(limit);
        ByteBufUtils.writeTag(buf, stack_data);
    }

    @Override
    public int getCategory() {
        return 0;
    }

    @Override
    public void read(ByteBuf buf) {
    	super.read(buf);
        this.cost = buf.readInt();
        int name_length = buf.readInt();
        try {
            this.name = new String(buf.readBytes(name_length).array(), "UTF-8");
            int info_length = buf.readInt();
            this.info = new String(buf.readBytes(info_length).array(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        limit = buf.readInt();
        stack_data = ByteBufUtils.readTag(buf);
        m_stack = ItemStack.loadItemStackFromNBT(stack_data);
        
        if ( name == null || name . isEmpty ( ) ) {
        	
        	name = m_stack . getDisplayName ( ) ;
        	
        }
        
    }
	
}

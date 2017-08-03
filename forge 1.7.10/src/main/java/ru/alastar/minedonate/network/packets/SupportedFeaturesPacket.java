package ru.alastar.minedonate.network.packets;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;

/**
 * Created by Alastar on 20.07.2017.
 */
public class SupportedFeaturesPacket implements IMessage {

    public SupportedFeaturesPacket(){}

    public boolean items;
    public boolean privelegies;
    public boolean regions;
    public boolean entities;
    public boolean userShops;

    public SupportedFeaturesPacket(boolean _items, boolean _privelegies, boolean _regions, boolean _entities, boolean _userShops) {

    	this.items = _items;
        this.privelegies = _privelegies;
        this.regions = _regions;
        this.entities = _entities;
        this.userShops = _userShops;
        
    }

    @Override 
    public void toBytes(ByteBuf buf) {
    	
        buf.writeBoolean(items);
        buf.writeBoolean(privelegies);
        buf.writeBoolean(regions);
        buf.writeBoolean(entities);
        buf.writeBoolean(userShops);

    }

    @Override 
    public void fromBytes(ByteBuf buf) {
    	
        this.items = buf.readBoolean();
        this.privelegies = buf.readBoolean();
        this.regions = buf.readBoolean();
        this.entities = buf.readBoolean();
        this.userShops = buf.readBoolean();

    }
}
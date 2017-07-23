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

    public SupportedFeaturesPacket(boolean i, boolean p, boolean r, boolean e) {
        this.items = i;
        this.privelegies = p;
        this.regions = r;
        this.entities = e;
    }

    @Override public void toBytes(ByteBuf buf) {
        buf.writeBoolean(items);
        buf.writeBoolean(privelegies);
        buf.writeBoolean(regions);
        buf.writeBoolean(entities);

    }

    @Override public void fromBytes(ByteBuf buf) {
        this.items = buf.readBoolean();
        this.privelegies = buf.readBoolean();
        this.regions = buf.readBoolean();
        this.entities = buf.readBoolean();
    }
}
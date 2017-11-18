package ru.alastar.minedonate.network.packets;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;
import ru.alastar.minedonate.MineDonate;
import ru.alastar.minedonate.merch.Merch;

/**
 * Created by Alastar on 23.07.2017.
 */
public class MerchInfoPacket  implements IMessage {

    public MerchInfoPacket() {

    }

    public Merch info;
    public int shopId = 0;
    public int catId = 0;

    public MerchInfoPacket(Merch info) {
    	
        this.info = info;
        this.shopId = info.getShopId();
        this.catId = info.getCategory();
        
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(shopId);
        buf.writeInt(catId);
        info.write(buf);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
    	
    	shopId = buf.readInt();
    	catId = buf.readInt();
        
        if ( MineDonate . checkCatExists ( shopId, catId ) ) {

        	info = MineDonate.shops.get(shopId).cats[catId].constructMerch();
        	info.read(buf); 
        	
        }
        
    }
}
package ru.alastar.minedonate.merch.info;

import java.io.UnsupportedEncodingException;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import ru.alastar.minedonate.merch.Merch;

public class UserShopInfo extends Merch {

    public int shopId;
    public String owner;
    public String name;
    public boolean isFreezed ;

    public UserShopInfo(int _merch_id, int _shopId, String _owner, String _name, boolean _isFreezed ) {
    	
        this.merch_id = _merch_id;
        this.shopId = _shopId;
        this.owner = _owner;
        this.name = _name;
        this.isFreezed = _isFreezed ;
        
    }	

    public UserShopInfo() {
        
    }

    @Override
    public int getCategory() {
        return 4;
    }

    @Override
    public void read(ByteBuf buf) {
        merch_id = buf.readInt();
        shopId = buf.readInt();
        
        int owner_length = buf.readInt();
        try {
            this.owner = new String(buf.readBytes(owner_length).array(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        
        int name_length = buf.readInt();
        try {
            this.name = new String(buf.readBytes(name_length).array(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        
        isFreezed = buf.readBoolean();
        
    }

    @Override
    public void write(ByteBuf buf) {
    	
        buf.writeInt(merch_id);
        buf.writeInt(shopId);
        
        buf.writeInt(owner.getBytes().length);
        buf.writeBytes(owner.getBytes());
        
        buf.writeInt(name.getBytes().length);
        buf.writeBytes(name.getBytes());
        
        buf . writeBoolean ( isFreezed ) ;
        
    }

    @Override
    public String getBoughtMessage() {
        return "bought users" ; // bought entity " + name;
    }

    @Override
    public int getCost() {
        return 0;
    }

    @Override
    public int getId() {
        return merch_id;
    }

    @Override
    public boolean canBuy(EntityPlayerMP serverPlayer, int amount) {
        return false;
    }

    @Override
    public int getAmountToBuy() {
        return 0;
    }

    @Override
    public void setId(int i) {
        merch_id = i;
    }
        
	@Override
	public int getShopId ( ) {
		
		return 0 ;
		
	}

	@Override
	public void setShopId ( int i ) {
				
	}
    
}
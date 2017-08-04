package ru.alastar.minedonate.merch;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import ru.alastar.minedonate.MineDonate;

/**
 * Created by Alastar on 20.07.2017.
 */
public abstract class Merch {

    public int cost;
    public int merch_id;
    public int shopId;
    public int catId;
	public int subCatId ;

	public Merch ( ) { }

	public Merch ( int _shopId, int _catId, int _merch_id ) {
		
		this.shopId = _shopId;
    	this.catId = _catId;
        this.merch_id = _merch_id;
        
	}
	
    public int getCategory() {
        return -1;
    }

    public void read(ByteBuf buf) {
    	shopId = buf.readInt();
    	catId = buf.readInt();
    	subCatId = buf.readInt();
        merch_id = buf.readInt();
    }

    public void write(ByteBuf buf) {
    	buf.writeInt(shopId);
        buf.writeInt(catId);
        buf.writeInt(subCatId);
        buf.writeInt(merch_id);
    }

    public String getBoughtMessage() {
        return "";
    }

    public int getCost() {
        return cost;
    }

    public int getId() {
        return merch_id;
    }

    public boolean canBuy(EntityPlayerMP serverPlayer, int amount) {
        return true;
    }

    public int getAmountToBuy() {
        return 1;
    }

    public void setId(int i) {
        merch_id = i;
    }


    public int getShopId() {
        return shopId;
    }

    public void setShopId(int i) {
    	shopId = i;
    }
    
	public String getMoneyType ( ) {
		
		return MineDonate . getMoneyType ( shopId, catId ) ;

	}

	public int withdrawMoney ( String buyer, int amount ) {
		
		return MineDonate . getMoneyProcessor ( getMoneyType ( ) ) . process ( this, buyer, amount ) ;
		
	}
	
}

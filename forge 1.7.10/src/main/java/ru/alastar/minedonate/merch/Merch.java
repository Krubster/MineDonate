package ru.alastar.minedonate.merch;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import ru.alastar.minedonate.MineDonate;

/**
 * Created by Alastar on 20.07.2017.
 */
public abstract class Merch {

    public int cost ;
    public int merchId ;
    public int shopId ;
    public int catId ;
	public int rating ;
	
	public Merch ( ) { }

	public Merch ( int _shopId, int _catId, int _merchId, int _rating ) {
		
		shopId = _shopId ;
    	catId = _catId ;
        merchId = _merchId ;
        rating = _rating ;
        
	}
	
    public int getCategory ( ) {
        
    	return -1 ;
        
    }

    public void read ( ByteBuf buf ) {
    	
    	shopId = buf . readInt ( ) ;
    	catId = buf . readInt ( ) ;
    	rating = buf . readInt ( ) ;
    	merchId = buf . readInt ( ) ;
    	
    }

    public void write ( ByteBuf buf ) {
    	
    	buf . writeInt ( shopId ) ;
        buf . writeInt ( catId ) ;
        buf . writeInt ( rating ) ;
        buf . writeInt ( merchId ) ;
        
    }

    public Merch copy ( ) {
    	
    	return null ;
    	
    }
    
    public String getBoughtMessage ( ) {
    	
        return "" ;
        
    }

    public int getCost() {
        return cost;
    }

    public int getId ( ) {
        
    	return merchId ;
        
    }

    public boolean canBuy(EntityPlayerMP serverPlayer, int amount) {
        
    	return true;
        
    }

    public int getAmountToBuy ( ) {
        
    	return 1 ;
        
    }

    public void setId ( int _merchId ) {
    	
    	merchId = _merchId;
    	
    }


    public int getShopId ( ) {
        
    	return shopId;
        
    }

    public void setShopId ( int _shopId ) {
    	
    	shopId = _shopId ;
    	
    }
    
    public int getRating ( ) {
    	
    	return rating ;
    	
    }
    
	public String getMoneyType ( ) {
		
		return MineDonate . getMoneyType ( shopId, catId ) ;

	}

	public int withdrawMoney ( String buyer, int amount ) {

        return MineDonate.getMoneyProcessor(getMoneyType()).process(this, MineDonate.getUUIDFromName(buyer), amount);

    }
	
	@SideOnly(Side.CLIENT)
	public String getSearchValue ( ) {
		
		return "" ;
		
	}
	
}

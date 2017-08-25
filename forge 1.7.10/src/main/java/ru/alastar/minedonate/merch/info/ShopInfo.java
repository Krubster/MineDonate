package ru.alastar.minedonate.merch.info;

import java.util.UUID;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.EnumChatFormatting;
import ru.alastar.minedonate.merch.Merch;
import ru.alastar.minedonate.rtnl.Utils;

public class ShopInfo extends Merch {
	
    public int merch_id ;
    public int shopId ;
    public String owner ;
    public String ownerName ;
    public String name ;
    
    public boolean isFreezed ;
    public String freezer ;
    public String freezReason ;
    public boolean canVisibleFreezedText ;
    
    public String moneyType ;
    
    public ShopInfo ( int _merch_id, int _shopId, int _rating, String _owner, String _ownerName, String _name, boolean _isFreezed, String _freezer, String _freezReason, boolean _canVisibleFreezedText, String _moneyType ) {
    	
       	super ( ) ;
       	
        this.merch_id = _merch_id;
    	this.catId = 4;
    	this.rating = _rating ;
    	
       	shopId = _shopId;
       	
        this . owner = _owner ;
        this . ownerName = _ownerName ;
        this . name = _name ;
        
        this . isFreezed = _isFreezed ;
        this . freezer = _freezer ;
        this . freezReason = _freezReason ;
        this . canVisibleFreezedText = _canVisibleFreezedText ;
        
        this . moneyType = _moneyType ;
        
    }	

    public ShopInfo ( ) {
       	
    	super();
       	
    }

    @Override
    public int getCategory ( ) {
    	
        return 4 ;
        
    }

    @Override
    public void read ( ByteBuf buf ) {
    	
        try {
        	
        	merch_id = buf . readInt ( ) ;
            shopId = buf . readInt ( ) ;
            
            owner = Utils . netReadString ( buf ) ;      
            ownerName = Utils . netReadString ( buf ) ;   
            name = Utils . netReadString ( buf ) ;

            isFreezed = buf.readBoolean();
            canVisibleFreezedText = buf . readBoolean ( ) ;

            if ( isFreezed && canVisibleFreezedText ) {
                
            	freezer = Utils . netReadString ( buf ) ;          
            	freezReason = Utils . netReadString ( buf ) ;
                
            }
                        
            moneyType = Utils . netReadString ( buf ) ;
            
        } catch ( Exception ex ) {
        	
        	ex . printStackTrace ( ) ; 
        	
        }
        
        if ( isFreezed ) {
        	
        	rating = -1 ;
        	
        }
        
    }

    
    @Override
    public void write ( ByteBuf buf ) {
    	
        try {
        	
	        buf . writeInt ( merch_id ) ;
	        buf . writeInt ( shopId ) ;
	        
	        Utils . netWriteString ( buf, owner ) ;
	        Utils . netWriteString ( buf, ownerName ) ;
	        Utils . netWriteString ( buf, name ) ;
	
	        buf . writeBoolean ( isFreezed ) ;
	        buf . writeBoolean ( canVisibleFreezedText ) ;
	
	        if ( isFreezed && canVisibleFreezedText ) {
	        	
	        	Utils . netWriteString ( buf, freezer ) ;
	        	Utils . netWriteString ( buf, freezReason ) ;
	
	        }
	                
	        Utils . netWriteString ( buf, moneyType ) ;
        
		} catch ( Exception ex ) {
			
			ex . printStackTrace ( ) ;
			
		}
	
    }

    @Override
    public Merch copy ( ) {
    	
    	return new ShopInfo ( merch_id, shopId, rating, owner, ownerName, name, isFreezed, freezer, freezReason, canVisibleFreezedText, moneyType )  ;
    	
    }
    
    @Override
    public String getBoughtMessage() {
        return "WTF?" ;
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

	@Override
	public String getMoneyType ( ) {
		
		return null ;
		
	}

	@Override
	public int withdrawMoney(UUID buyer, int amount) {
		return -1 ;
	}
    
	@SideOnly(Side.CLIENT)
	@Override
	public String getSearchValue ( ) {
		
		return EnumChatFormatting . getTextWithoutFormattingCodes ( name + ownerName ) ;
		
	}
	
	public void cleanFreezVisibleData ( ) {
		
		freezer = "" ;
		freezReason = "" ;
		canVisibleFreezedText = false ;
		
	}
	
}
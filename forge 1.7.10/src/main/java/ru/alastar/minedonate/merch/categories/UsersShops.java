package ru.alastar.minedonate.merch.categories;

import net.minecraft.entity.player.EntityPlayerMP;

import ru.alastar.minedonate.MineDonate;
import ru.alastar.minedonate.merch.Merch;
import ru.alastar.minedonate.merch.MerchCategory;
import ru.alastar.minedonate.merch.info.ShopInfo;
import ru.alastar.minedonate.rtnl.ModDataBase;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class UsersShops extends MerchCategory {

	boolean enabled = MineDonate.cfg.userShops ;
	public Map<Integer, ShopInfo> map = new HashMap<>();
	
	public UsersShops ( ) {
		
		super ( 0, 4, null ) ;
		
	}

    @Override
    public String getDatabaseTable ( ) {
        return MineDonate.cfg.dbShops;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
    
    @Override
    public void setEnabled ( boolean _enabled ) {
    	
    	enabled = _enabled ;
    	
    }
    
    @Override
    public Merch constructMerch() {
        return new ShopInfo();
    }

    public ShopInfo getShop ( int sid ) {
    
    	return map . get ( sid ) ;
    	
    }
    
    @Override
    public void addMerch ( Merch m ) {
    	
    	super . addMerch ( m ) ;
    	
        map . put ( ( ( ShopInfo ) m ) . shopId, ( ShopInfo ) m ) ;

    }

    @Override
    public void loadCategory ( ) throws Exception {
    
    	Statement stat = ModDataBase . getNewStatement ( getDatabaseLinkName ( ) ) ;
    	
    	ResultSet rs = stat . executeQuery ( "SELECT * FROM " + getDatabaseTable ( ) ) ;
    	
    	loadCategoryFromObject ( rs ) ;
    	
    	rs . close ( ) ;

    	ModDataBase . closeStatementAndConnection ( stat ) ;
    	
    }
    
    @Override
    public void loadCategoryFromObject ( Object o ) {
    	
    	ResultSet rs = ( ResultSet ) o ;
    	
        try {        	
        	
            while (rs.next()) {
            	
                final ShopInfo info = new ShopInfo(rs.getInt("id"), rs.getInt("id"), rs.getInt("rating"), rs.getString("UUID"), rs.getString("ownerName"), rs.getString("name"), rs.getBoolean("isFreezed"), rs.getString("freezer"), rs.getString("freezReason"), true, rs.getString("moneyType"));
                
                this.addMerch(info);
                
            }
            
        } catch (SQLException e) {
        	
            e.printStackTrace();
            
        }
    
        MineDonate . logInfo ( "Loaded " + m_Merch . size() + " merch in " + toString ( ) ) ;

    }

    @Override
    public void giveMerch(EntityPlayerMP player, Merch merch, int amount) {

    }
    
	@Override
	public String getMoneyType ( ) {
		
		return null ;
		
	}

	@Override
    public Type getCatType ( ) {
    	
    	return Type . SHOPS ;
    	
    }
    
}
package ru.alastar.minedonate.merch.categories;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import ru.alastar.minedonate.MineDonate;
import ru.alastar.minedonate.merch.Merch;
import ru.alastar.minedonate.merch.categories.MerchCategory.Type;
import ru.alastar.minedonate.merch.info.ShopInfo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class UsersShops extends MerchCategory {

	boolean enabled = MineDonate.cfg.userShops ;
	public Map<Integer, ShopInfo> map = new HashMap<>();
	
	public UsersShops ( ) {
		
		super ( 0, 4, null ) ;
		
	}
	
    @Override
    public boolean canReverse() {
        return false;
    }

    @Override
    public void reverseFor(String log_msg, String player) {
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
    public void loadMerchFromDB(ResultSet rs) {
        try {        	
            while (rs.next()) {
                final ShopInfo info = new ShopInfo(rs.getInt("id"), rs.getInt("id"), rs.getString("owner"), rs.getString("name"), rs.getBoolean("isFreezed"), rs.getString("freezer"), rs.getString("freezReason"), false, rs.getString("moneyType"));
                this.addMerch(info);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        MinecraftServer.getServer().logInfo("Loaded " + m_Merch.size() + " users shops");
    }

    @Override
    public Merch constructMerch() {
        return new ShopInfo();
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
    public void GiveMerch(EntityPlayerMP player, Merch merch, int amount) {

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
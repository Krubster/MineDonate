package ru.alastar.minedonate.merch.categories;

import net.minecraft.entity.player.EntityPlayerMP;

import ru.alastar.minedonate.MineDonate;
import ru.alastar.minedonate.merch.Merch;
import ru.alastar.minedonate.merch.MerchCategory;
import ru.alastar.minedonate.merch.info.RegionInfo;
import ru.alastar.minedonate.plugin.PluginHelper;
import ru.alastar.minedonate.plugin.worldProtection.WorldProtectionPlugin;
import ru.alastar.minedonate.rtnl.ModDataBase;
import ru.alastar.minedonate.rtnl.ModNetworkRegistry;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

/**
 * Created by Alastar on 21.07.2017.
 */
public class Regions extends MerchCategory {
	
	boolean enabled = MineDonate.cfg.sellRegions ;

	public Regions ( int _shopId, int _catId, String _moneyType ) {
	
    	super ( _shopId, _catId, _moneyType ) ;
		
	}

    @Override
    public String getDatabaseTable ( ) {
        return MineDonate.cfg.dbRegions;
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
    public boolean canReverse() {
        return true;
    }

    @Override
    public Merch constructMerch() {
        return new RegionInfo();
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
            	
                final RegionInfo info = new RegionInfo(shopId, catId, rs.getInt("id"), rs.getInt("cost"), rs.getString("name"), rs.getString("world"));
               
                this.addMerch(info);
                
            }
            
        } catch (SQLException e) {
        	
            e.printStackTrace();
            
        }
        
        MineDonate . logInfo ( "Loaded " + m_Merch . size() + " merch in " + toString ( ) ) ;
        
    }

    @Override
    public void giveMerch(EntityPlayerMP player, Merch merch, int amount) {

    	final RegionInfo info = (RegionInfo) merch;
    	( ( WorldProtectionPlugin ) PluginHelper . getPlugin ( "worldProtectionManager" ) ) . addPlayerToRegion ( info . world_name, info . name, player . getGameProfile ( ) . getId ( ) ) ;
        
        removeRegion ( info.world_name, info.name ) ;
        
    }

    @Override
    public void reverseFor ( int merchId, UUID player, String [ ] data ) {
    	
        String msg = data[9];
        msg.replace(" bought region ", "");
        String world_name = msg.split("=")[1];
        String name = msg.split("=")[0];
        
        ( ( WorldProtectionPlugin ) PluginHelper . getPlugin ( "worldProtectionManager" ) ) . removePlayerFromRegion ( world_name, name, player ) ;

        returnToStock(new RegionInfo(shopId, catId, merchId, Integer.valueOf(data[6]), name, world_name));
        
    }

    private void returnToStock(RegionInfo regionInfo) {
        
    	addMerch(regionInfo);
             
    	Statement stat = null ;
    	
        try {
        	
        	stat = ModDataBase . getNewStatement ( getDatabaseLinkName ( ) ) ;

        	stat . execute ( "INSERT INTO " + MineDonate.cfg.dbRegions + " (world, name, cost) VALUES('" + regionInfo.name + "', '" + regionInfo.world_name + "', " + regionInfo.getCost() + ")");
            
        } catch ( Exception ex ) {
            
        	ex . printStackTrace ( ) ;
            
        }

		ModDataBase . closeStatementAndConnection ( stat ) ;
		
        ModNetworkRegistry . sendToAllAddMerchPacket ( regionInfo ) ;

    }

    private void removeRegion(String world_name, String name) {
    	
    	Statement stat = null ;
    	 
        try {
        	
            stat = ModDataBase . getNewStatement ( getDatabaseLinkName ( ) ) ;
            
            stat . execute ( "DELETE FROM " + MineDonate.cfg.dbRegions + " WHERE name='" + name + "' AND world='" + world_name + "';");
            
        } catch ( Exception ex ) {
            
        	ex . printStackTrace ( ) ;
            
        }
        
		ModDataBase . closeStatementAndConnection ( stat ) ;
		
    }
    
	@Override
	public String getMoneyType ( ) {
		
		return moneyType ;
		
	}
	
	@Override
    public Type getCatType ( ) {
    	
    	return Type . REGIONS ;
    	
    }
    
}

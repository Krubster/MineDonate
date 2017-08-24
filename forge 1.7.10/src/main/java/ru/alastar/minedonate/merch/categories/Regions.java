package ru.alastar.minedonate.merch.categories;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import ru.alastar.minedonate.MineDonate;
import ru.alastar.minedonate.merch.Merch;
import ru.alastar.minedonate.merch.info.RegionInfo;
import ru.alastar.minedonate.plugin.PluginHelper;
import ru.alastar.minedonate.rtnl.ModNetwork;

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
    public boolean canReverse() {
        return true;
    }

    @Override
    public void reverseFor ( int merchId, UUID player, String [ ] data ) {
    	
        String msg = data[9];
        msg.replace(" bought region ", "");
        String world_name = msg.split("=")[1];
        String name = msg.split("=")[0];
        
        PluginHelper.wgMgr.removePlayerFromRegion(world_name, name, player);

        returnToStock(new RegionInfo(shopId, catId, merchId, Integer.valueOf(data[6]), name, world_name));
        
    }

    private void returnToStock(RegionInfo regionInfo) {
        
    	addMerch(regionInfo);
                
        try {
        	
        	Statement stmt = MineDonate . getNewStatement ( "main" ) ;
            String sql;
            sql = "INSERT INTO " + MineDonate.cfg.dbRegions + " (world, name, cost) VALUES('" + regionInfo.name + "', '" + regionInfo.world_name + "', " + regionInfo.getCost() + ")";
            stmt.execute(sql);
            stmt.close();
            
        } catch ( Exception ex ) {
            
        	ex . printStackTrace ( ) ;
            
        }

        ModNetwork . sendToAllAddMerchPacket ( regionInfo ) ;

    }

    @Override
    public void loadMerchFromDB(ResultSet rs) {
        try {
            while (rs.next()) {
                final RegionInfo info = new RegionInfo(shopId, catId, rs.getInt("id"), rs.getInt("cost"), rs.getString("name"), rs.getString("world"));
                this.addMerch(info);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        MinecraftServer.getServer().logInfo("Loaded " + m_Merch.size() + " regions");
    }

    @Override
    public Merch constructMerch() {
        return new RegionInfo();
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
    public void giveMerch(EntityPlayerMP player, Merch merch, int amount) {

    	final RegionInfo info = (RegionInfo) merch;
        PluginHelper.wgMgr.addPlayerToRegion(info.world_name, info.name, player.getGameProfile().getId());
        
        removeRegion ( info.world_name, info.name ) ;
        
    }

    private void removeRegion(String world_name, String name) {
    	
        try {
        	
            Statement stmt = MineDonate . getNewStatement ( "main" ) ;
            
            stmt.execute("DELETE FROM " + MineDonate.cfg.dbRegions + " WHERE name='" + name + "' AND world='" + world_name + "';");
            stmt.close();
            
        } catch ( Exception ex ) {
            
        	ex . printStackTrace ( ) ;
            
        }
        
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

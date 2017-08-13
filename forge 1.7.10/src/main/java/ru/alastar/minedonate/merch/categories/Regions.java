package ru.alastar.minedonate.merch.categories;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;

import ru.alastar.minedonate.MineDonate;
import ru.alastar.minedonate.merch.Merch;
import ru.alastar.minedonate.merch.categories.MerchCategory.Type;
import ru.alastar.minedonate.merch.info.RegionInfo;
import ru.alastar.minedonate.plugin.PluginHelper;
import ru.alastar.minedonate.rtnl.ModNetwork;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
    public void reverseFor(String log_msg, String player) {
        String msg = log_msg.split(":")[2];
        msg.replace(" bought region ", "");
        String world_name = msg.split("=")[1];
        String name = msg.split("=")[0];
        
        PluginHelper.wgMgr.removePlayerFromRegion(world_name, name, player);

        returnToStock(new RegionInfo(shopId, catId, m_Merch.size(), Integer.valueOf(log_msg.split(":")[4]), name, world_name));
    }

    private void returnToStock(RegionInfo regionInfo) {
        addMerch(regionInfo);
        Statement stmt = null;
        try {
            stmt = MineDonate.m_DB_Connection.createStatement();
            String sql;
            sql = "INSERT INTO " + MineDonate.cfg.dbRegions + " (world, name, cost) VALUES('" + regionInfo.name + "', '" + regionInfo.world_name + "', " + regionInfo.getCost() + ")";
            stmt.execute(sql);
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ModNetwork . sendToAllAddMerchPacket ( regionInfo ) ;

    }

    @Override
    public void loadMerchFromDB(ResultSet rs) {
        int i = 0;
        try {
            while (rs.next()) {
                final RegionInfo info = new RegionInfo(shopId, catId, i, rs.getInt("cost"), rs.getString("name"), rs.getString("world"));
                this.addMerch(info);
                ++i;
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
    public void GiveMerch(EntityPlayerMP player, Merch merch, int amount) {

    	final RegionInfo info = (RegionInfo) merch;
        PluginHelper.wgMgr.addPlayerToRegion(info.world_name, info.name, player.getDisplayName());

    }

    private void removeRegion(String name, String world_name) {
        try {
            Statement stmt = MineDonate.m_DB_Connection.createStatement();
            String sql;
            sql = "DELETE FROM " + MineDonate.cfg.dbRegions + " WHERE name='" + name + "' AND world='" + world_name + "';";
            stmt.execute(sql);
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
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

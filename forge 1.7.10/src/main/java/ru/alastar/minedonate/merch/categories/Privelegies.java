package ru.alastar.minedonate.merch.categories;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;

import ru.alastar.minedonate.MineDonate;
import ru.alastar.minedonate.merch.Merch;
import ru.alastar.minedonate.merch.categories.MerchCategory.Type;
import ru.alastar.minedonate.merch.info.PrivilegieInfo;
import ru.alastar.minedonate.plugin.PluginHelper;
import ru.alastar.minedonate.proxies.ClientProxy;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

/**
 * Created by Alastar on 21.07.2017.
 */
public class Privelegies extends MerchCategory {

	boolean enabled = MineDonate.cfg.sellPrivelegies ;

    public Privelegies(int _shopId, int _catId, String _moneyType) {

        super(_shopId, _catId, _moneyType);

    }

    @Override
    public boolean canReverse() {
        return true;
    }

    @Override
    public void reverseFor(String log_msg, String player) {
        
    	PluginHelper.pexMgr.removeGroup ( player, log_msg.split(":")[2].split("-")[1] ) ;

    }

    @Override
    public Merch constructMerch() {
        return new PrivilegieInfo();
    }

    @Override
    public void addMerch(Merch merch) {
        super.addMerch(merch);
        final PrivilegieInfo info = (PrivilegieInfo) merch;
        MineDonate.proxy.loadIcon(info.picture_url, info.merch_id);
    }

    @Override
    public void loadMerchFromDB(ResultSet rs) {
        try {
            while (rs.next()) {
                final PrivilegieInfo info = new PrivilegieInfo(shopId, catId, rs.getInt("id"), rs.getString("name"), rs.getString("description"), rs.getString("pic_url"), rs.getInt("cost"), rs.getLong("time"), rs.getString("worlds"));
                this.addMerch(info);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        MinecraftServer.getServer().logInfo("Loaded " + m_Merch.size() + " groups");
    }

    @Override
    public String getDatabaseTable ( ) {
        return MineDonate.cfg.dbPrivelegies;
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
    public void GiveMerch(EntityPlayerMP serverPlayer, Merch merch, int amount) {
    	
        try {
            
        	final PrivilegieInfo info = (PrivilegieInfo) merch;
            
            if (info.worlds.length > 0) {
            	
                for (String world : info.worlds) {
                	PluginHelper.pexMgr.addGroup(serverPlayer.getDisplayName(), info.name, world, info.getTimeInSeconds());
                   // Object obj = Bukkit.getPluginManager().getPlugin("PermissionsEx").getClass().getMethod("getUser", String.class).invoke(null, serverPlayer.getDisplayName());
                   // obj.getClass().getMethod("addGroup", String.class, String.class, long.class).invoke(obj, info.name, world, info.getTimeInSeconds());
                }
                
            } else {
            	
            	PluginHelper.pexMgr.addGroup(serverPlayer.getDisplayName(), info.name, null, info.getTimeInSeconds());
              //  Object obj = Bukkit.getPluginManager().getPlugin("PermissionsEx").getClass().getMethod("getUser", String.class).invoke(null, serverPlayer.getDisplayName());
              // obj.getClass().getMethod("addGroup", String.class, String.class, long.class).invoke(obj, info.name, "*", info.getTimeInSeconds());
            }
            
        } catch ( Exception ex ) {
        	
        	ex . printStackTrace ( ) ;
        	
        }
        
    }

    @Override
    public String getMoneyType() {

        return moneyType;

    }

	@Override
    public Type getCatType ( ) {
    	
    	return Type . PRIVELEGIES ;
    	
    }
    
}

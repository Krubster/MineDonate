package ru.alastar.minedonate.merch.categories;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import ru.alastar.minedonate.MineDonate;
import ru.alastar.minedonate.merch.Merch;
import ru.alastar.minedonate.merch.info.PrivilegieInfo;
import ru.alastar.minedonate.plugin.PluginHelper;

import java.sql.ResultSet;
import java.sql.SQLException;

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
    public void reverseFor ( int merchId, String player, String [ ] data ) {
        
    	PluginHelper.pexMgr.removeGroup ( player, data[9].split("-")[1] ) ;

    }

    @Override
    public Merch constructMerch() {
        return new PrivilegieInfo();
    }

    @Override
    public void addMerch ( Merch merch ) {
        
    	super . addMerch ( merch ) ;
        
        MineDonate . proxy . loadIcon ( ( ( PrivilegieInfo ) merch ) . picture_url, merch . getId ( ) ) ;
        
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
    public void giveMerch(EntityPlayerMP serverPlayer, Merch merch, int amount) {
    	
        try {
            
        	final PrivilegieInfo info = (PrivilegieInfo) merch;
            
            if (info.worlds.length > 0) {
            	
                for (String world : info.worlds) {
                	
                	PluginHelper.pexMgr.addGroup(serverPlayer.getDisplayName(), info.name, world, info.getTimeInSeconds());
                	
                }
                
            } else {
            	
            	PluginHelper.pexMgr.addGroup(serverPlayer.getDisplayName(), info.name, null, info.getTimeInSeconds());

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

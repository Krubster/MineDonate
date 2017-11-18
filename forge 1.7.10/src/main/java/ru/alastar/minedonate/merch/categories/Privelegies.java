package ru.alastar.minedonate.merch.categories;

import net.minecraft.entity.player.EntityPlayerMP;

import ru.alastar.minedonate.MineDonate;
import ru.alastar.minedonate.merch.Merch;
import ru.alastar.minedonate.merch.MerchCategory;
import ru.alastar.minedonate.merch.info.PrivilegieInfo;
import ru.alastar.minedonate.plugin.PluginHelper;
import ru.alastar.minedonate.plugin.permissions.PermissionsPlugin;
import ru.alastar.minedonate.rtnl.ModDataBase;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

/**
 * Created by Alastar on 21.07.2017.
 */
public class Privelegies extends MerchCategory {

	boolean enabled = MineDonate.cfg.sellPrivelegies ;

    public Privelegies(int _shopId, int _catId, String _moneyType) {

        super(_shopId, _catId, _moneyType);

    }

    @Override
    public String getDatabaseTable ( ) {
        
    	return MineDonate . cfg . dbPrivelegies;
        
    }

    @Override
    public boolean isEnabled ( ) {
        
    	return enabled ;
        
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
        return new PrivilegieInfo();
    }

    @Override
    public void addMerch ( Merch merch ) {
        
    	super . addMerch ( merch ) ;
        
        MineDonate . proxy . loadIcon ( ( ( PrivilegieInfo ) merch ) . picture_url, merch . getId ( ) ) ;
        
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
            	
                final PrivilegieInfo info = new PrivilegieInfo(shopId, catId, rs.getInt("id"), rs.getString("name"), rs.getString("title"), rs.getString("description"), rs.getString("pic_url"), rs.getInt("cost"), rs.getLong("time"), rs.getString("worlds"));
               
                this.addMerch(info);
                
            }
            
        } catch (SQLException e) {
        	
            e.printStackTrace();
            
        }
        
        MineDonate . logInfo ( "Loaded " + m_Merch . size() + " merch in " + toString ( ) ) ;
        
    }

    @Override
    public void giveMerch ( EntityPlayerMP serverPlayer, Merch merch, int amount ) {
    	
        try {
            
        	final PrivilegieInfo info = ( PrivilegieInfo ) merch ;
            
            if ( info . worlds . length > 0 ) {
            	
                for ( String world : info . worlds ) {
                	
                	( ( PermissionsPlugin ) PluginHelper . getPlugin ( "permissionsManager" ) ) . addGroup ( serverPlayer . getGameProfile ( ) . getId ( ), info . name, world, info . getTimeInSeconds ( ) ) ;
                	
                }
                
            } else {
            	
            	( ( PermissionsPlugin ) PluginHelper . getPlugin ( "permissionsManager" ) ) . addGroup ( serverPlayer . getGameProfile ( ) . getId ( ), info . name, null, info . getTimeInSeconds ( ) ) ;

            }
            
        } catch ( Exception ex ) {
        	
        	ex . printStackTrace ( ) ;
        	
        }
        
    }

    @Override
    public void reverseFor ( int merchId, UUID player, String [ ] data ) {
        
    	( ( PermissionsPlugin ) PluginHelper . getPlugin ( "permissionsManager" ) ) . removeGroup ( player, data[9].split("-")[1] ) ;

    }
    
    @Override
    public String getMoneyType ( ) {

        return moneyType;

    }

	@Override
    public Type getCatType ( ) {
    	
    	return Type . PRIVELEGIES ;
    	
    }
    
}

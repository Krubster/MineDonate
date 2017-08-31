package ru.alastar.minedonate.merch.categories;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;

import ru.alastar.minedonate.MineDonate;
import ru.alastar.minedonate.merch.Merch;
import ru.alastar.minedonate.merch.info.EntityInfo;
import ru.alastar.minedonate.rtnl.ModDataBase;
import ru.alastar.minedonate.rtnl.ModNetworkRegistry;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * Created by Alastar on 21.07.2017.
 */
public class Entities extends MerchCategory {

	boolean enabled = MineDonate.cfg.sellEntities ;

	public Entities ( int _shopId, int _catId, String _moneyType ) {
		
    	super ( _shopId, _catId, _moneyType ) ;
		
	}

    @Override
    public void loadMerchFromDB(ResultSet rs) {
    	
        try {
        	
            while (rs.next()) {
            	
                final EntityInfo info = new EntityInfo(shopId, catId, rs.getInt("id"), rs.getInt("rating"), rs.getInt("cost"), rs.getBlob("data"), rs.getString("name"), rs.getInt("lim"));
              
                this.addMerch(info);
                
            }
            
        } catch (SQLException e) {
        	
            e.printStackTrace();
            
        }
        
        MineDonate . logInfo ( "Loaded " + m_Merch . size() + " merch in " + toString ( ) ) ;

    }

    @Override
    public String getDatabaseTable ( ) {
        return MineDonate.cfg.dbEntities;
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
    public void giveMerch ( EntityPlayerMP serverPlayer, Merch merch, int amount ) {
    	
    	EntityInfo info = ( EntityInfo ) merch ;
        
        if ( info . limit != -1 ) {
        
        	if ( info . limit < amount ) {
        		
        		MineDonate . logError ( "Buy error, amount[" + amount +"] > info[" + info . toString ( ) + "].limit[" + info . limit + "]" ) ;
        	
        		return ;
        		
        	}
        	
        	info . limit -= amount ;
        	
        }
        
        try {
           
            Entity entity = (Entity) Class.forName(info.classpath).getDeclaredConstructor(net.minecraft.world.World.class).newInstance(serverPlayer.getEntityWorld());
            entity.readFromNBT(info.entity_data);
            entity.setLocationAndAngles(serverPlayer.posX, serverPlayer.posY, serverPlayer.posZ, serverPlayer.rotationYaw, serverPlayer.rotationPitch);
          
            serverPlayer.getEntityWorld().spawnEntityInWorld(entity);
            
        } catch ( Exception ex ) {
            
        	ex . printStackTrace ( ) ;
            
        }
        
        if ( info . limit > -1 ) {
            
        	updateEntityInfo ( info ) ;
        
        }
        
    }

    private void updateEntityInfo ( EntityInfo info ) {
    	
        Statement stat = null ;
        
        try {
            
        	stat = ModDataBase . getNewStatement ( "main" ) ;
            stat . executeUpdate ( "UPDATE " + getDatabaseTable ( ) + " SET lim=" + info.limit + " WHERE id=" + info . getId ( ) + ( info . shopId > 0 ? " AND shopId=" + info . shopId : "" ) + ";");
            
        } catch ( Exception ex ) {
            
        	ex . printStackTrace ( ) ;
            
        }

        ModDataBase . closeStatementAndConnection ( stat ) ;

        ModNetworkRegistry . sendToAllMerchInfoPacket ( info ) ;

    }
    
    @SideOnly ( Side . SERVER )
    @Override
    public void updateMerch ( int id, Merch info ) {
        
    	super . updateMerch ( id, info ) ;
        
    	updateEntityInfo ( ( EntityInfo ) info ) ;
    	
    }
    
    @Override
    public Merch constructMerch ( ) {
        
    	return new EntityInfo ( ) ;
        
    }
    
	@Override
	public String getMoneyType ( ) {
		
		return moneyType ;
		
	}
	
	@Override
    public Type getCatType ( ) {
    	
    	return Type . ENTITIES ;
    	
    }
    
}

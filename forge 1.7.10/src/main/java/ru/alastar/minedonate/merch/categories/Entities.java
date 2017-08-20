package ru.alastar.minedonate.merch.categories;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;

import ru.alastar.minedonate.MineDonate;
import ru.alastar.minedonate.merch.Merch;
import ru.alastar.minedonate.merch.info.EntityInfo;

import java.lang.reflect.InvocationTargetException;

import java.sql.ResultSet;
import java.sql.SQLException;

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
        MinecraftServer.getServer().logInfo("Loaded " + m_Merch.size() + " entities");
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
    public void giveMerch(EntityPlayerMP serverPlayer, Merch merch, int amount) {
        try {
            EntityInfo info = (EntityInfo)merch;
            Entity entity = (Entity) Class.forName(info.classpath).getDeclaredConstructor(net.minecraft.world.World.class).newInstance(serverPlayer.getEntityWorld());
            entity.readFromNBT(info.entity_data);
            entity.setLocationAndAngles(serverPlayer.posX, serverPlayer.posY, serverPlayer.posZ, serverPlayer.rotationYaw, serverPlayer.rotationPitch);
            serverPlayer.getEntityWorld().spawnEntityInWorld(entity);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Merch constructMerch() {
        return new EntityInfo();
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

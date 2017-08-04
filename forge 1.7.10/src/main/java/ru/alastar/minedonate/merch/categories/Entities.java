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
	
	public Entities ( int _shopId, int _catId, String _moneyType ) {
		
    	super ( _shopId, _catId, _moneyType ) ;
		
	}
	
    @Override
    public boolean canReverse() {
        return false;
    }

    @Override
    public void reverseFor(String log_msg, String player) {

    }

    @Override
    public void loadMerchFromDB(ResultSet rs) {
        int i = 0;
        try {
            while (rs.next()) {
                final EntityInfo info = new EntityInfo(shopId, catId, i, rs.getInt("cost"), rs.getBlob("data"), rs.getString("name"), rs.getInt("lim"));
                this.addMerch(info);
                ++i;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        MinecraftServer.getServer().logInfo("Loaded " + m_Merch.length + " entities");
    }

    @Override
    public String getDatabase() {
        return MineDonate.cfg.dbEntities;
    }

    @Override
    public boolean isEnabled() {
        return MineDonate.cfg.sellEntities;
    }

    @Override
    public void GiveMerch(EntityPlayerMP serverPlayer, Merch merch, int amount) {
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
	
}

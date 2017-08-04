package ru.alastar.minedonate.merch.categories;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import ru.alastar.minedonate.MineDonate;
import ru.alastar.minedonate.merch.Merch;
import ru.alastar.minedonate.merch.info.ShopInfo;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UsersShops extends MerchCategory {

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

    @Override
    public void loadMerchFromDB(ResultSet rs) {
        int i = 0;
        try {
            while (rs.next()) {
                final ShopInfo info = new ShopInfo(i, rs.getInt("id"), rs.getString("owner"), rs.getString("name"), rs.getBoolean("isFreezed"), rs.getString("moneyType"));
                this.addMerch(info);
                ++i;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        MinecraftServer.getServer().logInfo("Loaded " + m_Merch.length + " users shops");
    }

    @Override
    public Merch constructMerch() {
        return new ShopInfo();
    }

    @Override
    public String getDatabase() {
        return MineDonate.cfg.dbShops;
    }

    @Override
    public boolean isEnabled() {
        return MineDonate.cfg.userShops;
    }

    @Override
    public void GiveMerch(EntityPlayerMP player, Merch merch, int amount) {

    }
    
	@Override
	public String getMoneyType ( ) {
		
		return null ;
		
	}

}
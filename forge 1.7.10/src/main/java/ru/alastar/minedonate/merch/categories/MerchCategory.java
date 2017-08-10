package ru.alastar.minedonate.merch.categories;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayerMP;
import ru.alastar.minedonate.MineDonate;
import ru.alastar.minedonate.gui.ShopCategory;
import ru.alastar.minedonate.merch.Merch;
import ru.alastar.minedonate.network.packets.RemoveMerchPacket;
import ru.alastar.minedonate.rtnl.ModNetwork;

import java.sql.ResultSet;
import java.util.Arrays;

/**
 * Created by Alastar on 21.07.2017.
 */
public abstract class MerchCategory {
	
    public int shopId;
    public int catId;
    public String moneyType ;

    public MerchCategory ( int _shopId, int _catId, String _moneyType ) {
    	
    	shopId = _shopId ;
    	catId = _catId ;
		moneyType = _moneyType ;

    }
    
    protected Merch[] m_Merch = new Merch[0];

    @SideOnly(Side.SERVER)
    public abstract boolean canReverse();

    @SideOnly(Side.SERVER)
    public abstract void reverseFor(String log_msg, String player);

    @SideOnly(Side.SERVER)
    public abstract void loadMerchFromDB(ResultSet rs);

    @SideOnly(Side.SERVER)
    public abstract String getDatabase();

    public void addMerch(Merch merch) {
        if (merch.getId() >= m_Merch.length)
            m_Merch = Arrays.copyOf(m_Merch, merch.getId() + 1);

        m_Merch[merch.getId()] = merch;
    }

    public abstract boolean isEnabled();
    public abstract void setEnabled ( boolean _enabled ) ;
    
    @SideOnly(Side.SERVER)
    public abstract void GiveMerch(EntityPlayerMP player, Merch merch, int amount);

    public Merch getMerch(int id) {
        if (id < m_Merch.length) {
            return m_Merch[id];
        }
        return null;
    }

    public Merch[] getMerch() {
        return m_Merch;
    }

    public abstract Merch constructMerch();

    public void removeMerch(int info) {
        for (int i = info; i < m_Merch.length - 1; ++i) {
            m_Merch[i] = m_Merch[i + 1];
        }
        m_Merch = Arrays.copyOf(m_Merch, m_Merch.length - 1);
        for (int i = 0; i < m_Merch.length; ++i) {
            m_Merch[i].setId(i);
        }
    }

    @SideOnly(Side.SERVER)
    public void removeMerch(Merch info) {
        for (int i = info.getId(); i < m_Merch.length - 1; ++i) {
            m_Merch[i] = m_Merch[i + 1];
        }
        m_Merch = Arrays.copyOf(m_Merch, m_Merch.length - 1);
        for (int i = 0; i < m_Merch.length; ++i) {
            m_Merch[i].setId(i);
        }
        
        ModNetwork . sendToAllRemoveMerchPacket ( info . getShopId ( ), info . getId ( ), info . getCategory ( ) ) ;

    }

    public void updateMerch(int id, Merch info) {
        m_Merch[id] = info;
    }
    
    public String getMoneyType ( ) {
    	
    	return moneyType ;
    	
    }

}

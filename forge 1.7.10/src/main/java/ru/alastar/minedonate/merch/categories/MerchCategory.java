package ru.alastar.minedonate.merch.categories;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayerMP;
import ru.alastar.minedonate.merch.IMerch;
import ru.alastar.minedonate.network.MineDonateNetwork;
import ru.alastar.minedonate.network.packets.RemoveMerchPacket;

import java.sql.Array;
import java.sql.ResultSet;
import java.util.Arrays;

/**
 * Created by Alastar on 21.07.2017.
 */
public abstract class MerchCategory {
    protected IMerch[] m_Merch = new IMerch[0];

    @SideOnly(Side.SERVER)
    public abstract boolean canReverse();

    @SideOnly(Side.SERVER)
    public abstract void reverseFor(String log_msg, String player);

    @SideOnly(Side.SERVER)
    public abstract void loadMerchFromDB(ResultSet rs);

    @SideOnly(Side.SERVER)
    public abstract String getDatabase();

    public void addMerch(IMerch merch) {
        if (merch.getId() >= m_Merch.length)
            m_Merch = Arrays.copyOf(m_Merch, merch.getId() + 1);

        m_Merch[merch.getId()] = merch;
    }

    public abstract boolean isEnabled();

    @SideOnly(Side.SERVER)
    public abstract void GiveMerch(EntityPlayerMP player, IMerch merch, int amount);

    public IMerch getMerch(int id) {
        if (id < m_Merch.length) {
            return m_Merch[id];
        }
        return null;
    }

    public IMerch[] getMerch() {
        return m_Merch;
    }

    public abstract IMerch constructMerch();

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
    public void removeMerch(IMerch info) {
        for (int i = info.getId(); i < m_Merch.length - 1; ++i) {
            m_Merch[i] = m_Merch[i + 1];
        }
        m_Merch = Arrays.copyOf(m_Merch, m_Merch.length - 1);
        for (int i = 0; i < m_Merch.length; ++i) {
            m_Merch[i].setId(i);
        }
        MineDonateNetwork.INSTANCE.sendToAll(new RemoveMerchPacket(info.getId(), info.getCategory()));

    }

    public void updateMerch(int id, IMerch info) {
        m_Merch[id] = info;
    }
}

package ru.alastar.minedonate.merch.categories;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import org.bukkit.Bukkit;
import org.bukkit.World;
import ru.alastar.minedonate.MineDonate;
import ru.alastar.minedonate.merch.IMerch;
import ru.alastar.minedonate.merch.info.PrivilegieInfo;
import ru.alastar.minedonate.proxies.ClientProxy;

import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

/**
 * Created by Alastar on 21.07.2017.
 */
public class Privelegies extends MerchCategory {
    @Override
    public boolean canReverse() {
        return true;
    }

    @Override
    public void reverseFor(String log_msg, String player) {
        try {
            String name = log_msg.split(":")[2].split("-")[1];
            Object obj = Bukkit.getPluginManager().getPlugin("PermissionsEx").getClass().getMethod("getUser", String.class).invoke(null, player);
            obj.getClass().getMethod("removeGroup", String.class).invoke(obj, name);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    @Override
    public IMerch constructMerch() {
        return new PrivilegieInfo();
    }

    @Override
    public void addMerch(IMerch merch) {
        super.addMerch(merch);
        final PrivilegieInfo info = (PrivilegieInfo) merch;
        ClientProxy.loadIcon(info.picture_url, info.merch_id);
    }

    @Override
    public void loadMerchFromDB(ResultSet rs) {
        int i = 0;
        try {
            while (rs.next()) {
                final PrivilegieInfo info = new PrivilegieInfo(i, rs.getString("name"), rs.getString("description"), rs.getString("pic_url"), rs.getInt("cost"), rs.getLong("time"));
                this.m_Merch = Arrays.copyOf(m_Merch, i + 1);
                m_Merch[i] = info;
                ++i;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        MinecraftServer.getServer().logInfo("Loaded " + m_Merch.length + " groups");
    }

    @Override
    public String getDatabase() {
        return MineDonate.db_privelegies;
    }

    @Override
    public boolean isEnabled() {
        return MineDonate.m_Use_Privelegies;
    }

    @Override
    public void GiveMerch(EntityPlayerMP serverPlayer, IMerch merch, int amount) {
        try {
            final PrivilegieInfo info = (PrivilegieInfo) merch;
            for (World world : Bukkit.getWorlds()) {
                Object obj = Bukkit.getPluginManager().getPlugin("PermissionsEx").getClass().getMethod("getUser", String.class).invoke(null, serverPlayer.getDisplayName());
                obj.getClass().getMethod("addGroup", String.class, String.class, long.class).invoke(obj, info.name, world.getName(), info.getTimeInSeconds());
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
}

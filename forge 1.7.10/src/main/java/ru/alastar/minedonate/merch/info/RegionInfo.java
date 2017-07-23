package ru.alastar.minedonate.merch.info;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChatComponentText;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import ru.alastar.minedonate.MineDonate;
import ru.alastar.minedonate.merch.IMerch;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by Alastar on 20.07.2017.
 */
public class RegionInfo implements IMerch {

    public int merch_id;
    public String world_name;
    public int cost;
    public String name;

    public RegionInfo(int mid, int cost, String name, String world_name) {
        this.merch_id = mid;
        this.cost = cost;
        this.name = name;
        this.world_name = world_name;
    }

    @Override
    public int getId() {
        return merch_id;
    }

    @Override
    public boolean canBuy(EntityPlayerMP serverPlayer, int amount) {
        try {
            World bukkit_world = Bukkit.getWorld(this.world_name);
            Object plr = Bukkit.getPlayer(serverPlayer.getDisplayName());
            Object wg = MineDonate.wg_plugin.getClass().getMethod("inst").invoke(null);
            Object localPlayer = wg.getClass().getMethod("wrapPlayer", org.bukkit.entity.Player.class).invoke(wg, plr);
            Object reg_cont = wg.getClass().getMethod("getRegionContainer").invoke(wg);
            Object region_manager = reg_cont.getClass().getMethod("get", org.bukkit.World.class).invoke(reg_cont, bukkit_world);
            Object g_state_manager = wg.getClass().getMethod("getGlobalStateManager").invoke(wg);
            Object tmp = g_state_manager.getClass().getMethod("get", org.bukkit.World.class).invoke(g_state_manager, bukkit_world);
            Object resolver = tmp.getClass().getMethod("getMaxRegionCount", Player.class).invoke(tmp, plr);
            int res = (Integer) resolver;
            int plr_cnt = (Integer) region_manager.getClass().getMethod("getRegionCountOfPlayer", MineDonate.local_player_class).invoke(region_manager, localPlayer);
            System.out.println(res + " resolver, count " + plr_cnt);
            if (res >= 0 && plr_cnt >= res) {
                serverPlayer.addChatMessage(new ChatComponentText("You can't have more regions!"));
                return false;
            } else {
                return true;
            }

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        serverPlayer.addChatMessage(new ChatComponentText("Errorrrrrrrr!"));
        return false;
    }

    @Override
    public int getAmountToBuy() {
        return 1;
    }

    @Override
    public void setId(int i) {
        merch_id = i;
    }

    public RegionInfo() {

    }

    @Override
    public int getCategory() {
        return 2;
    }

    @Override
    public void read(ByteBuf buf) {
        merch_id = buf.readInt();
        cost = buf.readInt();
        int info_length = buf.readInt();
        try {
            this.name = new String(buf.readBytes(info_length).array(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void write(ByteBuf buf) {
        buf.writeInt(merch_id);
        buf.writeInt(cost);
        buf.writeInt(name.getBytes().length);
        buf.writeBytes(name.getBytes());
    }

    @Override
    public String getBoughtMessage() {
        return " bought region " + name + "=" + world_name;
    }

    @Override
    public int getCost() {
        return cost;
    }
}

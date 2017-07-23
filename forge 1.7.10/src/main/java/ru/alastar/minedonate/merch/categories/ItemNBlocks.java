package ru.alastar.minedonate.merch.categories;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import ru.alastar.minedonate.MineDonate;
import ru.alastar.minedonate.merch.IMerch;
import ru.alastar.minedonate.merch.info.ItemInfo;
import ru.alastar.minedonate.network.MineDonateNetwork;
import ru.alastar.minedonate.network.packets.MerchInfoPacket;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Alastar on 21.07.2017.
 */
public class ItemNBlocks extends MerchCategory {
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
                final ItemInfo info = new ItemInfo(i, rs.getInt(
                        "item_id"),
                        rs.getInt("count"),
                        rs.getByte("sub"),
                        rs.getInt("cost"),
                        rs.getString("name"),
                        rs.getString("info"),
                        rs.getInt("limit"));
                this.addMerch(info);
                ++i;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        MinecraftServer.getServer().logInfo("Loaded " + m_Merch.length + " lots");
    }

    @Override
    public String getDatabase() {
        return MineDonate.db_items;
    }

    @Override
    public boolean isEnabled() {
        return MineDonate.m_Use_Items;
    }

    @Override
    public void GiveMerch(EntityPlayerMP player, IMerch merch, int amount) {

        ItemInfo info = (ItemInfo) merch;
        if (info.limit > -1)
            info.limit -= amount * info.count;
        Object obj = Item.itemRegistry.getObjectById(info.item_id);
        ItemStack stack = null;
        if (obj instanceof Block) {
            stack = new ItemStack((Block) obj, info.count * amount, info.sub_id);
        } else if (obj instanceof Item) {
            stack = new ItemStack((Item) obj, info.count * amount, info.sub_id);
        }
        stack.setStackDisplayName(info.name);

        player.inventory.addItemStackToInventory(stack);
        if (info.limit > -1)
            updateItemInfo(info);

    }

    private void updateItemInfo(ItemInfo info) {
        Statement stmt = null;
        try {
            stmt = MineDonate.m_DB_Connection.createStatement();
            String sql;
            sql = "UPDATE " + getDatabase() + " SET " + getDatabase() + ".limit=" + info.limit + " WHERE name='" + info.name + "' and item_id=" + info.item_id + " and count=" + info.count + " and cost=" + info.cost + " and sub=" + info.sub_id + " and info='" + info.info + "';";
            stmt.executeUpdate(sql);
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        MineDonateNetwork.INSTANCE.sendToAll(new MerchInfoPacket(info));
    }

    @Override
    public IMerch constructMerch() {
        return new ItemInfo();
    }
}

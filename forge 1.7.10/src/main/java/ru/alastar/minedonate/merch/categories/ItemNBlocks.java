package ru.alastar.minedonate.merch.categories;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import ru.alastar.minedonate.MineDonate;
import ru.alastar.minedonate.merch.Merch;
import ru.alastar.minedonate.merch.info.ItemInfo;
import ru.alastar.minedonate.network.packets.MerchInfoPacket;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Alastar on 21.07.2017.
 */
public class ItemNBlocks extends MerchCategory {

    public int shopId;
    public int catId;
    
    String moneyType ;
	
	public ItemNBlocks ( int _shopId, int _catId, String _moneyType ) {
	
    	this.shopId = _shopId;
    	this.catId = _catId;
    	
		moneyType = _moneyType ;
		
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
                final ItemInfo info = new ItemInfo(shopId, catId, i,
                        rs.getInt("cost"),
                        rs.getString("name"),
                        rs.getString("info"),
                        rs.getInt("lim"),
                        rs.getBlob("stack_data"));
                this.addMerch(info);
                ++i;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        MinecraftServer.getServer().logInfo("Loaded " + m_Merch.length + " lots");
    }

    String dbTable = MineDonate.cfg.dbItems;

    @Override
    public String getDatabase() {

        return dbTable;

    }

    @Override
    public boolean isEnabled() {
        return MineDonate.cfg.sellItems;
    }

    @Override
    public void GiveMerch(EntityPlayerMP player, Merch merch, int amount) {

        ItemInfo info = (ItemInfo) merch;
        if (info.limit > -1)
            info.limit -= amount * info.stack_data.getInteger("Count");
        ItemStack stack = ItemStack.loadItemStackFromNBT(info.stack_data);
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
            sql = "UPDATE " + getDatabase() + " SET " + getDatabase() + ".lim=" + info.limit + " WHERE name='" + info.name + "', and cost=" + info.cost + "  and info='" + info.info + "';";
            stmt.executeUpdate(sql);
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        MineDonate.networkChannel.sendToAll(new MerchInfoPacket(info));
    }

    @Override
    public Merch constructMerch() {
        return new ItemInfo();
    }

	@Override
	public String getMoneyType ( ) {
		
		return moneyType ;
		
	}
	
    public MerchCategory setCustomDBTable(String _dbTable) {

        dbTable = _dbTable;

        return this;

    }

}

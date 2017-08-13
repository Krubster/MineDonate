package ru.alastar.minedonate.merch.categories;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;

import ru.alastar.minedonate.MineDonate;
import ru.alastar.minedonate.merch.Merch;
import ru.alastar.minedonate.merch.categories.MerchCategory.Type;
import ru.alastar.minedonate.merch.info.ItemInfo;
import ru.alastar.minedonate.rtnl.ModNetwork;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Alastar on 21.07.2017.
 */
public class ItemNBlocks extends MerchCategory {

	boolean enabled = MineDonate.cfg.sellItems ;

	public ItemNBlocks ( int _shopId, int _catId, String _moneyType ) {
	
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
        MinecraftServer.getServer().logInfo("Loaded " + m_Merch.size() + " lots");
    }

    String dbTable = MineDonate.cfg.dbItems;

    @Override
    public String getDatabaseTable ( ) {

        return dbTable;

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
    public void GiveMerch(EntityPlayerMP player, Merch merch, int amount) {

        ItemInfo info = (ItemInfo) merch;
        if (info.limit > -1)
            info.limit -= amount * info.stack_data.getInteger("Count");
        ItemStack stack = ItemStack.loadItemStackFromNBT(info.stack_data);
        if ( info . name != null && ! info . name . trim ( ) . isEmpty ( ) ) {
        	
        	stack.setStackDisplayName(info.name);
        	
        }

        player.inventory.addItemStackToInventory(stack);
        player.inventory.markDirty();
        
        if (info.limit > -1)
            updateItemInfo(info);

    }

    private void updateItemInfo(ItemInfo info) {
        Statement stmt = null;
        try {
            stmt = MineDonate.m_DB_Connection.createStatement();
            String sql;
            System.err.println("WARN! REPLACE TO PREPARED STATEMENT!");
            sql = "UPDATE " + getDatabaseTable() + " SET lim=" + info.limit + " WHERE name='" + info.name + "', and cost=" + info.cost + "  and info='" + info.info + "';";
            stmt.executeUpdate(sql);
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        ModNetwork . sendToAllMerchInfoPacket ( info ) ;

    }

    @Override
    public Merch constructMerch() {
        return new ItemInfo();
    }
	
    public void setCustomDBTable(String _dbTable) {

        dbTable = _dbTable;

    }

	@Override
    public Type getCatType ( ) {
    	
    	return Type . ITEMS ;
    	
    }

}

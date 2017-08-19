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
    public void loadMerchFromDB(ResultSet rs) {
        try {
            while (rs.next()) {
                final ItemInfo info = new ItemInfo(shopId, catId, rs.getInt("id"), rs.getInt("rating"),
                        rs.getInt("cost"),
                        rs.getString("name"),
                        rs.getInt("lim"),
                        rs.getBlob("stack_data"));
                this.addMerch(info);
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
    public boolean isEnabled ( ) {
        return enabled;
    }
    
    @Override
    public void setEnabled ( boolean _enabled ) {
    	
    	enabled = _enabled ;
    	
    }

    @Override
    public void giveMerch(EntityPlayerMP player, Merch merch, int amount) {

        ItemInfo info = (ItemInfo) merch;
        int toPut = amount * info.stack_data.getInteger("Count") ;
        
        if ( toPut < 1 ) {
        	
        	return ;
        	
        }
        
        if ( info.limit != -1 ) {
        	
        	info . limit -= amount ;
        
        }
  
        ItemStack stack = ItemStack.loadItemStackFromNBT(info.stack_data);
        
        if ( info . name != null && ! info . name . trim ( ) . isEmpty ( ) ) {
        	
        	stack.setStackDisplayName(info.name);
        	
        }

        ItemStack tmpCopy = stack . copy ( ) ;
        
        if ( toPut > stack . stackSize ) {
        	
        	int left = toPut ;
        	
        	for ( int i = 0 ; i < ( toPut / stack . getMaxStackSize ( ) ) + 1 ; i ++ ) {	

        		if ( left == -1 ) {
        			
        			continue ;
        			
        		}
        		
        		tmpCopy = stack . copy ( ) ;
        		
        		if ( left - stack . getMaxStackSize ( ) > 0 ) {
        		
        			tmpCopy . stackSize = stack . getMaxStackSize ( ) ;
                	addItemStackToInventory ( player, tmpCopy ) ;
                	
                	left -= stack . getMaxStackSize ( ) ;

        		} else {
        			
        			tmpCopy . stackSize = left ;

        			left = -1 ;
        			
                	addItemStackToInventory ( player, tmpCopy ) ;

        		}
        		
        	}
        	
        } else {
        	
        	addItemStackToInventory ( player, tmpCopy ) ;
            
        }
        
        tmpCopy = null ;
        
        player.inventory.markDirty();
        
        if (info.limit > -1) {
        
        	updateItemInfo(info);
        
        }
        
    }

    public void addItemStackToInventory ( EntityPlayerMP player, ItemStack is ) {
    	
        boolean b = player . inventory . addItemStackToInventory ( is ) ;
        
        if ( ! b ) {
        	
        	player . dropPlayerItemWithRandomChoice ( is, false ) ;
        	
        } 
        
    }
    
    private void updateItemInfo(ItemInfo info) {
    	
        Statement stmt = null;
        
        try {
            
        	stmt = MineDonate.m_DB_Connection.createStatement();
            String sql;
            sql = "UPDATE " + getDatabaseTable ( ) + " SET lim=" + info.limit + " WHERE id=" + info . getId ( ) + ( info . shopId > 0 ? " AND shopId=" + info . shopId : "" ) + ";";
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

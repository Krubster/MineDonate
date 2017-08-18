package ru.alastar.minedonate.rtnl;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import cpw.mods.fml.common.network.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import ru.alastar.minedonate.MineDonate;
import ru.alastar.minedonate.merch.Merch;
import ru.alastar.minedonate.merch.categories.MerchCategory;
import ru.alastar.minedonate.merch.categories.UsersShops;
import ru.alastar.minedonate.merch.info.EntityInfo;
import ru.alastar.minedonate.merch.info.ItemInfo;
import ru.alastar.minedonate.merch.info.ShopInfo;
import ru.alastar.minedonate.network.manage.packets.EditMerchNumberPacket;
import ru.alastar.minedonate.network.manage.packets.EditMerchStringPacket;

public class Manager {

	public static void createShop ( String owner, String name ) {
		
        ShopInfo info = new ShopInfo ( 0, MineDonate . getNextShopId ( ), owner, name, false, null, null, false, MineDonate.cfg.defaultUserShopMoneyType ) ;
        info . setId ( info . shopId ) ;
       
        MineDonate . shops . get ( 0 ) . cats [ 4 ] . addMerch ( info ) ;
      
        try {
        	
            PreparedStatement statement = MineDonate.getDBConnection().prepareStatement("INSERT INTO " + MineDonate.cfg.dbShops + " (owner, name, lastUpdate, isFreezed, freezer, freezReason, moneyType) VALUES(?,?,?,?,?,?,?)");
            
            statement.setString(1, owner);
            statement.setString(2, name);
            statement.setInt(3, 0);
            statement.setBoolean(4, info.isFreezed);
            statement.setString(5, "");
            statement.setString(6, "");
            statement.setString(7, info.moneyType);

            statement.execute();
            statement.close();
            
        } catch (SQLException e) {
            
        	e.printStackTrace();
            
        }
        
        MineDonate . getAccount ( owner ) . shopsCount ++ ;
        
		try {
			
			Statement st = MineDonate . getNewStatement ( ) ;
			
			st . executeUpdate ( "UPDATE " + MineDonate.cfg.dbUsers + " SET shopsCount = " + MineDonate . getAccount ( owner ) . shopsCount + " WHERE " + MineDonate.cfg.dbUsersNameColumn + "= '" + owner+ "';" ) ;
			
			st . close ( ) ;
			
		} catch ( Exception ex ) {
			
			ex . printStackTrace ( ) ;
		
		}
				
        MineDonate . loadUserShop ( info . shopId ) ;
        ModNetwork . sendToAllAddMerchPacket ( info ) ;
        
	}

	public static void deleteShop ( Shop s ) {
		
		ShopInfo si = ( ( UsersShops ) MineDonate . shops . get ( 0 ) . cats [ 4 ] ) . getShop ( s . sid ) ;
		
		MineDonate . shops . get ( 0 ) . cats [ 4 ] . removeMerch ( si ) ;
		MineDonate . shops . remove ( s . sid ) ;
		
		try {
			
			Statement st = MineDonate . getNewStatement ( ) ;
			
			st . executeUpdate ( "DELETE FROM " + MineDonate.cfg.dbShops + " WHERE id=" + s . sid + ";" ) ;
			
			st . close ( ) ;
			
		} catch ( Exception ex ) {
			
			ex . printStackTrace ( ) ;
			
		}
		
        MineDonate . getAccount ( s . owner ) . shopsCount -- ;
        
		try {
			
			Statement st = MineDonate . getNewStatement ( ) ;
			
			st . executeUpdate ( "UPDATE " + MineDonate.cfg.dbUsers + " SET shopsCount = " + MineDonate . getAccount ( s . owner ) . shopsCount + " WHERE " + MineDonate.cfg.dbUsersNameColumn + "= '" + s . owner + "';" ) ;
			
			st . close ( ) ;
			
		} catch ( Exception ex ) {
			
			ex . printStackTrace ( ) ;
		
		}
		
        ModNetwork . sendToAllRemoveMerchPacket ( 0, 4, si . getId ( ) ) ;

	}
	
	public static void freezeShop ( Shop s, String freezer, String reason ) {
		
		ShopInfo si = ( ( UsersShops ) MineDonate . shops . get ( 0 ) . cats [ 4 ] ) . getShop ( s . sid ) ;

		si . isFreezed = s . isFreezed = true ;
		si . freezer = s . freezer = freezer ;
		si . freezReason = s . freezReason = reason ;
		
        ModNetwork . sendToAllAddMerchPacket ( si ) ;

        try {
        	
            PreparedStatement statement = MineDonate.getDBConnection().prepareStatement("UPDATE " + MineDonate.cfg.dbShops + " SET isFreezed = 1, freezer = ?, freezReason = ? WHERE id = ?");
            
            statement.setString(1, freezer);
            statement.setString(2, reason);
            statement.setInt(3, s.sid);
            
            statement.execute();
            statement.close();
            
        } catch (SQLException e) {
            
        	e.printStackTrace();
            
        }
		
	}
	
	public static void unFreezeShop ( Shop s, String unFreezer ) {

		ShopInfo si = ( ( UsersShops ) MineDonate . shops . get ( 0 ) . cats [ 4 ] ) . getShop ( s . sid ) ;
		
		si . isFreezed = s . isFreezed = false ;
		si . freezer = s . freezer = unFreezer ;
		si . freezReason = s . freezReason = "" ;
		
        ModNetwork . sendToAllAddMerchPacket ( si ) ;

		try {
			
			Statement st = MineDonate . getNewStatement ( ) ;
			
			st . executeUpdate ( "UPDATE " + MineDonate.cfg.dbShops + " SET isFreezed = " + 0 + ", freezer='" + unFreezer + "', freezReason='' WHERE id=" + s . sid + ";" ) ;
			
			st . close ( ) ;
			
		} catch ( Exception ex ) {
			
			ex . printStackTrace ( ) ;
			
		}
		
	}

	public static void renameShop ( Shop s, String name ) {
		
		ShopInfo si = ( ( UsersShops ) MineDonate . shops . get ( 0 ) . cats [ 4 ] ) . getShop ( s . sid ) ;

		si . name = s . name = name ;
		
        ModNetwork . sendToAllAddMerchPacket ( si ) ;

        try {
        	
            PreparedStatement statement = MineDonate.getDBConnection().prepareStatement("UPDATE " + MineDonate.cfg.dbShops + " SET name = ? WHERE id = ?");
            
            statement.setString(1, name);
            statement.setInt(2, s.sid);
            
            statement.execute();
            statement.close();
            
        } catch (SQLException e) {
            
        	e.printStackTrace();
            
        }

 	}
	
	public static void freezePlayer ( Account acc, String freezer, String reason ) {
		
		acc . freezShopCreate = true ;
		acc . freezShopCreateFreezer = freezer ;
		acc . freezShopCreateReason = reason ;
		
        try {
        	
            PreparedStatement statement = MineDonate.getDBConnection().prepareStatement("UPDATE " + MineDonate.cfg.dbUsers + " SET freezShopCreate = 1, freezShopCreateFreezer = ?, freezShopCreateReason = ? WHERE " + MineDonate.cfg.dbUsersNameColumn + " = ?");
            
            statement.setString(1, freezer);
            statement.setString(2, reason);
            statement.setString(3, acc.name);
            
            statement.execute();
            statement.close();
            
        } catch (SQLException e) {
            
        	e.printStackTrace();
            
        }
		
	}

	public static void unFreezePlayer ( Account acc, String unFreezer ) {

		acc . freezShopCreate = false ;
		acc . freezShopCreateFreezer = unFreezer ;
		acc . freezShopCreateReason = "" ;
		
		try {
			
            PreparedStatement statement = MineDonate.getDBConnection().prepareStatement("UPDATE " + MineDonate.cfg.dbUsers + " SET freezShopCreate = 0, freezShopCreateFreezer = ?, freezShopCreateReason = ? WHERE " + MineDonate.cfg.dbUsersNameColumn + " = ?");
            
            statement.setString(1, unFreezer);
            statement.setString(2, "");
            statement.setString(3, acc.name);
            
            statement.execute();
            statement.close();
			
		} catch ( Exception ex ) {
			
			ex . printStackTrace ( ) ;
			
		}
		
	}


	public static void addEntityToShop ( Account acc, Shop s, int catId, int limit, int cost,  String name ) {
		
		if ( ! acc . canUnlimitedEntities ( ) ) {
			
			limit = 1 ;
			
		}

		EntityInfo info = new EntityInfo(s.sid, catId, s.cats[catId].getNextMerchId(), Integer.valueOf(cost), acc.ms.currentMob, name, limit);
        
        s.cats[catId].addMerch(info);
       
        ModNetwork . sendToAllAddMerchPacket ( info ) ;

        try {
          
            ByteBuf buf = Unpooled.buffer();
            
            ByteBufUtils.writeTag(buf, info.entity_data);
            
            InputStream stream = new ByteArrayInputStream(buf.array());
        
            PreparedStatement statement = MineDonate.getDBConnection().prepareStatement("INSERT INTO " + s.cats[catId].getDatabaseTable() + " (name, data, cost, lim) VALUES(?,?,?,?)");
            
            statement.setString(1, name);
            statement.setBlob(2, stream);
            statement.setInt(3, cost);
            statement.setInt(4, limit);

            statement.execute();
            statement.close();
            
        } catch (SQLException e) {
            
        	e.printStackTrace();
            
        }
        
	}
	
	public static void addItemToShop ( Account acc, Shop s, int catId, int limit, int cost, String name ) {
		
		if ( ! acc . canUnlimitedItems ( ) ) {
			
			limit = 1 ;
			
		}
		
    	ItemInfo info = new ItemInfo(s.sid, catId, s.cats[catId].getNextMerchId(), Integer.valueOf(cost), name, Integer.valueOf(limit), acc.ms.currentItemStack);
        
        s.cats[catId].addMerch(info);
       
        ModNetwork . sendToAllAddMerchPacket ( info ) ;

        try {
          
        	ByteBuf buf = Unpooled.buffer();
         
            NBTTagCompound nbt = new NBTTagCompound();
            
            acc.ms.currentItemStack.writeToNBT(nbt);
          
            ByteBufUtils.writeTag(buf, nbt);

            InputStream stream = new ByteArrayInputStream(buf.array());
           
            PreparedStatement statement ; 
            
            if ( s . sid == 0 ) {
            
            	statement = MineDonate.getDBConnection().prepareStatement("INSERT INTO " + s.cats[catId].getDatabaseTable() + " (name, cost, lim, stack_data) VALUES(?,?,?,?)");
            
            } else {
         
            	statement = MineDonate.getDBConnection().prepareStatement("INSERT INTO " + s.cats[catId].getDatabaseTable() + " (name, cost, lim, stack_data, shopId) VALUES(?,?,?,?,?)");
                statement.setInt(5, s.sid);

            }
            
            statement.setString(1, name);
            statement.setInt(2, Integer.valueOf(cost));
            statement.setInt(3, Integer.valueOf(limit));
            statement.setBlob(4, stream);
            statement.execute();
            statement.close();
            
        } catch (SQLException e) {
            
        	e.printStackTrace();
            
        }
        
	}
	
	public static void removeEntryFromShop ( EntityPlayerMP player, Shop s, int catId, int merchId ) {
		
		Merch m = s . cats [ catId ] . getMerch ( merchId ) ;
		
		MineDonate . shops . get ( s . sid ) . cats [ catId ] . removeMerch ( m ) ;
		
		try {
			
			Statement st = MineDonate . getNewStatement ( ) ;
			
			st . executeUpdate ( "DELETE FROM " + s . cats [ catId ] . getDatabaseTable ( ) + " WHERE id=" + merchId + ";" ) ;
			
			st . close ( ) ;
			
		} catch ( Exception ex ) {
			
			ex . printStackTrace ( ) ;
			
		}
		
        ModNetwork . sendToAllRemoveMerchPacket ( s . sid, catId, merchId ) ;
        
		if ( s . cats [ catId ] . getCatType ( ) == MerchCategory . Type . ITEMS ) {
			
			ItemInfo ii = ( ItemInfo ) m ;

			if ( ii . limit > 0 ) {
					
				MineDonate . shops . get ( s . sid ) . cats [ catId ] . giveMerch ( player, m, ii . limit ) ;
				
			}
			
		}
		
	}

	public static void editShopEntryNumber ( EntityPlayerMP player, Shop s, int catId, int merchId, EditMerchNumberPacket . Type type, int number) {
		
		MerchCategory mc = s . cats [ catId ] ;
		Merch m = mc . getMerch ( merchId ) ;
		
		switch ( type ) {
		
			case LIMIT :
				
				switch ( mc . getCatType ( ) ) {
				
					case ITEMS :
						
						( ( ItemInfo ) m ) . limit = number ;
	
					break ;
					
					case ENTITIES :
						
						( ( EntityInfo ) m ) . limit = number ;
	
					break ;
					
				}
				
			break;
			
			case COST :
			
				m . cost = number ;
			
			break ;
				
		}
		
		mc . updateMerch ( m . getId ( ), m ) ;
        ModNetwork . sendToAllMerchInfoPacket ( m ) ;

	}
	
	public static void editShopEntryString ( EntityPlayerMP player, Shop s, int catId, int merchId, EditMerchStringPacket . Type type, String str ) {
		
		MerchCategory mc = s . cats [ catId ] ;
		Merch m = mc . getMerch ( merchId ) ;
		
		switch ( type ) {
		
			case NAME :
				
				switch ( mc . getCatType ( ) ) {
				
					case ITEMS :
						
						( ( ItemInfo ) m ) . name = str ;
	
					break ;
					
					case ENTITIES :
						
						( ( EntityInfo ) m ) . name = str ;
	
					break ;
					
				}
	
			break;
		
		}
		
		mc . updateMerch ( m . getId ( ), m ) ;
        ModNetwork . sendToAllMerchInfoPacket ( m ) ;

	}
	
}

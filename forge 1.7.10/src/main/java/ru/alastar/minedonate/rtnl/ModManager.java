package ru.alastar.minedonate.rtnl;

import cpw.mods.fml.common.network.ByteBufUtils;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
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
import ru.alastar.minedonate.rtnl.common.Account;
import ru.alastar.minedonate.rtnl.common.Shop;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.UUID;

/**
 * Класс для удаления/создания/добавления/изменения/блокирования объектов/магазинов/игроков
 * 	Все вызовы делаются из network.manage.handlers
 */

public class ModManager {

	public static void createShop ( UUID owner, String ownerName, String name ) {
		
        ShopInfo info = new ShopInfo ( 0, MineDonate . getNextShopId ( ), 0, owner.toString(), ownerName, name, false, null, null, false, MineDonate.cfg.defaultUserShopMoneyType ) ;
        info . setId ( info . shopId ) ;
       
        MineDonate . shops . get ( 0 ) . cats [ 4 ] . addMerch ( info ) ;
      
        PreparedStatement pstat = null ;
        
        try {

        	pstat = ModDataBase . getPreparedStatement("main", "INSERT INTO " + MineDonate.cfg.dbShops + " (UUID, ownerName, name, lastUpdate, isFreezed, freezer, freezReason, moneyType) VALUES(?,?,?,?,?,?,?,?)");

        	pstat.setString(1, owner.toString());
            pstat.setString(2, ownerName);
            pstat.setString(3, name);
            pstat.setInt(4, 0);
            pstat.setBoolean(5, info.isFreezed);
            pstat.setString(6, "");
            pstat.setString(7, "");
            pstat.setString(8, info.moneyType);

            pstat.execute();
            
        } catch ( Exception ex ) {
            
        	ex . printStackTrace ( ) ;
            
        }
        
        ModDataBase . closePreparedStatementAndConnection ( pstat ) ;
        
        MineDonate . getAccountWithoutMoneyRegister ( owner ) . shopsCount ++ ;
        
        Statement stat = null ;
        
		try {
			
			stat = ModDataBase . getNewStatement ( "main" ) ;

            stat . executeUpdate("UPDATE " + MineDonate.cfg.dbUsers + " SET shopsCount = " + MineDonate . getAccountWithoutMoneyRegister ( owner ) . shopsCount + " WHERE " + MineDonate.cfg.dbUsersIdColumn + "= '" + owner + "';");
			
		} catch ( Exception ex ) {
			
			ex . printStackTrace ( ) ;
		
		}
					
		ModDataBase . closeStatementAndConnection ( stat ) ;
		
        MineDonate . loadUserShop ( info . shopId ) ;
        ModNetworkRegistry . sendToAllAddMerchPacket ( info ) ;
        
	}

	public static void deleteShop ( Shop s ) {
		
		ShopInfo si = ( ( UsersShops ) MineDonate . shops . get ( 0 ) . cats [ 4 ] ) . getShop ( s . sid ) ;
		
		MineDonate . shops . get ( 0 ) . cats [ 4 ] . removeMerch ( si ) ;
		MineDonate . shops . remove ( s . sid ) ;
		
		Statement stat = null ;
		
		try {
			
			stat = ModDataBase . getNewStatement ( "main" ) ;
			
			stat . executeUpdate ( "DELETE FROM " + MineDonate.cfg.dbShops + " WHERE id=" + s . sid + ";" ) ;
			
    		ModDataBase . closeStatementAndConnection ( stat ) ;
			
			stat = ModDataBase . getNewStatement ( "main" ) ;
			
			stat . executeUpdate ( "DELETE FROM " + MineDonate.cfg.dbUserItems + " WHERE shopId=" + s . sid + ";" ) ;
			
    		ModDataBase . closeStatementAndConnection ( stat ) ;
			
		} catch ( Exception ex ) {
			
			ex . printStackTrace ( ) ;
			
		}
					
		ModDataBase . closeStatementAndConnection ( stat ) ;

        MineDonate . getAccountWithoutRegister ( UUID . fromString ( s . owner ) ) . shopsCount -- ;
        
		try {
			
			stat = ModDataBase . getNewStatement ( "main" ) ;

            stat . executeUpdate("UPDATE " + MineDonate.cfg.dbUsers + " SET shopsCount = " + MineDonate . getAccountWithoutRegister ( UUID . fromString ( s . owner ) ) . shopsCount + " WHERE " + MineDonate.cfg.dbUsersIdColumn + "= '" + s . owner + "';");
			
		} catch ( Exception ex ) {
			
			ex . printStackTrace ( ) ;
		
		}
		
		ModDataBase . closeStatementAndConnection ( stat ) ;
		
        ModNetworkRegistry . sendToAllRemoveMerchPacket ( 0, 4, si . getId ( ) ) ;

	}
	
	public static void freezeShop ( Shop s, String freezer, String reason ) {
		
		ShopInfo si = ( ( UsersShops ) MineDonate . shops . get ( 0 ) . cats [ 4 ] ) . getShop ( s . sid ) ;

		si . isFreezed = s . isFreezed = true ;
		si . freezer = s . freezer = freezer ;
		si . freezReason = s . freezReason = reason ;
		
        ModNetworkRegistry . sendToAllAddMerchPacket ( si ) ;

        PreparedStatement pstat = null ;
        
        try {
        	
        	pstat = ModDataBase . getPreparedStatement ( "main", "UPDATE " + MineDonate.cfg.dbShops + " SET isFreezed = 1, freezer = ?, freezReason = ? WHERE id = ?");
            
        	pstat . setString ( 1, freezer ) ;
            pstat . setString ( 2, reason ) ;
            pstat . setInt ( 3, s . sid ) ;
            
            pstat . execute ( ); 
            
        } catch ( Exception ex ) {
            
        	ex . printStackTrace ( ) ;
            
        }
		
        ModDataBase . closeStatementAndConnection ( pstat ) ;

	}
	
	public static void unFreezeShop ( Shop s, String unFreezer ) {

		ShopInfo si = ( ( UsersShops ) MineDonate . shops . get ( 0 ) . cats [ 4 ] ) . getShop ( s . sid ) ;
		
		si . isFreezed = s . isFreezed = false ;
		si . freezer = s . freezer = unFreezer ;
		si . freezReason = s . freezReason = "" ;
		
        ModNetworkRegistry . sendToAllAddMerchPacket ( si ) ;

        Statement stat = null ;
        
		try {
			
			stat = ModDataBase . getNewStatement ( "main" ) ;
			
			stat . executeUpdate ( "UPDATE " + MineDonate.cfg.dbShops + " SET isFreezed = " + 0 + ", freezer='" + unFreezer + "', freezReason='' WHERE id=" + s . sid + ";" ) ;
						
		} catch ( Exception ex ) {
			
			ex . printStackTrace ( ) ;
			
		}
		
		ModDataBase . closeStatementAndConnection ( stat ) ;
		
	}

	public static void renameShop ( Shop s, String name ) {
		
		ShopInfo si = ( ( UsersShops ) MineDonate . shops . get ( 0 ) . cats [ 4 ] ) . getShop ( s . sid ) ;

		si . name = s . name = name ;
		
        ModNetworkRegistry . sendToAllAddMerchPacket ( si ) ;

        PreparedStatement pstat = null ;

        try {
        	
        	pstat = ModDataBase . getPreparedStatement ( "main", "UPDATE " + MineDonate.cfg.dbShops + " SET name = ? WHERE id = ?");
            
        	pstat . setString ( 1, name ) ;
        	pstat . setInt ( 2, s . sid ) ;
            
        	pstat . execute ( ) ;
            
        } catch ( Exception ex ) {
            
        	ex . printStackTrace ( ) ;
            
        }

        ModDataBase . closePreparedStatementAndConnection ( pstat ) ;

 	}
	
	public static void freezePlayer ( Account acc, String freezer, String reason ) {
		
		acc . freezShopCreate = true ;
		acc . freezShopCreateFreezer = freezer ;
		acc . freezShopCreateReason = reason ;
		
        PreparedStatement pstat = null ;

        try {

            pstat = ModDataBase . getPreparedStatement ( "main", "UPDATE " + MineDonate.cfg.dbUsers + " SET freezShopCreate = 1, freezShopCreateFreezer = ?, freezShopCreateReason = ? WHERE " + MineDonate.cfg.dbUsersIdColumn + " = ?");
            
            pstat.setString(1, freezer ) ;
            pstat.setString(2, reason ) ;
            pstat.setString(3, acc . id ) ;
            
            pstat.execute();
            
        } catch ( Exception ex ) {
            
        	ex . printStackTrace ( ) ;
            
        }
		
        ModDataBase . closePreparedStatementAndConnection ( pstat ) ;

	}

	public static void unFreezePlayer ( Account acc, String unFreezer ) {

		acc . freezShopCreate = false ;
		acc . freezShopCreateFreezer = unFreezer ;
		acc . freezShopCreateReason = "" ;
		
        PreparedStatement pstat = null ;

		try {

			pstat = ModDataBase . getPreparedStatement("main", "UPDATE " + MineDonate.cfg.dbUsers + " SET freezShopCreate = 0, freezShopCreateFreezer = ?, freezShopCreateReason = ? WHERE " + MineDonate.cfg.dbUsersIdColumn + " = ?");
            
			pstat.setString(1, unFreezer ) ;
            pstat.setString(2, "" ) ;
            pstat.setString(3, acc . id ) ;
            
            pstat.execute();
			
		} catch ( Exception ex ) {
			
			ex . printStackTrace ( ) ;
			
		}
		
        ModDataBase . closePreparedStatementAndConnection ( pstat ) ;

	}


	public static void addEntityToShop ( Account acc, Shop s, int catId, int limit, int cost, String name ) {

		EntityInfo info = new EntityInfo(s.sid, catId, s.cats[catId].getNextMerchId(), 0, Integer.valueOf(cost), acc.ms.currentMob, name, limit);
        
        s.cats[catId].addMerch(info);
       
        ModNetworkRegistry . sendToAllAddMerchPacket ( info ) ;

        PreparedStatement pstat = null ;

        try {
          
            ByteBuf buf = Unpooled.buffer();
            
            ByteBufUtils.writeTag(buf, info.entity_data);
            
            InputStream stream = new ByteArrayInputStream(buf.array());
        
            pstat = ModDataBase . getPreparedStatement ( "main", "INSERT INTO " + s.cats[catId].getDatabaseTable() + " (name, data, cost, lim) VALUES(?,?,?,?)");
            
            pstat.setString(1, name);
            pstat.setBlob(2, stream);
            pstat.setInt(3, cost);
            pstat.setInt(4, limit);

            pstat.execute();
            
        } catch ( Exception ex ) {
            
        	ex . printStackTrace ( ) ;
            
        }
        
        ModDataBase . closePreparedStatementAndConnection ( pstat ) ;

	}
	
	public static int canUppendAnotherItemInShop ( Account acc, Shop s, int catId ) {
	
		if ( acc . ms . currentItemStack == null ) {
			
			return -1 ;
			
		}
		
		ItemInfo ii ;

		for ( Merch m : s . cats [ catId ] . m_Merch . values ( ) ) {
			
			ii = ( ItemInfo ) m ;

			if ( ii . limit >= 0 && ii . limit < 10 && ItemStack . areItemStacksEqual ( ii . m_stack, acc . ms . currentItemStack ) ) {
				
				return ii . merchId ;
				
			}
			
		}
		
		return -1 ;
		
	}
	
	public static void uppendItemInShop ( Account acc, Shop s, int catId, int merchId ) {
	
		ItemInfo ii = ( ItemInfo ) s . cats [ catId ] . getMerch ( merchId ) ;

		if ( ii . limit < 10 && ItemStack . areItemStacksEqual ( ii . m_stack, acc . ms . currentItemStack ) ) {
			
			acc . ms . currentItemStack = null ;

			ii . limit ++ ;

			s . cats [ catId ] . updateMerch ( merchId, ii ) ;
			
			ModNetworkRegistry . sendToAllAddMerchPacket ( ii ) ;
	        
		}
		
	}
	
	public static void addItemToShop ( Account acc, Shop s, int catId, int limit, int cost, String name ) {
		
    	ItemInfo info = new ItemInfo(s.sid, catId, s.cats[catId].getNextMerchId(), 0, Integer.valueOf(cost), name, Integer.valueOf(limit), acc.ms.currentItemStack);

        PreparedStatement pstat = null ;

        try {
          
        	ByteBuf buf = Unpooled . buffer ( ) ;
         
            NBTTagCompound nbt = new NBTTagCompound ( ) ;
            
            acc . ms . currentItemStack . writeToNBT ( nbt ) ;
            acc . ms . currentItemStack = null ;
            
            s . cats [ catId ] . addMerch ( info ) ;
            
            ModNetworkRegistry . sendToAllAddMerchPacket ( info ) ;
            
            ByteBufUtils.writeTag(buf, nbt);

            InputStream stream = new ByteArrayInputStream(buf.array());
           
            
            if ( s . sid == 0 ) {
            
            	pstat = ModDataBase . getPreparedStatement ( "main", "INSERT INTO " + s.cats[catId].getDatabaseTable() + " (name, cost, lim, stack_data) VALUES(?,?,?,?)");
            
            } else {
         
            	pstat = ModDataBase . getPreparedStatement ( "main", "INSERT INTO " + s.cats[catId].getDatabaseTable() + " (name, cost, lim, stack_data, shopId) VALUES(?,?,?,?,?)");
            	pstat.setInt(5, s.sid);

            }
            
            pstat.setString(1, name);
            pstat.setInt(2, Integer.valueOf(cost));
            pstat.setInt(3, Integer.valueOf(limit));
            pstat.setBlob(4, stream);
            pstat.execute();
            
        } catch ( Exception ex ) {
            
        	ex . printStackTrace ( ) ;
            
        }
        
        ModDataBase . closePreparedStatementAndConnection ( pstat ) ;

	}
	
	public static void removeEntryFromShop ( EntityPlayerMP player, Shop s, int catId, int merchId ) {
		
		Merch m = s . cats [ catId ] . getMerch ( merchId ) ;
		
		MineDonate . shops . get ( s . sid ) . cats [ catId ] . removeMerch ( m ) ;
		
		Statement stat = null ;
		
		try {
			
			stat = ModDataBase . getNewStatement ( "main" ) ;
			
			stat . executeUpdate ( "DELETE FROM " + s . cats [ catId ] . getDatabaseTable ( ) + " WHERE id=" + merchId + ";" ) ;
						
		} catch ( Exception ex ) {
			
			ex . printStackTrace ( ) ;
			
		}
		
        ModDataBase . closeStatementAndConnection ( stat ) ;
		
        ModNetworkRegistry . sendToAllRemoveMerchPacket ( s . sid, catId, merchId ) ;
        
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
		
		m . updateNumber ( type, number ) ;
		
		mc . updateMerch ( m . getId ( ), m ) ;
		
        ModNetworkRegistry . sendToAllMerchInfoPacket ( m ) ;

	}
	
	public static void editShopEntryString ( EntityPlayerMP player, Shop s, int catId, int merchId, EditMerchStringPacket . Type type, String str ) {
		
		MerchCategory mc = s . cats [ catId ] ;
		Merch m = mc . getMerch ( merchId ) ;
		
		m . updateString ( type, str ) ;

		mc . updateMerch ( m . getId ( ), m ) ;
		
        ModNetworkRegistry . sendToAllMerchInfoPacket ( m ) ;

	}
	
}

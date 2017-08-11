package ru.alastar.minedonate.rtnl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import ru.alastar.minedonate.MineDonate;
import ru.alastar.minedonate.merch.categories.UsersShops;
import ru.alastar.minedonate.merch.info.ShopInfo;

public class Manager {

	public static void createShop ( String owner, String name ) {
		
        ShopInfo info = new ShopInfo( MineDonate.shops.get(0).cats[4].getMerch().length, MineDonate.getNextShopId(), owner, name, false, null, null, false, MineDonate.cfg.defaultUserShopMoneyType ) ;
       
        MineDonate.shops.get(0).cats[4].addMerch(info);
      
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
        
        MineDonate . loadUserShop ( info . shopId ) ;
        ModNetwork . sendToAllAddMerchPacket ( info ) ;
        
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
	
}

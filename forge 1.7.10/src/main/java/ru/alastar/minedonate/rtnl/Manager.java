package ru.alastar.minedonate.rtnl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import ru.alastar.minedonate.MineDonate;
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

	public static void unFreezeShop ( Shop s, String unFreezer ) {

		ShopInfo si = (ShopInfo) MineDonate.shops.get(0).cats[4].getMerch(s.sid);
		System.err.println(si.name + ">" + s.name);
		
		si . isFreezed = s . isFreezed = false ;
		si . freezer = s . freezer = unFreezer ;
		si . freezReason = s . freezReason = "" ;
		
        ModNetwork . sendToAllAddMerchPacket ( si ) ;

		try {
			
			Statement st = MineDonate . getNewStatement ( ) ;
			
			st . executeQuery ( "UPDATE " + MineDonate.cfg.dbShops + " SET isFreezed = " + 0 + ", freezer=" + unFreezer + ", freezReason='' WHERE id=" + s . sid + ";" ) ;
			
			st . close ( ) ;
			
		} catch ( Exception ex ) {
			
			ex . printStackTrace ( ) ;
			
		}
		
	}

	public static void renameShop ( Shop s, String name ) {
		
		ShopInfo si = (ShopInfo) MineDonate.shops.get(0).cats[4].getMerch(s.sid);
		
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
	
}

package ru.alastar.minedonate.mproc;

import java.sql.ResultSet;
import java.sql.Statement;

import ru.alastar.minedonate.MineDonate;
import ru.alastar.minedonate.merch.Merch;
import ru.log_inil.mc.minedonate.localData.DataOfMoneyProcessor;

public class StandartMoneyProcessor extends AbstractMoneyProcessor {

	public StandartMoneyProcessor ( DataOfMoneyProcessor _domp ) {
		
		super ( _domp ) ;
		
	}

	@Override
	public int process ( Merch m, String buyer, int procMoney ) {
				
		if ( domp . isTwoSideProcessor ) {
			
			if ( MineDonate . checkCatExists ( m . getShopId ( ), m.getCategory ( ) ) ) {
				
				String owner = MineDonate . shops . get ( m . getShopId ( ) ) . owner ;
				
				returnMoney ( owner, procMoney ) ;
				
				owner = null ;
				
			}
			
		}
				
		int currentMoney = getMoneyFor ( buyer ) - procMoney ;
		
		returnMoney ( buyer, -procMoney ) ;

		return currentMoney ;
		
	}

	@Override
	public int canBuy ( Merch m, String buyer, int amount ) {
		
		int procMoney = m . getCost ( ) * amount ;
		
		if ( procMoney > 0 && getMoneyFor ( buyer ) >= procMoney ) {
			
			return procMoney ;
			
		}
		
		return -1 ;
		
	}
	
	@Override
	public void registerPlayer ( String name ) {
		
		try {
			
			Statement stat = MineDonate . getNewStatement ( ) ;
						
			stat . execute ( "INSERT INTO " + domp . dbTable + " (" + domp . dbNameColumn + "," + domp . dbMoneyColumn + ") VALUES ( '" + name + "', " + domp . regMoney + " )" ) ;
            
            stat . close ( ) ;
            
		} catch ( Exception ex ) {
			
			// ex . printStackTrace ( ) ;
			
		}
		
	}

	@Override
	public int getMoneyFor ( String name ) {

		try {
			
			Statement stat = MineDonate . getNewStatement ( ) ;

			ResultSet rs = stat . executeQuery ( "SELECT " + domp . dbMoneyColumn + " FROM " + domp . dbTable + " WHERE " + domp . dbNameColumn + "='" + name + "';" ) ;
            
			boolean w = true ;
            int money = -1 ;
            
            while ( rs . next ( ) && w ) {
            	
                money = rs . getInt ( domp . dbMoneyColumn ) ;
                
                w = false ;
                
            }
            
            rs . close ( ) ;
            stat . close ( ) ;
            
            return money ;
            
		} catch ( Exception ex ) {
			
			ex . printStackTrace ( ) ;
			
		}
		
		return -1 ;
		
	}

	@Override
	public void returnMoney ( String name, int money ) {
		
		try {
			
			Statement stat = MineDonate . getNewStatement ( ) ;
			
			int reversed = getMoneyFor ( name ) + money ;
			
			stat . executeUpdate ( "UPDATE " + domp . dbTable + " SET " + domp . dbMoneyColumn + "=" + reversed + " WHERE " + domp . dbNameColumn + "='" + name + "';" ) ;
            
            stat . close ( ) ;
            
		} catch ( Exception ex ) {
			
			ex . printStackTrace ( ) ;
			
		}

	}

	@Override
	public boolean existsAccount ( String name ) {

		try {
		
			Statement stat = MineDonate . getNewStatement ( ) ;

			ResultSet rs = stat . executeQuery ( "SELECT id FROM " + domp . dbTable + " WHERE " + domp . dbNameColumn + "='" + name + "';" ) ;
            
            while ( rs . next ( ) ) {
            	
                rs . close ( ) ;
                stat . close ( ) ;

                return true;
                
            }
            
		} catch ( Exception ex ) {
			
			ex . printStackTrace ( ) ;
			
		}
		
		return false ;
		
	}



}

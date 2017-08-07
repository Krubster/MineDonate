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
	public void registerPlayer ( String name, java . util . Collection < AbstractMoneyProcessor > pl ) {
		
		try {
			
			Statement stat = MineDonate . getNewStatement ( ) ;
						
			stat . execute ( "INSERT INTO " + domp . dbTable + " (" + domp . dbNameColumn + "," + domp . dbMoneyColumn + ") VALUES ( '" + name . toLowerCase ( ) + "', " + domp . regMoney + " )" ) ;
            
            stat . close ( ) ;
            
			for ( AbstractMoneyProcessor amp : pl ) {
				
				if ( domp . dbTable . equals ( amp . domp . dbTable ) ) {
					
					amp . setMoney ( name, domp . regMoney ) ;

				}
				
			}

		} catch ( Exception ex ) {
			
			ex . printStackTrace ( ) ;
			
		}
		
	}

	@Override
	public int getMoneyFor ( String name ) {

		try {
			
			Statement stat = MineDonate . getNewStatement ( ) ;

			ResultSet rs = stat . executeQuery ( "SELECT " + domp . dbMoneyColumn + " FROM " + domp . dbTable + " WHERE " + domp . dbNameColumn + "='" + name . toLowerCase ( ) + "';" ) ;
            
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
		
		setMoney ( name, getMoneyFor ( name ) + money ) ;
			
	}
	
	@Override
	public void setMoney ( String name, int money ) {
		
		try {
			
			Statement stat = MineDonate . getNewStatement ( ) ;
						
			stat . executeUpdate ( "UPDATE " + domp . dbTable + " SET " + domp . dbMoneyColumn + "=" + money + " WHERE " + domp . dbNameColumn + "='" + name . toLowerCase ( ) + "';" ) ;
            
            stat . close ( ) ;
            
		} catch ( Exception ex ) {
			
			ex . printStackTrace ( ) ;
			
		}

	}

	@Override
	public boolean existsAccount ( String name ) {

		try {
		
			Statement stat = MineDonate . getNewStatement ( ) ;

			ResultSet rs = stat . executeQuery ( "SELECT id FROM " + domp . dbTable + " WHERE " + domp . dbNameColumn + "='" + name . toLowerCase ( ) + "';" ) ;
            
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

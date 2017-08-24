package ru.alastar.minedonate.mproc;

import ru.alastar.minedonate.MineDonate;
import ru.alastar.minedonate.merch.Merch;
import ru.alastar.minedonate.rtnl.ShopLogger;
import ru.log_inil.mc.minedonate.localData.DataOfMoneyProcessor;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.UUID;

public class StandartMoneyProcessor extends AbstractMoneyProcessor {

	public StandartMoneyProcessor ( DataOfMoneyProcessor _domp ) {
		
		super ( _domp ) ;
		
	}

	@Override
	public int process(Merch m, UUID buyer, int procMoney) {
				
		if ( domp . isTwoSideProcessor ) {
			
			if ( MineDonate . checkCatExists ( m . getShopId ( ), m.getCategory ( ) ) ) {
				
				String owner = MineDonate . shops . get ( m . getShopId ( ) ) . owner ;

				if ( ! owner . equals ( "SERVER" ) ) {
					
					returnMoney ( UUID . fromString ( owner ), procMoney ) ;
					
				}
				
				owner = null ;
				
			}
			
		}
				
		int currentMoney = getMoneyFor ( buyer ) - procMoney ;
		
		returnMoney ( buyer, -procMoney ) ;

		return currentMoney ;
		
	}

	@Override
	public int canBuy(Merch m, UUID buyer, int amount) {
		
		int procMoney = m . getCost ( ) * amount ;
		
		if ( procMoney > 0 && getMoneyFor ( buyer ) >= procMoney ) {
			
			return procMoney ;
			
		}
		
		return -1 ;
		
	}
	
	@Override
	public void registerPlayer(UUID id, String name, java.util.Collection<AbstractMoneyProcessor> pl) {
		
		try {
			
			Statement stat = MineDonate . getNewStatement ( domp . dbLinkName ) ;

			stat.execute("INSERT INTO " + domp.dbTable + " (" + domp.dbIdColumn + "," + domp . dbNameColumn + "," + domp.dbMoneyColumn + ") VALUES ( '" + id.toString() + "', '" + name + "', " + domp.regMoney + " )");

			stat . close ( ) ;
            
			if ( MineDonate . cfg . autoFixMoneyProcessorsTableCollisions ) {
				
				for ( AbstractMoneyProcessor amp : pl ) {
					
					if ( domp . dbTable . equals ( amp . domp . dbTable ) ) {
						
						if ( amp . domp . regMoney > 0 ) {

							ShopLogger.logMoney(id.toString(), 0, domp.regMoney, "register>" + amp.domp.moneyType);
						
						}

						amp.setMoney(id, amp.domp.regMoney);

					}
					
				}
				
			}

		} catch ( Exception ex ) {
			
			ex . printStackTrace ( ) ;
			
		}
		
	}

	@Override
	public int getMoneyFor(UUID id) {

		try {
			
			Statement stat = MineDonate . getNewStatement ( domp . dbLinkName ) ;

			ResultSet rs = stat.executeQuery("SELECT " + domp.dbMoneyColumn + " FROM " + domp.dbTable + " WHERE " + domp.dbIdColumn + "='" + id + "';");

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
	public void returnMoney(UUID id, int money) {

		int last = getMoneyFor(id);

		ShopLogger.logMoney(id.toString(), last, last + money, "returnMoney>" + domp.moneyType);

		setMoney(id, last + money);
			
	}
	
	@Override
	public void setMoney(UUID id, int money) {
		
		try {
			
			Statement stat = MineDonate . getNewStatement ( domp . dbLinkName ) ;

			stat.executeUpdate("UPDATE " + domp.dbTable + " SET " + domp.dbMoneyColumn + "=" + money + " WHERE " + domp.dbIdColumn + "='" + id + "';");

			stat . close ( ) ;
            
		} catch ( Exception ex ) {
			
			ex . printStackTrace ( ) ;
			
		}

	}

	@Override
	public boolean existsAccount(UUID id) {

		try {
		
			Statement stat = MineDonate . getNewStatement ( domp . dbLinkName ) ;
			ResultSet rs = stat.executeQuery("SELECT id FROM " + domp.dbTable + " WHERE " + domp.dbIdColumn + "='" + id + "';");

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

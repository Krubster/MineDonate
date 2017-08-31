package ru.alastar.minedonate.mproc;

import ru.alastar.minedonate.MineDonate;
import ru.alastar.minedonate.merch.Merch;
import ru.alastar.minedonate.rtnl.ModDataBase;
import ru.alastar.minedonate.rtnl.ModShopLogger;

import ru.log_inil.mc.minedonate.localData.DataOfMoneyProcessor;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.UUID;

public class StandartMoneyProcessor extends AbstractMoneyProcessor {

	public StandartMoneyProcessor ( DataOfMoneyProcessor _domp ) {
		
		super ( _domp ) ;
		
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
	public int process ( Merch m, UUID buyer, int procMoney) {
				
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
		
		withdrawMoney ( buyer, currentMoney, procMoney ) ;
		
		return currentMoney ;
		
	}

	@Override
	public void registerPlayer ( UUID id, String name, java.util.Collection<AbstractMoneyProcessor> pl ) {
		
		Statement stat = null ;
		
		try {

			if ( ! existsAccount ( id ) ) {
				
				stat = ModDataBase . getNewStatement ( domp . dbLinkName ) ;
	
				stat . execute ( "INSERT INTO " + domp.dbTable + " (" + domp.dbIdColumn + "," + domp . dbNameColumn + "," + domp.dbMoneyColumn + ") VALUES ( '" + id . toString ( ) + "', '" + name + "', " + domp . regMoney + " )");

				ModShopLogger . logMoney ( id . toString ( ), 0, domp . regMoney, "register_noExists>" + domp . moneyType ) ;

    			ModDataBase . closeStatementAndConnection ( stat ) ;
    			
			} else {
				
				setMoney ( id, domp . regMoney ) ;

				ModShopLogger . logMoney ( id . toString ( ), 0, domp . regMoney, "register_alreadyExists>" + domp . moneyType ) ;

			}

		} catch ( Exception ex ) {
			
			ex . printStackTrace ( ) ;
			
		}
		
		ModDataBase . closeStatementAndConnection ( stat ) ;
		
	}

	@Override
	public int getMoneyFor ( UUID id ) {

		Statement stat = null ;
		
		try {
			
			stat = ModDataBase . getNewStatement ( domp . dbLinkName ) ;

			ResultSet rs = stat . executeQuery ( "SELECT " + domp.dbMoneyColumn + " FROM " + domp.dbTable + " WHERE " + domp.dbIdColumn + "='" + id . toString ( ) + "';");

			boolean w = true ;
            int money = -1 ;
            
            while ( rs . next ( ) && w ) {
            	
                money = rs . getInt ( domp . dbMoneyColumn ) ;
                
                w = false ;
                
            }
            
            rs . close ( ) ;
            
    		ModDataBase . closeStatementAndConnection ( stat ) ;
            
            return money ;
            
		} catch ( Exception ex ) {
			
			ex . printStackTrace ( ) ;
			
		}
		
		ModDataBase . closeStatementAndConnection ( stat ) ;
		
		return -1 ;
		
	}

	@Override
	public void withdrawMoney ( UUID id, int finalBalance, int toWithdraw ) {
    	
		ModShopLogger . logMoney ( id . toString ( ), finalBalance + toWithdraw, finalBalance, "withdrawMoney>" + domp . moneyType ) ;

		setMoney ( id, finalBalance ) ;
		
    }

	@Override
	public void returnMoney ( UUID id, int money ) {

		int last = getMoneyFor ( id ) ;

		ModShopLogger . logMoney ( id . toString ( ), last, last + money, "returnMoney>" + domp . moneyType ) ;

		setMoney ( id, last + money ) ;
			
	}
	
	@Override
	public void setMoney ( UUID id, int money ) {
		
		Statement stat = null ;
		
		try {
			
			stat = ModDataBase . getNewStatement ( domp . dbLinkName ) ;

			stat . executeUpdate ( "UPDATE " + domp.dbTable + " SET " + domp.dbMoneyColumn + "=" + money + " WHERE " + domp.dbIdColumn + "='" + id . toString ( ) + "';");
            
		} catch ( Exception ex ) {
			
			ex . printStackTrace ( ) ;
			
		}
		
		ModDataBase . closeStatementAndConnection ( stat ) ;

		if ( MineDonate . getAccountFromCache ( id ) != null ) {
			
			MineDonate . getAccountFromCache ( id ) . putMoney ( domp . moneyType, money ) ;
			
		}
		
	}

	@Override
	public boolean existsAccount ( UUID id ) {

		Statement stat = null ;
		
		try {
		
			stat = ModDataBase . getNewStatement ( domp . dbLinkName ) ;
			ResultSet rs = stat.executeQuery("SELECT id FROM " + domp . dbTable + " WHERE " + domp.dbIdColumn + "='" + id . toString ( ) + "';");

			while ( rs . next ( ) ) {
            	
                rs . close ( ) ;
        		ModDataBase . closeStatementAndConnection ( stat ) ;

                return true;
                
            }
			
			rs . close ( ) ;
			ModDataBase . closeStatementAndConnection ( stat ) ;
            
		} catch ( Exception ex ) {
			
			ex . printStackTrace ( ) ;
			
		}
		
		ModDataBase . closeStatementAndConnection ( stat ) ;
		
		return false ;
		
	}

}

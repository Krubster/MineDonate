package ru.alastar.minedonate.mproc;

import java.util.UUID;

import ru.alastar.minedonate.plugin.PluginHelper;
import ru.alastar.minedonate.plugin.money.MoneyPlugin;
import ru.alastar.minedonate.rtnl.ModShopLogger;

import ru.log_inil.mc.minedonate.localData.DataOfMoneyProcessor;

public class MoneyAccessorPluginMoneyProcessor extends StandartMoneyProcessor {

	MoneyPlugin mp ;
	
	public MoneyAccessorPluginMoneyProcessor ( DataOfMoneyProcessor _domp ) {
		
		super ( _domp ) ;
		
		if ( domp . xProperties == null ) {
			
			System . err . println ( "[ERROR] [" + domp . className + "] domp[" + domp + "].xProperties == null, the plugin can not work!" ) ;
			
		} else {
			
			mp = ( MoneyPlugin ) PluginHelper . getPlugin ( ( String ) domp . xProperties . get ( "modPluginName" ) ) ;
			
		}
		
	}

	@Override
	public void registerPlayer ( UUID id, String name, java.util.Collection < AbstractMoneyProcessor > pl ) {
		
		setMoney ( id, domp . regMoney ) ;

		ModShopLogger . logMoney ( id . toString ( ), 0, domp . regMoney, "register>" + domp . moneyType ) ;
		
	}

	@Override
	public int getMoneyFor ( UUID id ) {
		
		return mp . getBalanceForUser ( id ) ;
		
	}
	
	@Override
	public void withdrawMoney ( UUID id, int finalBalance, int toWithdraw ) {
    	
		ModShopLogger . logMoney ( id . toString ( ), finalBalance + toWithdraw, finalBalance, "withdrawMoney>" + domp . moneyType ) ;

		mp . withdrawMoney ( id, toWithdraw ) ;
		
    }
	
	@Override
	public void returnMoney ( UUID id, int money ) {

		int last = getMoneyFor ( id ) ;

		ModShopLogger . logMoney ( id . toString ( ), last, last + money, "returnMoney>" + domp . moneyType ) ;

		mp . depositPlayer ( id, money ) ;
			
	}
	
	@Override
	public void setMoney ( UUID id, int money ) {
		
		// mp . setMoney ( id, money ) ;
		
	}

	@Override
	public boolean existsAccount ( UUID id ) {
		
		return true ;
		
	}
	
}

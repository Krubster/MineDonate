package ru.alastar.minedonate.mproc;

import ru.alastar.minedonate.merch.Merch;
import ru.log_inil.mc.minedonate.localData.DataOfMoneyProcessor;

public abstract class AbstractMoneyProcessor {

	String mtype ;
	DataOfMoneyProcessor domp ;
	
	public AbstractMoneyProcessor ( DataOfMoneyProcessor _domp ) {
		
		mtype = _domp . moneyType ;
		domp = _domp ;
		
	}
	
	public abstract int process ( Merch m, String buyer, int money ) ;
	public abstract void registerPlayer ( String name, java . util . Collection < AbstractMoneyProcessor > pl ) ;
	public abstract int getMoneyFor ( String name ) ;
	public abstract void returnMoney ( String name, int money ) ;
	public abstract void setMoney ( String name, int money ) ;
	public abstract boolean existsAccount ( String name ) ;
	public abstract int canBuy ( Merch m, String buyer, int amount ) ;

	public boolean isCustomMoneyType ( ) {
		
		return false ;
		
	}
	
	public String getClientMoneyType ( ) {
		
		return null ;
		
	}
	
	public String getMoneyType ( ) {
		
		return mtype ;
		
	}
	
}

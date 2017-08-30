package ru.alastar.minedonate.mproc;

import ru.alastar.minedonate.merch.Merch;
import ru.log_inil.mc.minedonate.localData.DataOfMoneyProcessor;

import java.util.UUID;

public abstract class AbstractMoneyProcessor {

	String mtype ;
	DataOfMoneyProcessor domp ;
	
	public AbstractMoneyProcessor ( DataOfMoneyProcessor _domp ) {
		
		mtype = _domp . moneyType ;
		domp = _domp ;
		
	}

    public abstract int canBuy ( Merch m, UUID buyer, int amount ) ;

    public abstract int process ( Merch m, UUID buyer, int money ) ;

    public abstract void registerPlayer ( UUID id, String name, java . util . Collection < AbstractMoneyProcessor > pl ) ;

    public abstract int getMoneyFor ( UUID id ) ;

    public abstract void withdrawMoney ( UUID id, int finalBalance, int toWithdraw ) ;

    public abstract void returnMoney ( UUID id, int money ) ;

    public abstract void setMoney ( UUID id, int money ) ;

    public abstract boolean existsAccount ( UUID id ) ;

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

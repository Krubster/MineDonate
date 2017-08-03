package ru.log_inil.mc.minedonate.localData.cats;

import ru.log_inil.mc.minedonate.localData.DataOfUICategoryAbstract;
import ru.log_inil.mc.minedonate.localData.DataOfUIElement;

public class DataOfUICategoryUsersShops extends DataOfUICategoryAbstract {

	public boolean dontShowFreezed ;
	public boolean lockGoShopButtonWhenFreezed ;
	public DataOfUIElement lockedGoButton ;

	public DataOfUICategoryUsersShops ( ) {
		
		super ( ) ;

		categoryButtonText = "Users shops" ;
		categoryButtonWidth = 95 ;
		itemBuyButton = new DataOfUIElement ( "Open", 44, 20 ) ;
		dontShowFreezed = false ;
		lockGoShopButtonWhenFreezed = true ;
		lockedGoButton = new DataOfUIElement ( "Locked", 54, 20 ) ;

	}
	
}

package ru.log_inil.mc.minedonate.localData.cats;

import ru.log_inil.mc.minedonate.localData.DataOfUiButton;
import ru.log_inil.mc.minedonate.localData.DataOfUiCategoryAbstract;

public class DataOfUiCategoryUsersShops extends DataOfUiCategoryAbstract {

	public boolean dontShowFreezed ;
	public boolean lockGoShopButtonWhenFreezed ;
	public DataOfUiButton lockedGoButton ;

	public DataOfUiCategoryUsersShops ( ) {
		
		super ( ) ;

		categoryButtonText = "Users shops" ;
		categoryButtonWidth = 95 ;
		itemBuyButton = new DataOfUiButton ( "Open", 44, 20 ) ;
		dontShowFreezed = false ;
		lockGoShopButtonWhenFreezed = true ;
		lockedGoButton = new DataOfUiButton ( "Locked", 54, 20 ) ;

	}
	
}

package ru.log_inil.mc.minedonate.localData.cats;

import ru.log_inil.mc.minedonate.localData.DataOfUICategoryAbstract;
import ru.log_inil.mc.minedonate.localData.DataOfUIElement;
import ru.log_inil.mc.minedonate.localData.DataOfUITextHolderElement;

public class DataOfUICategoryUsersShops extends DataOfUICategoryAbstract {

	public boolean dontShowFreezed ;
	public boolean lockGoShopButtonWhenFreezed ;
	public DataOfUIElement lockedGoButton ;
	public DataOfUIElement viewMyShopsButton ;
	public DataOfUIElement createNewShopButton ;
	public DataOfUITextHolderElement createNewShopNameField ;

	public DataOfUICategoryUsersShops ( ) {
		
		super ( ) ;

		categoryButtonText = "Users shops" ;
		categoryButtonWidth = 95 ;
		itemBuyButton = new DataOfUIElement ( "Open", 44, 20 ) ;
		
		dontShowFreezed = false ;
		lockGoShopButtonWhenFreezed = true ;
		
		lockedGoButton = new DataOfUIElement ( "Locked", 54, 20 ) ;

		viewMyShopsButton = new DataOfUIElement ( "My shops", 54, 20 ) ;
		createNewShopButton = new DataOfUIElement ( "Create shop", 64, 20 ) ;
		createNewShopNameField = new DataOfUITextHolderElement ( "", "Entry name", 160, 20 ) ;
		
	}
	
}

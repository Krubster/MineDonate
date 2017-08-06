package ru.log_inil.mc.minedonate.localData.cats;

import ru.log_inil.mc.minedonate.localData.DataOfUICategoryAbstract;
import ru.log_inil.mc.minedonate.localData.DataOfUIElement;

public class DataOfUICategoryItemsNBlocks extends DataOfUICategoryAbstract {
	
	public String itemLeft ;
	
	public DataOfUIElement addButton ;
	
	public DataOfUICategoryItemsNBlocks ( ) {
		
		super ( ) ;

		categoryButtonText = "Items" ;
		categoryButtonWidth = 45 ;
		itemLeft = "Left: " ;
		
		addButton = new DataOfUIElement ( "Add", 44, 20 ) ;
		
	}
	
}

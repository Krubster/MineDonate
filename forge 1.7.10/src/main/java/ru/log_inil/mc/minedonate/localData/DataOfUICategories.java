package ru.log_inil.mc.minedonate.localData;

import ru.log_inil.mc.minedonate.localData.cats.*;

public class DataOfUICategories {

	public DataOfUICategoryEntites entities ;
	public DataOfUICategoryItemsNBlocks itemsAndBlocks ;
	public DataOfUICategoryPrivelegies privelegies ;
	public DataOfUICategoryRegions regions ;
	public DataOfUICategoryUsersShops usersShops ;

	public DataOfUICategories ( ) {
		
		entities = new DataOfUICategoryEntites ( ) ;
		itemsAndBlocks = new DataOfUICategoryItemsNBlocks ( ) ;
		privelegies = new DataOfUICategoryPrivelegies ( ) ;
		regions = new DataOfUICategoryRegions ( ) ;
		usersShops = new DataOfUICategoryUsersShops ( ) ;
		
	}
	
}

package ru.log_inil.mc.minedonate.localData;

import ru.log_inil.mc.minedonate.localData.cats.DataOfUiCategoryEntites;
import ru.log_inil.mc.minedonate.localData.cats.DataOfUiCategoryItemsNBlocks;
import ru.log_inil.mc.minedonate.localData.cats.DataOfUiCategoryPrivelegies;
import ru.log_inil.mc.minedonate.localData.cats.DataOfUiCategoryRegions;
import ru.log_inil.mc.minedonate.localData.cats.DataOfUiCategoryUsersShops;

public class DataOfUiCategories {

	public DataOfUiCategoryEntites entities ;
	public DataOfUiCategoryItemsNBlocks itemsAndBlocks ;
	public DataOfUiCategoryPrivelegies privelegies ;
	public DataOfUiCategoryRegions regions ;
	public DataOfUiCategoryUsersShops usersShops ;

	public DataOfUiCategories ( ) {
		
		entities = new DataOfUiCategoryEntites ( ) ;
		itemsAndBlocks = new DataOfUiCategoryItemsNBlocks ( ) ;
		privelegies = new DataOfUiCategoryPrivelegies ( ) ;
		regions = new DataOfUiCategoryRegions ( ) ;
		usersShops = new DataOfUiCategoryUsersShops ( ) ;
		
	}
	
}

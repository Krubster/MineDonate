package ru.log_inil.mc.minedonate.localData;

public abstract class DataOfUiCategoryAbstract {

	public String categoryButtonText ;
	public int categoryButtonWidth ;
	
	public DataOfUiButton itemBuyButton ;
	
	public String pricePrefix ;
	public String priceSuffix ;

	public DataOfUiCategoryAbstract ( ) {
		
		categoryButtonText = "Category" ;
		categoryButtonWidth = 55 ;
		itemBuyButton = new DataOfUiButton ( "Buy", 44, 20 ) ;
		pricePrefix = "Price: " ;
		priceSuffix = "" ;
		
	}
	
}

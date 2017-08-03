package ru.log_inil.mc.minedonate.localData;

public abstract class DataOfUICategoryAbstract {

	public String categoryButtonText ;
	public int categoryButtonWidth ;
	
	public DataOfUIElement itemBuyButton ;
	
	public String pricePrefix ;
	public String priceSuffix ;

	public DataOfUICategoryAbstract ( ) {
		
		categoryButtonText = "Category" ;
		categoryButtonWidth = 55 ;
		itemBuyButton = new DataOfUIElement ( "Buy", 44, 20 ) ;
		pricePrefix = "Price: " ;
		priceSuffix = "" ;
		
	}
	
}

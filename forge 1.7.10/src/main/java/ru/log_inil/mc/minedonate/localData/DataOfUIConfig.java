package ru.log_inil.mc.minedonate.localData;

public class DataOfUIConfig {

	public DataOfUICategories cats ;

	public int subCategoryButtonHeight ;
	
	public DataOfUIElement exitButton ;
	public DataOfUIElement returnButton ;
	
	public String loadingText ;
	
	public boolean addSearchButton ;
	public DataOfUIElement searchButton ;
	public DataOfUITextHolderElement searchField ;
	
	public boolean bindF5RefreshButton ;
	
	public DataOfUIMoneyGroup [ ] moneyGroups ;
	
	public DataOfUIConfig ( ) {
		
		cats = new DataOfUICategories ( ) ;
		
		subCategoryButtonHeight = 20 ;
		
		exitButton = new DataOfUIElement ( "Exit", 26, 20 ) ;
		returnButton = new DataOfUIElement ( "Return", 36, 20 ) ;
		
		loadingText = "Loading data..." ;
		
		addSearchButton = true ;
		searchButton = new DataOfUIElement ( "Search", 40, 20 ) ;
		searchField = new DataOfUITextHolderElement ( "", "Entry name", 160, 20 ) ;
		
		bindF5RefreshButton = false ;
		
		moneyGroups = new DataOfUIMoneyGroup [ ] { new DataOfUIMoneyGroup ( "rub", "minedonate:/images/rub.png" ) } ;
		
	}
	
	
}

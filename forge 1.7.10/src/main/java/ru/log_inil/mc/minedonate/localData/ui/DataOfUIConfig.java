package ru.log_inil.mc.minedonate.localData.ui;

public class DataOfUIConfig {

	public DataOfUICategories cats ;
	public DataOfUIFrames frames ;

	//public int subCategoryButtonHeight ;
	
	public DataOfUIElement exitButton ;
	public DataOfUIElement returnButton ;
	
	public String loadingText ;
	
	public boolean addSearchButton ;
	public DataOfUIElement searchButton ;
	public DataOfUITextHolderElement searchField ;
		
	public DataOfUIMoneyGroup [ ] moneyGroups ;
	public DataOfUILang lang ;
	
	public DataOfUIElement mergeButton ;

	public DataOfUIConfig ( ) {
		
		cats = new DataOfUICategories ( ) ;
		frames = new DataOfUIFrames ( ) ;
		
		//subCategoryButtonHeight = 20 ;
		
		exitButton = new DataOfUIElement ( "Exit", 35, 20 ) ;
		returnButton = new DataOfUIElement ( "Return", 45, 20 ) ;
		
		loadingText = "Loading data..." ;
		
		addSearchButton = true ;
		searchButton = new DataOfUIElement ( "Search", 55, 20 ) ;
		searchField = new DataOfUITextHolderElement ( "", "Entry name", 160, 20 ) ;
				
		moneyGroups = new DataOfUIMoneyGroup [ ] { new DataOfUIMoneyGroup ( "rub", "minedonate:images/rub.png" ), new DataOfUIMoneyGroup ( "coin", "minedonate:images/coin.png" ) } ;
		lang = new DataOfUILang ( ) ;
		
		mergeButton = new DataOfUIElement ( "Accept", 40, 20 ) ;

	}
	
	
}

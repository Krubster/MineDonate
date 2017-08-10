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
	
	public boolean bindF5RefreshButton ;
	
	public DataOfUIMoneyGroup [ ] moneyGroups ;
	public DataOfUILang lang ;
	
	public DataOfUIElement mergeButton ;

	public DataOfUIConfig ( ) {
		
		cats = new DataOfUICategories ( ) ;
		frames = new DataOfUIFrames ( ) ;
		
		//subCategoryButtonHeight = 20 ;
		
		exitButton = new DataOfUIElement ( "Exit", 30, 20 ) ;
		returnButton = new DataOfUIElement ( "Return", 36, 20 ) ;
		
		loadingText = "Loading data..." ;
		
		addSearchButton = true ;
		searchButton = new DataOfUIElement ( "Search", 40, 20 ) ;
		searchField = new DataOfUITextHolderElement ( "", "Entry name", 160, 20 ) ;
		
		bindF5RefreshButton = false ;
		
		moneyGroups = new DataOfUIMoneyGroup [ ] { new DataOfUIMoneyGroup ( "rub", "minedonate:images/rub.png" ) } ;
		lang = new DataOfUILang ( ) ;
		
		mergeButton = new DataOfUIElement ( "Accept", 40, 20 ) ;

	}
	
	
}

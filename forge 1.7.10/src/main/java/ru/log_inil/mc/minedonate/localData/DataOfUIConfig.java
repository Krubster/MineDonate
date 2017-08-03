package ru.log_inil.mc.minedonate.localData;

public class DataOfUIConfig {

	public DataOfUiCategories cats ;
	public String moneyLinePrefix ;
	public String moneyLineSuffix ;
	public DataOfUiButton exitButton ;
	public String loadingText ;
	public boolean addSearchButton ;
	public DataOfUiButton searchButton ;
	public boolean bindF5RefreshButton ;
	
	public DataOfUIConfig ( ) {
		
		cats = new DataOfUiCategories ( ) ;
		
		moneyLinePrefix = "Your money: " ;
		moneyLineSuffix = "" ;
		
		exitButton = new DataOfUiButton ( "Exit", 20, 20 ) ;
		
		loadingText = "Loading data..." ;
		
		addSearchButton = true ;
		searchButton = new DataOfUiButton ( "?????", 40, 20 ) ;

		bindF5RefreshButton = false ;
		
	}
	
	
}

package ru.log_inil.mc.minedonate.localData;

import ru.alastar.minedonate.mproc.StandartMoneyProcessor;

public class DataOfConfig {

	public String dbHost = "localhost";
	public int dbPort = 3306 ;
	public String dbName = "shop";
	public String dbUser = "username" ;
	public String dbPassword = "password" ;
	
	public boolean sellItems = true ;
	public String dbItems = "md_items" ;
	public String itemsMoneyType = "coin" ;

	public boolean sellPrivelegies = true ;
	public String dbPrivelegies = "md_privelegies" ;
	public String privelegiesMoneyType = "rub" ;

	public boolean sellRegions = false ;
	public String dbRegions = "md_regions" ;
	public String regionMoneyType = "rub" ;

	public boolean sellEntities = true ;
	public String dbEntities = "md_entities" ;
	public String entitiesMoneyType = "coin" ;

	public boolean userShops = true;
	public String dbUserItems = "md_userItems" ;
	public String dbshops = "md_shops" ;
	public String defaultUserShopMoneyType = "coin" ;

	public DataOfMoneyProcessor [ ] moneyProcessors ;

	public boolean db_log = true;
	public String dbLogs = "md_logs";

	public String sessionPassword = "1234";

	public DataOfConfig ( ) {
		
		moneyProcessors = new DataOfMoneyProcessor [ ] { 
				new DataOfMoneyProcessor ( "rub", StandartMoneyProcessor . class . getName ( ), "md_accounts", "name", "money", false ),
		} ;
		
	}
	
}

package ru.log_inil.mc.minedonate.localData;

public class DataOfConfig {

	public String dbHost = "localhost";
	public int dbPort = 3306 ;
	public String dbName = "shop";
	public String dbUser = "username" ;
	public String dbPassword = "password" ;
	
	public String dbAccounts = "md_accounts" ;
	public String dbAccountsNameColumn = "name" ;
	public String dbAccountsMoneyColumn = "money" ;
	
	public boolean sellItems = true ;
	public String dbItems = "md_items" ;
	
	public boolean sellPrivelegies = true ;
	public String dbPrivelegies = "md_privelegies" ;
	
	public boolean sellRegions = false ;
	public String dbRegions = "md_regions" ;
	
	public boolean sellEntities = true ;
	public String dbEntities = "md_entities" ;
	
	public boolean userShops = true;
	public String dbUserItems = "md_userItems" ;
	public String dbshops = "md_shops" ;

	public int regMoney = 0 ;

	public boolean db_log = true;
	public String dbLogs = "md_logs";

	public String sessionPassword = "1234";

	public DataOfConfig ( ) {
		
	}
	
}

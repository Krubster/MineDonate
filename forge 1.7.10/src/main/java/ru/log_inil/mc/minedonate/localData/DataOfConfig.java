package ru.log_inil.mc.minedonate.localData;

import ru.alastar.minedonate.mproc.StandartMoneyProcessor;
import ru.alastar.minedonate.plugin.bukkit.PermissionsBukkitPlugin;
import ru.alastar.minedonate.plugin.bukkit.WorldGuardBukkitPlugin;

public class DataOfConfig {

	public boolean enable = false ;
	
	public String dbHost = "localhost";
	public int dbPort = 3306 ;
	public String dbName = "shop";
	public String dbUser = "username" ;
	public String dbPassword = "password" ;

	public String dbUsers = "md_accounts" ;
	public String dbUsersIdColumn = "UUID";
	
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
	public String dbShops = "md_shops" ;
	public String defaultUserShopMoneyType = "coin" ;
	public int maxUsersShopsCount = 3 ;
	public boolean defaultUserAllowShopCreate = true ;
	
	public boolean autoFixMoneyProcessorsTableCollisions = true ;
	public DataOfMoneyProcessor [ ] moneyProcessors ;

	public boolean sendLogToDB = true;
	public String dbLogs = "md_logs";
	
	public boolean enablePermissionsMode = false ;
	public String dbModPermissionsTable = "md_perms" ;
	public DataOfPermissionEntry [ ] permissionsTriggerList ;

	public String permissionsPluginClassName ;
	public String worldGuardPluginClassName ;

	public DataOfConfig ( ) {
		
		moneyProcessors = new DataOfMoneyProcessor [ ] {
				new DataOfMoneyProcessor("rub", StandartMoneyProcessor.class.getName(), "md_accounts", "UUID", "money", false),
				new DataOfMoneyProcessor("coin", StandartMoneyProcessor.class.getName(), "md_accounts", "UUID", "coins", false),
		} ;
		
		permissionsTriggerList = new DataOfPermissionEntry [ ] { new DataOfPermissionEntry ( "minedonate.default", new String [ ] { "default" } ), new DataOfPermissionEntry ( "minedonate.moderation", new String [ ] { "default", "moder" } ) } ;
	
		permissionsPluginClassName = PermissionsBukkitPlugin . class . getName ( ) ;
		worldGuardPluginClassName = WorldGuardBukkitPlugin . class . getName ( ) ;
		
	}
	
}

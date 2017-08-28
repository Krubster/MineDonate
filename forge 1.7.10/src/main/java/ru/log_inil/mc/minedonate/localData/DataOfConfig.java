package ru.log_inil.mc.minedonate.localData;

import java.util.HashMap;
import java.util.Map;

import ru.alastar.minedonate.mproc.StandartMoneyProcessor;
import ru.alastar.minedonate.plugin.permissions.PermissionsBukkitPlugin;
import ru.alastar.minedonate.plugin.permissions.PermissionsPlugin;
import ru.alastar.minedonate.plugin.permissions.PermissionsPluginReflection;
import ru.alastar.minedonate.plugin.worldProtection.WorldGuardBukkitPlugin;
import ru.alastar.minedonate.plugin.worldProtection.WorldProtectionPlugin;
import ru.alastar.minedonate.plugin.worldProtection.WorldGuardPluginReflection;

public class DataOfConfig {

	public boolean enable = false ;
	
	public Map < String, DataOfDataBaseLink > dataBases ;

	public String dbUsers ;
	public String dbUsersIdColumn ;
	public String dbUsersNameColumn ;
	public String dbUsersLinkName ;

	public boolean sellItems ;
	public String dbItems ;
	public String itemsMoneyType ;

	public boolean sellPrivelegies ;
	public String dbPrivelegies ;
	public String privelegiesMoneyType ;

	public boolean sellRegions ;
	public String dbRegions ;
	public String regionMoneyType ;

	public boolean sellEntities ;
	public String dbEntities ;
	public String entitiesMoneyType ;

	public boolean userShops ;
	public String dbUserItems ;
	public String dbShops ;
	public String defaultUserShopMoneyType ;
	public int maxUsersShopsCount ;
	public boolean defaultUserAllowShopCreate ;
	
	public boolean autoFixMoneyProcessorsTableCollisions ;
	public DataOfMoneyProcessor [ ] moneyProcessors ;
	
	public boolean enablePermissionsMode ;
	public String dbModPermissionsTable;
	public DataOfPermissionEntry [ ] permissionsTriggerList ;

	public DataOfAccessorPlugin [ ] accessPlugins ;

	public int packetsMaxLimit ;

	public boolean sendLogToDB ;
	public String dbLogs ;
	public String dbLogsLinkName ;
	
	public boolean displayInfoLog ;
	
	public DataOfConfig ( ) {
		
		dataBases = new HashMap < > ( ) ;
		
		dataBases . put ( "main", new DataOfDataBaseLink ( "localhost", 3306, "shop", "username", "password", "com.mysql.jdbc.Driver" ) ) ;
		
		dbUsers = "md_accounts" ;
		dbUsersIdColumn = "UUID";
		dbUsersNameColumn = "name" ;
		dbUsersLinkName = "main" ;
		 
		sellItems = true ;
		dbItems = "md_items" ;
		itemsMoneyType = "coin" ;
		
		sellPrivelegies = true ;
		dbPrivelegies = "md_privelegies" ;
		privelegiesMoneyType = "rub" ;
				
		sellRegions = false ;
		dbRegions = "md_regions" ;
		regionMoneyType = "rub" ;
		
		sellEntities = true ;
		dbEntities = "md_entities" ;
		entitiesMoneyType = "coin" ;
				
		userShops = true ;
		dbUserItems = "md_userItems" ;
		dbShops = "md_shops" ;
		defaultUserShopMoneyType = "coin" ;
		maxUsersShopsCount = 3 ;
		defaultUserAllowShopCreate = true ;
		
		autoFixMoneyProcessorsTableCollisions = true ;
		
		moneyProcessors = new DataOfMoneyProcessor [ ] {
				
			new DataOfMoneyProcessor ( "rub", StandartMoneyProcessor . class . getName ( ), "md_accounts", "UUID", "name", "money", "main", false ),
			new DataOfMoneyProcessor ( "coin", StandartMoneyProcessor . class . getName ( ), "md_accounts", "UUID", "name", "coins", "main", true ),
	
		} ;
		
		enablePermissionsMode = false ;
		dbModPermissionsTable = "md_perms" ;
		
		permissionsTriggerList = new DataOfPermissionEntry [ ] { 
				
			new DataOfPermissionEntry ( "minedonate.default", new String [ ] { "default" } ), 
			new DataOfPermissionEntry ( "minedonate.moderation", new String [ ] { "default", "moder" } ) 
			
		} ;
	
		accessPlugins = new DataOfAccessorPlugin [ ] { 
				
			new DataOfAccessorPlugin ( "permissionsManager", sellPrivelegies || enablePermissionsMode, "PermissionsEx", PermissionsBukkitPlugin . class . getName ( ), PermissionsPluginReflection . class . getName ( ), PermissionsPlugin . class . getName ( ) ),
			new DataOfAccessorPlugin ( "worldProtectionManager", sellRegions, "WorldGuard", WorldGuardBukkitPlugin . class . getName ( ), WorldGuardPluginReflection . class . getName ( ), WorldProtectionPlugin . class . getName ( ) ) 

		} ;
		
		packetsMaxLimit = 3 ;
		
		sendLogToDB = true ;
		dbLogs = "md_logs" ;
		dbLogsLinkName = "main" ;
		
		displayInfoLog = true ;
		
	}
	
}

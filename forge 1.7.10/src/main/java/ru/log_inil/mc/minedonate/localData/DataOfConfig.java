package ru.log_inil.mc.minedonate.localData;

import java.util.HashMap;
import java.util.Map;

import ru.alastar.minedonate.mproc.MoneyAccessorPluginMoneyProcessor;
import ru.alastar.minedonate.mproc.StandartMoneyProcessor;
import ru.alastar.minedonate.plugin.money.MoneyPlugin;
import ru.alastar.minedonate.plugin.money.VaultBukkitPlugin;
import ru.alastar.minedonate.plugin.money.VaultPluginReflection;
import ru.alastar.minedonate.plugin.permissions.PermissionsBukkitPlugin;
import ru.alastar.minedonate.plugin.permissions.PermissionsPlugin;
import ru.alastar.minedonate.plugin.permissions.PermissionsPluginReflection;
import ru.alastar.minedonate.plugin.worldProtection.WorldGuardBukkitPlugin;
import ru.alastar.minedonate.plugin.worldProtection.WorldProtectionPlugin;
import ru.alastar.minedonate.plugin.worldProtection.WorldGuardPluginReflection;

public class DataOfConfig {

	// Мод включен
	public boolean enable = false ;
	
	// Базы
	public Map < String, DataOfDataBaseLink > dataBases ;

	// Параметры таблицы с пользователями(должны иметь uuid(str), name(str), freezShopCreate(bool), freezShopCreateFreezer(str), freezShopCreateReason(str), shopsCount(int) )
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
	
	public boolean forceMoneyUpdatesEveryTime ;
	public DataOfMoneyProcessor [ ] moneyProcessors ;
	
	// Включить режим разрешений
	public boolean enablePermissionsMode ;
	
	// Таблица (внутренняя группа мода -> внутренний пермишен мода)
	public String dbModPermissionsTable;
	
	// Триггеры разрешений (имя пермишена на сервере -> внутренние группы мода)
	public DataOfPermissionEntry [ ] permissionsTriggerList ;

	// Плагины доступа
	public DataOfAccessorPlugin [ ] accessPlugins ;

	
	// Лимит кол-ва пакетных задач от клиента
	public int packetsMaxLimit ;

	
	// Отправлять логи магазига в бд
	public boolean sendShopLogToDB ;
	
	// Таблица с логами магазига
	public String dbShopLog ;
	
	// Имя используемой бд
	public String dbShopLogLinkName ;
	
	// Выводить в консоль доп. инфо
	public boolean displayInfoLog ;
	
	public Map < String, Object > xProperties ;

	public DataOfConfig ( ) {
		
		dataBases = new HashMap < > ( ) ;
		
		dataBases . put ( "main", new DataOfDataBaseLink ( "localhost", 3306, "shop", "username", "password", "com.mysql.jdbc.Driver" ) ) ;
		
		dbUsers = "md_accounts" ;
		dbUsersIdColumn = "UUID";
		dbUsersNameColumn = "name" ;
		dbUsersLinkName = "main" ;
		 
		sellItems = true ;
		dbItems = "md_items" ;
		itemsMoneyType = "vault" ;
		
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
		
		forceMoneyUpdatesEveryTime = false ;
		
		Map < String, Object > vaultMPXProps = new HashMap < > ( ) ;
		vaultMPXProps . put ( "modPluginName", "money.vault" ) ;
		
		moneyProcessors = new DataOfMoneyProcessor [ ] {
				
			new DataOfMoneyProcessor ( true, "rub", StandartMoneyProcessor . class . getName ( ), "md_accounts", "UUID", "name", "money", "main", false ),
			new DataOfMoneyProcessor ( true, "coin", StandartMoneyProcessor . class . getName ( ), "md_accounts", "UUID", "name", "coins", "main", true ),
			new DataOfMoneyProcessor ( true, "vault", MoneyAccessorPluginMoneyProcessor . class . getName ( ), null, null, null, null, null, true, vaultMPXProps ),

		} ;
		
		enablePermissionsMode = false ;
		dbModPermissionsTable = "md_perms" ;
		
		permissionsTriggerList = new DataOfPermissionEntry [ ] { 
				
			new DataOfPermissionEntry ( "minedonate.default", new String [ ] { "default" } ), 
			new DataOfPermissionEntry ( "minedonate.moderation", new String [ ] { "default", "moder" } ) 
			
		} ;
	
		accessPlugins = new DataOfAccessorPlugin [ ] { 
				
			new DataOfAccessorPlugin ( "permissionsManager", sellPrivelegies || enablePermissionsMode, "PermissionsEx", PermissionsBukkitPlugin . class . getName ( ), PermissionsPluginReflection . class . getName ( ), PermissionsPlugin . class . getName ( ) ),
			new DataOfAccessorPlugin ( "worldProtectionManager", sellRegions, "WorldGuard", WorldGuardBukkitPlugin . class . getName ( ), WorldGuardPluginReflection . class . getName ( ), WorldProtectionPlugin . class . getName ( ) ),
			new DataOfAccessorPlugin ( "money.vault", true, "Vault", VaultBukkitPlugin . class . getName ( ), VaultPluginReflection . class . getName ( ), MoneyPlugin . class . getName ( ) ) 

		} ;
		
		packetsMaxLimit = 3 ;
		
		sendShopLogToDB = true ;
		dbShopLog = "md_logs" ;
		dbShopLogLinkName = "main" ;
		
		displayInfoLog = true ;
		
	}
	
}

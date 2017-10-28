package ru.alastar.minedonate;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.*;

import ru.alastar.minedonate.gui.ShopGUI;
import ru.alastar.minedonate.gui.merge.ShopInventoryContainer;
import ru.alastar.minedonate.merch.Merch;
import ru.alastar.minedonate.merch.MerchCategory;
import ru.alastar.minedonate.merch.categories.*;
import ru.alastar.minedonate.mproc.AbstractMoneyProcessor;
import ru.alastar.minedonate.plugin.PluginHelper;
import ru.alastar.minedonate.plugin.permissions.PermissionsPlugin;
import ru.alastar.minedonate.proxies.CommonProxy;
import ru.alastar.minedonate.rtnl.ModDataBase;
import ru.alastar.minedonate.rtnl.common.*;

import ru.log_inil.mc.minedonate.localData.DataOfConfig;
import ru.log_inil.mc.minedonate.localData.DataOfMoneyProcessor;
import ru.log_inil.mc.minedonate.localData.DataOfPermissionEntry;
import ru.log_inil.mc.minedonate.localData.LocalDataInterchange;
import ru.log_inil.mc.minedonate.localData.ui.DataOfUIConfig;

import java.io.File;
import java.sql.*;
import java.util.*;

@Mod(modid = MineDonate.MODID, version = MineDonate.VERSION)
public class MineDonate {

    public static final String MODID = "MineDonate" ;
    public static final String VERSION = "0.7.1.30" ;

    public static boolean m_Enabled = false;

    public static Map < Integer, Shop > shops = new HashMap < > ( ) ;
    public static Map < String, ShopInventoryContainer > mergeContainers = new HashMap < > ( ) ;
    public static Map < Object, List < String > > permissions = new HashMap < > ( ) ;
    public static Map < String, AbstractMoneyProcessor > moneyProcessors = new HashMap < > ( ) ;
	public static Map < UUID, Account > users = new HashMap < > ( ) ;

    public static DataOfConfig cfg;

    @SideOnly(Side.CLIENT)
    public static DataOfUIConfig cfgUI;

    @Mod.Instance("MineDonate")
    private static MineDonate instance;

    @SidedProxy(clientSide = "ru.alastar.minedonate.proxies.ClientProxy", serverSide = "ru.alastar.minedonate.proxies.ServerProxy")
    public static CommonProxy proxy;

    public static MineDonate getInstance ( ) {
        
    	return instance ;
        
    }

    @Mod.EventHandler
    public void preInit ( FMLPreInitializationEvent event ) {

        proxy . preInit ( event ) ;

    }

    @Mod.EventHandler
    public void init ( FMLInitializationEvent event ) {

        proxy . init ( event ) ;
        
        instance = this ;

    }
   
    @Mod.EventHandler
    public void serverStarting ( FMLServerStartingEvent event ) {
    	
    	proxy . serverStarting ( event ) ;
    	
    }
    
    @SideOnly(Side.SERVER)
    public static void loadServerConfig ( ) {

        try {

            cfg = ( DataOfConfig ) LocalDataInterchange . read ( DataOfConfig . class, new File ( "." ), "server" ) ;

        } catch ( Exception ex ) {

            System . err . println ( "Config read error!" ) ;
            ex . printStackTrace ( ) ;

        }

    }

    @SideOnly(Side.SERVER)
	public static void loadServerMerch ( ) {
    	
        try {
        	
        	Shop s ;
        	
        	if ( shops . isEmpty ( ) ) {
        	
        		s = new Shop ( 0, new MerchCategory [ ] { new ItemNBlocks ( 0, 0, cfg . itemsMoneyType ), new Privelegies ( 0, 1, cfg . privelegiesMoneyType ), new Regions ( 0, 2, cfg . regionMoneyType ), new Entities ( 0, 3, cfg . entitiesMoneyType ), new UsersShops ( ) }, "SERVER", "SERVER","Server shop", false, null, null, false ) ;
        		shops . put ( 0, s ) ;
    			
        	} else {
        		
        		s = shops . get ( 0 ) ;
        		
        	}
     	
            for ( MerchCategory mc : s . cats ) { 
            	
            	mc . clearCategory ( ) ;
            	mc . loadCategory ( ) ;
            	
            }
            
        } catch ( Exception ex ) {
        	
            MineDonate . m_Enabled = false ;

            ex . printStackTrace ( ) ;
            
        }
        
    }
    
    public static void loadMoneyProccessors ( ) {
    	
        try {

	        for ( DataOfMoneyProcessor domp : cfg . moneyProcessors ) {
	        	
	        	if ( domp != null && domp . load ) {
	        		
	        		try {
	
	            		moneyProcessors . put ( domp . moneyType, ( AbstractMoneyProcessor ) MineDonate . class . getClassLoader ( ) . loadClass ( domp . className ) . getConstructor ( new Class [ ] { DataOfMoneyProcessor . class } ) . newInstance ( domp ) ) ;
					
	            	} catch ( Exception ex ) {
						
						ex . printStackTrace ( ) ;
						
					}
	        		
	        	}
	        	          	
	        }
        
	    } catch ( Exception ex ) {
	    	
	        MineDonate . m_Enabled = false ;
	
	        ex . printStackTrace ( ) ;
	        
	    }
    
    }
    
	public static boolean checkShopExists ( int shopId ) {

		return shops . containsKey ( shopId ) ;
    	
	}
	
	public static boolean checkShopAndLoad ( int shopId ) {

		if ( ! checkShopExists ( shopId ) ) {
			
			loadUserShop ( shopId ) ;
			return checkShopExists ( shopId ) ;
			
		} else {
			
			return true ;
			
		}
    	
	}
	
	public static boolean checkCatExists ( int shopId, int catId ) {

		return ( checkShopExists ( shopId ) ? shops . get ( shopId ) . cats . length > catId : false ) ;
    	
	}

    public static boolean userShopExistsInDataBase ( int shopId ) {

    	Statement stat = null ;
    	
        try {
            
        	stat = ModDataBase . getNewStatement ( "main" ) ;
            ResultSet rs = stat . executeQuery ( "SELECT " + "id" + " FROM " + cfg.dbShops + " WHERE id=" + shopId + ";" ) ;
            
            while ( rs . next ( ) ) {
            	
                rs . close ( ) ;
        		ModDataBase . closeStatementAndConnection ( stat ) ;
                
                return true;
                
            }

            rs . close ( ) ;
            
        } catch ( Exception ex ) {
            
        	ex . printStackTrace ( ) ;
            
        }

		ModDataBase . closeStatementAndConnection ( stat ) ;

        return false ;

    }
    
    public static int getNextShopId ( ) {
    	
    	Statement stat = null ;

        try {
        	
        	stat = ModDataBase . getNewStatement ( "main" ) ;
            ResultSet rs = stat . executeQuery ( "SHOW TABLE STATUS LIKE '" + cfg.dbShops + "';" ) ;

            while ( rs . next ( ) ) {

            	int i = rs . getInt ( "Auto_increment" ) ;
            	
            	rs . close ( ) ;
        		ModDataBase . closeStatementAndConnection ( stat ) ;

                return i ;

            }
            
        } catch ( Exception ex ) {
            
        	ex . printStackTrace ( ) ;
            
        }

		ModDataBase . closeStatementAndConnection ( stat ) ;

        return -1 ;

    }
    
	public static void loadUserShop ( int shopId ) {
		
		Shop s = null ;
    	Statement stat = null ;

		try {
        	
        	stat = ModDataBase . getNewStatement ( "main" ) ;
            ResultSet rs = stat . executeQuery ( "SELECT * FROM " + cfg.dbShops + " WHERE id=" + shopId + ";" ) ;

            while ( rs . next ( ) ) {
    	
            	ItemNBlocks inb = new ItemNBlocks ( shopId, 0, rs . getString ( "moneyType" ) ) ;
				
				inb . setCustomDBTable ( cfg . dbUserItems ) ;
				inb . setEnabled ( cfg . userShops ) ;
	
	            s = new Shop(shopId, new MerchCategory[]{inb}, rs.getString("UUID"), rs.getString("ownerName"), rs.getString("name"), rs.getBoolean("isFreezed"), rs.getString("freezer"), rs.getString("freezReason"), false);
	            
	            continue ;
            
            }
            
            rs . close ( ) ;
			
		} catch ( Exception ex) {
			
			ex . printStackTrace ( ) ;
			
		}
		
		ModDataBase . closeStatementAndConnection ( stat ) ;
		
		if ( s == null ) {
			
			return ;
			
		}
		
		shops . put ( shopId, s ) ;

		try {
        	
			stat = ModDataBase . getNewStatement ( "main" ) ;
			
            for ( int i = 0; i < shops . get ( shopId ) . cats . length ; i ++ ) {
            	
                if ( shops . get ( shopId ) . cats [ i ] . isEnabled ( ) ) {
                	                    
                    ResultSet rs = stat . executeQuery ( "SELECT * FROM " + shops . get ( shopId ) . cats [ i ] . getDatabaseTable ( ) + " WHERE shopId = " + shopId + ";" ) ;
                    
                    shops . get ( shopId ) . cats [ i ] . loadCategoryFromObject ( rs ) ;
                    
                    rs . close ( ) ;
                    
                }
                
            }
            
        } catch ( Exception ex ) {
            
        	ex . printStackTrace ( ) ;
            
        }
        
		ModDataBase . closeStatementAndConnection ( stat ) ;

	}

	public static String getMoneyType ( int shopId, int catId ) {
		
    	if ( ! checkCatExists ( shopId, catId ) ) { return null ; }
 
		return shops . get ( shopId ) . cats [ catId ] . getMoneyType ( ) ;
		
	}
	
	public static AbstractMoneyProcessor getMoneyProcessor ( String moneyType ) {
		
    	return moneyProcessors . get ( moneyType ) ;
		
	}
    
	public static List < String > getPermissionsByUser ( UUID userName ) {
		
		List < String > l = new ArrayList < > ( ) ;

		if ( cfg . enablePermissionsMode && PluginHelper . getPlugin ( "permissionsManager" ) != null ) {
			
			// Получаем все объекты пермишенов
			for ( DataOfPermissionEntry dopl : cfg . permissionsTriggerList ) {

				// Проверяем пермишен через плагин
				if ( ( ( PermissionsPlugin ) PluginHelper . getPlugin ( "permissionsManager" ) ) . hasPermission ( userName, dopl . permission ) ) {
					
					// Загружаем в лист все пермишены доступных групп мода
					l . addAll ( getPermissionsByGroups ( dopl . groups ) ) ;
					
				}
				
			}
			
		}

		return l ;
		
	}
	
    static List < String > getPermissionsByGroup ( String groupName ) {
    	
    	if ( permissions . containsKey ( groupName ) ) {
    		
    		return permissions . get ( groupName ) ;
    		
    	}
		
		List < String > l = new ArrayList < > ( ) ;

    	Statement stat = null ;

        try {
        	
        	stat = ModDataBase . getNewStatement ( "main" ) ;
            ResultSet rs = stat . executeQuery ( "SELECT * FROM " + cfg . dbModPermissionsTable+ " WHERE groupName = '" + groupName + "';");
            
            while ( rs . next ( ) ) {

                 l . add ( rs . getString ( "permission" ) . toLowerCase ( ) ) ;

            }

            rs . close ( ) ;
         
        } catch ( Exception ex ) {
        	
        	ex . printStackTrace ( ) ;
        	
        }

		ModDataBase . closeStatementAndConnection ( stat ) ;

        permissions . put ( groupName, l ) ;
        
    	return l ;
    	
    }
    
    static List < String > getPermissionsByGroups ( String [ ] groups ) {

    	if ( permissions . containsKey ( groups ) ) {
    		
    		return permissions . get ( groups ) ;
    		
    	}
    	
		List < String > l = new ArrayList < > ( ) ;

		for ( String s : groups ) {

			l . addAll ( getPermissionsByGroup ( s ) ) ;

		}
		
        permissions . put ( groups, l ) ;

		return l ;
		
    }
		
	public static Account getAccount0 ( UUID user, boolean getWithRegister ) {

		Account acc = null ;

		// Проверка в кэше
		if ( users . containsKey ( user ) ) {
			
			acc = users . get ( user ) ;
			
			// необходимо обновить счета
			if ( cfg . forceMoneyUpdatesEveryTime ) {
				
				for ( String k : moneyProcessors . keySet ( ) ) {
					
					acc . putMoney ( k, moneyProcessors . get ( k ) . getMoneyFor ( user ) ) ;
					
				}
				
			}
			
			return acc ;
			
		}

		Statement stat = null ;

		try {
				
			List < String > permissions = getPermissionsByUser ( user ) ;
			stat = ModDataBase . getNewStatement ( "main" ) ;
          
			// Ищем в бд аккаунт по uuid
			ResultSet rs = stat . executeQuery ( "SELECT * FROM " + cfg.dbUsers + " WHERE " + cfg.dbUsersIdColumn + "='" + user.toString() + "';" ) ;
            
			while ( rs . next ( ) ) {
				
				acc = new Account ( rs . getString ( cfg . dbUsersIdColumn ), rs . getString ( cfg . dbUsersNameColumn ), permissions, rs . getBoolean ( "freezShopCreate" ), rs . getString ( "freezShopCreateFreezer" ), rs . getString ( "freezShopCreateReason" ), rs . getInt ( "shopsCount" ) );

			}
			
			rs . close ( ) ;
    	
			ModDataBase . closeStatementAndConnection ( stat ) ;
    	
			boolean registerMoney = false ;
			
    		// Аккаунта нет и нам нужно его создать
			if ( registerMoney = ( acc == null && getWithRegister ) ) {
				
				// Создаем объект и выдаем блок, если нужен
				acc = new Account ( user . toString ( ), Utils . getNameFromUUID ( user ), permissions, ! cfg . defaultUserAllowShopCreate, cfg.defaultUserAllowShopCreate ? "SERVER" : null, cfg.defaultUserAllowShopCreate ? "Properties policy" : null, 0 ) ;

				stat = ModDataBase . getNewStatement ( "main" ) ;
		        
				// Регистрируем аккаунт
				stat . execute ( "INSERT INTO " + cfg . dbUsers + " (" + cfg . dbUsersIdColumn + "," + cfg . dbUsersNameColumn + ", freezShopCreate, freezShopCreateFreezer, freezShopCreateReason) VALUES ( '" + acc . id + "', '" + acc . name + "', " + acc . freezShopCreate + ", '" + acc . freezShopCreateFreezer + "', '" + acc . freezShopCreateReason + "' )" ) ;
	            
	    		ModDataBase . closeStatementAndConnection ( stat ) ;
	    		
			} else if ( ! getWithRegister ) { // Аккаунт не найден и его не надо регистрировать

				return null ;
				
			}
			
			// Проверяем на то, если у аккаунт есть блок, который был выдан при ! defaultUserAllowShopCreate в конфиге
			if ( cfg . defaultUserAllowShopCreate && acc . freezShopCreate && "SERVER" . equals ( acc . freezShopCreateFreezer ) && "Properties policy" . equals ( acc . freezShopCreateReason ) ) {
				
				acc . freezShopCreate = false ;
				acc . freezShopCreateFreezer = acc . freezShopCreateReason = "" ;

			}
			
			users . put ( user, acc ) ;

			if ( registerMoney ) {
				
	            for ( AbstractMoneyProcessor amp : MineDonate . moneyProcessors . values ( ) ) {
		
	                if ( amp . getDataOfMoneyProcessor ( ) . registerUsersWhenNoExists ) {
	                	
	                	amp . registerPlayer ( user, acc . name, MineDonate . moneyProcessors . values ( ) ) ;
	                	
	                }
	            	
	            }
	            
			}
			
			// Получаем деньги для игрока
			for ( String k : moneyProcessors . keySet ( ) ) {
				
				acc . putMoney ( k, moneyProcessors . get ( k ) . getMoneyFor ( user ) ) ;
				
			}
			
		} catch ( Exception ex ) {
			
			ex . printStackTrace ( ) ;
			
		}
				
		ModDataBase . closeStatementAndConnection ( stat ) ;

		return acc ;
		
	}
	
	public static Account getAccount ( EntityPlayer epmp ) {
		
    	return getAccount0 ( epmp . getGameProfile ( ) . getId ( ), false ) ;
		
	}
    
	public static Account getAccountWithRegister ( UUID uid ) {
		
    	return getAccount0 ( uid, true ) ;
		
	}
	
	public static Account getAccountWithoutMoneyRegister ( UUID uid ) {
		
    	return getAccount0 ( uid, true ) ;
		
	}
    
	public static Account getAccountWithoutRegister ( UUID uid ) {
		
    	return getAccount0 ( uid, false ) ;
		
	}
	
	public static Account getAccountFromCache ( UUID uid ) { 
		
		if ( users . containsKey ( uid ) ) {
			
			return users . get ( uid ) ;
			
		}
		
		return null ;
	
	}
		
	public static Account getAccountFromCache ( EntityPlayer user ) { 
	
		return getAccountFromCache ( user . getGameProfile ( ) . getId ( ) ) ;
	
	}
	
    @SideOnly(Side.SERVER)
    public static void logInfo ( Object s ) {
        
    	if ( cfg . displayInfoLog ) {
    	
    		System . out . println ( "[MineDonate] " + s ) ;
    	
    	}
        
    }
    
    @SideOnly(Side.SERVER)
    public static void logError ( Object s ) {
        
    	System . err . println ( "[MineDonate] [ERROR]: " + s ) ;
        
    }
    
    @SideOnly(Side.CLIENT)
    public static void loadClientConfig ( ) {

        cfg = new DataOfConfig();

        try {

            cfgUI = (DataOfUIConfig) LocalDataInterchange.read(DataOfUIConfig.class, Minecraft.getMinecraft().mcDataDir, "client");

        } catch (Exception ex) {

            System.err.println("Config ui read error!");
            ex.printStackTrace();

        }

    }
    
    @SideOnly(Side.CLIENT)
    public static void loadClientMerch ( ) {

    	shops . put ( 0, new Shop ( 0, new MerchCategory[]{new ItemNBlocks(0, 0,  cfg.itemsMoneyType), new Privelegies(0, 1, cfg.privelegiesMoneyType), new Regions(0, 2, cfg.regionMoneyType), new Entities(0, 3, cfg.entitiesMoneyType), new UsersShops()}, "SERVER", "SERVER", "Server shop", false, null, null, false ) ) ;

    }

    @SideOnly(Side.CLIENT)
    public static void addMerch ( int shopId, int catId, Merch info ) {
    	
    	if ( ! checkCatExists ( shopId, catId ) ) { return ; }
        
        shops . get ( shopId ) . cats [ catId ] . addMerch ( info ) ;
        
    }
    
    @SideOnly(Side.CLIENT)
    public static void removeMerch ( int shopId, int catId, int merchId ) {

    	if ( ! checkCatExists ( shopId, catId ) ) { return ; }

        shops . get ( shopId ) . cats [ catId ] . removeMerch ( merchId ) ;
        
    }
    
	public static Account acc ;
	
	public static Account getAccount ( ) {
		
		return acc ;

	}
	
	public static void setAccount ( Account _acc ) {
		
		acc = _acc ;
		
	}

    public static void setMoney ( String moneyType, int money ) {

    	getAccount ( ) . putMoney ( moneyType, money ) ;
    	
    	ShopGUI . instance . moneyArea . updateDrawData ( ) ;
    	
    }
    
    public static void modify ( int shopId, int category, int id, Merch info ) {

    	if ( ! checkCatExists ( shopId, category ) ) { return ; }

        shops . get ( shopId ) . cats [ category ] . updateMerch ( id, info ) ;

    }

}

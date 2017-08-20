package ru.alastar.minedonate;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.client.Minecraft;
import net.minecraft.server.MinecraftServer;

import ru.alastar.minedonate.gui.ShopGUI;
import ru.alastar.minedonate.gui.merge.ShopInventoryContainer;
import ru.alastar.minedonate.merch.Merch;
import ru.alastar.minedonate.merch.categories.*;
import ru.alastar.minedonate.mproc.AbstractMoneyProcessor;
import ru.alastar.minedonate.plugin.PluginHelper;
import ru.alastar.minedonate.proxies.CommonProxy;
import ru.alastar.minedonate.rtnl.Account;
import ru.alastar.minedonate.rtnl.Shop;

import ru.log_inil.mc.minedonate.localData.*;
import ru.log_inil.mc.minedonate.localData.ui.DataOfUIConfig;

import java.io.*;
import java.sql.*;
import java.util.*;

@Mod(modid = MineDonate.MODID, version = MineDonate.VERSION)
public class MineDonate {

    public static final String MODID = "MineDonate";
    public static final String VERSION = "0.7.1.16";

    @SideOnly(Side.SERVER)
    public static Connection m_DB_Connection;

    public static boolean m_Enabled = false;

    public static Map < Integer, Shop > shops = new HashMap < > ( ) ;
    public static Map < String, ShopInventoryContainer > mergeContainers = new HashMap < > ( ) ;
    public static Map < Object, List < String > > permissions = new HashMap < > ( ) ;
    public static Map < String, AbstractMoneyProcessor > moneyProcessors = new HashMap < > ( ) ;

    public static DataOfConfig cfg;

    @SideOnly(Side.CLIENT)
    public static DataOfUIConfig cfgUI;
    public static Map < String, Integer > clientMoney = new HashMap < > ( ) ;

    @Mod.Instance("MineDonate")
    private static MineDonate instance;

    @SidedProxy(clientSide = "ru.alastar.minedonate.proxies.ClientProxy", serverSide = "ru.alastar.minedonate.proxies.ServerProxy")
    public static CommonProxy proxy;

    public static MineDonate getInstance() {
        return instance;
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

            cfg = (DataOfConfig) LocalDataInterchange.read(DataOfConfig.class, new File("."), "server");

        } catch (Exception ex) {

            System . err . println ( "Config read error!" ) ;
            ex . printStackTrace ( ) ;

        }

    }

    public static Statement getNewStatement ( ) throws SQLException {
    	
    	return m_DB_Connection . createStatement ( ) ;
    	
    }
    
    public static Connection getDBConnection ( ) {
    	
    	return m_DB_Connection ;
    	
    }
    
    @SideOnly(Side.SERVER)
    public static void initDataBase ( ) {

        try {

            MinecraftServer.getServer().logInfo("Try connect to database...");

            Class . forName ( "com.mysql.jdbc.Driver" ) . newInstance ( ) ;
            m_DB_Connection = DriverManager . getConnection("jdbc:mysql://" + cfg.dbHost + ":" + cfg.dbPort + "/" + cfg.dbName, cfg.dbUser, cfg.dbPassword ) ;

            MinecraftServer.getServer().logInfo("Connected!");

            loadServerMerch ( ) ;
            m_Enabled = true;

        } catch ( Exception ex ) {
            
            MinecraftServer.getServer().logInfo("An error occured! Disabling feature!");
            m_Enabled = false;

        	ex . printStackTrace ( ) ;
            
        }
                
    }

    @SideOnly(Side.SERVER)
    private static void loadServerMerch ( ) {
    	
        try {
        	
        	if ( shops . isEmpty ( ) ) {
        	
        		Shop s = new Shop ( 0, new MerchCategory [ ] { new ItemNBlocks ( 0, 0, cfg . itemsMoneyType ), new Privelegies ( 0, 1, cfg . privelegiesMoneyType ), new Regions ( 0, 2, cfg . regionMoneyType ), new Entities ( 0, 3, cfg . entitiesMoneyType ), new UsersShops ( ) }, "SERVER", "Server shop", false, null, null, false ) ;
        		shops . put ( 0, s ) ;
    			
        	}
     	
        	Statement stmt ;
        	ResultSet rs ;
        	
            for ( int i = 0; i < shops . get ( 0 ) . cats . length ; i ++ ) {
            	
                if ( shops . get ( 0 ) . cats [ i ] . isEnabled ( ) ) {
                	
                    stmt = m_DB_Connection . createStatement ( ) ;
                    
                    rs = stmt . executeQuery ( "SELECT * FROM " + shops . get ( 0 ) . cats [ i ] . getDatabaseTable ( ) + ";" ) ;
                    
                    shops . get ( 0 ) . cats [ i ] . loadMerchFromDB ( rs ) ;
                    
                    rs . close ( ) ;
                    stmt . close ( ) ;
                    
                }
            }
            
            for ( DataOfMoneyProcessor domp : cfg . moneyProcessors ) {
            	
            	if ( domp != null ) {
            		
            		try {

                		moneyProcessors . put ( domp . moneyType, ( AbstractMoneyProcessor ) MineDonate . class . getClassLoader ( ) . loadClass ( domp . className ) . getConstructor ( new Class [ ] { DataOfMoneyProcessor . class } ) . newInstance ( domp ) ) ;
    				
                	} catch ( Exception ex ) {
    					
    					ex . printStackTrace ( ) ;
    					
    				}
            		
            	}
            	          	
            }
            
        } catch ( Exception ex ) {
        	
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

    public static void modify ( int shopId, int category, int id, Merch info ) {

    	if ( ! checkCatExists ( shopId, category ) ) { return ; }

        shops . get ( shopId ) . cats [ category ] . updateMerch ( id, info ) ;

    }

    public static boolean userShopExistsInDataBase(int shopId) {

        try {
            
        	Statement stmt = getNewStatement ( ) ;
            ResultSet rs = stmt . executeQuery ( "SELECT " + "id" + " FROM " + cfg.dbShops + " WHERE id=" + shopId + ";" ) ;
            
            while ( rs . next ( ) ) {
            	
                rs . close ( ) ;
                stmt . close ( ) ;
                
                return true;
                
            }

            rs . close ( ) ;
            stmt . close ( ) ;
            
        } catch ( SQLException ex ) {
            
        	ex . printStackTrace ( ) ;
            
        }

        return false ;

    }
    
    public static int getNextShopId ( ) {
    	
        try {
        	
        	Statement stmt = getNewStatement ( ) ;
            ResultSet rs = stmt . executeQuery ( "SHOW TABLE STATUS LIKE '" + cfg.dbShops + "';" ) ;

            while ( rs . next ( ) ) {

                return rs . getInt ( "Auto_increment" ) ;

            }
            
            stmt . close ( ) ;

        } catch ( Exception ex ) {
            
        	ex . printStackTrace ( ) ;
            
        }

        return -1;

    }
    

    public static ResultSet getShopData ( int shopId ) {
        
        try {
        	
        	Statement stmt = getNewStatement ( ) ;
            ResultSet rs = stmt . executeQuery ( "SELECT * FROM " + cfg.dbShops + " WHERE id=" + shopId + ";" ) ;

            while ( rs . next ( ) ) {

                return rs;

            }
            
            stmt . close ( ) ;

        } catch ( Exception ex ) {
            
        	ex . printStackTrace ( ) ;
            
        }

        return null;

    }
    
	public static void loadUserShop ( int shopId ) {

		ResultSet sdata = getShopData ( shopId ) ;
		
		if ( sdata == null ) {
			
			return ;
			
		}
		
		Shop s = null ;
		
		try {
			
			ItemNBlocks inb = new ItemNBlocks ( shopId, 0, sdata . getString ( "moneyType" ) ) ;
			
			inb . setCustomDBTable ( cfg . dbUserItems ) ;
			inb . setEnabled ( cfg . userShops ) ;
			
			s = new Shop ( shopId, new MerchCategory [ ] { inb }, sdata . getString ( "owner" ), sdata . getString ( "name" ), sdata . getBoolean ( "isFreezed" ), sdata . getString ( "freezer" ), sdata . getString ( "freezReason" ), false ) ;
			sdata . close ( ) ;
			
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		if ( s == null ) {
			
			return ;
			
		}
		
		shops . put ( shopId, s ) ;

		try {
        	
            for (int i = 0; i < shops . get ( shopId ) . cats . length ; i ++ ) {
            	
                if ( shops . get ( shopId ) . cats [ i ] . isEnabled ( ) ) {
                	
                    Statement stmt = getNewStatement ( ) ;
                    
                    ResultSet rs = stmt . executeQuery ( "SELECT * FROM " + shops . get ( shopId ) . cats [ i ] . getDatabaseTable ( ) + " WHERE shopId = " + shopId + ";" ) ;
                    
                    shops . get ( shopId ) . cats [ i ] . loadMerchFromDB ( rs ) ;
                    
                    rs . close ( ) ;
                    stmt . close ( ) ;
                    
                }
                
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
	}

	public static String getMoneyType ( int shopId, int catId ) {
		
    	if ( ! checkCatExists ( shopId, catId ) ) { return null ; }
 
		return shops . get ( shopId ) . cats [ catId ] . getMoneyType ( ) ;
		
	}
	
	public static AbstractMoneyProcessor getMoneyProcessor ( String moneyType ) {
		
    	return moneyProcessors . get ( moneyType ) ;
		
	}
    
	public static List < String > getPermissionsByUser ( String userName ) {
		
		List < String > l = new ArrayList < > ( ) ;

		if ( cfg . enablePermissionsMode ) {
			
			for ( DataOfPermissionEntry dopl : cfg . permissionsTriggerList ) {

				if ( PluginHelper . pexMgr . hasPermission ( userName, dopl . key ) ) {
					
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

        try {
        	
        	Statement stmt = m_DB_Connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM " + cfg.dbModPermissionsTable+ " WHERE groupName = '" + groupName + "';");
            
            while ( rs . next ( ) ) {

                 l . add ( rs . getString ( "permission" ) . toLowerCase ( ) ) ;

            }

            rs . close ( ) ;
            stmt . close ( ) ;
         
        } catch ( Exception ex ) {
        	
        	ex . printStackTrace ( ) ;
        	
        }

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
	
	public static Map < String, Account > users = new HashMap < > ( ) ;
	
    public static ResultSet getAccountData ( String name ) {
        
        try {
        	
        	Statement stmt = getNewStatement ( ) ;
            ResultSet rs = stmt . executeQuery ( "SELECT * FROM " + cfg.dbUsers + " WHERE " + cfg.dbUsersNameColumn + "='" + name + "';" ) ;

            while ( rs . next ( ) ) {

                return rs;

            }
            
            stmt . close ( ) ;

        } catch ( Exception ex ) {
            
        	ex . printStackTrace ( ) ;
            
        }

        return null;

    }
    
	public static Account getAccountFromCache ( String name ) {

		if ( users . containsKey ( name ) ) {
			
			return users . get ( name ) ;
			
		}
		
		return null ;
	
	}
	
	public static Account getAccount ( String name ) {

		if ( users . containsKey ( name ) ) {
			
			return users . get ( name ) ;
			
		}
		
		Account acc = null ;
		
		try {
			
			ResultSet rs = getAccountData ( name ) ;

			if ( rs != null ) {
				
				acc = new Account ( name, getPermissionsByUser ( name ), rs . getBoolean ( "freezShopCreate" ), rs . getString ( "freezShopCreateFreezer" ), rs . getString ( "freezShopCreateReason" ), rs . getInt ( "shopsCount" ) );
				
			} else {
				
				acc = new Account ( name, getPermissionsByUser ( name ), ! cfg . defaultUserAllowShopCreate, cfg.defaultUserAllowShopCreate ? "SERVER" : null, cfg.defaultUserAllowShopCreate ? "Properties policy" : null, 0 ) ;
				
			}
			
			users . put ( name, acc ) ;

		} catch ( SQLException ex ) {
			
			ex . printStackTrace ( ) ;
			
		}
				
		return acc ;
		
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

    	shops . put ( 0, new Shop ( 0, new MerchCategory[]{new ItemNBlocks(0, 0,  cfg.itemsMoneyType), new Privelegies(0, 1, cfg.privelegiesMoneyType), new Regions(0, 2, cfg.regionMoneyType), new Entities(0, 3, cfg.entitiesMoneyType), new UsersShops()}, "SERVER", "Server shop", false, null, null, false ) ) ;

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

    	clientMoney . put ( moneyType, money ) ;
    	
    	ShopGUI . instance . moneyArea . updateDrawData ( ) ;
    	
    }
	
    public static boolean canAdd ( int shopId, int catId ) {
    
    	return acc == null ? false : ( shopId == 0 ? acc . canEditShop ( "SERVER" ) : true ) ;
    	
    }
    
}

package ru.alastar.minedonate;

import com.mojang.authlib.GameProfile;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
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

import ru.log_inil.mc.minedonate.localData.DataOfConfig;
import ru.log_inil.mc.minedonate.localData.DataOfDataBaseLink;
import ru.log_inil.mc.minedonate.localData.DataOfMoneyProcessor;
import ru.log_inil.mc.minedonate.localData.DataOfPermissionEntry;
import ru.log_inil.mc.minedonate.localData.LocalDataInterchange;
import ru.log_inil.mc.minedonate.localData.ui.DataOfUIConfig;

import java.io.File;
import java.sql.*;
import java.util.*;

@Mod(modid = MineDonate.MODID, version = MineDonate.VERSION)
public class MineDonate {

    public static final String MODID = "MineDonate";
    public static final String VERSION = "0.7.1.20";

    public static boolean m_Enabled = false;

    public static Map < String, Connection > dataBaseConnections = new HashMap < > ( ) ;
    public static Map < Integer, Shop > shops = new HashMap < > ( ) ;
    public static Map < String, ShopInventoryContainer > mergeContainers = new HashMap < > ( ) ;
    public static Map < Object, List < String > > permissions = new HashMap < > ( ) ;
    public static Map < String, AbstractMoneyProcessor > moneyProcessors = new HashMap < > ( ) ;

    public static DataOfConfig cfg;

    @SideOnly(Side.CLIENT)
    public static DataOfUIConfig cfgUI;

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

    public static Statement getNewStatement ( String dbLinkName ) throws Exception {
    	
    	return getDataBaseConnection ( dbLinkName ) . createStatement ( ) ;
    	
    }
    
    public static PreparedStatement getPreparedStatement ( String dbLinkName, String sql ) throws Exception {
        
    	return getDataBaseConnection ( dbLinkName ) . prepareStatement(sql);
        
    }
    
    public static Connection getDataBaseConnection ( String dbLinkName ) throws Exception {
    	
    	return checkAndReconnectDataBase ( dbLinkName, dataBaseConnections . get ( dbLinkName ) ) ;
    	
    }
    
    public static Connection getDataBaseConnection ( String linkName, DataOfDataBaseLink link, boolean doPreLoadClass ) throws Exception {
    	
    	if ( doPreLoadClass && ! "" . equals ( link . preLoadClassName ) ) {
    		
    		 Class . forName ( link . preLoadClassName ) . newInstance ( ) ;
    		 
    	}
    
    	Connection c ;
    	if ( link . hasCustomLink ) {

    		c = DriverManager . getConnection ( link . customLink . replace ( "%host%", link . host ) . replace ( "%port%", Integer . toString ( link . port ) ) . replace ( "%name%", link . name ) + ( link . useUTF8 ? "?useUnicode=true&characterEncoding=UTF-8" : "" ), link . user, link . password ) ;
        	
    		dataBaseConnections . put ( linkName, c ) ;
    		
    	} else {
    		
    		c = DriverManager . getConnection ( "jdbc:mysql://" + link . host + ":" + link . port + "/" + link . name + ( link . useUTF8 ? "?useUnicode=true&characterEncoding=UTF-8" : "" ), link . user, link . password ) ;
    		dataBaseConnections . put ( linkName, c ) ;
    		
    	}
    	
    	return c ;
    	
    }
    
    public static Connection checkAndReconnectDataBase ( String linkName, Connection c ) throws Exception {
    	
    	if ( c . isClosed ( ) ) {
    		
    		return getDataBaseConnection ( linkName, cfg . dataBases . get ( linkName ), false ) ;
 
    	}
    	
    	return c ;
    	
    }
    
    @SideOnly(Side.SERVER)
    public static void initDataBase ( ) {

        try {

            logInfo("Init connections to database's...");

            DataOfDataBaseLink tmpDBLink ;
            
            for ( String linkName : cfg . dataBases . keySet ( ) ) {
            	
                logInfo("Load and try connect db[" + linkName+"]");

            	tmpDBLink = cfg . dataBases . get ( linkName ) ;
            	
            	getDataBaseConnection ( linkName, tmpDBLink, true ) ;
            	
                logInfo("Connected db[" + linkName + "]!");

            }
            
            loadServerMerch ( ) ;
            m_Enabled = true;

        } catch ( Exception ex ) {
            
            logError("An error occured! Disabling feature!");
            m_Enabled = false;

        	ex . printStackTrace ( ) ;
            
        }
                
    }

    @SideOnly(Side.SERVER)
    private static void loadServerMerch ( ) {
    	
        try {
        	
        	if ( shops . isEmpty ( ) ) {
        	
        		Shop s = new Shop ( 0, new MerchCategory [ ] { new ItemNBlocks ( 0, 0, cfg . itemsMoneyType ), new Privelegies ( 0, 1, cfg . privelegiesMoneyType ), new Regions ( 0, 2, cfg . regionMoneyType ), new Entities ( 0, 3, cfg . entitiesMoneyType ), new UsersShops ( ) }, "SERVER", "SERVER","Server shop", false, null, null, false ) ;
        		shops . put ( 0, s ) ;
    			
        	}
     	
        	Statement stmt ;
        	ResultSet rs ;
        	
            for ( int i = 0; i < shops . get ( 0 ) . cats . length ; i ++ ) {
            	
                if ( shops . get ( 0 ) . cats [ i ] . isEnabled ( ) ) {
                	
                    stmt = getNewStatement ( "main" ) ;
                    
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
            
        	Statement stmt = getNewStatement ( "main" ) ;
            ResultSet rs = stmt . executeQuery ( "SELECT " + "id" + " FROM " + cfg.dbShops + " WHERE id=" + shopId + ";" ) ;
            
            while ( rs . next ( ) ) {
            	
                rs . close ( ) ;
                stmt . close ( ) ;
                
                return true;
                
            }

            rs . close ( ) ;
            stmt . close ( ) ;
            
        } catch ( Exception ex ) {
            
        	ex . printStackTrace ( ) ;
            
        }

        return false ;

    }
    
    public static int getNextShopId ( ) {
    	
        try {
        	
        	Statement stmt = getNewStatement ( "main" ) ;
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
        	
        	Statement stmt = getNewStatement ( "main" ) ;
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

            s = new Shop(shopId, new MerchCategory[]{inb}, sdata.getString("UUID"), sdata.getString("ownerName"), sdata.getString("name"), sdata.getBoolean("isFreezed"), sdata.getString("freezer"), sdata.getString("freezReason"), false);
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
                	
                    Statement stmt = getNewStatement ( "main" ) ;
                    
                    ResultSet rs = stmt . executeQuery ( "SELECT * FROM " + shops . get ( shopId ) . cats [ i ] . getDatabaseTable ( ) + " WHERE shopId = " + shopId + ";" ) ;
                    
                    shops . get ( shopId ) . cats [ i ] . loadMerchFromDB ( rs ) ;
                    
                    rs . close ( ) ;
                    stmt . close ( ) ;
                    
                }
                
            }
            
        } catch ( Exception ex ) {
            
        	ex . printStackTrace ( ) ;
            
        }
        
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

		if ( cfg . enablePermissionsMode ) {
			
			for ( DataOfPermissionEntry dopl : cfg . permissionsTriggerList ) {

				if ( PluginHelper . pexMgr . hasPermission ( userName, dopl . permission ) ) {
					
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
        	
        	Statement stmt = getNewStatement ( "main" ) ;
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
	
	public static Map < UUID, Account > users = new HashMap < > ( ) ;
	
    public static ResultSet getAccountData ( UUID user ) {
        
        try {
        	
        	Statement stmt = getNewStatement ( "main" ) ;
            ResultSet rs = stmt.executeQuery("SELECT * FROM " + cfg.dbUsers + " WHERE " + cfg.dbUsersIdColumn + "='" + user.toString() + "';");

            while ( rs . next ( ) ) {

                return rs;

            }
            
            stmt . close ( ) ;

        } catch ( Exception ex ) {
            
        	ex . printStackTrace ( ) ;
            
        }

        return null;

    }
	
	public static Account getAccount ( UUID user ) {

		if ( users . containsKey ( user ) ) {
			
			return users . get ( user ) ;
			
		}
		
		Account acc = null ;
		
		try {
			
			ResultSet rs = getAccountData ( user ) ;

			if ( rs != null ) {
				
				acc = new Account ( rs . getString ( cfg . dbUsersIdColumn ), rs . getString ( cfg . dbUsersNameColumn ), getPermissionsByUser ( user ), rs . getBoolean ( "freezShopCreate" ), rs . getString ( "freezShopCreateFreezer" ), rs . getString ( "freezShopCreateReason" ), rs . getInt ( "shopsCount" ) );
				
			} else {
				
				acc = new Account ( user . toString ( ), getNameFromUUID ( user ), getPermissionsByUser ( user ), ! cfg . defaultUserAllowShopCreate, cfg.defaultUserAllowShopCreate ? "SERVER" : null, cfg.defaultUserAllowShopCreate ? "Properties policy" : null, 0 ) ;
				
			}
			
			for ( String k : moneyProcessors . keySet ( ) ) {
				
				acc . putMoney ( k, moneyProcessors . get ( k ) . getMoneyFor ( user ) ) ;
				
			}
			
			users . put ( user, acc ) ;

		} catch ( SQLException ex ) {
			
			ex . printStackTrace ( ) ;
			
		}
				
		return acc ;
		
	}
	
    @SideOnly(Side.SERVER)
	public static Account getAccount ( EntityPlayer epmp ) {
		
    	return getAccount ( epmp . getGameProfile ( ) . getId ( ) ) ;
		
	}
	
    @SideOnly(Side.SERVER)
	public static Account getAccountFromCache ( EntityPlayer user ) { 
		
		if ( users . containsKey ( user . getGameProfile ( ) . getId ( ) ) ) {
			
			return users . get ( user . getGameProfile ( ) . getId ( ) ) ;
			
		}
		
		return null ;
	
	}
		
    @SideOnly(Side.SERVER)
    public static String getNameFromUUID(UUID id) {
        GameProfile profile = MinecraftServer.getServer().func_152358_ax().func_152652_a(id);
        if (profile != null) {
            return profile.getName();
        } else {
            logError("Null profile, for id[" + id + "]!");
        }
        return "";
    }

    @SideOnly(Side.SERVER)
    public static UUID getUUIDFromName(String name) {
        GameProfile profile = MinecraftServer.getServer().func_152358_ax().func_152655_a(name);
        if (profile != null) {
            return profile.getId();
        } else {
            logError("Null profile for name[" + name + "]!");
        }
        return null;
    }
    
    @SideOnly(Side.SERVER)
    public static UUID getUUIDFromPlayer(EntityPlayerMP serverPlayer) {
        GameProfile profile = MinecraftServer.getServer().func_152358_ax().func_152655_a(serverPlayer.getDisplayName());
        if (profile != null) {
            return profile.getId();
        } else {
            logError("Null profile, for player[" + serverPlayer + "]!");
        }
        return null;
    }

    @SideOnly(Side.SERVER)
    public static void logInfo ( Object s ) {
        
    	System . err . println ( "[MineDonate] [INFO]: " + s) ;
        
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
	
    public static boolean canAdd ( int shopId, int catId ) {
    
    	return acc == null ? false : ( shopId == 0 ? acc . canEditShop ( "SERVER" ) : true ) ;
    	
    }
    
}

package ru.alastar.minedonate;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;

import ru.alastar.minedonate.commands.AddEntityCommand;
import ru.alastar.minedonate.commands.AddItemCommand;
import ru.alastar.minedonate.commands.AdminCommand;
import ru.alastar.minedonate.gui.ShopCategory;
import ru.alastar.minedonate.gui.ShopGUI;
import ru.alastar.minedonate.merch.Merch;
import ru.alastar.minedonate.merch.categories.*;
import ru.alastar.minedonate.merch.info.EntityInfo;
import ru.alastar.minedonate.merch.info.ItemInfo;
import ru.alastar.minedonate.mproc.AbstractMoneyProcessor;
import ru.alastar.minedonate.network.handlers.*;
import ru.alastar.minedonate.network.packets.*;
import ru.alastar.minedonate.plugin.PluginHelper;
import ru.alastar.minedonate.proxies.CommonProxy;

import ru.log_inil.mc.minedonate.localData.DataOfConfig;
import ru.log_inil.mc.minedonate.localData.DataOfMoneyProcessor;
import ru.log_inil.mc.minedonate.localData.DataOfPermissionLine;
import ru.log_inil.mc.minedonate.localData.DataOfUIConfig;
import ru.log_inil.mc.minedonate.localData.LocalDataInterchange;

import java.io.*;
import java.sql.*;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Mod(modid = MineDonate.MODID, version = MineDonate.VERSION, bukkitPlugin = "Vault")
public class MineDonate {

    public static final String MODID = "MineDonate";
    public static final String VERSION = "4.0.7.1";

    @SideOnly(Side.SERVER)
    public static Connection m_DB_Connection;

    public static boolean m_Enabled = false;
    public static int m_Client_Money = 0;

    static GregorianCalendar calendar = new GregorianCalendar();

    public static Map < Integer, Shop > shops = new HashMap < > ( ) ;
    public static Map < String, AbstractMoneyProcessor > moneyProcessors = new HashMap < > ( ) ;
    
    public static Map < String, Integer > clientMoney = new HashMap < > ( ) ;

    public static Map<EntityPlayerMP, AdminSession> m_Admin_Sessions = new HashMap<EntityPlayerMP, AdminSession>();

    /*
    @SideOnly(Side.SERVER)
    public static Object wg_plugin;
    */
    @SideOnly(Side.SERVER)
    private static BufferedWriter m_log;

    public static DataOfConfig cfg;

    @SideOnly(Side.CLIENT)
    public static DataOfUIConfig cfgUI;

    @Mod.Instance("MineDonate")
    private static MineDonate instance;

    @SidedProxy(clientSide = "ru.alastar.minedonate.proxies.ClientProxy",
            serverSide = "ru.alastar.minedonate.proxies.ServerProxy")
    public static CommonProxy proxy;
    public static Class<?> local_player_class;
    public static boolean m_Admin_mode = false;

    public static MineDonate getInstance() {
        return instance;
    }

    public static final SimpleNetworkWrapper networkChannel = NetworkRegistry.INSTANCE.newSimpleChannel(MineDonate.MODID);

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {

        proxy.preInit(event);

    }

    /*
    @Mod.EventHandler
    @SideOnly(Side.SERVER)
    public void fmlLifeCycle(FMLServerStartedEvent event) {
        if (MineDonate.cfg.sellRegions)
            MineDonate.InitWG();
    }*/

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {

        proxy.load(event);

        networkChannel.registerMessage(AccountInfoPacketHandler.class, AccountInfoPacket.class, 0, Side.CLIENT);
        networkChannel.registerMessage(AddMerchPacketHandler.class, AddMerchPacket.class, 1, Side.CLIENT);
        networkChannel.registerMessage(BuyResponsePacketHandler.class, BuyResponsePacket.class, 2, Side.CLIENT);
        networkChannel.registerMessage(BuyPacketHandler.class, BuyPacket.class, 3, Side.SERVER);
        networkChannel.registerMessage(SupportedFeaturesPacketHandler.class, SupportedFeaturesPacket.class, 4, Side.CLIENT);
        networkChannel.registerMessage(RemoveMerchPacketHandler.class, RemoveMerchPacket.class, 5, Side.CLIENT);
        networkChannel.registerMessage(MerchInfoPacketHandler.class, MerchInfoPacket.class, 6, Side.CLIENT);
        networkChannel.registerMessage(NeedUpdateServerPacketHandler.class, NeedUpdatePacket.class, 7, Side.SERVER);
        networkChannel.registerMessage(NeedUpdateClientPacketHandler.class, NeedUpdatePacket.class, 7, Side.CLIENT);
        networkChannel.registerMessage(NeedShopCategoryServerPacketHandler.class, NeedShopCategoryPacket.class, 8, Side.SERVER);
        networkChannel.registerMessage(CategoryPacketHandler.class, CategoryPacket.class, 9, Side.CLIENT);
        networkChannel.registerMessage(MoneyChangedPacketHandler.class, MoneyChangedPacket.class, 10, Side.CLIENT);

        instance = this;

    }
    
    @Mod.EventHandler
    public void serverLoad(FMLServerStartingEvent event) {
    	
        event.registerServerCommand(new AddEntityCommand());
        event.registerServerCommand(new AddItemCommand());
        event.registerServerCommand(new AdminCommand());

    }
    
    @Mod.EventHandler
    public void serverStarting(FMLServerStartingEvent event) {
        proxy.serverStarting(event);
    }
    
    @SideOnly(Side.SERVER)
    public static void loadServerConfig() { // #LOG

        try {

            cfg = (DataOfConfig) LocalDataInterchange.read(DataOfConfig.class, new File("."), "config");

        } catch (Exception ex) {

            System.err.println("Config read error!");
            ex.printStackTrace();

        }

    }

    @SideOnly(Side.CLIENT)
    public static void loadClientConfig() { // #LOG

        cfg = new DataOfConfig();

        try {

            cfgUI = (DataOfUIConfig) LocalDataInterchange.read(DataOfUIConfig.class, Minecraft.getMinecraft().mcDataDir, "ui");

        } catch (Exception ex) {

            System.err.println("Config ui read error!");
            ex.printStackTrace();

        }

    }

    public static Statement getNewStatement ( ) throws SQLException {
    	
    	return m_DB_Connection . createStatement ( ) ;
    	
    }
    
    @SideOnly(Side.SERVER)
    public static void InitDataBase() {

        try {

            MinecraftServer.getServer().logInfo("Initializing database connection...");

            Class.forName("com.mysql.jdbc.Driver").newInstance();
            m_DB_Connection = DriverManager.getConnection("jdbc:mysql://" + cfg.dbHost + ":" + cfg.dbPort + "/" + cfg.dbName + "?useUnicode=true&characterEncoding=utf-8", cfg.dbUser, cfg.dbPassword);

            MinecraftServer.getServer().logInfo("Connected!");

            m_Enabled = true;
            loadMerchServer();

            return;


        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        MinecraftServer.getServer().logInfo("An error occured! Disabling feature!");
        m_Enabled = false;
    }

    /*
    @SideOnly(Side.SERVER)
    private static void InitWG() {
        wg_plugin = Bukkit.getPluginManager().getPlugin("WorldGuard");
        System.out.println("World guard enabled!");
        System.out.println(Bukkit.getPluginManager().getPlugin("WorldGuard").getClass().toString());
        try {
            local_player_class = Class.forName("com.sk89q.worldguard.LocalPlayer", false, wg_plugin.getClass().getClassLoader());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }*/

    @SideOnly(Side.SERVER)
    private static void loadMerchServer ( ) {
    	
        try {
        	
        	if ( shops . isEmpty ( ) ) {
        	
        		Shop s = new Shop ( 0, new MerchCategory [ ] { new ItemNBlocks ( 0, 0, cfg . itemsMoneyType ), new Privelegies ( 0, 1, cfg . privelegiesMoneyType ), new Regions ( 0, 2, cfg . regionMoneyType ), new Entities ( 0, 3, cfg . entitiesMoneyType ), new UsersShops ( ) }, "SERVER", "Server shop", false ) ;
        		shops . put ( 0, s ) ;
        		
    			for ( MerchCategory mc : s . cats ) {
    				
    				mc . subCategories = getSubCategories ( mc.shopId, mc . catId ) ;
    				
    			}
    			
        	}

        	
        	Statement stmt ;
        	ResultSet rs ;
        	
            for ( int i = 0; i < shops . get ( 0 ) . cats . length ; i ++ ) {
            	
                if ( shops . get ( 0 ) . cats [ i ] . isEnabled ( ) ) {
                	
                    stmt = m_DB_Connection . createStatement ( ) ;
                    
                    rs = stmt . executeQuery ( "SELECT * FROM " + shops . get ( 0 ) . cats [ i ] . getDatabase ( ) + ";" ) ;
                    
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
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
    }

    @SideOnly(Side.CLIENT)
    public static void loadMerchClient() {

    	shops . put ( 0, new Shop ( 0, new MerchCategory[]{new ItemNBlocks(0, 0,  cfg.itemsMoneyType), new Privelegies(0, 1, cfg.privelegiesMoneyType), new Regions(0, 2, cfg.regionMoneyType), new Entities(0, 3, cfg.entitiesMoneyType), new UsersShops()}, "SERVER", "Server shop", false ) ) ;

    }

    @SideOnly(Side.SERVER)
    public static void logBuy(Merch bought, EntityPlayerMP by, int amount, String moneyType) {
        if (!cfg.sendLogToDB) {
            try {
                m_log.write(calendar.getTime().toString() + ":" + by.getDisplayName() + ":" + bought.getCategory() + ":" + bought.getBoughtMessage() + ":" + bought.getCost() * amount + ":x" + amount + "\r\n");
                m_log.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Statement stmt;
            try {
                stmt = m_DB_Connection.createStatement();
                String sql;
                sql = "INSERT INTO " + cfg.dbLogs + " (date, bought_by, message, amount, spent) VALUES('" + calendar.getTime().toString() + "', '" + by.getDisplayName() + "', '" + bought.getBoughtMessage() + "', " + amount + "," + bought.getCost() * amount + ")";
                stmt.execute(sql);
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    //Reverse section
    //WARNIGN! REVERSE ONLY MONEY, PRIVILEGIES, REGIONS! I CANT CARE OF BOUGHT ITEMS
    @SideOnly(Side.SERVER)
    public static void reverseAllBoughts(java.util.Date from, java.util.Date to) {
        SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
        File log_dir = new File(System.getProperty("user.dir") + File.separator + "donate_logs"); // #LOG
        for (File log_file : log_dir.listFiles()) {
            try {
                final java.util.Date log_date = dt.parse(log_file.getName());
                if (log_date.getTime() > from.getTime() && log_date.getTime() < to.getTime()) {
                    final BufferedReader reader = new BufferedReader(new FileReader(log_file));
                    String line = "";
                    while ((line = reader.readLine()) != null) {
                        final String player_name = line.split(":")[1];
                        final int cost = Integer.valueOf(line.split(":")[4]);
                        final int category = Integer.valueOf(line.split(":")[2]);
                        final String bought_msg = line.split(":")[3];
                      //  returnMoney(player_name, cost);
                      //  if (shops.get(0).cats[category].canReverse()) {
                      //      shops.get(0).cats[category].reverseFor(line, player_name);
                      //  }
                    }
                    reader.close();
                }
            } catch (ParseException e) {
                e.printStackTrace();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @SideOnly(Side.SERVER)
    private static void logReverse(String player_name, int cost, int reversed) {
        try {
            m_log.write(calendar.getTime().toString() + ":" + player_name + ":" + -1 + ":reverse:" + cost);
            m_log.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SideOnly(Side.SERVER)
    public static void reverseBoughtsFor(EntityPlayerMP by, Date from, Date to) {
        SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
        File log_dir = new File(System.getProperty("user.dir") + File.separator + "donate_logs"); // #LOG
        for (File log_file : log_dir.listFiles()) {
            try {
                final java.util.Date log_date = dt.parse(log_file.getName());
                if (log_date.getTime() > from.getTime() && log_date.getTime() < to.getTime()) {
                    final BufferedReader reader = new BufferedReader(new FileReader(log_file));
                    String line = "";
                    while ((line = reader.readLine()) != null) {
                        final String player_name = line.split(":")[1];
                        final int cost = Integer.valueOf(line.split(":")[4]);
                        final int category = Integer.valueOf(line.split(":")[2]);
                        final String bought_msg = line.split(":")[3];

                        if (player_name == by.getDisplayName()) {
                            //returnMoney(player_name, cost);
                            //if (shops.get(0).cats[category].canReverse()) {
                            //   shops.get(0).cats[category].reverseFor(line, player_name);
                            //}
                        }
                    }
                    reader.close();
                }
            } catch (ParseException e) {
                e.printStackTrace();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    ///End reverse section

    @SideOnly(Side.CLIENT)
    public static void setMoney ( String moneyType, int money ) {

    	clientMoney . put ( moneyType, money ) ;
    	
    	ShopGUI . instance . moneyArea . updateDrawData ( ) ;
    	
    }

    @SideOnly(Side.CLIENT)
    public static void AddMerch(int shopId, int m_category, Merch info) {
        if (m_category < shops.get(shopId).cats.length) {
            shops.get(shopId).cats[m_category].addMerch(info);
        }
    }

    @SideOnly(Side.SERVER)
    public static void initLog() {
        try {
            SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
            String str = dt.format(calendar.getTime());
            File dir = new File(System.getProperty("user.dir") + File.separator + "donate_logs"); // #LOG
            if (!dir.exists())
                dir.mkdir();
            m_log = new BufferedWriter(new FileWriter(System.getProperty("user.dir") + File.separator + "donate_logs" + File.separator + str + ".txt")); // #LOG
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void beginAdminSession(EntityPlayerMP player) {
        m_Admin_Sessions.put(player, new AdminSession());

    }

    public static boolean checkAdminSessionLogin(String pass, EntityPlayerMP player) {
        return MineDonate.cfg.sessionPassword.equalsIgnoreCase(pass) && FMLCommonHandler.instance().getMinecraftServerInstance().getConfigurationManager().func_152596_g(player.getGameProfile());
    }

    public static boolean checkAdminSession(EntityPlayerMP player){
        return m_Admin_Sessions.containsKey(player) && FMLCommonHandler.instance().getMinecraftServerInstance().getConfigurationManager().func_152596_g(player.getGameProfile());
    }

    public static void endAdminSession(EntityPlayerMP player) {
        if (m_Admin_Sessions.containsKey(player))
            m_Admin_Sessions.remove(player);
    }
    
    public static AdminSession getAdminSession(EntityPlayerMP entityPlayer) {
        if(m_Admin_Sessions.containsKey(entityPlayer))
            return m_Admin_Sessions.get(entityPlayer);
        return null;
    }

	public static boolean checkCatExists ( int shopId, int catId ) {

		return ( shops . containsKey ( shopId ) ? shops . get ( shopId ) . cats . length > catId : false ) ;
    	
	}
	
    public static void RemoveMerch(int shopId, int category_id, int merch_id) {

    	if ( ! checkCatExists ( shopId, category_id ) ) { return ; }

        if ( category_id >= 0) {
            shops.get(shopId).cats[category_id].removeMerch(merch_id);
        }
    }

    public static void modify(int shopId, int category, int id, Merch info) {

    	if ( ! checkCatExists ( shopId, category ) ) { return ; }

        shops.get(shopId).cats[category].updateMerch(id, info);

    }

    public static boolean userShopExistsInDataBase(int shopId) {

        Statement stmt = null;
        try {
            stmt = m_DB_Connection.createStatement();
            ResultSet rs = stmt.executeQuery ( "SELECT " + "id" + " FROM " + cfg.dbShops + " WHERE id=" + shopId + ";" ) ;
            while (rs.next()) {
                rs.close();
                stmt.close();
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;

    }

    public static ResultSet getShopData(int shopId) {

        Statement stmt = null;
        try {
            stmt = m_DB_Connection.createStatement();
            ResultSet rs = stmt . executeQuery ( "SELECT * FROM " + cfg.dbShops + " WHERE id=" + shopId + ";" ) ;

            while (rs.next()) {

                return rs;

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;

    }

    public static ShopCategory . SubCategory [ ] getSubCategories ( int shopId, int catId ) {
    	
    	List < ShopCategory . SubCategory > l = new ArrayList < > ( ) ;
        
    	/*
    	Statement stmt = null;
        
        try {
        	
        	stmt = getNewStatement ( ) ;
        	ResultSet rs = stmt . executeQuery ( "SELECT id, displayName FROM " + cfg.dbShopsCategories + " WHERE shopId=" + shopId + " AND catId=" + catId + ";" ) ;

            while ( rs . next ( ) ) {

                l . add ( new ShopCategory . SubCategory ( rs . getInt ( "id" ), rs . getString ( "displayName" ) ) ) ;
                
            }

            rs . close ( ) ;
            stmt . close ( ) ;
            
        } catch ( SQLException ex ) {
        	
            ex . printStackTrace ( ) ;
            
        }
        */
        
        ShopCategory . SubCategory [ ] arr = new ShopCategory . SubCategory [ l . size ( ) ] ;
        
    	return ( ShopCategory . SubCategory [ ] ) l . toArray ( arr ) ;
    	
    }
    
	public static void loadUserShop ( int shopId ) {

		ResultSet sdata = getShopData ( shopId ) ;
		
		if ( sdata == null ) {
			
			return ;
			
		}
		
		Shop s = null ;
		
		try {
			
			s = new Shop ( shopId, new MerchCategory [ ] { new ItemNBlocks ( shopId, 0, sdata . getString ( "moneyType" ) ) . setCustomDBTable ( cfg . dbUserItems ) }, sdata . getString ( "owner" ), sdata . getString ( "name" ), sdata . getBoolean ( "isFreezed") );
			sdata . close ( ) ;
			
			for ( MerchCategory mc : s . cats ) {
				
				mc . subCategories = getSubCategories ( mc.shopId, mc . catId ) ;
				
			}
			
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		if ( s == null ) {
			
			return ;
			
		}
		
		shops . put ( shopId, s ) ;

		try {
        	
            for (int i = 0; i < shops.get(shopId).cats.length; ++i) {
                if (shops.get(shopId).cats[i].isEnabled()) {
                    Statement stmt = m_DB_Connection.createStatement();
                    ResultSet rs = stmt.executeQuery("SELECT * FROM " + shops.get(shopId).cats[i].getDatabase() + " WHERE shopId = " + shopId + ";");
                    shops.get(shopId).cats[i].loadMerchFromDB(rs);
                    rs.close();
                    stmt.close();
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
	
    public static void AddItemToStock(ItemStack heldItem, String name, String cost, String limit) {
        ItemInfo info = new ItemInfo(0, 0, shops.get(0).cats[0].getMerch().length, Integer.valueOf(cost), name, "new merch", Integer.valueOf(limit), heldItem);
        shops.get(0).cats[0].addMerch(info);
        try {
            ByteBuf buf = Unpooled.buffer();
            NBTTagCompound nbt = new NBTTagCompound();
            heldItem.writeToNBT(nbt);
            ByteBufUtils.writeTag(buf, nbt);
            InputStream stream = new ByteArrayInputStream(buf.array());
            PreparedStatement statement = m_DB_Connection.prepareStatement("INSERT INTO " + cfg.dbItems + " (name, info, cost, lim, stack_data) VALUES(?,?,?,?,?)");
            statement.setString(1, name);
            statement.setString(2, "new merch");
            statement.setInt(3, Integer.valueOf(cost));
            statement.setInt(4, Integer.valueOf(limit));
            statement.setBlob(5, stream);
            statement.execute();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void AddEntityBy(AdminSession session, Entity target) {
        session.pending = false;
        String name = session.params[0];
        int cost = Integer.parseInt(session.params[1]);
        int limit = Integer.parseInt(session.params[2]);

        EntityInfo info = new EntityInfo(0, 3, shops.get(0).cats[3].getMerch().length, Integer.valueOf(cost), target, name, limit);
        shops.get(0).cats[3].addMerch(info);
        try {
            ByteBuf buf = Unpooled.buffer();
            ByteBufUtils.writeTag(buf, info.entity_data);
            InputStream stream = new ByteArrayInputStream(buf.array());
            PreparedStatement statement = m_DB_Connection.prepareStatement("INSERT INTO " + cfg.dbEntities + " (name, data, cost, lim) VALUES(?,?,?,?)");
            statement.setString(1, name);
            statement.setBlob(2, stream);
            statement.setInt(3, cost);
            statement.setInt(4, limit);

            statement.execute();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static Map < String, List < String > > permissions = new HashMap < > ( ) ;
    
    static List < String > getPermissionsByGroup ( String groupName ) {
    	
    	if ( permissions . containsKey ( groupName ) ) {
    		
    		return permissions . get ( groupName ) ;
    		
    	}
    	
		List < String > l = new ArrayList < > ( ) ;

		if ( groupName . contains ( "," ) ) {
			
			for ( String s : groupName . split ( "," ) ) {
				
				l . addAll ( getPermissionsByGroup ( s ) ) ;
				
			}
			
	        permissions . put ( groupName, l ) ;

			return l ;
			
		}
		
        try {
        	
        	Statement stmt = m_DB_Connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM " + cfg.dbModPermissionsTable+ " WHERE groupName = '" + groupName + "';");
            
            while ( rs . next ( ) ) {

                 l . add ( rs . getString ( "permission" ) ) ;

            }

            rs.close();
            stmt.close();
         
        } catch ( Exception ex ) {
        	
        	ex . printStackTrace ( ) ;
        	
        }
        
        permissions . put ( groupName, l ) ;
        
    	return l ;
    	
    }
    
	public static String [ ] getPermissionsByUser ( String userName ) {
		
		List < String > l = new ArrayList < > ( ) ;

		if ( cfg . enableInternalServerPermissions ) {
			
			for ( DataOfPermissionLine dopl : cfg . permissionsTriggerList ) {

				if ( PluginHelper . pexMgr . hasPermission ( userName, dopl . key ) ) {
					
					l . addAll ( getPermissionsByGroup ( dopl . groupName ) ) ;
					
				}
				
			}
			
		}
		
		String [ ] r = new String [ l . size ( ) ] ;
		r = l . toArray ( r ) ;
		
		l . clear ( ) ;
		
		return r ;
		
	}

	@SideOnly ( Side . CLIENT )
	public static Account acc ;
	
	@SideOnly ( Side . CLIENT )
	public static Account getAccount ( ) {
		
		return acc ;

	}
	
	@SideOnly ( Side . CLIENT )
	public static void setAccount ( Account _acc ) {
		
		acc = _acc ;
		
	}
	
	
}

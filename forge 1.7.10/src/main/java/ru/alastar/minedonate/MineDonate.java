package ru.alastar.minedonate;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartedEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import org.bukkit.Bukkit;
import ru.alastar.minedonate.merch.IMerch;
import ru.alastar.minedonate.merch.categories.*;
import ru.alastar.minedonate.network.MineDonateNetwork;
import ru.alastar.minedonate.network.handlers.*;
import ru.alastar.minedonate.network.packets.*;
import ru.alastar.minedonate.proxies.CommonProxy;

import java.io.*;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

@Mod(modid = MineDonate.MODID, version = MineDonate.VERSION, bukkitPlugin = "Vault")
public class MineDonate {
    public static final String MODID = "MineDonate";
    public static final String VERSION = "4.0";
    @SideOnly(Side.SERVER)
    public static Connection m_DB_Connection;

    public static boolean m_Enabled = false;
    public static int m_Client_Money = 0;

    static GregorianCalendar calendar = new GregorianCalendar();

    //Merch store
    public static MerchCategory[] m_Categories = new MerchCategory[]{new ItemNBlocks(), new Privelegies(), new Regions(), new Entities()};
    //
    //DOnate accounts
    @SideOnly(Side.SERVER)
    private static String db_accounts;
    //
    @SideOnly(Side.SERVER)
    public static Object wg_plugin;

    //Merch
    @SideOnly(Side.SERVER)
    public static String db_items;
    @SideOnly(Side.SERVER)
    public static String db_privelegies;
    @SideOnly(Side.SERVER)
    public static String db_regions;
    @SideOnly(Side.SERVER)
    public static String db_entities;
    //
    //Features
    public static boolean m_Use_Items;
    public static boolean m_Use_Privelegies;
    public static boolean m_Use_Entities;
    public static boolean m_Use_Regions;
    //

    @SideOnly(Side.SERVER)
    private static BufferedWriter m_log;

    @Mod.Instance("MineDonate")
    private static MineDonate instance;

    @SidedProxy(clientSide = "ru.alastar.minedonate.proxies.ClientProxy",
            serverSide = "ru.alastar.minedonate.proxies.ServerProxy")
    public static CommonProxy proxy;
    public static Class<?> local_player_class;

    public static MineDonate getInstance() {
        return instance;
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        proxy.preInit(event);
    }

    @Mod.EventHandler
    public void fmlLifeCycle(FMLServerStartedEvent event) {
        if (MineDonate.m_Use_Regions)
            MineDonate.InitWG();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.load(event);
        MineDonateNetwork.INSTANCE.registerMessage(AccountInfoPacketHandler.class, AccountInfoPacket.class, 0, Side.CLIENT);
        MineDonateNetwork.INSTANCE.registerMessage(AddMerchPacketHandler.class, AddMerchPacket.class, 1, Side.CLIENT);
        MineDonateNetwork.INSTANCE.registerMessage(BuyResponsePacketHandler.class, BuyResponsePacket.class, 2, Side.CLIENT);
        MineDonateNetwork.INSTANCE.registerMessage(BuyPacketHandler.class, BuyPacket.class, 3, Side.SERVER);
        MineDonateNetwork.INSTANCE.registerMessage(SupportedFeaturesPacketHandler.class, SupportedFeaturesPacket.class, 4, Side.CLIENT);
        MineDonateNetwork.INSTANCE.registerMessage(RemoveMerchPacketHandler.class, RemoveMerchPacket.class, 5, Side.CLIENT);
        MineDonateNetwork.INSTANCE.registerMessage(MerchInfoPacketHandler.class, MerchInfoPacket.class, 6, Side.CLIENT);

        instance = this;
    }

    @SideOnly(Side.SERVER)
    public static void InitDataBase() {
        try {
            MinecraftServer.getServer().logInfo("Initializing database connection...");

            BufferedReader br = new BufferedReader(new FileReader(System.getProperty("user.dir") + "\\donate.cfg"));
            String db_host = br.readLine().split("=")[1];
            String db_name = br.readLine().split("=")[1];
            String db_user = br.readLine().split("=")[1];
            String db_pass = br.readLine().split("=").length > 1 ? br.readLine().split("=")[1] : "";
            db_accounts = br.readLine().split("=")[1];
            m_Use_Items = Boolean.parseBoolean(br.readLine().split("=")[1]);
            db_items = br.readLine().split("=")[1];
            m_Use_Privelegies = Boolean.parseBoolean(br.readLine().split("=")[1]);
            db_privelegies = br.readLine().split("=")[1];
            m_Use_Regions = Boolean.parseBoolean(br.readLine().split("=")[1]);
            db_regions = br.readLine().split("=")[1];
            m_Use_Entities = Boolean.parseBoolean(br.readLine().split("=")[1]);
            db_entities = br.readLine().split("=")[1];

            br.close();
            Class.forName("com.mysql.jdbc.Driver").newInstance();

            m_DB_Connection = DriverManager.getConnection("jdbc:mysql://" + db_host + "/" + db_name + "?" +
                    "user=" + db_user + "&password=" + db_pass);
            MinecraftServer.getServer().logInfo("Connected!");
            m_Enabled = true;
            LoadMerch();
            return;
        } catch (IOException e) {
            e.printStackTrace();
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

    @SideOnly(Side.SERVER)
    public static void InitWG() {
        wg_plugin = Bukkit.getPluginManager().getPlugin("WorldGuard");
        System.out.println("World guard enabled!");
        System.out.println(Bukkit.getPluginManager().getPlugin("WorldGuard").getClass().toString());
        try {
            local_player_class = Class.forName("com.sk89q.worldguard.LocalPlayer", false, wg_plugin.getClass().getClassLoader());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    @SideOnly(Side.SERVER)
    private static void LoadMerch() {
        try {
            for (int i = 0; i < m_Categories.length; ++i) {
                if (m_Categories[i].isEnabled()) {
                    Statement stmt = m_DB_Connection.createStatement();
                    ResultSet rs = stmt.executeQuery("SELECT * FROM " + m_Categories[i].getDatabase() + ";");
                    m_Categories[i].loadMerchFromDB(rs);
                    rs.close();
                    stmt.close();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @SideOnly(Side.SERVER)
    public static void RegisterPlayer(EntityPlayerMP player) {
        String name = player.getDisplayName();
        Statement stmt = null;
        try {
            stmt = m_DB_Connection.createStatement();
            String sql;
            sql = "INSERT INTO " + db_accounts + " (name, money) VALUES('" + name + "', 100000)";
            stmt.execute(sql);
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @SideOnly(Side.SERVER)
    public static int getMoneyFor(String player) {
        int money = 0;
        Statement stmt = null;
        try {
            stmt = m_DB_Connection.createStatement();
            String sql;
            sql = "SELECT money FROM " + db_accounts + " WHERE name='" + player + "';";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                money = rs.getInt("money");
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return money;
    }

    @SideOnly(Side.SERVER)
    public static void WithdrawMoney(int result, EntityPlayerMP serverPlayer) {
        Statement stmt = null;
        String name = serverPlayer.getDisplayName();
        try {
            stmt = m_DB_Connection.createStatement();
            String sql;
            sql = "UPDATE " + db_accounts + " SET money=" + result + " WHERE name='" + name + "';";
            stmt.executeUpdate(sql);
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @SideOnly(Side.SERVER)
    public static void logBuy(IMerch bought, EntityPlayerMP by, int amount) {
        try {
            m_log.write(calendar.getTime().toString() + ":" + by.getDisplayName() + ":" + bought.getCategory() + ":" + bought.getBoughtMessage() + ":" + bought.getCost() * amount + ":x" +amount+ "\r\n");
            m_log.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Reverse section
    //WARNIGN! REVERSE ONLY MONEY, PRIVILEGIES, REGIONS! I CANT CARE OF BOUGHT ITEMS
    @SideOnly(Side.SERVER)
    public static void reverseAllBoughts(java.util.Date from, java.util.Date to) {
        SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
        File log_dir = new File(System.getProperty("user.dir") + "\\donate_logs");
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
                        returnMoney(player_name, cost);
                        if (m_Categories[category].canReverse()) {
                            m_Categories[category].reverseFor(line, player_name);
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

    @SideOnly(Side.SERVER)
    private static void returnMoney(String player_name, int cost) {
        Statement stmt = null;
        try {
            stmt = m_DB_Connection.createStatement();
            String sql;
            int reversed = getMoneyFor(player_name) + cost;
            sql = "UPDATE " + db_accounts + " SET money=" + reversed + " WHERE name='" + player_name + "';";
            logReverse(player_name, cost, reversed);
            stmt.executeUpdate(sql);
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
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
        File log_dir = new File(System.getProperty("user.dir") + "\\donate_logs");
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
                            returnMoney(player_name, cost);
                            if (m_Categories[category].canReverse()) {
                                m_Categories[category].reverseFor(line, player_name);
                            }
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

    @SideOnly(Side.SERVER)
    public static boolean ExistsAccount(EntityPlayer player) {
        String name = player.getDisplayName();
        Statement stmt = null;
        try {
            stmt = m_DB_Connection.createStatement();
            String sql;
            sql = "SELECT money FROM " + db_accounts + " WHERE name='" + name + "';";
            ResultSet rs = stmt.executeQuery(sql);
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
    
    @SideOnly(Side.CLIENT)
    public static void SetMoney(int money) {
        m_Client_Money = money;
    }

    @SideOnly(Side.CLIENT)
    public static void AddMerch(int m_category, IMerch info) {
        if (m_category < m_Categories.length) {
            m_Categories[m_category].addMerch(info);
        }
    }

    @SideOnly(Side.SERVER)
    public static void initLog() {
        try {
            SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
            String str = dt.format(calendar.getTime());
            File dir = new File(System.getProperty("user.dir") + "\\donate_logs");
            if (!dir.exists())
                dir.mkdir();
            m_log = new BufferedWriter(new FileWriter(System.getProperty("user.dir") + "\\donate_logs\\" + str + ".txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void RemoveMerch(int category_id, int merch_id) {
        if (category_id < m_Categories.length && category_id >= 0) {
            m_Categories[category_id].removeMerch(merch_id);
        }
    }

    public static void modify(int m_category, int id, IMerch info) {
        m_Categories[m_category].updateMerch(id, info);
    }
}

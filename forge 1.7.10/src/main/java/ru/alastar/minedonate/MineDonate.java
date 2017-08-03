package ru.alastar.minedonate;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartedEvent;
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
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import org.bukkit.Bukkit;
import ru.alastar.minedonate.commands.AddItemCommand;
import ru.alastar.minedonate.commands.AdminCommand;
import ru.alastar.minedonate.merch.Merch;
import ru.alastar.minedonate.merch.categories.*;
import ru.alastar.minedonate.merch.info.EntityInfo;
import ru.alastar.minedonate.merch.info.ItemInfo;
import ru.alastar.minedonate.network.handlers.*;
import ru.alastar.minedonate.network.packets.*;
import ru.alastar.minedonate.proxies.CommonProxy;
import ru.log_inil.mc.minedonate.localData.DataOfConfig;
import ru.log_inil.mc.minedonate.localData.DataOfUIConfig;
import ru.log_inil.mc.minedonate.localData.LocalDataInterchange;

import java.io.*;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

@Mod(modid = MineDonate.MODID, version = MineDonate.VERSION, bukkitPlugin = "Vault")
public class MineDonate {

    public static final String MODID = "MineDonate";
    public static final String VERSION = "4.0.7.1";

    @SideOnly(Side.SERVER)
    public static Connection m_DB_Connection;

    public static boolean m_Enabled = false;
    public static int m_Client_Money = 0;

    static GregorianCalendar calendar = new GregorianCalendar();

    public static Map<Integer, Shop> shops = new HashMap<Integer, Shop>();
    // public static MerchCategory[] m_Categories = new MerchCategory[]{new ItemNBlocks(0), new Privelegies(), new Regions(), new Entities(), new UsersShops()};
    //

    public static Map<EntityPlayerMP, AdminSession> m_Admin_Sessions = new HashMap<EntityPlayerMP, AdminSession>();

    @SideOnly(Side.SERVER)
    public static Object wg_plugin;

    @SideOnly(Side.SERVER)
    private static BufferedWriter m_log;

    public static DataOfConfig cfg; // #LOG

    @SideOnly(Side.CLIENT)
    public static DataOfUIConfig cfgUI; // #LOG

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

    @Mod.EventHandler
    @SideOnly(Side.SERVER)
    public void fmlLifeCycle(FMLServerStartedEvent event) {
        if (MineDonate.cfg.sellRegions)
            MineDonate.InitWG();
    }

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
        instance = this;

    }
    @Mod.EventHandler
    public void serverLoad(FMLServerStartingEvent event) {
        event.registerServerCommand(new AddItemCommand());
        event.registerServerCommand(new AdminCommand());

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


    @SideOnly(Side.SERVER)
    public static void InitDataBase() {

        try {

            MinecraftServer.getServer().logInfo("Initializing database connection...");

            Class.forName("com.mysql.jdbc.Driver").newInstance();
            m_DB_Connection = DriverManager.getConnection("jdbc:mysql://" + cfg.dbHost + ":" + cfg.dbPort + "/" + cfg.dbName, cfg.dbUser, cfg.dbPassword);

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

    }

    @SideOnly(Side.SERVER)
    private static void loadMerchServer() {

        try {

            if (shops.isEmpty()) {

                shops.put(0, new Shop(0, new MerchCategory[]{new ItemNBlocks(0), new Privelegies(), new Regions(), new Entities(), new UsersShops()}, "SERVER", "Server shop", false));

            }

            for (int i = 0; i < shops.get(0).cats.length; ++i) {
                if (shops.get(0).cats[i].isEnabled()) {
                    Statement stmt = m_DB_Connection.createStatement();
                    ResultSet rs = stmt.executeQuery("SELECT * FROM " + shops.get(0).cats[i].getDatabase() + ";");
                    shops.get(0).cats[i].loadMerchFromDB(rs);
                    rs.close();
                    stmt.close();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @SideOnly(Side.CLIENT)
    public static void loadMerchClient() {

        if (shops.isEmpty()) {

            shops.put(0, new Shop(0, new MerchCategory[]{new ItemNBlocks(0), new Privelegies(), new Regions(), new Entities(), new UsersShops()}, "SERVER", "Server shop", false));

        }

    }

    @SideOnly(Side.SERVER)
    public static void RegisterPlayer(EntityPlayerMP player) {
        String name = player.getDisplayName();
        Statement stmt;
        try {
            stmt = m_DB_Connection.createStatement();
            String sql;
            sql = "INSERT INTO " + cfg.dbAccounts + " (" + cfg.dbAccountsNameColumn + ", " + cfg.dbAccountsMoneyColumn + ") VALUES('" + name + "', " + cfg.regMoney + ")";
            stmt.execute(sql);
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @SideOnly(Side.SERVER)
    public static int getMoneyFor(String player) {
        int money = 0;
        Statement stmt;
        try {
            stmt = m_DB_Connection.createStatement();
            String sql;
            sql = "SELECT " + cfg.dbAccountsMoneyColumn + " FROM " + cfg.dbAccounts + " WHERE " + cfg.dbAccountsNameColumn + "='" + player + "';";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                money = rs.getInt(cfg.dbAccountsMoneyColumn);
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
        Statement stmt;
        String name = serverPlayer.getDisplayName();
        try {
            stmt = m_DB_Connection.createStatement();
            String sql;
            sql = "UPDATE " + cfg.dbAccounts + " SET " + cfg.dbAccountsMoneyColumn + "=" + result + " WHERE " + cfg.dbAccountsNameColumn + "='" + name + "';";
            stmt.executeUpdate(sql);
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @SideOnly(Side.SERVER)
    public static void logBuy(Merch bought, EntityPlayerMP by, int amount) {
        if (!cfg.db_log) {
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
                        returnMoney(player_name, cost);
                        if (shops.get(0).cats[category].canReverse()) {
                            shops.get(0).cats[category].reverseFor(line, player_name);
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
        Statement stmt;
        try {
            stmt = m_DB_Connection.createStatement();
            String sql;
            int reversed = getMoneyFor(player_name) + cost;
            sql = "UPDATE " + cfg.dbAccounts + " SET " + cfg.dbAccountsMoneyColumn + "=" + reversed + " WHERE " + cfg.dbAccountsNameColumn + "='" + player_name + "';";
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
                            returnMoney(player_name, cost);
                            if (shops.get(0).cats[category].canReverse()) {
                                shops.get(0).cats[category].reverseFor(line, player_name);
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
            sql = "SELECT " + cfg.dbAccountsMoneyColumn + " FROM " + cfg.dbAccounts + " WHERE " + cfg.dbAccountsNameColumn + "='" + name + "';";
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

    public static void RemoveMerch(int shop, int category_id, int merch_id) {

        if (!shops.containsKey(shop)) {
            return;
        }
        if (shops.get(shop).cats.length < category_id) {
            return;
        }

        if (category_id < shops.get(shop).cats.length && category_id >= 0) {
            shops.get(shop).cats[category_id].removeMerch(merch_id);
        }
    }

    public static void modify(int shop, int m_category, int id, Merch info) {

        if (!shops.containsKey(shop)) {
            return;
        }
        if (shops.get(shop).cats.length < m_category) {
            return;
        }

        shops.get(shop).cats[m_category].updateMerch(id, info);

    }

    public static boolean userShopExistsInDataBase(int shopId) {

        Statement stmt = null;
        try {
            stmt = m_DB_Connection.createStatement();
            String sql;
            sql = "SELECT " + "id" + " FROM " + cfg.dbshops + " WHERE id=" + shopId + ";";
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

    public static ResultSet getShopData(int shopId) {

        Statement stmt = null;
        try {
            stmt = m_DB_Connection.createStatement();
            String sql;
            sql = "SELECT * FROM " + cfg.dbshops + " WHERE id=" + shopId + ";";
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {

                return rs;

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;

    }

    public static void loadUserShop(int shopId) {

        ResultSet sdata = getShopData(shopId);

        if (sdata == null) {

            return;

        }

        Shop s = null;

        try {

            s = new Shop(shopId, new MerchCategory[]{new ItemNBlocks(shopId).setCustomDBTable(cfg.dbUserItems)}, sdata.getString("owner"), sdata.getString("name"), sdata.getBoolean("isFreezed"));
            sdata.close();

        } catch (SQLException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        if (s == null) {

            return;

        }

        shops.put(shopId, s);

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


    public static void AddItemToStock(ItemStack heldItem, String name, String cost, String limit) {
        ItemInfo info = new ItemInfo(shops.get(0).cats[0].getMerch().length, Integer.valueOf(cost), name, "new merch", Integer.valueOf(limit), heldItem);
        shops.get(0).cats[0].addMerch(info);
        Statement stmt;
        try {
            ByteBuf buf = Unpooled.buffer();
            ByteBufUtils.writeItemStack(buf, heldItem);
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

    public static AdminSession getAdminSession(EntityPlayerMP entityPlayer) {
        if(m_Admin_Sessions.containsKey(entityPlayer))
            return m_Admin_Sessions.get(m_Admin_Sessions);
        return null;
    }

    public static void AddEntityBy(AdminSession session, Entity target) {
        session.pending = false;
        String name = session.params[0];
        int cost = Integer.parseInt(session.params[1]);
        int limit = Integer.parseInt(session.params[2]);

        EntityInfo info = new EntityInfo(shops.get(0).cats[3].getMerch().length, Integer.valueOf(cost), target, name);
        shops.get(0).cats[3].addMerch(info);
        Statement stmt;
        try {
            ByteBuf buf = Unpooled.buffer();
            NBTTagCompound tag = new NBTTagCompound();
            target.writeToNBT(tag);
            ByteBufUtils.writeTag(buf, tag);
            InputStream stream = new ByteArrayInputStream(buf.array());
            PreparedStatement statement = m_DB_Connection.prepareStatement("INSERT INTO " + cfg.dbEntities + " (name, data, cost) VALUES(?,?,?)");
            statement.setString(1, name);
            statement.setBlob(2, stream);
            statement.setInt(3, Integer.valueOf(cost));
            statement.execute();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

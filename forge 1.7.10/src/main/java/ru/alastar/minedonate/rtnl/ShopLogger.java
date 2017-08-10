package ru.alastar.minedonate.rtnl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayerMP;
import ru.alastar.minedonate.MineDonate;
import ru.alastar.minedonate.merch.Merch;

@SideOnly(Side.SERVER)
public class ShopLogger {
	
	// note! warn! alert! etc. methods from this class not used

	static GregorianCalendar calendar = new GregorianCalendar();

    private static BufferedWriter m_log;
    
    public static void init ( ) {
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

    public static void logBuy(Merch bought, EntityPlayerMP by, int amount, String moneyType) {
        if (!MineDonate.cfg.sendLogToDB) {
            try {
                m_log.write(calendar.getTime().toString() + ":" + by.getDisplayName() + ":" + bought.getCategory() + ":" + bought.getBoughtMessage() + ":" + bought.getCost() * amount + ":x" + amount + "\r\n");
                m_log.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Statement stmt;
            try {
                stmt = MineDonate.getNewStatement();//m_DB_Connection.createStatement();
                String sql;
                sql = "INSERT INTO " + MineDonate.cfg.dbLogs + " (date, bought_by, message, amount, spent) VALUES('" + calendar.getTime().toString() + "', '" + by.getDisplayName() + "', '" + bought.getBoughtMessage() + "', " + amount + "," + bought.getCost() * amount + ")";
                stmt.execute(sql);
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    //Reverse section
    //WARNIGN! REVERSE ONLY MONEY, PRIVILEGIES, REGIONS! I CANT CARE OF BOUGHT ITEMS
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

    private static void logReverse(String player_name, int cost, int reversed) {
        try {
            m_log.write(calendar.getTime().toString() + ":" + player_name + ":" + -1 + ":reverse:" + cost);
            m_log.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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

}

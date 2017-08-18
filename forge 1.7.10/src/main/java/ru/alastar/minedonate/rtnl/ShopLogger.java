package ru.alastar.minedonate.rtnl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Date;
import java.sql.ResultSet;
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
	
	static GregorianCalendar calendar = new GregorianCalendar();

    private static BufferedWriter m_log;
    
    public static void init ( ) {
        try {
            SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
            String str = dt.format(calendar.getTime());
            File dir = new File(System.getProperty("user.dir") + File.separator + "donate_logs");
            if (!dir.exists())
                dir.mkdir();
            m_log = new BufferedWriter(new FileWriter(System.getProperty("user.dir") + File.separator + "donate_logs" + File.separator + str + ".txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

	static SimpleDateFormat dateFormatLog = new SimpleDateFormat ( "yyyy-MM-dd" ) ;

    public static void logBuy(Merch m, EntityPlayerMP by, int amount, String moneyType) {
        if (!MineDonate.cfg.sendLogToDB) {
            try {
                m_log.write(dateFormatLog.format(calendar.getTime()) + ":" + m.shopId + ":" + m.catId + ":" + m.merch_id + ":" + by.getDisplayName() + ":" + m.getCategory() + ":" + ( m.getCost() * amount ) + ":" + moneyType +  ":x" + amount + ":" + m.getBoughtMessage() + "\r\n");
                m_log.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Statement stmt;
            try {
                stmt = MineDonate.getNewStatement();
                String sql;
                sql = "INSERT INTO " + MineDonate.cfg.dbLogs + " (date, shopId, catId, merchId, playerName, money, moneyType, message, amount) VALUES('" + dateFormatLog.format(calendar.getTime()) + "'," + m .shopId + "," + m.catId + "," + m.merch_id + ", '" + by.getDisplayName() + "', " + m.getCost() * amount + ", '" + moneyType + "', '" + m.getBoughtMessage() + "', " + amount + " )";
                stmt.execute(sql);
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
       }
    }

    //Reverse section
    //WARNIGN! REVERSE ONLY MONEY, PRIVILEGIES, REGIONS! I CANT CARE OF BOUGHT ITEMS
    
    public static void reverseBoughtsFor ( boolean printFullInfo, boolean fromDB, String playerName, Date from, Date to ) {
    	
    	reverseAllBoughts ( printFullInfo, fromDB, from, to, true, playerName . toLowerCase ( ) . trim ( ) ) ;

    }
    
    public static void reverseAllBoughts ( boolean printFullInfo, boolean fromDB, java.util.Date from, java.util.Date to, boolean forOncePlayer, String _playerName ) {
    	
    	long fromTime = from . getTime ( ) ;
    	long toTime = to . getTime ( )  ;
    	
    	SimpleDateFormat dateFormat = new SimpleDateFormat ( "yyyy-MM-dd" ) ;
    	
    	int lineNumber ;

    	if ( fromDB ) {
    		
    		String query = "SELECT * FROM " + MineDonate . cfg . dbLogs + " WHERE " + ( forOncePlayer ? "playerName='" + _playerName + "' AND " : "" ) + " date BETWEEN '" + dateFormat.format(from) + " 00:00:00' AND '" + dateFormat.format(to) + " 23:59:59'" ;
      
    		if ( printFullInfo ) {

        		System . out . println ( "[MineDonate] [Reverse] Query[" + query + "]" ) ;

        	}
        	
        	try {
        		
        		Statement stmt = MineDonate . getNewStatement ( ) ;
                ResultSet rs = stmt . executeQuery ( query ) ;
                lineNumber = 0 ;
                
                while ( rs . next ( ) ) {
                	
                	lineNumber ++ ;
                    processLine ( rs . getDate ( "date" ) . toString ( ) + ":" + rs . getInt ( "shopId" ) + ":" + rs . getInt ( "catId" ) + ":" + rs . getInt ( "metchId" ) + ":" + rs . getString ( "playerName" ) + ":" + rs . getInt ( "money" ) + ":" + rs . getString ( "moneyType" ), printFullInfo, lineNumber, forOncePlayer, _playerName ) ;
                	
                	
                }
                
                stmt . close ( ) ;
                
        	} catch ( Exception ex ) {
        		
        		ex . printStackTrace ( ) ;
        		
        	}
    		
    	} else {
    		
	    	File [ ] logs = ( new File ( File . separator + "donate_logs" ) ) . listFiles ( ) ;
	
	    	java.util.Date logDate ;
	    	BufferedReader reader ;
	    	String line ; 
	    	
	    	for ( File logFile : logs ) {
	    		
	    		lineNumber = 0 ;
	    		
	    		System . out . println ( "[MineDonate] [Reverse] Start process logFile[" + logFile + "]" ) ;
	    		
	    		try {
	    			
	    			logDate = dateFormat . parse ( logFile . getName ( ) ) ;
	    			
	                if ( logDate . getTime ( ) > fromTime && logDate . getTime ( ) < toTime ) {
	                	
	                    reader = new BufferedReader ( new FileReader ( logFile ) ) ;
	                    
	                    line = "" ;
	                    
	                    while ( ( line = reader . readLine ( ) ) != null ) {
	                    	
	                    	lineNumber ++ ;
	                    	
	                        processLine ( line, printFullInfo, lineNumber, forOncePlayer, _playerName ) ;
	                        
	                    }
	                    
	                    reader . close ( ) ;
	                }
	                
	            } catch ( Exception ex ) {
	                
	            	ex . printStackTrace( ) ;
	                
	            }
	    		
	    	}
	    	
    	}
    	
    }

    static String [ ] tmpSplit ;
    static String playerName, moneyType ;
    static int shopId, catId, merchId, cost ;
	
    private static void processLine ( String line, boolean printFullInfo, int lineNumber, boolean forOncePlayer, String _playerName ) {

    	tmpSplit = line . split ( ":" ) ;
    	     	
    	shopId = Integer . valueOf ( tmpSplit [ 1 ] ) ;
    	catId = Integer . valueOf ( tmpSplit [ 2 ] ) ;
    	merchId = Integer . valueOf ( tmpSplit [ 3 ] ) ;
    	
    	playerName = tmpSplit [ 4 ] ;
    	cost = Integer . valueOf ( tmpSplit [ 5 ] ) ;
    	moneyType = tmpSplit [ 6 ] ;

    	if ( forOncePlayer ) {
    		
    		if ( ! playerName . equals ( _playerName ) ) {
    			
    			return ;
    			
    		}
    		
    	}
    	
    	if ( printFullInfo ) {
    		
    		System . out . println ( "[MineDonate] [Reverse] [P] [date=" + tmpSplit [ 0 ] + ", shopId=" + shopId + ", catId=" + catId + ", merchId=" + merchId + ", playerName=" + playerName + ", cost=" + cost + ", moneyType=" + moneyType + "], row[" + lineNumber + "]"  ) ;

    	}
    	
        try {
        	
        	reverseMoney ( playerName, cost, moneyType ) ;

        	reverseMerch ( printFullInfo, lineNumber, shopId, catId, merchId, tmpSplit, playerName ) ;
        	
        } catch ( Exception ex ) {
        	
        	System . err . println ( "[MineDonate] [Reverse] Error reverse row[" + lineNumber + "]" ) ;
        	
        	ex . printStackTrace ( ) ;
        	
        }
        
	}

	private static void reverseMoney ( String playerName, int money, String moneyType ) {
	
    	MineDonate . getMoneyProcessor ( moneyType ) . returnMoney ( playerName, money ) ; 
    	
	}

	private static void reverseMerch ( boolean printFullInfo, int lineNumber, int shopId, int catId, int merchId, String [ ] splData, String playerName ) {
    	
        if ( MineDonate . checkShopAndLoad ( shopId ) ) {
        	
        	if ( MineDonate . checkCatExists ( shopId, catId ) ) {
        		
            	if ( printFullInfo ) {

            		if ( MineDonate . shops . get ( shopId ) . cats [ catId ] . canReverse ( ) ) {
            			
            			MineDonate . shops . get ( shopId ) . cats [ catId ] . reverseFor ( merchId, playerName, splData ) ;
            			
            		} else {
            			
                    	if ( printFullInfo ) {
                    		
                    		System . out . println ( "[MineDonate] [Reverse] [P] Full reverse no supported for this merch, row[" + lineNumber + "]" ) ;
                    		
                    	}
                    	
            		} 
            		
            	}
        		
        	} else {
        		
            	System . err . println ( "[MineDonate] [Reverse] Error cat[" + catId + "] not found in shop[" + shopId + "], row[" + lineNumber + "]" ) ;

        	}
        	
        } else {
        	
        	System . err . println ( "[MineDonate] [Reverse] Error shop[" + shopId + "] not found, row[" + lineNumber + "]"  ) ;

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

	/*
    public static void reverseBoughtsFor ( boolean printFullInfo, String playerName, Date from, Date to ) {
    	    	
    	
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
    }*/

    ///End reverse section

}

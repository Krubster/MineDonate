package ru.alastar.minedonate.rtnl;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayerMP;
import ru.alastar.minedonate.MineDonate;
import ru.alastar.minedonate.merch.Merch;

import java.io.*;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.UUID;

@SideOnly(Side.SERVER)
public class ModShopLogger {
	
	static GregorianCalendar calendar = new GregorianCalendar ( ) ;

    private static BufferedWriter m_log, logReverse, logMoney ;
    
    static SimpleDateFormat dateFormat = new SimpleDateFormat ( "yyyy-MM-dd" ) ;

    public static void init ( ) {
    	
        try {
        	
            String fileName = dateFormat . format ( calendar . getTime ( ) ) ;
            
            File dir = new File ( ( new File ( ".") ) . getAbsolutePath ( ) + File . separator + "minedonate" + File . separator + "donate" ) ;
            if ( ! dir . exists ( ) ) { dir . mkdirs ( ) ; }
            
            m_log = new BufferedWriter ( new FileWriter ( dir . getAbsolutePath ( ) + File . separator + fileName + ".txt" ) ) ;

            dir = new File ( ( new File ( ".") ) . getAbsolutePath ( ) + File . separator + "minedonate" + File . separator + "reverse" ) ;
            if ( ! dir . exists ( ) ) { dir . mkdir ( ) ; }
            
            logReverse = new BufferedWriter ( new FileWriter ( dir . getAbsolutePath ( ) + File . separator + fileName + ".txt" ) ) ;

            dir = new File ( ( new File ( ".") ) . getAbsolutePath ( ) + File . separator + "minedonate" + File . separator + "money");
            if ( ! dir . exists ( ) ) { dir . mkdir ( ) ; }
            
            logMoney = new BufferedWriter ( new FileWriter ( dir . getAbsolutePath ( ) + File . separator + fileName + ".txt" ) ) ;

        } catch ( Exception ex ) {
            
        	ex . printStackTrace ( ) ;
            
        }
        
    }

    public static void logBuy ( Merch m, EntityPlayerMP by, int amount, String moneyType ) {
    	
        if ( ! MineDonate . cfg . sendLogToDB ) {
        	
            try {
                
            	m_log . write ( dateFormat.format(calendar.getTime()) + ":" + m.shopId + ":" + m.catId + ":" + m . getId ( ) + ":" + by.getGameProfile().getId() + ":" + m.getCategory() + ":" + ( m.getCost() * amount ) + ":" + moneyType +  ":x" + amount + ":" + m.getBoughtMessage() + "\r\n");
                m_log . flush ( ) ;
                
            } catch ( Exception ex ) {
                
            	ex . printStackTrace ( ) ;
                
            }
            
        } else {
            
            try {
            	
            	Statement stmt = ModDataBase . getNewStatement ( MineDonate . cfg . dbLogsLinkName ) ;
                stmt.execute( "INSERT INTO " + MineDonate.cfg.dbLogs + " (date, shopId, catId, merchId, playerName, money, moneyType, message, amount) VALUES('" + dateFormat.format(calendar.getTime()) + "'," + m .shopId + "," + m.catId + "," + m . getId ( ) + ", '" + by.getGameProfile().getId() + "', " + m.getCost() * amount + ", '" + moneyType + "', '" + m.getBoughtMessage() + "', " + amount + " )" );
                stmt.close();
                
            } catch ( Exception ex ) {
                
            	ex . printStackTrace ( ) ;
                
            }
            
       }
        
    }
    
	private static void logReverse ( String playerName, int cost ) {
        
		try {

            logReverse.write(calendar.getTime().toString() + ";" + playerName + ";" + cost + "\r\n");
            logReverse . flush ( ) ;
            
        } catch ( Exception ex ) {
            
        	ex . printStackTrace ( ) ;
            
        }
        
    }
	
	public static void logMoney ( String playerName, int last, int next, String factorData ) {
        
		try {

            logMoney.write(calendar.getTime().toString() + ";" + playerName + ";" + last + ";" + next + ";" + factorData + "\r\n");
            ;
            logMoney . flush ( ) ;
            
        } catch ( Exception ex ) {
            
        	ex . printStackTrace ( ) ;
            
        }
        
    }
    
    public static void reverseBoughtsFor ( boolean printFullInfo, boolean fromDB, String playerName, Date from, Date to ) {
    	
    	reverseAllBoughts ( printFullInfo, fromDB, from, to, true, playerName . toLowerCase ( ) . trim ( ) ) ;

    }
    
    public static void reverseAllBoughts ( boolean printFullInfo, boolean fromDB, java.util.Date from, java.util.Date to, boolean forOncePlayer, String _playerName ) {
    	
    	long fromTime = from . getTime ( ) ;
    	long toTime = to . getTime ( )  ;
    	    	
    	int lineNumber ;

    	if ( fromDB ) {
    		
    		String query = "SELECT * FROM " + MineDonate . cfg . dbLogs + " WHERE " + ( forOncePlayer ? "playerName='" + _playerName + "' AND " : "" ) + " date BETWEEN '" + dateFormat.format(from) + " 00:00:00' AND '" + dateFormat.format(to) + " 23:59:59'" ;
      
    		if ( printFullInfo ) {

        		System . out . println ( "[MineDonate] [Reverse] Query[" + query + "]" ) ;

        	}
        	
        	try {
        		
        		Statement stmt = ModDataBase . getNewStatement ( MineDonate . cfg . dbLogsLinkName ) ;
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
    		
	    	File [ ] logs = ( new File ( File . separator + "minedonate" + File.separator + "donate") ) . listFiles ( ) ;
	
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
    static String tmpPlayer, tmpMoneyType ;
    static int tmpShopId, tmpCatId, tmpMerchId, tmpCost ;
	
    private static void processLine ( String line, boolean printFullInfo, int lineNumber, boolean forOncePlayer, String _playerName ) {

    	tmpSplit = line . split ( ":" ) ;
    	     	
    	tmpShopId = Integer . valueOf ( tmpSplit [ 1 ] ) ;
    	tmpCatId = Integer . valueOf ( tmpSplit [ 2 ] ) ;
    	tmpMerchId = Integer . valueOf ( tmpSplit [ 3 ] ) ;
    	
    	tmpPlayer = tmpSplit [ 4 ] ;
    	tmpCost = Integer . valueOf ( tmpSplit [ 5 ] ) ;
    	tmpMoneyType = tmpSplit [ 6 ] ;

    	if ( forOncePlayer ) {
    		
    		if ( ! tmpPlayer . equals ( _playerName ) ) {
    			
    			return ;
    			
    		}
    		
    	}
    	
    	if ( printFullInfo ) {
    		
    		System . out . println ( "[MineDonate] [Reverse] [P] [date=" + tmpSplit [ 0 ] + ", shopId=" + tmpShopId + ", catId=" + tmpCatId + ", merchId=" + tmpMerchId + ", player=" + tmpPlayer + ", cost=" + tmpCost + ", moneyType=" + tmpMoneyType + "], row[" + lineNumber + "]"  ) ;

    	}
    	
        try {
        	
        	reverseMoney ( tmpPlayer, tmpCost, tmpMoneyType ) ;

        	reverseMerch ( printFullInfo, lineNumber, tmpShopId, tmpCatId, tmpMerchId, tmpSplit, tmpPlayer ) ;
        	
        } catch ( Exception ex ) {
        	
        	System . err . println ( "[MineDonate] [Reverse] Error reverse row[" + lineNumber + "]" ) ;
        	
        	ex . printStackTrace ( ) ;
        	
        }
        
	}

	private static void reverseMoney ( String playerName, int money, String moneyType ) {

        MineDonate.getMoneyProcessor(moneyType).returnMoney(MineDonate.getUUIDFromName(playerName), money);

    }

	private static void reverseMerch ( boolean printFullInfo, int lineNumber, int shopId, int catId, int merchId, String [ ] splData, String player ) {
    	
        if ( MineDonate . checkShopAndLoad ( shopId ) ) {
        	
        	if ( MineDonate . checkCatExists ( shopId, catId ) ) {
        		
            	if ( printFullInfo ) {

            		if ( MineDonate . shops . get ( shopId ) . cats [ catId ] . canReverse ( ) ) {
            			
            			MineDonate . shops . get ( shopId ) . cats [ catId ] . reverseFor ( merchId, UUID . fromString ( player ), splData ) ;
            			
            		} else {
            			
                    	if ( printFullInfo ) {
                    		
                    		System . out . println ( "[MineDonate] [Reverse] [P] Full reverse no supported for this merch, row[" + lineNumber + "]" ) ;
                    		
                    	}
                    	
            		} 
            		
            		logReverse ( player, Integer . parseInt ( splData [ 5 ] ) ) ;
            		
            	}
        		
        	} else {
        		
            	System . err . println ( "[MineDonate] [Reverse] Error cat[" + catId + "] not found in shop[" + shopId + "], row[" + lineNumber + "]" ) ;

        	}
        	
        } else {
        	
        	System . err . println ( "[MineDonate] [Reverse] Error shop[" + shopId + "] not found, row[" + lineNumber + "]"  ) ;

        }
        
	}
	
}

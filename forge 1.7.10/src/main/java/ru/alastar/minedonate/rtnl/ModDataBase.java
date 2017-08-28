package ru.alastar.minedonate.rtnl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import ru.alastar.minedonate.MineDonate;

import ru.log_inil.mc.minedonate.localData.DataOfDataBaseLink;

@SideOnly(Side.SERVER)
public class ModDataBase {

    public static Map < String, Connection > dataBaseConnections = new HashMap < > ( ) ;

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
    		
    		return getDataBaseConnection ( linkName, MineDonate . cfg . dataBases . get ( linkName ), false ) ;
 
    	}
    	
    	return c ;
    	
    }
    
    public static Statement getNewStatement ( String dbLinkName ) throws Exception {
    	
    	return getDataBaseConnection ( dbLinkName ) . createStatement ( ) ;
    	
    }
    
    public static PreparedStatement getPreparedStatement ( String dbLinkName, String sql ) throws Exception {
        
    	return getDataBaseConnection ( dbLinkName ) . prepareStatement ( sql ) ;
        
    }
    
    public static void initDataBase ( ) {

        try {

        	MineDonate . logInfo ( "Init connections to database's..." ) ;

            DataOfDataBaseLink tmpDBLink ;
            
            for ( String linkName : MineDonate . cfg . dataBases . keySet ( ) ) {
            	
            	MineDonate . logInfo ( "Load and try connect db[" + linkName+"]" ) ;

            	tmpDBLink = MineDonate . cfg . dataBases . get ( linkName ) ;
            	
            	getDataBaseConnection ( linkName, tmpDBLink, true ) ;
            	
            	MineDonate . logInfo ( "Connected db[" + linkName + "]!" ) ;

            }
            
            MineDonate . m_Enabled = true;

        } catch ( Exception ex ) {
            
        	MineDonate . logError ( "An error occured! Disabling feature!" ) ;
        	MineDonate . m_Enabled = false;

        	ex . printStackTrace ( ) ;
            
        }
                
    }
    
}

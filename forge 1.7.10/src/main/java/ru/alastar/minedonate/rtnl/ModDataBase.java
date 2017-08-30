package ru.alastar.minedonate.rtnl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import ru.alastar.minedonate.MineDonate;

import ru.log_inil.mc.minedonate.localData.DataOfDataBaseLink;

/**
 * Доступ к ресурсам бд
 */
@SideOnly(Side.SERVER)
public class ModDataBase {

    public static Map < String, DataSource > dataBaseSources = new HashMap < > ( ) ;

    public static Connection getDataBaseConnection ( String dbLinkName ) throws Exception {
    	
    	return dataBaseSources . get ( dbLinkName ) . getConnection ( ) ;
    	
    }
    
    public static Connection getDataBaseConnection ( String linkName, DataOfDataBaseLink link, boolean doPreLoadClass ) throws Exception {
    	
    	if ( doPreLoadClass && ! "" . equals ( link . preLoadClassName ) ) {
    		
    		 Class . forName ( link . preLoadClassName ) . newInstance ( ) ;
    		 
    	}
    	
		BasicDataSource bds = new BasicDataSource ( ) ;
		
    	if ( link . hasCustomLink ) {

    		bds . setUrl ( link . customLink . replace ( "%host%", link . host ) . replace ( "%port%", Integer . toString ( link . port ) ) . replace ( "%name%", link . name ) + ( link . useUTF8 ? "?useUnicode=true&characterEncoding=UTF-8" : "" ) ) ;
  		
    	} else {
    		
    		bds . setUrl (  "jdbc:mysql://" + link . host + ":" + link . port + "/" + link . name + ( link . useUTF8 ? "?useUnicode=true&characterEncoding=UTF-8" : "" ) ) ;
    		    		
    	}
    	
		if ( ! "" . equals ( link . user ) && ! "" . equals ( link . password ) ) {
			
			bds . setUsername ( link . user ) ;
			bds . setPassword ( link . password ) ;
			
		}

		dataBaseSources . put ( linkName, bds ) ;

    	return bds . getConnection ( ) ;
    	
    }
    
    public static Statement getNewStatement ( String dbLinkName ) throws Exception {

    	return getDataBaseConnection ( dbLinkName ) . createStatement ( )  ;
    	
    }
    
	public static void closeStatementAndConnection ( Statement stat ) {
		
		try {

			Connection c = null ;
			
			if ( stat != null && ! stat . isClosed ( ) ) {
				
				c = stat . getConnection ( ) ;
				
				stat . close ( ) ;
	
			}
			
			try {

				if ( c != null && ! c . isClosed ( ) ) {
										
					c . close ( ) ;
					
					c = null ;
					
				}
				
			} catch ( Exception ex ) { ex . printStackTrace ( ) ; }

		} catch ( Exception ex ) {
			
			ex . printStackTrace ( ) ;
			
		}
			
	}
	
    public static PreparedStatement getPreparedStatement ( String dbLinkName, String sql ) throws Exception {

    	return getDataBaseConnection ( dbLinkName ) . prepareStatement ( sql ) ;
        
    }
    
	public static void closePreparedStatementAndConnection ( PreparedStatement stat ) {
		
		closeStatementAndConnection ( stat ) ;

	}
	
	
    public static void initDataBase ( ) {

        try {

        	MineDonate . logInfo ( "Init connections to database's..." ) ;

            DataOfDataBaseLink tmpDBLink ;
            
            for ( String linkName : MineDonate . cfg . dataBases . keySet ( ) ) {
            	
            	MineDonate . logInfo ( "Load and try connect db[" + linkName+"]" ) ;

            	tmpDBLink = MineDonate . cfg . dataBases . get ( linkName ) ;
            	
            	Connection c = getDataBaseConnection ( linkName, tmpDBLink, true ) ;
            	
            	MineDonate . logInfo ( "Connected db[" + linkName + "]!" ) ;

            	c . close ( ) ;
            	
            }
            
            MineDonate . m_Enabled = true;

        } catch ( Exception ex ) {
            
        	MineDonate . logError ( "An error occured! Disabling feature!" ) ;
        	MineDonate . m_Enabled = false;

        	ex . printStackTrace ( ) ;
            
        }
                
    }

}

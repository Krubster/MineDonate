package ru.alastar.minedonate.proxies;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;

import net.minecraftforge.common.MinecraftForge;

import ru.alastar.minedonate.MineDonate;
import ru.alastar.minedonate.Utils;
import ru.alastar.minedonate.commands.AddMoneyCommand;
import ru.alastar.minedonate.events.EntitySelectEventHandler;
import ru.alastar.minedonate.events.PlayerJoinEventHandler;
import ru.alastar.minedonate.plugin.PluginHelper;
import ru.alastar.minedonate.rtnl.ModShopLogger;

/**
 * Created by Alastar on 01.04.2017.
 */
public class ServerProxy extends CommonProxy {

    @Override
    public void preInit ( FMLPreInitializationEvent event ) {

        super . preInit ( event ) ;

        ModShopLogger . init ( ) ;
        MineDonate . loadServerConfig ( ) ;
        
        if ( ! MineDonate . cfg . enable ) { 
        	 
        	return ;
        	
        }

        ClassLoader cl = this . getClass ( ) . getClassLoader ( ) ;
        
        // hly shi~
        if ( ! Utils . loadLibs (
        		
	    		new String [ ] [ ] {
	    			new String [ ] { "org.apache.commons.logging.LogFactory", "commons-logging-1.2.jar" },
	    			new String [ ] { "org.apache.commons.pool2.PooledObjectFactory", "commons-pool2-2.4.2.jar" },
	    			new String [ ] { "org.apache.commons.dbcp2.BasicDataSource", "commons-dbcp2-2.1.1.jar" },
				}, ( net . minecraft . launchwrapper . LaunchClassLoader ) cl
	    		
    		) ) {
        
        	MineDonate . logError ( "Error load libraries, the database can not work!" ) ;
        	MineDonate . m_Enabled = false ;
        	
        	return ;
        	
        }
    	
        try {
		
        	Class . forName ( "ru.alastar.minedonate.rtnl.ModDataBase" ) . getMethod ( "initDataBase" ) . invoke ( null ) ;
		
        } catch ( Exception ex ) {
			
			ex . printStackTrace ( ) ;
			
		}

        MineDonate . loadServerMerch ( ) ;
        
        MinecraftForge . EVENT_BUS . register ( new EntitySelectEventHandler ( ) ) ;
                
    }

	@Mod.EventHandler
    public void serverStarting ( FMLServerStartingEvent event ) {
    	
    	super . serverStarting ( event ) ;
    	
    	FMLCommonHandler . instance ( ) . bus ( ) . register ( new PlayerJoinEventHandler ( ) ) ;

    	if ( ! MineDonate . m_Enabled ) {
    		
    		return ;
    		
    	}
    	
        if ( ! MineDonate . cfg . enable ) { 
       	 
        	return ;
        	
        }
        
    	PluginHelper . loadPlugins ( ) ;
    	MineDonate . loadMoneyProccessors ( ) ;
    	
        event . registerServerCommand ( new AddMoneyCommand ( ) ) ;

    }
   
	
}

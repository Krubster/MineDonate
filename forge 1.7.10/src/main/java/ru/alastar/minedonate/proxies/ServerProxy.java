package ru.alastar.minedonate.proxies;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.common.MinecraftForge;
import ru.alastar.minedonate.MineDonate;
import ru.alastar.minedonate.events.EntitySelectEventHandler;
import ru.alastar.minedonate.plugin.PluginHelper;
import ru.alastar.minedonate.rtnl.ShopLogger;

/**
 * Created by Alastar on 01.04.2017.
 */
public class ServerProxy extends CommonProxy {

    @Override
    public void preInit(FMLPreInitializationEvent event) {

        super.preInit(event);

        ShopLogger . init ( ) ;
        MineDonate . loadServerConfig ( ) ;
        
        if ( ! MineDonate . cfg . enable ) { 
        	 
        	return ;
        	
        }

        MineDonate . initDataBase ( ) ;

        MinecraftForge . EVENT_BUS . register ( new EntitySelectEventHandler ( ) ) ;

    }
    
    
    @Mod.EventHandler
    public void serverStarting ( FMLServerStartingEvent event ) {
    	
    	super . serverStarting ( event ) ;
    	
        if ( ! MineDonate . cfg . enable ) { 
       	 
        	return ;
        	
        }
        
    	PluginHelper . loadPlugins ( ) ;
    	
        
    }
   
	
}

package ru.alastar.minedonate.proxies;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.common.MinecraftForge;
import ru.alastar.minedonate.MineDonate;
import ru.alastar.minedonate.events.MouseEventHandler;
import ru.alastar.minedonate.plugin.PluginHelper;

/**
 * Created by Alastar on 01.04.2017.
 */
public class ServerProxy extends CommonProxy {

    @Override
    public void preInit(FMLPreInitializationEvent event) {

        super.preInit(event);

        MineDonate.initLog();
        MineDonate.loadServerConfig();
        MineDonate.InitDataBase();

        MinecraftForge.EVENT_BUS.register(new MouseEventHandler());


    }

    
    @Mod.EventHandler
    public void serverStarting ( FMLServerStartingEvent event ) {
    	
    	super . serverStarting ( event ) ;
    	
    	PluginHelper . loadPlugins ( ) ;
    	
    }
     
}

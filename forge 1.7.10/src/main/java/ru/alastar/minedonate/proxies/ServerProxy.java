package ru.alastar.minedonate.proxies;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.common.MinecraftForge;
import ru.alastar.minedonate.MineDonate;
import ru.alastar.minedonate.commands.AddEntityCommand;
import ru.alastar.minedonate.commands.AddItemCommand;
import ru.alastar.minedonate.commands.AdminCommand;
import ru.alastar.minedonate.events.MouseEventHandler;
import ru.alastar.minedonate.plugin.PluginHelper;
import ru.alastar.minedonate.rtnl.ModNetwork;
import ru.alastar.minedonate.rtnl.ShopLogger;

/**
 * Created by Alastar on 01.04.2017.
 */
public class ServerProxy extends CommonProxy {

    @Override
    public void preInit(FMLPreInitializationEvent event) {

        super.preInit(event);

        ShopLogger.init();
        MineDonate.loadServerConfig();
        MineDonate.initDataBase();

        MinecraftForge.EVENT_BUS.register(new MouseEventHandler());
        
    }
    
    @Mod.EventHandler
    public void init ( FMLInitializationEvent event ) {

    	super . init ( event ) ;
    	
        ModNetwork . register ( ) ;

    }
    
    @Mod.EventHandler
    public void serverStarting ( FMLServerStartingEvent event ) {
    	
    	super . serverStarting ( event ) ;
    	
    	PluginHelper . loadPlugins ( ) ;
    	
        event.registerServerCommand(new AddEntityCommand());
        event.registerServerCommand(new AddItemCommand());
        event.registerServerCommand(new AdminCommand());
        
    }
   
}

package ru.alastar.minedonate.proxies;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkRegistry;

import ru.alastar.minedonate.MineDonate;
import ru.alastar.minedonate.events.MineDonateGUIHandler;
import ru.alastar.minedonate.rtnl.ModNetwork;

/**
 * Created by Alastar on 01.04.2017.
 */
public class CommonProxy {
    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {

    	NetworkRegistry.INSTANCE.registerGuiHandler(MineDonate.getInstance(), new MineDonateGUIHandler());
        ModNetwork . register ( ) ;

    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
    }
    
    @Mod.EventHandler
    public void serverStarting(FMLServerStartingEvent event) {
    }

    public void loadIcon(String url, int id) {
    }
    
	public void clientOpenGui ( int id ) {	
		
	}
	
}

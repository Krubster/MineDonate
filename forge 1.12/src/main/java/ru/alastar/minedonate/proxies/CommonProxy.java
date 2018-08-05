package ru.alastar.minedonate.proxies;

import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ru.alastar.minedonate.MineDonate;
import ru.alastar.minedonate.events.MineDonateGUIHandler;
import ru.alastar.minedonate.rtnl.ModNetworkRegistry;

/**
 * Created by Alastar on 01.04.2017.
 */
public class CommonProxy {
    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        
        ModNetworkRegistry.register();

    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
    }

    @Mod.EventHandler
    public void serverStarting(FMLServerStartingEvent event) {
    }
    @SideOnly(Side.CLIENT)
    public DynamicTexture loadIcon(String url, int id) {
    return null;
    }

    public void clientOpenGui(int id) {

    }

}

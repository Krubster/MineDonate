package ru.alastar.minedonate.proxies;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import ru.alastar.minedonate.MineDonate;
import ru.alastar.minedonate.events.EntitySelectEventHandler;
import ru.alastar.minedonate.events.PlayerJoinEventHandler;
import ru.alastar.minedonate.plugin.sponge.SpongePluginHelper;
import ru.alastar.minedonate.rtnl.ModShopLogger;

/**
 * Created by Alastar on 01.04.2017.
 */
public class ServerProxy extends CommonProxy {

    @Override
    public void preInit(FMLPreInitializationEvent event) {

        super.preInit(event);

        ModShopLogger.init();
        MineDonate.loadServerConfig();

        if (!MineDonate.cfg.enable) {

            return;

        }
        try {

            Class.forName("ru.alastar.minedonate.rtnl.ModDataBase").getMethod("initDataBase").invoke(null);

        } catch (Exception ex) {

            ex.printStackTrace();

        }

        MineDonate.loadServerMerch();

        MinecraftForge.EVENT_BUS.register(EntitySelectEventHandler.class);
        MinecraftForge.EVENT_BUS.register(PlayerJoinEventHandler.class);

    }

    @Mod.EventHandler
    public void serverStarting(FMLServerStartingEvent event) {

        super.serverStarting(event);
        
        if (!MineDonate.m_Enabled) {

            return;

        }

        if (!MineDonate.cfg.enable) {

            return;

        }

        SpongePluginHelper.loadPlugins();
        MineDonate.loadMoneyProccessors();
    }


}

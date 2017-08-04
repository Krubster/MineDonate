package ru.alastar.minedonate.proxies;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.common.MinecraftForge;
import ru.alastar.minedonate.MineDonate;
import ru.alastar.minedonate.ServerEventHandler;
import ru.alastar.minedonate.events.MouseEventHandler;

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

        FMLCommonHandler.instance().bus().register(new ServerEventHandler());
        MinecraftForge.EVENT_BUS.register(new MouseEventHandler());


    }

}

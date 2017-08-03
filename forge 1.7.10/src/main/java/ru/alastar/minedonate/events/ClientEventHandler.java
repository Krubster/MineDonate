package ru.alastar.minedonate.events;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ru.alastar.minedonate.gui.ShopGUI;

/**
 * Created by Alastar on 18.07.2017.
 */

@SideOnly(Side.CLIENT)
public class ClientEventHandler {

    @SubscribeEvent
    public void onEvent(PlayerEvent.PlayerLoggedOutEvent event) {

        if (ShopGUI.instance != null) {

            ShopGUI.instance.needNetUpdate = true;

        }

    }


}

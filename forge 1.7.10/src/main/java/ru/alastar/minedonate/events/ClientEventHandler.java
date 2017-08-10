package ru.alastar.minedonate.events;

import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import ru.alastar.minedonate.MineDonate;
import ru.alastar.minedonate.gui.ShopGUI;
import ru.alastar.minedonate.network.packets.InventoryShopPacket;
import ru.alastar.minedonate.proxies.ClientProxy;
import ru.alastar.minedonate.rtnl.ModNetwork;

/**
 * Created by Alastar on 18.07.2017.
 */

@SideOnly(Side.CLIENT)
public class ClientEventHandler {

    @SubscribeEvent
    public void onEvent(PlayerEvent.PlayerLoggedOutEvent event) {

        if ( ShopGUI . instance != null ) {

            ShopGUI . instance . needNetUpdate = true ;

        }

    }

	@SideOnly(Side.CLIENT)
    @SubscribeEvent(priority= EventPriority.NORMAL, receiveCanceled=true)
    public void onEvent(InputEvent.KeyInputEvent event) {
    	
    	if (ClientProxy.openShop.isPressed() ){//&& Minecraft.getMinecraft().theWorld.isRemote) {
    		    		
    		//MineDonate.networkChannel.sendToServer( new InventoryShopPacket ( InventoryShopPacket.Type.OPEN_INV ) ) ;
            Minecraft.getMinecraft().thePlayer.openGui(MineDonate.getInstance(), 0, Minecraft.getMinecraft().theWorld, (int)  Minecraft.getMinecraft().thePlayer.posX, (int)  Minecraft.getMinecraft().thePlayer.posY, (int)  Minecraft.getMinecraft().thePlayer.posZ);

        }/* else if (ClientProxy.openAdmin.isPressed() && Minecraft.getMinecraft().theWorld.isRemote) {

            Minecraft.getMinecraft().thePlayer.openGui(MineDonate.getInstance(), 1, Minecraft.getMinecraft().theWorld, (int)  Minecraft.getMinecraft().thePlayer.posX, (int)  Minecraft.getMinecraft().thePlayer.posY, (int)  Minecraft.getMinecraft().thePlayer.posZ);

        }*/
        
    }
	
}

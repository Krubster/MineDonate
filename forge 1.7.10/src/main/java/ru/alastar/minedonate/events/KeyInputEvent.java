package ru.alastar.minedonate.events;

import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import ru.alastar.minedonate.MineDonate;
import ru.alastar.minedonate.gui.ShopGUI;
import ru.alastar.minedonate.network.packets.NeedUpdatePacket;
import ru.alastar.minedonate.proxies.ClientProxy;

/**
 * Created by Alastar on 18.07.2017.
 */
public class KeyInputEvent {
	
	/*
    public static class Refresh {
    	
    	@SideOnly(Side.CLIENT)
        @SubscribeEvent(priority= EventPriority.NORMAL, receiveCanceled=true)
        public void onEvent(InputEvent.KeyInputEvent event) {
        	
        	if ( ClientProxy . refreshCfg . isPressed ( ) ) {
            	
        		MineDonate . loadClientConfig ( ) ;
               
            }
            
        }
    	
	}*/

	@SideOnly(Side.CLIENT)
    @SubscribeEvent(priority= EventPriority.NORMAL, receiveCanceled=true)
    public void onEvent(InputEvent.KeyInputEvent event) {
    	
    	if (ClientProxy.openHUD.isPressed() && Minecraft.getMinecraft().theWorld.isRemote) {
        	
            Minecraft.getMinecraft().thePlayer.openGui(MineDonate.getInstance(), 0, Minecraft.getMinecraft().theWorld, (int)  Minecraft.getMinecraft().thePlayer.posX, (int)  Minecraft.getMinecraft().thePlayer.posY, (int)  Minecraft.getMinecraft().thePlayer.posZ);

        }
        if (ClientProxy.openAdmin.isPressed() && Minecraft.getMinecraft().theWorld.isRemote) {

            Minecraft.getMinecraft().thePlayer.openGui(MineDonate.getInstance(), 1, Minecraft.getMinecraft().theWorld, (int)  Minecraft.getMinecraft().thePlayer.posX, (int)  Minecraft.getMinecraft().thePlayer.posY, (int)  Minecraft.getMinecraft().thePlayer.posZ);

        }
        
    }
    
}

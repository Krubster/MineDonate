package ru.alastar.minedonate.events;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import ru.alastar.minedonate.MineDonate;
import ru.alastar.minedonate.network.packets.CodePacket;
import ru.alastar.minedonate.rtnl.ModNetworkRegistry;

/**
 *	Отправка статуса мода клиенту 
 * */
public class PlayerJoinEventHandler {

   @SubscribeEvent
   public void onEntityJoin ( EntityJoinWorldEvent event ) {

       if ( ! event . world . isRemote && event . entity instanceof EntityPlayerMP ) {
           
    	  ModNetworkRegistry . sendTo ( ( EntityPlayerMP ) event . entity, new CodePacket ( MineDonate . m_Enabled ? CodePacket . Code . MOD_ENABLED : CodePacket . Code . MOD_DISABLED ) ) ;
           
       }
       
   }
	   
}

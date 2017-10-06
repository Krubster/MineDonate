package ru.alastar.minedonate.events;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import net.minecraft.entity.player.EntityPlayerMP;
import ru.alastar.minedonate.MineDonate;
import ru.alastar.minedonate.network.packets.CodePacket;
import ru.alastar.minedonate.rtnl.ModNetworkRegistry;

/**
 *	Отправка статуса мода клиенту 
 * */
public class PlayerJoinEventHandler {

	@SubscribeEvent
	public void onPlayerLoggedInEvent ( PlayerEvent . PlayerLoggedInEvent event ) {
   
		ModNetworkRegistry . sendTo ( ( EntityPlayerMP ) event . player, new CodePacket ( MineDonate . m_Enabled ? CodePacket . Code . MOD_ENABLED : CodePacket . Code . MOD_DISABLED ) ) ;
	
	}
	   
}

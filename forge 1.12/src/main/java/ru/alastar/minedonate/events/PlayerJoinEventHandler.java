package ru.alastar.minedonate.events;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.relauncher.Side;
import ru.alastar.minedonate.MineDonate;
import ru.alastar.minedonate.network.packets.CodePacket;
import ru.alastar.minedonate.rtnl.ModNetworkRegistry;

/**
 * Отправка статуса мода клиенту
 */
@Mod.EventBusSubscriber(value = Side.SERVER, modid = MineDonate.MODID)
public class PlayerJoinEventHandler {

    @SubscribeEvent
    public static void onEntityJoin(PlayerEvent.PlayerLoggedInEvent event) {

        ModNetworkRegistry.sendTo((EntityPlayerMP) event.player, new CodePacket(MineDonate.m_Enabled ? CodePacket.Code.MOD_ENABLED : CodePacket.Code.MOD_DISABLED));

    }

}

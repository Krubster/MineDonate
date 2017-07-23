package ru.alastar.minedonate.events;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import ru.alastar.minedonate.MineDonate;
import ru.alastar.minedonate.network.MineDonateNetwork;
import ru.alastar.minedonate.network.packets.AccountInfoPacket;
import ru.alastar.minedonate.network.packets.AddMerchPacket;
import ru.alastar.minedonate.network.packets.SupportedFeaturesPacket;

/**
 * Created by Alastar on 18.07.2017.
 */
@SideOnly(Side.SERVER)
public class PlayerConnectedEvent {

    @SubscribeEvent
    public void onEvent(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.player instanceof EntityPlayerMP) {
            if (MineDonate.m_Enabled) {
                if (!MineDonate.ExistsAccount((EntityPlayer) event.player)) {
                    MineDonate.RegisterPlayer((EntityPlayerMP) event.player);
                }
                SupportedFeaturesPacket features_packet = new SupportedFeaturesPacket(MineDonate.m_Use_Items, MineDonate.m_Use_Privelegies, MineDonate.m_Use_Regions, MineDonate.m_Use_Entities);
                MineDonateNetwork.INSTANCE.sendTo(features_packet, (EntityPlayerMP) event.player);
                AccountInfoPacket info_packet = new AccountInfoPacket(MineDonate.getInstance().getMoneyFor(event.player.getDisplayName()));
                MineDonateNetwork.INSTANCE.sendTo(info_packet, (EntityPlayerMP) event.player);
                for (int i = 0; i < MineDonate.m_Categories.length; ++i) {
                    for (int j = 0; j < MineDonate.m_Categories[i].getMerch().length; ++j) {
                        AddMerchPacket packet = new AddMerchPacket(MineDonate.m_Categories[i].getMerch()[j]);
                        MineDonateNetwork.INSTANCE.sendTo(packet, (EntityPlayerMP) event.player);
                    }
                }
            }
        }
    }
}

package ru.alastar.minedonate.events;

import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.event.entity.player.EntityInteractEvent;
import ru.alastar.minedonate.AdminSession;
import ru.alastar.minedonate.MineDonate;

/**
 * Created by Alastar on 03.08.2017.
 */
public class MouseEventHandler {
    @SideOnly(Side.SERVER)
    @SubscribeEvent(priority= EventPriority.NORMAL, receiveCanceled=true)
    public void onEvent(EntityInteractEvent event) {
        if(event.entityPlayer instanceof EntityPlayerMP) {
            if (MineDonate.checkAdminSession((EntityPlayerMP) event.entityPlayer)) {
                final AdminSession session = MineDonate.getAdminSession((EntityPlayerMP) event.entityPlayer);
                if(session.pending){
                    event.entityPlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.GOLD + "Entity added! Check it shop now"));
                    MineDonate.AddEntityBy(session, event.target);
                }
            }
        }
    }
}

package ru.alastar.minedonate.events;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ru.alastar.minedonate.MineDonate;
import ru.alastar.minedonate.network.manage.packets.ManageResponsePacket.ResponseCode;
import ru.alastar.minedonate.network.manage.packets.ManageResponsePacket.ResponseStatus;
import ru.alastar.minedonate.network.manage.packets.ManageResponsePacket.ResponseType;
import ru.alastar.minedonate.rtnl.ModNetworkRegistry;
import ru.alastar.minedonate.rtnl.common.Account;

/**
 * Добавление мобов в магазин
 */
@Mod.EventBusSubscriber(value = Side.SERVER, modid = MineDonate.MODID)
public class EntitySelectEventHandler {

    @SideOnly(Side.SERVER)
    @SubscribeEvent(priority = EventPriority.NORMAL)
    public static void onEvent(PlayerInteractEvent.EntityInteractSpecific e) {

        if (e.getEntity() instanceof EntityPlayerMP) {
            EntityPlayerMP plr =  (EntityPlayerMP)e.getEntity();
            Account acc = MineDonate.getAccountFromCache(plr);

            if (acc != null) {

                if (acc.ms.mobSelect) {

                    if (!(e.getTarget() instanceof EntityLivingBase)) {

                        plr.sendMessage(new TextComponentString(TextFormatting.RED + " [MineDonate] " + TextFormatting.RESET + "Error add entity! EntityLivingBase check error!"));

                        ModNetworkRegistry.sendToManageResponsePacket( plr, ResponseType.ENTITY, ResponseCode.SELECT, ResponseStatus.ERROR_ENTITY_CHECK_LIVINGBASE);

                    } else {

                        try {

                            Class.forName(e.getTarget().getClass().getName()).getDeclaredConstructor(net.minecraft.world.World.class).newInstance(plr.getEntityWorld());

                        } catch (Exception ex) {

                            ModNetworkRegistry.sendToManageResponsePacket(plr, ResponseType.ENTITY, ResponseCode.SELECT, ResponseStatus.ERROR_ENTITY_CHECK_INIT);

                            e.setCanceled(true); //HOLY FUCK, A FUCKING TYPO!!11
                            acc.ms.mobSelect = false;

                            return;

                        }

                        acc.ms.currentMob = e.getTarget();

                        ModNetworkRegistry.sendToManageResponsePacket( plr, ResponseType.ENTITY, ResponseCode.SELECT, ResponseStatus.OK);

                        ModNetworkRegistry.sendToMobSelectPacket( plr, 1);

                    }

                    e.setCanceled(true);
                    acc.ms.mobSelect = false;

                    ModNetworkRegistry.sendToManageResponsePacket( plr, ResponseType.ENTITY, ResponseCode.ADD, ResponseStatus.ENTITY_SELECT_STOP);

                }

            }

        }

    }


}

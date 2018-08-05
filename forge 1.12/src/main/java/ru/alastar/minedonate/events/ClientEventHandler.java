package ru.alastar.minedonate.events;


import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ru.alastar.minedonate.MineDonate;
import ru.alastar.minedonate.gui.ShopGUI;
import ru.alastar.minedonate.proxies.ClientProxy;

/**
 * Created by Alastar on 18.07.2017.
 */

@SideOnly(Side.CLIENT)
@Mod.EventBusSubscriber(value = Side.CLIENT, modid = MineDonate.MODID)
public class ClientEventHandler {

    @SubscribeEvent
    public static void onEvent(PlayerEvent.PlayerLoggedOutEvent event) {

        if (ShopGUI.instance != null) {

            ShopGUI.instance.needNetUpdate = true;

        }

    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent(priority = EventPriority.NORMAL, receiveCanceled = true)
    public static void onEvent(InputEvent.KeyInputEvent event) {

        if (ClientProxy.openShop.isPressed()) {

            Minecraft.getMinecraft().player.openGui(MineDonate.getInstance(), 0, Minecraft.getMinecraft().world, (int) Minecraft.getMinecraft().player.posX, (int) Minecraft.getMinecraft().player.posY, (int) Minecraft.getMinecraft().player.posZ);

        }

    }

}

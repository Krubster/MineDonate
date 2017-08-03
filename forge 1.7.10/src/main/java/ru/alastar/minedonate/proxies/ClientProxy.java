package ru.alastar.minedonate.proxies;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;
import ru.alastar.minedonate.MineDonate;
import ru.alastar.minedonate.events.ClientEventHandler;
import ru.alastar.minedonate.events.KeyInputEvent;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;

/**
 * Created by Alastar on 01.04.2017.
 */
public class ClientProxy extends CommonProxy {

    public static KeyBinding openHUD = new KeyBinding("minedonate.open.shop", Keyboard.KEY_EQUALS, "key.minedonate.main");
    public static KeyBinding refreshCfg;

    public static DynamicTexture[] m_Privelegies_Icons = new DynamicTexture[0];
    public static KeyBinding openAdmin = new KeyBinding("minedonate.open.admin", Keyboard.KEY_MINUS, "key.minedonate.main.admin");
    @Override
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);


        FMLCommonHandler.instance().bus().register(new ClientEventHandler());
        FMLCommonHandler.instance().bus().register(new KeyInputEvent());

        MineDonate.loadClientConfig();
        MineDonate.loadMerchClient();

        ClientRegistry.registerKeyBinding(openHUD);
        ClientRegistry.registerKeyBinding(openAdmin);

        if (MineDonate.cfgUI.bindF5RefreshButton) {

            // FMLCommonHandler.instance().bus().register(new KeyInputEvent.Refresh());
            refreshCfg = new KeyBinding("minedonate.refresh", Keyboard.KEY_F5, "key.minedonate.refresh");
            ClientRegistry.registerKeyBinding(refreshCfg);

        }

    }

    static ResourceLocation res = new ResourceLocation(MineDonate.MODID.toLowerCase(), "cash");

    public static void playCash() {

        int x = (int) Minecraft.getMinecraft().thePlayer.posX;
        int y = (int) Minecraft.getMinecraft().thePlayer.posY;
        int z = (int) Minecraft.getMinecraft().thePlayer.posZ;

        Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.func_147675_a(res, x, y, z));
    }

    public static void loadIcon(String url, int id) {
        BufferedImage image = null;
        System.out.println("Icon url: " + url);

        try {
            image = ImageIO.read(new URL(url));
            if (image != null) {
                DynamicTexture dyn_tex = new DynamicTexture(image);
                if (id >= m_Privelegies_Icons.length)
                    m_Privelegies_Icons = Arrays.copyOf(m_Privelegies_Icons, id + 1);
                m_Privelegies_Icons[id] = dyn_tex;
            } else {
                System.out.println("Null image!!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static DynamicTexture getImage(int id) {
        return m_Privelegies_Icons[id];
    }

}
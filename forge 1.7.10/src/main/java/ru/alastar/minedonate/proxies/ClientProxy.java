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

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;

/**
 * Created by Alastar on 01.04.2017.
 */
public class ClientProxy extends CommonProxy {

    public static KeyBinding openShop = new KeyBinding("minedonate.open.shop", Keyboard.KEY_EQUALS, "key.minedonate.main");

    public static DynamicTexture[] m_Privelegies_Icons = new DynamicTexture[0];
        
    @Override
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);

        FMLCommonHandler.instance().bus().register(new ClientEventHandler());

        MineDonate.loadClientConfig();

        ClientRegistry.registerKeyBinding(openShop);

    }

    static ResourceLocation res = new ResourceLocation(MineDonate.MODID.toLowerCase(), "cash");

    public static void playCash() {

        int x = (int) Minecraft.getMinecraft().thePlayer.posX;
        int y = (int) Minecraft.getMinecraft().thePlayer.posY;
        int z = (int) Minecraft.getMinecraft().thePlayer.posZ;

        Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.func_147675_a(res, x, y, z));
    }

    public void loadIcon(String url, int id) {
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

    @Override
	public void clientOpenGui ( int id ) {	

    	Minecraft.getMinecraft().thePlayer.openGui(MineDonate.getInstance(), id, Minecraft.getMinecraft().theWorld, (int)  Minecraft.getMinecraft().thePlayer.posX, (int)  Minecraft.getMinecraft().thePlayer.posY, (int)  Minecraft.getMinecraft().thePlayer.posZ);
    
    }
    
}
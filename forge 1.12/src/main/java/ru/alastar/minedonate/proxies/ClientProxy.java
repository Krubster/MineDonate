package ru.alastar.minedonate.proxies;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GLContext;
import ru.alastar.minedonate.MineDonate;
import ru.alastar.minedonate.events.ClientEventHandler;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.Hashtable;

/**
 * Created by Alastar on 01.04.2017.
 */
@Mod.EventBusSubscriber(Side.CLIENT)
public class ClientProxy extends CommonProxy {

    public static KeyBinding openShop = new KeyBinding("minedonate.open.shop", KeyConflictContext.IN_GAME, Keyboard.KEY_EQUALS, "key.minedonate.main");

    public static Hashtable m_Privelegies_Icons = new Hashtable();
    static ResourceLocation  res = new ResourceLocation (MineDonate.MODID.toLowerCase(), "cash");
    
    static SoundEvent s_event = new SoundEvent(res);

    public static void playCash() {

        int x = (int) Minecraft.getMinecraft().player.posX;
        int y = (int) Minecraft.getMinecraft().player.posY;
        int z = (int) Minecraft.getMinecraft().player.posZ;
        Minecraft.getMinecraft().world.playSound(Minecraft.getMinecraft().player, Minecraft.getMinecraft().player.getPosition(), s_event, SoundCategory.MUSIC, 1, 1);
       // Minecraft.getMinecraft().getSoundHandler().playSound(new PositionedSoundRecord(res, ));//PositionedSoundRecord.getRecordSoundRecord());
    }

    public static DynamicTexture getImage(String url) {
        if(m_Privelegies_Icons.containsKey(url))
        return (DynamicTexture) m_Privelegies_Icons.get(url);
        else
        {
            BufferedImage image = null;
            System.out.println("Icon url: " + url);
            try {
                image = ImageIO.read(new URL(url));
                if (image != null) {
                    DynamicTexture dyn_tex = new DynamicTexture(ImageIO.read(new URL(url)));
                    if(!m_Privelegies_Icons.contains(dyn_tex))
                        m_Privelegies_Icons.put(url, dyn_tex);
                    return dyn_tex;
                } else {
                    System.out.println("Null image!!");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return  null;
        }
    }

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);
        ClientRegistry.registerKeyBinding(openShop);

        MinecraftForge.EVENT_BUS.register(ClientEventHandler.class);

        MineDonate.loadClientConfig();

    }
    @SubscribeEvent
    public void registerSounds(RegistryEvent.Register<SoundEvent> event) {
        event.getRegistry().registerAll(s_event);
    }


    @Override
    public void clientOpenGui(int id) {

        Minecraft.getMinecraft().player.openGui(MineDonate.getInstance(), id, Minecraft.getMinecraft().world, (int) Minecraft.getMinecraft().player.posX, (int) Minecraft.getMinecraft().player.posY, (int) Minecraft.getMinecraft().player.posZ);

    }

}
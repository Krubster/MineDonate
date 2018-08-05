package ru.alastar.minedonate;

import com.mojang.authlib.GameProfile;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.EmptyByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import ru.alastar.minedonate.rtnl.common.IsolatedClassloader;

import java.awt.*;
import java.io.File;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.UUID;


public class Utils {

    public static String netReadString(ByteBuf buf) throws UnsupportedEncodingException {
      //  byte[] bytes = ByteBufUtil.getBytes(buf);

        return ByteBufUtils.readUTF8String(buf);

    }

    public static void netWriteString(ByteBuf buf, String str) throws UnsupportedEncodingException {

        ByteBufUtils.writeUTF8String(buf, str);
    }

    public static String getNameFromUUID(UUID id) {


        GameProfile profile = FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerProfileCache().getProfileByUUID(id);

        if (profile != null) {

            return profile.getName();

        } else {

            MineDonate.logError("Null profile, for id[" + id + "]!");

        }

        return "";

    }

    public static UUID getUUIDFromName(String name) {

        GameProfile profile = FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerProfileCache().getGameProfileForUsername(name);

        if (profile != null) {

            return profile.getId();

        } else {

            MineDonate.logError("Null profile for name[" + name + "]!");

        }

        return null;

    }

    public static UUID getUUIDFromPlayer(EntityPlayerMP serverPlayer) {

        GameProfile profile = FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerProfileCache().getGameProfileForUsername(serverPlayer.getDisplayNameString());

        if (profile != null) {

            return profile.getId();

        } else {

            MineDonate.logError("Null profile, for player[" + serverPlayer + "]!");

        }

        return null;

    }

    public static void sendModMessage(EntityPlayerMP player, String msg) {

        player.sendMessage(new TextComponentString(TextFormatting.AQUA + " [MineDonate] " + TextFormatting.RESET + msg));

    }

    public static int rgbaToInt(Color c) {

        int r = c.getRed() & 0xFF;
        int g = c.getGreen() & 0xFF;
        int b = c.getBlue() & 0xFF;
        int a = c.getAlpha() & 0xFF;

        return (r << 16) + (g << 8) + (b) + (a << 24);

    }

    public static boolean classExists(String name, net.minecraft.launchwrapper.LaunchClassLoader cl) {

        try {

            return cl.findResource(name.replace(".", "/").concat(".class")) != null;

        } catch (Throwable tw) {
            tw.printStackTrace();
        }

        return false;

    }

    public static boolean loadLibs(String[][] _args, net.minecraft.launchwrapper.LaunchClassLoader _cl) {

        for (String[] args : _args) {

            if (!loadLib(args, _cl)) {

                return false;

            }

        }

        return true;

    }

    public static boolean loadLib(String[] args, net.minecraft.launchwrapper.LaunchClassLoader cl) {

        if (!Utils.classExists(args[0], cl)) {

            try {

                MineDonate.logInfo("Try load library[" + args[1] + "] ...");

                String p = System.getProperty("user.dir") + File.separator + "libs" + File.separator + args[1];
                URL url = new File(p).toURI().toURL();
                cl.addURL(url);
                
                MineDonate.logInfo("Library[" + args[1] + "] loaded!");
                
            } catch (Exception ex) {

                MineDonate.logError("Error load library[" + args[1] + "]!");

                ex.printStackTrace();

            }

            if (!Utils.classExists(args[0], cl)) {

                MineDonate.logError("Class[" + args[0] + "] not found!");

            } else {

                return true;

            }

            return false;

        } else {

            return true;

        }

    }

}

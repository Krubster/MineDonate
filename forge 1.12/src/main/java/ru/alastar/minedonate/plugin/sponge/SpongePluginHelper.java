package ru.alastar.minedonate.plugin.sponge;

import com.google.common.io.ByteStreams;
import org.spongepowered.api.Sponge;
import ru.alastar.minedonate.MineDonate;
import ru.alastar.minedonate.plugin.AccessorPlugin;
import ru.log_inil.mc.minedonate.localData.DataOfAccessorPlugin;

import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Alastar on 28.11.2017.
 */
public class SpongePluginHelper {
    public static Map<String, AccessorPlugin> accessorPlugins = new HashMap<>();

    public static boolean hasExistsOnServer(String name) {

        return Sponge.getPluginManager().isLoaded(name);

    }

    public static void loadPlugins() {

        ClassLoader cl;
        Object oServerSide;
        AccessorPlugin oModSide;

        boolean abstractAccessorClassLoaded = false;

        for (DataOfAccessorPlugin doap : MineDonate.cfg.accessPlugins) {

            if (doap.load && hasExistsOnServer(doap.serverPluginName)) {

                MineDonate.logInfo("[MineDonate] Try init AccessorPlugin[" + doap.modPluginName + "], server name: " + doap.serverPluginName);

                try {

                   /*
                    // Проверяем и загружаем родительский класс плагинов доступа
                    if (!abstractAccessorClassLoaded) {

                        defineClassInClassLoader(cl, AccessorPlugin.class.getName(), false);

                        abstractAccessorClassLoaded = true;

                    }

                    if (doap.cleanInterfaceClassName != null && doap.cleanInterfaceClassName.equals("")) {

                        // загружаем в класс лоадер "чистый" класс плагина доступа
                        defineClassInClassLoader(cl, doap.cleanInterfaceClassName, false);

                    }

                    // загружаем дочернийх[cleanInterfaceClassName] класс с реализацией методов


                    // получаем конечный, дочерный класс с доступом к классу[serverInterfaceClassName] с реализацией методов


                        */
                   cl = Sponge.getPluginManager().getPlugin(doap.serverPluginName).getClass().getClassLoader();
                   if(cl == null)
                       cl = Sponge.class.getClassLoader();
                   oServerSide = defineClassInClassLoader(cl, doap.serverInterfaceClassName, true).newInstance();
                    oModSide = (AccessorPlugin) Class.forName(doap.reflectionInterfaceClassName).newInstance();

                    oModSide.init(oServerSide, doap);
                    accessorPlugins.put(doap.modPluginName, oModSide);
                    MineDonate.logInfo("[MineDonate] AccessorPlugin[" + doap.modPluginName + "] inited!");

                } catch (Exception ex) {

                    ex.printStackTrace();

                }

            }

        }

        for (String k : accessorPlugins.keySet()) {

            MineDonate.logInfo("[MineDonate] Try load AccessorPlugin[" + k + "]");

            accessorPlugins.get(k).load(accessorPlugins.get(k).getConfigPluginData().xProperties);

            MineDonate.logInfo("[MineDonate] AccessorPlugin[" + k + "] loaded!");

        }

    }

    public static AccessorPlugin getPlugin(String modPluginName) {

        return accessorPlugins.get(modPluginName);

    }

    private static Class<?> defineClassInClassLoader(ClassLoader classLoader, String cl, boolean loadClass) {

        try {

           if (loadClass) {

                return classLoader.loadClass(cl);

            }

        } catch (Exception ex) {

            ex.printStackTrace();

        }

        return null;

    }
}

package ru.alastar.minedonate.rtnl.common;

import java.net.URL;
import java.net.URLClassLoader;

/**
 * Created by Alastar on 17.06.2018.
 */
public class IsolatedClassloader extends URLClassLoader {
    static {
        ClassLoader.registerAsParallelCapable();
    }

    public IsolatedClassloader(URL[] urls) {
        /*
         * ClassLoader#getSystemClassLoader returns the AppClassLoader
         *
         * Calling #getParent on this returns the ExtClassLoader (Java 8) or
         * the PlatformClassLoader (Java 9). Since we want this classloader to
         * be isolated from the Minecraft server (the app), we set the parent
         * to be the platform class loader.
         */
        super(urls, ClassLoader.getSystemClassLoader().getParent());
    }

}
package ru.alastar.minedonate.plugin.server;

import org.bukkit.Bukkit;

import ru.alastar.minedonate.plugin.PluginHelper;

public class BukkitServer extends ServerPluginLoader {

	@Override
	public boolean pluginExists ( String name ) throws Throwable {
		
		return Bukkit . getPluginManager ( ) . isPluginEnabled ( name ) ;
		
	}

	@Override
	public Class < ? > defineClassFromBytes ( byte [ ] clazz, boolean loadClass ) throws Throwable {
		
		return PluginHelper . defineClassInClassLoader ( Bukkit . getPluginManager ( ) . getPlugins ( ) [ 0 ] . getClass ( ) . getClassLoader ( ), clazz, loadClass ) ;
		
	}

	public static class Detector implements IServerDetector {
		
		@Override
		public boolean detect ( ) throws Throwable {
			
			return this . getClass ( ) . getClassLoader ( ) . loadClass ( "org.bukkit.Bukkit" ) != null ;
			
		}

		@Override
		public String getServerPluginLoaderClassName ( ) {

			return BukkitServer . class . getName ( ) ;
			
		}
		
	}
	
}

package ru.alastar.minedonate.plugin;

import java.io.InputStream;
import java.lang.reflect.Method;

import org.bukkit.Bukkit;

import com.google.common.io.ByteStreams;

import ru.alastar.minedonate.MineDonate;
import ru.alastar.minedonate.plugin.bukkit.PermissionsBukkitPlugin;
import ru.alastar.minedonate.plugin.bukkit.WorldGuardBukkitPlugin;
import ru.alastar.minedonate.plugin.clean.PermissionsPlugin;
import ru.alastar.minedonate.plugin.clean.WorldGuardPlugin;
import ru.alastar.minedonate.plugin.reflection.PermissionsPluginReflection;
import ru.alastar.minedonate.plugin.reflection.WorldGuardPluginReflection;

public class PluginHelper {

	public static PermissionsPlugin pexMgr = new PermissionsPlugin ( ) ;
	public static WorldGuardPlugin wgMgr = new WorldGuardPlugin ( ) ;
	
	public static boolean hasExists ( String name ) {
		
		return Bukkit . getPluginManager ( ) . isPluginEnabled ( name ) ;
		
	}
	
	public static void loadPlugins ( ) {
		
		if ( MineDonate . cfg . sellPrivelegies && hasExists ( "PermissionsEx" ) ) {
			
			try {
				
				loadClassToBukkit ( Bukkit . getPluginManager ( ) . getPlugin ( "PermissionsEx" ) . getClass ( ) . getClassLoader ( ), PermissionsPlugin . class . getName ( ), false ) ;
				
				pexMgr = new PermissionsPluginReflection ( loadClassToBukkit ( Bukkit . getPluginManager ( ) . getPlugin ( "PermissionsEx" ) . getClass ( ) . getClassLoader ( ), PermissionsBukkitPlugin . class . getName ( ), true ) . newInstance ( ) ) ;
				pexMgr . load ( ) ;

				System . out . println ( "[MineDonate] Pex loaded!" ) ;
				
			} catch ( Exception ex ) {
			
				System . err . println ( "Error load pexMgr accessor plugin!" ) ;
				
				ex . printStackTrace ( ) ;
				
			}		
			
		}
		
		if ( MineDonate . cfg . sellRegions && hasExists ( "WorldGuard" ) ) {

			
			try {
								
				loadClassToBukkit ( Bukkit . getPluginManager ( ) . getPlugin ( "WorldGuard" ) . getClass ( ) . getClassLoader ( ), WorldGuardPlugin . class . getName ( ), false ) ;
			
				wgMgr = new WorldGuardPluginReflection ( loadClassToBukkit ( Bukkit . getPluginManager ( ) . getPlugin ( "WorldGuard" ) . getClass ( ) . getClassLoader ( ), WorldGuardBukkitPlugin . class . getName ( ), true ) . newInstance ( ) ) ;
				wgMgr . load ( ) ;
				
				System . out . println ( "[MineDonate] WG loaded!" ) ;
				
			} catch ( Exception ex ) {
			
				System . err . println ( "Error load wgMgr accessor plugin!" ) ;
				
				ex . printStackTrace ( ) ;
				
			}		
			
		}
		
		
	}

	public static boolean existsClass ( String name ) {
		
		try {
			
			Class . forName ( name ) ;
			
			return true ;
			
		} catch ( Exception ex ) { }
		
		return false ;
		
	}
        
	private static Class<?> loadClassToBukkit ( ClassLoader classLoader, String cl, boolean returnClass ) {
		
		try {
			
			InputStream stream = PluginHelper . class . getClassLoader ( ) . getResourceAsStream ( cl . replace ( '.', '/' ) . concat ( ".class" ) ) ;
			byte [ ] b =  ByteStreams . toByteArray ( stream ) ;
			
			Method m = ClassLoader . class . getDeclaredMethod ( "defineClass", new Class [ ] { String . class, byte [ ] . class, int . class, int . class } ) ;
			
			m . setAccessible ( true ) ;
			m . invoke(classLoader, null, b, 0, b . length ) ;
						
			if ( returnClass ) {
				
				return classLoader . loadClass ( cl ) ;
				
			}
			
		} catch ( Exception ex ) {
			
			ex . printStackTrace ( ) ;
			
		}
		
		return null ;
		
	}

}

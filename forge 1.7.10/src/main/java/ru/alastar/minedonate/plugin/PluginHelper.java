package ru.alastar.minedonate.plugin;

import java.io.InputStream;
import java.lang.reflect.Method;

import org.bukkit.Bukkit;

import com.google.common.io.ByteStreams;

import ru.alastar.minedonate.MineDonate;
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
		
		ClassLoader cl ;
		
		if ( MineDonate . cfg . sellPrivelegies && hasExists ( "PermissionsEx" ) ) {
			
			try {
				
				cl = Bukkit . getPluginManager ( ) . getPlugin ( "PermissionsEx" ) . getClass ( ) . getClassLoader ( ) ;
				
				defineClassInClassLoader ( cl, PermissionsPlugin . class . getName ( ), false ) ;
				
				pexMgr = new PermissionsPluginReflection ( defineClassInClassLoader ( cl , MineDonate . cfg . permissionsPluginClassName, true ) . newInstance ( ) ) ;
				pexMgr . load ( ) ;

				System . out . println ( "[MineDonate] Pex loaded!" ) ;
				
			} catch ( Exception ex ) {
			
				System . err . println ( "Error load pexMgr accessor plugin!" ) ;
				
				ex . printStackTrace ( ) ;
				
			}		
			
		}
		
		if ( MineDonate . cfg . sellRegions && hasExists ( "WorldGuard" ) ) {

			
			try {
								
				cl = Bukkit . getPluginManager ( ) . getPlugin ( "WorldGuard" ) . getClass ( ) . getClassLoader ( ) ;
				defineClassInClassLoader ( cl, WorldGuardPlugin . class . getName ( ), false ) ;
			
				wgMgr = new WorldGuardPluginReflection ( defineClassInClassLoader ( cl, MineDonate . cfg . worldGuardPluginClassName, true ) . newInstance ( ) ) ;
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
        
	private static Class<?> defineClassInClassLoader ( ClassLoader classLoader, String cl, boolean loadClass ) {
		
		try {
			
			InputStream stream = PluginHelper . class . getClassLoader ( ) . getResourceAsStream ( cl . replace ( '.', '/' ) . concat ( ".class" ) ) ;
			byte [ ] b =  ByteStreams . toByteArray ( stream ) ;
			
			Method m = ClassLoader . class . getDeclaredMethod ( "defineClass", new Class [ ] { String . class, byte [ ] . class, int . class, int . class } ) ;
			
			m . setAccessible ( true ) ;
			m . invoke(classLoader, null, b, 0, b . length ) ;
						
			if ( loadClass ) {
				
				return classLoader . loadClass ( cl ) ;
				
			}
			
		} catch ( Exception ex ) {
			
			ex . printStackTrace ( ) ;
			
		}
		
		return null ;
		
	}

}

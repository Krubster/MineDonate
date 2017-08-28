package ru.alastar.minedonate.plugin;

import com.google.common.io.ByteStreams;
import org.bukkit.Bukkit;

import ru.alastar.minedonate.MineDonate;
import ru.alastar.minedonate.plugin.permissions.PermissionsPlugin;
import ru.log_inil.mc.minedonate.localData.DataOfAccessorPlugin;

import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class PluginHelper {
	
	public static Map < String, AccessorPlugin > accessorPlugins = new HashMap < > ( ) ;
	
	public static boolean hasExists ( String name ) {
		
		return Bukkit . getPluginManager ( ) . isPluginEnabled ( name ) ;
		
	}
	
	public static void loadPlugins ( ) {
		
		ClassLoader cl ;
		Object oServerSide;
		AccessorPlugin oModSide ;
		
		for ( DataOfAccessorPlugin doap : MineDonate . cfg . accessPlugins ) {
			
			if ( doap . load && hasExists ( doap . serverPluginName ) ) {
			
				MineDonate . logInfo ( "[MineDonate] Try init AccessorPlugin[" + doap . modPluginName + "], server name: " + doap . serverPluginName ) ;

				try {
					
					cl = Bukkit . getPluginManager ( ) . getPlugin ( doap . serverPluginName ) . getClass ( ) . getClassLoader ( ) ;
					
					defineClassInClassLoader ( cl, PermissionsPlugin . class . getName ( ), false ) ;

					oServerSide = defineClassInClassLoader ( cl , doap . serverInterfaceClassName, true ) . newInstance ( ) ;
					
					oModSide = ( AccessorPlugin ) Class . forName ( doap . reflectionInterfaceClassName ) . newInstance ( ) ;
					
					oModSide . init ( oServerSide, doap ) ;
					
					accessorPlugins . put ( doap . modPluginName, oModSide ) ;
									
					MineDonate . logInfo ( "[MineDonate] AccessorPlugin[" + doap . modPluginName + "] inited!" ) ;
					
				} catch ( Exception ex ) {
					
					ex . printStackTrace ( ) ;
					
				}
				
			}
			
		}
		
		for ( String k : accessorPlugins . keySet ( ) ) {
		
			MineDonate . logInfo ( "[MineDonate] Try load AccessorPlugin[" + k + "]" ) ;
			
			accessorPlugins . get ( k ) . load ( ) ;
			
			MineDonate . logInfo ( "[MineDonate] AccessorPlugin[" + k + "] loaded!" ) ;

		}
		
		/*
		if ( MineDonate . cfg . sellPrivelegies && hasExists ( "PermissionsEx" ) ) {
			
			try {
				
				cl = Bukkit . getPluginManager ( ) . getPlugin ( "PermissionsEx" ) . getClass ( ) . getClassLoader ( ) ;
				
				defineClassInClassLoader ( cl, PermissionsPlugin . class . getName ( ), false ) ;
				
				pexMgr = new PermissionsPluginReflection ( defineClassInClassLoader ( cl , MineDonate . cfg . permissionsPluginClassName, true ) . newInstance ( ) ) ;
				pexMgr . load ( ) ;

				MineDonate . logInfo ( "[MineDonate] Pex loaded!" ) ;
				
			} catch ( Exception ex ) {
			
				MineDonate . logError ( "Error load pexMgr accessor plugin!" ) ;
				
				ex . printStackTrace ( ) ;
				
			}		
			
		}
		
		if ( MineDonate . cfg . sellRegions && hasExists ( "WorldGuard" ) ) {

			
			try {
								
				cl = Bukkit . getPluginManager ( ) . getPlugin ( "WorldGuard" ) . getClass ( ) . getClassLoader ( ) ;
				defineClassInClassLoader ( cl, WorldGuardPlugin . class . getName ( ), false ) ;
			
				wgMgr = new WorldGuardPluginReflection ( defineClassInClassLoader ( cl, MineDonate . cfg . worldGuardPluginClassName, true ) . newInstance ( ) ) ;
				wgMgr . load ( ) ;
				
				MineDonate . logInfo ( "[MineDonate] WG loaded!" ) ;
				
			} catch ( Exception ex ) {
			
				MineDonate . logError ( "Error load wgMgr accessor plugin!" ) ;
				
				ex . printStackTrace ( ) ;
				
			}		
			
		}*/
		
		
	}

	public static AccessorPlugin getPlugin ( String modPluginName ) {
		
		return accessorPlugins . get ( modPluginName ) ;
		
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

package ru.alastar.minedonate.plugin;

import org.bukkit.Bukkit;

import ru.alastar.minedonate.MineDonate;
import ru.alastar.minedonate.plugin.clean.PermissionsPlugin;
import ru.alastar.minedonate.plugin.clean.WorldGuardPlugin;

public class PluginHelper {

	public static PermissionsPlugin pexMgr = new PermissionsPlugin ( ) ;
	public static WorldGuardPlugin wgMgr = new WorldGuardPlugin ( ) ;
	
	public static boolean hasExists ( String name ) {
		
		return Bukkit . getPluginManager ( ) . isPluginEnabled ( name ) ;
		
	}
	
	public static void loadPlugins ( ) {
		
		ClassLoader cll = PluginHelper . class . getClassLoader ( ) ;
		
		if ( MineDonate . cfg . sellPrivelegies && hasExists ( "PermissionsEx" ) ) {
			
			try {
				
				pexMgr = ( PermissionsPlugin ) cll . loadClass ( "ru.alastar.minedonate.plugin.bukkit.PermissionsBukkitPlugin" ) . newInstance ( ) ;
				
				System . out . println ( "[MineDonate] Pex loaded!" ) ;
				
			} catch ( Exception ex ) {
			
				System . err . println ( "Error load pexMgr accessor plugin!" ) ;
				
				ex . printStackTrace ( ) ;
				
			}		
			
		}
		
		if ( MineDonate . cfg . sellRegions && hasExists ( "WorldGuard" ) ) {

			try {
				
				wgMgr = ( WorldGuardPlugin ) cll . loadClass ( "ru.alastar.minedonate.plugin.bukkit.WorldGuardBukkitPlugin" ) . newInstance ( ) ;
				
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
	
}

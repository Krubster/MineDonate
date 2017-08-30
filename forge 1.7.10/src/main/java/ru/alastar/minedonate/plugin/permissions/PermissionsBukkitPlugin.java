package ru.alastar.minedonate.plugin.permissions;

import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;

import ru.tehkode.permissions.bukkit.PermissionsEx;

public class PermissionsBukkitPlugin extends PermissionsPlugin {

	boolean loaded = false ;
	PermissionsEx pexPlugin ;

	public PermissionsBukkitPlugin ( ) { }

	@Override
	public void load ( Map < String, Object > prop ) {
		
		if ( ! loaded ) {
		
			pexPlugin = ( PermissionsEx ) Bukkit . getPluginManager ( ) . getPlugin ( "PermissionsEx" ) ;
			
			loaded = true ;
			
		}
		
	}
	
	@Override
	public boolean hasPermission ( UUID user, String name ) {
		
		if ( Bukkit . getPlayer ( user ) != null ) {
		
			return pexPlugin . has ( Bukkit . getPlayer ( user ), name ) ;
		
		} else {
			
			return false ;
			
		}
		
	}
	
	@Override
	public void addGroup ( UUID user, String name, String world, Long time ) {
					
		pexPlugin . getUser ( Bukkit . getPlayer ( user ) ) . addGroup ( name, world . isEmpty ( ) ? null : world, time ) ;
		
	}
	
	@Override
	public void removeGroup ( UUID user, String name ) {
		
		pexPlugin . getUser ( Bukkit . getPlayer ( user ) ) . removeGroup ( name ) ;
		
	}
	
}

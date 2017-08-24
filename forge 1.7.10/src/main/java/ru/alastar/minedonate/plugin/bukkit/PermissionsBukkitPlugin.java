package ru.alastar.minedonate.plugin.bukkit;

import java.util.UUID;

import org.bukkit.Bukkit;
import ru.alastar.minedonate.plugin.clean.PermissionsPlugin;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class PermissionsBukkitPlugin extends PermissionsPlugin {

	boolean loaded = false ;
	PermissionsEx pexMgr ;

	public PermissionsBukkitPlugin(){}

	@Override
	public void load ( ) {
		
		if ( ! loaded ) {
		
			pexMgr = ( PermissionsEx ) Bukkit . getPluginManager ( ) . getPlugin ( "PermissionsEx" ) ;
			
			loaded = true ;
			
		}
		
	}
	
	@Override
	public boolean hasPermission ( UUID user, String name ) {
		
		if ( Bukkit . getPlayer ( user ) != null ) {
		
			return pexMgr . has ( Bukkit . getPlayer ( user ), name ) ;
		
		} else {
			
			return false ;
			
		}
		
	}
	
	@Override
	public void addGroup ( UUID user, String name, String world, Long time ) {
					
		pexMgr . getUser ( Bukkit . getPlayer ( user ) ) . addGroup ( name, world . isEmpty ( ) ? null : world, time ) ;
		
	}
	
	@Override
	public void removeGroup ( UUID user, String name ) {
		
		pexMgr . getUser ( Bukkit . getPlayer ( user ) ) . removeGroup ( name ) ;
		
	}
	
}

package ru.alastar.minedonate.plugin.bukkit;

import java.net.URL;

import org.bukkit.Bukkit;

import ru.alastar.minedonate.plugin.PluginHelper;
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
	public boolean hasPermission ( String user, String name ) {
		
		return pexMgr . has ( Bukkit . getPlayer ( user ), name ) ;
		
	}
	
	@Override
	public void addGroup ( String user, String name, String world, Long time ) {
					
		pexMgr . getUser ( Bukkit . getPlayer ( user ) ) . addGroup ( name, world . isEmpty ( ) ? null : world, time ) ;
		
	}
	
	@Override
	public void removeGroup ( String user, String name ) {
		
		pexMgr . getUser ( Bukkit . getPlayer ( user ) ) . removeGroup ( name ) ;
		
	}
	
}

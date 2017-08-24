package ru.alastar.minedonate.plugin.bukkit;

import java.util.UUID;

import org.bukkit.Bukkit;

public class WorldGuardBukkitPlugin extends ru.alastar.minedonate.plugin.clean.WorldGuardPlugin {

	boolean loaded = false ;
	com.sk89q.worldguard.bukkit.WorldGuardPlugin wgMgr ;
	
	@Override
	public void load ( ) {
		
		if ( ! loaded ) {
			
			wgMgr = ( ( com.sk89q.worldguard.bukkit.WorldGuardPlugin ) Bukkit . getPluginManager ( ) . getPlugin ( "WorldGuard" ) ) . inst ( ) ;
			
			loaded = true ;
			
		}
		
	}
	
    @Override
	public void addPlayerToRegion ( String world, String rgName, UUID player ) {
		
		try {
			
			wgMgr . getRegionManager ( Bukkit . getWorld ( world ) ) . getRegion ( rgName ) . getOwners ( ) . addPlayer (  wgMgr . wrapPlayer ( Bukkit . getPlayer ( player ) ) ) ;
			
		} catch ( Exception ex ) {
			
			ex . printStackTrace ( ) ;
			
		}
	
	}
    
    @Override
	public void removePlayerFromRegion ( String world, String rgName, UUID player ) {
		
		try {

			wgMgr . getRegionManager ( Bukkit . getWorld ( world ) ) . getRegion ( rgName ) . getOwners ( ) . removePlayer ( wgMgr . wrapPlayer ( Bukkit . getPlayer ( player ) ) ) ;

		} catch ( Exception ex ) {
			
			ex . printStackTrace ( ) ;
			
		}
		
	}
	
    @Override
	public boolean checkRegionMaxOut ( String world, UUID player ) {

    	if ( Bukkit . getWorld ( world ) == null ) {
    		
    		System . err . println ( "[MineDonate] World: " + world + ", not found!" ) ;
    		
    		return false ;
    		
    	}

    	return wgMgr . getGlobalStateManager ( ) . get ( Bukkit . getWorld ( world ) ) . getMaxRegionCount ( Bukkit . getPlayer ( player ) ) <= wgMgr . getGlobalRegionManager ( ) . get ( Bukkit . getWorld ( world ) ) . getRegionCountOfPlayer ( wgMgr . wrapPlayer ( Bukkit . getPlayer ( player ) ) ) ;
		
	}
	
}

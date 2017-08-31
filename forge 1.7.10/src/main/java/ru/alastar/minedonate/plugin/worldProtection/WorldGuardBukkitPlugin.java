package ru.alastar.minedonate.plugin.worldProtection;

import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;

public class WorldGuardBukkitPlugin extends ru.alastar.minedonate.plugin.worldProtection.WorldProtectionPlugin {

	boolean loaded = false ;
	com.sk89q.worldguard.bukkit.WorldGuardPlugin wgPlugin ;
	
	@Override
	public void load ( Map < String, Object > prop ) {
		
		if ( ! loaded ) {
			
			wgPlugin = ( ( com.sk89q.worldguard.bukkit.WorldGuardPlugin ) Bukkit . getPluginManager ( ) . getPlugin ( "WorldGuard" ) ) . inst ( ) ;
			
			loaded = true ;
			
		}
		
	}
	
    @Override
	public void addPlayerToRegion ( String world, String rgName, UUID player ) {
		
		try {
			
			wgPlugin . getRegionManager ( Bukkit . getWorld ( world ) ) . getRegion ( rgName ) . getOwners ( ) . addPlayer (  wgPlugin . wrapPlayer ( Bukkit . getPlayer ( player ) ) ) ;
			
		} catch ( Exception ex ) {
			
			ex . printStackTrace ( ) ;
			
		}
	
	}
    
    @Override
	public void removePlayerFromRegion ( String world, String rgName, UUID player ) {
		
		try {

			wgPlugin . getRegionManager ( Bukkit . getWorld ( world ) ) . getRegion ( rgName ) . getOwners ( ) . removePlayer ( wgPlugin . wrapPlayer ( Bukkit . getPlayer ( player ) ) ) ;

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

    	return wgPlugin . getGlobalStateManager ( ) . get ( Bukkit . getWorld ( world ) ) . getMaxRegionCount ( Bukkit . getPlayer ( player ) ) <= wgPlugin . getGlobalRegionManager ( ) . get ( Bukkit . getWorld ( world ) ) . getRegionCountOfPlayer ( wgPlugin . wrapPlayer ( Bukkit . getPlayer ( player ) ) ) ;
		
	}
	
}

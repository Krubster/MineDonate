package ru.alastar.minedonate.plugin.worldProtection;

import java.util.UUID;

import ru.alastar.minedonate.plugin.AccessorPlugin;

public abstract class WorldProtectionPlugin extends AccessorPlugin {

	public void addPlayerToRegion ( String world, String rgName, UUID player ) {
		
	}
	
	public void removePlayerFromRegion ( String world, String rgName, UUID player ) {
		
	}

	public boolean checkRegionMaxOut ( String world, UUID player ) {
		
		return false ;
		
	}
	
}

package ru.alastar.minedonate.plugin.reflection;

import ru.alastar.minedonate.plugin.clean.WorldGuardPlugin;

import java.lang.reflect.Method;
import java.util.UUID;

public class WorldGuardPluginReflection extends WorldGuardPlugin {

	Method mLoad,  mAddPlayerToRegion, mRemovePlayerFromRegion, mCheckRegionMaxOut;
	
	Object o ;
	
	public WorldGuardPluginReflection ( Object _o ) {
	
		try {
			
			o = _o ;
			
			mLoad = o.getClass().getMethod("load");
			mAddPlayerToRegion = o.getClass().getMethod("addPlayerToRegion", new Class[]{String.class, String.class, UUID.class});
			mRemovePlayerFromRegion = o.getClass().getMethod("removePlayerFromRegion", new Class[]{String.class, String.class, UUID.class});
			mCheckRegionMaxOut = o.getClass().getMethod("checkRegionMaxOut", new Class[]{String.class, UUID.class});

		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void load() {
		
		try {
			mLoad.invoke(o);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public void addPlayerToRegion ( String world, String rgName, UUID player ) {
		
		try {
			mAddPlayerToRegion.invoke(o, new Object[]{world, rgName, player});
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void removePlayerFromRegion ( String world, String rgName, UUID player ) {
		
		try {
			mRemovePlayerFromRegion.invoke(o, new Object[]{world, rgName, player});
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public boolean checkRegionMaxOut ( String world, UUID player ) {
		
		try {
			return (boolean) mCheckRegionMaxOut.invoke(o, new Object[]{world, player});
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		return false ;
		
	}
	
}

package ru.alastar.minedonate.plugin.reflection;

import java.lang.reflect.Method;

import ru.alastar.minedonate.plugin.clean.PermissionsPlugin;
import ru.alastar.minedonate.plugin.clean.WorldGuardPlugin;

public class WorldGuardPluginReflection extends WorldGuardPlugin {

	Method mLoad,  mAddPlayerToRegion, mRemovePlayerFromRegion, mCheckRegionMaxOut;
	
	Object o ;
	
	public WorldGuardPluginReflection ( Object _o ) {
	
		try {
			
			o = _o ;
			
			mLoad = o.getClass().getMethod("load");
			mAddPlayerToRegion = o.getClass().getMethod("addPlayerToRegion", new Class[]{String.class, String.class, String.class});
			mRemovePlayerFromRegion = o.getClass().getMethod("removePlayerFromRegion", new Class[]{String.class, String.class, String.class});
			mCheckRegionMaxOut = o.getClass().getMethod("checkRegionMaxOut", new Class[]{String.class, String.class});

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

	public void addPlayerToRegion ( String world, String rgName, String player ) {
		
		try {
			mAddPlayerToRegion.invoke(o, new Object[]{world, rgName, player});
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void removePlayerFromRegion ( String world, String rgName, String player ) {
		
		try {
			mRemovePlayerFromRegion.invoke(o, new Object[]{world, rgName, player});
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public boolean checkRegionMaxOut ( String world, String name ) {
		
		try {
			return (boolean) mCheckRegionMaxOut.invoke(o, new Object[]{world, name});
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		return false ;
		
	}
	
}

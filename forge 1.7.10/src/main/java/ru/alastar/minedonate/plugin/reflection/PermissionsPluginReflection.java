package ru.alastar.minedonate.plugin.reflection;

import ru.alastar.minedonate.plugin.clean.PermissionsPlugin;

import java.lang.reflect.Method;

public class PermissionsPluginReflection extends PermissionsPlugin {

	Method mLoad,  mHasPermission, mAddGroup, mRemoveGroup;
	
	Object o ;

	public PermissionsPluginReflection ( Object _o ) {
	
		try {
			
			o = _o ;

			mLoad = o.getClass().getMethod("load");
			mHasPermission = o.getClass().getMethod("hasPermission", new Class[]{String.class, String.class});
			mAddGroup = o.getClass().getMethod("addGroup", new Class[]{String.class, String.class, String.class, Long.class});
			mRemoveGroup  = o.getClass().getMethod("removeGroup", new Class[]{String.class, String.class});
			
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

	@Override
	public boolean hasPermission(String user, String name) {
		
		try {
			return (boolean) mHasPermission.invoke(o, new Object[]{user, name});
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return false;
		
	}

	@Override
	public void addGroup(String user, String name, String world, Long time) {
		
		try {
			mAddGroup.invoke(o, new Object[]{user, name, world, time});
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void removeGroup(String user, String name ) {
		
		try {
			mRemoveGroup.invoke(o, new Object[]{user, name});
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
}

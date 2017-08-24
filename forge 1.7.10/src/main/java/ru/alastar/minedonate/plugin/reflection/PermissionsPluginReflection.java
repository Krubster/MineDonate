package ru.alastar.minedonate.plugin.reflection;

import ru.alastar.minedonate.plugin.clean.PermissionsPlugin;

import java.lang.reflect.Method;
import java.util.UUID;

public class PermissionsPluginReflection extends PermissionsPlugin {

	Method mLoad,  mHasPermission, mAddGroup, mRemoveGroup;
	
	Object o ;

	public PermissionsPluginReflection ( Object _o ) {
	
		try {
			
			o = _o ;

			mLoad = o.getClass().getMethod("load");
			mHasPermission = o.getClass().getMethod("hasPermission", new Class[]{UUID.class, String.class});
			mAddGroup = o.getClass().getMethod("addGroup", new Class[]{UUID.class, String.class, String.class, Long.class});
			mRemoveGroup  = o.getClass().getMethod("removeGroup", new Class[]{UUID.class, String.class});
			
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
	public boolean hasPermission(UUID user, String name) {
		
		try {
			return (boolean) mHasPermission.invoke(o, new Object[]{user, name});
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return false;
		
	}

	@Override
	public void addGroup(UUID user, String name, String world, Long time) {
		
		try {
			mAddGroup.invoke(o, new Object[]{user, name, world, time});
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void removeGroup(UUID user, String name ) {
		
		try {
			mRemoveGroup.invoke(o, new Object[]{user, name});
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
}

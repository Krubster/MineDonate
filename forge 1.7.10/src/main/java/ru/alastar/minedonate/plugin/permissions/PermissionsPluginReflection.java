package ru.alastar.minedonate.plugin.permissions;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.UUID;

import ru.log_inil.mc.minedonate.localData.DataOfAccessorPlugin;

public class PermissionsPluginReflection extends PermissionsPlugin {
	
	Object o ;
	
	Method mLoad, mHasPermission, mAddGroup, mRemoveGroup;

	@Override
	public void init ( Object _o, DataOfAccessorPlugin _doap ) {
	
		super . init ( _o, _doap ) ;
		
		try {
			
			o = _o ;

			mLoad = o.getClass().getMethod("load", new Class[]{Map.class});
			mHasPermission = o.getClass().getMethod("hasPermission", new Class[]{UUID.class, String.class});
			mAddGroup = o.getClass().getMethod("addGroup", new Class[]{UUID.class, String.class, String.class, Long.class});
			mRemoveGroup  = o.getClass().getMethod("removeGroup", new Class[]{UUID.class, String.class});
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void load ( Map < String, Object > prop ) {
		
		try {
			mLoad.invoke(o, prop);
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

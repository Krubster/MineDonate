package ru.alastar.minedonate.plugin.sponge.worldProtection;

import ru.alastar.minedonate.plugin.WorldProtectionPlugin;
import ru.log_inil.mc.minedonate.localData.DataOfAccessorPlugin;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.UUID;

public class RedProtectPluginReflection extends WorldProtectionPlugin {

    Method mLoad, mAddPlayerToRegion, mRemovePlayerFromRegion, mCheckRegionMaxOut;

    Object o;

    @Override
    public void init(Object _o, DataOfAccessorPlugin _doap) {

        super.init(_o, _doap);

        try {

            o = _o;

            mLoad = o.getClass().getMethod("load", new Class[]{Map.class});
            mAddPlayerToRegion = o.getClass().getMethod("addPlayerToRegion", new Class[]{String.class, String.class, UUID.class});
            mRemovePlayerFromRegion = o.getClass().getMethod("removePlayerFromRegion", new Class[]{String.class, String.class, UUID.class});
            mCheckRegionMaxOut = o.getClass().getMethod("checkRegionMaxOut", new Class[]{String.class, UUID.class});

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void load(Map<String, Object> prop) {

        try {
            mLoad.invoke(o, prop);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void addPlayerToRegion(String world, String rgName, UUID player) {

        try {
            mAddPlayerToRegion.invoke(o, new Object[]{world, rgName, player});
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void removePlayerFromRegion(String world, String rgName, UUID player) {

        try {
            mRemovePlayerFromRegion.invoke(o, new Object[]{world, rgName, player});
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public boolean checkRegionMaxOut(String world, UUID player) {

        try {
            return (boolean) mCheckRegionMaxOut.invoke(o, new Object[]{world, player});
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;

    }

}

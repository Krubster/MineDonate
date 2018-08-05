package ru.alastar.minedonate.plugin;

import ru.alastar.minedonate.plugin.AccessorPlugin;

import java.util.UUID;

public abstract class WorldProtectionPlugin extends AccessorPlugin {

    public void addPlayerToRegion(String world, String rgName, UUID player) {

    }

    public void removePlayerFromRegion(String world, String rgName, UUID player) {

    }

    public boolean checkRegionMaxOut(String world, UUID player) {

        return false;

    }

}

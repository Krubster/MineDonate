package ru.alastar.minedonate.plugin;

import ru.alastar.minedonate.plugin.AccessorPlugin;

import java.util.UUID;

public abstract class PermissionsPlugin extends AccessorPlugin {

    public boolean hasPermission(UUID user, String name) {

        return false;

    }

    public void addGroup(UUID user, String name, String world, Long time) {

    }

    public void removeGroup(UUID user, String name) {

    }

}

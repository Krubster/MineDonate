package ru.alastar.minedonate.plugin;

import ru.log_inil.mc.minedonate.localData.DataOfAccessorPlugin;

import java.util.Map;

public abstract class AccessorPlugin {

    DataOfAccessorPlugin doap;

    public DataOfAccessorPlugin getConfigPluginData() {

        return doap;

    }

    public void init(Object _o, DataOfAccessorPlugin _doap) {

        doap = _doap;

    }

    public void load(Map<String, Object> prop) {

    }

}

package ru.log_inil.mc.minedonate.localData;

import java.util.Map;

public class DataOfAccessorPlugin {

    // Имя плагина доступа для мода
    public String modPluginName;

    // Загружать ли его
    public boolean load;

    // Имя плагина на сервере, к которому необходим доступ
    public String serverPluginName;

    // (Указывать если необходим) Родительский класс с необходимыми методами
    public String cleanInterfaceClassName;

    // Дочерний класс, содержащий необходимые методы[из cleanInterfaceClassName] с реализацией, загружаемый на сервер
    public String serverInterfaceClassName;

    // Дочерный класс на стороне мода, содержит реализацию доступа к классу[serverInterfaceClassName]
    public String reflectionInterfaceClassName;

    // Доп. опций плагина доступа
    public Map<String, Object> xProperties;

    public DataOfAccessorPlugin(String _modPluginName, boolean _load, String _serverPluginName, String _serverInterfaceClassName, String _reflectionInterfaceClassName, String _cleanInterfaceClassName) {

        modPluginName = _modPluginName;
        load = _load;
        serverPluginName = _serverPluginName;

        serverInterfaceClassName = _serverInterfaceClassName;
        reflectionInterfaceClassName = _reflectionInterfaceClassName;
        cleanInterfaceClassName = _cleanInterfaceClassName;

    }

    public DataOfAccessorPlugin(String _modPluginName, boolean _load, String _serverPluginName, String _serverInterfaceClassName, String _reflectionInterfaceClassName, String _cleanInterfaceClassName, Map<String, Object> _xProperties) {

        this(_modPluginName, _load, _serverPluginName, _serverInterfaceClassName, _reflectionInterfaceClassName, _cleanInterfaceClassName);

        xProperties = _xProperties;

    }
}

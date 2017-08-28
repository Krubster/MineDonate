package ru.log_inil.mc.minedonate.localData;

import java.util.Map;

public class DataOfAccessorPlugin {

	public String modPluginName ;
	public boolean load ;
	
	public String serverPluginName ;

	public String serverInterfaceClassName ;
	public String reflectionInterfaceClassName ;
	public String cleanInterfaceClassName ;

	public Map < String, Object > xProperties ;
	
	public DataOfAccessorPlugin ( String _modPluginName, boolean _load, String _serverPluginName, String _serverInterfaceClassName, String _reflectionInterfaceClassName, String _cleanInterfaceClassName ) {
		
		modPluginName = _modPluginName ;
		load = _load ;
		serverPluginName = _serverPluginName ;

		serverInterfaceClassName = _serverInterfaceClassName ;
		reflectionInterfaceClassName = _reflectionInterfaceClassName ;
		cleanInterfaceClassName = _cleanInterfaceClassName ;
		
	}
	
}

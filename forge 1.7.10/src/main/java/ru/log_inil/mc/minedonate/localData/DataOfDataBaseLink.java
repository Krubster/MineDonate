package ru.log_inil.mc.minedonate.localData;

public class DataOfDataBaseLink {

	public String host, name, user, password ;
	public int port ;

	public boolean useUTF8 = true ;

	public boolean hasCustomLink = false ;
	public String customLink = "" ;
	public String preLoadClassName = "" ;
	
	public DataOfDataBaseLink ( ) {
		
	}
	
	public DataOfDataBaseLink ( String _host, int _port, String _name, String _user, String _password, String _preLoadClassName ) {
		
		host = _host ;
		port = _port ;
		name = _name ;
		user = _user ;
		password = _password ;
		preLoadClassName = _preLoadClassName ;
		
	}

}

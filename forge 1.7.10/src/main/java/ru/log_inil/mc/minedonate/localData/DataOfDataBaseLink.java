package ru.log_inil.mc.minedonate.localData;

import java.util.Map;

public class DataOfDataBaseLink {

	// Доступ к базе
	public String host, name, user, password ;
	public int port ;

	// Добавлять в ссылку аргумент включения юникода
	public boolean useUTF8 = true ;

	// Использовать другую ссылку
	public boolean hasCustomLink = false ;
	
	// Сама ссылка (можно использовать %host%, %name%, %user%, для авто-замены из полей данного объекта(класса)  )
	public String customLink = "" ;
	
	// (Указывать если необходим) Загружаемый класс драйвера 
	public String preLoadClassName = "" ;
	
	public static Map < String, Object > xProperties ;

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

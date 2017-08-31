package ru.log_inil.mc.minedonate.localData;

public class DataOfPermissionEntry {

	// Имя разрешения, проверяемое в плагине пермишенов
	public String permission ;
	
	// Выдаваемые группы мода с разрешениями(в бд)
	public String [ ] groups ;
	
	public DataOfPermissionEntry ( String _permission, String [ ] _groups ) {
		
		permission = _permission ;
		groups = _groups ;
		
	}
	
}

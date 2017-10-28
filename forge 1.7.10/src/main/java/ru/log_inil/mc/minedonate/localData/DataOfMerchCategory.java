package ru.log_inil.mc.minedonate.localData;

import java.util.Map;

public class DataOfMerchCategory {

	public boolean enabled ;
	public String type ;
	
	public int orderId ;
	public String title ;
	
	public String merchCategoryClassName ;
	public String merchEntryClassName ;

	public String dbLinkName ;
	
	public String moneyType ;

	public Map < String, Object > xProperties ;

	public DataOfMerchCategory ( ) {
		
		enabled = false ;
		type = "-" ;
		orderId = -1 ;
		title = "-" ;
		merchCategoryClassName = "-" ;
		merchEntryClassName = "-" ;
		dbLinkName = "main" ;
		moneyType = "coins" ;
		
	}
	
	public DataOfMerchCategory ( String _type, int _orderId, String _title, String _merchCategoryClassName, String _merchEntryClassName ) {
	
		this ( ) ;
		
		type = _type ;
		orderId = _orderId ;
		title = _title ;
		merchCategoryClassName = _merchCategoryClassName ;
		merchEntryClassName = _merchEntryClassName ;
		
	}
	
}

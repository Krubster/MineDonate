
package ru.log_inil.mc.minedonate.localData;

import java.util.Map;

public class DataOfMoneyProcessor {

	public String moneyType, className, dbTable, dbIdColumn, dbNameColumn, dbMoneyColumn, dbLinkName ;
	public boolean isTwoSideProcessor ;
	public int regMoney ;
	
	public static Map < String, Object > xProperties ;

	public DataOfMoneyProcessor ( ) {
		
		moneyType = "unknown" ;
		className = "?" ;
		regMoney = 0 ;
		
	}

    public DataOfMoneyProcessor(String _mt, String _className, String _dbTable, String _dbIdColumn, String _dbNameColumn, String _dbMoneyColumn, String _dbLinkName, boolean _isTwoSideProcessor) {

        moneyType = _mt ;
		className = _className ;
		dbTable = _dbTable ;
        dbIdColumn = _dbIdColumn;
        dbNameColumn = _dbNameColumn ;
        dbMoneyColumn = _dbMoneyColumn ;
		isTwoSideProcessor = _isTwoSideProcessor; ;
		dbLinkName = _dbLinkName ;
		regMoney = 0 ;
		
	}
	
}

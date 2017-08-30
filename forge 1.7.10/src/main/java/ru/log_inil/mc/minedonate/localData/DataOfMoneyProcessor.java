
package ru.log_inil.mc.minedonate.localData;

import java.util.Map;

public class DataOfMoneyProcessor {

	// Загружать
	public boolean load ;
	
	// Тип денег, должен быть равен тому, что прописывается в категории
	public String moneyType;
	
	// Класс реализующий AbstractMoneyProcessor
	public String className ;
	
	public String dbTable, dbIdColumn, dbNameColumn ;
	
	// Колонка с деньгами, которая должна в бд иметь значение по умолчанию
	public String dbMoneyColumn ;
	
	// Имя используемой базы
	public String dbLinkName ;
	
	// Процессор работает в обе стороны (т.е. совершена покупка в магазине, и со счета покупателя выполнен перевод на счет владельца магазина )
	public boolean isTwoSideProcessor ;
	
	// Первичные деньги аккаунта
	public int regMoney ;
	
	public Map < String, Object > xProperties ;

	public DataOfMoneyProcessor ( ) {
		
		load = true ;
		moneyType = "unknown" ;
		className = "?" ;
		regMoney = 0 ;
		
	}

    public DataOfMoneyProcessor ( boolean _load, String _mt, String _className, String _dbTable, String _dbIdColumn, String _dbNameColumn, String _dbMoneyColumn, String _dbLinkName, boolean _isTwoSideProcessor) {

    	load = _load ;
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
	
    public DataOfMoneyProcessor ( boolean _load, String _mt, String _className, String _dbTable, String _dbIdColumn, String _dbNameColumn, String _dbMoneyColumn, String _dbLinkName, boolean _isTwoSideProcessor, Map < String, Object > _xProperties ) {

        this ( _load, _mt, _className, _dbTable, _dbIdColumn, _dbNameColumn, _dbMoneyColumn, _dbLinkName, _isTwoSideProcessor ) ;
		
        xProperties = _xProperties ;
        
	}
}

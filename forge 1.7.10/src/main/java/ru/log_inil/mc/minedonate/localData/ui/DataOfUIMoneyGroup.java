package ru.log_inil.mc.minedonate.localData.ui;

public class DataOfUIMoneyGroup {

	public String id ;
	public String pricePref ;
	public String priceSuff ;
	public String balancePref ;
	public String balanceSuff ;
	public boolean imageIsResource ;
	public String image ;
	
	public DataOfUIMoneyGroup ( ) {
		
		id = "unknown" ;
		pricePref = "" ;
		priceSuff = "" ;
		balancePref = "" ;
		balanceSuff = "" ;
		imageIsResource = true ;
		image = "minedonate:images/unknown.png" ;
		
	}
	
	public DataOfUIMoneyGroup ( String _id, String _image ) {
		
		id = _id ;
		pricePref = "" ;
		priceSuff = "" ;
		balancePref = "" ;
		balanceSuff = "" ;
		imageIsResource = true ;
		image = _image ;
		
	}
	
}

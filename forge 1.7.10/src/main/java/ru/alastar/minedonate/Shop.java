package ru.alastar.minedonate;

import ru.alastar.minedonate.merch.categories.MerchCategory;

public class Shop {

	public int sid ;
	public MerchCategory [ ] cats ;
	public String owner ;
	public String name ;
	public boolean isFreezed ;
	
	public Shop ( int _sid, MerchCategory [ ] _cats, String _owner, String _name, boolean _isFreezed ) {
		
		sid = _sid ;
		cats = _cats ;
		owner = _owner ;
		name = _name ;
		isFreezed = _isFreezed ;
		
	}

}

package ru.log_inil.mc.minedonate.localData.ui;

import ru.log_inil.mc.minedonate.localData.frames.*;

public class DataOfUIFrames {

	//public DataOfUIFrameRenameItem rename ;
	public DataOfUIFrameCreateShop createShop ;
	public DataOfUIFrameRenameShop renameShop ;
	public DataOfUIFrameDeleteShop deleteShop ;
	public DataOfUIFrameFreezeShop freezeShop ;
	
	public DataOfUIFrameAddItem addItem ;
	public DataOfUIFramEditItem editItem ;
	public DataOfUIFrameDeleteItem deleteItem ;
	
	public DataOfUIFrameAddEntity addEntity ;
	public DataOfUIFrameEditEntity editEntity ;
	public DataOfUIFrameDeleteEntity deleteEntity ;
	
	public DataOfUIFrameFreezeAccount freezeAccount;

	public DataOfUIFrames ( ) {
	
		createShop = new DataOfUIFrameCreateShop ( ) ;
		renameShop = new DataOfUIFrameRenameShop ( ) ;	
		deleteShop = new DataOfUIFrameDeleteShop ( ) ;
		freezeShop = new DataOfUIFrameFreezeShop ( ) ;
		
		addItem = new DataOfUIFrameAddItem ( ) ;
		editItem = new DataOfUIFramEditItem ( ) ;
		deleteItem = new DataOfUIFrameDeleteItem ( ) ;
		
		addEntity = new DataOfUIFrameAddEntity ( ) ;
		editEntity = new DataOfUIFrameEditEntity ( ) ;
		deleteEntity = new DataOfUIFrameDeleteEntity ( ) ;
		
		freezeAccount = new DataOfUIFrameFreezeAccount ( ) ;
				
	}
	
}

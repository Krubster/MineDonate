package ru.log_inil.mc.minedonate.localData.ui;

import ru.log_inil.mc.minedonate.localData.frames.*;

public class DataOfUIFrames {

	//public DataOfUIFrameRenameItem rename ;
	public DataOfUIFrameCreateShop createShop ;
	public DataOfUIFrameRenameShop renameShop ;
	public DataOfUIFrameDeleteShop deleteShop ;
	public DataOfUIFrameFreezeShop freezeShop ;
	
	public DataOfUIFrameRenameItem renameItem ;
	public DataOfUIFrameDeleteItem deleteItem ;
	
	public DataOfUIFrameRenameEntity renameEntity ;
	public DataOfUIFrameDeleteEntity deleteEntity ;

	//public DataOfUIFrameDeleteItem deleteEntity ;

	public DataOfUIFrames ( ) {
	
		createShop = new DataOfUIFrameCreateShop ( ) ;
		renameShop = new DataOfUIFrameRenameShop ( ) ;	
		deleteShop = new DataOfUIFrameDeleteShop ( ) ;
		freezeShop = new DataOfUIFrameFreezeShop ( ) ;
		
		renameItem = new DataOfUIFrameRenameItem ( ) ;
		deleteItem = new DataOfUIFrameDeleteItem ( ) ;
		
		renameEntity = new DataOfUIFrameRenameEntity ( ) ;
		deleteEntity = new DataOfUIFrameDeleteEntity ( ) ;
		
		//rename = new DataOfUIFrameRenameItem ( ) ;
		
	}
	
}

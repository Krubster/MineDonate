package ru.log_inil.mc.minedonate.localData.ui;

import java.util.HashMap;
import java.util.Map;

import ru.alastar.minedonate.network.manage.packets.ManageResponsePacket;
import ru.alastar.minedonate.network.manage.packets.ManageResponsePacket.ResponseCode;
import ru.alastar.minedonate.network.manage.packets.ManageResponsePacket.ResponseStatus;
import ru.alastar.minedonate.network.manage.packets.ManageResponsePacket.ResponseType;
import ru.alastar.minedonate.network.packets.BuyResponsePacket;
import ru.alastar.minedonate.network.packets.BuyResponsePacket.Status;

public class DataOfUILang {

	public String editEntityMerch ;
	public String deleteEntityMerch ;
	
	public String editItemMerch ;
	public String deleteItemMerch ;
	
	public String freezeShop ;
	public String unfreezeShop ;
	public String renameShop ;
	public String deleteShop ;
	
	public String freezeAccount ;
	public String unfreezeAccount ;
	
	public String shopFreezer ;
	public String shopFreezReason ;
	
	public Map < String, String > actions ;
	
	public DataOfUILang ( ) {
		
		editEntityMerch = "Edit this" ;
		deleteEntityMerch = "Delete this" ;
		
		editItemMerch = "Edit this" ;
		deleteItemMerch = "Delete this" ;
		
		freezeShop = "Freeze this shop" ;
		unfreezeShop = "Unfreeze this shop" ;
		renameShop = "Rename this shop" ;
		deleteShop = "Delete this shop";
		
		freezeAccount = "Freeze owner account" ;
		unfreezeAccount = "Unfreeze owner account" ;

		shopFreezer = "Freezer: ";
		shopFreezReason = "Reason: ";
		
		actions = new HashMap < > ( ) ;
	
		ManageResponsePacket.ResponseType type;
		ManageResponsePacket.ResponseCode code;
		 
		type = ManageResponsePacket.ResponseType.OBJ;
		code = ManageResponsePacket.ResponseCode.ADD;
		
		add ( actions, type, code, ResponseStatus.ERROR_SHOP_NOTFOUND, "Shop not found!" ) ;
		add ( actions, type, code, ResponseStatus.ERROR_SHOP_FREEZED, "Shop freezed!" ) ;
		add ( actions, type, code, ResponseStatus.ERROR_UNKNOWN, "Unknown error!" ) ;
		add ( actions, type, code, ResponseStatus.ERROR_ACCESS_DENIED, "Access denied!" ) ;
		add ( actions, type, code, ResponseStatus.OK, "Added!" ) ;
		
		//
		
		type = ManageResponsePacket.ResponseType.OBJ;
		code = ManageResponsePacket.ResponseCode.APPEND;
		
		add ( actions, type, code, ResponseStatus.ERROR_SHOP_NOTFOUND, "Shop not found!" ) ;
		add ( actions, type, code, ResponseStatus.ERROR_SHOP_FREEZED, "Shop freezed!" ) ;
		add ( actions, type, code, ResponseStatus.ERROR_CAT_NOTFOUND, "Category not found!" ) ;
		add ( actions, type, code, ResponseStatus.ERROR_UNKNOWN, "Unknown error!" ) ;
		add ( actions, type, code, ResponseStatus.ERROR_ENTRY_NOTFOUND, "Object not found!" ) ;
		add ( actions, type, code, ResponseStatus.ERROR_ACCESS_DENIED, "Access denied!" ) ;
		add ( actions, type, code, ResponseStatus.OK, "Appended!" ) ;
		
		//
		
		type = ManageResponsePacket.ResponseType.SHOP;
		code = ManageResponsePacket.ResponseCode.CREATE;
		
		add ( actions, type, code, ResponseStatus.ERROR_UNKNOWN, "Unknown error!" ) ;
		add ( actions, type, code, ResponseStatus.ERROR_SHOP_CREATE_BAN, "You banned!" ) ;
		add ( actions, type, code, ResponseStatus.ERROR_SHOP_CREATE_LIMIT, "You can't create many shops!" ) ;
		add ( actions, type, code, ResponseStatus.OK, "Created!" ) ;
		add ( actions, type, code, ResponseStatus.ERROR_ACCESS_DENIED, "Access denied!" ) ;

		//
		
		type = ManageResponsePacket.ResponseType.OBJ;
		code = ManageResponsePacket.ResponseCode.REMOVE;
		
		add ( actions, type, code, ResponseStatus.ERROR_SHOP_NOTFOUND, "Shop not found!" ) ;
		add ( actions, type, code, ResponseStatus.ERROR_SHOP_FREEZED, "Shop freezed!" ) ;
		add ( actions, type, code, ResponseStatus.ERROR_CAT_NOTFOUND, "Category not found!" ) ;
		add ( actions, type, code, ResponseStatus.ERROR_ENTRY_NOTFOUND, "Object not found!" ) ;
		add ( actions, type, code, ResponseStatus.OK, "Deleted!" ) ;
		add ( actions, type, code, ResponseStatus.ERROR_ACCESS_DENIED, "Access denied!" ) ;

		//
		
		type = ManageResponsePacket.ResponseType.SHOP;
		code = ManageResponsePacket.ResponseCode.REMOVE;
		
		add ( actions, type, code, ResponseStatus.ERROR_SHOP_NOTFOUND, "Shop not found!" ) ;
		add ( actions, type, code, ResponseStatus.ERROR_SHOP_FREEZED, "Shop freezed!" ) ;
		add ( actions, type, code, ResponseStatus.OK, "Deleted!" ) ;
		add ( actions, type, code, ResponseStatus.ERROR_ACCESS_DENIED, "Access denied!" ) ;
		
		//
		
		type = ManageResponsePacket.ResponseType.OBJ;
		code = ManageResponsePacket.ResponseCode.EDIT;
		
		add ( actions, type, code, ResponseStatus.ERROR_SHOP_NOTFOUND, "Shop not found!" ) ;
		add ( actions, type, code, ResponseStatus.ERROR_SHOP_FREEZED, "Shop freezed!" ) ;
		add ( actions, type, code, ResponseStatus.ERROR_UNKNOWN, "Unknown error!" ) ;
		add ( actions, type, code, ResponseStatus.ERROR_ACCESS_DENIED, "Access denied!" ) ;
		add ( actions, type, code, ResponseStatus.ERROR_CAT_NOTFOUND, "Category not found!" ) ;
		add ( actions, type, code, ResponseStatus.ERROR_ENTRY_NOTFOUND, "Object not found!" ) ;
		add ( actions, type, code, ResponseStatus.ERROR_NEGATIVE_INTEGER, "Negative integer!" ) ;
		add ( actions, type, code, ResponseStatus.OK, "Edited!" ) ;
		
		//
		
		type = ManageResponsePacket.ResponseType.SHOP;
		code = ManageResponsePacket.ResponseCode.UNFREEZ;
		
		add ( actions, type, code, ResponseStatus.ERROR_SHOP_NOTFOUND, "Shop not found!" ) ;
		add ( actions, type, code, ResponseStatus.OK, "Unfreezed!" ) ;
		add ( actions, type, code, ResponseStatus.ERROR_ACCESS_DENIED, "Access denied!" ) ;
		add ( actions, type, code, ResponseStatus.ERRROR_ACCOUNT_NO_FREEZED, "Shop no freezed!" ) ;

		type = ManageResponsePacket.ResponseType.SHOP;
		code = ManageResponsePacket.ResponseCode.FREEZ;
		
		add ( actions, type, code, ResponseStatus.ERROR_SHOP_NOTFOUND, "Shop not found!" ) ;
		add ( actions, type, code, ResponseStatus.OK, "Freezed!" ) ;
		add ( actions, type, code, ResponseStatus.ERROR_ACCESS_DENIED, "Access denied!" ) ;
		add ( actions, type, code, ResponseStatus.ERRROR_ACCOUNT_ALREADY_FREEZED, "Shop already freezed!" ) ;
		add ( actions, type, code, ResponseStatus.ERROR_UNKNOWN, "Unknown error!" ) ;

		//
		
		type = ManageResponsePacket.ResponseType.ACCOUNT;
		code = ManageResponsePacket.ResponseCode.UNFREEZ;
		
		add ( actions, type, code, ResponseStatus.ERROR_ACCOUNT_NOTFOUND, "Account not found!" ) ;
		add ( actions, type, code, ResponseStatus.OK, "Unfreezed!" ) ;
		add ( actions, type, code, ResponseStatus.ERROR_ACCESS_DENIED, "Access denied!" ) ;
		add ( actions, type, code, ResponseStatus.ERROR_UNKNOWN, "Unknown error!" ) ;
		add ( actions, type, code, ResponseStatus.ERRROR_ACCOUNT_NO_FREEZED, "Account no freezed!" ) ;

		type = ManageResponsePacket.ResponseType.ACCOUNT;
		code = ManageResponsePacket.ResponseCode.FREEZ;
		
		add ( actions, type, code, ResponseStatus.ERROR_ACCOUNT_NOTFOUND, "Account not found!" ) ;
		add ( actions, type, code, ResponseStatus.OK, "Freezed!" ) ;
		add ( actions, type, code, ResponseStatus.ERROR_ACCESS_DENIED, "Access denied!" ) ;
		add ( actions, type, code, ResponseStatus.ERRROR_ACCOUNT_ALREADY_FREEZED, "Account already freezed!" ) ;
		add ( actions, type, code, ResponseStatus.ERROR_UNKNOWN, "Unknown error!" ) ;

		//
		
		type = ManageResponsePacket.ResponseType.ENTITY;
		code = ManageResponsePacket.ResponseCode.ADD;
		
		add ( actions, type, code, ResponseStatus.ENTITY_SELECT_START, "Entity select start!" ) ;
		add ( actions, type, code, ResponseStatus.ENTITY_SELECT_STOP, "Entity select stop!" ) ;
		
		//
		
		type = ManageResponsePacket.ResponseType.SHOP;
		code = ManageResponsePacket.ResponseCode.RENAME;
		
		add ( actions, type, code, ResponseStatus.ENTITY_SELECT_START, "Entity select start!" ) ;
		add ( actions, type, code, ResponseStatus.ENTITY_SELECT_STOP, "Entity select stop!" ) ;
		
		add ( actions, type, code, ResponseStatus.ERROR_SHOP_NOTFOUND, "Shop not found!" ) ;
		add ( actions, type, code, ResponseStatus.ERROR_SHOP_FREEZED, "Shop freezed!" ) ;
		add ( actions, type, code, ResponseStatus.ERROR_UNKNOWN, "Unknown error!" ) ;
		add ( actions, type, code, ResponseStatus.ERROR_ACCESS_DENIED, "Access denied!" ) ;
		add ( actions, type, code, ResponseStatus.OK, "Renamed!" ) ;
		
		////
		
		//
		
		type = ManageResponsePacket.ResponseType.ENTITY;
		code = ManageResponsePacket.ResponseCode.SELECT;
		
		add ( actions, type, code, ResponseStatus.ERROR_ENTITY_CHECK_LIVINGBASE, "No valid entity!" ) ;
		add ( actions, type, code, ResponseStatus.ERROR_ENTITY_CHECK_INIT, "Can't init entity object!" ) ;
		add ( actions, type, code, ResponseStatus.OK, "Selected!" ) ;

		////
		
		actions . put ( "BUY>" + BuyResponsePacket.Status.SUCCESSFUL.name(), "Buyed!" ) ;
		actions . put ( "BUY>" + BuyResponsePacket.Status.ERROR_CANT_BUY.name(), "Can't buy!" ) ;
		actions . put ( "BUY>" + BuyResponsePacket.Status.ERROR_NOT_ENOUGH_MONEY.name(), "Not enough money!" ) ;
		actions . put ( "BUY>" + BuyResponsePacket.Status.ERROR_SHOP_FREEZED.name(), "Shop freezed!" ) ;
		actions . put ( "BUY>" + BuyResponsePacket.Status.ERROR_UNKNOWN.name(), "Unknown error!" ) ;

	}
	
	public void add ( Map < String, String > m, ManageResponsePacket.ResponseType type, ManageResponsePacket.ResponseCode code, ManageResponsePacket.ResponseStatus status, String text ) {
		
		m . put ( type . name ( ) + ">" + code . name ( ) + ">" + status . name ( ), text ) ;
		
	}
	
	public String get ( ManageResponsePacket.ResponseType type, ManageResponsePacket.ResponseCode code, ManageResponsePacket.ResponseStatus status ) {
		
		String r = actions . get ( type . name ( ) + ">" + code . name ( ) + ">" + status . name ( ) ) ;
		
		if ( r != null ) {
			
			return r ;
			
		}
		
		return type . name ( ) + "> " + code . name ( ) + "> " + status . name ( ) ;
		
	}
	
	public String get ( BuyResponsePacket.Status type ) {
		
		String r = actions . get ( "BUY>" + type . name ( ) ) ;
		
		if ( r != null ) {
			
			return r ;
			
		}
		
		return type . name ( ) ;
		
	}
}

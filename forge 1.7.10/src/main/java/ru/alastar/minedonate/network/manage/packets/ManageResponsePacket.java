package ru.alastar.minedonate.network.manage.packets;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;

public class ManageResponsePacket implements IMessage {

	public ResponseType type ;
	public ResponseCode code ;
	public ResponseStatus status ;
	
    public ManageResponsePacket ( ) { }

    public ManageResponsePacket ( ResponseType _type, ResponseCode _code, ResponseStatus _status ) {
    	
    	type = _type ;
    	code = _code ;
    	status = _status ;
    	    	
    }
    
    @Override 
    public void toBytes ( ByteBuf buf ) {
		
		buf . writeByte ( type . ordinal ( ) ) ;
		buf . writeByte ( code . ordinal ( ) ) ;
		buf . writeByte ( status . ordinal ( ) ) ;

    }

    @Override 
    public void fromBytes ( ByteBuf buf ) {

    	type = ResponseType . values ( ) [ buf . readByte ( ) ] ;
    	code = ResponseCode . values ( ) [ buf . readByte ( ) ] ;
    	status = ResponseStatus . values ( ) [ buf . readByte ( ) ] ;

    }
    
    public enum ResponseType {
    	
    	ITEM, ENTITY, SHOP, OBJ, REFRESH_MAIN_SHOP, ACCOUNT
    	
    }
    
    public enum ResponseCode {
    	
    	CREATE, ADD, APPEND, SELECT, RENAME, REMOVE, EDIT, FREEZ, UNFREEZ
    	
    }
    
    public enum ResponseStatus {
    	
    	OK, 
    	ERROR_SHOP_NOTFOUND, ERROR_SHOP_CREATE_LIMIT, ERROR_SHOP_CREATE_BAN, ERROR_SHOP_FREEZED, ERROR_SHOP_ALREADY_FREEZED, ERROR_SHOP_NO_FREEZED, ERROR_ACCOUNT_NOTFOUND, 
    	ERROR_CAT_NOTFOUND, ERROR_ENTRY_NOTFOUND, ERROR_NEGATIVE_INTEGER,
    	ENTITY_SELECT_START, ENTITY_SELECT_STOP,
    	ERROR_ENTITY_CHECK_LIVINGBASE, ERROR_ENTITY_CHECK_INIT,
    	ERRROR_ACCOUNT_ALREADY_FREEZED, ERRROR_ACCOUNT_NO_FREEZED,
    	ERROR_ACCESS_DENIED, ERROR_UNKNOWN
    	
    }
    
 }
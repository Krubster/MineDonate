package ru.alastar.minedonate.network.packets.manage;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;

public class ManageResponsePacket implements IMessage {

	int type ;
	int code ;
	int status ;
	
    public ManageResponsePacket ( ) { }
    
    public ManageResponsePacket ( int _type, int _code, int _status ) {
    	
    	type = _type ;
    	code = _code ;
    	status = _status ;
    	
    }

    public ManageResponsePacket ( ResponseType _type, ResponseCode _code, ResponseStatus _status ) {
    	
    	this ( _type . ordinal ( ), _code . ordinal ( ), _status . ordinal ( ) ) ;
    	
    }
    
    @Override 
    public void toBytes ( ByteBuf buf ) {
		
		buf . writeInt ( type ) ;
		buf . writeInt ( code ) ;
		buf . writeInt ( status ) ;

    }

    @Override 
    public void fromBytes ( ByteBuf buf ) {

    	type = buf . readInt ( ) ;
    	code = buf . readInt ( ) ;
    	status = buf . readInt ( ) ;

    }
    
    public enum ResponseType {
    	
    	ITEM, ENTITY, SHOP, ITEMS, REFRESH_MAIN_SHOP
    	
    }
    
    public enum ResponseCode {
    	
    	CREATE, ADD, RENAME, REMOVE, PUTT, FREEZ, UNFREEZ
    	
    }
    
    public enum ResponseStatus {
    	
    	OK, ERROR_SHOP_CREATE_LIMIT, ERROR_ACCESS_DENIED, ERROR_INVENTORY_FULL, ERROR_UNKNOWN
    	
    }
    
 }
package ru.alastar.minedonate.network.packets.manage;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;
import ru.alastar.minedonate.rtnl.Utils;

public class AddNewItemPacket implements IMessage {

	public int shopId, catId, limit, cost ;
	public String name ;
	
    public AddNewItemPacket ( ) { }
    public AddNewItemPacket ( int _shopId, int _catId, int _limit, int _cost, String _name ) {
    	
    	shopId = _shopId ;
    	catId = _catId ;
    	limit = _limit ;
    	cost = _cost ;
    	
    	name = _name ;
    	
    }

    @Override 
    public void toBytes ( ByteBuf buf ) {
    	
    	try {
			
    		buf . writeInt ( shopId ) ; 
    		buf . writeInt ( catId ) ;
    		
    		buf . writeInt ( limit ) ; 
    		buf . writeInt ( cost ) ;

    		Utils . netWriteString ( buf, name ) ;
			
		} catch ( Exception ex ) {
			
			ex . printStackTrace ( ) ;
			
		}
    	
    }

    @Override 
    public void fromBytes ( ByteBuf buf ) {
    	
       try {
    	   
    	   shopId = buf . readInt ( ) ;
    	   catId = buf . readInt ( ) ;
    	   
    	   limit = buf . readInt ( ) ;
    	   cost = buf . readInt ( ) ;

    	   name = Utils . netReadString ( buf ) ;
           
       } catch ( Exception ex ) {
    	   
    	   ex . printStackTrace ( ) ;
    	   
       }

    }
    
 }
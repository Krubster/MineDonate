package ru.alastar.minedonate.network.packets;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;
import ru.alastar.minedonate.gui.ShopCategory;
import ru.alastar.minedonate.rtnl.Utils;

public class CategoryPacket implements IMessage {

	public int shopId, catId ;

    public CategoryPacket(){}
    public CategoryPacket(int _shopId, int _catId ) {
    	
    	shopId = _shopId ;
    	catId = _catId ;
    	
    }

    @Override 
    public void toBytes(ByteBuf buf) {
    	
		try {
			
			buf . writeInt ( shopId ) ;
			buf . writeInt ( catId ) ;
			
       } catch ( Exception ex ) {
    	   
    	   ex . printStackTrace ( ) ;
    	   
       }
    	
    }

    @Override 
    public void fromBytes(ByteBuf buf) {
    	
       try {
    	   
    	   shopId = buf . readInt ( ) ;
    	   catId = buf . readInt ( ) ;
           
       } catch ( Exception ex ) {
    	   
    	   ex . printStackTrace ( ) ;
    	   
       }

    }
    
 }
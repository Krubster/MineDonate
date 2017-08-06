package ru.alastar.minedonate.network.packets;

import java.io.UnsupportedEncodingException;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;
import ru.alastar.minedonate.Utils;
import ru.log_inil.mc.minedonate.localData.DataOfConfig;

/**
 * Created by Alastar on 20.07.2017.
 */
public class SupportedFeaturesPacket implements IMessage {

    public SupportedFeaturesPacket(){}

    public boolean items;
    public String itemsMoneyType ;
    public boolean privelegies;
    public String privelegiesMoneyType ;
    public boolean regions;
    public String regionsMoneyType ;
    public boolean entities;
    public String entitiesMoneyType ;
    public boolean userShops;

    public SupportedFeaturesPacket ( DataOfConfig _cfg ) {
    	
    	items = _cfg . sellItems ;
    	itemsMoneyType = _cfg . itemsMoneyType ;
    	
    	privelegies = _cfg . sellPrivelegies ;
    	privelegiesMoneyType = _cfg . privelegiesMoneyType ;
    	
    	regions = _cfg . sellRegions ;
    	regionsMoneyType = _cfg . regionMoneyType ;
    	
    	entities = _cfg . sellEntities ;
    	entitiesMoneyType = _cfg . entitiesMoneyType ;
    	
    	userShops = _cfg . userShops ;
    	
    }

    @Override 
    public void toBytes(ByteBuf buf) {
    	
        try {
        	
        	buf.writeBoolean(items);
            Utils . netWriteString ( buf, itemsMoneyType ) ;
            
            buf.writeBoolean(privelegies);  
            Utils . netWriteString ( buf, privelegiesMoneyType ) ;
            
            buf.writeBoolean(regions);
            Utils . netWriteString ( buf, regionsMoneyType ) ;
            
            buf.writeBoolean(entities); 
            Utils . netWriteString ( buf, entitiesMoneyType ) ;
            
            buf.writeBoolean(userShops);
            
        } catch ( Exception ex ) {
        	
        	ex . printStackTrace( ) ;
        	
        }

    }

    @Override 
    public void fromBytes(ByteBuf buf) {
    	
       try {
    	   
    	   this.items = buf.readBoolean();
    	   this.itemsMoneyType = Utils . netReadString ( buf ) ;
    	   
           this.privelegies = buf.readBoolean();
    	   this.privelegiesMoneyType = Utils . netReadString ( buf ) ;
    	   
           this.regions = buf.readBoolean();
    	   this.regionsMoneyType = Utils . netReadString ( buf ) ;
    	   
           this.entities = buf.readBoolean();
    	   this.entitiesMoneyType = Utils . netReadString ( buf ) ;
    	   
           this.userShops = buf.readBoolean();
           
       } catch ( Exception ex ) {
    	   
    	   ex . printStackTrace ( ) ;
    	   
       }

    }
    
}
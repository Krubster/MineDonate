package ru.alastar.minedonate.network.packets;

import java.io.UnsupportedEncodingException;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;
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
    	
        buf.writeBoolean(items);
        writeString ( buf, itemsMoneyType ) ;
        buf.writeBoolean(privelegies);
        writeString ( buf, privelegiesMoneyType ) ;
        buf.writeBoolean(regions);
        writeString ( buf, regionsMoneyType ) ;
        buf.writeBoolean(entities);
        writeString ( buf, entitiesMoneyType ) ;
        buf.writeBoolean(userShops);

    }

    @Override 
    public void fromBytes(ByteBuf buf) {
    	
       try {
    	   
    	   this.items = buf.readBoolean();
    	   this.itemsMoneyType = readString ( buf ) ;
           this.privelegies = buf.readBoolean();
    	   this.privelegiesMoneyType = readString ( buf ) ;
           this.regions = buf.readBoolean();
    	   this.regionsMoneyType = readString ( buf ) ;
           this.entities = buf.readBoolean();
    	   this.entitiesMoneyType = readString ( buf ) ;
           this.userShops = buf.readBoolean();
           
       } catch ( Exception ex ) {
    	   
    	   ex . printStackTrace ( ) ;
    	   
       }

    }
    
    String readString ( ByteBuf buf ) throws UnsupportedEncodingException {
    	
        return new String ( buf . readBytes ( buf . readInt ( ) ) . array ( ), "UTF-8" ) ;
        
    }
     
    
    void writeString ( ByteBuf buf, String str ) {
    	
        buf.writeInt(str.getBytes().length);
        buf.writeBytes(str.getBytes());
        
    }
     }
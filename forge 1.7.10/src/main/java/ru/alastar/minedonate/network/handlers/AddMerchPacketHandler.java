package ru.alastar.minedonate.network.handlers;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

import ru.alastar.minedonate.MineDonate;
import ru.alastar.minedonate.gui.ShopGUI;
import ru.alastar.minedonate.merch.categories.ItemNBlocks;
import ru.alastar.minedonate.merch.categories.MerchCategory;
import ru.alastar.minedonate.merch.info.ShopInfo;
import ru.alastar.minedonate.network.packets.AddMerchPacket;
import ru.alastar.minedonate.rtnl.Shop;

/**
 * Created by Alastar on 18.07.2017.
 */
public class AddMerchPacketHandler implements IMessageHandler<AddMerchPacket, IMessage> {

    public AddMerchPacketHandler() {

    }

    @Override
    public IMessage onMessage ( AddMerchPacket message, MessageContext ctx ) {
    	
        if ( message . info instanceof ShopInfo ) {
        	
        	ShopInfo us = ( ShopInfo ) message . info ;
        	
        	if ( us . shopId == 0 ) {
        		
        	    if ( ShopGUI . instance != null ) {
        	    	
                    ShopGUI . instance . updateButtons ( true ) ;
                    
                }
        	    
        		return null ;
        		
        	}

        	MineDonate . shops . put ( us . shopId, new Shop ( us . shopId, new MerchCategory [ ] { new ItemNBlocks ( us . shopId, us . getCategory ( ), us . moneyType ) }, us . owner, us . ownerName, us . name, us . isFreezed, us . freezer, us . freezReason, us . canVisibleFreezedText ) ) ;
        	
        }

        MineDonate . addMerch ( message . shopId, message . m_category, message . info ) ;

        if ( ShopGUI . instance != null ) {
        
        	ShopGUI . instance . updateButtons ( true ) ;
        
        }
        
        return null;
    }

}
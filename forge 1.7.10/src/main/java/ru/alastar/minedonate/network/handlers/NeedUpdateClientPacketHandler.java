package ru.alastar.minedonate.network.handlers;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import ru.alastar.minedonate.MineDonate;
import ru.alastar.minedonate.gui.ShopGUI;
import ru.alastar.minedonate.gui.categories.ItemNBlockCategory;
import ru.alastar.minedonate.gui.categories.UsersShopsCategory;
import ru.alastar.minedonate.network.packets.NeedUpdatePacket;

public class NeedUpdateClientPacketHandler implements IMessageHandler<NeedUpdatePacket, IMessage> {

    public NeedUpdateClientPacketHandler() {

    }

    @Override 
    public IMessage onMessage(NeedUpdatePacket message, MessageContext ctx) {
    	
    	if ( message . r == 1 ) {
	           
			if ( ShopGUI . instance != null ) {

				ShopGUI . instance . lastCategory = ShopGUI . instance . m_Selected_Category = ShopGUI . instance . defaultCategory ;
				ShopGUI . instance . needNetUpdate = false ;
				ShopGUI . instance . loading = false ;

			}      
    			
    	}
    	
    	if ( message . r == 2 ) {
	           
			if ( ShopGUI . instance != null ) {
				 
				ShopGUI . instance . loading = false ;

				if ( ShopGUI . instance . getCurrentCategory ( ) instanceof UsersShopsCategory ) {
					
					UsersShopsCategory usc = ( UsersShopsCategory ) ShopGUI . instance . getCurrentCategory ( )  ;

					if ( usc . getSelectedShop ( ) != null ) {

						ShopGUI . instance . currentShop = usc . getSelectedShop ( ) . shopId ;

						ItemNBlockCategory cat = new ItemNBlockCategory ( "cat.items.custom" ) ;
						cat . shopOwner = usc . getSelectedShop ( ) . owner ;
						cat . setDisableCatCheck ( ) ;
						
						cat . preShow ( ShopGUI . instance ) ;
						
						cat . setShopId ( usc . getSelectedShop ( ) . shopId ) ;
						
						usc . updateUserShopCategory ( ShopGUI . instance, cat, true ) ;
						usc . postShow ( ShopGUI . instance ) ; 

						//return null ;
						
					}
			
				}
				
			}      
    			
    	}
    	
    	ShopGUI . instance . initGui ( ) ;

        return null ;
        
    }
    

}
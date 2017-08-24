package ru.alastar.minedonate.gui;

import ru.alastar.minedonate.MineDonate;
import ru.alastar.minedonate.rtnl.ModNetwork;
import ru.log_inil.mc.minedonate.gui.GuiTexturedButton;

/**
 * Created by Alastar on 19.07.2017.
 */
public class BuyButton extends GuiTexturedButton {
	
    int merch_id = -1;
    int shopId = 0 ;
    int catId ;
    
    public BuyButton( int _shopId, int _catId, int merch_id, int buttonId, int x, int y, String buttonText) {
    	
        super(buttonId, x, y, buttonText);
        
        this.merch_id = merch_id;
        shopId = _shopId ;
        catId = _catId ;
        
    }

    public BuyButton(int _shopId, int _catId, int merch_id, int buttonId, int x, int y, int widthIn, int heightIn, String buttonText) {
    	
        super(buttonId, x, y, widthIn, heightIn, buttonText);
        
        this.merch_id = merch_id;
        shopId = _shopId ;
        catId = _catId ;

    }

    public void buy ( ) {
    	
        if ( merch_id != -1 ) {
        	
        	ModNetwork . sendToServerBuyPacket ( shopId, merch_id, catId, MineDonate . shops . get ( shopId ) . cats [ catId ] . getMerch ( merch_id ) . getAmountToBuy ( ) ) ;
     
        }
        
    }


}
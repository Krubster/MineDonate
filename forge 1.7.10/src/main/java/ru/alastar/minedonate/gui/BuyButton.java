package ru.alastar.minedonate.gui;

import net.minecraft.client.gui.GuiButton;
import ru.alastar.minedonate.MineDonate;
import ru.alastar.minedonate.network.packets.BuyPacket;

/**
 * Created by Alastar on 19.07.2017.
 */
public class BuyButton extends GuiButton {
	
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

    public void buy( ) {
        if (merch_id != -1) {
            BuyPacket packet = new BuyPacket(shopId, merch_id, catId, MineDonate.shops.get(shopId).cats[catId].getMerch(merch_id).getAmountToBuy());
            MineDonate.networkChannel.sendToServer(packet);
        }
    }


}
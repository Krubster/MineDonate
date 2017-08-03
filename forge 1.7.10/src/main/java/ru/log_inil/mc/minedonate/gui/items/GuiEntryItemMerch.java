package ru.log_inil.mc.minedonate.gui.items;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.Tessellator;
import ru.alastar.minedonate.gui.ShopCategory;
import ru.alastar.minedonate.merch.info.ItemInfo;
import ru.log_inil.mc.minedonate.gui.GuiEntry;
import ru.log_inil.mc.minedonate.gui.GuiItems;
import ru.log_inil.mc.minedonate.gui.painters.ItemNBlockLineItemPainter;

public class GuiEntryItemMerch extends GuiEntry {

	public ItemInfo ii ;
	public ItemNBlockLineItemPainter inbip ; // #LOG

	public GuiEntryItemMerch ( ItemInfo _iim, ShopCategory sc ) {

		ii = _iim ;
   	 	inbip = new ItemNBlockLineItemPainter ( sc, ii ) ;

	}
	
	GuiButton pl, min, buy ;
	public void setButtons ( GuiButton _pl, GuiButton _min, GuiButton _buy ) {
	
		pl = _pl ;
		min = _min ;
		buy = _buy ;
		
	}
	
	@Override 
	public void draw ( GuiItems gi, int var2, int var3, int var4, Tessellator var5 ) {

		if ( pl != null ) {
		
			pl . yPosition = var3 + 2 ; 
			min . yPosition = var3 + 2 ;
			buy . yPosition = var3 + 2 ;

		}
		
        inbip . draw ( gi, var2, var3, 0, 0, 0f ) ;
		this . drawHorizontalLine ( gi . parent, var2 - 10, 40, var3 - 3, 999999999 ) ;

	}
	
	@Override
	public void undraw ( ) {
		
		if ( pl != null ) {
			
			pl . yPosition = -100 ;
			min . yPosition = -100 ;
			buy . yPosition = -100 ;

		}
		
	}
	
	
}

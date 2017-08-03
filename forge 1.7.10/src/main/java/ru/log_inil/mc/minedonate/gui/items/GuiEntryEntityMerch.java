package ru.log_inil.mc.minedonate.gui.items;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.Tessellator;
import ru.alastar.minedonate.gui.ShopCategory;
import ru.alastar.minedonate.merch.info.EntityInfo;
import ru.log_inil.mc.minedonate.gui.GuiEntry;
import ru.log_inil.mc.minedonate.gui.GuiItems;
import ru.log_inil.mc.minedonate.gui.painters.EntityLineItemPainter;

public class GuiEntryEntityMerch extends GuiEntry {

	public EntityInfo ei ;
	public EntityLineItemPainter elip ; // #LOG

	public GuiEntryEntityMerch ( EntityInfo _eim, ShopCategory sc ) {

		ei = _eim ;
		elip = new EntityLineItemPainter ( sc, ei ) ;

	}
	
	GuiButton buy ;
	public void setButtons ( GuiButton _buy ) {
	
		buy = _buy ;
		
	}
	
	@Override 
	public void draw ( GuiItems gi, int var2, int var3, int var4, Tessellator var5 ) {

		if ( buy != null ) {
			
			buy . yPosition = var3 + 2 ;

		}
		
		elip . draw ( gi, var2, var3, 0, 0, 0f ) ;
		this . drawHorizontalLine ( gi . parent, var2 - 10, 40, var3 - 3, 999999999 ) ;
		
	}
	
	@Override
	public void undraw ( ) {
		
		if ( buy != null ) {
			
			buy . yPosition = -100 ;

		}
		
	}
	
	
}

package ru.log_inil.mc.minedonate.gui.items;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import ru.alastar.minedonate.MineDonate;
import ru.alastar.minedonate.gui.GoButton;
import ru.alastar.minedonate.gui.ShopCategory;
import ru.alastar.minedonate.gui.ShopGUI;
import ru.alastar.minedonate.merch.info.ShopInfo;
import ru.log_inil.mc.minedonate.gui.GuiAbstractItemEntry;
import ru.log_inil.mc.minedonate.gui.GuiItemsScrollArea;

public class GuiItemEntryOfUserShopMerch extends GuiAbstractItemEntry {

	public ShopInfo info ;
	public ShopCategory sc ;
	
	GoButton go ;

	public GuiItemEntryOfUserShopMerch ( ShopInfo _usi, ShopCategory _sc ) {

		info = _usi ;
		sc = _sc ;

	}
		
	String shopTitle ;
	
	@Override
	public GuiAbstractItemEntry updateDrawData ( ) {
			
		shopTitle = info . owner + ( info . name != null && ! info . name . isEmpty ( ) ? " — " + info . name : "" ) ;
		
		return this ;
		
	}
	
	@Override 
	public void draw ( GuiItemsScrollArea gi, int x_offset, int y_offset, int var4, Tessellator var5, int index, int size ) {

		if ( go != null ) {
			
			go . yPosition = y_offset + 2 ;

		}
		
		GL11 . glEnable ( GL12 . GL_RESCALE_NORMAL ) ;
		RenderHelper . enableGUIStandardItemLighting ( ) ;

		//
		
		gi . parent . drawString ( gi . getFontRenderer ( ), shopTitle, 41, y_offset + 8, 16777215 ) ;
		
		//
		
		RenderHelper . disableStandardItemLighting ( ) ;
		GL11 . glDisable ( GL12 . GL_RESCALE_NORMAL ) ;
	    
		//

		this . drawHorizontalLine ( gi . parent, x_offset - 10, 40, y_offset - 3, 520093695 ) ;

  		if ( index + 1 == size && y_offset + 30 < gi . bottom ) {
  			
  			this . drawHorizontalLine ( gi . parent, x_offset - 10, 40, y_offset - 3 + 30, 520093695 ) ;

  		}
  		
	}
	
	@Override
	public void undraw ( ) {

		if ( go != null ) {
			
			go . yPosition = -100 ;

		}
		
	}
	
	@Override
	public GuiAbstractItemEntry addButtons ( ShopGUI gui ) {

		go = new GoButton ( info . shopId, ShopGUI . getNextButtonId ( ), gui . resolution . getScaledWidth ( ) - 16 - 30 - ( info . isFreezed ? MineDonate . cfgUI . cats . shops . lockedGoButton . width : MineDonate . cfgUI . cats . shops . itemBuyButton . width ), -100, info . isFreezed ? MineDonate . cfgUI . cats . shops . lockedGoButton . width : MineDonate . cfgUI . cats . shops . itemBuyButton . width, info . isFreezed ? MineDonate . cfgUI . cats . shops . lockedGoButton . height : MineDonate . cfgUI . cats . shops . itemBuyButton . height, info . isFreezed ? MineDonate . cfgUI . cats . shops . lockedGoButton . text : MineDonate . cfgUI . cats . shops . itemBuyButton . text ) ;
		go . enabled = info . isFreezed ? ! MineDonate . cfgUI . cats . shops . lockGoShopButtonWhenFreezed : true ;
		
        gui . addBtn ( go ) ;
                
		return this ;
		
	}
	
}
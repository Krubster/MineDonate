package ru.log_inil.mc.minedonate.gui.items;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import ru.alastar.minedonate.MineDonate;
import ru.alastar.minedonate.gui.GoButton;
import ru.alastar.minedonate.gui.ShopCategory;
import ru.alastar.minedonate.gui.ShopGUI;
import ru.alastar.minedonate.merch.info.ShopInfo;
import ru.log_inil.mc.minedonate.gui.DrawType;
import ru.log_inil.mc.minedonate.gui.GuiAbstractItemEntry;
import ru.log_inil.mc.minedonate.gui.GuiItemsScrollArea;
import ru.log_inil.mc.minedonate.gui.context.ContextElement;
import ru.log_inil.mc.minedonate.gui.context.ContextMenu;
import ru.log_inil.mc.minedonate.gui.context.ContextMenuManager;

public class GuiItemEntryOfUserShopMerch extends GuiAbstractItemEntry {

	public ShopInfo info ;
	public ShopCategory sc ;
	
	GoButton go ;

	public GuiItemEntryOfUserShopMerch ( ShopInfo _usi, ShopCategory _sc ) {

		info = _usi ;
		sc = _sc ;

		List < ContextElement > cElements = new ArrayList < > ( ) ;

		if ( MineDonate . getAccount ( ) . canFreezeShop ( info . owner ) ) {
			
			if ( info . isFreezed ) {
			
				cElements . add ( new ContextElement ( 1, "unfreeze", MineDonate.cfgUI.lang.unfreezeShop, this, 9 ) ) ;

			} else {
			
				cElements . add ( new ContextElement ( 0, "freeze", MineDonate.cfgUI.lang.freezeShop, this, 9 ) ) ;
			
			}
			
		}
		
		if ( MineDonate . getAccount ( ) . canRenameShop ( info . owner ) ) {
			
			cElements . add ( new ContextElement ( 2, "rename", MineDonate.cfgUI.lang.renameShop, this, 9 ) ) ;

		}
		
		if ( MineDonate . getAccount ( ) . canDeleteShop ( info . owner ) ) {
			
			cElements . add ( new ContextElement ( 3, "delete", MineDonate.cfgUI.lang.deleteShop, this, 9 ) ) ;

		}
				
		if ( ! cElements . isEmpty ( ) ) {
			
			cmm = new ContextMenu ( 1, 1, cElements ) ;
			
			ContextMenuManager . addNewMenu ( cmm ) ;
			
		}
			
	}
		
	String shopTitle ;
	boolean updateDataNeed = false ;
	List < String > freezText ;
	
	@Override
	public GuiAbstractItemEntry updateDrawData ( ) {
			
		shopTitle = info . owner + ( info . name != null && ! info . name . isEmpty ( ) ? " — " + info . name : "" ) ;
		
		if ( info . isFreezed && info . canVisibleFreezedText ) {
			
			freezText = new ArrayList < > ( ) ;
			
			freezText . add ( MineDonate.cfgUI.lang.shopFreezer + info . freezer ) ;
			freezText . add ( MineDonate.cfgUI.lang.shopFreezReason + info . freezReason ) ;

		}
		
		
		updateDataNeed = true ;
		
		return this ;
		
	}
	
	@Override 
	public void draw ( GuiItemsScrollArea gi, int x_offset, int y_offset, int xRightOffset, int mouseX, int mouseY, Tessellator var5,  DrawType dt, int index, int size ) {

		super.draw(gi, x_offset, y_offset, xRightOffset, mouseX, mouseY, var5, dt, index, size);

		if ( go != null ) {
			
			go . yPosition = y_offset + 2 ;

		}
		
		if ( updateDataNeed ) {
			
			this . updateSize ( xRightOffset - x_offset, 28 ) ;
			
			updateDataNeed = false ;
			
		}
	
		if ( dt == DrawType.PRE) {
		
			GL11 . glEnable ( GL12 . GL_RESCALE_NORMAL ) ;
			RenderHelper . enableGUIStandardItemLighting ( ) ;
		
			//
			
			gi . parent . drawString ( gi . getFontRenderer ( ), shopTitle, 41, y_offset + 8, 16777215 ) ;
			
			//
			
			RenderHelper . disableStandardItemLighting ( ) ;
			GL11 . glDisable ( GL12 . GL_RESCALE_NORMAL ) ;
		    
			//
		
			this . drawHorizontalLine ( gi . parent, 40, xRightOffset - 10, y_offset - 3, 520093695 ) ;
		
			if ( index + 1 == size && y_offset + 30 < gi . bottom ) {
				
				this . drawHorizontalLine ( gi . parent, 40, xRightOffset - 10, y_offset - 3 + 30, 520093695 ) ;
		
			}
			
		} else {
			
			if ( info . isFreezed && freezText != null ) {
				
				if ( mouseX >= go.xPosition && go.xPosition+go.width >= mouseX ) {
					
					if ( mouseY >= go.yPosition && go.yPosition + go.height >= mouseY ) {
						
						gi.parent.drawHoveringTextAccess(freezText, mouseX, mouseY, gi.getFontRenderer());
						
					}
					
				}
				
			}
			
		}
  		
	}
	
	@Override
	public void unDraw ( ) {

		super . unDraw ( ) ;
		if ( go != null ) {
			
			go . yPosition = -100 ;

		}
		
	}
	
	@Override
	public GuiAbstractItemEntry addButtons ( ShopGUI gui ) {

		go = new GoButton ( info . shopId, ShopGUI . getNextButtonId ( ), gui . resolution . getScaledWidth ( ) - 16 - 30 - ( info . isFreezed ? MineDonate . cfgUI . cats . shops . lockedGoButton . width : MineDonate . cfgUI . cats . shops . itemBuyButton . width ), -100, info . isFreezed ? MineDonate . cfgUI . cats . shops . lockedGoButton . width : MineDonate . cfgUI . cats . shops . itemBuyButton . width, info . isFreezed ? MineDonate . cfgUI . cats . shops . lockedGoButton . height : MineDonate . cfgUI . cats . shops . itemBuyButton . height, info . isFreezed ? MineDonate . cfgUI . cats . shops . lockedGoButton . text : MineDonate . cfgUI . cats . shops . itemBuyButton . text ) ;
		go . enabled = info . isFreezed ? ! MineDonate . cfgUI . cats . shops . lockGoShopButtonWhenFreezed : true ;
		
        gui . addBtn ( go, false ) ;
                
		return this ;
		
	}
	
	@Override
	public void onClickContextMenuElement(ShopGUI g, ContextMenu cmm, ContextElement e) {
		System.err.println( ">" + e.name);
	}
	
}
package ru.log_inil.mc.minedonate.gui.items;

import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import ru.alastar.minedonate.MineDonate;
import ru.alastar.minedonate.gui.BuyButton;
import ru.alastar.minedonate.gui.CountButton;
import ru.alastar.minedonate.gui.ShopCategory;
import ru.alastar.minedonate.gui.ShopGUI;
import ru.alastar.minedonate.merch.info.ItemInfo;
import ru.log_inil.mc.minedonate.gui.DrawType;
import ru.log_inil.mc.minedonate.gui.GuiAbstractItemEntry;
import ru.log_inil.mc.minedonate.gui.GuiItemsScrollArea;
import ru.log_inil.mc.minedonate.gui.context.ContextElement;
import ru.log_inil.mc.minedonate.gui.context.ContextMenu;
import ru.log_inil.mc.minedonate.gui.context.ContextMenuManager;
import ru.log_inil.mc.minedonate.gui.frames.GuiFrameDeleteItem;
import ru.log_inil.mc.minedonate.gui.frames.GuiFrameEditItem;

import java.util.ArrayList;
import java.util.List;

public class GuiItemEntryOfItemMerch extends GuiAbstractItemEntry {

	public ItemInfo info ;
	public ShopCategory sc ;
	
	CountButton pl, min;
	BuyButton buy ;

	public GuiItemEntryOfItemMerch ( ItemInfo _iim, ShopCategory _sc ) {

		info = _iim ;
		sc = _sc ;
		
		String owner = MineDonate . shops . get ( info . shopId ) . owner ;
		if ( MineDonate . getAccount ( ) . canEditShop ( owner ) ) {
			
			List < ContextElement > cElements = new ArrayList < > ( ) ;
	
			cElements . add ( new ContextElement ( 0, "edit",  MineDonate.cfgUI.lang.editItemMerch, this, 10 ) ) ;
			cElements . add ( new ContextElement ( 1, "delete", MineDonate.cfgUI.lang.deleteItemMerch, this, 10 ) ) ;
	
			cmm = new ContextMenu ( 1, 1, cElements ) ;
			
			ContextMenuManager . addNewMenu ( cmm ) ;

		}
		
	}
	
	String limitLine, stackCountLine ;
	boolean updateDataNeed = false ;
	
	@Override
	public GuiAbstractItemEntry updateDrawData ( ) {
		
		limitLine = MineDonate . cfgUI . cats . itemsAndBlocks . itemLeft + info . limit ;
		stackCountLine = Integer . toString ( info . modified * info.stack_data.getInteger("Count") ) ;
		
		updateDataNeed = true ;
		
		return this ;
		
	}
	
	@Override 
	public void draw ( GuiItemsScrollArea gi, int x_offset, int y_offset, int xRightOffset, float partialTicks, int mouseX, int mouseY, Tessellator var5, DrawType dt, int index, int size ) {
	
		super.draw(gi, x_offset, y_offset, xRightOffset, partialTicks, mouseX, mouseY, var5, dt, index, size);

		if ( pl != null ) {
			
			pl . yPosition = y_offset + 2 ; 
			min . yPosition = y_offset + 2 ;
			buy . yPosition = y_offset + 2 ;

		}
		
		if ( updateDataNeed ) {
			
			this . updateSize ( xRightOffset - x_offset, 28 ) ;

			updateDataNeed = false ;
			
		}
		
	//	GL11 . glDisable ( GL11.GL_LIGHTING ) ;

		if ( dt == DrawType . PRE ) {
			
			GL11 . glEnable ( GL12 . GL_RESCALE_NORMAL ) ;
			RenderHelper . enableGUIStandardItemLighting ( ) ;
			
			//

			gi . parent . drawString ( gi . getFontRenderer ( ), info . name, 67, y_offset + 8, 16777215 ) ;
			gi . parent . moneyArea . drawPriceArea ( xRightOffset - 70 - 50 - 20, y_offset + 8, ( info . cost * info . modified ), info . getMoneyType ( ) ) ;

			if ( info . limit != -1) {
			  
				gi . parent . drawCenteredString ( gi . getFontRenderer ( ), limitLine, xRightOffset - 80 - 50 - 50 - 20, y_offset + 8, 16777215 ) ; // - costLineWidth

			}
			
			//
			
			GL11 . glPushMatrix ( ) ; 
			
			GL11 . glTranslatef ( 40, y_offset + 2, 0 ) ;
			GL11 . glScalef ( 1.2f, 1.2f, 1f ) ;  
			
			gi . parent . getItemRender ( ) . renderItemAndEffectIntoGUI ( gi . getFontRenderer ( ), gi . parent . mc . getTextureManager ( ), info . m_stack, 0, 0 ) ;

			GL11 . glPopMatrix ( ) ;
			
			//
			
			GL11 . glDisable ( GL12 . GL_RESCALE_NORMAL ) ;

			//
			this . drawHorizontalLine ( gi . parent, 40, xRightOffset - 10, y_offset - 3, 520093695 ) ;

	  		if ( index + 1 == size && y_offset + 30 < gi . bottom ) {
	  			
	  			this . drawHorizontalLine ( gi . parent, 40, xRightOffset - 10, y_offset - 3 + 30, 520093695 ) ;

	  		}
	  		
	  		
			gi . parent . getItemRender ( ) . renderItemOverlayIntoGUI ( gi . getFontRenderer ( ), gi . parent . mc . getTextureManager ( ), info . m_stack, 45, y_offset + 5, stackCountLine ) ;

			RenderHelper . disableStandardItemLighting ( ) ;
			
		} else if ( dt == DrawType . OVERLAY_PRE ) {
			
			if ( mouseX >= 40 && 60 >= mouseX ) {
				
				if ( mouseY >= y_offset - 2 && y_offset + 20 >= mouseY ) {
					
					gi.parent.renderToolTipAccess(info.m_stack, mouseX, mouseY);
					RenderHelper . disableStandardItemLighting ( ) ;

				}
				
			}
			
		}

	}
	
	@Override
	public void unDraw ( ) {

		super . unDraw ( ) ;

		if ( pl != null ) {

			pl . yPosition = -100 ;
			min . yPosition = -100 ;
			buy . yPosition = -100 ;

		}
		
	}
	
	@Override
	public GuiAbstractItemEntry addButtons ( ShopGUI gui ) {
				
		buy = new BuyButton ( info . getShopId ( ), info . getCategory ( ), info . getId ( ), ShopGUI . getNextButtonId ( ), gui . resolution . getScaledWidth ( ) - 91, -100, MineDonate . cfgUI . cats.itemsAndBlocks . itemBuyButton . width, MineDonate . cfgUI . cats . itemsAndBlocks . itemBuyButton . height, MineDonate . cfgUI . cats . itemsAndBlocks . itemBuyButton . text ) ;
        gui . addButton ( buy, false ) ;
        
        min = new CountButton ( -1, info, ShopGUI . getNextButtonId ( ), buy . xPosition - 1 - 16, - 100, 16, 20, "-" ) ;
        min . setEntityOnUpdate ( this ) ;
        
        gui . addButton ( min, false ) ;
        
        pl = new CountButton ( 1, info, ShopGUI . getNextButtonId ( ), min . xPosition - 1 - 16, -100, 16, 20, "+" ) ;
        pl . setEntityOnUpdate ( this ) ;
        
        gui . addButton ( pl, false ) ;

    	if ( ! pl . canModify ( ) && ! min . canModify ( ) ) {
        	
    		pl . enabled = false ;
    		min . enabled = false ;	

        }
        
        if ( info . limit < 1 && info . limit != -1 ) {
        	
        	buy . enabled = false ;
            pl . enabled = false ;
            min . enabled = false ;
            
        }
		        
		return this ;
		
	}

	@Override 
	public void postShow ( GuiItemsScrollArea gui ) { 
		
		super . postShow ( gui ) ;
		
		updateContextMenu ( ) ;
		
	}
	
	@Override 
	public void updateContextMenu ( ) {
		
		super . updateContextMenu ( ) ;
		
	}
	
	@Override
	public void onClickContextMenuElement ( ShopGUI g, ContextMenu cmm, ContextElement e ) {
		
		if ( e != null && info != null ) {
			
			switch ( e . name ) {
				
				case "edit" :
					
					GuiFrameEditItem gfrs = ( GuiFrameEditItem ) g . showEntry ( "frame.item.edit", true ) ;	

					gfrs . setInfo ( info . shopId, info . catId, info . getId ( ), info . limit, info . cost ) ;
					gfrs . setFieldData ( info . m_stack . getDisplayName ( ) . equals ( info . name ) ? "" :  info . name, info . m_stack . getDisplayName ( ) ) ;
					gfrs . postShow ( g ) ;

				break ;
				
				case "delete" :
					
					GuiFrameDeleteItem gfdi = ( GuiFrameDeleteItem ) g . showEntry ( "frame.item.delete", true ) ;	
					
					gfdi . setInfo ( info . shopId, info . catId, info . getId ( ) ) ;
					gfdi . setConfirmCode ( Integer . toString ( Math . abs ( info . hashCode ( ) ) ) . substring ( 0, 3 ) ) ;
					gfdi . postShow ( g ) ;
					
				break ;
				
			}
			
		}
		
	}
	
}

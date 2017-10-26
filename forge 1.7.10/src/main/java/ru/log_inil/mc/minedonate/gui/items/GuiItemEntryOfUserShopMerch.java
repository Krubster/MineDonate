package ru.log_inil.mc.minedonate.gui.items;

import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import ru.alastar.minedonate.MineDonate;
import ru.alastar.minedonate.gui.GoButton;
import ru.alastar.minedonate.gui.ShopCategory;
import ru.alastar.minedonate.gui.ShopGUI;
import ru.alastar.minedonate.merch.info.ShopInfo;
import ru.alastar.minedonate.rtnl.ModNetworkRegistry;
import ru.log_inil.mc.minedonate.gui.DrawType;
import ru.log_inil.mc.minedonate.gui.GuiAbstractItemEntry;
import ru.log_inil.mc.minedonate.gui.GuiItemsScrollArea;
import ru.log_inil.mc.minedonate.gui.context.ContextElement;
import ru.log_inil.mc.minedonate.gui.context.ContextMenu;
import ru.log_inil.mc.minedonate.gui.context.ContextMenuManager;
import ru.log_inil.mc.minedonate.gui.frames.GuiFrameDeleteShop;
import ru.log_inil.mc.minedonate.gui.frames.GuiFrameFreezeAccount;
import ru.log_inil.mc.minedonate.gui.frames.GuiFrameFreezeShop;
import ru.log_inil.mc.minedonate.gui.frames.GuiFrameRenameShop;

import java.util.ArrayList;
import java.util.List;

public class GuiItemEntryOfUserShopMerch extends GuiAbstractItemEntry {

	public ShopInfo info ;
	public ShopCategory sc ;
	
	GoButton go ;

	public GuiItemEntryOfUserShopMerch ( ShopInfo _usi, ShopCategory _sc ) {

		info = _usi ;
		sc = _sc ;
			
		List < ContextElement > cElements = getContextElements ( ) ;
				
		if ( ! cElements . isEmpty ( ) ) {
			
			cmm = new ContextMenu ( 1, 1, cElements ) ;
						
		}
		
	}
		
	public List<ContextElement> getContextElements() {
		
		List < ContextElement > cElements = new ArrayList < > ( ) ;
			
		if ( info . isFreezed && MineDonate . getAccount ( ) . canUnFreezeShop ( info . owner ) ) {
		
			cElements . add ( new ContextElement ( 1, "unfreeze", MineDonate.cfgUI.lang.unfreezeShop, this, 10 ) ) ;

		} else if ( ! info . isFreezed && MineDonate . getAccount ( ) . canFreezeShop ( info . owner ) ) {
		
			cElements . add ( new ContextElement ( 0, "freeze", MineDonate.cfgUI.lang.freezeShop, this, 10 ) ) ;
		
		}
	
		if ( MineDonate . getAccount ( ) . canRenameShop ( info . owner ) ) {
			
			cElements . add ( new ContextElement ( 2, "rename", MineDonate.cfgUI.lang.renameShop, this, 10 ) ) ;

		}
		
		if ( MineDonate . getAccount ( ) . canDeleteShop ( info . owner ) ) {
			
			cElements . add ( new ContextElement ( 3, "delete", MineDonate.cfgUI.lang.deleteShop, this, 10 ) ) ;

		}
		
		if ( MineDonate . getAccount ( ) . canFreezeOtherAccount ( ) ) {
			
			cElements . add ( new ContextElement ( 4, "freezeAcc", MineDonate.cfgUI.lang.freezeAccount, this, 10 ) ) ;

		}
		
		if ( MineDonate . getAccount ( ) . canUnFreezeOtherAccount ( ) ) {
			
			cElements . add ( new ContextElement ( 4, "unFreezeAcc", MineDonate.cfgUI.lang.unfreezeAccount, this, 10 ) ) ;

		}
		
		return cElements ;
		
	}

	String shopTitle ;
	boolean updateDataNeed = false ;
	List < String > freezText ;
	
	@Override
	public GuiAbstractItemEntry updateDrawData ( ) {
			
		shopTitle = info . ownerName + ( info . name != null && ! info . name . isEmpty ( ) ? " — " + info . name : "" ) ;
		
		if ( info . isFreezed && info . canVisibleFreezedText && MineDonate . acc . canViewOtherFreezText ( ) ) {
			
			freezText = new ArrayList < > ( ) ;
			
			freezText . add ( MineDonate.cfgUI.lang.shopFreezer + info . freezer ) ;
			freezText . add ( MineDonate.cfgUI.lang.shopFreezReason + info . freezReason ) ;

		}
				
		if ( cmm != null ) {
			ShopGUI.instance.getContextMenuManager().addNewMenu(cmm);
		}
		
		updateDataNeed = true ;
		
		return this ;
		
	}
	
	@Override 
	public void draw ( GuiItemsScrollArea gi, int x_offset, int y_offset, int xRightOffset, float partialTicks, int mouseX, int mouseY, Tessellator var5,  DrawType dt, int index, int size ) {

		super.draw(gi, x_offset, y_offset, xRightOffset, partialTicks, mouseX, mouseY, var5, dt, index, size);

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
			
		} else if ( dt == DrawType . OVERLAY_PRE ) {
			
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

		go = new GoButton ( info . shopId, ShopGUI . getNextButtonId ( ), gui . resolution . getScaledWidth ( ) - 17 - 30 - ( info . isFreezed ? MineDonate . cfgUI . cats . shops . lockedGoButton . width : MineDonate . cfgUI . cats . shops . itemBuyButton . width ), -100, info . isFreezed ? MineDonate . cfgUI . cats . shops . lockedGoButton . width : MineDonate . cfgUI . cats . shops . itemBuyButton . width, info . isFreezed ? MineDonate . cfgUI . cats . shops . lockedGoButton . height : MineDonate . cfgUI . cats . shops . itemBuyButton . height, info . isFreezed ? MineDonate . cfgUI . cats . shops . lockedGoButton . text : MineDonate . cfgUI . cats . shops . itemBuyButton . text ) ;
		go . enabled = info . isFreezed ? ! MineDonate . cfgUI . cats . shops . lockGoShopButtonWhenFreezed : true ;
		
        gui . addButton ( go, false ) ;
                
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
	public void onClickContextMenuElement(ShopGUI g, ContextMenu cmm, ContextElement e) {
		
		if ( e != null && info != null ) {
		
			switch ( e . name ) {
				
				case "freeze" :
					
					GuiFrameFreezeShop gffs = ( GuiFrameFreezeShop ) g . showEntry ( "frame.shop.freeze", true ) ;	
					
					gffs . setShopId ( info . shopId ) ;

					if ( GuiFrameFreezeShop . lastReason != null ) {
						
						gffs . setFieldData ( GuiFrameFreezeShop . lastReason, gffs . fieldHolder ) ;
						
					}
					
					gffs . postShow ( g ) ;

				break ;
					
				case "unfreeze" :
					
		    		ModNetworkRegistry . sendToServerUnfreezeShopPacket ( info . shopId ) ;

		    		g . setLoading ( true ) ;

				break ;
				
				case "rename" :
					
					GuiFrameRenameShop gfrs = ( GuiFrameRenameShop ) g . showEntry ( "frame.shop.rename", true ) ;	
					
					gfrs . setShopId ( info . shopId ) ;
					gfrs . setFieldData ( info . name, gfrs . fieldHolder ) ;
					gfrs . postShow ( g ) ;

				break ;
				
				case "delete" :
					
					GuiFrameDeleteShop gfds = ( GuiFrameDeleteShop ) g . showEntry ( "frame.shop.delete", true ) ;	
					
					gfds . setShopId ( info . shopId ) ;
					gfds . setConfirmCode ( Integer . toString ( Math . abs ( info . hashCode ( ) ) ) . substring ( 0, 3 ) ) ;
					gfds . postShow ( g ) ;

				break ;
				
				case "freezeAcc" :
					
					GuiFrameFreezeAccount gffa = ( GuiFrameFreezeAccount ) g . showEntry ( "frame.acc.freeze", true ) ;	
					
					gffa . setAccountName ( info . owner ) ;
					gffa . postShow ( g ) ;

				break ;
				
				case "unFreezeAcc" :
					
		    		ModNetworkRegistry . sendToServerUnfreezeAccountPacket ( info . owner ) ;

		    		g . setLoading ( true ) ;
		    		
				break ;
				
				
			}
			
		}
		
	}
	
}
package ru.log_inil.mc.minedonate.gui.frames;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.RenderHelper;
import ru.alastar.minedonate.MineDonate;
import ru.alastar.minedonate.gui.ShopGUI;
import ru.alastar.minedonate.rtnl.Manager;
import ru.alastar.minedonate.rtnl.ModNetwork;
import ru.alastar.minedonate.rtnl.Utils;
import ru.log_inil.mc.minedonate.gui.DrawType;
import ru.log_inil.mc.minedonate.gui.GuiFrame;
import ru.log_inil.mc.minedonate.gui.GuiGradientButton;
import ru.log_inil.mc.minedonate.gui.GuiGradientTextField;
import ru.log_inil.mc.minedonate.localData.frames.DataOfUIFrameAddItem;

import java.awt.*;

public class GuiFrameAddItem extends GuiFrame {

	int width = 220 ;
	int height = 40 ;
	
	int posX ;
	int posY ;
	
	static int backgroundColor = Utils . rgbaToInt ( new Color ( 0, 0, 0, 150 ) ) ;
	static int fieldBorderRedColor = Utils . rgbaToInt ( new Color ( 255, 0, 0, 150 ) ) ;
	static int fieldBorderColor = Utils . rgbaToInt ( new Color ( 255, 255, 255, 150 ) ) ;
	static int titleColor = Utils . rgbaToInt ( new Color ( 255, 255, 255, 180 ) ) ;
	
	int widthCenter = width / 2 ;
	int heightCenter = height / 2 ;
	
	DataOfUIFrameAddItem douifcs ;
	
	int shopId = -1, catId = -1 ;
	public boolean drawItemStack = true ;
	
	public GuiFrameAddItem ( String _name, DataOfUIFrameAddItem _douifcs ) {
		
		super ( _name ) ;

		douifcs = _douifcs ;
		
		fieldText = douifcs . nameField . text ;
		fieldHolder = douifcs . nameField . textHolder ;
		
	}
	
    public void draw ( ShopGUI g, int page, int mouseX, int mouseY, float partialTicks, DrawType dt ) {

		g . drawRect ( posX, posY, posX + width, posY + height, backgroundColor ) ;
    	
    	super . draw ( g, page, mouseX, mouseY, partialTicks, dt ) ;
    	
    	g . drawString ( g . getFontRenderer ( ), douifcs . title, posX + 5, posY + 3, titleColor ) ;
    	
    	nameField . drawTextBox ( ) ;
    	costField . drawTextBox ( ) ;
    	limitField . drawTextBox ( ) ;
    	
    	if ( drawItemStack && MineDonate . getAccount ( ) . ms . currentItemStack != null ) {
    		
			RenderHelper . enableGUIStandardItemLighting ( ) ;
			
    		g . getItemRender ( ) . renderItemAndEffectIntoGUI ( g . getFontRenderer ( ), g . mc . getTextureManager ( ), MineDonate.getAccount().ms.currentItemStack, posX + 8, posY + 16 ) ;
			g . getItemRender ( ) . renderItemOverlayIntoGUI ( g . getFontRenderer ( ), g . mc . getTextureManager ( ), MineDonate.getAccount().ms.currentItemStack, posX + 8, posY + 16 , Integer.toString(MineDonate.getAccount().ms.currentItemStack.stackSize) ) ;

			RenderHelper . disableStandardItemLighting ( ) ;

    	}
        	
    }
    
    GuiButton saveChangesButton, uppendChangesButton ;
    GuiButton cancelChangesButton ;
    GuiGradientTextField nameField, costField, limitField ; 
    
    String fieldText, fieldHolder ;
    boolean canUppend, unShowDropFix ;
    
    @Override
	public void postShow ( ShopGUI g ) {
    	
		if ( ! isVisible ( ) ) {
			
			return ;
			
		}
		
    	super . postShow ( g ) ;
    	
    	if ( canUppend ) {

    		canUppend = Manager . canUppendAnotherItemInShop ( MineDonate . getAccount ( ), MineDonate . shops . get ( g . getCurrentShopId ( ) ), catId ) != -1 ;
    		
    	}
    	
    	unShowDropFix = true ;
    	
    	width = 5 ;

    	if ( drawItemStack ) {
    		
    		width += 20 ;
    		
    	}
    	
		width += douifcs . nameField . width ;
		
		if ( MineDonate . getAccount ( ) . canUnlimitedItems ( ) && douifcs . limitField != null ) {
			
			width += douifcs . limitField . width ;
			width += 6 ;
			
		}
		
		width += douifcs . costField . width ;
		width += 19 ;

		widthCenter = width / 2 ;

    	posX = (g.getScaledResolution().getScaledWidth()/2) - widthCenter;
    	posY = (g.getScaledResolution().getScaledHeight()/2) - heightCenter;

    	if ( uppendChangesButton == null && douifcs . okExistsButton != null ) {
        	
    		uppendChangesButton = new GuiGradientButton ( ShopGUI . getNextButtonId ( ), posX, posY + height, 
    				douifcs.okExistsButton.width, douifcs.okExistsButton.height, douifcs.okExistsButton.text, false ) ;
    	
    	}
    	
    	if ( uppendChangesButton != null ) {
    	
    		uppendChangesButton . visible = uppendChangesButton . enabled = canUppend ;
    	
    	}

    	if ( saveChangesButton == null ) {
        	
    		saveChangesButton = new GuiGradientButton ( ShopGUI . getNextButtonId ( ), posX, posY + height, 
    				douifcs.okButton.width, douifcs.okButton.height, douifcs.okButton.text, false ) ;
    	
    	}
    	
    	if ( cancelChangesButton == null ) {
        	
    		cancelChangesButton = new GuiGradientButton ( ShopGUI . getNextButtonId ( ), posX, posY + height, 
    				douifcs.cancelButton.width, douifcs.cancelButton.height, douifcs.cancelButton.text, false ) ;
    	
    	}
		
		if ( nameField == null ) {
						
			nameField = new GuiGradientTextField ( g.getFontRenderer(), 30, 10, douifcs . nameField . width - 1, douifcs . nameField . height, true ) ;
			nameField . setMaxStringLength ( 140 ) ;
			nameField . setEnableBorderDrawing ( true, fieldBorderColor ) ;
			
		}
		
		nameField . setText ( fieldText != null ? fieldText : "" ) ;
		nameField . setTextHolder ( MineDonate.getAccount().ms.currentItemStack != null ? MineDonate.getAccount().ms.currentItemStack.getDisplayName() : fieldHolder ) ;
		
		nameField . fieldBorderColor = fieldBorderColor ;

		nameField . xPosition = posX + 10 + ( drawItemStack ? 22 : 0 ) ;
		nameField . yPosition = posY + 15 ;

		if ( limitField == null && douifcs . limitField != null ) {
			
			limitField = new GuiGradientTextField ( g.getFontRenderer(), 30, 10, douifcs . limitField . width - 1, douifcs . limitField . height, true ) ;
			limitField . setMaxStringLength ( 10 ) ;
			limitField . setEnableBorderDrawing ( true, fieldBorderColor ) ;
			
		}

		if ( limitField != null ) {
			
			limitField . setText ( "" ) ;
			limitField . setTextHolder ( "" ) ;
    		limitField . fieldBorderColor = fieldBorderColor ;

		}
		
		if ( costField == null ) {
			
			costField = new GuiGradientTextField ( g.getFontRenderer(), 30, 10, douifcs . costField . width - 1, douifcs . costField . height, true ) ;
			costField . setMaxStringLength ( 10 ) ;
			costField . setEnableBorderDrawing ( true, fieldBorderColor ) ;
						
		}
	
		costField . setText ( douifcs . costField . text ) ;
		costField . setTextHolder ( douifcs . costField . textHolder ) ;
		
		costField . fieldBorderColor = fieldBorderColor ;

		costField . xPosition = nameField . xPosition + nameField . width + 6 ;
		costField . yPosition = nameField . yPosition ;

		if ( limitField != null ) {

			if ( MineDonate . getAccount ( ) . canUnlimitedItems ( ) ) {

				limitField . xPosition = costField . xPosition + costField . width + 6 ;
				limitField . yPosition = nameField . yPosition ;
				
				limitField . setText ( douifcs . limitField . text ) ;
				limitField . setTextHolder ( douifcs . limitField . textHolder ) ;
				
			}
		
			limitField . setEnabled ( MineDonate . getAccount ( ) . canUnlimitedItems ( ) ) ;
			limitField . setVisible ( MineDonate . getAccount ( ) . canUnlimitedItems ( ) ) ;
			
		}

    	cancelChangesButton . yPosition = saveChangesButton . yPosition = posY + height ;
    	cancelChangesButton . xPosition = posX + width - cancelChangesButton . width ;
    	saveChangesButton . xPosition = cancelChangesButton . xPosition - saveChangesButton . width ;
    	
    	if ( uppendChangesButton != null ) {

    		uppendChangesButton . xPosition = saveChangesButton . xPosition - uppendChangesButton . width ;
    	
    	}
    	
    	if ( this . isVisible ( ) ) {

    		if ( canUppend && uppendChangesButton != null ) {
        		
    			g . addButton ( uppendChangesButton, false ) ;
    		
    		}
    		
    		g . addButton ( saveChangesButton, false ) ;
			g . addButton ( cancelChangesButton, false ) ;

    	}

    }
    
    @Override
	public void unShow ( ShopGUI g ) {
		
    	super . unShow ( g ) ;
    	
    	g . removeButton ( uppendChangesButton ) ;
    	g . removeButton ( saveChangesButton ) ;
    	g . removeButton ( cancelChangesButton ) ;

		if ( unShowDropFix && MineDonate . getAccount ( ) . ms . currentItemStack != null ) {
			new Exception().printStackTrace();
			ModNetwork . sendToServerCancelShopInventoryPacket ( ) ;
			
		}
		
	}
	
    @Override
	public boolean actionPerformed ( ShopGUI g, GuiButton b ) {
		
    	if ( b . id == saveChangesButton . id ) {

    		int cost = 1, limit = 1 ;
    		if ( costField . getText ( ) . trim ( ) . isEmpty ( ) ) {
    			
    			costField . fieldBorderColor = fieldBorderRedColor ;
    			
    			return false ;
    			
    		} else {
    			
    			try {
    				
    				cost = Integer . parseInt ( costField . getText ( ) ) ;
    				
    				if ( cost < 1 ) {
    					
    	    			costField . fieldBorderColor = fieldBorderRedColor ;
    	    			
    	    			return false ;
    	    			
    				}
    				
    			} catch ( Exception ex ) {
    				
    				ex . printStackTrace ( ) ;
        			
    				costField . fieldBorderColor = fieldBorderRedColor ;
        			
        			return false ;
        			
    			}
    			
    		}
    		
    		if ( MineDonate . getAccount ( ) . canUnlimitedItems ( ) ) {
    			
    			try {

    				limit = Integer . parseInt ( this . limitField . getText ( ) ) ;

    			} catch ( Exception ex ) {
    				
    				ex . printStackTrace ( ) ;
        			
    				limitField . fieldBorderColor = fieldBorderRedColor ;
        			
        			return false ;
        			
    			}
    			
    		} else {
    			
    			limit = MineDonate . getAccount ( ) . ms . currentItemStack . stackSize ;
    			
    		}
    		
    		g . setLoading ( true ) ;
    		
            ModNetwork . sendToServerAddNewEntryPacket ( shopId, catId, limit, cost, this . nameField . getText ( ) ) ;
            
    		unShowDropFix = false ;

            this . hideFrame ( g ) ;
           
    	}
    	
    	if ( uppendChangesButton != null && b . id == uppendChangesButton . id ) {
        	        	
    		if ( MineDonate . getAccount ( ) . ms . currentItemStack != null ) {
    			
    			ModNetwork . sendToServerUppendEntryPacket ( shopId, catId ) ;
    			
    		}
    		
    		unShowDropFix = false ;

            this . hideFrame ( g ) ;
    		
    	}
    	
    	if ( b . id == cancelChangesButton . id ) {

    		if ( MineDonate . getAccount ( ) . ms . currentItemStack != null ) {
    			
    			ModNetwork . sendToServerCancelShopInventoryPacket ( ) ;
    			
    		}
    		
    		unShowDropFix = false ;

            this . hideFrame ( g ) ;
    		
    	}
    	
		return false ;
		
	}
	
    @Override
	public boolean onClick ( int x, int y, int i ) {
		
    	if ( nameField != null ) {

    		nameField . mouseClicked ( x, y, i ) ;
    		    		
			nameField . fieldBorderColor = fieldBorderColor ;

    	}
    	
    	if ( limitField != null ) {

    		limitField . mouseClicked ( x, y, i ) ;
    		    		
    		limitField . fieldBorderColor = fieldBorderColor ;

    	}
    	
    	if ( costField != null ) {

    		costField . mouseClicked ( x, y, i ) ;
    		    		
    		costField . fieldBorderColor = fieldBorderColor ;

    	}
    	
		return false ;
		
	}

    @Override
	public boolean onKey ( char c, int k ) {
		
    	if ( nameField != null && nameField . isFocused ( ) ) {
    		
    		nameField . textboxKeyTyped ( c, k ) ;
    		
			nameField . fieldBorderColor = fieldBorderColor ;

    		return true ;
    		
    	}
    	
    	if ( limitField != null && limitField . isFocused ( ) ) {
    		
    		limitField . textboxKeyTyped ( c, k ) ;
    		
    		limitField . fieldBorderColor = fieldBorderColor ;

    		return true ;
    		
    	}
    	
    	if ( costField != null && costField . isFocused ( ) ) {
    		
    		costField . textboxKeyTyped ( c, k ) ;
    		
    		costField . fieldBorderColor = fieldBorderColor ;

    		return true ;
    		
    	}
    	
		return false ;
		
	}
	
	public boolean isOwnerButton ( GuiButton gb ) {
		
		return ( ( uppendChangesButton != null && gb == uppendChangesButton ) || gb == saveChangesButton || gb == cancelChangesButton ) ;
		
	}
	
	
    @Override
	public boolean needUnShowWhenGuiClose ( ) {
		
		return ! unShowDropFix ;
		
	}
    
    @Override
	public boolean coordContains ( int x, int y ) {
		
		return ( posX <= x && x <= posX + width ) && ( posY <= y && posY + height >= y ) ;
		
	}

    @Override
	public boolean lockContextMenuUnderEntry ( ) {
		
		return isVisible ( ) ;
		
	}
	
    @Override
	public boolean lockButtonsUnderEntry ( ) {
		
		return isVisible ( ) ;
		
	}
	
    public void setFieldData ( String _text, String _holder ) {
    	
    	fieldText = _text ;
    	fieldHolder = _holder ;

    }

	public void setInfo ( int _shopId, int _catId, boolean _canUppend ) {

		shopId = _shopId ;
		catId = _catId ;
		canUppend = _canUppend ;
		
	}
    
    
}

package ru.log_inil.mc.minedonate.gui.frames;

import java.awt.Color;

import net.minecraft.client.gui.GuiButton;
import ru.alastar.minedonate.MineDonate;
import ru.alastar.minedonate.gui.ShopGUI;
import ru.alastar.minedonate.network.manage.packets.EditMerchNumberPacket;
import ru.alastar.minedonate.network.manage.packets.EditMerchStringPacket;
import ru.alastar.minedonate.rtnl.ModNetwork;
import ru.alastar.minedonate.rtnl.Utils;

import ru.log_inil.mc.minedonate.gui.DrawType;
import ru.log_inil.mc.minedonate.gui.GuiFrame;
import ru.log_inil.mc.minedonate.gui.GuiGradientButton;
import ru.log_inil.mc.minedonate.gui.GuiGradientTextField;
import ru.log_inil.mc.minedonate.localData.frames.DataOfUIFramEditItem;

public class GuiFrameEditItem extends GuiFrame {

	int width = 200 ;
	int height = 40 ;
	
	int posX ;
	int posY ;
	
	static int titleColor = Utils . rgbaToInt ( new Color ( 255, 255, 255, 180 ) ) ;
	static int backgroundColor = Utils . rgbaToInt ( new Color ( 0, 0, 0, 150 ) ) ;
	static int fieldBorderColor = Utils . rgbaToInt ( new Color ( 255, 255, 255, 150 ) ) ;
	static int fieldBorderRedColor = Utils . rgbaToInt ( new Color ( 255, 0, 0, 150 ) ) ;
	
	int widthCenter = width / 2 ;
	int heightCenter = height / 2 ;
	
	public DataOfUIFramEditItem douifcs ;

	int shopId = -1, catId = -1, merch_id = -1, limit = -1, cost = -1 ;
	
	public GuiFrameEditItem ( String _name, DataOfUIFramEditItem _douifcs ) {
		
		super ( _name ) ;

		douifcs = _douifcs ;
		
		fieldText = douifcs . nameField . text ;
		fieldHolder = douifcs . nameField . textHolder ;
		
	}
	
    public void draw(ShopGUI g, int page, int mouseX, int mouseY, float partialTicks, DrawType dt ) {

    	g . drawRect ( posX, posY, posX + width, posY + height, backgroundColor ) ;
    	
    	super . draw ( g, page, mouseX, mouseY, partialTicks, dt ) ;
    	
    	g . drawString ( g . getFontRenderer ( ), douifcs . title, posX + 5, posY + 3, titleColor ) ;
    	
    	nameField . drawTextBox ( ) ;
    	costField . drawTextBox ( ) ;
    	
    	if ( limitField != null ) {
    	
    		limitField . drawTextBox ( ) ;    	
    	
    	}
    	
    }
    
    public GuiButton saveChangesButton ;
    public GuiButton cancelChangesButton ;
    public GuiGradientTextField nameField, costField, limitField ; 
    
    public String fieldText, fieldHolder ;
    
    @Override
	public void postShow ( ShopGUI g ) {
		
    	super . postShow ( g ) ;

    	posX = (g.getScaledResolution().getScaledWidth()/2) - widthCenter;
    	posY = (g.getScaledResolution().getScaledHeight()/2) - heightCenter;

    	if ( saveChangesButton == null ) {
        	
    		saveChangesButton = new GuiGradientButton ( ShopGUI . getNextButtonId ( ), posX, posY + height, 
    				douifcs.editButton.width, douifcs.editButton.height, douifcs.editButton.text, false ) ;
    	
    	}
    	
    	if ( this . isVisible ( ) ) {

    		g . addButton ( saveChangesButton, false ) ;
    		
    	}
    	
    	if ( cancelChangesButton == null ) {
        	
    		cancelChangesButton = new GuiGradientButton ( ShopGUI . getNextButtonId ( ), posX, posY + height, 
    				douifcs.cancelButton.width, douifcs.cancelButton.height, douifcs.cancelButton.text, false ) ;
    	
    	}
    	
    	if ( this . isVisible ( ) ) {

    		g . addButton ( cancelChangesButton, false ) ;
    		
    	}

		if ( nameField == null ) {
			
			nameField = new GuiGradientTextField ( g.getFontRenderer(), 30, 10, douifcs . nameField . width - 1, douifcs . nameField . height, true ) ;
			nameField . setMaxStringLength ( 140 ) ;
			nameField . setEnableBorderDrawing ( true, fieldBorderColor ) ;
			
		}
		
		nameField . setText ( fieldText != null ? fieldText : "" ) ;
		nameField . setTextHolder ( MineDonate.getAccount().ms.currentItemStack != null ? MineDonate.getAccount().ms.currentItemStack.getDisplayName() : fieldHolder ) ;
		
		nameField . xPosition = posX + 40 ;
		nameField . yPosition = posY + 15 ;

		if ( limitField == null && douifcs . limitField != null ) {
			
			limitField = new GuiGradientTextField ( g.getFontRenderer(), 30, 10, douifcs . limitField . width - 1, douifcs . limitField . height, true ) ;
			limitField . setMaxStringLength ( 10 ) ;
			limitField . setEnableBorderDrawing ( true, fieldBorderColor ) ;
			
		}

		if ( limitField != null ) {
			
			limitField . setEnabled ( MineDonate . getAccount ( ) . canUnlimitedItems ( ) ) ;
			limitField . setVisible ( MineDonate . getAccount ( ) . canUnlimitedItems ( ) ) ;
			
			limitField . setText ( "" ) ;
			limitField . setTextHolder ( "" ) ;
			
		}
		
		if ( MineDonate . getAccount ( ) . canUnlimitedItems ( ) ) {
				
			nameField . xPosition = posX + 20 ;

			if ( limitField != null ) {

				height = 65 ;

				limitField . xPosition = nameField . xPosition ;
				limitField . yPosition = nameField . yPosition + nameField . height + 5 ;
				
				limitField . setText ( douifcs . limitField . text ) ;
				limitField . setTextHolder ( douifcs . limitField . textHolder ) ; 
			
			} else {
								
				height = 40 ;

			}
			
		}
		
		if ( costField == null ) {
			
			costField = new GuiGradientTextField ( g.getFontRenderer(), 30, 10, douifcs . costField . width - 1, douifcs . costField . height, true ) ;
			costField . setMaxStringLength ( 10 ) ;
			costField . setEnableBorderDrawing ( true, fieldBorderColor ) ;
			
			costField . setText ( douifcs . costField . text ) ;
			costField . setTextHolder ( douifcs . costField . textHolder ) ;
						
		}
	
		costField . xPosition = nameField . xPosition + nameField . width + 5 ;
		costField . yPosition = nameField . yPosition ;

    	cancelChangesButton . yPosition = saveChangesButton . yPosition = posY + height ;
    	cancelChangesButton . xPosition = posX + width - cancelChangesButton . width ;
    	saveChangesButton . xPosition = cancelChangesButton . xPosition - saveChangesButton . width ;
    	
    }
    
    @Override
	public void unShow ( ShopGUI g ) {
		
    	super . unShow ( g ) ;
    	
    	g . removeButton ( cancelChangesButton ) ;
    	g . removeButton ( saveChangesButton ) ;

	}
	
    @Override
	public boolean actionPerformed ( ShopGUI g, GuiButton b ) {
		
    	if ( b . id == saveChangesButton . id ) {
    	
    		g . setLoading ( true ) ;
    		
    		if ( MineDonate . getAccount ( ) . canUnlimitedItems ( ) ) {

    			try {
    				
    				if ( limitField != null ) {
    					
	    				Integer n = Integer . parseInt ( limitField . getText ( ) ) ;
		    			
	    				if ( limit != n ) {
	    					
	    					hideFrame ( g ) ;
	    			    		
	    					ModNetwork . sendToServerEditMerchNumberPacket ( shopId, catId, merch_id, EditMerchNumberPacket . Type . LIMIT, ( int ) n ) ;
	    					
	    					limit = n ;
	    					    						
	    				}
	    				
    				}
    				
	    		} catch ( Exception ex ) {
	    				    			
	    		}
    			
    		}
    		
			try {
				
				Integer n = Integer . parseInt ( costField . getText ( ) ) ;
    			
				if ( cost != n ) {
					
					hideFrame ( g ) ;
			        
					ModNetwork . sendToServerEditMerchNumberPacket ( shopId, catId, merch_id, EditMerchNumberPacket . Type . COST, ( int ) n ) ;
					
					cost = n ;
										
				}
				
    		} catch ( Exception ex ) {
    			    			
    		}

    		if ( ! nameField . getText ( ) . trim ( ) . equals ( fieldText ) ) {
    			
				hideFrame ( g ) ;
		        
    			ModNetwork . sendToServerRenameMerchPacket ( shopId, catId, merch_id, EditMerchStringPacket . Type . NAME, ( fieldText = nameField . getText ( ) ) ) ;
    			
    		}
    		
    	}
    	
    	if ( b . id == cancelChangesButton . id ) {
    	
			hideFrame ( g ) ;

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
		
		return ( gb == cancelChangesButton || gb == saveChangesButton ) ;
		
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

		nameField . setText ( fieldText != null ? fieldText : "" ) ;
		nameField . setTextHolder ( fieldHolder ) ;
		
    }

	public void setInfo ( int _shopId, int _catId, int _merch_id, int _limit, int _cost ) {
		
		shopId = _shopId ;
		catId = _catId ;
		merch_id = _merch_id ;
		limit = _limit ;
		cost = _cost ;
		
	}
    
}

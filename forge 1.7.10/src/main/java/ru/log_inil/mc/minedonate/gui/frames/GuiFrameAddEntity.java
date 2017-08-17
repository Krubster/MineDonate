package ru.log_inil.mc.minedonate.gui.frames;

import net.minecraft.client.gui.GuiButton;

import ru.alastar.minedonate.MineDonate;
import ru.alastar.minedonate.gui.ShopGUI;
import ru.alastar.minedonate.rtnl.ModNetwork;

import ru.log_inil.mc.minedonate.gui.DrawType;
import ru.log_inil.mc.minedonate.gui.GuiGradientButton;
import ru.log_inil.mc.minedonate.gui.GuiGradientTextField;
import ru.log_inil.mc.minedonate.localData.frames.DataOfUIFrameAddItem;

public class GuiFrameAddEntity extends GuiFrameAddItem {

	public GuiFrameAddEntity ( String _name, DataOfUIFrameAddItem _douifcs ) {
	
		super ( _name, _douifcs ) ;
		width = 200 ;
		widthCenter = width / 2 ;
		
	}

    public void draw ( ShopGUI g, int page, int mouseX, int mouseY, float partialTicks, DrawType dt ) {

    	g . drawRect ( posX, posY, posX + width, posY + height, backgroundColor ) ;
    	
    	super . draw( g, page, mouseX, mouseY, partialTicks, dt ) ;
    	
    	g . drawString ( g . getFontRenderer ( ), douifcs . title, posX + 5, posY + 3, titleColor ) ;
    	
    	nameField . drawTextBox ( ) ;
    	costField . drawTextBox ( ) ;
    	limitField . drawTextBox ( ) ;
    	
    }
    
    @Override
 	public void postShow ( ShopGUI g ) {
     	
		if ( ! isVisible ( ) ) {
			
			return ;
			
		}

     	posX = (g.getScaledResolution().getScaledWidth()/2) - widthCenter;
     	posY = (g.getScaledResolution().getScaledHeight()/2) - heightCenter;

     	if ( saveChangesButton == null ) {
         	
     		saveChangesButton = new GuiGradientButton ( ShopGUI . getNextButtonId ( ), posX, posY + height, 
     				douifcs.createButton.width, douifcs.createButton.height, douifcs.createButton.text, false ) ;
     	
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
 		
 		nameField . xPosition = posX + 20 ;
 		nameField . yPosition = posY + 15 ;

 		if ( limitField == null ) {
 			
 			limitField = new GuiGradientTextField ( g.getFontRenderer(), 30, 10, douifcs . limitField . width - 1, douifcs . limitField . height, true ) ;
 			limitField . setMaxStringLength ( 10 ) ;
 			limitField . setEnableBorderDrawing ( true, fieldBorderColor ) ;
 			
 		}

 		limitField . setEnabled ( MineDonate . getAccount ( ) . canUnlimitedItems ( ) ) ;
 		limitField . setVisible ( MineDonate . getAccount ( ) . canUnlimitedItems ( ) ) ;
 		
 		limitField . setText ( "" ) ;
 		limitField . setTextHolder ( "" ) ;
 		
 		if ( MineDonate . getAccount ( ) . canUnlimitedEntities ( ) ) {
 			
 			height = 40 ;
 			limitField . xPosition = nameField . xPosition ;
 			limitField . yPosition = nameField . yPosition + nameField . height ;
 			
 			limitField . setText ( douifcs . limitField . text ) ;
 			limitField . setTextHolder ( douifcs . limitField . textHolder ) ;
 			
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
     	
    	limitField.setEnabled(false);
    	limitField.setVisible(false);
    	
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
    		
    		if ( MineDonate . getAccount ( ) . canUnlimitedEntities ( ) && this . limitField != null ) {
    			
    			try {

    				limit = Integer . parseInt ( this . limitField . getText ( ) ) ;

    			} catch ( Exception ex ) {
    				        			
    				limit = 1 ;
    				/*
    				limitField . fieldBorderColor = fieldBorderRedColor ;
        			
        			return false ;*/
        			
    			}
    			
    		} else {
    			
    			limit = MineDonate . getAccount ( ) . ms . currentItemStack . stackSize ;
    			
    		}
    		
    		g . setLoading ( true ) ;
    		
            ModNetwork . sendToServerAddNewEntryPacket ( shopId, catId, limit, cost, this . nameField . getText ( ) ) ;

            this . hideFrame ( g ) ;

    	}
    	
    	if ( b . id == cancelChangesButton . id ) {
    		
            this . hideFrame ( g ) ;
    		
    	}
    	
		return false ; 
		
    }	
		
}

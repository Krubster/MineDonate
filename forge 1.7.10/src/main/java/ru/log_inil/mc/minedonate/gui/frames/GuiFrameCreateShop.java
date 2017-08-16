package ru.log_inil.mc.minedonate.gui.frames;

import java.awt.Color;

import net.minecraft.client.gui.GuiButton;

import ru.alastar.minedonate.gui.ShopGUI;
import ru.alastar.minedonate.rtnl.ModNetwork;
import ru.alastar.minedonate.rtnl.Utils;

import ru.log_inil.mc.minedonate.gui.DrawType;
import ru.log_inil.mc.minedonate.gui.GuiFrame;
import ru.log_inil.mc.minedonate.gui.GuiGradientButton;
import ru.log_inil.mc.minedonate.gui.GuiGradientTextField;
import ru.log_inil.mc.minedonate.localData.frames.DataOfUIFrameCreateShop;

public class GuiFrameCreateShop extends GuiFrame {

	int width = 200 ;
	int height = 40 ;
	
	int posX ;
	int posY ;
	
	static int backgroundColor = Utils . rgbaToInt ( new Color ( 0, 0, 0, 150 ) ) ;
	static int fieldBorderRedColor = Utils . rgbaToInt ( new Color ( 255, 0, 0, 150 ) ) ;
	static int fieldBorderColor = Utils . rgbaToInt ( new Color ( 255, 255, 255, 150 ) ) ;
	static int titleColor = Utils . rgbaToInt ( new Color ( 255, 255, 255, 180 ) ) ;
	
	int widthCenter = width / 2 ;
	int heightCenter = height / 2 ;
	
	DataOfUIFrameCreateShop douifcs ;
	public GuiFrameCreateShop ( String _name, DataOfUIFrameCreateShop _douifcs ) {
		
		super ( _name ) ;
		
		douifcs = _douifcs ;
		
		fieldText = douifcs . nameField . text ;
		fieldHolder = douifcs . nameField . textHolder ;
		
	}
	
    public void draw(ShopGUI g, int page, int mouseX, int mouseY, float partialTicks, DrawType dt ) {

    	g . drawRect ( posX, posY, posX + width, posY + height, backgroundColor ) ;
    	
    	super . draw( g, page, mouseX, mouseY, partialTicks, dt ) ;
    	
    	g . drawString ( g . getFontRenderer ( ), douifcs . title, posX + 5, posY + 3, titleColor ) ;
    	
    	nameField . drawTextBox ( ) ;
    	
    	
    }
    
    GuiButton saveChangesButton ;
    GuiButton cancelChangesButton ;
    GuiGradientTextField nameField ; 
    
    String fieldText, fieldHolder ;
    
    @Override
	public void postShow ( ShopGUI g ) {
		
		if ( ! isVisible ( ) ) {
			
			return ;
			
		}
		
    	super.postShow(g);

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
		nameField . setTextHolder ( fieldHolder ) ;
		
		nameField . xPosition = posX + 20 ;
		nameField . yPosition = posY + 15 ;

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
    	
    		if ( nameField . getText ( ) . trim ( ) . isEmpty ( ) ) {
    			
    			nameField . fieldBorderColor = fieldBorderRedColor ;
    			
    			return false ;
    			
    		}
    		
    		g . setLoading ( true ) ;
    		
            ModNetwork . sendToServerCreateNewShopPacket ( this . nameField . getText ( ) ) ;
            
			hideFrame ( g ) ;

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
    	
		return false ;
		
	}

    @Override
	public boolean onKey ( char c, int k ) {
		
    	if ( nameField != null && nameField . isFocused ( ) ) {
    		
    		nameField . textboxKeyTyped ( c, k ) ;
    		
			nameField . fieldBorderColor = fieldBorderColor ;

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

    }
    
    
}

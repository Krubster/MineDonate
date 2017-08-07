package ru.log_inil.mc.minedonate.gui;

import java.awt.Color;

import net.minecraft.client.gui.GuiButton;
import ru.alastar.minedonate.MineDonate;
import ru.alastar.minedonate.Utils;
import ru.alastar.minedonate.gui.ShopGUI;
import ru.log_inil.mc.minedonate.localData.frames.DataOfUIFrameRename;

public class GuiFrameItemRename extends GuiEntry {

	int width = 200 ;
	int height = 40 ;
	
	int posX ;
	int posY ;
	
	static int backgroundColor = Utils . rgbaToInt ( new Color ( 0, 0, 0, 150 ) ) ;
	static int fieldBorderColor = Utils . rgbaToInt ( new Color ( 255, 255, 255, 150 ) ) ;
	static int titleColor = Utils . rgbaToInt ( new Color ( 255, 255, 255, 180 ) ) ;

	int widthCenter = width / 2 ;
	int heightCenter = height / 2 ;
	
	DataOfUIFrameRename douifr ;
	public GuiFrameItemRename ( DataOfUIFrameRename _douifr ) {
		
		douifr = _douifr ;
		
	}
	
    public void draw(ShopGUI g, int page, int mouseX, int mouseY, float partialTicks, DrawType dt ) {

    	g . drawRect ( posX, posY, posX + width, posY + height, backgroundColor ) ;
    	
    	super . draw( g, page, mouseX, mouseY, partialTicks, dt ) ;
    	
    	g . drawString ( g . getFontRenderer ( ), douifr.title, posX + 5, posY + 3, titleColor ) ;
    	
    	renameField . drawTextBox ( ) ;
    	
    	
    }
    
    GuiButton saveChangesButton ;
    GuiButton cancelChangesButton ;
    GuiGradientTextField renameField ; 
    
    String fieldText, fieldHolder ;
    
    @Override
	public void postShow ( ShopGUI g ) {
		
    	super.postShow(g);
    	
    	posX = (g.getScaledResolution().getScaledWidth()/2) - widthCenter;
    	posY = (g.getScaledResolution().getScaledHeight()/2) - heightCenter;

    	if ( saveChangesButton == null ) {
        	
    		saveChangesButton = new GuiGradientButton ( ShopGUI . getNextButtonId ( ), posX, posY + height, 
    				douifr.saveButton.width, douifr.saveButton.height, douifr.saveButton.text, false ) ;
    	
    	}
    	
    	if ( this . isVisible ( ) ) {

    		g . addBtn ( saveChangesButton, false ) ;
    		
    	}
    	
    	if ( cancelChangesButton == null ) {
        	
    		cancelChangesButton = new GuiGradientButton ( ShopGUI . getNextButtonId ( ), posX, posY + height, 
    				douifr.cancelButton.width, douifr.cancelButton.height, douifr.cancelButton.text, false ) ;
    	
    	}
    	
    	if ( this . isVisible ( ) ) {
    		
    		g . addBtn ( cancelChangesButton, false ) ;
    		
    	}

		if ( renameField == null ) {
		
			renameField = new GuiGradientTextField ( g.getFontRenderer(), 30, 10, douifr . renameField . width - 1, douifr . renameField . height, true ) ;
			renameField . setEnableBorderDrawing ( true, fieldBorderColor ) ;
			
		}
		
		renameField . setText ( fieldText != null ? fieldText : "" ) ;
		renameField . setTextHolder ( fieldHolder ) ;
		
		renameField . xPosition = posX + 20 ;
		renameField . yPosition = posY + 15 ;

    	cancelChangesButton . yPosition = saveChangesButton . yPosition = posY + height ;
    	cancelChangesButton . xPosition = posX + width - cancelChangesButton . width ;
    	saveChangesButton . xPosition = cancelChangesButton . xPosition - cancelChangesButton . width ;

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
    	
    		//System.err.println(renameField.getText());
    		//this . show ( false ) ;

    	}
    	
    	if ( b . id == cancelChangesButton . id ) {
    	
    		g . showEntry ( "itemRename", false ) ;
    		unShow ( g ) ;
    		
    	}
    	
		return false ;
		
	}
	
    @Override
	public boolean onClick ( int x, int y, int i ) {
		
    	if ( renameField != null ) {

    		renameField . mouseClicked ( x, y, i ) ;
    		    		
    	}
    	
		return false ;
		
	}

    @Override
	public boolean onKey ( char c, int k ) {
		
    	if ( renameField != null && renameField . isFocused ( ) ) {
    		
    		renameField . textboxKeyTyped ( c, k ) ;
    		
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

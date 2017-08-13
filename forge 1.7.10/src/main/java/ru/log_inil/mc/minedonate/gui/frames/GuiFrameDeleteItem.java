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
import ru.log_inil.mc.minedonate.localData.frames.DataOfUIFrameDeleteItem;

public class GuiFrameDeleteItem extends GuiFrame {

	int width = 200 ;
	int height = 50 ;
	
	int posX ;
	int posY ;
	
	static int backgroundColor = Utils . rgbaToInt ( new Color ( 0, 0, 0, 150 ) ) ;
	static int fieldBorderRedColor = Utils . rgbaToInt ( new Color ( 255, 0, 0, 150 ) ) ;
	static int fieldBorderColor = Utils . rgbaToInt ( new Color ( 255, 255, 255, 150 ) ) ;
	static int titleColor = Utils . rgbaToInt ( new Color ( 255, 255, 255, 180 ) ) ;
	
	int widthCenter = width / 2 ;
	int heightCenter = height / 2 ;
	
	DataOfUIFrameDeleteItem douifdi ;

	int shopId = -1, catId = -1, merchId = -1 ;

	public GuiFrameDeleteItem ( String _name, DataOfUIFrameDeleteItem _douifdi ) {
		
		super ( _name ) ;

		douifdi = _douifdi ;
		
		fieldText = douifdi . codeField . text ;
		fieldHolder = douifdi . codeField . textHolder ;
		
	}
	
    public void draw(ShopGUI g, int page, int mouseX, int mouseY, float partialTicks, DrawType dt ) {

    	g . drawRect ( posX, posY, posX + width, posY + height, backgroundColor ) ;
    	
    	super . draw ( g, page, mouseX, mouseY, partialTicks, dt ) ;
    	
    	g . drawString ( g . getFontRenderer ( ), douifdi . title, posX + 5, posY + 3, titleColor ) ;
    	g . drawString ( g . getFontRenderer ( ), codeText, posX + 5, posY + 13, titleColor ) ;

    	codeField . drawTextBox ( ) ;
    	
    	
    }
    
    GuiButton deleteButton ;
    GuiButton cancelChangesButton ;
    GuiGradientTextField codeField ; 
    
    String fieldText, fieldHolder, code, codeText ;
    
    @Override
	public void postShow ( ShopGUI g ) {
		
    	super . postShow ( g ) ;

    	posX = (g.getScaledResolution().getScaledWidth()/2) - widthCenter;
    	posY = (g.getScaledResolution().getScaledHeight()/2) - heightCenter;

    	if ( deleteButton == null ) {
        	
    		deleteButton = new GuiGradientButton ( ShopGUI . getNextButtonId ( ), posX, posY + height, 
    				douifdi.deleteButton.width, douifdi.deleteButton.height, douifdi.deleteButton.text, false ) ;
    	
    	}
    	
    	if ( this . isVisible ( ) ) {

    		g . addButton ( deleteButton, false ) ;
    		
    	}
    	
    	if ( cancelChangesButton == null ) {
        	
    		cancelChangesButton = new GuiGradientButton ( ShopGUI . getNextButtonId ( ), posX, posY + height, 
    				douifdi.cancelButton.width, douifdi.cancelButton.height, douifdi.cancelButton.text, false ) ;
    	
    	}
    	
    	if ( this . isVisible ( ) ) {

    		g . addButton ( cancelChangesButton, false ) ;
    		
    	}

		if ( codeField == null ) {
		
			codeField = new GuiGradientTextField ( g.getFontRenderer(), 30, 10, douifdi . codeField . width - 1, douifdi . codeField . height, true ) ;
			codeField . setMaxStringLength ( 143 ) ;
			codeField . setEnableBorderDrawing ( true, fieldBorderColor ) ;
			
		}
		
		codeField . setText ( fieldText != null ? fieldText : "" ) ;
		codeField . setTextHolder ( fieldHolder ) ;
		
		codeField . xPosition = posX + 20 ;
		codeField . yPosition = posY + 25 ;

    	cancelChangesButton . yPosition = deleteButton . yPosition = posY + height ;
    	cancelChangesButton . xPosition = posX + width - cancelChangesButton . width ;
    	deleteButton . xPosition = cancelChangesButton . xPosition - deleteButton . width ;

    }
    
    @Override
	public void unShow ( ShopGUI g ) {
		
    	super . unShow ( g ) ;
    	
    	g . removeButton ( cancelChangesButton ) ;
    	g . removeButton ( deleteButton ) ;

	}
	
    @Override
	public boolean actionPerformed ( ShopGUI g, GuiButton b ) {
		
    	if ( b . id == deleteButton . id ) {
    	
    		if ( ! ShopGUI . instance . confirmFlag && ( codeField . getText ( ) . trim ( ) . isEmpty ( ) || ! code . equals ( codeField . getText ( ) ) ) ) {
    			
    			codeField . fieldBorderColor = fieldBorderRedColor ;
    			
    			return false ;
    			
    		}
    		
    		g . setLoading ( true ) ;
    		
    		ModNetwork . sendToServerDeleteShopMerchPacket ( shopId, catId, merchId ) ;

			hideFrame ( g ) ;

    	}
    	
    	if ( b . id == cancelChangesButton . id ) {
    	
			hideFrame ( g ) ;
    		
    	}
    	
		return false ;
		
	}
	
    @Override
	public boolean onClick ( int x, int y, int i ) {
		
    	if ( codeField != null ) {

    		codeField . mouseClicked ( x, y, i ) ;
    		    		
    		codeField . fieldBorderColor = fieldBorderColor ;

    	}
    	
		return false ;
		
	}

    @Override
	public boolean onKey ( char c, int k ) {
		
    	if ( codeField != null && codeField . isFocused ( ) ) {
    		
    		codeField . textboxKeyTyped ( c, k ) ;
    		
    		codeField . fieldBorderColor = fieldBorderColor ;

    		return true ;
    		
    	}
    	
		return false ;
		
	}
	
	public boolean isOwnerButton ( GuiButton gb ) {
		
		return ( gb == cancelChangesButton || gb == deleteButton ) ;
		
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

	public void setInfo ( int _shopId, int _catId, int _merchId ) {
		
		shopId = _shopId ;
		catId = _catId ;
		merchId = _merchId ;
		
	}
	
	public void setConfirmCode ( String _code ) {

		code = _code ;
		codeText = this . douifdi . text . replace ( "%code%", code ) ;
		
	}
	
}

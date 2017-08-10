package ru.log_inil.mc.minedonate.gui.frames;

import java.awt.Color;

import net.minecraft.client.gui.GuiButton;
import ru.alastar.minedonate.gui.ShopGUI;
import ru.alastar.minedonate.rtnl.Utils;
import ru.log_inil.mc.minedonate.gui.DrawType;
import ru.log_inil.mc.minedonate.gui.GuiEntry;
import ru.log_inil.mc.minedonate.gui.GuiGradientButton;
import ru.log_inil.mc.minedonate.gui.GuiGradientTextField;
import ru.log_inil.mc.minedonate.localData.frames.DataOfUIFrameRenameEntity;
import ru.log_inil.mc.minedonate.localData.frames.DataOfUIFrameRenameItem;

public class GuiFrameRenameEntity extends GuiEntry {

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
	
	DataOfUIFrameRenameEntity douifri ;

	int shopId = -1, catId = -1, merch_id = -1 ;
	
	public GuiFrameRenameEntity ( DataOfUIFrameRenameEntity _douifri ) {
		
		douifri = _douifri ;
		
		fieldText = douifri . renameField . text ;
		fieldHolder = douifri . renameField . textHolder ;
		
	}
	
    public void draw(ShopGUI g, int page, int mouseX, int mouseY, float partialTicks, DrawType dt ) {

    	g . drawRect ( posX, posY, posX + width, posY + height, backgroundColor ) ;
    	
    	super . draw( g, page, mouseX, mouseY, partialTicks, dt ) ;
    	
    	g . drawString ( g . getFontRenderer ( ), douifri . title, posX + 5, posY + 3, titleColor ) ;
    	
    	renameField . drawTextBox ( ) ;
    	
    	
    }
    
    GuiButton saveChangesButton ;
    GuiButton cancelChangesButton ;
    GuiGradientTextField renameField ; 
    
    public String fieldText, fieldHolder ;
    
    @Override
	public void postShow ( ShopGUI g ) {
		
    	super . postShow ( g ) ;

    	posX = ( g . getScaledResolution ( ) . getScaledWidth ( ) / 2 ) - widthCenter ;
    	posY = ( g . getScaledResolution ( ) . getScaledHeight ( ) / 2 ) - heightCenter ;

    	if ( saveChangesButton == null ) {
        	
    		saveChangesButton = new GuiGradientButton ( ShopGUI . getNextButtonId ( ), posX, posY + height, douifri.saveButton.width, douifri.saveButton.height, douifri.saveButton.text, false ) ;
    	
    	}
    	
    	if ( this . isVisible ( ) ) {

    		g . addButton ( saveChangesButton, false ) ;
    		
    	}
    	
    	if ( cancelChangesButton == null ) {
        	
    		cancelChangesButton = new GuiGradientButton ( ShopGUI . getNextButtonId ( ), posX, posY + height, douifri.cancelButton.width, douifri.cancelButton.height, douifri.cancelButton.text, false ) ;
    	
    	}
    	
    	if ( this . isVisible ( ) ) {

    		g . addButton ( cancelChangesButton, false ) ;
    		
    	}

		if ( renameField == null ) {
		
			renameField = new GuiGradientTextField ( g.getFontRenderer(), 30, 10, douifri . renameField . width - 1, douifri . renameField . height, true ) ;
			renameField . setMaxStringLength ( 143 ) ;
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
    	
    		if ( renameField . getText ( ) . trim ( ) . isEmpty ( ) ) {
    			
    			renameField . fieldBorderColor = fieldBorderRedColor ;
    			
    			return false ;
    			
    		}
    		
    		g . setLoading ( true ) ;
    		
            // MineDonate . networkChannel . sendToServer ( new RenameShopPacket ( shopId, this . renameField . getText ( ) ) ) ;

            g . showEntry ( "renameEntity", false ) ; 

            unShow ( g ) ;

    	}
    	
    	if ( b . id == cancelChangesButton . id ) {
    	
    		g . showEntry ( "renameEntity", false ) ;
    		
    		unShow ( g ) ;
    		
    	}
    	
		return false ;
		
	}
	
    @Override
	public boolean onClick ( int x, int y, int i ) {
		
    	if ( renameField != null ) {

    		renameField . mouseClicked ( x, y, i ) ;
    		    		
    		renameField . fieldBorderColor = fieldBorderColor ;

    	}
    	
		return false ;
		
	}

    @Override
	public boolean onKey ( char c, int k ) {
		
    	if ( renameField != null && renameField . isFocused ( ) ) {
    		
    		renameField . textboxKeyTyped ( c, k ) ;
    		
    		renameField . fieldBorderColor = fieldBorderColor ;

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

		renameField . setText ( fieldText != null ? fieldText : "" ) ;
		renameField . setTextHolder ( fieldHolder ) ;
		
    }

	public void setInfo ( int _shopId, int _catId, int _merch_id ) {
		
		shopId = _shopId ;
		catId = _catId ;
		merch_id = _merch_id ;
		
	}
    
}

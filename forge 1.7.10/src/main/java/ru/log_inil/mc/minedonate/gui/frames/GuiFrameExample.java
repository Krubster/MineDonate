package ru.log_inil.mc.minedonate.gui.frames;

import java.awt.Color;

import ru.alastar.minedonate.gui.ShopGUI;
import ru.alastar.minedonate.rtnl.Utils;

import ru.log_inil.mc.minedonate.gui.DrawType;
import ru.log_inil.mc.minedonate.gui.GuiFrame;

public class GuiFrameExample extends GuiFrame {

	public GuiFrameExample ( String _name ) {
	
		super ( _name ) ;

	}

	int width = 200 ;
	int height = 120 ;
	
	int posX ;
	int posY ;
	
	static int background = Utils . rgbaToInt ( new Color ( 0, 0, 0, 150 ) ) ;
	
	int widthCenter = width / 2 ;
	int heightCenter = height / 2 ;
	
    public void draw(ShopGUI g, int page, int mouseX, int mouseY, float partialTicks, DrawType dt ) {

    	super . draw ( g, page, mouseX, mouseY, partialTicks, dt ) ;
    	
    	g . drawRect ( posX, posY, posX + width, posY + height, background ) ;
    	
    }
    
    @Override
	public void postShow ( ShopGUI g ) {
		
    	super.postShow(g);
    	
    	posX = (g.getScaledResolution().getScaledWidth()/2) - widthCenter;
    	posY = (g.getScaledResolution().getScaledHeight()/2) - heightCenter;

	}
    
    @Override
	public boolean coordContains ( int x, int y ) {
		
		return ( posX <= x && x <= posX + width ) && ( posY <= y && posY + height >= y ) ;
		
	}

    @Override
	public boolean lockContextMenuUnderEntry ( ) {
		
		return true ;
		
	}
	
    @Override
	public boolean lockButtonsUnderEntry ( ) {
		
		return true ;
		
	}
	
}

package ru.log_inil.mc.minedonate.gui.frames;

import ru.alastar.minedonate.Utils;
import ru.alastar.minedonate.gui.ShopGUI;
import ru.log_inil.mc.minedonate.gui.DrawType;
import ru.log_inil.mc.minedonate.gui.GuiFrame;

import java.awt.*;

public class GuiFrameLoading extends GuiFrame {

	public GuiFrameLoading ( String _name ) {
	
		super ( _name ) ;

	}

	int width = 200 ;
	int height = 20 ;
	
	int posX ;
	int posY ;
	
	static int backgroundColor = Utils.rgbaToInt(new Color(0,0,0,100));
	static int textColor = Utils.rgbaToInt(new Color(255,255,255,255));
	
	int widthCenter = width / 2 ;
	int heightCenter = height / 2 ;
	
	boolean needUpdate = false ;
	
	int rectX0 = 0 ;
	int rectY0 = 0 ;
	
	int rectX1 = 0 ;
	int rectY1 = 0 ;

	int textX = 0 ;
	int textY = 0 ;
	
    public void draw(ShopGUI g, int page, int mouseX, int mouseY, float partialTicks, DrawType dt ) {
 
    	if ( needUpdate ) {
    		
    		rectX0 = (g.getScaledResolution().getScaledWidth()/2)-10-g.getFontRenderer().getStringWidth(t)/2;
    		rectY0 = posY - heightCenter ;
    		
    		rectX1 = (g.getScaledResolution().getScaledWidth()/2)+10+g.getFontRenderer().getStringWidth(t)/2;
    		rectY1 = posY + heightCenter ;
    		
    		textX = g.getScaledResolution().getScaledWidth()/2;
    		textY = ( posY - heightCenter / 2 ) + 1 ;
    		
    	}
    	
    	g.drawRect(rectX0, rectY0, rectX1, rectY1, backgroundColor);
	  	   
    	super . draw ( g, page, mouseX, mouseY, partialTicks, dt ) ;

    	g.drawCenteredString(g.getFontRenderer(), t, textX, textY, textColor);
	
    }
    
    @Override
	public void postShow ( ShopGUI g ) {
    	
		if ( ! isVisible ( ) ) {
			
			return ;
			
		}
		
    	super.postShow(g);
    	
    	posX = (g.getScaledResolution().getScaledWidth()/2) - widthCenter;
    	posY = 10 + (g.getScaledResolution().getScaledHeight()/2) - heightCenter;

	}
    
    @Override
	public boolean isVisible ( ) {
		
		return ShopGUI.instance.loading ;
		
	}
    
    String t ;
    
    public void setText ( String _t ) {
    	
    	t = _t ;
    	needUpdate = true ;
    	
    }
	
}
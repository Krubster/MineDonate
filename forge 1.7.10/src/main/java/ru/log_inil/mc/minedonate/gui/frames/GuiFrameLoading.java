package ru.log_inil.mc.minedonate.gui.frames;

import java.awt.Color;

import ru.alastar.minedonate.gui.ShopGUI;
import ru.alastar.minedonate.rtnl.Utils;

import ru.log_inil.mc.minedonate.gui.DrawType;
import ru.log_inil.mc.minedonate.gui.GuiFrame;

public class GuiFrameLoading extends GuiFrame {

	public GuiFrameLoading ( String _name ) {
	
		super ( _name ) ;

	}

	int width = 200 ;
	int height = 120 ;
	
	int posX ;
	int posY ;
	
	static int backgroundColor = Utils.rgbaToInt(new Color(0,0,0,100));
	static int textColor = Utils.rgbaToInt(new Color(255,255,255,255));

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
    		rectY0 = (g.getScaledResolution().getScaledHeight()/2)-5;
    		
    		rectX1 = (g.getScaledResolution().getScaledWidth()/2)+10+g.getFontRenderer().getStringWidth(t)/2;
    		rectY1 = (g.getScaledResolution().getScaledHeight()/2) + 15;
    		
    		textX = g.getScaledResolution().getScaledWidth()/2;
    		textY = g.getScaledResolution().getScaledHeight()/2;
    		
    	}
    	
    	g.drawRect(rectX0, rectY0, rectX1, rectY1, backgroundColor);
	  	   
    	g.drawCenteredString(g.getFontRenderer(), t, textX, textY, textColor);
	
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
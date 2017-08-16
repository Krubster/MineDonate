package ru.log_inil.mc.minedonate.gui;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;

public abstract class MCGuiAccessible extends GuiScreen {

	GuiScreen gs ;
	
	public void setParent ( GuiScreen _gs ) {
		
		gs = _gs ;
		
	}
	
	public GuiScreen getParent ( ) {
		
		return gs ;
		
	}
	
	FontRenderer fr ;
	
	public void setFontRenderer ( FontRenderer _fr ) {
		
		fr = _fr ;
		
	}
	
	public FontRenderer getFontRenderer ( ) {
		
		return fr ;
		
	}
	
}

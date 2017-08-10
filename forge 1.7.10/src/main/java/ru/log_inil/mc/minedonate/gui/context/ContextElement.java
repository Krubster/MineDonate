package ru.log_inil.mc.minedonate.gui.context;

import ru.alastar.minedonate.gui.ShopGUI;
import ru.log_inil.mc.minedonate.gui.MCGuiAccessible;

public class ContextElement  {
	
	public int id ;
	public String line ;
	public ContextMenuInteract cmi ;
	public int lineHeight ;
	public String name ;
	
	public ContextElement ( int _id, String _name, String _line, ContextMenuInteract _cmi, int _lineHeight ) {
		
		id = _id ;
		name = _name ;
		line = _line ;
		cmi = _cmi ;
		lineHeight = _lineHeight ;
		
	}
	
	public int getLineWidth ( MCGuiAccessible g ) {
		
		return g . getFontRenderer ( ) . getStringWidth ( line ) ;
		
	}
	
	public void draw ( MCGuiAccessible g, int ind, int max, int xOffset, int yOffset, ContextDrawType cdt ) {
		
		if ( cdt == ContextDrawType . NORMAL ) {
			
			g . drawString ( g . getFontRenderer ( ), line, xOffset + 4, yOffset + ( ind * lineHeight ), 14737632 ) ;
			
		} else {
			
			g . drawString ( g . getFontRenderer ( ), "§n" + line, xOffset + 4, yOffset + ( ind * lineHeight ), 14737632 ) ;
			
		}
		
	}
	
	public void onClick ( ShopGUI g, ContextMenu cmm ) {

		if ( cmi != null ) {
		
			cmi . onClickContextMenuElement ( g, cmm, this ) ;
		
		}
		
	}
	
}
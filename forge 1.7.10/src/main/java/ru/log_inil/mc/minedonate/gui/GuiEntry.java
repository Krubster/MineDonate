package ru.log_inil.mc.minedonate.gui;

import net.minecraft.client.gui.GuiButton;
import ru.alastar.minedonate.gui.ShopGUI;

public abstract class GuiEntry {

	boolean visible = false ;
	boolean needUnShow = false ;

	String name ;
	
	public GuiEntry ( String _name ) {
		
		name = _name ;
		
	}
	
    public void draw(ShopGUI relative, int page, int mouseX, int mouseY, float partialTicks, DrawType dt ) {
    	
    }
    
    public void unDraw ( ) {
    	
    }
    
    protected ShopGUI gui ;
    
	public void preShow ( ShopGUI _shopGUI ) {
		
		gui = _shopGUI ;
		
	}
	
	public void postShow ( ShopGUI g ) {
		
	}
	
	public void unShow ( ShopGUI g ) {
		
		needUnShow = false ;
		
	}
	
	public boolean isVisible ( ) {
		
		return visible ;
		
	}
	
	public boolean lockButtonsUnderEntry ( ) {
		
		return false ;
		
	}
	
	public boolean isOwnerButton ( GuiButton gb ) {
		
		return false ;
		
	}

	public boolean actionPerformed(ShopGUI shopGUI, GuiButton button) {
		
		return true ;
		
	}
	
	public void show ( boolean _visible ) {
		
		visible = _visible ;
		needUnShow = ! visible ;
		
	}

	public boolean needUnShow ( ) {
		
		return needUnShow ;
		
	}
	
	public boolean needReloadOnUnShow ( ) {
		
		return true ;
		
	}
	
	public boolean coordContains ( int x, int y ) {
		
		return false ;
		
	}

	public boolean lockContextMenuUnderEntry ( ) {
		
		return false ;
		
	}

	public boolean onClick ( int x, int y, int i ) {
		
		return false ;
		
	}

	public boolean onKey ( char c, int k ) {
		
		return false ;
		
	}
	
}

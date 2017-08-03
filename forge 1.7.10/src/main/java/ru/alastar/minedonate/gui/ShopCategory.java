package ru.alastar.minedonate.gui;

import net.minecraft.client.gui.GuiButton;
import ru.alastar.minedonate.MineDonate;
import ru.log_inil.mc.minedonate.gui.DrawType;
import ru.log_inil.mc.minedonate.gui.GuiScrollingList;

/**
 * Created by Alastar on 19.07.2017.
 */
public abstract class ShopCategory {

	protected int catId ;
	
	public boolean getEnabled ( ) {
		
		return false ;
		
	}

    public int getSourceCount ( int shopId ) {
    	
    	return 0 ;
    	
    }

    public String getName ( ) {
    	
    	return "generic" ;
    	
    }

    public abstract void draw(ShopGUI relative, int page, int mouseX, int mouseY, float partialTicks, DrawType dt ) ;
    public void undraw ( ) {
    	
    }
    
    public void updateButtons ( ShopGUI relative, int page ) {
    	
    }

    public int elementsOnPage ( ) {
    	
    	return rowCount * colCount ;
    	
    }

    public void actionPerformed ( GuiButton button ) {
    	
    }
    
    public int getButtonWidth ( ) {
    	
    	return 0 ;
    	
    }
    
    public String getButtonText ( ) {
    	
    	return "?" ;
    	
    }

    protected int rowCount ;
    
    public int getRowCount ( ) {
    	
    	return rowCount ;
    	
    }
    
    public void setRowCount ( int i ) {
    	
    	rowCount = i ;
    	
    }
    
    protected int colCount ;
    public int getColCount ( ) { 
    	
    	return colCount ;
    	
    }
    
    public void setColCount ( int i ) {
    	
    	colCount = i ;
    	
    }

    public int getItemWidth ( ) {
    	
    	return 0 ;
    	
    }
    
    public int getItemHeight ( ) {
    	
    	return 0 ;
    	
    }
    
    protected ShopGUI gui ;
    
	public void init ( ShopGUI _shopGUI ) {
		
		gui = _shopGUI ;
		
	}

	public void initGui ( ) {
		
	}

	protected boolean search ;
	protected String searchValue ;
	
	public void search ( String text ) {
		
		search = ! ( text == null || text . trim ( ) . isEmpty ( ) ) ;
		
		if ( search ) {
			
			searchValue = text . toLowerCase ( ) . trim ( ) ;
			
		} else {
			
			searchValue = "" ;
			
		}
			
		updateButtons ( gui, 0 ) ;
		
	}

	public GuiScrollingList getScrollList ( ) {
		
		return null ;
		
	}
	
	public String getCatMoneyType ( ) {
    
		return  MineDonate . getMoneyType ( ShopGUI . instance . getCurrentShopId ( ), catId ) ;
    	
    }

}

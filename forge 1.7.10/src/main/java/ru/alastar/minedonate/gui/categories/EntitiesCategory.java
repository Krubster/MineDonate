package ru.alastar.minedonate.gui.categories;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.ScaledResolution;
import ru.alastar.minedonate.MineDonate;
import ru.alastar.minedonate.gui.ShopCategory;
import ru.alastar.minedonate.gui.ShopGUI;
import ru.alastar.minedonate.merch.info.EntityInfo;
import ru.log_inil.mc.minedonate.gui.DrawType;
import ru.log_inil.mc.minedonate.gui.GuiAbstractItemEntry;
import ru.log_inil.mc.minedonate.gui.GuiItemsScrollArea;
import ru.log_inil.mc.minedonate.gui.GuiScrollingList;
import ru.log_inil.mc.minedonate.gui.items.GuiItemEntryOfEntityMerch;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alastar on 20.07.2017.
 */
public class EntitiesCategory implements ShopCategory {

	int catId = 3 ;
    
    @Override
    public boolean getEnabled() {
        return MineDonate.cfg.sellEntities;
    }

    @Override
    public int getSourceCount(int shopId) {
    	
    	return MineDonate . shops . containsKey ( shopId ) ? MineDonate . shops . get ( shopId ) . cats [ catId ] . getMerch ( ) . length : 0 ;
   
    }

    @Override
    public String getName() {
        return "Entities";
    }

    ScaledResolution resolution ;
    
    @Override
    public void draw(ShopGUI relative, int m_Page, int mouseX, int mouseY, float partialTicks, DrawType dt) {

    	resolution = new ScaledResolution(relative.mc, relative.mc.displayWidth, relative.mc.displayHeight);
    	gi.drawScreen(mouseX, mouseY, partialTicks, dt);
    	
    }
    
    @Override
    public void undraw ( ) {
    	
    }
    
    @Override
    public void updateButtons(ShopGUI relative, int m_Page) {
    	
    	refreshGui ( ) ; 

    }

    @Override
    public int elements_per_page() {
        return 0;
    }

    @Override
    public void actionPerformed(GuiButton button) {
        
    }
    
    // #LOG
    
	@Override
	public int getButtonWidth ( ) {
		
		return MineDonate.cfgUI.cats.entities.categoryButtonWidth;
		
	}
	
	@Override 
	public String getButtonText ( ) {
		
		return MineDonate.cfgUI.cats.entities.categoryButtonText ;
		
	}

	@Override
	public int getRowCount() {
		return 0;
	}

	@Override
	public void setRowCount(int i) {
	}

	@Override
	public int getColCount() {
		return 0;
	}

	@Override
	public void setColCount(int i) {
	}

	@Override
	public int getItemWidth() {
		return 0;
	}

	@Override
	public int getItemHeight() {
		return 0;
	}


	GuiItemsScrollArea gi ;
	List < GuiAbstractItemEntry > entrs = new ArrayList < > ( ) ;
	
	ShopGUI gui ;
	
	@Override
	public void init ( ShopGUI _shopGUI ) {

		gui = _shopGUI ;
		
	}
	
	@Override
	public void initGui ( ) {
	
		refreshGui ( ) ;
		
	}
	
	EntityInfo eim ;

	public void refreshGui ( ) {
		
		resolution = new ScaledResolution(gui.mc, gui.mc.displayWidth, gui.mc.displayHeight);

		gi = new GuiItemsScrollArea ( resolution, gui, entrs, 0 ) ;
	
		for ( GuiAbstractItemEntry gie : entrs ) {

			gie . undraw ( ) ;
			
		}
		
		entrs . clear ( ) ;
		
		if ( MineDonate . shops . containsKey ( gui . getCurrentShopId ( ) ) ) {
	
	    	if ( search ) {
	    		
	    		for ( int i = 0 ; i < MineDonate . shops . get ( gui . getCurrentShopId ( ) ) . cats [ catId ] . getMerch ( ) . length ; i ++ ) {
	        		
	        		eim = ( EntityInfo ) MineDonate . shops . get ( gui . getCurrentShopId ( ) ) . cats [ catId ] . getMerch ( ) [ i ] ; 
	        		
	        		if ( eim . name . toLowerCase ( ) . contains ( searchValue ) ) {
	        			
	            		entrs . add ( new GuiItemEntryOfEntityMerch ( eim, this ) . addButtons ( gui ) . updateDrawData ( ) ) ;
	        			
	        		}
	        		
	        	} 
	        		
	    	} else {
	    		
	        	for ( int i = 0 ; i <  MineDonate . shops . get ( gui . getCurrentShopId ( ) ) . cats [ catId ] . getMerch ( ) . length ; i ++ ) {
	        		
	        		eim = ( EntityInfo ) MineDonate . shops . get ( gui . getCurrentShopId ( ) ) . cats [ catId ] . getMerch ( ) [ i ] ; 
	        		entrs . add ( new GuiItemEntryOfEntityMerch ( eim, this ) . addButtons ( gui ) . updateDrawData ( ) ) ;
	        		
	        	}
	    		
	    	}
		
		}
    	
    	gi . entrs = entrs ;
    	gi . applyScrollLimits ( ) ;
    	
	}

	boolean search = false ;
	String searchValue = "" ;
	
	@Override
	public void search ( String text ) {
		
		search = ! ( text == null || text . trim ( ) . isEmpty ( ) ) ;
		
		if ( search ) {
			
			searchValue = text . toLowerCase ( ) . trim ( ) ;
			
		} else {
			
			searchValue = "" ;
			
		}
		
		refreshGui ( ) ;
		
	}
	
	@Override
	public GuiScrollingList getScrollList() {
		return null;
	}

	@Override
	public String getCatMoneyType ( ) {
		
		return  MineDonate . getMoneyType ( ShopGUI . instance . getCurrentShopId ( ), catId ) ;
		
	}
	
}

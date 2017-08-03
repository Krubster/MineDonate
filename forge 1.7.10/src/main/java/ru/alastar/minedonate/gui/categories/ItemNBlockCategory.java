package ru.alastar.minedonate.gui.categories;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;
import ru.alastar.minedonate.MineDonate;
import ru.alastar.minedonate.gui.CountButton;
import ru.alastar.minedonate.gui.ShopCategory;
import ru.alastar.minedonate.gui.ShopGUI;
import ru.alastar.minedonate.merch.info.ItemInfo;
import ru.log_inil.mc.minedonate.gui.DrawType;
import ru.log_inil.mc.minedonate.gui.GuiAbstractItemEntry;
import ru.log_inil.mc.minedonate.gui.GuiItemsScrollArea;
import ru.log_inil.mc.minedonate.gui.GuiScrollingList;
import ru.log_inil.mc.minedonate.gui.items.GuiItemEntryOfItemMerch;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alastar on 19.07.2017.
 */
public class ItemNBlockCategory implements ShopCategory {

	int catId = 0 ;
	
    private static int m_Per_Row = 4;
    private static int m_Per_Col = 2;

    @SideOnly(Side.CLIENT)
    ResourceLocation background = new ResourceLocation(MineDonate.MODID, "test.png");

    @Override
    public boolean getEnabled() {
        return MineDonate.cfg.sellItems;
    }

    @Override
    public int getSourceCount(int shopId) {
    	
    	return MineDonate . shops . containsKey ( shopId ) ? MineDonate . shops . get ( shopId ) . cats [ catId ] . getMerch ( ) . length : 0 ;

    }

    @Override
    public String getName() {
        return "Items & Blocks";
    }

    ScaledResolution resolution;
    
    @Override
    public void draw(ShopGUI relative, int m_Page, int mouseX, int mouseY, float partialTicks, DrawType dt) {
    	
        resolution = new ScaledResolution(relative.mc, relative.mc.displayWidth, relative.mc.displayHeight);
         
    	gi.drawScreen(mouseX, mouseY, partialTicks, dt);
    	
    }

    @Override
    public void updateButtons(ShopGUI relative, int m_Page) {
    	
    	initGui ( ) ;
    	
    }

    @Override
    public int elements_per_page() {
        return 0 ;
    }
    
    @Override
    public void actionPerformed(GuiButton button) {
    	
        if (button instanceof CountButton) {
        	
            CountButton countButton = (CountButton) button;
            countButton.tryModify();
            
        }
        
    }

    // #LOG
    
	@Override
	public int getButtonWidth ( ) {
		
		return MineDonate.cfgUI.cats.itemsAndBlocks.categoryButtonWidth;
		
	}
	
	@Override 
	public String getButtonText ( ) {
		
		return MineDonate.cfgUI.cats.itemsAndBlocks.categoryButtonText ;
		
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
	List < GuiAbstractItemEntry > entrs = new ArrayList <GuiAbstractItemEntry > ( ) ;
	
	ShopGUI gui ;
	
	@Override
	public void init ( ShopGUI _shopGUI ) {

		gui = _shopGUI ;
		
	}

	ItemInfo iim ;
	
	@Override
	public void initGui ( ) {
	
		resolution = new ScaledResolution(gui.mc, gui.mc.displayWidth, gui.mc.displayHeight);

		gi = new GuiItemsScrollArea ( resolution, gui, entrs, 0 ) ;
	
		for ( GuiAbstractItemEntry gie : entrs ) {

			gie . undraw ( ) ;
			
		}
		
		entrs . clear ( ) ;
		
		if ( MineDonate . shops . containsKey ( gui . getCurrentShopId ( ) ) ) {
	
	    	if ( search ) {
	    		
	    		for ( int i = 0 ; i < MineDonate . shops . get ( gui . getCurrentShopId ( ) ) . cats [ catId ] . getMerch ( ) . length ; i ++ ) {
	        		
	        		iim = ( ItemInfo ) MineDonate . shops . get ( gui . getCurrentShopId ( ) ) . cats [ catId ] . getMerch ( ) [ i ] ; 
	        		
	        		if ( ( iim . m_stack != null ? iim . m_stack . getDisplayName ( ) . contains( searchValue ) : false ) || iim . name . toLowerCase ( ) . contains ( searchValue ) ) {
	        			
	        			entrs . add ( new GuiItemEntryOfItemMerch ( iim, this ) . addButtons ( gui ) . updateDrawData ( ) ) ;
	        			
	        		}
	        		
	        	} 
	        		
	    	} else {
	    		
	    		for ( int i = 0 ; i < MineDonate . shops . get ( gui . getCurrentShopId ( ) ) . cats [ catId ] . getMerch ( ) . length ; i ++ ) {
	        		
	        		iim = ( ItemInfo ) MineDonate . shops . get ( gui . getCurrentShopId ( ) ) . cats [ catId ] . getMerch ( ) [ i ] ; 
	        		entrs . add ( new GuiItemEntryOfItemMerch ( iim, this ) . addButtons ( gui ) . updateDrawData ( ) ) ;
	        		
	        	} 
	    		
	    	}
		
		}
		
    	gi . entrs = entrs ;
    	gi. applyScrollLimits ( ) ;		
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
		
		initGui ( ) ;
		
	}

	@Override
	public GuiScrollingList getScrollList() {
		return gi;
	}

}

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
import ru.alastar.minedonate.merch.Merch;
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
public class ItemNBlockCategory extends ShopCategory {

	public ItemNBlockCategory ( ) {
		
		catId = 0 ;
		shopId = 0 ;
		
	}
	
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
    public void undraw ( ) {
    	
		for ( GuiAbstractItemEntry gie : entrs ) {

			gie . undraw ( ) ;
			
		}
		
    }

    @Override
    public void updateButtons(ShopGUI relative, int m_Page ) {
    	
    	initGui ( ) ;
    	
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

	GuiItemsScrollArea gi ;
	List < GuiAbstractItemEntry > entrs = new ArrayList <GuiAbstractItemEntry > ( ) ;
	
	ItemInfo iim ;
	
	@Override
	public void initGui ( ) {
	
		if ( subCatId == -1 ) {
			
			setSubCategory ( subCatId ) ;
			
		}
		
		resolution = new ScaledResolution ( gui . mc, gui.mc.displayWidth, gui.mc.displayHeight); // bull shit

		gi = new GuiItemsScrollArea ( resolution, gui, entrs, 0 ) ;
	
		for ( GuiAbstractItemEntry gie : entrs ) {

			gie . undraw ( ) ;
			
		}
		
		entrs . clear ( ) ;
		
		if ( MineDonate . shops . containsKey ( gui . getCurrentShopId ( ) ) ) {
	
	    	if ( search ) {
	    		
	    		for ( Merch m : noSearchedEntries ) {
	        		
	        		iim = ( ItemInfo ) m ; 

	        		if ( ( iim . m_stack != null ? iim . m_stack . getDisplayName ( ) . contains ( searchValue ) : false ) || iim . name . toLowerCase ( ) . contains ( searchValue ) ) {
	        			
	        			entrs . add ( new GuiItemEntryOfItemMerch ( iim, this ) . addButtons ( gui ) . updateDrawData ( ) ) ;
	        			
	        		}
	        		
	        	} 
	        		
	    	} else {
	    		
	    		for ( Merch m : noSearchedEntries ) {
	        		
	        		iim = ( ItemInfo ) m ; 
	        		
	        		entrs . add ( new GuiItemEntryOfItemMerch ( iim, this ) . addButtons ( gui ) . updateDrawData ( ) ) ;
	        		
	        	} 
	    		
	    	}
		
		}
		
    	gi . entrs = entrs ;
    	gi . applyScrollLimits ( ) ;
    	
	}
	
	public void setShopId ( int _shopId ) { 
		
		shopId = _shopId ;
		
	}
	
	@Override
	public GuiScrollingList getScrollList ( ) {
		
		return gi ;
		
	}
}

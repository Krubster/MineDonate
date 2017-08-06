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
import ru.log_inil.mc.minedonate.gui.GuiGradientButton;
import ru.log_inil.mc.minedonate.gui.GuiItemsScrollArea;
import ru.log_inil.mc.minedonate.gui.GuiScrollingList;
import ru.log_inil.mc.minedonate.gui.items.GuiItemEntryOfItemMerch;

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

        if ( subCats != null && subCats . length > 0 && subCatId == -1 && dt == DrawType . PRE ){        	
        
        	return ;
        	
        }
        
    	gi.drawScreen(mouseX, mouseY, partialTicks, dt);

    }
    
    @Override
    public void unDraw ( ) {
    	
    	super . unDraw ( ) ;
    	
		for ( GuiAbstractItemEntry gie : entrs ) {

			gie . unDraw ( ) ;
			
		}
		
    }

    GuiGradientButton addButton ;
	GuiButton rightButton ;

    @Override
    public void updateButtons(ShopGUI relative, int page ) {
    	    	    	    	
    	if ( ! ( relative.getCurrentCategory() instanceof ItemNBlockCategory ) ) {
    		
    		rightButton = (GuiButton) relative.getButtonList().get(relative.getButtonList().size()-1);
    		
    		if(rightButton.visible){
        		rightButton = (GuiButton) relative.getButtonList().get(relative.getButtonList().size()-2);
    		}
    		
    		if(rightButton.visible){
        		rightButton = (GuiButton) relative.getButtonList().get(relative.getButtonList().size()-3);
    		}
    		
    	} else {

    		rightButton = relative.exitButton;
    		
    	}

    	relative . getButtonList ( ) . add ( addButton = new GuiGradientButton ( ShopGUI . getNextButtonId ( ), 
    			rightButton . xPosition -  MineDonate . cfgUI . cats . itemsAndBlocks . addButton . width,
    			rightButton.yPosition, MineDonate . cfgUI . cats . itemsAndBlocks . addButton . width, MineDonate . cfgUI . cats . itemsAndBlocks . addButton . height, MineDonate . cfgUI . cats . itemsAndBlocks . addButton . text, false ) ) ;
    	
    	super.updateButtons(relative, page);
    	
    }
    
    @Override
    public void actionPerformed(GuiButton button) {
    	
    	super.actionPerformed(button);
    	
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
	
	ItemInfo iim ;
	
	@Override
	public void postShow ( ) {
	
		if ( subCatId == -1 ) {
			
			setSubCategory ( subCatId ) ;
			
		}
		
		resolution = new ScaledResolution ( gui . mc, gui.mc.displayWidth, gui.mc.displayHeight); // bull shit

		super . postShow ( ) ;
		
		gi = new GuiItemsScrollArea ( resolution, gui, entrs, 0 ) ;
	
		for ( GuiAbstractItemEntry gie : entrs ) {

			gie . unDraw ( ) ;
			
		}
		
		entrs . clear ( ) ;	
        
        if ( subCats != null && subCats . length > 0 && subCatId == -1 ) {
        	
        	return ;
        	
        }
        
		if ( MineDonate . shops . containsKey ( gui . getCurrentShopId ( ) ) ) {
	
	    	if ( search ) {
	    		
	    		for ( Merch m : noSearchedEntries ) {
	        		
	        		iim = ( ItemInfo ) m ; 

	        		if ( iim . getSearchValue ( ) . toLowerCase ( ) . contains ( searchValue ) ) {
	        			
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

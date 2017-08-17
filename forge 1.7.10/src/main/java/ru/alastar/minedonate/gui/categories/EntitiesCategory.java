package ru.alastar.minedonate.gui.categories;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

import ru.alastar.minedonate.MineDonate;
import ru.alastar.minedonate.events.MineDonateGUIHandler;
import ru.alastar.minedonate.gui.ShopCategory;
import ru.alastar.minedonate.gui.ShopGUI;
import ru.alastar.minedonate.merch.Merch;
import ru.alastar.minedonate.merch.info.EntityInfo;
import ru.alastar.minedonate.rtnl.ModNetwork;

import ru.log_inil.mc.minedonate.gui.DrawType;
import ru.log_inil.mc.minedonate.gui.GuiAbstractItemEntry;
import ru.log_inil.mc.minedonate.gui.GuiGradientButton;
import ru.log_inil.mc.minedonate.gui.GuiItemsScrollArea;
import ru.log_inil.mc.minedonate.gui.GuiScrollingList;
import ru.log_inil.mc.minedonate.gui.frames.GuiFrameAddEntity;
import ru.log_inil.mc.minedonate.gui.items.GuiItemEntryOfEntityMerch;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alastar on 20.07.2017.
 */
public class EntitiesCategory extends ShopCategory {
    
	public EntitiesCategory ( String _name ) {
		
		super ( _name ) ;
		
		catId = 3 ;
		
	}
	
    @Override
    public boolean getEnabled ( ) {
        
    	return MineDonate . cfg . sellEntities ;
        
    }

    @Override
    public int getSourceCount(int shopId) {
    	
    	return MineDonate . shops . containsKey ( shopId ) ? MineDonate . shops . get ( shopId ) . cats [ catId ] . getMerch ( ) . length : 0 ;
   
    }

    @Override
    public String getName ( ) {
        
    	return "Entities" ;
        
    }
    
    @Override
    public void draw(ShopGUI g, int page, int mouseX, int mouseY, float partialTicks, DrawType dt) {
    
    	super.draw(g, page, mouseX, mouseY, partialTicks, dt);

    	if ( gi != null ) {
    		
    		gi . drawScreen(mouseX, mouseY, partialTicks, dt);
    		
    	}
    	
    }
    
    GuiButton addButton;
    GuiButton rightButton;
    
    @Override
    public void updateButtons(ShopGUI relative, int page ) {	    	    	    	

    	rightButton = relative.exitButton;
    
    	if ( addButton != null ) {
    		
    		relative . removeButton ( addButton ) ;
    		
    	}
    	
    	relative . getButtonList ( ) . add ( addButton = new GuiGradientButton ( ShopGUI . getNextButtonId ( ), rightButton . xPosition -  MineDonate . cfgUI . cats . entities . addButton . width, rightButton . yPosition, MineDonate . cfgUI . cats . entities . addButton . width, MineDonate . cfgUI . cats . entities . addButton . height, MineDonate . cfgUI . cats . entities . addButton . text, false ) ) ;
    	
    	super.updateButtons(relative, page);
    	
    }
        

    @Override
    public boolean actionPerformed(ShopGUI g, GuiButton button) {

    	super.actionPerformed(g, button);

    	 if ( addButton != null && button . id == addButton . id ) {
         	
    		GuiFrameAddEntity gfai = ( GuiFrameAddEntity ) g . showEntry ( "frame.entity.add", true ) ;
         	
         	gfai . setInfo ( g . getCurrentShopId ( ), catId ) ;
         	
         	MineDonateGUIHandler . setBackShopGUI ( true ) ;
         	
    		ModNetwork . sendToServerMobSelectPacket ( 0 ) ;
         	
    		Minecraft . getMinecraft ( ) . displayGuiScreen ( null ) ;
    		
         }
    	 
		return false ;
        
    }
    
	@Override
	public int getButtonWidth ( ) {
		
		return MineDonate.cfgUI.cats.entities.categoryButtonWidth;
		
	}
	
	@Override 
	public String getButtonText ( ) {
		
		return MineDonate.cfgUI.cats.entities.categoryButtonText ;
		
	}

	GuiItemsScrollArea gi ;
		
	@Override
	public void postShow ( ShopGUI g ) {

		if ( subCatId == -1 ) {
			
			setSubCategory ( subCatId ) ;
			
		}

		if ( gi == null ) {

			gi = new GuiItemsScrollArea ( g . getScaledResolution ( ), gui, entrs, 0 ) ;
	
		} else {
			
			gi . updateSizes ( g . getScaledResolution ( ) ) ;
			
		}
		
		for ( GuiAbstractItemEntry gie : entrs ) {

			gie . unDraw ( ) ;
			
		}
		
		entrs . clear ( ) ;
		 
		if ( g . isLoading ( ) ) {
			
			return ;
			
		}
		
        if ( subCats != null && subCats . length > 0 && subCatId == -1 ) {
        	
        	return ;
        	
        }
        
		if ( MineDonate . shops . containsKey ( gui . getCurrentShopId ( ) ) ) {
	
	    	if ( search ) {
	    		
	    		for ( Merch m : noSearchedEntries ) {
	        		
	        		eim = ( EntityInfo ) m ; 
	        		
	        		if ( eim . name . toLowerCase ( ) . contains ( searchValue ) ) {
	        			
	            		entrs . add ( new GuiItemEntryOfEntityMerch ( eim, this ) . addButtons ( gui ) . updateDrawData ( ) ) ;
	        			
	        		}
	        		
	        	} 
	        		
	    	} else {
	    		
	    		for ( Merch m : noSearchedEntries ) {
	        		
	        		eim = ( EntityInfo ) m ; 
	        		entrs . add ( new GuiItemEntryOfEntityMerch ( eim, this ) . addButtons ( gui ) . updateDrawData ( ) ) ;
	        		
	        	}

	    	}
		
		}
    	
    	gi . entrs = entrs ;
    	gi . applyScrollLimits ( ) ;
    			
		super . postShow ( g ) ;
		
	}
	
	EntityInfo eim ;

	List < Merch > noSearchedEntries = new ArrayList < > ( ) ;
	
	public void setSubCategory ( int _subCatId ) {
		
		noSearchedEntries . clear ( ) ;
		
		if ( MineDonate . shops . containsKey ( gui . getCurrentShopId ( ) ) ) {
	
			for ( Merch m : MineDonate . shops . get ( 0 ) . cats [ catId ] . getMerch ( ) ) {
				
				if ( m . subCatId == _subCatId || _subCatId == -1 ) {
					
					noSearchedEntries . add ( m ) ;
					
				}
				
			}
			
		}
		
	}
	
	@Override
	public GuiScrollingList getScrollList ( ) {
		
		return gi ;
		
	}
	
}

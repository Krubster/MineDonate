package ru.alastar.minedonate.gui.categories;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.ScaledResolution;
import ru.alastar.minedonate.MineDonate;
import ru.alastar.minedonate.gui.ShopCategory;
import ru.alastar.minedonate.gui.ShopGUI;
import ru.alastar.minedonate.merch.info.ShopInfo;
import ru.log_inil.mc.minedonate.gui.DrawType;
import ru.log_inil.mc.minedonate.gui.GuiAbstractItemEntry;
import ru.log_inil.mc.minedonate.gui.GuiItemsScrollArea;
import ru.log_inil.mc.minedonate.gui.GuiScrollingList;
import ru.log_inil.mc.minedonate.gui.items.GuiItemEntryOfUserShopMerch;

public class UsersShopsCategory extends ShopCategory {

	int catId = 4 ;
	ShopCategory userSC ;
	public int selectedShop = 0 ;
	
    @Override
    public boolean getEnabled ( ) {
    	
        return ( userSC != null ? userSC . getEnabled ( ) : MineDonate . cfg . userShops ) ;
        
    }

    @Override
    public int getSourceCount ( int shopId ) {
        
    	return ( userSC != null ? userSC . getSourceCount ( shopId ) : MineDonate . shops . containsKey ( shopId ) ? MineDonate . shops . get ( shopId ) . cats [ catId ] . getMerch ( ) . length : 0 ) ;
        
    }

    @Override
    public String getName() {
        
    	return "Users shops";
        
    }

    ScaledResolution resolution;
    
    @Override
    public void draw(ShopGUI relative, int m_Page, int mouseX, int mouseY, float partialTicks, DrawType dt ) {
    	
        resolution = new ScaledResolution ( relative . mc, relative . mc . displayWidth, relative . mc . displayHeight ) ;// bull shit
        
        if ( userSC != null && userSC . getScrollList ( ) != null && dt != DrawType . BG ) {

        	userSC . getScrollList ( ) . drawScreen ( mouseX, mouseY, partialTicks, dt ) ; }
    	
        if ( ( userSC != null ? dt != DrawType . POST : true ) ) {
        	
        	gi . drawScreen ( mouseX, mouseY, partialTicks, dt ) ;
        	
        }
    	
    }
    
    @Override
    public void updateButtons ( ShopGUI relative, int m_Page ) {
    	
    	refreshGui ( ) ;
    	
    	if ( userSC != null ) {
    		
    		userSC . initGui (  ) ;
    	
    	}

    	updateReturnButton ( ) ;

    }

    @Override
    public void actionPerformed(GuiButton button) {
        
    	if ( userSC != null ) {
    		
    		userSC . actionPerformed ( button ) ;
    		
    		if ( button . id == ShopGUI . instance . returnButton . id ) {
    			
				ShopGUI . instance . currentShop = selectedShop = 0 ;

    			updateUserShopCategory ( null, true ) ;
        		//relative . returnButton . enabled = relative . returnButton . visible =ShopGUI .instance . displayReturnButton = false ;
    			
    		}
    		
    	}
        
    }

    // #LOG
    
	@Override
	public int getButtonWidth ( ) {
		
		return MineDonate.cfgUI.cats.usersShops.categoryButtonWidth;
		
	}
	
	@Override 
	public String getButtonText ( ) {
		
		return MineDonate.cfgUI.cats.usersShops.categoryButtonText ;
		
	}

	GuiItemsScrollArea gi ;
	List < GuiAbstractItemEntry > entrs = new ArrayList < > ( ) ;
	
	ShopInfo iim ;
	
	@Override
	public void initGui ( ) {
	
		if ( userSC != null ) {
			
			userSC . init ( gui ) ;
			userSC . initGui ( ) ;
			
		}
		
		updateReturnButton ( ) ;
		refreshGui ( ) ;
		
	}
	
	public void refreshGui ( ) {
		
		resolution = new ScaledResolution(gui.mc, gui.mc.displayWidth, gui.mc.displayHeight);// bull shit

		gi = new GuiItemsScrollArea ( resolution, gui, entrs, 0 ) ;
	
		for ( GuiAbstractItemEntry gie : entrs ) {

			gie . undraw ( ) ;
			
		}
		
		entrs . clear ( ) ;
		
		if ( userSC == null ) {
						
			if ( MineDonate . shops . containsKey ( gui . getCurrentShopId ( ) ) ) {
				
		    	if ( search ) {
		    		
		    		for ( int i = 0 ; i < MineDonate . shops . get ( gui . getCurrentShopId ( ) ) . cats [ catId ] . getMerch ( ) . length ; i ++ ) {
		        		
		        		iim = ( ShopInfo ) MineDonate . shops . get ( gui . getCurrentShopId ( ) ) . cats [ catId ] . getMerch ( ) [ i ] ; 
		        		
		        		if ( iim != null ) {

			        		if ( ( iim . isFreezed ? ! MineDonate . cfgUI . cats . usersShops . dontShowFreezed : true ) && ( iim . owner . toLowerCase ( ) . contains ( searchValue ) ||  iim . name . toLowerCase ( ) . contains ( searchValue ) ) ) {
			        			
			        			entrs . add ( new GuiItemEntryOfUserShopMerch ( iim, this ) . addButtons ( gui ) . updateDrawData ( ) ) ;
			        			
			        		}
			        		
		        		}
		        		
		        	} 
		        		
		    	} else {
		    		
		    		for ( int i = 0 ; i < MineDonate . shops . get ( gui . getCurrentShopId ( ) ) . cats [ catId ] . getMerch ( ) . length ; i ++ ) {
		        		
		        		iim = ( ShopInfo ) MineDonate . shops . get ( gui . getCurrentShopId ( ) ) . cats [ catId ] . getMerch ( ) [ i ] ; 
		        		
		        		if ( iim != null ) {
		        		
		        			if ( ( iim . isFreezed ? ! MineDonate . cfgUI . cats . usersShops . dontShowFreezed : true ) ) {
		        			
		        				entrs . add ( new GuiItemEntryOfUserShopMerch ( iim, this ) . addButtons ( gui ) . updateDrawData ( ) ) ;
		        			
		        			}
		        			
		        		}
		        		
		        	} 
		    		
		    	}
	    	}
		
		}
    	
    	gi . entrs = entrs ;
    	gi . applyScrollLimits ( ) ;
    	
	}
	
	public void updateUserShopCategory ( ShopCategory sc, boolean r ) {

		if ( userSC != null ) {
			
			userSC . undraw ( ) ;
			
		}
		
		userSC = sc ;
		if ( r ) { refreshGui ( ) ; }
		updateReturnButton ( ) ;
		
	}

	public void updateReturnButton ( ) {
		
		if ( userSC != null ) {

			ShopGUI . instance . returnButton . enabled =  ShopGUI . instance . returnButton . visible = ShopGUI . instance . displayReturnButton = true ;
			
		} else {

			ShopGUI . instance . returnButton . enabled =  ShopGUI . instance . returnButton . visible = ShopGUI . instance . displayReturnButton = false ;

		}
		
	}

	@Override
	public GuiScrollingList getScrollList ( ) {
		
		return gi;
		
	}
	
}

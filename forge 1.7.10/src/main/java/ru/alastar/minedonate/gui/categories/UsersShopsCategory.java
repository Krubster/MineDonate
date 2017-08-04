package ru.alastar.minedonate.gui.categories;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.ScaledResolution;
import ru.alastar.minedonate.MineDonate;
import ru.alastar.minedonate.gui.ShopCategory;
import ru.alastar.minedonate.gui.ShopGUI;
import ru.alastar.minedonate.merch.Merch;
import ru.alastar.minedonate.merch.info.ShopInfo;
import ru.log_inil.mc.minedonate.gui.DrawType;
import ru.log_inil.mc.minedonate.gui.GuiAbstractItemEntry;
import ru.log_inil.mc.minedonate.gui.GuiGradientButton;
import ru.log_inil.mc.minedonate.gui.GuiItemsScrollArea;
import ru.log_inil.mc.minedonate.gui.GuiScrollingList;
import ru.log_inil.mc.minedonate.gui.items.GuiItemEntryOfUserShopMerch;

public class UsersShopsCategory extends ShopCategory {

	public UsersShopsCategory ( ) {
		
		catId = 4 ;
		
	}
	
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

        	userSC . getScrollList ( ) . drawScreen ( mouseX, mouseY, partialTicks, dt ) ; 
        	
        }
    	
        if ( ( userSC != null ? dt != DrawType . POST : true ) ) {
        	
        	gi . drawScreen ( mouseX, mouseY, partialTicks, dt ) ;
        	
        }
    	
    }

    GuiGradientButton returnButton;
    GuiGradientButton viewMyShopsButton;
    GuiGradientButton createNewShopButton;

    public boolean displayReturnButton = false; 

    @Override
    public void updateButtons ( ShopGUI relative, int m_Page ) {
    	
        gui = relative ;
        
    	relative . getButtonList ( ) . add ( returnButton = new GuiGradientButton ( ShopGUI . getNextButtonId ( ), relative.exitButton.xPosition -  MineDonate . cfgUI . returnButton . width, relative . exitButton . yPosition, MineDonate . cfgUI . returnButton . width, MineDonate . cfgUI . returnButton . height, MineDonate . cfgUI . returnButton . text, false ) ) ;
        
    	relative . getButtonList ( ) . add ( viewMyShopsButton = new GuiGradientButton ( ShopGUI . getNextButtonId ( ), relative.exitButton.xPosition -  MineDonate . cfgUI . cats . shops . viewMyShopsButton . width, relative . exitButton . yPosition, MineDonate . cfgUI . cats . shops . viewMyShopsButton . width, MineDonate . cfgUI . cats . shops . viewMyShopsButton . height, MineDonate . cfgUI . cats . shops . viewMyShopsButton . text, false ) ) ;
    	relative . getButtonList ( ) . add ( createNewShopButton = new GuiGradientButton ( ShopGUI . getNextButtonId ( ), viewMyShopsButton.xPosition -  MineDonate . cfgUI . cats . shops . createNewShopButton . width, viewMyShopsButton . yPosition, MineDonate . cfgUI . cats . shops . createNewShopButton . width, MineDonate . cfgUI . cats . shops . createNewShopButton . height, MineDonate . cfgUI . cats . shops . createNewShopButton . text, false ) ) ;

    	refreshGui ( ) ;
    	
    	if ( userSC != null ) {
    		
    		userSC . initGui (  ) ;
    	
    	}

    	updateButtons ( ) ;

    }

    boolean viewMyShops = false ;
    
    @Override
    public void actionPerformed(GuiButton button) {
    	
    	if ( userSC != null ) {
    		
    		userSC . actionPerformed ( button ) ;
    		
    		if ( button . id == returnButton . id ) {
    			
    			gui . lockProcess ( ) ;

    			if ( viewMyShops ) {
    				
    				actionPerformed ( viewMyShopsButton ) ;
        			gui . updateBtns ( ) ;

    				return ;
    				
    			}
    			
				ShopGUI . instance . currentShop = selectedShop = 0 ;

    			updateUserShopCategory ( null, true ) ;
    			gui . updateBtns ( ) ;
    			
        		//relative . returnButton . enabled = relative . returnButton . visible =ShopGUI .instance . displayReturnButton = false ;
    			return ;
    			
			}
    		
    	}

		if ( button . id == this . viewMyShopsButton . id ) {

			gui . lockProcess ( ) ;
			
			if ( ! viewMyShops ) {

				super . search ( Minecraft . getMinecraft ( ) . thePlayer . getDisplayName ( ) ) ;
				
			} else {
				
				super . search ( null ) ;
				
			}
			
			viewMyShops = ! viewMyShops ;

			gui . updateBtns ( ) ;

    	}
        
		if ( button . id == this . createNewShopButton . id ) {

			gui . lockProcess ( ) ;
			

			gui . updateBtns ( ) ;

    	}
        
    }

    // #LOG
    
	@Override
	public int getButtonWidth ( ) {
		
		return MineDonate.cfgUI.cats.shops.categoryButtonWidth;
		
	}
	
	@Override 
	public String getButtonText ( ) {
		
		return MineDonate.cfgUI.cats.shops.categoryButtonText ;
		
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
		
		if ( subCatId == -1 ) {
			
			setSubCategory ( subCatId ) ;
			
		}
		
		updateButtons ( ) ;
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
		    		
		    		for ( Merch m : noSearchedEntries ) {
		        		
		        		iim = ( ShopInfo ) m ; 
		        		
		        		if ( iim != null ) {

			        		if ( ( iim . isFreezed ? ! MineDonate . cfgUI . cats . shops . dontShowFreezed : true ) && ( viewMyShops ? ( iim . owner . equalsIgnoreCase ( searchValue ) ) : ( iim . owner . toLowerCase ( ) . contains ( searchValue ) ||  iim . name . toLowerCase ( ) . contains ( searchValue ) ) ) ) {
			        			
			        			entrs . add ( new GuiItemEntryOfUserShopMerch ( iim, this ) . addButtons ( gui ) . updateDrawData ( ) ) ;
			        			
			        		}
			        		
		        		}
		        		
		        	} 
		        		
		    	} else {
		    		
		    		for ( Merch m : noSearchedEntries ) {
		        		
		        		iim = ( ShopInfo ) m ; 
		        		
		        		if ( iim != null ) {
		        		
		        			if ( ( iim . isFreezed ? ! MineDonate . cfgUI . cats . shops . dontShowFreezed : true ) ) {
		        			
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
		updateButtons ( ) ;
		
	}

    public void updateButtons ( ) {
    	
		if ( userSC != null ) {

			returnButton . enabled = returnButton . visible = displayReturnButton = true ;
			
		} else {

			returnButton . enabled = returnButton . visible = displayReturnButton = false ;

		}
		
		if ( displayReturnButton ) {
			
			viewMyShopsButton . yPosition = -100 ;
			createNewShopButton . yPosition = -100 ;
			
		} else {

			viewMyShopsButton . xPosition = gui . exitButton . xPosition - viewMyShopsButton . width ;
			viewMyShopsButton . yPosition = gui . exitButton . yPosition ;
			createNewShopButton . xPosition = viewMyShopsButton . xPosition - viewMyShopsButton . width ;
			createNewShopButton . yPosition = viewMyShopsButton . yPosition ;


		}
		
    }
    
	@Override
	public GuiScrollingList getScrollList ( ) {
		
		return gi;
		
	}
	
	@Override
	public void search ( String s ) {
		
		viewMyShops = false ;
		
		super . search( s ) ;
		
	}
	
}

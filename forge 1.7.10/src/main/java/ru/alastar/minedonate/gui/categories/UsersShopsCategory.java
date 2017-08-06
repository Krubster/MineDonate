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
import ru.alastar.minedonate.network.packets.CreateNewShopPacket;
import ru.alastar.minedonate.network.packets.NeedShopCategoryPacket;
import ru.log_inil.mc.minedonate.gui.DrawType;
import ru.log_inil.mc.minedonate.gui.GuiAbstractItemEntry;
import ru.log_inil.mc.minedonate.gui.GuiGradientButton;
import ru.log_inil.mc.minedonate.gui.GuiGradientTextField;
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
    		
    		userSC . postShow (  ) ;
    	
    	}

    	updateButtons ( ) ;

    }

    boolean viewMyShops = false ;
    boolean createNewShop = false ;
    boolean createNewShopFirst = true ;

    @Override
    public void actionPerformed(GuiButton button) {

    	if ( userSC != null ) {
    		
    		userSC . actionPerformed ( button ) ;
    		
    		if ( button . id == returnButton . id ) {
    			
    			gui . lockProcess ( ) ;

    			if ( viewMyShops ) {
    				
    				actionPerformed ( viewMyShopsButton ) ;
    				
        			gui . updateBtns ( ) ;
        			updateButtons ( ) ;

    				return ;
    				
    			}
    			
    			if ( createNewShop ) {
    			
    				gui . listTextFields . remove ( this . newShopNameField ) ;
    				createNewShop = false ;
    				
        			gui . updateBtns ( ) ;
        			updateButtons ( ) ;

    				return ;
    				
    			}
    			
				ShopGUI . instance . currentShop = selectedShop = 0 ;

    			updateUserShopCategory ( null, true ) ;
    			gui . updateBtns ( ) ;
    			updateButtons ( ) ;

        		//relative . returnButton . enabled = relative . returnButton . visible =ShopGUI .instance . displayReturnButton = false ;
    			return ;
    			
			}
    		
    	}
    	
		if ( button . id == returnButton . id ) {

			if ( createNewShop ) {
				
				gui . listTextFields . remove ( this . newShopNameField ) ;
				createNewShop = false ;
				
				gui . updateBtns ( ) ;
				updateButtons ( ) ;

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
			updateButtons ( ) ;
			
    	}
        
		if ( button . id == this . createNewShopButton . id ) {

			gui . lockProcess ( ) ;

			if ( createNewShop && ! createNewShopFirst ) {

				gui . loading = true ;
                MineDonate . networkChannel . sendToServer ( new CreateNewShopPacket ( this . newShopNameField . getText ( ) ) ) ;

			}
			
			createNewShop = ! createNewShop ;
			createNewShopFirst = false ;

			gui . updateBtns ( ) ;
			updateButtons ( ) ;

    	}
        
    }
    
	@Override
	public int getButtonWidth ( ) {
		
		return MineDonate.cfgUI.cats.shops.categoryButtonWidth;
		
	}
	
	@Override 
	public String getButtonText ( ) {
		
		return MineDonate.cfgUI.cats.shops.categoryButtonText ;
		
	}

	GuiItemsScrollArea gi ;
	
	ShopInfo iim ;
	
	@Override
	public void postShow ( ) {
	
		if ( userSC != null ) {
			
			userSC . preShow ( gui ) ;
			userSC . postShow ( ) ;
			
		}
		
		if ( subCatId == -1 ) {
			
			setSubCategory ( subCatId ) ;
			
		}
		
		updateButtons ( ) ;
		refreshGui ( ) ;
		
		super . postShow ( ) ;

	}
	
	GuiGradientTextField newShopNameField ;
	
	public void refreshGui ( ) {
		
		resolution = new ScaledResolution(gui.mc, gui.mc.displayWidth, gui.mc.displayHeight);// bull shit

		gi = new GuiItemsScrollArea ( resolution, gui, entrs, 0 ) ;
	
		for ( GuiAbstractItemEntry gie : entrs ) {

			gie . unDraw ( ) ;
			
		}
		
		entrs . clear ( ) ;
		
		if ( createNewShop ) {
			
			if ( newShopNameField == null ) {
				
				newShopNameField = new GuiGradientTextField ( gui . getFontRenderer ( ), 0, 0, MineDonate . cfgUI . cats.shops.createNewShopNameField . width, MineDonate . cfgUI . cats.shops.createNewShopNameField . height, true ) ;
				newShopNameField . setTextHolder ( MineDonate . cfgUI . cats . shops . createNewShopNameField . textHolder ) ;
				newShopNameField . setMaxStringLength ( 120 ) ;

			}
			
			newShopNameField . xPosition = ( resolution . getScaledWidth ( ) ) - 30 - MineDonate.cfgUI.cats.shops.createNewShopNameField.width ;
			newShopNameField . yPosition = ( int ) ( ( resolution . getScaledHeight ( ) ) - ( resolution . getScaledHeight ( ) * 0.1 ) ) - 5 - MineDonate . cfgUI . cats.shops.createNewShopNameField . height ;
			
			if ( ! gui . listTextFields . contains ( newShopNameField ) ) {
				
				gui . listTextFields . add ( newShopNameField ) ;
				
			}

		} else if ( userSC == null ) {
						
			if ( MineDonate . shops . containsKey ( gui . getCurrentShopId ( ) ) ) {
				
		    	if ( search ) {
		    		
		    		for ( Merch m : noSearchedEntries ) {
		        		
		        		iim = ( ShopInfo ) m ; 
		        		
		        		if ( iim != null ) {

			        		if ( ( iim . isFreezed ? ! MineDonate . cfgUI . cats . shops . dontShowFreezed : true ) && ( viewMyShops ? ( iim . owner . equalsIgnoreCase ( searchValue ) ) : ( iim . owner . toLowerCase ( ) . contains ( searchValue ) ||  iim . getSearchValue ( ) . toLowerCase ( ) . contains ( searchValue ) ) ) ) {
			        			
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
	
    @Override
    public void unShow ( ) {
    	
    	super . unShow ( ) ;
    	
		gui . listTextFields . remove ( this . newShopNameField ) ;
		createNewShop = false ;
		
    }
    
	public void updateUserShopCategory ( ShopCategory sc, boolean r ) {

		if ( userSC != null ) {
			
			userSC . unDraw ( ) ;
			
		}

		userSC = sc ;
		if ( r ) { refreshGui ( ) ; }
		updateButtons ( ) ;
		
	}

    public void updateButtons ( ) {
    	
		if ( createNewShop ) {
			
			returnButton . enabled = returnButton . visible = displayReturnButton = true ;
			viewMyShopsButton . yPosition = -100 ;
			createNewShopButton . xPosition = returnButton . xPosition - createNewShopButton . width ;
			createNewShopButton . yPosition = returnButton . yPosition ;			
			
		} else {
			
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
				createNewShopButton . xPosition = viewMyShopsButton . xPosition - createNewShopButton . width ;
				createNewShopButton . yPosition = viewMyShopsButton . yPosition ;

			}
			
		}

		viewMyShopsButton . pressed = viewMyShops ;
		
    }
    
	@Override
	public GuiScrollingList getScrollList ( ) {
		
		return gi;
		
	}
	
	@Override
	public void search ( String s ) {
		
		viewMyShops = false ;
		
		super . search ( s ) ;
		
		if ( userSC != null ) {
			
			userSC . search ( s ) ;
			
		}
		
	}
	
}

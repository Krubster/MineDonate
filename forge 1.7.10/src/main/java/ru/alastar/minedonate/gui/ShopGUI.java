package ru.alastar.minedonate.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.item.ItemStack;

import ru.alastar.minedonate.MineDonate;
import ru.alastar.minedonate.gui.categories.*;
import ru.alastar.minedonate.proxies.ClientProxy;
import ru.alastar.minedonate.rtnl.ModNetwork;

import ru.log_inil.mc.minedonate.gui.*;
import ru.log_inil.mc.minedonate.gui.context.ContextMenuManager;
import ru.log_inil.mc.minedonate.gui.frames.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Alastar on 18.07.2017.
 */
public class ShopGUI extends MCGuiAccessible {

    public static ShopGUI instance ;
    
    public boolean can_process = true ;

    public boolean needNetUpdate = true ;
    public boolean loading = false ;
    public boolean dbgFlag = false ;
    public boolean confirmFlag = false ;

    private static int buttonLastId = 3 ;
    
    public static int m_Page = 0 ;
    
    public int currentShop = 0 ;
    public int m_Selected_Category = 0;	
    public int defaultCategory = 0 ;

    public int lastCategory = -1 ;
    
    private ShopCategory[] cats ;
    
    public List < GuiGradientTextField > listTextFields = new ArrayList < > ( ) ;
    public List < GuiButton > noHidenButtonByGLScissor = new ArrayList < > ( ) ;

    public ScaledResolution resolution ;

    public GuiMoneyArea moneyArea ;
    public GuiGradientTextField searchField ;
    public PreviousButton pb ;
    public NextButton nb ;
    public GuiGradientButton exitButton ;
    public GuiGradientButton searchButton ;
    
    public Map < String, GuiEntry > gEntries = new LinkedHashMap < > ( ) ;
    GuiEntry lastEntry ;
    
    GuiFrameLoading gfl ;
    
    public ShopGUI ( ) {

    	cats = new ShopCategory [ ] { new ItemNBlockCategory ( ), new PrivilegieCategory ( ), new RegionsCategory ( ), new EntitiesCategory ( ), new UsersShopsCategory ( ) } ;
    
    	for ( ShopCategory sc: cats ) {
    		
    		sc . preShow ( this ) ;
    		
    	}
    	
    	//gEntries . put ( "itemRename", new GuiFrameItemRename ( MineDonate . cfgUI . frames . rename ) ) ;
    	gEntries . put ( "createShop", new GuiFrameCreateShop ( MineDonate . cfgUI . frames . createShop ) ) ;
    	gEntries . put ( "renameShop", new GuiFrameRenameShop ( MineDonate . cfgUI . frames . renameShop ) ) ;
    	gEntries . put ( "deleteShop", new GuiFrameDeleteShop ( MineDonate . cfgUI . frames . deleteShop ) ) ;
    	gEntries . put ( "freezeShop", new GuiFrameFreezeShop ( MineDonate . cfgUI . frames . freezeShop ) ) ;

    	gEntries . put ( "renameItem", new GuiFrameRenameItem ( MineDonate . cfgUI . frames . renameItem ) ) ;
    	gEntries . put ( "deleteItem", new GuiFrameDeleteItem ( MineDonate . cfgUI . frames . deleteItem ) ) ;
    	
    	gEntries . put ( "renameEntity", new GuiFrameRenameEntity ( MineDonate . cfgUI . frames . renameEntity ) ) ;
    	gEntries . put ( "deleteEntity", new GuiFrameDeleteEntity ( MineDonate . cfgUI . frames . deleteEntity ) ) ;
    	
    	gEntries . put ( "loading", ( gfl = new GuiFrameLoading ( ) ) ) ;
    	gfl . setText ( MineDonate . cfgUI . loadingText ) ;
    	
    }

    public static int getNextButtonId ( ) {
        
    	return buttonLastId ++ ;
        
    }

    public GuiEntry showEntry ( String k, boolean v ) {

    	if ( gEntries . containsKey ( k ) ) {
    		
    		if ( v && lastEntry != null ) {
    			
    			lastEntry . show ( false ) ;
    			lastEntry . unShow ( this ) ;

    			if ( lastEntry . needReloadOnUnShow ( ) ) {
    				
    				initGui ( ) ;
    				
    			} 
    			
    		}
    		
    		gEntries . get ( k ) . show ( v ) ;
    		gEntries . get ( k ) . postShow ( this ) ;
    			
    		if ( v ) {
    			
    			lastEntry = gEntries . get ( k ) ;
    			
    		}
    		/*
    		if ( reload ) {
    		
    			initGui ( ) ;
    		
    		}*/
    		
    		return gEntries . get ( k ) ;
    		
    	}
    	
    	return null ;
    	
    }
    
    public ShopCategory [ ] getCurrentShopCategories ( ) { // #LOG
    	
    	return cats ;
    	
    }
    
    public ShopCategory getCurrentCategory ( ) {
    	
    	return cats [ m_Selected_Category ] ;
    	
    }
    
    public int getCurrentShopId ( ) {
    	
    	return currentShop ;
    	
    }
    
    public void lockProcess ( ) {
    	
    	can_process = false ;
    	
    }
    
	public void setLoading ( boolean _loading ) {
		
		loading = _loading ;

		showEntry ( "loading", loading ) ;
    	
	}
    
	public boolean isLoading ( ) {
		
		return loading ;
		
	}
	
    @Override
    protected void keyTyped ( char p_73869_1_, int p_73869_2_ ) {

        for ( ru.log_inil.mc.minedonate.gui.GuiEntry ge : gEntries . values ( ) ) {

        	if ( ge . isVisible ( ) && ge . onKey ( p_73869_1_, p_73869_2_ ) ) {
        		
        		return ;
        		
        	}
        	
        }
           
    	if ( searchField != null && searchField . isFocused ( ) ) {
    		
    		searchField . textboxKeyTyped ( p_73869_1_, p_73869_2_ ) ;		

            //updateGrid ( ) ;
            //updateBtns ( ) ;
			
            if ( "!DBGE" . equals ( searchField . getText ( ) ) ) {
            	
            	dbgFlag = ! dbgFlag ;
            	
            	searchField . setText ( "" ) ;
            	
            }
            
            if ( "!CNFRM" . equals ( searchField . getText ( ) ) ) {
            	
            	confirmFlag = ! confirmFlag ;
            	
            	searchField . setText ( "" ) ;
            	
            }
            
			getCurrentCategory ( ) . search ( searchField . getText ( ) ) ;
			
			getCurrentCategory ( ) . unShow ( this ) ;
			getCurrentCategory ( ) . preShow ( this ) ;
			getCurrentCategory ( ) . postShow ( this ) ;
			
    		return ;
    		
    	}
    	
  		for ( GuiGradientTextField ggtf: listTextFields ) {
			
  			if ( ggtf . isFocused ( ) ) {
			
  				ggtf . textboxKeyTyped ( p_73869_1_, p_73869_2_ ) ;		
  				
			}
  			
		}

  		if ( dbgFlag && p_73869_2_ == 63 ) { 
    		
    		MineDonate . loadClientConfig ( ) ;
    		initGui ( ) ;
    		
    	}

    		
      	// 32 205 -> d
    	// 30 203 <- a
    	
    	if ( ( 30 == p_73869_2_  || 203 == p_73869_2_ ) && pb . enabled ) {

    		actionPerformed ( pb ) ;
    		
    	} else if ( ( 32 == p_73869_2_  || 205 == p_73869_2_ ) && nb . enabled ) {

    		actionPerformed ( nb ) ;

    	} else if ( ClientProxy . openShop . getKeyCode ( ) == p_73869_2_ ) {
    		
    		Minecraft . getMinecraft ( ) . displayGuiScreen ( null ) ;
    		
    	} else {
    		
    		super . keyTyped ( p_73869_1_, p_73869_2_ ) ;
    		
    	}
    	
    }
    
    public boolean contextMenuClickCallFlag = true ;
    
    @Override
    protected void mouseClicked ( int p_73864_1_, int p_73864_2_, int p_73864_3_ ) {
    	
    	if ( dbgFlag ) {

    		System . err . println ( "Mouse click: x" + p_73864_1_ + ", y" + p_73864_2_ + ", k" + p_73864_3_ ) ;
    		
    	}
    	
    	contextMenuClickCallFlag = true ;
    	
        for ( ru.log_inil.mc.minedonate.gui.GuiEntry ge : gEntries . values ( ) ) {

        	if ( ge . isVisible ( ) && ge . coordContains ( p_73864_1_,  p_73864_2_ ) ) {
        			
        		if ( ge . onClick ( p_73864_1_,  p_73864_2_, p_73864_3_ ) ) {
        			
        			return ;
        			
        		}
        		
        		if ( ge . lockContextMenuUnderEntry ( ) ) {
        			
        			contextMenuClickCallFlag = false ;
        			
        		}
        		
        	} else if ( ge . isVisible ( ) && ge . lockContextMenuUnderEntry ( ) ) {
        	
        		contextMenuClickCallFlag = false ;

        	}
        	
        }
          
        if ( contextMenuClickCallFlag ) {

        	if ( ContextMenuManager . click ( this, p_73864_1_, p_73864_2_, p_73864_3_ ) ) {
        		
        		return ;
        		
        	}
        	
        }
    	
  		for ( GuiGradientTextField ggtf: listTextFields ) {
			
  			ggtf . mouseClicked ( p_73864_1_, p_73864_2_, p_73864_3_ ) ;		

		}
  			
    	super . mouseClicked ( p_73864_1_, p_73864_2_, p_73864_3_ ) ;
    	
    }

    @Override
    protected void actionPerformed(GuiButton button) {

    	if ( dbgFlag ) {
    		
    		System . err . println ( "" ) ;
    		
    	}
    	
        for ( ru.log_inil.mc.minedonate.gui.GuiEntry ge : gEntries . values ( ) ) {
        	
        	if ( ge . isOwnerButton ( button ) ) {
    			
        		if ( ge . actionPerformed ( this, button ) ) {
        			
        			return ;
        			
        		}
        		
    		} else if ( ge . lockButtonsUnderEntry ( ) ) {
        		
    			return ; 
        		
        	}
        	
        }
        
    	if (button.id == 0) {
        	
            button.enabled = false;
            this.mc.thePlayer.closeScreen();
            
        } 

        if(can_process) {
          
        	can_process = false ;

            if (button instanceof PreviousButton) {
            	
                button.enabled = false;
                m_Page = m_Page - 1;
                updateGrid ( ) ;
                updateButtons ( true ) ;
                
            } else if (button instanceof NextButton) {

                button.enabled = false;
                m_Page = m_Page + 1;
                updateGrid ( ) ;
                updateButtons ( true ) ;
                
            } else if (button instanceof BuyButton ) {
                
            	((BuyButton) button) . buy ( ) ;
            	
            } else if (button instanceof CategoryButton) {
            
            	if ( currentShop != 0 ) {
            
                    if ( cats [ m_Selected_Category ] instanceof UsersShopsCategory ) {
                    	
                    	( ( UsersShopsCategory ) cats [ m_Selected_Category ] ) . selectedShop = 0 ;
                    	( ( UsersShopsCategory ) cats [ m_Selected_Category ] ) . updateUserShopCategory ( this, null, false ) ;
                   
                    }
                                		
            	}

            	if ( lastCategory != -1 ) {
            		
            		cats [ lastCategory ] . unShow ( this ) ;  
            		
            	}

            	currentShop = 0 ;
                m_Page = 0;
                lastCategory = m_Selected_Category = ((CategoryButton) button).getCategory();

                ModNetwork . sendToServerNeedShopCategoryPacket ( getCurrentShopId ( ), m_Selected_Category ) ;

                loading = true ;
                
                resolution = new ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight);

            	if ( searchField != null ) {
            	
        			getCurrentCategory ( ) . search ( searchField . getText ( ) ) ;
        			            		
            	}
            	
                updateGrid ( ) ;
                updateButtons ( true ) ;
                
            } else if ( button instanceof GoButton ) {
            	
            	// currentShop = ( ( GoButton ) button ) . shopId ;
            	( ( UsersShopsCategory ) getCurrentCategory ( ) ) . selectedShop = ( ( GoButton ) button ) . shopId ;
                ModNetwork . sendToServerNeedShopCategoryPacket ( ( ( GoButton ) button ) . shopId, 0 ) ;

                loading = true ;
                
              //  returnButton . enabled = returnButton . visible = displayReturnButton = true ;

            } else if ( button instanceof GuiGradientButton ) {
            	
            	if ( button . id == searchButton . id ) {

            		if ( searchField != null ) {
            			
                    	if ( searchField . getVisible ( ) ) { 
                    		
                    		searchField . setVisible ( false ) ;
                        	searchField . setEnabled ( false ) ;

                			getCurrentCategory ( ) . search ( null ) ;
                			
                  			getCurrentCategory ( ) . unShow ( this ) ;
                			getCurrentCategory ( ) . preShow ( this ) ;
                			getCurrentCategory ( ) . postShow ( this ) ;
                			
                            updateGrid ( ) ;       
                			
                     //       updateBtns ( ) ;
                            
                    	} else {
                    		
                    		searchField . setVisible ( true ) ;
                        	searchField . setEnabled ( true ) ;
                        	searchField . setFocused ( true ) ;
                        	
                			getCurrentCategory ( ) . search ( searchField . getText ( ) ) ;
                			
                  			getCurrentCategory ( ) . unShow ( this ) ;
                			getCurrentCategory ( ) . preShow ( this ) ;
                			getCurrentCategory ( ) . postShow ( this ) ;
                			
                            updateGrid ( ) ;
                     //       updateBtns ( ) ;
                            
                    	}
                    	       
            		}
            		
            	}
            	
            }
            
            getCurrentCategory ( ) . actionPerformed ( this, button ) ;
            
        }
        
    }
    
    @Override
    public void drawScreen ( int mouseX, int mouseY, float partialTicks ) {    	
    
    	this . drawRect ( 0, 0, resolution . getScaledWidth ( ), resolution . getScaledHeight ( ), 1258291200 ) ;

        if ( ! needNetUpdate ) {
        	
        	moneyArea . drawBalanceArea ( ( int ) resolution . getScaledWidth ( ) - 20, ( int ) ( resolution . getScaledHeight ( ) * 0.1 + 25 ), mouseX, mouseY ) ;
        	
	        if ( ! loading ) { 
	        	
	        	can_process = true; 
	        	
	        	getCurrentCategory ( ) . draw ( this, m_Page, mouseX, mouseY, partialTicks, DrawType . BG ) ;   
  
		        super . drawScreen ( mouseX, mouseY, partialTicks ) ;  
		        
	            getCurrentCategory ( ) . draw ( this, m_Page, mouseX, mouseY, partialTicks, DrawType . PRE ) ;    		

		  		for ( GuiGradientTextField ggtf: listTextFields ) {
					
		  			ggtf . drawTextBox ( ) ;		

				}
		  		
		        getCurrentCategory ( ) . draw ( this, m_Page, mouseX, mouseY, partialTicks, DrawType . POST ) ;   
		        getCurrentCategory ( ) . draw ( this, m_Page, mouseX, mouseY, partialTicks, DrawType . OVERLAY ) ;   

		        //
	    	
	        } else { 
	        	
	        	can_process = false ; 
	            
		    	getCurrentCategory ( ) . draw ( this, m_Page, mouseX, mouseY, partialTicks, DrawType . BG ) ;   

		        super . drawScreen ( mouseX, mouseY, partialTicks ) ;  
		      
		    	getCurrentCategory ( ) . draw ( this, m_Page, mouseX, mouseY, partialTicks, DrawType . POST ) ;   
		    	
		        getCurrentCategory ( ) . draw ( this, m_Page, mouseX, mouseY, partialTicks, DrawType . OVERLAY ) ;   

	        }
	        
	    } else {
	    	
	    	if ( loading ) {
	    		
		        can_process = false;
	

	    	}
	    	
	    }

        ContextMenuManager . draw ( this, mouseX, mouseY ) ;
        
        if ( dbgFlag ) {
        	
        	ContextMenuManager . drawDebug ( this, mouseX, mouseY ) ;
        	
        }

        for ( ru.log_inil.mc.minedonate.gui.GuiEntry ge : gEntries . values ( ) ) {

        	if ( ge . isVisible ( ) ) {
        		
        		ge . draw ( this, 0, mouseX, mouseY, partialTicks, DrawType . POST ) ;
        		
        	}
        	
        }
          
    }

    public void drawHoveringText(ArrayList list, int mouseX, int mouseY, FontRenderer fontRenderer) {
        super.drawHoveringText(list, mouseX, mouseY, fontRenderer);
    }

    @Override
    public void initGui() {

    	super.initGui();
        instance = this ;
        
        resolution = new ScaledResolution ( this . mc, this . mc . displayWidth, this . mc . displayHeight ) ;
        
        m_Page = 0 ;
        
		getCurrentCategory ( ) . unShow ( this ) ;

        updateGrid ( ) ;
        updateButtons ( false ) ;

        getCurrentCategory ( ) . postShow ( this ) ;

        //getCurrentCategory ( ) . updateButtons ( this, m_Page ) ;

        //
        
		if ( moneyArea == null ) {
			
			moneyArea = new GuiMoneyArea ( ) ;
			
		}
		
		moneyArea . initGui ( this ) ;
		
		//
        
		if ( needNetUpdate && ! loading ) {

            ModNetwork . sendToServerNeedUpdatePacket ( 0 ) ;

            loading = true ;
            
        }

        for ( ru.log_inil.mc.minedonate.gui.GuiEntry ge : gEntries . values ( ) ) {

        	if ( ge . isVisible ( ) ) {
        		
        		ge . postShow ( this ) ;
        		
        	}
        	
        }
                
    }

    @Override
    public void onGuiClosed ( ) {

    	ContextMenuManager . clean ( ) ;

    }
    
    private void addCategories ( ) {

        int posX = 30 ;
        
        for (int i = 0; i < getCurrentShopCategories ( ) . length ; ++i) {

        	if ( getCurrentShopCategories ( ) [ i ] . getEnabled ( ) ) { //#LOG
        		
	            CategoryButton btn = new CategoryButton ( i, getNextButtonId ( ), posX, (int) (resolution.getScaledHeight() * 0.1) + 19, getCurrentShopCategories ( ) [i].getButtonText() ) ;

	            btn . width = getCurrentShopCategories ( ) [ i ] . getButtonWidth ( ) ; // 75
	            this . addButton ( btn, true ) ;
	      
	            posX += btn.width ;
	            
	            if ( i == m_Selected_Category ) { // #LOG
	            	
	            	btn . enabled = false ;
	            	
	            } else {
	            	
	            	btn . enabled = true ;
	            	
	            }
	            
        	}
        	
        }
    }

    public void addButton ( GuiButton b, boolean noHide ) {
    	        
    	this . buttonList . add ( b ) ;
    	
        if ( noHide ) {
        	
        	noHidenButtonByGLScissor . add ( b ) ;
        	
        }
        
    }
    
    public void removeButton ( GuiButton but ) {
    	
    	this . buttonList . remove ( but ) ;
    	this . noHidenButtonByGLScissor . remove ( but ) ;
    	
    }

    public void updateButtons ( boolean updateInCat ) {

    	buttonList.clear();
        noHidenButtonByGLScissor.clear();
        addCategories();

        if ( MineDonate . cfgUI . addSearchButton ){
        
        	buttonList . add ( searchButton = new GuiGradientButton ( ShopGUI . getNextButtonId ( ), 30, ( int ) ( ( resolution . getScaledHeight ( ) ) - ( resolution . getScaledHeight ( ) * 0.1 ) ) - 5, MineDonate . cfgUI . searchButton . width, MineDonate . cfgUI . searchButton . height, MineDonate . cfgUI . searchButton . text, false ) ) ;
            noHidenButtonByGLScissor . add ( searchButton ) ;

        	if ( searchField == null ) {
        		
        		listTextFields . add ( searchField = new GuiGradientTextField ( this . fontRendererObj, 30 + MineDonate.cfgUI.searchButton.width, (int) ( (resolution.getScaledHeight()) - (resolution.getScaledHeight() * 0.1) )-5, MineDonate . cfgUI . searchField . width, MineDonate . cfgUI . searchField . height, true ) ) ;

        		searchField . setText ( MineDonate . cfgUI . searchField . text ) ;
        		searchField . setTextHolder ( MineDonate . cfgUI . searchField . textHolder ) ;
        		
        		searchField . setVisible ( false ) ;
            	searchField . setEnabled ( false ) ;
            	
        	}

        	searchField . xPosition = 30 + MineDonate.cfgUI.searchButton.width ;
    		searchField . yPosition = (int) ( (resolution.getScaledHeight()) - (resolution.getScaledHeight() * 0.1) ) - 5 ;
        	
        }
        
        buttonList . add ( exitButton = new GuiGradientButton ( 0, ( int ) ( resolution . getScaledWidth ( ) - 30 ) - MineDonate . cfgUI . exitButton . width, ( int ) ( ( resolution . getScaledHeight ( ) ) - ( resolution . getScaledHeight ( ) * 0.1 ) - 5 ), MineDonate . cfgUI . exitButton . width, MineDonate . cfgUI . exitButton . height, MineDonate . cfgUI . exitButton . text, false ) ) ;
        noHidenButtonByGLScissor . add ( exitButton ) ;
        // buttonList . add ( returnButton = new GuiGradientButton ( ShopGUI . getNextButtonId ( ), exitButton.xPosition -  MineDonate . cfgUI . returnButton . width, exitButton . yPosition, MineDonate . cfgUI . returnButton . width, MineDonate . cfgUI . returnButton . height, MineDonate . cfgUI . returnButton . text, false ) ) ;
        
        buttonList . add ( pb = new PreviousButton ( ShopGUI . getNextButtonId ( ), ( int ) ( resolution . getScaledWidth ( ) * 0.5 ) - 20 - 2 , ( int ) ( ( resolution . getScaledHeight ( ) ) - ( resolution . getScaledHeight ( ) * 0.1 ) ) - 20 - 10, 20, 20, "<" ) ) ;
        noHidenButtonByGLScissor . add ( pb ) ;

        buttonList . add ( nb = new NextButton ( ShopGUI . getNextButtonId ( ), pb . xPosition + pb . width + 4, pb . yPosition, 20, 20, ">" ) ) ;
        noHidenButtonByGLScissor . add ( nb ) ;

        if ( getCurrentCategory ( ) . elementsOnPage ( ) != 0 && getCurrentCategory ( ) . getSourceCount ( getCurrentShopId ( ) ) > getCurrentCategory ( ) . elementsOnPage ( ) ) {

            pb . enabled = ( m_Page > 0) ;

            nb . enabled = getCurrentCategory ( ) . elementsOnPage ( ) > 0 && m_Page < (int) Math.ceil(getCurrentCategory ( ).getSourceCount(getCurrentShopId()) / (getCurrentCategory ( ) . elementsOnPage ( ) ) ) ;

        } else {
        	
        	pb . enabled = nb . enabled = false ;
        	pb . visible = nb . visible = false ;

        }
        
        if ( updateInCat ) {
        	
        	getCurrentCategory ( ) . updateButtons ( this, m_Page ) ;
        	
        }
        
        for ( ru.log_inil.mc.minedonate.gui.GuiEntry ge : gEntries . values ( ) ) {

        	if ( ge . isVisible ( ) ) {
        		
        		ge . postShow ( this ) ;
        		
        	}
        	
        }
        
    }

    int tmpH ;

    public void updateGrid ( ) {
			
		int tmpW ;

		if ( getCurrentCategory ( ) . getItemWidth ( ) > 0 ) {
    		
    		tmpH = resolution . getScaledHeight ( ) - 50 - 25 ;
    		
    		tmpW = resolution . getScaledWidth ( ) - 50 - 50 ;
    		
    		getCurrentCategory ( ) . setColCount ( tmpW / getCurrentCategory ( ) . getItemWidth ( ) ) ;
    		
    		getCurrentCategory ( ) . setRowCount ( tmpH / getCurrentCategory ( ) . getItemHeight ( ) ) ;
    		    		
    	}
    	
    }
    
    public FontRenderer getFontRenderer() {
        return this.fontRendererObj;
    }

    public RenderItem getItemRender() {
        return this.itemRender;
    }

    public void drawGradientRectAccess(int par1, int par2, int par3, int par4, int par5, int par6) {
    	drawGradientRect(par1, par2, par3, par4, par5, par6) ;
    }
    
    
    public void renderToolTipAccess(ItemStack p_146285_1_, int p_146285_2_, int p_146285_3_){
    	renderToolTip(p_146285_1_, p_146285_2_, p_146285_3_);
    }
    
	public void drawTexturedModalRectNormal(int x, int y, int width, int height) {
		
	     Tessellator tessellator = Tessellator.instance;
	     tessellator.startDrawingQuads();    
	     tessellator.addVertexWithUV(x        , y + height, 0, 0.0, 1.0);
	     tessellator.addVertexWithUV(x + width, y + height, 0, 1.0, 1.0);
	     tessellator.addVertexWithUV(x + width, y         , 0, 1.0, 0.0);
	     tessellator.addVertexWithUV(x        , y         , 0, 0.0, 0.0);
	     tessellator.draw();
	     
	}

	public List getButtonList ( ) {
		
		return this . buttonList ;
		
	}

	public ScaledResolution getScaledResolution ( ) {

		return resolution ;

	}

	public void drawHoveringTextAccess(List<String> list, int mouseX, int mouseY, FontRenderer fontRenderer) {
		this.drawHoveringText(list, mouseX, mouseY, fontRenderer);
	}
	
    @Override
    public boolean doesGuiPauseGame ( ) {
        
    	return false ;
        
    }

	@Override
	public void drawDefaultBackground ( ) {
		
	}
	
}
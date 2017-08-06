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
import ru.alastar.minedonate.network.packets.NeedShopCategoryPacket;
import ru.alastar.minedonate.network.packets.NeedUpdatePacket;
import ru.alastar.minedonate.proxies.ClientProxy;
import ru.log_inil.mc.minedonate.gui.DrawType;
import ru.log_inil.mc.minedonate.gui.GuiGradientButton;
import ru.log_inil.mc.minedonate.gui.GuiGradientTextField;
import ru.log_inil.mc.minedonate.gui.GuiMoneyArea;
import ru.log_inil.mc.minedonate.gui.MCGuiAccessable;
import ru.log_inil.mc.minedonate.gui.context.ContextMenuManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alastar on 18.07.2017.
 */
public class ShopGUI extends MCGuiAccessable {

    public static ShopGUI instance ;
    
    public boolean can_process = true ;

    public boolean needNetUpdate = true ;
    public boolean loading = false ;
    
    private static int buttonLastId = 3 ;
    
    public static int m_Page = 0 ;
    
    public int currentShop = 0 ;
    private int m_Selected_Category = 0;
    public int lastCategory = -1 ;
    
    private ShopCategory[] cats ;
    
    public List < GuiGradientTextField > listTextFields = new ArrayList < > ( ) ;

    public ScaledResolution resolution ;

    public GuiMoneyArea moneyArea ;
    public GuiGradientTextField searchField ;
    public PreviousButton pb ;
    public NextButton nb ;
    public GuiGradientButton exitButton ;
    public GuiGradientButton searchButton ;
    
    public ShopGUI ( ) {

    	cats = new ShopCategory [ ] { new ItemNBlockCategory ( ), new PrivilegieCategory ( ), new RegionsCategory ( ), new EntitiesCategory ( ), new UsersShopsCategory ( ) } ;
    
    	for ( ShopCategory sc: cats ) {
    		
    		sc . preShow ( this ) ;
    		
    	}
    	
    }

    public static int getNextButtonId ( ) {
        
    	return buttonLastId ++ ;
        
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
    
    @Override
    protected void keyTyped ( char p_73869_1_, int p_73869_2_ ) {

    	if ( searchField != null && searchField . isFocused ( ) ) {
    	
    		searchField . textboxKeyTyped ( p_73869_1_, p_73869_2_ ) ;		

			getCurrentCategory ( ) . search ( searchField . getText ( ) ) ;
			
            updateGrid ( ) ;
            updateBtns ( ) ;
            
    		return ;
    		
    	}
    	
  		for ( GuiGradientTextField ggtf: listTextFields ) {
			
  			if ( ggtf . isFocused ( ) ) {
			
  				ggtf . textboxKeyTyped ( p_73869_1_, p_73869_2_ ) ;		
  				
			}
  			
		}
  		
    	if ( ClientProxy . refreshCfg != null && ClientProxy . refreshCfg . getKeyCode ( ) == p_73869_2_ ) { 
    		
    		MineDonate . loadClientConfig ( ) ;
    		initGui ( ) ;
    		
    	}

    		
      	// 32 205 -> d
    	// 30 203 <- a
    	
    	if ( ( 30 == p_73869_2_  || 203 == p_73869_2_ ) && pb . enabled ) {

    		actionPerformed ( pb ) ;
    		
    	} else if ( ( 32 == p_73869_2_  || 205 == p_73869_2_ ) && nb . enabled ) {

    		actionPerformed ( nb ) ;

    	} else if ( ClientProxy . openHUD . getKeyCode ( ) == p_73869_2_ ) {
    		
    		Minecraft . getMinecraft ( ) . displayGuiScreen( null ) ;
    		
    	} else {
    		
    		super . keyTyped ( p_73869_1_, p_73869_2_ ) ;
    		
    	}
    	
    }
    
    @Override
    protected void mouseClicked ( int p_73864_1_, int p_73864_2_, int p_73864_3_ ) {
    	
    	ContextMenuManager . click ( p_73864_1_, p_73864_2_, p_73864_3_ ) ;
    	
  		for ( GuiGradientTextField ggtf: listTextFields ) {
			
  			ggtf . mouseClicked ( p_73864_1_, p_73864_2_, p_73864_3_ ) ;		

		}
  			
    	super . mouseClicked ( p_73864_1_, p_73864_2_, p_73864_3_ ) ;
    	
    }

    @Override
    public void drawScreen ( int mouseX, int mouseY, float partialTicks ) {    	
    	
        this . drawRect ( 0, 0, resolution . getScaledWidth ( ), resolution . getScaledHeight ( ), 1258291200 ) ;

        if ( ! needNetUpdate ) {
        	
        	
	        // String vault = String.format( MineDonate.cfgUI.moneyLinePrefix + "%d" + MineDonate.cfgUI.moneyLineSuffix, MineDonate.m_Client_Money) ;
	        // this.drawCenteredString(this.fontRendererObj, vault, resolution.getScaledWidth() - this.fontRendererObj.getStringWidth(vault)-3, (int) (resolution.getScaledHeight() * 0.1)+20+5, 16777215);
	       
        	moneyArea . drawBalanceArea ( ( int ) resolution . getScaledWidth ( ) - 20, ( int ) ( resolution . getScaledHeight ( ) * 0.1 + 25 ), mouseX, mouseY ) ;
        	
	        if ( ! loading ) { 
	        	
	        	can_process = true; 
	        	
		    	getCurrentCategory ( ) . draw ( this, m_Page, mouseX, mouseY, partialTicks, DrawType . BG ) ;   

	            getCurrentCategory ( ) . draw ( this, m_Page, mouseX, mouseY, partialTicks, DrawType . PRE ) ;   
	        	
		        super . drawScreen ( mouseX, mouseY, partialTicks ) ;  
		
		  		for ( GuiGradientTextField ggtf: listTextFields ) {
					
		  			ggtf . drawTextBox ( ) ;		

				}
		  		
		        getCurrentCategory ( ) . draw ( this, m_Page, mouseX, mouseY, partialTicks, DrawType . POST ) ;   

		        //
	    	
	        } else { 
	        	
	        	can_process = false ; 
	            
		    	getCurrentCategory ( ) . draw ( this, m_Page, mouseX, mouseY, partialTicks, DrawType . BG ) ;   

		        super . drawScreen ( mouseX, mouseY, partialTicks ) ;  
		        
		    	getCurrentCategory ( ) . draw ( this, m_Page, mouseX, mouseY, partialTicks, DrawType . POST ) ;   

		    	this.drawRect((resolution.getScaledWidth()/2)-10-this.fontRendererObj.getStringWidth(MineDonate.cfgUI.loadingText)/2, (resolution.getScaledHeight()/2)-5, (resolution.getScaledWidth()/2)+10+this.fontRendererObj.getStringWidth(MineDonate.cfgUI.loadingText)/2, (resolution.getScaledHeight()/2) + 15, 1258291200);
			  	   
		    	this.drawCenteredString( this.fontRendererObj, MineDonate.cfgUI.loadingText, resolution.getScaledWidth()/2, resolution.getScaledHeight()/2, 16777215);
		       

	        }
	        
	    } else {
	    	
	    	if ( loading ) {
	    		
		        can_process = false;
	
		    	this.drawRect((resolution.getScaledWidth()/2)-10-this.fontRendererObj.getStringWidth(MineDonate.cfgUI.loadingText)/2, (resolution.getScaledHeight()/2)-5, (resolution.getScaledWidth()/2)+10+this.fontRendererObj.getStringWidth(MineDonate.cfgUI.loadingText)/2, (resolution.getScaledHeight()/2) + 15, 1258291200);
			  	   
		    	this.drawCenteredString( this.fontRendererObj, MineDonate.cfgUI.loadingText, resolution.getScaledWidth()/2, resolution.getScaledHeight()/2, 16777215);
		    	
	    	}
	    	
	    }
        
        ContextMenuManager . draw ( this, mouseX, mouseY  ) ;
        
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        
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
                updateBtns();
                
            } else if (button instanceof NextButton) {

                button.enabled = false;
                m_Page = m_Page + 1;
                System.out.println("new page is: " + m_Page);
                updateGrid ( ) ;
                updateBtns();
                
            } else if (button instanceof BuyButton ) {
                
            	((BuyButton) button) . buy ( ) ;
            	
            } else if (button instanceof CategoryButton) {
            
            	if ( currentShop != 0 ) {
            
                    if ( cats [ m_Selected_Category ] instanceof UsersShopsCategory ) {
                    	
                    	( ( UsersShopsCategory ) cats [ m_Selected_Category ] ) . selectedShop = 0 ;
                    	( ( UsersShopsCategory ) cats [ m_Selected_Category ] ) . updateUserShopCategory ( null, false ) ;
                   
                    }
                                		
            	}

            	if ( lastCategory != -1 ) {
            		
            		cats [ lastCategory ] . unShow ( ) ;  
            		
            	}

            	currentShop = 0 ;
                m_Page = 0;
                lastCategory = m_Selected_Category = ((CategoryButton) button).getCategory();

                MineDonate . networkChannel . sendToServer ( new NeedShopCategoryPacket ( getCurrentShopId ( ), m_Selected_Category ) ) ;
                loading = true ;
                
                resolution = new ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight);

            	if ( searchField != null ) {
            	
        			getCurrentCategory ( ) . search ( searchField . getText ( ) ) ;
        			            		
            	}
            	
                updateGrid ( ) ;
                updateBtns ( ) ;
                
            } else if ( button instanceof GoButton ) {
            	
            	// currentShop = ( ( GoButton ) button ) . shopId ;
            	( ( UsersShopsCategory ) getCurrentCategory ( ) ) . selectedShop = ( ( GoButton ) button ) . shopId ;
                MineDonate . networkChannel . sendToServer ( new NeedShopCategoryPacket ( ( ( GoButton ) button ) . shopId, 0 ) ) ;
                loading = true ;
                
              //  returnButton . enabled = returnButton . visible = displayReturnButton = true ;

            } else if ( button instanceof GuiGradientButton ) {
            	
            	if ( button . id == searchButton . id ) {

            		if ( searchField != null ) {
            			
                    	if ( searchField . getVisible ( ) ) { 
                    		
                    		searchField . setVisible ( false ) ;
                        	searchField . setEnabled ( false ) ;

                			getCurrentCategory ( ) . search ( null ) ;
                			
                            updateGrid ( ) ;
                            updateBtns ( ) ;
                            
                    	} else {
                    		
                    		searchField . setVisible ( true ) ;
                        	searchField . setEnabled ( true ) ;
                        	searchField . setFocused ( true ) ;
                        	
                			getCurrentCategory ( ) . search ( searchField . getText () ) ;
                			
                            updateGrid ( ) ;
                            updateBtns ( ) ;
                    	}
                    	       
            		}
            		
            	}
            	
            }
            
            getCurrentCategory ( ) . actionPerformed ( button ) ;
            
        }
        
    }

    public void drawHoveringText(ArrayList list, int mouseX, int mouseY, FontRenderer fontRenderer) {
        super.drawHoveringText(list, mouseX, mouseY, fontRenderer);
    }

    @Override
    public void initGui() {
    	
        instance = this ;
        
        resolution = new ScaledResolution ( this . mc, this . mc . displayWidth, this . mc . displayHeight ) ;
        
        m_Page = 0 ;
        
        updateGrid ( ) ;
        updateBtns ( ) ;
        
        //
        
		if ( moneyArea == null ) {
			
			moneyArea = new GuiMoneyArea ( ) ;
			
		}
		
		moneyArea . initGui ( this ) ;
		
		//
		
        getCurrentCategory ( ) . postShow ( ) ;
        
		if ( needNetUpdate && ! loading ) {

            MineDonate . networkChannel . sendToServer ( new NeedUpdatePacket ( 0 ) ) ;
            loading = true ;
            
        }

    }

    private void addCategories ( ) {

    	resolution = new ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight); // bull shit

        int posX = 30 ; //( resolution . getScaledWidth ( ) / 2 ) - ( widthCatsBlock / 2 ) ;
        
        for (int i = 0; i < getCurrentShopCategories ( ) . length ; ++i) {
        	
        	if ( getCurrentShopCategories ( ) [ i ] . getEnabled ( ) ) { //#LOG
        		
	            CategoryButton btn = new CategoryButton ( i, getNextButtonId ( ), posX, (int) (resolution.getScaledHeight() * 0.1) + 19, getCurrentShopCategories ( ) [i].getButtonText() ) ;
	          
	            btn . width = getCurrentShopCategories ( ) [ i ] .getButtonWidth(); // 75
	            this . addBtn ( btn ) ;
	      
	            posX += btn.width ;
	            
	            if ( i == m_Selected_Category ) { // #LOG
	            	
	            	btn . enabled = false ;
	            	
	            } else {
	            	
	            	btn . enabled = true ;
	            	
	            }
	            
        	}
        	
        }
    }


    public void addBtn ( GuiButton b ) {
    	        
    	this . buttonList . add ( b ) ;
        
    }
    
    public void removeButton ( GuiButton but ) {
    	
    	this . buttonList . remove ( but ) ;
    	
    }

    public void updateBtns ( ) {
    	
        buttonList.clear();
        addCategories();
      
        if ( MineDonate . cfgUI . addSearchButton ){
        
        	buttonList . add ( searchButton = new GuiGradientButton ( ShopGUI . getNextButtonId ( ), 30, ( int ) ( ( resolution . getScaledHeight ( ) ) - ( resolution . getScaledHeight ( ) * 0.1 ) ) - 5, MineDonate . cfgUI . searchButton . width, MineDonate . cfgUI . searchButton . height, MineDonate . cfgUI . searchButton . text, false ) ) ;
        
        	if ( searchField == null ) {
        		
        		listTextFields . add( searchField = new GuiGradientTextField ( this . fontRendererObj, 30 + MineDonate.cfgUI.searchButton.width, (int) ( (resolution.getScaledHeight()) - (resolution.getScaledHeight() * 0.1) )-5, MineDonate . cfgUI . searchField . width, MineDonate . cfgUI . searchField . height, true ) ) ;

        		searchField . setText ( MineDonate . cfgUI . searchField . text ) ;
        		searchField . setTextHolder ( MineDonate . cfgUI . searchField . textHolder ) ;
        		
        		searchField . setVisible ( false ) ;
            	searchField . setEnabled ( false ) ;
            	
        	}

        	searchField . xPosition = 30 + MineDonate.cfgUI.searchButton.width ;
    		searchField . yPosition = (int) ( (resolution.getScaledHeight()) - (resolution.getScaledHeight() * 0.1) ) - 5 ;
        	
        }
        
        buttonList . add ( exitButton = new GuiGradientButton ( 0, ( int ) ( resolution . getScaledWidth ( ) - 30 /** 0.5*/ ) - MineDonate . cfgUI . exitButton . width, ( int ) ( ( resolution . getScaledHeight ( ) ) - ( resolution . getScaledHeight ( ) * 0.1 ) - 5 ), MineDonate . cfgUI . exitButton . width, MineDonate . cfgUI . exitButton . height, MineDonate . cfgUI . exitButton . text, false ) ) ;
      
        // buttonList . add ( returnButton = new GuiGradientButton ( ShopGUI . getNextButtonId ( ), exitButton.xPosition -  MineDonate . cfgUI . returnButton . width, exitButton . yPosition, MineDonate . cfgUI . returnButton . width, MineDonate . cfgUI . returnButton . height, MineDonate . cfgUI . returnButton . text, false ) ) ;
        
        buttonList . add ( pb = new PreviousButton ( ShopGUI . getNextButtonId ( ), ( int ) ( resolution . getScaledWidth ( ) * 0.5 ) - 20 - 2 , ( int ) ( ( resolution . getScaledHeight ( ) ) - ( resolution . getScaledHeight ( ) * 0.1 ) ) - 20 - 10, 20, 20, "<" ) ) ;
        buttonList . add ( nb = new NextButton ( ShopGUI . getNextButtonId ( ), pb . xPosition + pb . width + 4, pb . yPosition, 20, 20, ">" ) ) ;
        		
        if ( getCurrentCategory ( ) . elementsOnPage ( ) != 0 && getCurrentCategory ( ) . getSourceCount ( getCurrentShopId ( ) ) > getCurrentCategory ( ) . elementsOnPage ( ) ) {

            pb . enabled = ( m_Page > 0) ;

            nb . enabled = getCurrentCategory ( ) . elementsOnPage ( ) > 0 && m_Page < (int) Math.ceil(getCurrentCategory ( ).getSourceCount(getCurrentShopId()) / (getCurrentCategory ( ) . elementsOnPage ( ) ) ) ;

        } else {
        	
        	pb . enabled = nb . enabled = false ;
        	pb . visible = nb . visible = false ;

        }
        
        getCurrentCategory ( ) . updateButtons ( this, m_Page ) ;

    }

    int tmpH ;
    public void updateGrid ( ) { // #LOG
			
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

    public List getLabels() {
        return this.labelList;
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
	
}
package ru.alastar.minedonate.gui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.gui.GuiButton;
import ru.alastar.minedonate.MineDonate;
import ru.alastar.minedonate.merch.Merch;
import ru.log_inil.mc.minedonate.gui.DrawType;
import ru.log_inil.mc.minedonate.gui.GuiAbstractItemEntry;
import ru.log_inil.mc.minedonate.gui.GuiScrollingList;

/**
 * Created by Alastar on 19.07.2017.
 */
public abstract class ShopCategory {

	protected int shopId = 0 ;
	protected int catId ;
	protected int subCatId = -1 ;
    protected int rowCount ;
    protected int colCount ;

    protected List < Merch > noSearchedEntries = new ArrayList < > ( ) ;

	
	public boolean getEnabled ( ) {
		
		return false ;
		
	}

    public int getSourceCount ( int shopId ) {
    	
    	return 0 ;
    	
    }

    public String getName ( ) {
    	
    	return "generic" ;
    	
    }

    public abstract void draw(ShopGUI relative, int page, int mouseX, int mouseY, float partialTicks, DrawType dt ) ;
    
    public void unDraw ( ) {
    	
    }
    
    public void updateButtons ( ShopGUI relative, int page ) {
    	
    	postShow ( ) ;

    }

    public int elementsOnPage ( ) {
    	
    	return rowCount * colCount ;
    	
    }

    public void actionPerformed ( GuiButton button ) {
    
    	if ( subButtonsMap . containsKey ( button . id ) ) {
    		
    	//	setSubCategory ( subCatIdMap . get ( button . id ) ) ;
    		
    	}

    	// postShow ( ) ;
    	
    }
    
    public int getButtonWidth ( ) {
    	
    	return 0 ;
    	
    }
    
    public String getButtonText ( ) {
    	
    	return "?" ;
    	
    }
    
    public int getRowCount ( ) {
    	
    	return rowCount ;
    	
    }
    
    public void setRowCount ( int i ) {
    	
    	rowCount = i ;
    	
    }
    
    public int getColCount ( ) { 
    	
    	return colCount ;
    	
    }
    
    public void setColCount ( int i ) {
    	
    	colCount = i ;
    	
    }

    public int getItemWidth ( ) {
    	
    	return 0 ;
    	
    }
    
    public int getItemHeight ( ) {
    	
    	return 0 ;
    	
    }
    
    protected ShopGUI gui ;
    
	public void preShow ( ShopGUI _shopGUI ) {
		
		gui = _shopGUI ;
		
	}

	public int calcMaxCatStringLineWidth ( ) {
		
		/*
		int max = 0 ;
		
		for ( SubCategory sc : MineDonate . shops . get (  gui . getCurrentShopId ( )  ) . cats [ catId ] . subCategories ) {
			
			max = Math . max ( max, gui . getFontRenderer ( ) . getStringWidth ( sc . displayName ) ) ;

		}*/
				
		return 75 ;// max ;
		
	}
	
	int tmpH, tmpW, tmpMW ;
	int buttonsRowCount, buttonsColCount, drawn ;

	public void updateMaxRowAndColCount ( ) {

		if ( ( tmpMW = calcMaxCatStringLineWidth ( ) ) > 0 ) {

			tmpH = gui . getScaledResolution ( ) . getScaledHeight ( ) - 50 - 25 ;
    		
    		tmpW = gui . getScaledResolution ( ) . getScaledWidth ( ) - 40 - 40 ;

    		buttonsColCount = (tmpW) / tmpMW;

    		buttonsRowCount = tmpH / MineDonate.cfgUI.subCategoryButtonHeight;
		
    	}
		
	}
	
	SubCategory subCat ;
	public SubCategory [ ] subCats ;
	int xOffset, yOffset ;
	GuiButton openSubCatButton ;
	
    Map < Integer, GuiButton > subButtonsMap = new HashMap < > ( ) ;
    Map < Integer, Integer > subCatIdMap = new HashMap < > ( ) ;

	public void postShow ( ) {
		
		if ( ! MineDonate . checkCatExists ( gui . getCurrentShopId ( ), catId ) ) {
			
			return ;
			
		}
		
		subCats = MineDonate . shops . get ( gui . getCurrentShopId ( )  ) . cats [ catId ] . subCategories ;
		drawn = 0 ;

		if ( subCats != null && subCats . length > 0 ) {
			
			if ( subCatId == -1 ) {
				
				subCatIdMap . clear ( ) ;

				for ( GuiButton gb : subButtonsMap . values ( ) ) {
				//	System.err.println(gb);
					gui . removeButton ( gb ) ;
					
				}
				
				subButtonsMap . clear ( ) ;
				
				updateMaxRowAndColCount ( ) ;

				int added = 0 ;
				
				int en = 0 ;
				int bccx = 0 ;
				int brcy = 0 ;
				
		        for ( int i = 0; i < buttonsRowCount ; i ++ ) {

		            for ( int j = 0; j < buttonsColCount; j ++ ) {

		                if ( en < subCats . length ) {
		                	
		                	brcy = Math.max(i+1, brcy);
		                	bccx = Math.max(j+1, bccx);

		                	en ++ ;
		                	
		                }
		                
		            }
		            
		        }
		        
		        buttonsRowCount = brcy ;
		        buttonsColCount = bccx ;
				
		        for ( int i = 0; i < buttonsRowCount ; i ++ ) {

		            for ( int j = 0; j < buttonsColCount; j ++ ) {

		                if ( added < subCats . length ) {
		                	
		                	subCat = subCats [ added ] ;
		                	added ++ ;
		                	
		                	if ( search ? subCat . displayName . toLowerCase ( ) . contains ( searchValue ) : true ) {
		                		
			                	xOffset = 15 + ( (int) ( ( gui . getScaledResolution ( ) . getScaledWidth ( ) / 2 - 45 - ( buttonsColCount * 75 ) / 2 ) / 2 ) + 75 * ( j + 1 ) ) ;
			                	yOffset = (int) (gui . getScaledResolution ( ) . getScaledHeight ( )*0.05) + ( ( ( gui . getScaledResolution ( ) . getScaledHeight ( ) / 2 - ( buttonsRowCount * MineDonate.cfgUI.cats.regions.itemBuyButton.height ) ) ) + MineDonate.cfgUI.cats.regions.itemBuyButton.height * 2 * ( i ) ) ;
		  		                  
			                	if( yOffset > gui . getScaledResolution ( ) . getScaledHeight ( ) - 55 ) {
				                	
			                		continue ;
			                		
			                	}
			                	
		                		if( yOffset < 55 ) {
				                	
			                		continue ;
			                		
			                	}
			                	
			                	if( xOffset > gui . getScaledResolution ( ) . getScaledWidth ( ) - 55 ) {
				                	
			                		continue ;
			                		
			                	}

			                	openSubCatButton = new GuiButton ( ShopGUI . getNextButtonId ( ), xOffset, yOffset, gui . getFontRenderer ( ) . getStringWidth ( subCat . displayName ) + 15, MineDonate.cfgUI.cats.regions.itemBuyButton.height, subCat . displayName ) ;
			                	openSubCatButton.xPosition-=  openSubCatButton . width/2 ;
			                	
			                	subButtonsMap . put ( openSubCatButton . id, openSubCatButton ) ;
	
			                	subCatIdMap . put ( openSubCatButton . id, subCat . subCatId ) ;
			                	
			                    gui . addBtn ( openSubCatButton, true ) ;
			                    
			                    drawn ++ ;

		                	}
	                	}
		                
		            }
		           
		        }
				
			} else {
				
				
			}
			
		} else {
			
    		setSubCategory ( -1 ) ;

		}
		
	}
	
	protected List < GuiAbstractItemEntry > entrs = new ArrayList < > ( ) ;
	
	public List < GuiAbstractItemEntry > getEntries ( ) {
		
		return entrs ;
		
	}
	
	public void unShow ( ) {
		
		for ( GuiAbstractItemEntry gie : entrs ) {

			gie . unDraw ( ) ;
			
		}
		
	}
	
	public void setSubCategory ( int _subCatId ) {
		
		if ( ! MineDonate . checkCatExists (  gui . getCurrentShopId ( ) , catId ) ) {
			
			return ;
			
		}

		subCatId = _subCatId ;
		
		noSearchedEntries . clear ( ) ;
			
		for ( Merch m : MineDonate . shops . get ( gui . getCurrentShopId ( )  ) . cats [ catId ] . getMerch ( ) ) {
			
			if ( m != null && ( m . subCatId == _subCatId || _subCatId == -1 ) ) {
				
				noSearchedEntries . add ( m ) ;
				
			}
			
		}	
		
	}
	
	protected boolean search ;
	protected String searchValue ;
	
	public void search ( String text ) {
		
		boolean b = search ;

		search = ! ( text == null || text . trim ( ) . isEmpty ( ) ) ;
		
		if ( search ) {
			
			searchValue = text . toLowerCase ( ) . trim ( ) ;
			
		} else {
			
			searchValue = "" ;
			
		}
			
		if ( b ) {
			
			updateButtons ( gui, 0 ) ;
			
		}
		
	}

	public GuiScrollingList getScrollList ( ) {
		
		return null ;
		
	}
	
	public String getCatMoneyType ( ) {
    
		return MineDonate . getMoneyType ( ShopGUI . instance . getCurrentShopId ( ), catId ) ;
    	
    }

	public static class SubCategory {
        
		public int subCatId ;
		public String displayName ;
    	
    	public SubCategory ( int _subCatId, String _displayName ) {
    		
    		subCatId = _subCatId ;
    		displayName = _displayName ;
    		
    	}
    	
    }
    
}

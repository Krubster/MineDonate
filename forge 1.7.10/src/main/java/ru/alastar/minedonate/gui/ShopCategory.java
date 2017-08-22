package ru.alastar.minedonate.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.Tessellator;
import org.lwjgl.opengl.GL11;
import ru.alastar.minedonate.MineDonate;
import ru.alastar.minedonate.merch.Merch;
import ru.log_inil.mc.minedonate.gui.DrawType;
import ru.log_inil.mc.minedonate.gui.GuiAbstractItemEntry;
import ru.log_inil.mc.minedonate.gui.GuiEntry;
import ru.log_inil.mc.minedonate.gui.GuiScrollingList;

import java.util.*;

/**
 * Created by Alastar on 19.07.2017.
 */
public abstract class ShopCategory extends GuiEntry {

	public String shopOwner ;
	protected int shopId = 0 ;
	protected int catId ;
	protected int subCatId = -1 ;
    protected int rowCount ;
    protected int colCount ;

    protected List < Merch > noSearchedEntries = new ArrayList < > ( ) ;

	public ShopCategory ( String _name ) {
		
		super ( _name ) ;
		
	}
	
    public void setCatId ( int _catId ) {
    	
    	catId = _catId ;

    }
    
    public int getCatId ( ) {
    	
    	return catId ; 
    	
    }
	
	public boolean getEnabled ( ) {
		
		return false ;
		
	}

    public int getSourceCount ( int shopId ) {
    	
    	return 0 ;
    	
    }

    public String getName ( ) {
    	
    	return "generic" ;
    	
    }


    
    public void updateButtons ( ShopGUI g, int page ) {
    	
    }

    public int elementsOnPage ( ) {
    	
    	return rowCount * colCount ;
    	
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
    


	public int calcMaxCatStringLineWidth ( ) {
				
		return 75 ;
		
	}
	
	int tmpH, tmpW, tmpMW ;
	int buttonsRowCount, buttonsColCount, drawn ;

	public void updateMaxRowAndColCount ( ) {

		if ( ( tmpMW = calcMaxCatStringLineWidth ( ) ) > 0 ) {

			tmpH = gui . getScaledResolution ( ) . getScaledHeight ( ) - 50 - 25 ;
    		
    		tmpW = gui . getScaledResolution ( ) . getScaledWidth ( ) - 40 - 40 ;

    		buttonsColCount = (tmpW) / tmpMW;
		
    	}
		
	}
	
    public void draw(ShopGUI g, int page, int mouseX, int mouseY, float partialTicks, DrawType dt ) {
    	
    	super.draw(g, page, mouseX, mouseY, partialTicks, dt);
    	
    	if ( dt == DrawType.BG ) {
    		
        	g . drawGradientRectAccess(30, (int) (g . getScaledResolution ( ).getScaledHeight() * 0.1) + 19 + 20, g . getScaledResolution ( ).getScaledWidth() - 30, (int) ((g . getScaledResolution ( ).getScaledHeight()) - (g . getScaledResolution ( ).getScaledHeight() * 0.1)) - 5, -1072689136, -804253680);

    	} else if ( dt == DrawType . POST ) {
    		
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            GL11.glDisable(GL11.GL_ALPHA_TEST);
            GL11.glShadeModel(GL11.GL_SMOOTH);
            GL11.glDisable(GL11.GL_TEXTURE_2D);

            //

            Tessellator var18 = Tessellator.instance;
            byte var20 = 12;

            var18.startDrawingQuads();

            var18.setColorRGBA_I(0, 0);

            var18.addVertexWithUV((double) 30, (double) ((int) (g . getScaledResolution ( ).getScaledHeight() * 0.1) + 19 + 20 + var20), 0.0D, 0.0D, 1.0D);
            var18.addVertexWithUV((double) (int) g . getScaledResolution ( ).getScaledWidth() - 30, (double) ((g . getScaledResolution ( ).getScaledHeight() * 0.1) + 19 + 20 + var20), 0.0D, 1.0D, 1.0D);

            var18.setColorRGBA_I(0, 105);

            var18.addVertexWithUV((double) g . getScaledResolution ( ).getScaledWidth() - 30, (double) (int) (g . getScaledResolution ( ).getScaledHeight() * 0.1) + 19 + 20, 0.0D, 1.0D, 0.0D);
            var18.addVertexWithUV((double) 30, (double) (int) (g . getScaledResolution ( ).getScaledHeight() * 0.1) + 19 + 20, 0.0D, 0.0D, 0.0D);

            var18.draw();


            var18.startDrawingQuads();

            var18.setColorRGBA_I(0, 105);

            var18.addVertexWithUV((double) 30, (double) ((g . getScaledResolution ( ).getScaledHeight()) - (g . getScaledResolution ( ).getScaledHeight() * 0.1)) - 5, 0.0D, 0.0D, 1.0D);
            var18.addVertexWithUV((double) g . getScaledResolution ( ).getScaledWidth() - 30, (double) ((g . getScaledResolution ( ).getScaledHeight()) - (g . getScaledResolution ( ).getScaledHeight() * 0.1)) - 5, 0.0D, 1.0D, 1.0D);

            var18.setColorRGBA_I(0, 0);

            var18.addVertexWithUV((double) g . getScaledResolution ( ).getScaledWidth() - 30, (double) (((g . getScaledResolution ( ).getScaledHeight()) - (g . getScaledResolution ( ).getScaledHeight() * 0.1)) - 5 - var20), 0.0D, 1.0D, 0.0D);
            var18.addVertexWithUV((double) 30, (double) (((g . getScaledResolution ( ).getScaledHeight()) - (g . getScaledResolution ( ).getScaledHeight() * 0.1)) - 5 - var20), 0.0D, 0.0D, 0.0D);

            var18.draw();

            //

            GL11.glEnable(GL11.GL_TEXTURE_2D);
            GL11.glShadeModel(GL11.GL_FLAT);
            GL11.glEnable(GL11.GL_ALPHA_TEST);
            GL11.glDisable(GL11.GL_BLEND);

    	}
    	
    	
    }
    
	SubCategory subCat ;
	public SubCategory [ ] subCats ;
	int xOffset, yOffset ;
	GuiButton openSubCatButton ;
	
    Map < Integer, GuiButton > subButtonsMap = new HashMap < > ( ) ;
    Map < Integer, Integer > subCatIdMap = new HashMap < > ( ) ;

	@Override
	public void postShow ( ShopGUI g ) {
		
    	updateButtons ( g, g . m_Page ) ;

		if ( ! MineDonate . checkCatExists ( gui . getCurrentShopId ( ), catId ) ) {

			return ;
			
		}
		
		super . unShow ( g ) ;

		drawn = 0 ;

		filterProcess ( ) ;
		
	}
	
	public void filterProcess ( ) {

		if ( catCheck && ! MineDonate . checkCatExists ( gui . getCurrentShopId ( ) , catId ) ) {

			return ;
			
		}
		
		noSearchedEntries . clear ( ) ;

		for ( Merch m : MineDonate . shops . get ( gui . getCurrentShopId ( )  ) . cats [ catId ] . getMerch ( ) ) {

			if ( m != null ) {

				noSearchedEntries . add ( m ) ;
				
			}
			
		}	

		Collections . sort ( noSearchedEntries, new Comparator < Merch > ( ) {
			
             @Override
             public int compare ( Merch l, Merch r ) {
                 
            	 return l . getRating ( ) > r . getRating ( ) ? -1 : ( l . getRating ( ) < r . getRating ( ) ) ? 1 : 0 ;
                 
             }
             
        } ) ;
		 
	}
	
	protected List < GuiAbstractItemEntry > entrs = new ArrayList < > ( ) ;
	
	public List < GuiAbstractItemEntry > getEntries ( ) {
		
		return entrs ;
		
	}
	
	@Override
	public void unShow ( ShopGUI g ) {
		
		super . unShow ( g ) ;

		for ( GuiAbstractItemEntry gie : entrs ) {

			gie . unShow ( g ) ;
			
		}
		
	}
	
	boolean catCheck = true ;
	
	public void setDisableCatCheck ( ) {
		
		catCheck = false ;
		
	}
	
	protected boolean search ;
	protected String searchValue ;
	
	public void search ( String text ) {
		
		//boolean b = searchValue != null ? ! searchValue . equals ( text ) : searchValue != text ;

		search = ! ( text == null || text . trim ( ) . isEmpty ( ) ) ;
		
		if ( search ) {
			
			searchValue = text . toLowerCase ( ) . trim ( ) ;
			
		} else {
			
			searchValue = "" ;
			
		}
		
		if ( searchValue . isEmpty ( ) ) {
			
			search = false ;
			
		}

		updateButtons ( gui, 0 ) ;	
		
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

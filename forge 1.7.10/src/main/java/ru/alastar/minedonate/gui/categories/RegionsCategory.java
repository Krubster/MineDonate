package ru.alastar.minedonate.gui.categories;

import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.RenderHelper;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import ru.alastar.minedonate.MineDonate;
import ru.alastar.minedonate.gui.BuyButton;
import ru.alastar.minedonate.gui.ShopCategory;
import ru.alastar.minedonate.gui.ShopGUI;
import ru.alastar.minedonate.merch.Merch;
import ru.alastar.minedonate.merch.info.RegionInfo;
import ru.log_inil.mc.minedonate.gui.DrawType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Alastar on 20.07.2017.
 */
public class RegionsCategory extends ShopCategory {

    public RegionsCategory ( String _name ) {
		
		super ( _name ) ;
		
		catId = 2 ;
    	 
    	rowCount = 4 ;
    	colCount = 2 ;
    	 
    }
    
    @Override
    public boolean getEnabled ( ) {
    	
        return MineDonate . cfg . sellRegions ;
        
    }

    @Override
    public int getSourceCount ( int shopId ) {
    	
        return list . size ( )  ;
        
    }

    @Override
    public String getName ( ) {
    	
        return "Regions" ;
        
    }
    
    int drawn ;
    
    @Override
    public void draw(ShopGUI g, int page, int mouseX, int mouseY, float partialTicks, DrawType dt) {
    	
    	super.draw(g, page, mouseX, mouseY, partialTicks, dt);

    	if ( dt == DrawType . PRE ) {
        	
    		GL11 . glEnable ( GL12 . GL_RESCALE_NORMAL ) ;
    		RenderHelper . enableGUIStandardItemLighting ( ) ;
    		
            drawn = 0 ;
            
            for ( int i = 0; i < rowCount ; i ++ ) {
            	
                for ( int j = 0; j < colCount; j ++ ) {
                	
                    if ( page * colCount * rowCount + drawn < list . size ( ) ) {
               
                    	drawRegion ( g, g . getScaledResolution ( ), page, mouseX, mouseY, partialTicks, list . get ( page * colCount * rowCount + drawn ), i, j ) ;
                    	
                    	drawn ++ ;
                    	
                    }
                    
                }
                
            }
            
    		RenderHelper . disableStandardItemLighting ( ) ;
    		GL11 . glDisable ( GL12 . GL_RESCALE_NORMAL ) ;
    		
        }
 
    }
    
    int x_offset ;
    int y_offset ;
    
	public void drawRegion(ShopGUI relative, ScaledResolution sc, int m_Page, int mouseX, int mouseY, float partialTicks, RegionInfo _info, int gridI, int gridJ) {

	  x_offset = (int) ( ( sc . getScaledWidth ( ) / 2 - ( getColCount ( ) * 75 ) / 2 )/2 ) + 75 * ( gridJ + 1 ) ;
	  y_offset = (int) ( ( sc . getScaledHeight ( ) / 2 - ( getRowCount ( ) * 75 ) / 2 )/2 ) + 75 * ( gridI + 1  );

      relative.drawCenteredString(relative.getFontRenderer(), _info.name, x_offset, y_offset - 15, 16777215);
     //relative.drawCenteredString(relative.getFontRenderer(), MineDonate.cfgUI.cats.regions.pricePrefix + info.cost + MineDonate.cfgUI.cats.regions.priceSuffix, x_offset, y_offset, 16777215);
      relative . moneyArea . drawPriceArea ( x_offset - 7, y_offset, _info . cost, _info . getMoneyType ( ) ) ;

	}

    List < RegionInfo > list = new ArrayList < > ( ) ;
    Map < Integer, BuyButton > buttonsMap = new HashMap < > ( ) ; // holy shi~

    int lastPage = 0 ;
    BuyButton bb ;
    
    @Override
    public void updateButtons ( ShopGUI relative, int m_Page ) {
        
		for ( RegionInfo ri : list ) {
			
			if ( buttonsMap . containsKey ( ri . getId ( ) ) ) relative . removeButton ( buttonsMap . get ( ri . getId ( ) ) ) ;

    	}
		
		buttonsMap . clear ( ) ;
    	list . clear ( ) ;
    	
		if ( MineDonate . shops . containsKey ( gui . getCurrentShopId ( ) ) ) {

	    	if ( search ) {
	    		
	    		for ( Merch ri : MineDonate . shops . get ( gui . getCurrentShopId ( ) ) . cats [ catId ] . getMerch ( ) ) {
	    			
	        		if ( ( ( RegionInfo ) ri ) . getSearchValue ( ) . toLowerCase ( ) . contains ( searchValue ) ) {
	        			
	        			list . add ( ( RegionInfo ) ri ) ;
	        			
	        		}
	        		
	        	}
	    		
	    	} else {
	    		
	    		for ( Merch ri: MineDonate . shops . get ( gui . getCurrentShopId ( ) ) . cats [ catId ] . getMerch ( ) ) {
	    			
	    			list . add ( ( RegionInfo ) ri ) ;
	  
	        	}
	    		
	    		lastPage = m_Page ; 
	    		
	    	}
		
		}
		
    	int drawn = 0;

    	ScaledResolution resolution = new ScaledResolution( relative.mc, relative.mc.displayWidth, relative.mc.displayHeight); // bull shit
    	
        RegionInfo info ;
        int x_offset ;
        int y_offset ;

        for (int i = 0; i < rowCount ; ++i) {
            for (int j = 0; j < colCount; ++j) {
                if (m_Page * colCount * rowCount + drawn < list.size()) {
 
                	x_offset = ( ( resolution . getScaledWidth ( ) / 2 - ( getColCount ( ) * 75 ) / 2 ) / 2 ) + 75 * ( j + 1 );
                	y_offset = ( ( resolution . getScaledHeight ( ) / 2 - ( getRowCount ( ) * 75 ) / 2 ) / 2 ) + 75 * ( i + 1 );
                
                    info = list.get(m_Page * colCount * rowCount + drawn);
                  
                    bb = new BuyButton ( info . getShopId ( ), info . getCategory ( ), info . getId ( ), ShopGUI . getNextButtonId ( ), x_offset - 22, y_offset + 15, MineDonate.cfgUI.cats.regions.itemBuyButton.width, MineDonate.cfgUI.cats.regions.itemBuyButton.height, MineDonate.cfgUI.cats.regions.itemBuyButton.text);
                    buttonsMap . put ( info . getId ( ), bb ) ;

                    relative . addButton ( bb, false ) ;
                    
                    ++drawn;
                    
                }
            }
        }
    }
      
    @Override 
	public void filterProcess ( ) { }
    
	@Override
	public int getButtonWidth ( ) {
		
		return MineDonate.cfgUI.cats.regions.categoryButtonWidth ;
		
	}
	
	@Override 
	public String getButtonText ( ) {
		
		return MineDonate.cfgUI.cats.regions.categoryButtonText ;
		
	}
	
	@Override
	public int getItemWidth() {
		
		return Math.max(MineDonate.cfgUI.cats.regions.itemBuyButton.width, 75);

	}

	@Override
	public int getItemHeight() {
		
		return Math.max(MineDonate.cfgUI.cats.regions.itemBuyButton.height, 95);
		
	}
	
}

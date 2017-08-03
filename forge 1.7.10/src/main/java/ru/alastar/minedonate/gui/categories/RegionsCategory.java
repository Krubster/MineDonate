package ru.alastar.minedonate.gui.categories;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import ru.alastar.minedonate.MineDonate;
import ru.alastar.minedonate.gui.BuyButton;
import ru.alastar.minedonate.gui.ShopCategory;
import ru.alastar.minedonate.gui.ShopGUI;
import ru.alastar.minedonate.merch.Merch;
import ru.alastar.minedonate.merch.info.RegionInfo;
import ru.log_inil.mc.minedonate.gui.DrawType;
import ru.log_inil.mc.minedonate.gui.GuiScrollingList;
import ru.log_inil.mc.minedonate.gui.painters.RegionGridItemPainter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

/**
 * Created by Alastar on 20.07.2017.
 */
public class RegionsCategory extends ShopCategory {

    RegionGridItemPainter rip ;
    public RegionsCategory ( ) {
    	
    	 rip = new RegionGridItemPainter ( this ) ;
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
    
    @Override
    public void draw(ShopGUI relative, int m_Page, int mouseX, int mouseY, float partialTicks, DrawType dt) {
    	
		ScaledResolution resolution = new ScaledResolution( relative.mc, relative.mc.displayWidth, relative.mc.displayHeight);
		
    	if ( dt == DrawType . BG ){
    		
    	    relative.drawGradientRectAccess(30, (int) (resolution.getScaledHeight() * 0.1) + 15+24, resolution.getScaledWidth()-30,  (int) ( (resolution.getScaledHeight()) - (resolution.getScaledHeight() * 0.1) ) - 5,  -1072689136, -804253680);

    	} else if ( dt == DrawType.POST ) {
        	
    		GL11 . glEnable ( GL12 . GL_RESCALE_NORMAL ) ;
    		RenderHelper . enableGUIStandardItemLighting ( ) ;
    		
            rip . drawn = 0 ;
            
            for (int i = 0; i < rowCount; ++i) {
                for (int j = 0; j < colCount; ++j) {
                    if (m_Page * colCount * rowCount + rip . drawn < list . size ( ) ) {
               
                    	rip.draw(relative, resolution, m_Page, mouseX, mouseY, partialTicks, list.get(m_Page * colCount * rowCount + rip.drawn), i, j);
                    	
                    	rip . drawn ++ ;
                    	
                    }
                }
            }
            
    		RenderHelper . disableStandardItemLighting ( ) ;
    		GL11 . glDisable ( GL12 . GL_RESCALE_NORMAL ) ;
    		
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
	        
	        var18.addVertexWithUV((double)30, (double)((int) (resolution.getScaledHeight() * 0.1) + 19+20+ var20), 0.0D, 0.0D, 1.0D);
	        var18.addVertexWithUV((double)(int) resolution.getScaledWidth()-30, (double)( (resolution.getScaledHeight() * 0.1) + 19+20 + var20), 0.0D, 1.0D, 1.0D);
	        
	        var18.setColorRGBA_I(0, 255);
	        
	        var18.addVertexWithUV((double)resolution.getScaledWidth()-30, (double)(int) (resolution.getScaledHeight() * 0.1) + 19+20, 0.0D, 1.0D, 0.0D);
	        var18.addVertexWithUV((double)30, (double)(int) (resolution.getScaledHeight() * 0.1) + 19+20, 0.0D, 0.0D, 0.0D);
	        
	        var18.draw();

	        
	        var18.startDrawingQuads();
	        
	        var18.setColorRGBA_I(0, 255);
	        
	        var18.addVertexWithUV((double)30, (double) ( (resolution.getScaledHeight()) - (resolution.getScaledHeight() * 0.1) ) - 6, 0.0D, 0.0D, 1.0D);
	        var18.addVertexWithUV((double)resolution.getScaledWidth()-30, (double) ( (resolution.getScaledHeight()) - (resolution.getScaledHeight() * 0.1) ) - 6, 0.0D, 1.0D, 1.0D);
	        
	        var18.setColorRGBA_I(0, 0);
	        
	        var18.addVertexWithUV((double)resolution.getScaledWidth()-30, (double)( ( (resolution.getScaledHeight()) - (resolution.getScaledHeight() * 0.1) ) - 6 - var20), 0.0D, 1.0D, 0.0D);
	        var18.addVertexWithUV((double)30, (double)( ( (resolution.getScaledHeight()) - (resolution.getScaledHeight() * 0.1) ) - 6 - var20), 0.0D, 0.0D, 0.0D);
	        
	        var18.draw();
	        
	        //
	        
	        GL11.glEnable(GL11.GL_TEXTURE_2D);
	        GL11.glShadeModel(GL11.GL_FLAT);
	        GL11.glEnable(GL11.GL_ALPHA_TEST);
	        GL11.glDisable(GL11.GL_BLEND);
	        
        }
 
    }

    List < RegionInfo > list = new ArrayList < > ( ) ;
    Map < Integer, BuyButton > buttonsMap = new HashMap < > ( ) ; // holy shi~

    int lastPage = 0 ;
    BuyButton bb ;
    
    @Override
    public void updateButtons ( ShopGUI relative, int m_Page ) {
        
		for ( RegionInfo ri : list ) {
			
			if ( buttonsMap . containsKey ( ri . merch_id ) ) relative . removeButton ( buttonsMap . get ( ri . merch_id ) ) ;

    	}
		
		buttonsMap . clear ( ) ;
    	list . clear ( ) ;
    	
		if ( MineDonate . shops . containsKey ( gui . getCurrentShopId ( ) ) ) {

	    	if ( search ) {
	    		
	    		for ( Merch ri : MineDonate . shops . get ( gui . getCurrentShopId ( ) ) . cats [ catId ] . getMerch ( ) ) {
	    			
	        		if ( ( ( RegionInfo ) ri ) . name . contains ( searchValue ) ) {
	        			
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

    	ScaledResolution resolution= new ScaledResolution( relative.mc, relative.mc.displayWidth, relative.mc.displayHeight); // bull shit
    	
        RegionInfo info ;
        int x_offset ;
        int y_offset ;

        for (int i = 0; i < rowCount ; ++i) {
            for (int j = 0; j < colCount; ++j) {
                if (m_Page * colCount * rowCount + drawn < list.size()) {
 
                	x_offset = ( ( resolution . getScaledWidth ( ) / 2 - ( getColCount ( ) * 75 ) / 2 ) / 2 ) + 75 * ( j + 1 );
                	y_offset = ( ( resolution . getScaledHeight ( ) / 2 - ( getRowCount ( ) * 75 ) / 2 ) / 2 ) + 75 * ( i + 1 );
                
                    info = list.get(m_Page * colCount * rowCount + drawn);
                  
                    bb = new BuyButton ( info . getShopId ( ), info . getCategory ( ), info . merch_id, ShopGUI.getNextButtonId(), x_offset - 22, y_offset + 15, MineDonate.cfgUI.cats.regions.itemBuyButton.width, MineDonate.cfgUI.cats.regions.itemBuyButton.height, MineDonate.cfgUI.cats.regions.itemBuyButton.text);
                    buttonsMap.put(info.merch_id, bb);

                    relative.addBtn(bb);
                    
                    ++drawn;
                    
                }
            }
        }
    }
        
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

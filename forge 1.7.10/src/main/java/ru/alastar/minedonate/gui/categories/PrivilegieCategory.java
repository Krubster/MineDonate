package ru.alastar.minedonate.gui.categories;

import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import org.lwjgl.opengl.GL11;
import ru.alastar.minedonate.MineDonate;
import ru.alastar.minedonate.gui.BuyButton;
import ru.alastar.minedonate.gui.ShopCategory;
import ru.alastar.minedonate.gui.ShopGUI;
import ru.alastar.minedonate.merch.Merch;
import ru.alastar.minedonate.merch.info.PrivilegieInfo;
import ru.alastar.minedonate.proxies.ClientProxy;
import ru.log_inil.mc.minedonate.gui.DrawType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Alastar on 20.07.2017.
 */
public class PrivilegieCategory extends ShopCategory {

    public PrivilegieCategory() {

        catId = 1 ;
        
    }

    @Override
    public boolean getEnabled() {
        return MineDonate.cfg.sellPrivelegies;
    }

    @Override
    public int getSourceCount(int shopId) {
        return list.size();
    }

    @Override
    public String getName() {
        return "Privelegies";
    }

    @Override
    public int elementsOnPage ( ) {

        return 1;

    }

    ScaledResolution resolution;

    @Override
    public void draw(ShopGUI relative, int page, int mouseX, int mouseY, float partialTicks, DrawType dt) {

        ScaledResolution resolution = new ScaledResolution(relative.mc, relative.mc.displayWidth, relative.mc.displayHeight);

        if (dt == DrawType.BG) {

            // relative.drawRect(30, (int) (resolution.getScaledHeight() * 0.1) + 15+24, resolution.getScaledWidth()-30,  (int) ( (resolution.getScaledHeight()) - (resolution.getScaledHeight() * 0.1) ) - 5, 1258291200);
            relative.drawGradientRectAccess(30, (int) (resolution.getScaledHeight() * 0.1) + 19 + 20, resolution.getScaledWidth() - 30, (int) ((resolution.getScaledHeight()) - (resolution.getScaledHeight() * 0.1)) - 5, -1072689136, -804253680);

        } else if (dt == DrawType.PRE) {

            if (page < list.size()) {

                drawPrivilegie(relative, resolution, 0, mouseX, mouseY, partialTicks,  list.get(page), 0, 0);
                
            }

        } else if (dt == DrawType.POST) {

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

            var18.addVertexWithUV((double) 30, (double) ((int) (resolution.getScaledHeight() * 0.1) + 19 + 20 + var20), 0.0D, 0.0D, 1.0D);
            var18.addVertexWithUV((double) (int) resolution.getScaledWidth() - 30, (double) ((resolution.getScaledHeight() * 0.1) + 19 + 20 + var20), 0.0D, 1.0D, 1.0D);

            var18.setColorRGBA_I(0, 255);

            var18.addVertexWithUV((double) resolution.getScaledWidth() - 30, (double) (int) (resolution.getScaledHeight() * 0.1) + 19 + 20, 0.0D, 1.0D, 0.0D);
            var18.addVertexWithUV((double) 30, (double) (int) (resolution.getScaledHeight() * 0.1) + 19 + 20, 0.0D, 0.0D, 0.0D);

            var18.draw();


            var18.startDrawingQuads();

            var18.setColorRGBA_I(0, 255);

            var18.addVertexWithUV((double) 30, (double) ((resolution.getScaledHeight()) - (resolution.getScaledHeight() * 0.1)) - 6, 0.0D, 0.0D, 1.0D);
            var18.addVertexWithUV((double) resolution.getScaledWidth() - 30, (double) ((resolution.getScaledHeight()) - (resolution.getScaledHeight() * 0.1)) - 6, 0.0D, 1.0D, 1.0D);

            var18.setColorRGBA_I(0, 0);

            var18.addVertexWithUV((double) resolution.getScaledWidth() - 30, (double) (((resolution.getScaledHeight()) - (resolution.getScaledHeight() * 0.1)) - 6 - var20), 0.0D, 1.0D, 0.0D);
            var18.addVertexWithUV((double) 30, (double) (((resolution.getScaledHeight()) - (resolution.getScaledHeight() * 0.1)) - 6 - var20), 0.0D, 0.0D, 0.0D);

            var18.draw();

            //

            GL11.glEnable(GL11.GL_TEXTURE_2D);
            GL11.glShadeModel(GL11.GL_FLAT);
            GL11.glEnable(GL11.GL_ALPHA_TEST);
            GL11.glDisable(GL11.GL_BLEND);

        }

    }

	public int x_offset ;
	int y_offset ;
	 
	ArrayList listDescription = new ArrayList();
	
	int maxStringLength = 0 ;
	int maxStringWidth = 0 ;

	String [ ] strings ;

	public void drawPrivilegie ( ShopGUI relative, ScaledResolution resolution, int m_Page, int mouseX, int mouseY, float partialTicks, PrivilegieInfo _info, int gridI, int gridJ) {

	   x_offset = buyButton . xPosition = (int) ( resolution . getScaledWidth ( ) / 2 ) - ( maxStringWidth + 75 + 5 ) / 2; 
       y_offset = (int) (resolution.getScaledHeight() * 0.30);
       
 	   RenderHelper.enableGUIStandardItemLighting();
 	  
       //  GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
       GL11.glBindTexture(GL11.GL_TEXTURE_2D, ClientProxy.getImage(_info.merch_id).getGlTextureId());
       relative.drawTexturedModalRectNormal(x_offset, y_offset, 75, 75);
       
       relative.drawString(relative.getFontRenderer(), _info.name, x_offset + (75/2)- relative.getFontRenderer().getStringWidth(_info.name)/2, y_offset + 80, 0xFFFFFF);
       relative . moneyArea . drawPriceArea ( x_offset, y_offset + 75+20+24, _info . cost, _info . getMoneyType ( ) ) ;

	   list . clear ( ) ;

	   maxStringWidth = 0 ;
	   
	   strings = _info.description.split("\r\n");

       for (int i = 0; i < strings.length; ++i) {

    	   listDescription.add(strings[i]);
           
           if ( maxStringWidth < relative . getFontRenderer ( ) . getStringWidth ( strings [ i ] )) {
        	   
        	   maxStringWidth = relative . getFontRenderer ( ) . getStringWidth ( strings [ i ] ) ;
        	   
           }
           
       }
       
       relative.drawHoveringText(listDescription, x_offset + 75, y_offset+15, relative.getFontRenderer());

       RenderHelper.disableStandardItemLighting();

	}
	
    public BuyButton buyButton ;

    List<PrivilegieInfo> list = new ArrayList<PrivilegieInfo>();
    Map<Integer, BuyButton> buttonsMap = new HashMap<Integer, BuyButton>(); // holy shi~

    int lastPage = 0;

    @Override
    public void updateButtons(ShopGUI relative, int page) {
        
		for ( PrivilegieInfo ri : list ) {
			
			if ( buttonsMap . containsKey ( ri.merch_id ) ) relative . removeButton ( buttonsMap . get ( ri . merch_id ) ) ;

    	}
		
		buttonsMap . clear ( ) ;
    	list . clear ( ) ;
    	
		if ( MineDonate . shops . containsKey ( gui . getCurrentShopId ( ) ) ) {

	    	if ( search ) {
	    		
	    		for ( Merch ri:  MineDonate . shops . get ( gui . getCurrentShopId ( ) ) . cats [ catId ] . getMerch ( ) ) {
	    			
	        		if ( ( ( PrivilegieInfo ) ri ) . name . contains ( searchValue ) ) {
	        			
	        			list . add (  ( PrivilegieInfo ) ri ) ;
	        			
	        		}
	        		
	        	}
	    		
	    	} else {
	    		
	    		for ( Merch ri: MineDonate . shops . get ( gui . getCurrentShopId ( ) ) . cats [ catId ] . getMerch ( ) ) {
	    			
	    			list . add ( ( PrivilegieInfo ) ri ) ;
	  
	        	}
	    		
	    		lastPage = page ; 
	    		
	    	}
    	        
		}
		
		
        if (page < list.size()) {
        	
            PrivilegieInfo info = list.get(page);
            ScaledResolution resolution = new ScaledResolution(relative.mc, relative.mc.displayWidth, relative.mc.displayHeight);// bull shit

            int y_offset = (int) (resolution.getScaledHeight() * 0.30);
                       
            buyButton = new BuyButton( info . getShopId ( ), info . getCategory ( ), info . merch_id, ShopGUI.getNextButtonId(), (buyButton!=null?buyButton.xPosition: resolution . getScaledWidth ( ) / 2 - MineDonate.cfgUI.cats.privelegies.itemBuyButton.width / 2), y_offset +  93, MineDonate.cfgUI.cats.privelegies.itemBuyButton.width, MineDonate.cfgUI.cats.privelegies.itemBuyButton.height, MineDonate.cfgUI.cats.privelegies.itemBuyButton.text);
            buttonsMap.put(info.merch_id, buyButton);

            relative . addBtn ( buyButton ) ;
            
        }
    }
    
    @Override 
	public void setSubCategory ( int _subCatId ) { }
    
	@Override
	public int getButtonWidth ( ) {
		
		return MineDonate.cfgUI.cats.privelegies.categoryButtonWidth;
		
	}
	
	@Override 
	public String getButtonText ( ) {
		
		return MineDonate.cfgUI.cats.privelegies.categoryButtonText ;
		
	}

}

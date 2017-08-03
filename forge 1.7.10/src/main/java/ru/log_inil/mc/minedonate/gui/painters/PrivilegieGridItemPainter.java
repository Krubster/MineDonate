package ru.log_inil.mc.minedonate.gui.painters;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.RenderHelper;
import ru.alastar.minedonate.MineDonate;
import ru.alastar.minedonate.gui.ShopGUI;
import ru.alastar.minedonate.gui.categories.PrivilegieCategory;
import ru.alastar.minedonate.merch.Merch;
import ru.alastar.minedonate.merch.info.EntityInfo;
import ru.alastar.minedonate.merch.info.PrivilegieInfo;
import ru.alastar.minedonate.proxies.ClientProxy;

public class PrivilegieGridItemPainter implements IGridItemPainter {

	PrivilegieCategory pc ;
	
	public PrivilegieGridItemPainter ( PrivilegieCategory _pc ) {
		
		pc = _pc ;
		
	}
	
	public int x_offset ;
	int y_offset ;
	 
	PrivilegieInfo info ;
	ArrayList list = new ArrayList();
	
	int maxStringLength = 0 ;
	int maxStringWidth = 0 ;

	String [ ] strings ;

	@Override
	public void draw ( ShopGUI relative, ScaledResolution resolution, int m_Page, int mouseX, int mouseY, float partialTicks, Merch _info, int gridI, int gridJ) {

	   info = ( PrivilegieInfo ) _info;

	   x_offset = pc . bb . xPosition = (int) ( resolution . getScaledWidth ( ) / 2 ) - ( maxStringWidth + 75 + 5 ) / 2; 
       y_offset = (int) (resolution.getScaledHeight() * 0.30);
       
 	   RenderHelper.enableGUIStandardItemLighting();
 	  
       GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
       GL11.glBindTexture(GL11.GL_TEXTURE_2D, ClientProxy.getImage(info.merch_id).getGlTextureId());
       relative.drawTexturedModalRectNormal(x_offset, y_offset, 75, 75);
       
       relative.drawString(relative.getFontRenderer(), info.name, x_offset + (75/2)- relative.getFontRenderer().getStringWidth(info.name)/2, y_offset + 80, 0xFFFFFF);
       relative . moneyArea . drawPriceArea ( x_offset, y_offset + 75+20+24, info . cost, info . getMoneyType ( ) ) ;

	   list . clear ( ) ;

	   maxStringWidth = 0 ;
	   
	   strings = info.description.split("\r\n");

       for (int i = 0; i < strings.length; ++i) {

    	   // list.add(strings[i]);
           
           if ( maxStringWidth < relative . getFontRenderer ( ). getStringWidth ( strings [ i ] )) {
        	   
        	   maxStringWidth = relative . getFontRenderer ( ). getStringWidth ( strings [ i ] ) ;
        	   
           }
           
       }
       
       relative.drawHoveringText(list, x_offset + 75, y_offset+15, relative.getFontRenderer());

       RenderHelper.disableStandardItemLighting();
       
	}

}

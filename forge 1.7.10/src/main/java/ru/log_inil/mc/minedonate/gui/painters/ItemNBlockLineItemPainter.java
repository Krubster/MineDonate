package ru.log_inil.mc.minedonate.gui.painters;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import net.minecraft.client.renderer.RenderHelper;
import ru.alastar.minedonate.MineDonate;
import ru.alastar.minedonate.gui.ShopCategory;
import ru.alastar.minedonate.merch.info.ItemInfo;
import ru.log_inil.mc.minedonate.gui.GuiItems;
import ru.log_inil.mc.minedonate.gui.ILineItemPainter;

public class ItemNBlockLineItemPainter implements ILineItemPainter {

	ItemInfo info ;
	
	ArrayList list = new ArrayList();
	
	ShopCategory sc ;
	
	public ItemNBlockLineItemPainter ( ShopCategory _sc, ItemInfo _ii ) {
		
		sc = _sc ;
		info = _ii ;
		
	}
	

	@Override
	public void draw(GuiItems relative, int x_offset, int y_offset, int mouseX, int mouseY, float partialTicks ) {

	  String tmp = MineDonate.cfgUI.cats.itemsAndBlocks.pricePrefix + 
			  (info.cost * info.modified) + MineDonate.cfgUI.cats.itemsAndBlocks.priceSuffix ;
		
	  GL11.glEnable(GL12.GL_RESCALE_NORMAL); // #LOG
	  RenderHelper.enableGUIStandardItemLighting();
	        
	  relative.parent.drawString(relative.getFontRenderer(),  info.name, 65, y_offset+8, 16777215);
	  
	  relative.parent.drawCenteredString(relative.getFontRenderer(), tmp, x_offset-70-40, y_offset+8, 16777215);

      if (info.limit != -1) {
    	  
    	  relative.parent.drawCenteredString(relative.getFontRenderer(), MineDonate.cfgUI.cats.itemsAndBlocks.itemLeft + info.limit, 
    			  x_offset-80-40-relative.getFontRenderer().getStringWidth(tmp), y_offset + 8, 16777215);
     
      }
	
      relative.parent.getItemRender().renderItemAndEffectIntoGUI(relative.getFontRenderer(), relative.parent.mc.getTextureManager(), info.m_stack, 40, y_offset + 3 );
      relative.parent.getItemRender().renderItemOverlayIntoGUI(relative.getFontRenderer(), relative.parent.mc.getTextureManager(), info.m_stack, 40, y_offset + 3, info.modified * info.stack_data.getInteger("Count") + "");
      
      RenderHelper.disableStandardItemLighting();
      GL11.glDisable(GL12.GL_RESCALE_NORMAL);
      
	}

	public ArrayList getList ( ) {
		
		return list ;
		
	}
	
}

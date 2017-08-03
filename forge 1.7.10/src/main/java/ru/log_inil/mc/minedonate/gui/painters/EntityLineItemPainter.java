package ru.log_inil.mc.minedonate.gui.painters;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import net.minecraft.client.renderer.RenderHelper;
import ru.alastar.minedonate.MineDonate;
import ru.alastar.minedonate.gui.ShopCategory;
import ru.alastar.minedonate.merch.info.EntityInfo;
import ru.log_inil.mc.minedonate.gui.GuiItems;
import ru.log_inil.mc.minedonate.gui.ILineItemPainter;

public class EntityLineItemPainter implements ILineItemPainter {

	EntityInfo info ;
	
	ArrayList list = new ArrayList();
	
	ShopCategory sc ;
	
	public EntityLineItemPainter ( ShopCategory _sc, EntityInfo _ei ) {
		
		sc = _sc ;
		info = _ei ;
		
	}
	

	@Override
	public void draw(GuiItems relative, int x_offset, int y_offset, int mouseX, int mouseY, float partialTicks ) {

	  GL11.glEnable(GL12.GL_RESCALE_NORMAL); // #LOG
	  RenderHelper.enableGUIStandardItemLighting();
	    
	  relative.parent.drawString(relative.getFontRenderer(), ((EntityInfo)info).name, 40, y_offset+8, 16777215);
  
	  relative.parent.drawCenteredString(relative.getFontRenderer(),  MineDonate.cfgUI.cats.entities.pricePrefix + info.cost + MineDonate.cfgUI.cats.regions.priceSuffix, x_offset-70-5, y_offset + 8, 16777215);

      RenderHelper.disableStandardItemLighting();
      GL11.glDisable(GL12.GL_RESCALE_NORMAL);
      
	}

	public ArrayList getList ( ) {
		
		return list ;
		
	}
	
}
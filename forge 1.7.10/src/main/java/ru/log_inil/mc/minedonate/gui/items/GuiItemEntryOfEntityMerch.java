package ru.log_inil.mc.minedonate.gui.items;

import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLivingBase;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import ru.alastar.minedonate.MineDonate;
import ru.alastar.minedonate.gui.BuyButton;
import ru.alastar.minedonate.gui.ShopCategory;
import ru.alastar.minedonate.gui.ShopGUI;
import ru.alastar.minedonate.merch.info.EntityInfo;
import ru.log_inil.mc.minedonate.gui.GuiAbstractItemEntry;
import ru.log_inil.mc.minedonate.gui.GuiItemsScrollArea;
import ru.log_inil.mc.minedonate.gui.context.ContextMenu;
import ru.log_inil.mc.minedonate.gui.context.ContextElement;

public class GuiItemEntryOfEntityMerch extends GuiAbstractItemEntry {

	public EntityInfo info ;
	public ShopCategory sc ;
	
	BuyButton buy ;

	public GuiItemEntryOfEntityMerch ( EntityInfo _eim, ShopCategory _sc ) {

		info = _eim ;
		sc = _sc ;

	}

	String costLine, limitLine;
	boolean updateDataNeed = false ;
	
	@Override
	public GuiAbstractItemEntry updateDrawData ( ) {
		
		super . updateDrawData ( ) ;
		
		limitLine = MineDonate.cfgUI.cats.entities.itemLeft + info.limit;
		updateDataNeed = true ;
		
		return this ;
		
	}
	
	@Override 
	public void draw ( GuiItemsScrollArea gi, int x_offset, int y_offset, int xRightOffset, int mouseX, int mouseY, Tessellator var5, int index, int size ) {

		super.draw(gi, x_offset, y_offset, xRightOffset, mouseX, mouseY, var5, index, size);
		
		xOffset = x_offset ;
		yOffset = y_offset ;
		
		super . updateContextMenu ( ) ;
		
		if ( buy != null ) {
			
			buy . yPosition = y_offset + 2 ;

		}
		
		if ( updateDataNeed ) {
			
			this . updateSize ( xRightOffset - x_offset, 30 ) ;

			updateDataNeed = false ;
			
		}
		
		GL11 . glEnable ( GL12 . GL_RESCALE_NORMAL ) ;
		RenderHelper . enableGUIStandardItemLighting ( ) ;

		//
		renderEntity(55, y_offset + 25, 13 /* scale */, 55 + 55, y_offset - 200, info.entity);
		gi.parent.drawCenteredString(gi.getFontRenderer(), info.name, 40 + 55, y_offset + 8, 16777215);
		gi . parent . moneyArea . drawPriceArea ( x_offset - 70 - 15 - 20, y_offset + 8, info . cost, info . getMoneyType ( ) ) ;

		if (info.limit != -1) {

			gi.parent.drawCenteredString(gi.getFontRenderer(), limitLine, x_offset - 80 - 50 - 50 - 10, y_offset + 8, 16777215); // - costLineWidth

		}

		//gi . parent . drawCenteredString ( gi . getFontRenderer ( ), costLine, x_offset - 70 - 15, y_offset + 8, 16777215 ) ;

		//
		
		RenderHelper . disableStandardItemLighting ( ) ;
		GL11 . glDisable ( GL12 . GL_RESCALE_NORMAL ) ;
	    
		//
		this . drawHorizontalLine ( gi . parent, 40, xRightOffset - 10, y_offset - 3, 520093695 ) ;

  		if ( index + 1 == size && y_offset + 30 < gi . bottom ) {
  			
  			this . drawHorizontalLine ( gi . parent, 40, xRightOffset - 10, y_offset - 3 + 30, 520093695 ) ;

  		}
		
	}

	private void renderEntity(int p_147046_0_, int p_147046_1_, int p_147046_2_, float p_147046_3_, float p_147046_4_, EntityLivingBase p_147046_5_) {
		GL11.glEnable(2903);
		GL11.glPushMatrix();
		GL11.glTranslatef((float) p_147046_0_, (float) p_147046_1_, 50.0F);
		GL11.glScalef((float) (-p_147046_2_), (float) p_147046_2_, (float) p_147046_2_);
		GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
		float var6 = p_147046_5_.renderYawOffset;
		float var7 = p_147046_5_.rotationYaw;
		float var8 = p_147046_5_.rotationPitch;
		float var9 = p_147046_5_.prevRotationYawHead;
		float var10 = p_147046_5_.rotationYawHead;
		GL11.glRotatef(135.0F, 0.0F, 1.0F, 0.0F);
		RenderHelper.enableStandardItemLighting();
		GL11.glRotatef(-135.0F, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(-((float) Math.atan((double) (p_147046_4_ / 40.0F))) * 20.0F, 1.0F, 0.0F, 0.0F);
		p_147046_5_.renderYawOffset = (float) Math.atan((double) (p_147046_3_ / 40.0F)) * 20.0F;
		p_147046_5_.rotationYaw = (float) Math.atan((double) (p_147046_3_ / 40.0F)) * 40.0F;
		p_147046_5_.rotationPitch = -((float) Math.atan((double) (p_147046_4_ / 40.0F))) * 20.0F;
		p_147046_5_.rotationYawHead = p_147046_5_.rotationYaw;
		p_147046_5_.prevRotationYawHead = p_147046_5_.rotationYaw;
		GL11.glTranslatef(0.0F, p_147046_5_.yOffset, 0.0F);
		RenderManager.instance.playerViewY = 180.0F;
		RenderManager.instance.renderEntityWithPosYaw(p_147046_5_, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F);
		p_147046_5_.renderYawOffset = var6;
		p_147046_5_.rotationYaw = var7;
		p_147046_5_.rotationPitch = var8;
		p_147046_5_.prevRotationYawHead = var9;
		p_147046_5_.rotationYawHead = var10;
		GL11.glPopMatrix();
		RenderHelper.disableStandardItemLighting();
//		GL11.glDisable('耺');
		OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapTexUnit);
		GL11.glDisable(3553);
		OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit);
	}


	@Override
	public void unDraw ( ) {
		
		super . unDraw ( ) ;
		
		if ( buy != null ) {
			
			buy . yPosition = -100 ;

		}
		
	}
	
	@Override
	public GuiAbstractItemEntry addButtons ( ShopGUI gui ) {

		buy = new BuyButton ( info . getShopId ( ), info . getCategory ( ), info . merch_id, ShopGUI . getNextButtonId ( ), gui . resolution . getScaledWidth ( ) - 91, -100, MineDonate . cfgUI . cats . itemsAndBlocks . itemBuyButton . width, MineDonate . cfgUI . cats . itemsAndBlocks . itemBuyButton . height, MineDonate . cfgUI . cats . itemsAndBlocks . itemBuyButton . text ) ;

        gui . addBtn ( buy ) ;
		if (info.limit != -1 && info.limit == 0) {

			buy.enabled = false;

		}
		return this ;
		
	}

	@Override
	public void onClickContextMenuElement(ContextMenu cmm, ContextElement e) {
		System.err.println(cmm + ">" + e);
	}
	
}

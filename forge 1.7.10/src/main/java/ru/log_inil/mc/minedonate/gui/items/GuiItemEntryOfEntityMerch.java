package ru.log_inil.mc.minedonate.gui.items;

import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLivingBase;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import ru.alastar.minedonate.MineDonate;
import ru.alastar.minedonate.gui.BuyButton;
import ru.alastar.minedonate.gui.ShopCategory;
import ru.alastar.minedonate.gui.ShopGUI;
import ru.alastar.minedonate.merch.info.EntityInfo;
import ru.log_inil.mc.minedonate.gui.DrawType;
import ru.log_inil.mc.minedonate.gui.GuiAbstractItemEntry;
import ru.log_inil.mc.minedonate.gui.GuiItemsScrollArea;
import ru.log_inil.mc.minedonate.gui.context.ContextMenu;
import ru.log_inil.mc.minedonate.gui.context.ContextMenuManager;
import ru.log_inil.mc.minedonate.gui.frames.GuiFrameDeleteEntity;
import ru.log_inil.mc.minedonate.gui.frames.GuiFrameEditEntity;
import ru.log_inil.mc.minedonate.gui.context.ContextElement;

public class GuiItemEntryOfEntityMerch extends GuiAbstractItemEntry {

	public EntityInfo info ;
	public ShopCategory sc ;
	
	BuyButton buy ;

	public GuiItemEntryOfEntityMerch ( EntityInfo _eim, ShopCategory _sc ) {

		info = _eim ;
		sc = _sc ;
		
		String owner = MineDonate . shops . get ( info . shopId ) . owner ;
		if ( MineDonate . getAccount ( ) . canEditShop ( owner ) ) {
			
			List < ContextElement > cElements = new ArrayList < > ( ) ;
	
			cElements . add ( new ContextElement ( 0, "edit", MineDonate.cfgUI.lang.editEntityMerch, this, 10 ) ) ;
			cElements . add ( new ContextElement ( 1, "delete", MineDonate.cfgUI.lang.deleteEntityMerch, this, 10 ) ) ;
	
			cmm = new ContextMenu ( 1, 1, cElements ) ;
			
			ContextMenuManager . addNewMenu ( cmm ) ;
			
		}
		
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
	
	float rotate = 20f ;
	float scale = 13f ;
	
	@Override 
	public void draw ( GuiItemsScrollArea gi, int x_offset, int y_offset, int xRightOffset, int mouseX, int mouseY, Tessellator var5, DrawType dt, int index, int size ) {

		super.draw(gi, x_offset, y_offset, xRightOffset, mouseX, mouseY, var5, dt, index, size);
		
		xOffset = x_offset ;
		yOffset = y_offset ;
		
		super . updateContextMenu ( ) ;

		if ( dt == DrawType . PRE ) {

			if ( buy != null ) {
				
				buy . yPosition = y_offset + 2 ;

			}
			
			if ( updateDataNeed ) {
				
				this . updateSize ( xRightOffset - x_offset, 28 ) ;

				updateDataNeed = false ;
				
			}
			
			GL11 . glEnable ( GL12 . GL_RESCALE_NORMAL ) ;
			RenderHelper . enableGUIStandardItemLighting ( ) ;

			//
			
			gi . parent . drawCenteredString(gi.getFontRenderer(), info.name, 40 + 55, y_offset + 8, 16777215);
			gi . parent . moneyArea . drawPriceArea ( xRightOffset - 70 - 15 - 20, y_offset + 8, info . cost, info . getMoneyType ( ) ) ;

			if (info.limit != -1) {

				gi.parent.drawCenteredString(gi.getFontRenderer(), limitLine, xRightOffset - 80 - 50 - 50 - 10, y_offset + 8, 16777215); // - costLineWidth

			}

			//
			
			RenderHelper . disableStandardItemLighting ( ) ;
			GL11 . glDisable ( GL12 . GL_RESCALE_NORMAL ) ;
		    
			//
			
			this . drawHorizontalLine ( gi . parent, 40, xRightOffset - 10, y_offset - 3, 520093695 ) ;

	  		if ( index + 1 == size && y_offset + 30 < gi . bottom ) {
	  			
	  			this . drawHorizontalLine ( gi . parent, 40, xRightOffset - 10, y_offset - 3 + 30, 520093695 ) ;

	  		}
	  		
		} else if ( dt == DrawType . OVERLAY ) {
			
			if ( info . entity!= null ) {
				
				if ( ( mouseX >= 40 && 70 >= mouseX ) && ( mouseY >= y_offset - 2 && y_offset + 25 >= mouseY ) ) {

					rotate -= 0.4f ;
					
					if ( scale >= 30f ) {
						
						scale = 30f ;
						
					} else {
						
						scale += 0.9f ;
						
					}
										
					if ( rotate <= -275f ) {
						
						rotate = 20f ;
						
					}
					
				} else {
					
					if ( rotate >= 20f ) {
						
						rotate = 20f ;
						
					} else {
						
						rotate += 2.9f ;
						
					}
					
					if ( scale <= 13 ) {
						
						scale = 13f ;
						
					} else {
						
						scale -= 0.7f ;
						
					}
					
				}
								
				renderEntity ( 55, y_offset + 25, scale, 55 + 55, -18f, ( index + 1 ) * 50f, rotate, info . entity ) ;
						    
			}
			
		}
		
	}

	private void renderEntity(int p_147046_0_, int p_147046_1_, float scale, float p_147046_3_, float p_147046_4_, float z, float rotate, EntityLivingBase p_147046_5_) {
		GL11.glEnable(2903);
		GL11.glPushMatrix();
		GL11.glTranslatef((float) p_147046_0_, (float) p_147046_1_, z);//50.0F
		GL11.glScalef((-scale), scale, scale);
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
		p_147046_5_.renderYawOffset = (float) Math.atan((double) (p_147046_3_ / 40.0F)) * rotate;//20F
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
		GL11.glDisable(2903);

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

        gui . addButton ( buy, false ) ;
		if (info.limit != -1 && info.limit == 0) {

			buy.enabled = false;

		}
		return this ;
		
	}

	@Override 
	public void postShow ( GuiItemsScrollArea gui ) { 
		
		super . postShow ( gui ) ;
		
		updateContextMenu ( ) ;
		
	}
	
	@Override 
	public void updateContextMenu ( ) {
		
		super . updateContextMenu ( ) ;
		
	}
	
	@Override
	public void onClickContextMenuElement(ShopGUI g, ContextMenu cmm, ContextElement e) {
		
		if ( e != null && info != null ) {
			
			switch ( e . name ) {
				
				case "edit" :
					
					GuiFrameEditEntity gfre = ( GuiFrameEditEntity ) g . showEntry ( "frame.entity.edit", true ) ;	
					
					gfre . setInfo ( info . shopId, info . catId, info . merch_id, info . cost ) ;
					gfre . setFieldData ( info . name, gfre . fieldHolder ) ;
					
				break ;
				
				case "delete" :
					
					GuiFrameDeleteEntity gfde = ( GuiFrameDeleteEntity ) g . showEntry ( "frame.entity.delete", true ) ;	
					
					gfde . setInfo ( info . shopId, info . catId, info . merch_id ) ;
					gfde . setConfirmCode ( Integer . toString ( Math . abs ( info . hashCode ( ) ) ) . substring ( 0, 3 ) ) ;
					
				break ;
				
			}
			
		}
		
	}

}

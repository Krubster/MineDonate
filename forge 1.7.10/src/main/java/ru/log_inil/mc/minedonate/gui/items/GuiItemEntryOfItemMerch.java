package ru.log_inil.mc.minedonate.gui.items;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.item.ItemStack;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import ru.alastar.minedonate.MineDonate;
import ru.alastar.minedonate.gui.BuyButton;
import ru.alastar.minedonate.gui.CountButton;
import ru.alastar.minedonate.gui.ShopCategory;
import ru.alastar.minedonate.gui.ShopGUI;
import ru.alastar.minedonate.merch.info.ItemInfo;
import ru.log_inil.mc.minedonate.gui.DrawType;
import ru.log_inil.mc.minedonate.gui.GuiAbstractItemEntry;
import ru.log_inil.mc.minedonate.gui.GuiItemsScrollArea;
import ru.log_inil.mc.minedonate.gui.context.ContextElement;
import ru.log_inil.mc.minedonate.gui.context.ContextMenu;
import ru.log_inil.mc.minedonate.gui.context.ContextMenuManager;
import ru.log_inil.mc.minedonate.gui.frames.GuiFrameDeleteItem;
import ru.log_inil.mc.minedonate.gui.frames.GuiFrameEditItem;

import java.util.ArrayList;
import java.util.List;

public class GuiItemEntryOfItemMerch extends GuiAbstractItemEntry {

	public ItemInfo info ;
	public ShopCategory sc ;
	
	CountButton pl, min;
	BuyButton buy ;

	public GuiItemEntryOfItemMerch ( ItemInfo _iim, ShopCategory _sc ) {

		info = _iim ;
		sc = _sc ;
		
		String owner = MineDonate . shops . get ( info . shopId ) . owner ;
		if ( MineDonate . getAccount ( ) . canEditShop ( owner ) ) {
			
			List < ContextElement > cElements = new ArrayList < > ( ) ;
	
			cElements . add ( new ContextElement ( 0, "edit",  MineDonate.cfgUI.lang.editItemMerch, this, 10 ) ) ;
			cElements . add ( new ContextElement ( 1, "delete", MineDonate.cfgUI.lang.deleteItemMerch, this, 10 ) ) ;
	
			cmm = new ContextMenu ( 1, 1, cElements ) ;
			
		}
		
	}
	
	String limitLine, stackCountLine ;
	boolean updateDataNeed = false ;
	
	@Override
	public GuiAbstractItemEntry updateDrawData ( ) {
		
		limitLine = MineDonate . cfgUI . cats . itemsAndBlocks . itemLeft + info . limit ;
		stackCountLine = Integer . toString ( info . modified * info.stack_data.getInteger("Count") ) ;
		
		ShopGUI.instance.getContextMenuManager().addNewMenu(cmm);

		updateDataNeed = true ;
		
		return this ;
		
	}
	
	@Override 
	public void draw ( GuiItemsScrollArea gi, int x_offset, int y_offset, int xRightOffset, float partialTicks, int mouseX, int mouseY, Tessellator var5, DrawType dt, int index, int size ) {
	
		super.draw(gi, x_offset, y_offset, xRightOffset, partialTicks, mouseX, mouseY, var5, dt, index, size);

		if ( pl != null ) {
			
			pl . yPosition = y_offset + 2 ; 
			min . yPosition = y_offset + 2 ;
			buy . yPosition = y_offset + 2 ;

		}
		
		if ( updateDataNeed ) {
			
			this . updateSize ( xRightOffset - x_offset, 28 ) ;

			updateDataNeed = false ;
			
		}
		
	//	GL11 . glDisable ( GL11.GL_LIGHTING ) ;

		if ( dt == DrawType . PRE ) {
			
			GL11 . glEnable ( GL12 . GL_RESCALE_NORMAL ) ;
			RenderHelper . enableGUIStandardItemLighting ( ) ;
			
			//

			gi . parent . drawString ( gi . getFontRenderer ( ), info . name, 67, y_offset + 8, 16777215 ) ;
			gi . parent . moneyArea . drawPriceArea ( xRightOffset - 70 - 50 - 20, y_offset + 8, ( info . cost * info . modified ), info . getMoneyType ( ) ) ;

			if ( info . limit != -1 ) {
			  //80 - 50 - 50 - 20
				gi . parent . drawCenteredString ( gi . getFontRenderer ( ), limitLine, xRightOffset - MineDonate.cfgUI.offsets.itemLimitLine.x, y_offset - MineDonate.cfgUI.offsets.itemLimitLine.y, 16777215 ) ; // - costLineWidth

			}
			
			//
			
			GL11 . glPushMatrix ( ) ; 
			
			GL11 . glTranslatef ( 40, y_offset + 2, 0 ) ;
			GL11 . glScalef ( 1.2f, 1.2f, 1f ) ;  
			
			gi . parent . getItemRender ( ) . renderItemAndEffectIntoGUI ( gi . getFontRenderer ( ), gi . parent . mc . getTextureManager ( ), info . m_stack, 0, 0 ) ;
		
			GL11 . glTranslatef ( -2.3f, 4, 0 ) ;
			GL11 . glScalef ( 1.24f, 1.0f, 1f ) ;  

			renderItemOverlayIntoGUI ( gi . getFontRenderer ( ), gi . parent . mc . getTextureManager ( ), info . m_stack, 0,  0, stackCountLine, false ) ;
			
			GL11 . glPopMatrix ( ) ;
			
			renderItemOverlayIntoGUI ( gi . getFontRenderer ( ), gi . parent . mc . getTextureManager ( ), info . m_stack, 43, y_offset + 5, stackCountLine, true ) ;

			//
			
			GL11 . glDisable ( GL12 . GL_RESCALE_NORMAL ) ;

			//
			this . drawHorizontalLine ( gi . parent, 40, xRightOffset - 10, y_offset - 3, 520093695 ) ;

	  		if ( index + 1 == size && y_offset + 30 < gi . bottom ) {
	  			
	  			this . drawHorizontalLine ( gi . parent, 40, xRightOffset - 10, y_offset - 3 + 30, 520093695 ) ;

	  		}
	  		
			RenderHelper . disableStandardItemLighting ( ) ;
			
		} else if ( dt == DrawType . OVERLAY_PRE ) {
			
			if ( mouseX >= 40 && 60 >= mouseX ) {
				
				if ( mouseY >= y_offset - 2 && y_offset + 20 >= mouseY ) {
					
					gi.parent.renderToolTip(info.m_stack, mouseX, mouseY);
					RenderHelper . disableStandardItemLighting ( ) ;

				}
				
			}
			
		}

	}
	
	// RenderItem 700+
    public void renderItemOverlayIntoGUI(FontRenderer p_94148_1_, TextureManager p_94148_2_, ItemStack p_94148_3_, int p_94148_4_, int p_94148_5_, String p_94148_6_, boolean t)
    {
        if (p_94148_3_ != null)
        {
            if ( t ) {
            	
            	if (p_94148_3_.stackSize > 1 || p_94148_6_ != null)
                {
                    String s1 = p_94148_6_ == null ? String.valueOf(p_94148_3_.stackSize) : p_94148_6_;
                    GL11.glDisable(GL11.GL_LIGHTING);
                    GL11.glDisable(GL11.GL_DEPTH_TEST);
                    GL11.glDisable(GL11.GL_BLEND);
                    p_94148_1_.drawStringWithShadow(s1, p_94148_4_ + 19 - 2 - p_94148_1_.getStringWidth(s1), p_94148_5_ + 6 + 3, 16777215);
                    GL11.glEnable(GL11.GL_LIGHTING);
                    GL11.glEnable(GL11.GL_DEPTH_TEST);
                }
            	
            } else if (p_94148_3_.getItem().showDurabilityBar(p_94148_3_))
            {
                double health = p_94148_3_.getItem().getDurabilityForDisplay(p_94148_3_);
                int j1 = (int)Math.round(13.0D - health * 13.0D);
                int k = (int)Math.round(255.0D - health * 255.0D);
                GL11.glDisable(GL11.GL_LIGHTING);
                GL11.glDisable(GL11.GL_DEPTH_TEST);
                GL11.glDisable(GL11.GL_TEXTURE_2D);
                GL11.glDisable(GL11.GL_ALPHA_TEST);
                GL11.glDisable(GL11.GL_BLEND);
                Tessellator tessellator = Tessellator.instance;
                int l = 255 - k << 16 | k << 8;
                int i1 = (255 - k) / 4 << 16 | 16128;
                renderQuad(tessellator, p_94148_4_ + 2, p_94148_5_ + 13, 13, 2, 0);
                this.renderQuad(tessellator, p_94148_4_ + 2, p_94148_5_ + 13, 12, 1, i1);
                this.renderQuad(tessellator, p_94148_4_ + 2, p_94148_5_ + 13, j1, 1, l);
                //GL11.glEnable(GL11.GL_BLEND); // Forge: Disable Bled because it screws with a lot of things down the line.
                GL11.glEnable(GL11.GL_ALPHA_TEST);
                GL11.glEnable(GL11.GL_TEXTURE_2D);
                GL11.glEnable(GL11.GL_LIGHTING);
                GL11.glEnable(GL11.GL_DEPTH_TEST);
                GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            }
        }
    }
    
    // RenderItem 745+
    private void renderQuad(Tessellator p_77017_1_, int p_77017_2_, int p_77017_3_, int p_77017_4_, int p_77017_5_, int p_77017_6_)
    {
        p_77017_1_.startDrawingQuads();
        p_77017_1_.setColorOpaque_I(p_77017_6_);
        p_77017_1_.addVertex((double)(p_77017_2_ + 0), (double)(p_77017_3_ + 0), 0.0D);
        p_77017_1_.addVertex((double)(p_77017_2_ + 0), (double)(p_77017_3_ + p_77017_5_), 0.0D);
        p_77017_1_.addVertex((double)(p_77017_2_ + p_77017_4_), (double)(p_77017_3_ + p_77017_5_), 0.0D);
        p_77017_1_.addVertex((double)(p_77017_2_ + p_77017_4_), (double)(p_77017_3_ + 0), 0.0D);
        p_77017_1_.draw();
    }
    
	@Override
	public void unDraw ( ) {

		super . unDraw ( ) ;

		if ( pl != null ) {

			pl . yPosition = -100 ;
			min . yPosition = -100 ;
			buy . yPosition = -100 ;

		}
		
	}
	
	@Override
	public GuiAbstractItemEntry addButtons ( ShopGUI gui ) {
				
		buy = new BuyButton ( info . getShopId ( ), info . getCategory ( ), info . getId ( ), ShopGUI . getNextButtonId ( ), gui . resolution . getScaledWidth ( ) - 91, -100, MineDonate . cfgUI . cats.itemsAndBlocks . itemBuyButton . width, MineDonate . cfgUI . cats . itemsAndBlocks . itemBuyButton . height, MineDonate . cfgUI . cats . itemsAndBlocks . itemBuyButton . text ) ;
        gui . addButton ( buy, false ) ;
        
        min = new CountButton ( -1, info, ShopGUI . getNextButtonId ( ), buy . xPosition - 1 - 16, - 100, 16, 20, "-" ) ;
        min . setEntityOnUpdate ( this ) ;
        
        gui . addButton ( min, false ) ;
        
        pl = new CountButton ( 1, info, ShopGUI . getNextButtonId ( ), min . xPosition - 1 - 16, -100, 16, 20, "+" ) ;
        pl . setEntityOnUpdate ( this ) ;
        
        gui . addButton ( pl, false ) ;

    	if ( ! pl . canModify ( ) && ! min . canModify ( ) ) {
        	
    		pl . enabled = false ;
    		min . enabled = false ;	

        }
        
        if ( info . limit < 1 && info . limit != -1 ) {
        	
        	buy . enabled = false ;
            pl . enabled = false ;
            min . enabled = false ;
            
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
	public void onClickContextMenuElement ( ShopGUI g, ContextMenu cmm, ContextElement e ) {
		
		if ( e != null && info != null ) {
			
			switch ( e . name ) {
				
				case "edit" :
					
					GuiFrameEditItem gfrs = ( GuiFrameEditItem ) g . showEntry ( "frame.item.edit", true ) ;	

					gfrs . setInfo ( info . shopId, info . catId, info . getId ( ), info . limit, info . cost ) ;
					gfrs . setFieldData ( info . m_stack . getDisplayName ( ) . equals ( info . name ) ? "" :  info . name, info . m_stack . getDisplayName ( ) ) ;
					gfrs . postShow ( g ) ;

				break ;
				
				case "delete" :
					
					GuiFrameDeleteItem gfdi = ( GuiFrameDeleteItem ) g . showEntry ( "frame.item.delete", true ) ;	
					
					gfdi . setInfo ( info . shopId, info . catId, info . getId ( ) ) ;
					gfdi . setConfirmCode ( Integer . toString ( Math . abs ( info . hashCode ( ) ) ) . substring ( 0, 3 ) ) ;
					gfdi . postShow ( g ) ;
					
				break ;
				
			}
			
		}
		
	}
	
}

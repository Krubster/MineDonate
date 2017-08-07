package ru.log_inil.mc.minedonate.gui;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.Tessellator;
import ru.alastar.minedonate.gui.ShopGUI;
import ru.log_inil.mc.minedonate.gui.context.ContextMenu;
import ru.log_inil.mc.minedonate.gui.context.ContextMenuInteract;
import ru.log_inil.mc.minedonate.gui.context.ContextMenuManager;

public abstract class GuiAbstractItemEntry extends MCGuiAccessable implements ContextMenuInteract {

	protected int xOffset, yOffset;
	protected ContextMenu cmm ;
	
	public GuiAbstractItemEntry ( ) {

	}

	public GuiAbstractItemEntry updateDrawData ( ) {
		
		return this ;
		
	}
	
	boolean nextMoveUpdate = false ;
	
	public void move ( int x, int y ) {
		
		if ( x != xOffset || y != yOffset ) {
			
			xOffset = x ;
			yOffset = y ;
			
			if ( cmm != null ) {

				cmm . updateInteractArea ( this, x, y - 4 ) ;
				if ( ! nextMoveUpdate ) nextMoveUpdate = true ;
				
			}
			
		}
		
	}
	
	public void updateSize ( int w, int h ) {
		
		if ( cmm != null ) {

			cmm . updateInteractAreaSizes ( this, w, h ) ;
			
		}
		
	}
	
	GuiItemsScrollArea gi ;
	public void draw ( GuiItemsScrollArea _gi, int var2, int var3, int var4, int mouseX, int mouseY, Tessellator var5, DrawType dt, int index, int size ) {

		gi = _gi ;
		
		move ( var2, var3 ) ;
		
	}

	public void unDraw ( ) {
		
		if ( cmm != null ) {

			ContextMenuManager . removeMenu ( cmm ) ;
			cmm = null ;
			
		}
		
	}
	
	public GuiAbstractItemEntry addButtons ( ShopGUI gui ) { 
		
		return this ;
		
	}
		
	public void drawHorizontalLine ( GuiScreen gs, int p_73730_1_, int p_73730_2_, int p_73730_3_, int p_73730_4_ ) {
		
        if ( p_73730_2_ < p_73730_1_ ) {
        	
            int i1 = p_73730_1_ ;
            p_73730_1_ = p_73730_2_ ;
            p_73730_2_ = i1 ;
            
        }

        gs . drawRect ( p_73730_1_, p_73730_3_, p_73730_2_ + 1, p_73730_3_ + 1, p_73730_4_ ) ;
        
    }

	public void updateContextMenu ( ) {
		
	}

	@Override
	public FontRenderer getFontRenderer ( ) {
		
		return gi . getFontRenderer ( ) ;
		
	}
	
}

package ru.log_inil.mc.minedonate.gui;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.Tessellator;
import ru.alastar.minedonate.gui.ShopGUI;
import ru.log_inil.mc.minedonate.gui.context.ContextMenu;
import ru.log_inil.mc.minedonate.gui.context.ContextMenuInteract;
import ru.log_inil.mc.minedonate.gui.context.ContextMenuManager;

public abstract class GuiAbstractItemEntry implements ContextMenuInteract {

	protected int xOffset, yOffset;
	protected ContextMenu cmm ;
	
	public GuiAbstractItemEntry ( ) {

	}

	public GuiAbstractItemEntry updateDrawData ( ) {
		
		return this ;
		
	}
	
	public void move ( int x, int y ) {
		
		if ( x != xOffset || y != yOffset ) {
			
			xOffset = x ;
			yOffset = y ;
			
			if ( cmm != null ) {

				cmm . updateInteractArea ( x, y - 4 ) ;
				
			}
			
		}
		
	}
	
	public void updateSize ( int w, int h ) {
		
		if ( cmm != null ) {

			cmm . updateInteractAreaSizes ( w, h ) ;
			
		}
		
	}
	
	public void draw ( GuiItemsScrollArea gi, int var2, int var3, int var4, int mouseX, int mouseY, Tessellator var5, int index, int size ) {

		move ( var2, var3 ) ;
		
	}

	public void unDraw ( ) {
		
		if ( cmm != null ) {

			System.err.println("??");
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
	
}

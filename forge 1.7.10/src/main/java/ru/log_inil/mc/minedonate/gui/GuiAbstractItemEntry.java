package ru.log_inil.mc.minedonate.gui;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.Tessellator;
import ru.alastar.minedonate.gui.ShopGUI;

public abstract class GuiAbstractItemEntry {

	public GuiAbstractItemEntry ( ) {

	}

	public GuiAbstractItemEntry updateDrawData ( ) {
		
		return this ;
		
	}
	
	public void draw ( GuiItemsScrollArea gi, int var2, int var3, int var4, Tessellator var5, int index, int size ) {
		
	}

	public void undraw ( ) {
		
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
	
}

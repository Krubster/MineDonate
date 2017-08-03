package ru.log_inil.mc.minedonate.gui;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.Tessellator;

public abstract class GuiEntry {

	// Merch im ;
	
	public GuiEntry ( ) {

		
	}

	public void draw ( GuiItems gi, int var2, int var3, int var4, Tessellator var5 ) {
		
       //  gi.parent.drawString(gi.getFR(), gi.getFR().trimStringToWidth("test", gi.listWidth - 10), gi.left + 3 , var3 + 2, 0xFF2222);

	}

	public void undraw ( ) {
		
	}
	
	
	public void drawHorizontalLine(GuiScreen gs, int p_73730_1_, int p_73730_2_, int p_73730_3_, int p_73730_4_)
    {
        if (p_73730_2_ < p_73730_1_)
        {
            int i1 = p_73730_1_;
            p_73730_1_ = p_73730_2_;
            p_73730_2_ = i1;
        }

        gs.drawRect(p_73730_1_, p_73730_3_, p_73730_2_ + 1, p_73730_3_ + 1, p_73730_4_);
    }
	
}

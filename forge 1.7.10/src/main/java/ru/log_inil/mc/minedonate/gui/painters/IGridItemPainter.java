package ru.log_inil.mc.minedonate.gui.painters;

import net.minecraft.client.gui.ScaledResolution;
import ru.alastar.minedonate.gui.ShopGUI;
import ru.alastar.minedonate.merch.Merch;

public interface IGridItemPainter {

	public void draw ( ShopGUI relative, ScaledResolution sc, int m_Page, int mouseX, int mouseY, float partialTicks, Merch merch, int gridI, int gridJ ) ;
	
}

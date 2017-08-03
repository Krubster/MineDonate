package ru.log_inil.mc.minedonate.gui.painters;

import java.util.ArrayList;

import net.minecraft.client.gui.ScaledResolution;
import ru.alastar.minedonate.MineDonate;
import ru.alastar.minedonate.gui.ShopCategory;
import ru.alastar.minedonate.gui.ShopGUI;
import ru.alastar.minedonate.merch.Merch;
import ru.alastar.minedonate.merch.info.ItemInfo;
import ru.alastar.minedonate.merch.info.RegionInfo;

public class RegionGridItemPainter implements IGridItemPainter {

	int x_offset ;
	int y_offset ;
	 
	public int drawn ;

	RegionInfo info ;
	
	ShopCategory sc ;
	
	public RegionGridItemPainter ( ShopCategory _sc ) {
		
		sc = _sc ;
		
	}
	
	@Override
	public void draw(ShopGUI relative, ScaledResolution resolution, int m_Page, int mouseX, int mouseY, float partialTicks, Merch _info, int gridI, int gridJ) {

	  x_offset = (int) ( ( resolution . getScaledWidth ( ) / 2 - ( sc . getColCount ( ) * 75 ) / 2 )/2 ) + 75 * ( gridJ + 1 ) ;
	  y_offset = (int) ( ( resolution . getScaledHeight ( ) / 2 - ( sc . getRowCount ( ) * 75 ) / 2 )/2 ) + 75 * ( gridI + 1  );

      info = ( RegionInfo ) _info ;

      relative.drawCenteredString(relative.getFontRenderer(), info.name, x_offset, y_offset - 15, 16777215);
     //relative.drawCenteredString(relative.getFontRenderer(), MineDonate.cfgUI.cats.regions.pricePrefix + info.cost + MineDonate.cfgUI.cats.regions.priceSuffix, x_offset, y_offset, 16777215);
      relative . moneyArea . drawPriceArea ( x_offset, y_offset, info . cost, info . getMoneyType ( ) ) ;

	}
	
}
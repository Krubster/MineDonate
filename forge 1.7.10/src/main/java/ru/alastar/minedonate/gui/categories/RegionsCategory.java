package ru.alastar.minedonate.gui.categories;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.RenderHelper;
import ru.alastar.minedonate.MineDonate;
import ru.alastar.minedonate.gui.BuyButton;
import ru.alastar.minedonate.gui.ShopCategory;
import ru.alastar.minedonate.gui.ShopGUI;
import ru.alastar.minedonate.merch.info.RegionInfo;

import java.util.ArrayList;

/**
 * Created by Alastar on 20.07.2017.
 */
public class RegionsCategory implements ShopCategory {

    private static int m_Per_Row = 4;
    private static int m_Per_Col = 2;

    @Override
    public boolean getEnabled() {
        return MineDonate.m_Use_Regions;
    }

    @Override
    public int getSourceCount() {
        return MineDonate.m_Categories[2].getMerch().length;
    }

    @Override
    public String getName() {
        return "Regions";
    }

    @Override
    public void draw(ShopGUI relative, int m_Page, int mouseX, int mouseY, float partialTicks) {
        ScaledResolution resolution= new ScaledResolution( relative.mc, relative.mc.displayWidth, relative.mc.displayHeight);

        RenderHelper.enableGUIStandardItemLighting();
        int drawn = 0;
        ArrayList list = new ArrayList();
        for (int i = 0; i < m_Per_Row; ++i) {
            for (int j = 0; j < m_Per_Col; ++j) {
                if (m_Page * m_Per_Col * m_Per_Row + drawn < MineDonate.m_Categories[2].getMerch().length) {

                    int x_offset = (int)(resolution.getScaledWidth() * 0.5 - 75 * 1.5) + 75  * i;
                    int y_offset = (int)(resolution.getScaledHeight() * 0.35)  + 80  * j;
                    final RegionInfo info = (RegionInfo) MineDonate.m_Categories[2].getMerch()[m_Page * m_Per_Col * m_Per_Row + drawn];
                    relative.drawCenteredString(relative.getFontRenderer(), info.name, x_offset, y_offset - 15, 16777215);
                    relative.drawCenteredString(relative.getFontRenderer(), "Price: " + info.cost, x_offset, y_offset, 16777215);
                    ++drawn;
                }
            }
        }
        RenderHelper.disableStandardItemLighting();
        if(list.size() > 0)
            relative.drawHoveringText(list, mouseX, mouseY, relative.getFontRenderer());
    }

    @Override
    public void updateButtons(ShopGUI relative, int m_Page) {
        int drawn = 0;
        ScaledResolution resolution= new ScaledResolution( relative.mc, relative.mc.displayWidth, relative.mc.displayHeight);

        for (int i = 0; i < m_Per_Row; ++i) {
            for (int j = 0; j < m_Per_Col; ++j) {
                if (m_Page * m_Per_Col * m_Per_Row + drawn < MineDonate.m_Categories[2].getMerch().length) {
                    int x_offset = (int)(resolution.getScaledWidth() * 0.5 - 75 * 1.5) + 75  * i;
                    int y_offset = (int)(resolution.getScaledHeight() * 0.35)  + 80  * j;
                    final RegionInfo info = (RegionInfo) MineDonate.m_Categories[2].getMerch()[m_Page * m_Per_Col * m_Per_Row + drawn];
                    relative.addBtn(new BuyButton(info.merch_id, ShopGUI.get_last_button_id(), x_offset - 22, y_offset + 30, 44, 20, "Buy"));
                    ++drawn;
                }
            }
        }
    }

    @Override
    public int elements_per_page() {
        return m_Per_Col * m_Per_Row;
    }

    @Override
    public void actionPerformed(GuiButton button) {
        
    }
}

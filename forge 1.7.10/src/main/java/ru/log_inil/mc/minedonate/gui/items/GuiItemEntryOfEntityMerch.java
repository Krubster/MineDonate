package ru.log_inil.mc.minedonate.gui.items;

import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import ru.alastar.minedonate.MineDonate;
import ru.alastar.minedonate.gui.BuyButton;
import ru.alastar.minedonate.gui.ShopCategory;
import ru.alastar.minedonate.gui.ShopGUI;
import ru.alastar.minedonate.merch.info.EntityInfo;
import ru.log_inil.mc.minedonate.gui.GuiAbstractItemEntry;
import ru.log_inil.mc.minedonate.gui.GuiItemsScrollArea;

public class GuiItemEntryOfEntityMerch extends GuiAbstractItemEntry {

    public EntityInfo info;
    public ShopCategory sc;

    BuyButton buy;

    public GuiItemEntryOfEntityMerch(EntityInfo _eim, ShopCategory _sc) {

        info = _eim;
        sc = _sc;

    }

    String costLine;

    @Override
    public GuiAbstractItemEntry updateDrawData() {

        costLine = MineDonate.cfgUI.cats.entities.pricePrefix + info.cost + MineDonate.cfgUI.cats.regions.priceSuffix;

        return this;

    }

    @Override
    public void draw(GuiItemsScrollArea gi, int x_offset, int y_offset, int var4, Tessellator var5, int index, int size) {

        if (buy != null) {

            buy.yPosition = y_offset + 2;

        }

        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        RenderHelper.enableGUIStandardItemLighting();

        //

        gi.parent.drawString(gi.getFontRenderer(), info.name, 41, y_offset + 8, 16777215);
        gi.parent.drawCenteredString(gi.getFontRenderer(), costLine, x_offset - 70 - 15, y_offset + 8, 16777215);

        //

        RenderHelper.disableStandardItemLighting();
        GL11.glDisable(GL12.GL_RESCALE_NORMAL);

        //
        this.drawHorizontalLine(gi.parent, x_offset - 10, 40, y_offset - 3, 520093695);

        if (index + 1 == size && y_offset + 30 < gi.bottom) {

            this.drawHorizontalLine(gi.parent, x_offset - 10, 40, y_offset - 3 + 30, 520093695);

        }

    }

    @Override
    public void undraw() {

        if (buy != null) {

            buy.yPosition = -100;

        }

    }

    @Override
    public GuiAbstractItemEntry addButtons(ShopGUI gui) {

        buy = new BuyButton(info.getShopId(), info.merch_id, ShopGUI.getNextButtonId(), gui.resolution.getScaledWidth() - 91, -100, MineDonate.cfgUI.cats.itemsAndBlocks.itemBuyButton.width, MineDonate.cfgUI.cats.itemsAndBlocks.itemBuyButton.height, MineDonate.cfgUI.cats.itemsAndBlocks.itemBuyButton.text);

        gui.addBtn(buy);

        return this;

    }

}

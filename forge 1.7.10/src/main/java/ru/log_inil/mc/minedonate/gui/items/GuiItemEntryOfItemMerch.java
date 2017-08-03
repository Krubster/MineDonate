package ru.log_inil.mc.minedonate.gui.items;

import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import ru.alastar.minedonate.MineDonate;
import ru.alastar.minedonate.gui.BuyButton;
import ru.alastar.minedonate.gui.CountButton;
import ru.alastar.minedonate.gui.ShopCategory;
import ru.alastar.minedonate.gui.ShopGUI;
import ru.alastar.minedonate.merch.info.ItemInfo;
import ru.log_inil.mc.minedonate.gui.GuiAbstractItemEntry;
import ru.log_inil.mc.minedonate.gui.GuiItemsScrollArea;

public class GuiItemEntryOfItemMerch extends GuiAbstractItemEntry {

    public ItemInfo info;
    public ShopCategory sc;

    CountButton pl, min;
    BuyButton buy;

    public GuiItemEntryOfItemMerch(ItemInfo _iim, ShopCategory _sc) {

        info = _iim;
        sc = _sc;

    }

    String costLine, limitLine, stackCountLine;
    int costLineWidth;
    boolean updateDataNeed = false;

    @Override
    public GuiAbstractItemEntry updateDrawData() {

        costLine = MineDonate.cfgUI.cats.itemsAndBlocks.pricePrefix + (info.cost * info.modified) + MineDonate.cfgUI.cats.itemsAndBlocks.priceSuffix;
        limitLine = MineDonate.cfgUI.cats.itemsAndBlocks.itemLeft + info.limit;
        stackCountLine = Integer.toString(info.modified * info.stack_data.getInteger("Count"));

        updateDataNeed = true;

        return this;

    }

    @Override
    public void draw(GuiItemsScrollArea gi, int x_offset, int y_offset, int var4, Tessellator var5, int index, int size) {

        if (pl != null) {

            pl.yPosition = y_offset + 2;
            min.yPosition = y_offset + 2;
            buy.yPosition = y_offset + 2;

        }

        if (updateDataNeed) {

            costLineWidth = gi.getFontRenderer().getStringWidth(costLine);
            updateDataNeed = false;

        }

        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        RenderHelper.enableGUIStandardItemLighting();

        //

        gi.parent.drawString(gi.getFontRenderer(), info.name, 67, y_offset + 8, 16777215);
        gi.parent.drawCenteredString(gi.getFontRenderer(), costLine, x_offset - 70 - 50, y_offset + 8, 16777215);

        if (info.limit != -1) {

            gi.parent.drawCenteredString(gi.getFontRenderer(), limitLine, x_offset - 80 - 50 - 50, y_offset + 8, 16777215); // - costLineWidth

        }

        //

        GL11.glPushMatrix();

        GL11.glTranslatef(40, y_offset + 2, 10);
        GL11.glScalef(1.2f, 1.2f, 1f);

        gi.parent.getItemRender().renderItemAndEffectIntoGUI(gi.getFontRenderer(), gi.parent.mc.getTextureManager(), info.m_stack, 0, 0);

        GL11.glPopMatrix();

		/*
        relative.parent.getItemRender().renderItemAndEffectIntoGUI(relative.getFontRenderer(), relative.parent.mc.getTextureManager(), info.m_stack, 40, y_offset + 3 );
		relative.parent.getItemRender().renderItemOverlayIntoGUI(relative.getFontRenderer(), relative.parent.mc.getTextureManager(), info.m_stack, 40, y_offset + 3, info.modified * info.count + "");
		*/

        //

        gi.parent.getItemRender().renderItemOverlayIntoGUI(gi.getFontRenderer(), gi.parent.mc.getTextureManager(), info.m_stack, 45, y_offset + 5, stackCountLine);

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

        if (pl != null) {

            pl.yPosition = -100;
            min.yPosition = -100;
            buy.yPosition = -100;

        }

    }

    @Override
    public GuiAbstractItemEntry addButtons(ShopGUI gui) {

        buy = new BuyButton(info.getShopId(), info.merch_id, ShopGUI.getNextButtonId(), gui.resolution.getScaledWidth() - 91, -100, MineDonate.cfgUI.cats.itemsAndBlocks.itemBuyButton.width, MineDonate.cfgUI.cats.itemsAndBlocks.itemBuyButton.height, MineDonate.cfgUI.cats.itemsAndBlocks.itemBuyButton.text);
        gui.addBtn(buy);

        min = new CountButton(-1, info, ShopGUI.getNextButtonId(), buy.xPosition - 1 - 16, -100, 16, 20, "-");
        min.setEntityOnUpdate(this);

        gui.addBtn(min);

        pl = new CountButton(1, info, ShopGUI.getNextButtonId(), min.xPosition - 1 - 16, -100, 16, 20, "+");
        pl.setEntityOnUpdate(this);

        gui.addBtn(pl);

        if (!pl.canModify() && !min.canModify()) {

            pl.enabled = false;
            min.enabled = false;

        }

        if (info.limit != -1 && info.stack_data.getInteger("Count") > info.limit) {

            buy.enabled = false;

        }

        if (info.limit == 0) {

            buy.enabled = false;
            pl.enabled = false;
            min.enabled = false;

        }

        return this;

    }

}

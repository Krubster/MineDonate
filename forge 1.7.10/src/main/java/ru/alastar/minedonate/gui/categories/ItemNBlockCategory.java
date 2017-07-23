package ru.alastar.minedonate.gui.categories;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.util.ResourceLocation;
import ru.alastar.minedonate.MineDonate;
import ru.alastar.minedonate.gui.BuyButton;
import ru.alastar.minedonate.gui.CountButton;
import ru.alastar.minedonate.gui.ShopCategory;
import ru.alastar.minedonate.gui.ShopGUI;
import ru.alastar.minedonate.merch.info.ItemInfo;

import java.util.ArrayList;

/**
 * Created by Alastar on 19.07.2017.
 */
public class ItemNBlockCategory implements ShopCategory {

    private static int m_Per_Row = 4;
    private static int m_Per_Col = 2;

    @SideOnly(Side.CLIENT)
    ResourceLocation background = new ResourceLocation(MineDonate.MODID, "test.png");

    @Override
    public boolean getEnabled() {
        return MineDonate.m_Use_Items;
    }

    @Override
    public int getSourceCount() {
        return MineDonate.m_Categories[0].getMerch().length;
    }

    @Override
    public String getName() {
        return "Items & Blocks";
    }

    @Override
    public void draw(ShopGUI relative, int m_Page, int mouseX, int mouseY, float partialTicks) {
        ScaledResolution resolution = new ScaledResolution(relative.mc, relative.mc.displayWidth, relative.mc.displayHeight);
             /*
        if (this.background != null && relative.mc.renderEngine != null) {
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            relative.mc.renderEngine.bindTexture(this.background);
            relative.drawTexturedModalRect((int)(resolution.getScaledWidth() * 0.33), (int)(resolution.getScaledHeight() * 0.27), 0, 0, 280, 180);
        }  */
        RenderHelper.enableGUIStandardItemLighting();
        int drawn = 0;
        ArrayList list = new ArrayList();
        for (int i = 0; i < m_Per_Row; ++i) {
            for (int j = 0; j < m_Per_Col; ++j) {
                if (m_Page * m_Per_Col * m_Per_Row + drawn < MineDonate.m_Categories[0].getMerch().length) {

                    int x_offset = (int) (resolution.getScaledWidth() * 0.5 - 75 * 1.5) + 75 * i;
                    int y_offset = (int) (resolution.getScaledHeight() * 0.35) + 95 * j;
                    final ItemInfo info = (ItemInfo) MineDonate.m_Categories[0].getMerch()[m_Page * m_Per_Col * m_Per_Row + drawn];
                    relative.drawCenteredString(relative.getFontRenderer(),info.modified * info.count + "x " + info.name, x_offset, y_offset - 15, 16777215);
                    if (info.limit != -1) {
                        relative.drawCenteredString(relative.getFontRenderer(), "Left: " + info.limit, x_offset, y_offset - 5, 16777215);
                    }
                    relative.getItemRender().renderItemAndEffectIntoGUI(relative.getFontRenderer(), relative.mc.getTextureManager(), info.m_stack, x_offset - 10, y_offset);
                    relative.getItemRender().renderItemOverlayIntoGUI(relative.getFontRenderer(), relative.mc.getTextureManager(), info.m_stack, x_offset - 10, y_offset, info.modified * info.count + "");
                    if (mouseX > x_offset - 20 && mouseX < x_offset + 45 && mouseY > y_offset - 15 && mouseY < y_offset + 50) {
                        list.add(info.info);
                    }
                    ++drawn;
                }
            }
        }
        RenderHelper.disableStandardItemLighting();
        if (list.size() > 0)
            relative.drawHoveringText(list, mouseX, mouseY, relative.getFontRenderer());
    }

    @Override
    public void updateButtons(ShopGUI relative, int m_Page) {
        int drawn = 0;
        ScaledResolution resolution = new ScaledResolution(relative.mc, relative.mc.displayWidth, relative.mc.displayHeight);

        for (int i = 0; i < m_Per_Row; ++i) {
            for (int j = 0; j < m_Per_Col; ++j) {
                if (m_Page * m_Per_Col * m_Per_Row + drawn < MineDonate.m_Categories[0].getMerch().length) {
                    int x_offset = (int) (resolution.getScaledWidth() * 0.5 - 75 * 1.5) + 75 * i;
                    int y_offset = (int) (resolution.getScaledHeight() * 0.35) + 95 * j;
                    ItemInfo info = (ItemInfo) MineDonate.m_Categories[0].getMerch()[m_Page * m_Per_Col * m_Per_Row + drawn];
                    final BuyButton buyBtn = new BuyButton(info.merch_id, ShopGUI.get_last_button_id(), x_offset - 22, y_offset + 50, 44, 20, "Buy");

                    relative.addBtn(buyBtn);
                    final CountButton c1btn = new CountButton(-1, info, ShopGUI.get_last_button_id(), x_offset - 22, y_offset + 30, 22, 20, "<");
                    relative.addBtn(c1btn);
                    final CountButton c2btn = new CountButton(1, info, ShopGUI.get_last_button_id(), x_offset, y_offset + 30, 22, 20, ">");
                    relative.addBtn(c2btn);
                    if (info.limit == 0) {
                        buyBtn.enabled = false;
                        c1btn.enabled = false;
                        c2btn.enabled = false;
                    }
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
        if (button instanceof CountButton) {
            CountButton countButton = (CountButton) button;
            countButton.tryModify();
        }
    }
}

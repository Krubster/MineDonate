package ru.alastar.minedonate.gui;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.entity.RenderItem;
import ru.alastar.minedonate.MineDonate;
import ru.alastar.minedonate.gui.categories.EntitiesCategory;
import ru.alastar.minedonate.gui.categories.ItemNBlockCategory;
import ru.alastar.minedonate.gui.categories.PrivilegieCategory;
import ru.alastar.minedonate.gui.categories.RegionsCategory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alastar on 18.07.2017.
 */
public class ShopGUI extends GuiScreen {
    public static ShopGUI instance;
    private boolean can_process = true;
    private static int m_last_id = 3;
    public static int m_Page = 0;
    private ShopCategory[] m_Categories = new ShopCategory[]{new ItemNBlockCategory(), new PrivilegieCategory(), new RegionsCategory(), new EntitiesCategory()};
    private int m_Selected_Category = 0;   // 0 - blocks & items, 1 - privilegies, 2 - wg regions, 3 - entities

    public ShopGUI() {

    }

    public static int get_last_button_id() {
        return m_last_id++;
    }


    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        ScaledResolution resolution = new ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight);

        this.drawCenteredString(this.fontRendererObj, String.format("Your money: %d", MineDonate.m_Client_Money), resolution.getScaledWidth() / 2, (int) (resolution.getScaledHeight() * 0.1), 16777215);
        super.drawScreen(mouseX, mouseY, partialTicks);
        m_Categories[m_Selected_Category].draw(this, m_Page, mouseX, mouseY, partialTicks);
        can_process = true;
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        if(can_process) {
            can_process = false;
            System.out.println("ACTION PERFORMED!");
            if (button.id == 0) {
                button.enabled = false;
                this.mc.thePlayer.closeScreen();
            } else if (button instanceof PreviousButton) {
                button.enabled = false;
                m_Page = m_Page - 1;
                updateBtns();
            } else if (button instanceof NextButton) {
                button.enabled = false;
                m_Page = m_Page + 1;
                System.out.println("new page is: " + m_Page);
                updateBtns();
            } else if (button instanceof BuyButton) {
                ((BuyButton) button).buy(m_Selected_Category);
            } else if (button instanceof CategoryButton) {
                m_Page = 0;
                m_Selected_Category = ((CategoryButton) button).getCategory();
                updateBtns();
            }
            m_Categories[m_Selected_Category].actionPerformed(button);
        }
    }

    public void drawHoveringText(ArrayList list, int mouseX, int mouseY, FontRenderer fontRenderer) {
        super.drawHoveringText(list, mouseX, mouseY, fontRenderer);
    }

    @Override
    public void initGui() {
        instance = this;
        updateBtns();
    }

    private void addCategories() {
        int offset = 0;
        ScaledResolution resolution = new ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight);
        for (int i = 0; i < m_Categories.length; ++i) {
            CategoryButton btn = new CategoryButton(i, get_last_button_id(), offset + (int) (resolution.getScaledWidth() * 0.5) - 150, (int) (resolution.getScaledHeight() * 0.1) + 40, m_Categories[i].getName());
            btn.width = (75);
            this.addBtn(btn);
            btn.enabled = m_Categories[i].getEnabled();
            offset += btn.width + 3;
        }
    }


    public void addBtn(GuiButton b) {
        this.buttonList.add(b);
    }

    public void updateBtns() {
        System.out.println("clear buttons");
        buttonList.clear();
        addCategories();
        System.out.println("added category buttons");

        m_Categories[m_Selected_Category].updateButtons(this, m_Page);
        System.out.println("updated category buttons");
        ScaledResolution resolution = new ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight);
        buttonList.add(new GuiButton(0, (int) (resolution.getScaledWidth() * 0.5) - 100, (int) (resolution.getScaledHeight() * 0.1) + 15, "X"));//Close button

        if (m_Categories[m_Selected_Category].getSourceCount() > m_Categories[m_Selected_Category].elements_per_page()) {
            if (m_Page > 0) {
                //add previous btn
                buttonList.add(new PreviousButton(ShopGUI.get_last_button_id(), (int) (resolution.getScaledWidth() * 0.5) - 125, (int) (resolution.getScaledHeight() * 0.1) + 15, 20, 20, "<"));

            }
            if (m_Page < (int) Math.ceil(m_Categories[m_Selected_Category].getSourceCount() / (m_Categories[m_Selected_Category].elements_per_page()))) {
                //add next btn
                buttonList.add(new NextButton(ShopGUI.get_last_button_id(), (int) (resolution.getScaledWidth() * 0.5) + 105, (int) (resolution.getScaledHeight() * 0.1) + 15, 20, 20, ">"));
            }
        }
    }

    public FontRenderer getFontRenderer() {
        return this.fontRendererObj;
    }

    public RenderItem getItemRender() {
        return this.itemRender;
    }

    public List getLabels() {
        return this.labelList;
    }
}
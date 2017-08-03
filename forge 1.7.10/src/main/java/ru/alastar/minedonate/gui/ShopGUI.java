package ru.alastar.minedonate.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.entity.RenderItem;
import ru.alastar.minedonate.MineDonate;
import ru.alastar.minedonate.gui.categories.*;
import ru.alastar.minedonate.network.packets.NeedShopCategoryPacket;
import ru.alastar.minedonate.network.packets.NeedUpdatePacket;
import ru.alastar.minedonate.proxies.ClientProxy;
import ru.log_inil.mc.minedonate.gui.DrawType;
import ru.log_inil.mc.minedonate.gui.GuiGradientButton;
import ru.log_inil.mc.minedonate.gui.GuiGradientTextField;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alastar on 18.07.2017.
 */
public class ShopGUI extends GuiScreen {

    public static ShopGUI instance;
    public boolean needNetUpdate = true;
    public boolean loading = false;

    private boolean can_process = true;
    private static int m_last_id = 3;
    public static int m_Page = 0;

    private int m_Selected_Category = 0;   // 0 - blocks & items, 1 - privilegies, 2 - wg regions, 3 - entities
    private ShopCategory[] cats;
    public GuiGradientTextField searchField;
    public int currentShop = 0;

    public ShopGUI() {

        cats = new ShopCategory[]{new ItemNBlockCategory(), new PrivilegieCategory(), new RegionsCategory(), new EntitiesCategory(), new UsersShopsCategory()};

        for (ShopCategory sc : cats) {

            sc.init(this);

        }

    }

    public static int getNextButtonId() {

        return m_last_id++;

    }

    public ShopCategory[] getCurrentShopCategories() { // #LOG

        return cats;

    }

    public ShopCategory getCurrentCategory() {

        return cats[m_Selected_Category];

    }

    public int getCurrentShopId() {

        return currentShop;

    }

    //#LOG
    @Override
    protected void keyTyped(char p_73869_1_, int p_73869_2_) {

        if (searchField != null && searchField.isFocused()) {

            searchField.textboxKeyTyped(p_73869_1_, p_73869_2_);

            getCurrentCategory().search(searchField.getText());

            updateGrid();
            updateBtns();

            return;

        }

        if (ClientProxy.refreshCfg != null && ClientProxy.refreshCfg.getKeyCode() == p_73869_2_) {

            MineDonate.loadClientConfig();
            initGui();

        }


        // 32 205 -> d
        // 30 203 <- a

        if ((30 == p_73869_2_ || 203 == p_73869_2_) && pb.enabled) {

            actionPerformed(pb);

        } else if ((32 == p_73869_2_ || 205 == p_73869_2_) && nb.enabled) {

            actionPerformed(nb);

        } else if (ClientProxy.openHUD.getKeyCode() == p_73869_2_) {

            Minecraft.getMinecraft().displayGuiScreen(null);

        } else {

            super.keyTyped(p_73869_1_, p_73869_2_);

        }

    }

    @Override
    protected void mouseClicked(int p_73864_1_, int p_73864_2_, int p_73864_3_) {

        if (searchField != null) {

            searchField.mouseClicked(p_73864_1_, p_73864_2_, p_73864_3_);

        }

        super.mouseClicked(p_73864_1_, p_73864_2_, p_73864_3_);

    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {

        ScaledResolution resolution = new ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight);

        this.drawRect(0, 0, resolution.getScaledWidth(), resolution.getScaledHeight(), 1258291200);

        if (!needNetUpdate) {

            String vault = String.format(MineDonate.cfgUI.moneyLinePrefix + "%d" + MineDonate.cfgUI.moneyLineSuffix, MineDonate.m_Client_Money);
            this.drawCenteredString(this.fontRendererObj, vault, resolution.getScaledWidth() - this.fontRendererObj.getStringWidth(vault) - 3, (int) (resolution.getScaledHeight() * 0.1) + 20 + 5, 16777215);


            if (!loading) {

                can_process = true;

                getCurrentCategory().draw(this, m_Page, mouseX, mouseY, partialTicks, DrawType.BG);

                getCurrentCategory().draw(this, m_Page, mouseX, mouseY, partialTicks, DrawType.PRE);

                super.drawScreen(mouseX, mouseY, partialTicks);

                if (searchField != null) {
                    searchField.drawTextBox();
                }

                getCurrentCategory().draw(this, m_Page, mouseX, mouseY, partialTicks, DrawType.POST);

                //

            } else {

                can_process = false;

                getCurrentCategory().draw(this, m_Page, mouseX, mouseY, partialTicks, DrawType.BG);

                super.drawScreen(mouseX, mouseY, partialTicks);
                getCurrentCategory().draw(this, m_Page, mouseX, mouseY, partialTicks, DrawType.POST);

                this.drawRect((resolution.getScaledWidth() / 2) - 10 - this.fontRendererObj.getStringWidth(MineDonate.cfgUI.loadingText) / 2, (resolution.getScaledHeight() / 2) - 5, (resolution.getScaledWidth() / 2) + 10 + this.fontRendererObj.getStringWidth(MineDonate.cfgUI.loadingText) / 2, (resolution.getScaledHeight() / 2) + 15, 1258291200);

                this.drawCenteredString(this.fontRendererObj, MineDonate.cfgUI.loadingText, resolution.getScaledWidth() / 2, resolution.getScaledHeight() / 2, 16777215);


            }

        } else {

            if (loading) {

                can_process = false;

                this.drawRect((resolution.getScaledWidth() / 2) - 10 - this.fontRendererObj.getStringWidth(MineDonate.cfgUI.loadingText) / 2, (resolution.getScaledHeight() / 2) - 5, (resolution.getScaledWidth() / 2) + 10 + this.fontRendererObj.getStringWidth(MineDonate.cfgUI.loadingText) / 2, (resolution.getScaledHeight() / 2) + 15, 1258291200);

                this.drawCenteredString(this.fontRendererObj, MineDonate.cfgUI.loadingText, resolution.getScaledWidth() / 2, resolution.getScaledHeight() / 2, 16777215);

            }

        }

    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    @Override
    protected void actionPerformed(GuiButton button) {

        if (button.id == 0) {

            button.enabled = false;
            this.mc.thePlayer.closeScreen();

        }

        if (can_process) {

            can_process = false;

            if (button instanceof PreviousButton) {

                button.enabled = false;
                m_Page = m_Page - 1;
                updateGrid();
                updateBtns();

            } else if (button instanceof NextButton) {

                button.enabled = false;
                m_Page = m_Page + 1;
                System.out.println("new page is: " + m_Page);
                updateGrid();
                updateBtns();

            } else if (button instanceof BuyButton) {

                ((BuyButton) button).buy(m_Selected_Category);

            } else if (button instanceof CategoryButton) {

                if (currentShop != 0) {

                    if (cats[m_Selected_Category] instanceof UsersShopsCategory) {

                        ((UsersShopsCategory) cats[m_Selected_Category]).selectedShop = 0;
                        ((UsersShopsCategory) cats[m_Selected_Category]).updateUserShopCategory(null, false);

                    }

                }

                currentShop = 0;
                m_Page = 0;
                m_Selected_Category = ((CategoryButton) button).getCategory();

                MineDonate.networkChannel.sendToServer(new NeedShopCategoryPacket(getCurrentShopId(), m_Selected_Category));
                loading = true;

                resolution = new ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight);

                if (searchField != null) {

                    getCurrentCategory().search(searchField.getText());

                }

                updateGrid();
                updateBtns();

            } else if (button instanceof GoButton) {

                // currentShop = ( ( GoButton ) button ) . shopId ;
                ((UsersShopsCategory) getCurrentCategory()).selectedShop = ((GoButton) button).shopId;
                MineDonate.networkChannel.sendToServer(new NeedShopCategoryPacket(((GoButton) button).shopId, 0));
                loading = true;

            } else if (button instanceof GuiGradientButton) {

                if (button.id == searchButton.id) {

                    if (searchField != null) {

                        if (searchField.getVisible()) {

                            searchField.setVisible(false);
                            searchField.setEnabled(false);

                        } else {

                            searchField.setVisible(true);
                            searchField.setEnabled(true);
                            searchField.setFocused(true);

                        }

                    }

                }

            }

            getCurrentCategory().actionPerformed(button);

        }
    }

    public void drawHoveringText(ArrayList list, int mouseX, int mouseY, FontRenderer fontRenderer) {
        super.drawHoveringText(list, mouseX, mouseY, fontRenderer);
    }

    @Override
    public void initGui() {

        instance = this;
        resolution = new ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight); // #LOG
        m_Page = 0;
        updateGrid(); // #LOG
        updateBtns();
        getCurrentCategory().initGui();

        if (needNetUpdate && !loading) {

            MineDonate.networkChannel.sendToServer(new NeedUpdatePacket(0));
            loading = true;

        }


    }

    // int widthCatsBlock = 0 ;
    private void addCategories() {
        //   int offset = 0;
        ScaledResolution resolution = new ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight);
        ScaledResolution resolution0;

        /*
        int posX = 0 ;
       // widthCatsBlock = 0 ;
        for ( ShopCategory sc : getCurrentShopCategories ( ) ) {//#LOG
        	if ( sc.getEnabled() ) { 
        		
        		resolution0 = new ScaledResolution(this.mc, sc . getButtonWidth ( ) + 5, 0 ) ; //TODO rewrite
        		widthCatsBlock += resolution0.getScaledWidth() ; 	
        		
        	}
        }*/

        int posX = 30; //( resolution . getScaledWidth ( ) / 2 ) - ( widthCatsBlock / 2 ) ;

        for (int i = 0; i < getCurrentShopCategories().length; ++i) {

            if (getCurrentShopCategories()[i].getEnabled()) { //#LOG

                CategoryButton btn = new CategoryButton(i, getNextButtonId(), posX, (int) (resolution.getScaledHeight() * 0.1) + 19, getCurrentShopCategories()[i].getButtonText());

                btn.width = getCurrentShopCategories()[i].getButtonWidth(); // 75
                this.addBtn(btn);

                posX += btn.width;

                if (i == m_Selected_Category) { // #LOG

                    btn.enabled = false;

                } else {

                    btn.enabled = true;

                }

            }

        }
    }


    public void addBtn(GuiButton b) {

        this.buttonList.add(b);

    }

    public void removeButton(GuiButton but) {

        this.buttonList.remove(but);

    }

    PreviousButton pb;
    NextButton nb;
    public ScaledResolution resolution;
    GuiButton exitButton;
    GuiGradientButton searchButton;

    public void updateBtns() {

        //System.out.println("clear buttons");
        buttonList.clear();
        addCategories();
        //System.out.println("added category buttons");

        getCurrentCategory().updateButtons(this, m_Page);
        //System.out.println("updated category buttons");

        if (MineDonate.cfgUI.addSearchButton) {

            buttonList.add(searchButton = new GuiGradientButton(ShopGUI.getNextButtonId(), 30, (int) ((resolution.getScaledHeight()) - (resolution.getScaledHeight() * 0.1)) - 5, MineDonate.cfgUI.searchButton.width, MineDonate.cfgUI.searchButton.height, MineDonate.cfgUI.searchButton.text, false));

            if (searchField == null) {

                searchField = new GuiGradientTextField(this.fontRendererObj, 30 + MineDonate.cfgUI.searchButton.width, (int) ((resolution.getScaledHeight()) - (resolution.getScaledHeight() * 0.1)) - 5, 160, MineDonate.cfgUI.searchButton.height);
                searchField.setVisible(false);
                searchField.setEnabled(false);

            }

            searchField.xPosition = 30 + MineDonate.cfgUI.searchButton.width;
            searchField.yPosition = (int) ((resolution.getScaledHeight()) - (resolution.getScaledHeight() * 0.1)) - 5;


        }

        buttonList.add(exitButton = new GuiButton(0, (int) (resolution.getScaledWidth() * 0.5) - MineDonate.cfgUI.exitButton.width / 2, (int) ((resolution.getScaledHeight()) - (resolution.getScaledHeight() * 0.1)), MineDonate.cfgUI.exitButton.width, MineDonate.cfgUI.exitButton.height, MineDonate.cfgUI.exitButton.text));

        buttonList.add(pb = new PreviousButton(ShopGUI.getNextButtonId(), exitButton.xPosition - 4 - 20, exitButton.yPosition, 20, 20, "<"));
        buttonList.add(nb = new NextButton(ShopGUI.getNextButtonId(), exitButton.xPosition + exitButton.width + 4, exitButton.yPosition, 20, 20, ">"));

        if (getCurrentCategory().getSourceCount(getCurrentShopId()) > getCurrentCategory().elements_per_page()) {

            pb.enabled = (m_Page > 0);

            nb.enabled = getCurrentCategory().elements_per_page() > 0 && m_Page < (int) Math.ceil(getCurrentCategory().getSourceCount(getCurrentShopId()) / (getCurrentCategory().elements_per_page()));

        } else {

            pb.enabled = nb.enabled = false;

        }

    }

    int tmpH;

    public void updateGrid() { // #LOG

        int tmpW;

        if (getCurrentCategory().getItemWidth() > 0) {

            tmpH = resolution.getScaledHeight() - 50 - 25;

            tmpW = resolution.getScaledWidth() - 50 - 50;

            getCurrentCategory().setColCount(tmpW / getCurrentCategory().getItemWidth());

            getCurrentCategory().setRowCount(tmpH / getCurrentCategory().getItemHeight());

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

    public void drawGradientRectAccess(int par1, int par2, int par3, int par4, int par5, int par6) {
        drawGradientRect(par1, par2, par3, par4, par5, par6);
    }

}
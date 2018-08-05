package ru.alastar.minedonate.gui.categories;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import ru.alastar.minedonate.MineDonate;
import ru.alastar.minedonate.gui.ShopCategory;
import ru.alastar.minedonate.gui.ShopGUI;
import ru.alastar.minedonate.merch.Merch;
import ru.alastar.minedonate.merch.info.ShopInfo;
import ru.log_inil.mc.minedonate.gui.*;
import ru.log_inil.mc.minedonate.gui.items.GuiItemEntryOfUserShopMerch;

public class UsersShopsCategory extends ShopCategory {

    public ShopInfo selectedShop;
    public boolean displayReturnButton = false;
    ShopCategory userSC;
    GuiGradientButton returnButton;
    GuiGradientButton viewMyShopsButton;
    GuiGradientButton createNewShopButton;
    boolean viewMyShops = false;
    GuiItemsScrollArea gi;
    ShopInfo iim;
    public UsersShopsCategory(String _name) {

        super(_name);

        catId = 4;

    }

    // public int selectedShop = 0 ;
    public ShopInfo getSelectedShop() {

        return selectedShop;

    }

    @Override
    public boolean getEnabled() {

        return MineDonate.cfg.userShops; // ( userSC != null ? userSC . getEnabled ( ) : MineDonate . cfg . userShops ) ;

    }

    @Override
    public int getSourceCount(int shopId) {

        return (userSC != null ? userSC.getSourceCount(shopId) : MineDonate.shops.containsKey(shopId) ? MineDonate.shops.get(shopId).cats[catId].getMerch().length : 0);

    }

    @Override
    public String getName() {

        return "Users shops";

    }

    @Override
    public void draw(ShopGUI g, int page, int mouseX, int mouseY, float partialTicks, DrawType dt) {

        super.draw(g, page, mouseX, mouseY, partialTicks, dt);

        if (userSC != null && userSC.getScrollList() != null && dt != DrawType.BG) {

            userSC.getScrollList().drawScreen(mouseX, mouseY, partialTicks, dt);

        }

        if (gi != null && (userSC != null ? dt != DrawType.POST : true)) {

            gi.drawScreen(mouseX, mouseY, partialTicks, dt);

        }

    }

    @Override
    public void updateButtons(ShopGUI relative, int page) {

        gui = relative;

        if (returnButton != null) {

            relative.removeButton(returnButton);

        }

        if (viewMyShopsButton != null) {

            relative.removeButton(viewMyShopsButton);

        }

        if (createNewShopButton != null) {

            relative.removeButton(createNewShopButton);

        }

        relative.getButtonList().add(returnButton = new GuiGradientButton(ShopGUI.getNextButtonId(), relative.exitButton.x - MineDonate.cfgUI.returnButton.width, relative.exitButton.y, MineDonate.cfgUI.returnButton.width, MineDonate.cfgUI.returnButton.height, MineDonate.cfgUI.returnButton.text, false));
        relative.rightButton = returnButton;

        relative.getButtonList().add(viewMyShopsButton = new GuiGradientButton(ShopGUI.getNextButtonId(), relative.exitButton.x - MineDonate.cfgUI.cats.shops.viewMyShopsButton.width, relative.exitButton.y, MineDonate.cfgUI.cats.shops.viewMyShopsButton.width, MineDonate.cfgUI.cats.shops.viewMyShopsButton.height, MineDonate.cfgUI.cats.shops.viewMyShopsButton.text, false, true));
        relative.getButtonList().add(createNewShopButton = new GuiGradientButton(ShopGUI.getNextButtonId(), viewMyShopsButton.x - MineDonate.cfgUI.cats.shops.createNewShopButton.width, viewMyShopsButton.y, MineDonate.cfgUI.cats.shops.createNewShopButton.width, MineDonate.cfgUI.cats.shops.createNewShopButton.height, MineDonate.cfgUI.cats.shops.createNewShopButton.text, false));

        if (userSC != null) {

            //userSC . updateButtons(relative, page);
            userSC.postShow(relative);
        /*
    		relative . getButtonList ( ) . add ( ( (ItemNBlockCategory) userSC ) . addButton = new GuiGradientButton ( ShopGUI . getNextButtonId ( ),
    				createNewShopButton . x -  MineDonate . cfgUI . cats . itemsAndBlocks . addButton . width,
    				createNewShopButton.y, MineDonate . cfgUI . cats . itemsAndBlocks . addButton . width, MineDonate . cfgUI . cats . itemsAndBlocks . addButton . height, MineDonate . cfgUI . cats . itemsAndBlocks . addButton . text, false ) ) ;
	*/
        }


        super.updateButtons(relative, page);
        //refreshGui ( relative ) ;

        updateButtons();

    }

    @Override
    public boolean actionPerformed(ShopGUI g, GuiButton button) {

        super.actionPerformed(g, button);

        if (userSC != null) {

            userSC.actionPerformed(g, button);

            if (returnButton != null && button.id == returnButton.id) {

                gui.lockProcess();

                if (viewMyShops) {

                    actionPerformed(g, viewMyShopsButton);

                    gui.updateButtons(true);
                    updateButtons();
                    postShow(g);

                    return true;

                }

                selectedShop = null;
                ShopGUI.instance.currentShop = 0;

                updateUserShopCategory(g, null, true);
                gui.updateButtons(true);
                updateButtons();
                postShow(g);

                return true;

            }

        }

        if (viewMyShopsButton != null && button.id == this.viewMyShopsButton.id) {

            gui.lockProcess();

            if (!viewMyShops) {

                super.search(Minecraft.getMinecraft().player.getName());

            } else {

                super.search(null);

            }

            viewMyShops = !viewMyShops;

            gui.updateButtons(true);
            updateButtons();
            postShow(g);

            return true;

        }

        if (createNewShopButton != null && button.id == this.createNewShopButton.id) {

            gui.lockProcess();

            g.showEntry("frame.shop.create", true);

            gui.updateButtons(true);
            updateButtons();
            postShow(g);

            return true;

        }

        return false;

    }

    @Override
    public int getButtonWidth() {

        return MineDonate.cfgUI.cats.shops.categoryButtonWidth;

    }

    @Override
    public String getButtonText() {

        return MineDonate.cfgUI.cats.shops.categoryButtonText;

    }

    @Override
    public void postShow(ShopGUI g) {

        super.postShow(g);

        if (userSC != null) {

            if (subCatId == -1) {

                userSC.filterProcess();

            }

            userSC.preShow(gui);
            userSC.postShow(g);

        }

        if (subCatId == -1) {

            filterProcess();

        }

        updateButtons();
        refreshGui(g);

    }

    public void refreshGui(ShopGUI g) {

        if (gi == null) {

            gi = new GuiItemsScrollArea(g.getScaledResolution(), gui, entrs, 0);

        } else {

            gi.updateSizes(g.getScaledResolution());

        }

        for (GuiAbstractItemEntry gie : entrs) {

            gie.unShow(g);

        }

        entrs.clear();

        if (g.isLoading()) {

            return;

        }

        if (userSC == null) {

            if (MineDonate.shops.containsKey(gui.getCurrentShopId())) {

                if (search) {

                    for (Merch m : noSearchedEntries) {

                        iim = (ShopInfo) m;

                        if (iim != null) {

                            if ((iim.isFreezed ? !MineDonate.cfgUI.cats.shops.dontShowFreezed : true) && (viewMyShops ? (iim.ownerName.equalsIgnoreCase(searchValue)) : (iim.ownerName.toLowerCase().contains(searchValue) || iim.getSearchValue().toLowerCase().contains(searchValue)))) {

                                entrs.add(new GuiItemEntryOfUserShopMerch(iim, this).addButtons(gui).updateDrawData());

                            }

                        }

                    }

                } else {

                    for (Merch m : noSearchedEntries) {

                        iim = (ShopInfo) m;

                        if (iim != null) {

                            if ((iim.isFreezed ? !MineDonate.cfgUI.cats.shops.dontShowFreezed : true)) {

                                entrs.add(new GuiItemEntryOfUserShopMerch(iim, this).addButtons(gui).updateDrawData());

                            }

                        }

                    }

                }
            }

        }

        gi.entrs = entrs;
        gi.applyScrollLimits();

    }

    @Override
    public void unShow(ShopGUI g) {

        super.unShow(g);

        if (userSC != null) {

            userSC.unShow(g);

        }

    }

    public void updateUserShopCategory(ShopGUI g, ShopCategory sc, boolean r) {

        if (userSC != null) {

            userSC.unShow(g);

        }

        userSC = sc;
        if (r) {
            refreshGui(g);
        }
        updateButtons();

    }

    public void updateButtons() {

        if (userSC != null) {

            returnButton.enabled = returnButton.visible = displayReturnButton = true;

        } else {

            returnButton.enabled = returnButton.visible = displayReturnButton = false;

        }

        if (displayReturnButton) {

            viewMyShopsButton.y = -100;
            createNewShopButton.y = -100;

        } else {

            viewMyShopsButton.x = gui.exitButton.x - viewMyShopsButton.width;
            viewMyShopsButton.y = gui.exitButton.y;
            createNewShopButton.x = viewMyShopsButton.x - createNewShopButton.width;
            createNewShopButton.y = viewMyShopsButton.y;

        }

        viewMyShopsButton.pressed = viewMyShops;
        createNewShopButton.visible = createNewShopButton.enabled = MineDonate.getAccount().canCreateShop();

    }

    @Override
    public GuiScrollingList getScrollList() {

        return gi;

    }

    @Override
    public void search(String s) {

        viewMyShops = false;

        super.search(s);

        if (userSC != null) {

            userSC.search(s);

        }

    }

}

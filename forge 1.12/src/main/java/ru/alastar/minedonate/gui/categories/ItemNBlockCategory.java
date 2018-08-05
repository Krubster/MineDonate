package ru.alastar.minedonate.gui.categories;

import net.minecraft.client.gui.GuiButton;
import ru.alastar.minedonate.MineDonate;
import ru.alastar.minedonate.events.MineDonateGUIHandler;
import ru.alastar.minedonate.gui.CountButton;
import ru.alastar.minedonate.gui.ShopCategory;
import ru.alastar.minedonate.gui.ShopGUI;
import ru.alastar.minedonate.merch.Merch;
import ru.alastar.minedonate.merch.info.ItemInfo;
import ru.alastar.minedonate.rtnl.ModNetworkRegistry;
import ru.log_inil.mc.minedonate.gui.*;
import ru.log_inil.mc.minedonate.gui.frames.GuiFrameAddItem;
import ru.log_inil.mc.minedonate.gui.items.GuiItemEntryOfItemMerch;

/**
 * Created by Alastar on 19.07.2017.
 */
public class ItemNBlockCategory extends ShopCategory {

    GuiGradientButton addButton;
    GuiButton rightButton;
    GuiItemsScrollArea gi;
    ItemInfo iim;

    public ItemNBlockCategory(String _name) {

        super(_name);

        catId = 0;
        shopId = 0;

    }

    @Override
    public boolean getEnabled() {

        return MineDonate.cfg.sellItems;

    }

    @Override
    public int getSourceCount(int shopId) {

        return MineDonate.shops.containsKey(shopId) ? MineDonate.shops.get(shopId).cats[catId].getMerch().length : 0;

    }

    @Override
    public String getName() {
        return "Items & Blocks";
    }

    @Override
    public void draw(ShopGUI g, int page, int mouseX, int mouseY, float partialTicks, DrawType dt) {

        super.draw(g, page, mouseX, mouseY, partialTicks, dt);

        if (subCats != null && subCats.length > 0 && subCatId == -1 && dt == DrawType.PRE) {

            return;

        }

        if (gi != null) {

            gi.drawScreen(mouseX, mouseY, partialTicks, dt);

        }

    }

    @Override
    public void unDraw() {

        super.unDraw();

        for (GuiAbstractItemEntry gie : entrs) {

            gie.unDraw();

        }

    }

    @Override
    public void updateButtons(ShopGUI g, int page) {

        rightButton = g.rightButton;

        if (addButton != null) {

            g.removeButton(addButton);

        }

        if (rightButton != null) {

            if (addButton == null) {

                g.getButtonList().add(addButton = new GuiGradientButton(ShopGUI.getNextButtonId(),
                        rightButton.x - MineDonate.cfgUI.cats.itemsAndBlocks.addButton.width,
                        rightButton.y, MineDonate.cfgUI.cats.itemsAndBlocks.addButton.width, MineDonate.cfgUI.cats.itemsAndBlocks.addButton.height, MineDonate.cfgUI.cats.itemsAndBlocks.addButton.text, false));

            } else {

                if (!g.getButtonList().contains(addButton)) {

                    g.getButtonList().add(addButton);

                }

                addButton.x = rightButton.x - MineDonate.cfgUI.cats.itemsAndBlocks.addButton.width;
                addButton.y = rightButton.y;

            }

            addButton.visible = addButton.enabled = MineDonate.canAdd(g.getCurrentShopId(), catId) && (shopOwner == null ? true : MineDonate.getAccount().canEditShop(shopOwner));

        }

        super.updateButtons(g, page);

    }

    @Override
    public boolean actionPerformed(ShopGUI g, GuiButton button) {

        super.actionPerformed(g, button);

        if (button instanceof CountButton) {

            CountButton countButton = (CountButton) button;
            countButton.tryModify();

            return true;

        } else if (addButton != null && button.id == addButton.id) {

            GuiFrameAddItem gfai = (GuiFrameAddItem) g.showEntry("frame.item.add", true);

            gfai.setInfo(g.getCurrentShopId(), catId, true);

            MineDonateGUIHandler.setBackShopGUI(true);

            ModNetworkRegistry.sendToServerOpenShopInventoryPacket();

        }

        return false;

    }

    @Override
    public int getButtonWidth() {

        return MineDonate.cfgUI.cats.itemsAndBlocks.categoryButtonWidth;

    }

    @Override
    public String getButtonText() {

        return MineDonate.cfgUI.cats.itemsAndBlocks.categoryButtonText;

    }

    @Override
    public void postShow(ShopGUI g) {

        if (subCatId == -1) {

            filterProcess();

        }

        super.postShow(g);

        if (gi == null) {

            gi = new GuiItemsScrollArea(g.getScaledResolution(), gui, entrs, 0);

        } else {

            gi.updateSizes(g.getScaledResolution());

        }

        for (GuiAbstractItemEntry gie : entrs) {

            gie.unDraw();

        }

        entrs.clear();

        if (g.isLoading()) {

            return;

        }

        if (subCats != null && subCats.length > 0 && subCatId == -1) {

            return;

        }

        if (MineDonate.shops.containsKey(gui.getCurrentShopId())) {

            if (search) {

                for (Merch m : noSearchedEntries) {

                    iim = (ItemInfo) m;

                    if (iim.getSearchValue().toLowerCase().contains(searchValue)) {

                        entrs.add(new GuiItemEntryOfItemMerch(iim, this).addButtons(gui).updateDrawData());

                    }

                }

            } else {

                for (Merch m : noSearchedEntries) {

                    iim = (ItemInfo) m;

                    entrs.add(new GuiItemEntryOfItemMerch(iim, this).addButtons(gui).updateDrawData());

                }

            }

        }

        gi.entrs = entrs;
        gi.applyScrollLimits();

    }

    public void setShopId(int _shopId) {

        shopId = _shopId;

    }

    @Override
    public GuiScrollingList getScrollList() {

        return gi;

    }

}

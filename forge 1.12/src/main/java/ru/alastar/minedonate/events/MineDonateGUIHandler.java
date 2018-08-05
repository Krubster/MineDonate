package ru.alastar.minedonate.events;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import ru.alastar.minedonate.MineDonate;
import ru.alastar.minedonate.gui.ShopGUI;
import ru.alastar.minedonate.gui.merge.ShopInventoryContainer;
import ru.alastar.minedonate.gui.merge.ShopInventoryGUI;

public class MineDonateGUIHandler implements IGuiHandler {

    public static final int SHOP_ID = 0, STORE_ID = 1;
    public static ShopGUI lastOpened, lastBacked;

    public static void setBackShopGUI(boolean b) {

        if (b) {

            lastBacked = lastOpened;

        } else {

            lastBacked = null;

        }

    }

    @Override
    public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {

        if (MineDonate.m_Enabled) {

            if (id == STORE_ID) {

                ShopInventoryContainer sic = new ShopInventoryContainer(player.inventory);

                MineDonate.mergeContainers.put(player.getDisplayName().getFormattedText(), sic);

                return sic;

            }

        }

        return null;

    }

    @Override
    public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {

        if (MineDonate.m_Enabled) {

            if (id == SHOP_ID) {

                if (lastOpened != null) {

                    lastOpened.onGuiClosed(true);

                }

                if (lastBacked != null && (MineDonate.getAccount().ms.currentItemStack != null || MineDonate.getAccount().ms.mobSelect)) {

                    MineDonate.getAccount().ms.mobSelect = false;

                    lastOpened = lastBacked;

                    lastBacked = null;

                } else {

                    lastOpened = new ShopGUI();

                }

                return lastOpened;

            } else if (id == STORE_ID) {

                return new ShopInventoryGUI(player.inventory);

            }

        }

        return null;

    }

}
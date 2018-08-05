package ru.alastar.minedonate.gui.merge;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.item.ItemStack;
import ru.alastar.minedonate.gui.merge.ShopInventoryContainer.UpdateInventorySlot;

public class AcceptButtonUpdater implements UpdateInventorySlot {

    GuiButton acceptButton;

    public AcceptButtonUpdater(GuiButton _acceptButton) {

        acceptButton = _acceptButton;

    }

    @Override
    public void onUpdate(ItemStack is) {

        acceptButton.enabled = is != null;

    }

}

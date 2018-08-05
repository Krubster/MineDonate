package ru.alastar.minedonate.gui.merge;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import ru.alastar.minedonate.MineDonate;
import ru.alastar.minedonate.rtnl.ModNetworkRegistry;

import java.io.IOException;

public class ShopInventoryGUI extends GuiContainer {

    ResourceLocation mdinvGuiTexture = new ResourceLocation("minedonate", "textures/mdinv.png");

    ShopInventoryContainer sic;
    GuiButton acceptButton;
    ScaledResolution resolution;

    public ShopInventoryGUI(InventoryPlayer _ip) {

        super(new ShopInventoryContainer(_ip));

        sic = (ShopInventoryContainer) this.inventorySlots;

    }

    @Override
    public void initGui() {

        super.initGui();

        resolution = new ScaledResolution(this.mc);

        this.buttonList.add((acceptButton = new GuiButton(this.buttonList.size(), ((this.width) / 2) - (MineDonate.cfgUI.mergeButton.width / 2), ((this.height + this.ySize) / 2) - 85 - MineDonate.cfgUI.mergeButton.height, MineDonate.cfgUI.mergeButton.width, MineDonate.cfgUI.mergeButton.height, MineDonate.cfgUI.mergeButton.text)));

        sic.mdInv.uis = new AcceptButtonUpdater(acceptButton);

        acceptButton.enabled = false;

    }


    @Override
    protected void actionPerformed(GuiButton b) {

        try {
            super.actionPerformed(b);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (b.id == acceptButton.id && sic.mdInv.getStackInSlot(0) != null) {

            ModNetworkRegistry.sendToServerCloseShopInventoryPacket();

        }

    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        this.mc.getTextureManager().bindTexture(mdinvGuiTexture);

        int top = (this.width - this.xSize) / 2;
        int left = (this.height - this.ySize) / 2;

        this.drawTexturedModalRect(top, left, 0, 0, this.xSize, this.ySize);

        this.fontRenderer.drawString("MineDonate", top + 8, left + 5, -10132123);

    }


}

package ru.alastar.minedonate;

import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import ru.alastar.minedonate.gui.ShopGUI;

public class MineDonateGUIHandler implements IGuiHandler {
    public static final int GUI_ID = 0;

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {

        if (ID == GUI_ID)

            return new ShopGUI();

        return null;

    }

}
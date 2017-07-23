package ru.alastar.minedonate.gui.categories;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.opengl.GL11;
import ru.alastar.minedonate.MineDonate;
import ru.alastar.minedonate.gui.BuyButton;
import ru.alastar.minedonate.gui.ShopCategory;
import ru.alastar.minedonate.gui.ShopGUI;
import ru.alastar.minedonate.merch.info.PrivilegieInfo;
import ru.alastar.minedonate.proxies.ClientProxy;

import java.util.ArrayList;

/**
 * Created by Alastar on 20.07.2017.
 */
public class PrivilegieCategory implements ShopCategory {

    @Override
    public String getName() {
        return "Privelegies";
    }

    @Override
    public void draw(ShopGUI relative, int page, int mouseX, int mouseY, float partialTicks) {
        if (page < MineDonate.m_Categories[1].getMerch().length) {
            PrivilegieInfo info = (PrivilegieInfo) MineDonate.m_Categories[1].getMerch()[page];
            ScaledResolution resolution = new ScaledResolution(relative.mc, relative.mc.displayWidth, relative.mc.displayHeight);

            int x_offset = (int) (resolution.getScaledWidth() * 0.5 - 75 * 2);
            int y_offset = (int) (resolution.getScaledHeight() * 0.35);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, ClientProxy.getImage(info.merch_id).getGlTextureId());
            relative.drawTexturedModalRect(x_offset, y_offset, 0, 0, 75, 75);

            relative.drawCenteredString(relative.getFontRenderer(), info.name, x_offset + 35, y_offset + 100, 0xFFFFFF);
            ArrayList list = new ArrayList();
            String[] strings = info.description.split("\r\n");
            for (int i = 0; i < strings.length; ++i)
                list.add(strings[i]);
            relative.drawHoveringText(list, x_offset + 75, y_offset, relative.getFontRenderer());
        }
    }

    @Override
    public void updateButtons(ShopGUI relative, int page) {
        System.out.println("Page: " + page);
        if (page < MineDonate.m_Categories[1].getMerch().length) {
            PrivilegieInfo info = (PrivilegieInfo) MineDonate.m_Categories[1].getMerch()[page];
            ScaledResolution resolution = new ScaledResolution(relative.mc, relative.mc.displayWidth, relative.mc.displayHeight);
            int x_offset = (int) (resolution.getScaledWidth() * 0.5 - 75 * 2);
            int y_offset = (int) (resolution.getScaledHeight() * 0.35);
            relative.addBtn(new BuyButton(info.merch_id, ShopGUI.get_last_button_id(), x_offset, y_offset +  125
, 75, 20, "Buy"));
        }
    }

    @Override
    public boolean getEnabled() {
        return MineDonate.m_Use_Privelegies;
    }

    @Override
    public int getSourceCount() {
        return MineDonate.m_Categories[1].getMerch().length;
    }

    @Override
    public int elements_per_page() {
        return 1;
    }

    @Override
    public void actionPerformed(GuiButton button) {
        
    }
}

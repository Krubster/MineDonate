package ru.log_inil.mc.minedonate.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.util.text.TextFormatting;
import org.lwjgl.opengl.GL11;

public class GuiGradientButton extends GuiTexturedButton {

    public boolean gradientVector = false;
    public boolean pressed = false;
    public boolean gradientFlag = true;
    public boolean stuckFlag = false;
    public boolean underlineFlag = true;

    public GuiGradientButton(int _id, int _x, int _y, String _text, boolean _gv) {
        this(_id, _x, _y, 200, 20, _text, _gv, false);
    }

    public GuiGradientButton(int _id, int _x, int _y, int _w, int _h, String _text, boolean _gv) {
        this(_id, _x, _y, _w, _h, _text, _gv, false);
    }

    public GuiGradientButton(int _id, int _x, int _y, int _w, int _h, String _text, boolean _gv, boolean _stuckFlag) {

        super(_id, _x, _y, _w, _h, _text);
        this.enabled = true;
        this.visible = true;
        this.id = _id;
        this.x = _x;
        this.y = _y;
        this.width = _w;
        this.height = _h;
        this.displayString = _text;
        gradientVector = _gv;
        stuckFlag = _stuckFlag;

    }

    @Override
    public void drawButton(Minecraft p_146112_1_, int p_146112_2_, int p_146112_3_, float p) {
        if (!gradientFlag) {

            super.drawButton(p_146112_1_, p_146112_2_, p_146112_3_, p);
            return;

        }

        if (this.visible) {
            FontRenderer fontrenderer = p_146112_1_.fontRenderer;
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            this.hovered = p_146112_2_ >= this.x && p_146112_3_ >= this.y && p_146112_2_ < this.x + this.width && p_146112_3_ < this.y + this.height;
            int k = this.getHoverState(this.hovered);
            GL11.glEnable(GL11.GL_BLEND);
            OpenGlHelper.glBlendFunc(770, 771, 1, 0);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

            if (pressed) {

                k = 0;

            }

            int rgba = (k == 0 ? -939458303 : k == 1 ? 1761673473 : -1694433023);// -1694433023 ) ;

            this.drawGradientRect(x, y, x + width, y + height, gradientVector ? 0 : rgba, gradientVector ? rgba : 0);

            this.mouseDragged(p_146112_1_, p_146112_2_, p_146112_3_);
            int l = 14737632;

            if (packedFGColour != 0) {
                l = packedFGColour;
            } else if (!this.enabled || pressed) {
                l = -1258291201;
            } else if (this.hovered) {
                l = -587202561;
            }

            this.drawCenteredString(fontrenderer, ((underlineFlag ? (k > 1 || (hovered && stuckFlag)) : false) ? TextFormatting.UNDERLINE : "") + this.displayString, this.x + this.width / 2, this.y + (this.height - 8) / 2, l);
        }
    }


}
package ru.log_inil.mc.minedonate.gui;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.OpenGlHelper;

public class GuiGradientButton extends GuiButton {

	boolean gradientVector = false ;
	
    public GuiGradientButton(int p_i1020_1_, int p_i1020_2_, int p_i1020_3_, String p_i1020_4_, boolean _gv)
    {
        this(p_i1020_1_, p_i1020_2_, p_i1020_3_, 200, 20, p_i1020_4_, _gv);
    }

    public GuiGradientButton(int p_i1021_1_, int p_i1021_2_, int p_i1021_3_, int p_i1021_4_, int p_i1021_5_, String p_i1021_6_, boolean _gv)
    {
    	super(p_i1021_1_, p_i1021_2_, p_i1021_3_, p_i1021_4_, p_i1021_5_, p_i1021_6_);
        this.width = 200;
        this.height = 20;
        this.enabled = true;
        this.visible = true;
        this.id = p_i1021_1_;
        this.xPosition = p_i1021_2_;
        this.yPosition = p_i1021_3_;
        this.width = p_i1021_4_;
        this.height = p_i1021_5_;
        this.displayString = p_i1021_6_;
        gradientVector = _gv ;
        
    }

  
    public void drawButton(Minecraft p_146112_1_, int p_146112_2_, int p_146112_3_)
    {
        if (this.visible)
        {
            FontRenderer fontrenderer = p_146112_1_.fontRenderer;
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            this.field_146123_n = p_146112_2_ >= this.xPosition && p_146112_3_ >= this.yPosition && p_146112_2_ < this.xPosition + this.width && p_146112_3_ < this.yPosition + this.height;
            int k = this.getHoverState(this.field_146123_n);
            GL11.glEnable(GL11.GL_BLEND);
            OpenGlHelper.glBlendFunc(770, 771, 1, 0);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

            /*
        	int r0 = 0 & 0xFF;
			int g0 = 0 & 0xFF;
			int b0 = 0 & 0xFF;
			int a0 = 0 & 0xFF;

			int rgba0 = (r0 << 16) + (g0 << 8) + (b0) + (a0<<24);
			//System.err.println(rgba0);
			 * */
			int rgba = ( k == 0 ? -939458303 : k == 1 ? 1761673473 : -1694433023 ) ;
			
			this.drawGradientRect ( xPosition, yPosition, xPosition + width, yPosition+height, gradientVector ? 0 : rgba, gradientVector ? rgba : 0);

            this.mouseDragged(p_146112_1_, p_146112_2_, p_146112_3_);
            int l = 14737632;

            if (packedFGColour != 0)
            {
                l = packedFGColour;
            }
            else if (!this.enabled)
            {
                l = -1258291201;
            }
            else if (this.field_146123_n)
            {
                l = -587202561;
            }

            this.drawCenteredString(fontrenderer, this.displayString, this.xPosition + this.width / 2, this.yPosition + (this.height - 8) / 2, l);
        }
    }


}
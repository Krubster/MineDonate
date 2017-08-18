package ru.alastar.minedonate.gui;

import ru.log_inil.mc.minedonate.gui.GuiGradientButton;

/**
 * Created by Alastar on 20.07.2017.
 */
public class PreviousButton extends GuiGradientButton {
    public PreviousButton(int p_i1020_1_, int p_i1020_2_, int p_i1020_3_, String p_i1020_4_) {
        super(p_i1020_1_, p_i1020_2_, p_i1020_3_, p_i1020_4_, false);
    }

    public PreviousButton(int p_i1021_1_, int p_i1021_2_, int p_i1021_3_, int p_i1021_4_, int p_i1021_5_, String p_i1021_6_) {
        super(p_i1021_1_, p_i1021_2_, p_i1021_3_, p_i1021_4_, p_i1021_5_, p_i1021_6_, false);
        
        underlineFlag = false ;

    }
}


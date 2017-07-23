package ru.alastar.minedonate.gui;

import net.minecraft.client.gui.GuiButton;
import ru.alastar.minedonate.merch.info.ItemInfo;

/**
 * Created by Alastar on 23.07.2017.
 */
public class CountButton extends GuiButton {
    private int mod;
    private ItemInfo info;
    private static int max_mul = 10;

    public CountButton(int mod, ItemInfo info, int p_i1020_1_, int p_i1020_2_, int p_i1020_3_, String p_i1020_4_) {
        super(p_i1020_1_, p_i1020_2_, p_i1020_3_, p_i1020_4_);
        this.info = info;
        this.mod = mod;
    }

    public CountButton(int mod, ItemInfo info, int p_i1021_1_, int p_i1021_2_, int p_i1021_3_, int p_i1021_4_, int p_i1021_5_, String p_i1021_6_) {
        super(p_i1021_1_, p_i1021_2_, p_i1021_3_, p_i1021_4_, p_i1021_5_, p_i1021_6_);
        this.info = info;
        this.mod = mod;
    }

    public void tryModify() {
       if(info.modified + mod > 0 && info.modified + mod < max_mul){
           if(info.limit != -1 && (info.modified + mod) * info.count <= info.limit || info.limit == -1){
               info.modified  += mod;
           }
       }
    }
}

package ru.alastar.minedonate.gui;

import net.minecraft.client.gui.ScaledResolution;

public class CurrentItemDescription {

    public int buttonBuyId = 999999990;
    public int buttonCloseId = 999999989;

    BuyButton but;
    int cat;

    ShopCategory sc;

    public CurrentItemDescription(BuyButton _but, int _cat) {

        but = _but;
        cat = _cat;

    }

    public void prepare() {

        System.err.println("Prepare: " + but.id);
        sc = ShopGUI.instance.getCurrentShopCategories()[cat];

    }

    public void draw(ScaledResolution sr, ShopGUI relative) {


    }

    public void buy() {

        System.err.println("buy: " + but.id);

    }

    public void clean() {

        System.err.println("clean: " + but.id);
        sc = null;

    }

}

package ru.log_inil.mc.minedonate.localData.ui;

public abstract class DataOfUICategoryAbstract {

    public String categoryButtonText;
    public int categoryButtonWidth;

    public DataOfUIElement itemBuyButton;

    public DataOfUICategoryAbstract() {

        categoryButtonText = "Category";
        categoryButtonWidth = 55;
        itemBuyButton = new DataOfUIElement("Buy", 44, 20);

    }

}

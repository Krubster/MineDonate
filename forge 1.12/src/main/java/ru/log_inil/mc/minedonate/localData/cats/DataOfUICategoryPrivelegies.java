package ru.log_inil.mc.minedonate.localData.cats;

import ru.log_inil.mc.minedonate.localData.ui.DataOfUICategoryAbstract;
import ru.log_inil.mc.minedonate.localData.ui.DataOfUIElement;

public class DataOfUICategoryPrivelegies extends DataOfUICategoryAbstract {

    public DataOfUICategoryPrivelegies() {

        super();

        categoryButtonText = "Privelegies";
        categoryButtonWidth = 75;

        itemBuyButton = new DataOfUIElement("Buy", 77, 20);

    }

}

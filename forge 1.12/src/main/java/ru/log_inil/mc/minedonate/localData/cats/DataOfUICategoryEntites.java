package ru.log_inil.mc.minedonate.localData.cats;

import ru.log_inil.mc.minedonate.localData.ui.DataOfUICategoryAbstract;
import ru.log_inil.mc.minedonate.localData.ui.DataOfUIElement;

public class DataOfUICategoryEntites extends DataOfUICategoryAbstract {

    public String itemLeft;

    public DataOfUIElement addButton;

    public DataOfUICategoryEntites() {

        super();
        itemLeft = "Left: ";
        categoryButtonText = "Entites";
        categoryButtonWidth = 55;

        addButton = new DataOfUIElement("Add", 44, 20);

    }

}

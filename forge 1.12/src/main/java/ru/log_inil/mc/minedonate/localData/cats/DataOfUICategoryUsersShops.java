package ru.log_inil.mc.minedonate.localData.cats;

import ru.log_inil.mc.minedonate.localData.ui.DataOfUICategoryAbstract;
import ru.log_inil.mc.minedonate.localData.ui.DataOfUIElement;

public class DataOfUICategoryUsersShops extends DataOfUICategoryAbstract {

    public boolean dontShowFreezed;
    public boolean lockGoShopButtonWhenFreezed;
    public DataOfUIElement lockedGoButton;
    public DataOfUIElement viewMyShopsButton;
    public DataOfUIElement createNewShopButton;

    public DataOfUICategoryUsersShops() {

        super();

        categoryButtonText = "Users shops";
        categoryButtonWidth = 85;
        itemBuyButton = new DataOfUIElement("Open", 44, 20);

        dontShowFreezed = false;
        lockGoShopButtonWhenFreezed = true;

        lockedGoButton = new DataOfUIElement("Locked", 54, 20);

        viewMyShopsButton = new DataOfUIElement("My shops", 64, 20);
        createNewShopButton = new DataOfUIElement("Create shop", 74, 20);

    }

}

package ru.log_inil.mc.minedonate.localData.ui;

import ru.log_inil.mc.minedonate.localData.frames.*;

public class DataOfUIFrames {

    public DataOfUIFrameField createShop;
    public DataOfUIFrameField renameShop;
    public DataOfUIFrameConfirm deleteShop;
    public DataOfUIFrameField freezeShop;

    public DataOfUIFrameAddItem addItem;
    public DataOfUIFramEditObject editItem;
    public DataOfUIFrameConfirm deleteItem;

    public DataOfUIFrameAddEntity addEntity;
    public DataOfUIFramEditObject editEntity;
    public DataOfUIFrameConfirm deleteEntity;

    public DataOfUIFrameField freezeAccount;

    public DataOfUIFrames() {

        createShop = new DataOfUIFrameField("Create shop", new DataOfUIElement("Create", 50, 20), new DataOfUITextHolderElement("", "Shop name", 160, 20));
        renameShop = new DataOfUIFrameField("Rename shop", new DataOfUIElement("Rename", 40, 20), new DataOfUITextHolderElement("", "Shop name", 160, 20));
        deleteShop = new DataOfUIFrameConfirm("Delete entity", "Entry confirm code[%code%] to field", "Delete");
        freezeShop = new DataOfUIFrameField("Freeze shop", new DataOfUIElement("Freeze", 50, 20), new DataOfUITextHolderElement("", "Reason", 160, 20));

        addItem = new DataOfUIFrameAddItem();
        editItem = new DataOfUIFramEditObject("Edit item", "Item name");
        deleteItem = new DataOfUIFrameConfirm("Delete item", "Entry confirm code[%code%] to field", "Delete");

        addEntity = new DataOfUIFrameAddEntity();
        editEntity = new DataOfUIFramEditObject("Edit entity", "Entity name");
        deleteEntity = new DataOfUIFrameConfirm("Delete entity", "Entry confirm code[%code%] to field", "Delete");

        freezeAccount = new DataOfUIFrameField("Freeze account", new DataOfUIElement("Freeze", 50, 20), new DataOfUITextHolderElement("", "Reason", 160, 20));
        //= new DataOfUIFrameFreezeAccount ( ) ;

    }

}

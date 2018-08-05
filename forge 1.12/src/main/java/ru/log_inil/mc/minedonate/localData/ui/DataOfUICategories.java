package ru.log_inil.mc.minedonate.localData.ui;

import ru.log_inil.mc.minedonate.localData.cats.*;

public class DataOfUICategories {

    public DataOfUICategoryEntites entities;
    public DataOfUICategoryItemsNBlocks itemsAndBlocks;
    public DataOfUICategoryPrivelegies privelegies;
    public DataOfUICategoryRegions regions;
    public DataOfUICategoryUsersShops shops;

    public DataOfUICategories() {

        entities = new DataOfUICategoryEntites();
        itemsAndBlocks = new DataOfUICategoryItemsNBlocks();
        privelegies = new DataOfUICategoryPrivelegies();
        regions = new DataOfUICategoryRegions();
        shops = new DataOfUICategoryUsersShops();

    }

}

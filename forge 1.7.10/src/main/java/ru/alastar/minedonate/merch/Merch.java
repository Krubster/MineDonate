package ru.alastar.minedonate.merch;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;

/**
 * Created by Alastar on 20.07.2017.
 */
public abstract class Merch {

    public int cost;
    public int merch_id;
    public int shop_id;


    public int getCategory() {
        return -1;
    }

    public void read(ByteBuf buf) {
    }

    public void write(ByteBuf buf) {
    }

    public String getBoughtMessage() {
        return "";
    }

    public int getCost() {
        return cost;
    }

    public int getId() {
        return merch_id;
    }

    public boolean canBuy(EntityPlayerMP serverPlayer, int amount) {
        return true;
    }

    public int getAmountToBuy() {
        return 1;
    }

    public void setId(int i) {
        merch_id = i;
    }


    public int getShopId() {
        return shop_id;
    }

    public void setShopId(int i) {
        shop_id = i;
    }
}

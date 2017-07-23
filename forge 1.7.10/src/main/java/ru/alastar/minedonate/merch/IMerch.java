package ru.alastar.minedonate.merch;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;

/**
 * Created by Alastar on 20.07.2017.
 */
public interface IMerch {
    int getCategory();
    void read(ByteBuf buf);
    void write(ByteBuf buf);

    String getBoughtMessage();

    int getCost();

    int getId();

    boolean canBuy(EntityPlayerMP serverPlayer, int amount);

    int getAmountToBuy();

    void setId(int i);
}

package ru.alastar.minedonate.network.handlers;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import ru.alastar.minedonate.MineDonate;
import ru.alastar.minedonate.Shop;
import ru.alastar.minedonate.gui.ShopGUI;
import ru.alastar.minedonate.merch.categories.ItemNBlocks;
import ru.alastar.minedonate.merch.categories.MerchCategory;
import ru.alastar.minedonate.merch.info.UserShopInfo;
import ru.alastar.minedonate.network.packets.AddMerchPacket;

/**
 * Created by Alastar on 18.07.2017.
 */
public class AddMerchPacketHandler implements IMessageHandler<AddMerchPacket, IMessage> {

    public AddMerchPacketHandler() {

    }

    @Override
    public IMessage onMessage(AddMerchPacket message, MessageContext ctx) {
        // System.err.println(message.shopId + "> " + message.m_category + "> " + message.info);

        if (message.info instanceof UserShopInfo) {

            UserShopInfo us = (UserShopInfo) message.info;
            if (us.shopId == 0) {

                if (ShopGUI.instance != null) {
                    ShopGUI.instance.updateBtns();
                }

                return null;

            }

            MineDonate.shops.put(us.shopId, new Shop(us.shopId, new MerchCategory[]{new ItemNBlocks(us.shopId)}, us.owner, us.name, us.isFreezed));

        }

        MineDonate.AddMerch(message.shopId, message.m_category, message.info);

        if (ShopGUI.instance != null) {
            ShopGUI.instance.updateBtns();
        }
        return null;
    }

}
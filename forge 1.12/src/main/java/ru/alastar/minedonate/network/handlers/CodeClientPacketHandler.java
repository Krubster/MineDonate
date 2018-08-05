package ru.alastar.minedonate.network.handlers;

import net.minecraft.client.Minecraft;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import ru.alastar.minedonate.MineDonate;
import ru.alastar.minedonate.gui.ShopGUI;
import ru.alastar.minedonate.gui.categories.ItemNBlockCategory;
import ru.alastar.minedonate.gui.categories.UsersShopsCategory;
import ru.alastar.minedonate.network.packets.CodePacket;

public class CodeClientPacketHandler implements IMessageHandler<CodePacket, IMessage> {

    public CodeClientPacketHandler() {

    }

    @Override
    public IMessage onMessage(CodePacket message, MessageContext ctx) {

        if (message.code == CodePacket.Code.CLIENT_RECEIVED_FULL_INFO) {

            if (ShopGUI.instance != null) {

                ShopGUI.instance.lastCategory = ShopGUI.instance.m_Selected_Category = ShopGUI.instance.defaultCategory;
                ShopGUI.instance.needNetUpdate = false;
                ShopGUI.instance.loading = false;

            }

        }

        if (message.code == CodePacket.Code.CLIENT_RECEIVED_NEEDED_CAT_INFO) {

            if (ShopGUI.instance != null) {

                ShopGUI.instance.loading = false;

                if (ShopGUI.instance.getCurrentCategory() instanceof UsersShopsCategory) {

                    UsersShopsCategory usc = (UsersShopsCategory) ShopGUI.instance.getCurrentCategory();

                    if (usc.getSelectedShop() != null) {

                        ShopGUI.instance.currentShop = usc.getSelectedShop().shopId;

                        ItemNBlockCategory cat = new ItemNBlockCategory("cat.items.custom");
                        cat.shopOwner = usc.getSelectedShop().owner;
                        cat.setDisableCatCheck();

                        cat.preShow(ShopGUI.instance);

                        cat.setShopId(usc.getSelectedShop().shopId);

                        usc.updateUserShopCategory(ShopGUI.instance, cat, true);
                        usc.postShow(ShopGUI.instance);

                    }

                }

            }

        }

        if (message.code == CodePacket.Code.SERVER_ERROR_WAIT_OTHER_TASK) {

            Minecraft.getMinecraft().player.sendChatMessage(TextFormatting.AQUA + " [MineDonate] " + TextFormatting.RESET + message.code);

            return null;

        }

        switch (message.code) {

            case SERVER_ERROR_WAIT_OTHER_TASK:

                Minecraft.getMinecraft().player.sendChatMessage(TextFormatting.AQUA + " [MineDonate] " + TextFormatting.RESET + message.code);

                return null;

            case MOD_ENABLED:
            case MOD_DISABLED:

                MineDonate.m_Enabled = message.code == CodePacket.Code.MOD_ENABLED;

                return null;

            default:
                break;

        }
        ShopGUI.instance.initGui();

        return null;

    }


}
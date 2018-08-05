package ru.alastar.minedonate.network.manage.handlers;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import ru.alastar.minedonate.MineDonate;
import ru.alastar.minedonate.events.MineDonateGUIHandler;
import ru.alastar.minedonate.gui.merge.ShopInventoryContainer;
import ru.alastar.minedonate.network.INetworkTask;
import ru.alastar.minedonate.network.manage.packets.InventoryShopPacket;
import ru.alastar.minedonate.network.manage.packets.ItemMergedPacket;
import ru.alastar.minedonate.rtnl.ModNetworkTaskProcessor;

public class InventoryShopServerPacketHandler implements IMessageHandler<InventoryShopPacket, IMessage>, INetworkTask<InventoryShopPacket, IMessage> {

    public InventoryShopServerPacketHandler() {

    }

    @Override
    public IMessage onMessage(InventoryShopPacket message, MessageContext ctx) {

        ModNetworkTaskProcessor.processTask((INetworkTask) this, message, ctx);

        return null;

    }

    @Override
    public IMessage onMessageProcess(InventoryShopPacket message, MessageContext ctx) {

        EntityPlayerMP serverPlayer = ctx.getServerHandler().player;

        if (message.type == InventoryShopPacket.Type.OPEN_INV) {

            serverPlayer.openGui(MineDonate.getInstance(), MineDonateGUIHandler.STORE_ID, serverPlayer.getEntityWorld(), (int) serverPlayer.posX, (int) serverPlayer.posY, (int) serverPlayer.posZ);

        } else if (message.type == InventoryShopPacket.Type.CLOSE_WITH_MERGE) {

            if (MineDonate.mergeContainers.containsKey(serverPlayer.getDisplayName())) {

                ShopInventoryContainer sic = MineDonate.mergeContainers.get(serverPlayer.getDisplayName());

                ItemStack is;

                if ((is = sic.mdInv.getStackInSlot(0)) != null) {

                    sic.mdInv.setInventorySlotContents(0, null);

                    MineDonate.getAccount(serverPlayer).ms.setItemStack(is);

                    return new ItemMergedPacket(is);

                } else {

                    return new ItemMergedPacket(null);

                }

            }

        } else if (message.type == InventoryShopPacket.Type.CLOSE_NO_MERGE) {

            if (MineDonate.mergeContainers.containsKey(serverPlayer.getDisplayName())) {

                ShopInventoryContainer sic = MineDonate.mergeContainers.get(serverPlayer.getDisplayName());

                ItemStack is;

                if ((is = sic.mdInv.getStackInSlot(0)) != null) {

                    sic.mdInv.setInventorySlotContents(0, null);

                }

                sic = null;

                is = MineDonate.getAccount(serverPlayer).ms.currentItemStack;

                MineDonate.getAccount(serverPlayer).ms.setItemStack(null);

                if (is != null) {

                    try {

                        serverPlayer.dropItem(is, false);

                    } catch (Exception ex) {

                        ex.printStackTrace();

                    }

                }

                is = null;

                return new ItemMergedPacket(null);

            }

        }

        return null;

    }

}
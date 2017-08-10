package ru.alastar.minedonate.network.handlers;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import ru.alastar.minedonate.MineDonate;
import ru.alastar.minedonate.events.MineDonateGUIHandler;
import ru.alastar.minedonate.gui.merge.ShopInventoryContainer;
import ru.alastar.minedonate.network.packets.AdminPacket;
import ru.alastar.minedonate.network.packets.InventoryShopPacket;
import ru.alastar.minedonate.network.packets.manage.ItemMergedPacket;

public class InventoryShopServerPacketHandler implements IMessageHandler<InventoryShopPacket, IMessage> {

    public InventoryShopServerPacketHandler ( ) {

    }

    @Override
    public IMessage onMessage(InventoryShopPacket message, MessageContext ctx) {
     //   if (MineDonate.m_Enabled) {
    	
    	if ( message . type == InventoryShopPacket . Type . OPEN_INV ) { 
        
    		EntityPlayerMP serverPlayer = ctx . getServerHandler ( ) . playerEntity ;
            serverPlayer . openGui ( MineDonate . getInstance ( ), MineDonateGUIHandler . STORE_ID, serverPlayer . getEntityWorld ( ), ( int ) serverPlayer . posX, ( int ) serverPlayer . posY, ( int ) serverPlayer . posZ ) ;
    	
    	} else if ( message . type == InventoryShopPacket . Type . CLOSE_WITH_MERGE ) {
    		
    		EntityPlayerMP serverPlayer = ctx . getServerHandler ( ) . playerEntity ;

    		if ( MineDonate . mergeContainers . containsKey ( serverPlayer . getDisplayName ( ) ) ) {
    			
    			ShopInventoryContainer sic = MineDonate . mergeContainers . get ( serverPlayer . getDisplayName ( ) ) ;
    			
    			ItemStack is ;
    			
    			if ( ( is = sic . mdInv . getStackInSlot ( 0 ) ) != null ) {
    				
    				sic . mdInv . setInventorySlotContents ( 0, null ) ;

    				MineDonate . getAccount ( serverPlayer . getDisplayName ( ) ) . ms . setItemStack ( is ) ;
    				
    	            serverPlayer . openGui ( MineDonate . getInstance ( ), MineDonateGUIHandler . SHOP_ID, serverPlayer . getEntityWorld ( ), ( int ) serverPlayer . posX, ( int ) serverPlayer . posY, ( int ) serverPlayer . posZ ) ;
   				
    				return new ItemMergedPacket ( is ) ;
    				
    			}	
    			
    		}
    		
    	}
    	
        return null;

    }

    private void handleAdmin(AdminPacket message, EntityPlayerMP serverPlayer) {
        
    }
}
package ru.alastar.minedonate.network.manage.handlers;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import ru.alastar.minedonate.MineDonate;
import ru.alastar.minedonate.events.MineDonateGUIHandler;
import ru.alastar.minedonate.gui.merge.ShopInventoryContainer;
import ru.alastar.minedonate.network.manage.packets.InventoryShopPacket;
import ru.alastar.minedonate.network.manage.packets.ItemMergedPacket;

public class InventoryShopServerPacketHandler implements IMessageHandler<InventoryShopPacket, IMessage> {

    public InventoryShopServerPacketHandler ( ) {

    }

    @Override
    public IMessage onMessage ( InventoryShopPacket message, MessageContext ctx ) {
    	
		EntityPlayerMP serverPlayer = ctx . getServerHandler ( ) . playerEntity ;

    	if ( message . type == InventoryShopPacket . Type . OPEN_INV ) { 
        
            serverPlayer . openGui ( MineDonate . getInstance ( ), MineDonateGUIHandler . STORE_ID, serverPlayer . getEntityWorld ( ), ( int ) serverPlayer . posX, ( int ) serverPlayer . posY, ( int ) serverPlayer . posZ ) ;
    	
    	} else if ( message . type == InventoryShopPacket . Type . CLOSE_WITH_MERGE ) {

    		if ( MineDonate . mergeContainers . containsKey ( serverPlayer . getDisplayName ( ) ) ) {
    			
    			ShopInventoryContainer sic = MineDonate . mergeContainers . get ( serverPlayer . getDisplayName ( ) ) ;
    			
    			ItemStack is ;
    			
    			if ( ( is = sic . mdInv . getStackInSlot ( 0 ) ) != null ) {
    				
    				sic . mdInv . setInventorySlotContents ( 0, null ) ;

    				MineDonate . getAccount ( serverPlayer ) . ms . setItemStack ( is ) ;
    				   				
    				return new ItemMergedPacket ( is ) ;
    				
    			} else {

    				return new ItemMergedPacket ( null ) ;

    			}
    			
    		}
    		
    	} else if ( message.type == InventoryShopPacket . Type . CLOSE_NO_MERGE ) {

    		if ( MineDonate . mergeContainers . containsKey ( serverPlayer . getDisplayName ( ) ) ) {
    			
    			ShopInventoryContainer sic = MineDonate . mergeContainers . get ( serverPlayer . getDisplayName ( ) ) ;
    			
    			ItemStack is ;
    			
    			if ( ( is = sic . mdInv . getStackInSlot ( 0 ) ) != null ) {
    				
    				sic . mdInv . setInventorySlotContents ( 0, null ) ;
    				   				    				
    			} else {

    				return new ItemMergedPacket ( null ) ;

    			}	

    			sic = null ;
    			
				is = MineDonate . getAccount ( serverPlayer ) . ms . currentItemStack ;

				MineDonate . getAccount ( serverPlayer ) . ms . setItemStack ( null ) ;

    			if ( is != null ) {
    				
    				serverPlayer . dropPlayerItemWithRandomChoice ( is, false ) ;
    				
    			} 
    			
    			is = null ;
    			
				return new ItemMergedPacket ( null ) ;

    		}
    		
    	}
    	
        return null;

    }
    
}
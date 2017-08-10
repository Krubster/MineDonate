package ru.alastar.minedonate.rtnl;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.entity.player.EntityPlayerMP;
import ru.alastar.minedonate.MineDonate;
import ru.alastar.minedonate.gui.ShopCategory.SubCategory;
import ru.alastar.minedonate.merch.Merch;
import ru.alastar.minedonate.network.handlers.*;
import ru.alastar.minedonate.network.handlers.manage.*;
import ru.alastar.minedonate.network.packets.*;
import ru.alastar.minedonate.network.packets.manage.*;

public class ModNetwork {

    public static SimpleNetworkWrapper networkChannel ;

    public static void register ( ) {
    	
    	networkChannel = NetworkRegistry . INSTANCE . newSimpleChannel ( MineDonate . MODID ) ;
    	
    	int i = 0 ;
    	
        networkChannel . registerMessage ( AccountInfoPacketHandler . class, AccountInfoPacket . class, 0, Side . CLIENT ) ;
        networkChannel . registerMessage ( AddMerchPacketHandler . class, AddMerchPacket . class, i ++, Side . CLIENT ) ;
        
        networkChannel . registerMessage ( BuyPacketHandler . class, BuyPacket . class, i ++, Side . SERVER ) ;
        networkChannel . registerMessage ( BuyResponsePacketHandler . class, BuyResponsePacket . class, i ++, Side . CLIENT ) ;

        networkChannel . registerMessage ( SupportedFeaturesPacketHandler . class, SupportedFeaturesPacket . class, i ++, Side . CLIENT ) ;
        networkChannel . registerMessage ( RemoveMerchPacketHandler . class, RemoveMerchPacket . class, i ++, Side . CLIENT ) ;
        networkChannel . registerMessage ( MerchInfoPacketHandler . class, MerchInfoPacket . class, i ++, Side . CLIENT ) ;
        
        networkChannel . registerMessage ( NeedUpdateServerPacketHandler . class, NeedUpdatePacket . class, i ++, Side .  SERVER ) ;
        networkChannel . registerMessage ( NeedUpdateClientPacketHandler . class, NeedUpdatePacket . class, i ++, Side . CLIENT ) ;
        networkChannel . registerMessage ( NeedShopCategoryServerPacketHandler . class, NeedShopCategoryPacket . class, i ++, Side . SERVER ) ;
        
        networkChannel . registerMessage ( CategoryPacketHandler . class, CategoryPacket . class, i ++, Side . CLIENT ) ;
        networkChannel . registerMessage ( MoneyChangedPacketHandler . class, MoneyChangedPacket . class, i ++, Side . CLIENT ) ;
        
        networkChannel . registerMessage ( ManageResponseClientPacketHandler . class, ManageResponsePacket . class, i ++, Side . CLIENT ) ;

        networkChannel . registerMessage ( CreateNewShopServerPacketHandler . class, CreateNewShopPacket . class, i ++, Side . SERVER ) ;
        networkChannel . registerMessage ( RenameShopServerPacketHandler . class, RenameShopPacket . class, i ++, Side . SERVER ) ;
        networkChannel . registerMessage ( UnfreezeShopServerPacketHandler . class, UnfreezeShopPacket . class, i ++, Side . SERVER ) ;

        networkChannel . registerMessage ( InventoryShopServerPacketHandler . class, InventoryShopPacket . class, i ++, Side . SERVER ) ;
        
        networkChannel . registerMessage ( ItemMergedClientPacketHandler . class, ItemMergedPacket . class, i ++, Side . CLIENT ) ;

    }
    
	public static void sendToServerCloseShopInventoryPacket ( ) {
		
		networkChannel . sendToServer ( new InventoryShopPacket ( InventoryShopPacket . Type . CLOSE_WITH_MERGE ) ) ;
		
	}

	public static void sendToServerOpenShopInventoryPacket ( ) {

		networkChannel . sendToServer ( new InventoryShopPacket ( InventoryShopPacket . Type . OPEN_INV ) ) ;
		
	}

	public static void sendToServerBuyPacket ( int shopId, int merch_id, int catId, int amountToBuy ) {

		networkChannel . sendToServer ( new BuyPacket ( shopId, merch_id, catId, amountToBuy ) ) ;
		
	}

	public static void sendToServerNeedShopCategoryPacket ( int shopId, int catId ) {
        
		networkChannel . sendToServer ( new NeedShopCategoryPacket ( shopId, catId ) ) ;
		
	}

	public static void sendToServerNeedUpdatePacket ( int r ) {

		networkChannel . sendToServer ( new NeedUpdatePacket ( r ) ) ;
		
	}

	public static void sendToAllMerchInfoPacket ( Merch info ) {
		
        networkChannel . sendToAll ( new MerchInfoPacket ( info ) ) ;

	}

	public static void sendToAllRemoveMerchPacket ( int shopId, int id, int catId ) {
	
	    networkChannel . sendToAll ( new RemoveMerchPacket ( shopId, id, catId ) ) ;

	}

	public static void sendToAllAddMerchPacket ( Merch info ) {

        networkChannel . sendToAll ( new AddMerchPacket ( info ) ) ;
		
	}

	public static void sendToMoneyChangedPacket ( EntityPlayerMP player, int currentMoney, String moneyType ) {

        networkChannel . sendTo ( new MoneyChangedPacket ( currentMoney, moneyType ), player ) ;
		
	}

	public static void sendToCategoryPacket ( EntityPlayerMP player, int shopId, int catId ) {

        networkChannel . sendTo ( new CategoryPacket ( shopId, catId ), player ) ;

	}

	public static void sendToServerCreateNewShopPacket ( String createNewShop ) {

        networkChannel . sendToServer ( new CreateNewShopPacket ( createNewShop ) ) ;

	}
	
	public static void sendToServerRenameShopPacket ( int shopId, String name ) {

		networkChannel . sendToServer ( new RenameShopPacket ( shopId, name ) ) ;

	}

	public static void sendToServerUnfreezeShopPacket ( int shopId ) {

		networkChannel . sendToServer ( new UnfreezeShopPacket ( shopId ) ) ;
		
	}
	
	public static void sendTo ( EntityPlayerMP player, IMessage packet ) {
		
		networkChannel . sendTo ( packet, player ) ;
		
	}
	
}

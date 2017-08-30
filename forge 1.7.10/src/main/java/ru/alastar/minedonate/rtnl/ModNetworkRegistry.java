package ru.alastar.minedonate.rtnl;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.entity.player.EntityPlayerMP;
import ru.alastar.minedonate.MineDonate;
import ru.alastar.minedonate.merch.Merch;
import ru.alastar.minedonate.network.handlers.*;
import ru.alastar.minedonate.network.manage.handlers.*;
import ru.alastar.minedonate.network.manage.packets.*;
import ru.alastar.minedonate.network.manage.packets.ManageResponsePacket.ResponseCode;
import ru.alastar.minedonate.network.manage.packets.ManageResponsePacket.ResponseStatus;
import ru.alastar.minedonate.network.manage.packets.ManageResponsePacket.ResponseType;
import ru.alastar.minedonate.network.packets.*;

public class ModNetworkRegistry {

    public static SimpleNetworkWrapper networkChannel ;

    public static void register ( ) {
    	
    	networkChannel = NetworkRegistry . INSTANCE . newSimpleChannel ( MineDonate . MODID ) ;
    	
    	int i = 0 ;
    	
        networkChannel . registerMessage ( SupportedFeaturesPacketHandler . class, SupportedFeaturesPacket . class, i ++, Side . CLIENT ) ;
        networkChannel . registerMessage ( AccountInfoPacketHandler . class, AccountInfoPacket . class, i ++, Side . CLIENT ) ;
        networkChannel . registerMessage ( MoneyChangedPacketHandler . class, MoneyChangedPacket . class, i ++, Side . CLIENT ) ;

        networkChannel . registerMessage ( AddMerchPacketHandler . class, AddMerchPacket . class, i ++, Side . CLIENT ) ;
        
        networkChannel . registerMessage ( BuyPacketHandler . class, BuyPacket . class, i ++, Side . SERVER ) ;
        networkChannel . registerMessage ( BuyResponsePacketHandler . class, BuyResponsePacket . class, i ++, Side . CLIENT ) ;

        networkChannel . registerMessage ( RemoveMerchPacketHandler . class, RemoveMerchPacket . class, i ++, Side . CLIENT ) ;
        networkChannel . registerMessage ( MerchInfoPacketHandler . class, MerchInfoPacket . class, i ++, Side . CLIENT ) ;

        //
        
        i ++ ;
        
        networkChannel . registerMessage ( CodeServerPacketHandler . class, CodePacket . class, i, Side .  SERVER ) ;
        networkChannel . registerMessage ( CodeClientPacketHandler . class, CodePacket . class, i, Side . CLIENT ) ;

        i ++ ;

        networkChannel . registerMessage ( NeedShopCategoryServerPacketHandler . class, NeedShopCategoryPacket . class, i ++, Side . SERVER ) ;
        
        //
        
        networkChannel . registerMessage ( CategoryPacketHandler . class, CategoryPacket . class, i ++, Side . CLIENT ) ;
        
        //
        
        networkChannel . registerMessage ( CreateNewShopServerPacketHandler . class, CreateNewShopPacket . class, i ++, Side . SERVER ) ;
        networkChannel . registerMessage ( DeleteShopServerPacketHandler . class, DeleteShopPacket . class, i ++, Side . SERVER ) ;
        networkChannel . registerMessage ( RenameShopServerPacketHandler . class, RenameShopPacket . class, i ++, Side . SERVER ) ;
     
        //
        
        networkChannel . registerMessage ( InventoryShopServerPacketHandler . class, InventoryShopPacket . class, i ++, Side . SERVER ) ;
        networkChannel . registerMessage ( ItemMergedClientPacketHandler . class, ItemMergedPacket . class, i ++, Side . CLIENT ) ;
   
        //
        
        networkChannel . registerMessage ( AddNewEntryServerPacketHandler . class, AddNewEntryPacket . class, i ++, Side . SERVER ) ;
        networkChannel . registerMessage ( AppendEntryServerPacketHandler . class, AppendEntryPacket . class, i ++, Side . SERVER ) ;

        i ++ ;
        
        networkChannel . registerMessage ( MobSelectServerPacketHandler . class, MobSelectPacket . class, i, Side . SERVER ) ;
        networkChannel . registerMessage ( MobSelectClientPacketHandler . class, MobSelectPacket . class, i, Side . CLIENT ) ;

        i ++ ;
        
        //
        
        networkChannel . registerMessage ( DeleteShopMerchServerPacketHandler . class, DeleteShopMerchPacket . class, i ++, Side . SERVER ) ;
        networkChannel . registerMessage ( EditMerchNumberServerPacketHandler . class, EditMerchNumberPacket . class, i ++, Side . SERVER ) ;
        networkChannel . registerMessage ( EditMerchStringServerPacketHandler . class, EditMerchStringPacket . class, i ++, Side . SERVER ) ;
        
        //
        networkChannel . registerMessage ( FreezeObjectServerPacketHandler . class, FreezeObjectPacket . class, i ++, Side . SERVER ) ;

        networkChannel . registerMessage ( ManageResponseClientPacketHandler . class, ManageResponsePacket . class, i ++, Side . CLIENT ) ;

    }

	public static void sendToServerOpenShopInventoryPacket ( ) {

		networkChannel . sendToServer ( new InventoryShopPacket ( InventoryShopPacket . Type . OPEN_INV ) ) ;
		
	}
	
	public static void sendToServerCloseShopInventoryPacket ( ) {
		
		networkChannel . sendToServer ( new InventoryShopPacket ( InventoryShopPacket . Type . CLOSE_WITH_MERGE ) ) ;
		
	}
	
	public static void sendToServerCancelShopInventoryPacket ( ) {

		networkChannel . sendToServer ( new InventoryShopPacket ( InventoryShopPacket . Type . CLOSE_NO_MERGE ) ) ;
		
	}

	public static void sendToServerBuyPacket ( int shopId, int merch_id, int catId, int amountToBuy ) {

		networkChannel . sendToServer ( new BuyPacket ( shopId, merch_id, catId, amountToBuy ) ) ;

	}

	public static void sendToServerNeedShopCategoryPacket ( int shopId, int catId ) {
        
		networkChannel . sendToServer ( new NeedShopCategoryPacket ( shopId, catId ) ) ;
		
	}

	public static void sendToServerNeedUpdatePacket ( CodePacket . Code c ) {

		networkChannel . sendToServer ( new CodePacket ( c ) ) ;
		
	}

	public static void sendToAllMerchInfoPacket ( Merch info ) {
		
        networkChannel . sendToAll ( new MerchInfoPacket ( info ) ) ;

	}

	public static void sendToAllRemoveMerchPacket ( int shopId, int catId, int id ) {
	
	    networkChannel . sendToAll ( new RemoveMerchPacket ( shopId, catId, id ) ) ;

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

	public static void sendToManageResponsePacket ( EntityPlayerMP player, ResponseType type, ResponseCode code, ResponseStatus status ) {
		
        networkChannel . sendTo ( new ManageResponsePacket ( type, code, status ), player ) ;

	}
	
	public static void sendToMobSelectPacket ( EntityPlayerMP player, int s ) {

		networkChannel . sendTo ( new MobSelectPacket ( s ), player ) ;

	}

	public static void sendToServerCreateNewShopPacket ( String createNewShop ) {

        networkChannel . sendToServer ( new CreateNewShopPacket ( createNewShop ) ) ;

	}
	
	public static void sendToServerDeleteShopPacket ( int shopId ) {

        networkChannel . sendToServer ( new DeleteShopPacket ( shopId ) ) ;

	}
	
	public static void sendToServerRenameShopPacket ( int shopId, String name ) {

		networkChannel . sendToServer ( new RenameShopPacket ( shopId, name ) ) ;

	}
	
	public static void sendToServerFreezeShopPacket ( int shopId, String reason ) {

		networkChannel . sendToServer ( new FreezeObjectPacket ( shopId, reason, true ) ) ;
		
	}
	
	public static void sendToServerUnfreezeShopPacket ( int shopId ) {

		networkChannel . sendToServer ( new FreezeObjectPacket ( shopId, null, false ) ) ;
		
	}

	public static void sendToServerAddNewEntryPacket ( int shopId, int catId, int limit, int cost, String name ) {
		
		networkChannel . sendToServer ( new AddNewEntryPacket ( shopId, catId, limit, cost, name ) ) ;
		
	}

	public static void sendToServerUppendEntryPacket ( int shopId, int catId ) {
		
		networkChannel . sendToServer ( new AppendEntryPacket ( shopId, catId ) ) ;

	}
	
	public static void sendToServerMobSelectPacket ( int s ) {

		networkChannel . sendToServer ( new MobSelectPacket ( s ) ) ;

	}
	
	public static void sendToServerDeleteShopMerchPacket ( int shopId, int catId, int merchId ) {

        networkChannel . sendToServer ( new DeleteShopMerchPacket ( shopId, catId, merchId ) ) ;

	}
	
	public static void sendToServerFreezeAccountPacket ( String player, String reason ) {
		
		networkChannel . sendToServer ( new FreezeObjectPacket ( player, reason, true ) ) ;
		
	}
	
	public static void sendToServerUnfreezeAccountPacket ( String player ) {
		
		networkChannel . sendToServer ( new FreezeObjectPacket ( player, null, false ) ) ;
		
	}
	
	public static void sendToServerEditMerchNumberPacket ( int shopId, int catId, int merchId, EditMerchNumberPacket . Type type, int number ) {
		
		networkChannel . sendToServer ( new EditMerchNumberPacket ( shopId, catId, merchId, type, number ) ) ;
		
	}
	
	public static void sendToServerEditMerchStringPacket ( int shopId, int catId, int merchId, EditMerchStringPacket . Type type, String str ) {

		networkChannel . sendToServer ( new EditMerchStringPacket ( shopId, catId, merchId, type, str ) ) ;
		
	}
	
	public static void sendTo ( EntityPlayerMP player, IMessage packet ) {

		networkChannel . sendTo ( packet, player ) ;

	}

}

package ru.alastar.minedonate.rtnl.common;

import ru.alastar.minedonate.MineDonate;
import ru.alastar.minedonate.merch.Merch;
import ru.alastar.minedonate.merch.info.ItemInfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.item.ItemStack;

public class Account {

	public String id, name ;
	public List < String > permissions ;
	public ManageSession manageSession = new ManageSession ( ) ;
	
	public boolean freezShopCreate ;
	public String freezShopCreateFreezer ;
	public String freezShopCreateReason ;
	public int shopsCount ;
	
    public Map < String, Integer > moneys = new HashMap < > ( ) ;

	public Account ( String _id, String _name, List < String > _permissions, boolean _freezShopCreate, String _freezShopCreateFreezer, String _freezShopCreateReason, int _shopsCount ) {
		
		id = _id ;
		name = _name ;
		permissions = _permissions ;
		
		freezShopCreate = _freezShopCreate ;
		freezShopCreateFreezer = _freezShopCreateFreezer;
		freezShopCreateReason = _freezShopCreateReason ;
		shopsCount = _shopsCount ;
		
	}
	
	public boolean hasPermission ( String name ) {

		return permissions . contains ( name . toLowerCase ( ) ) ;
		
	}
	
	public boolean canFreezeShop ( String owner ) {

		return ( id . equalsIgnoreCase ( owner ) && hasPermission ( "freezeOwnedShop" ) ) || ( hasPermission ( "freezeOtherShop" ) && ! owner . equalsIgnoreCase ( "SERVER" ) ) || hasPermission ( "*" ) ;
		
	}
	
	public boolean canUnFreezeShop ( String owner ) {

		return ( id . equalsIgnoreCase ( owner ) && hasPermission ( "unFreezeOwnedShop" ) ) || ( hasPermission ( "unFreezeOtherShop" ) && ! owner . equalsIgnoreCase ( "SERVER" ) ) || hasPermission ( "*" ) ;
		
	}
	
	public boolean canDeleteShop ( String owner ) {
		
		return ( id . equalsIgnoreCase ( owner ) && hasPermission ( "removeOwnedShop" ) ) || ( hasPermission ( "removeOtherShop" ) && ! owner . equalsIgnoreCase ( "SERVER" ) ) || hasPermission ( "*" ) ;

	}
	
	public boolean canRenameShop ( String owner ) {
		
		return ( id . equalsIgnoreCase ( owner ) && hasPermission ( "renameOwnedShop" ) ) || ( hasPermission ( "renameOtherShop" ) && ! owner . equalsIgnoreCase ( "SERVER" ) ) || hasPermission ( "*" ) ;

	}
	
	public boolean canEditShop ( String owner ) {

		return ( id . equalsIgnoreCase ( owner ) && hasPermission ( "editOwnedShop" ) ) || ( hasPermission ( "editOtherShop" ) && ! owner . equalsIgnoreCase ( "SERVER" ) ) || hasPermission ( "*" ) ;

	}
	
	public boolean canUnlimitedItems ( ) {
		
		return hasPermission ( "unlimitedItems" ) || hasPermission ( "*" ) ;

	}
	
	public boolean canUnlimitedEntities ( ) {
		
		return hasPermission ( "unlimitedEntities" ) || hasPermission ( "*" ) ;

	}
	
	public boolean canCreateShop ( ) {
		
		return hasPermission ( "createShop" ) || hasPermission ( "*" ) ;

	}
	
	public boolean canFreezeOtherAccount ( ) {
		
		return hasPermission ( "freezeAccount" ) || hasPermission ( "*" ) ;

	}
	
	public boolean canUnFreezeOtherAccount ( ) {
		
		return hasPermission ( "unFreezeAccount" ) || hasPermission ( "*" ) ;

	}
	
	public boolean freezedShopCreate ( ) {
		
		return this . freezShopCreate ;
		
	}
	
	public boolean limitShopCreate ( ) {
		
		return MineDonate . cfg . maxUsersShopsCount <= this . shopsCount ;
		
	}
	
	public boolean canViewOtherFreezText ( ) {

		return hasPermission ( "canViewOtherFreezText" ) || hasPermission ( "*" ) ;
		
	}

	public boolean canAddMoney ( ) {
		
		return hasPermission ( "canAddMoney" ) || hasPermission ( "*" ) ;
		
	}

	public int getMoney ( String moneyType ) {
	
		return moneys . get ( moneyType ) ;
		
	}
	
	public void putMoney ( String moneyType, int money ) {
		
		moneys . put ( moneyType, money ) ; 

	}
	
	public int canAppendAnotherItemInShop ( Shop _s, int _catId ) {
		
		if ( manageSession . currentItemStack == null ) {
			
			return -1 ;
			
		}
		
		ItemInfo ii ;

		for ( Merch m : _s . cats [ _catId ] . m_Merch . values ( ) ) {
			
			ii = ( ItemInfo ) m ;

			if ( ii . limit >= 0 && ii . limit < ii . maxLimit && ItemStack . areItemStacksEqual ( ii . m_stack, manageSession . currentItemStack ) ) {
				
				return ii . merchId ;
				
			}
			
		}
		
		return -1 ;
		
	}
	
	@SideOnly(Side.CLIENT)
    public boolean canAdd ( int shopId, int catId ) {
        
    	return ( shopId == 0 ? canEditShop ( "SERVER" ) : true ) ;
    	
    }
    
}

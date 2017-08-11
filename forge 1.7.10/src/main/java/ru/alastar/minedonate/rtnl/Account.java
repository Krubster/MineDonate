package ru.alastar.minedonate.rtnl;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.item.ItemStack;
import ru.alastar.minedonate.MineDonate;

public class Account {

	public String name ;
	public List < String > permissions ;
	public ManageSession ms = new ManageSession ( ) ;
	
	public boolean allowShopCreate ;
	public String allowShopCreateBanner ;
	public String allowShopCreateReason ;
	public int shopsCount ;

	@SideOnly(Side.SERVER)
	public AdminSession adminSesson ;
	
	public Account ( String _name, List < String > _permissions, boolean _allowShopCreate, String _allowShopCreateBanner, String _allowShopCreateReason, int _shopsCount ) {
		
		name = _name ;
		permissions = _permissions ;
		
		allowShopCreate = _allowShopCreate ;
		allowShopCreateBanner = _allowShopCreateBanner;
		allowShopCreateReason = _allowShopCreateReason ;
		shopsCount = _shopsCount ;
		
	}
	
	public boolean hasPermission ( String name ) {

		return true;//permissions . contains ( name . toLowerCase ( ) ) ;
		
	}
	
	public boolean canFreezeShop ( String owner ) {

		return ( hasPermission ( "freezeOtherShop" ) && ! owner . equalsIgnoreCase ( "SERVER" ) ) || hasPermission ( "*" ) ;
		
	}
	
	public boolean canUnFreezeShop ( String owner ) {

		return ( hasPermission ( "unFreezeOtherShop" ) && ! owner . equalsIgnoreCase ( "SERVER" ) ) || hasPermission ( "*" ) ;
		
	}
	
	public boolean canDeleteShop ( String owner ) {
		
		return ( name . equalsIgnoreCase ( owner ) && hasPermission ( "removeOwnedShop" ) ) || ( hasPermission ( "removeOtherShop" ) && ! owner . equalsIgnoreCase ( "SERVER" ) ) || hasPermission ( "*" ) ;

	}
	
	public boolean canRenameShop ( String owner ) {
		
		return ( name . equalsIgnoreCase ( owner ) && hasPermission ( "renameOwnedShop" ) ) || ( hasPermission ( "renameOtherShop" ) && ! owner . equalsIgnoreCase ( "SERVER" ) ) || hasPermission ( "*" ) ;

	}
	
	public boolean canEditShop ( String owner ) {
		
		return ( name . equalsIgnoreCase ( owner ) && hasPermission ( "editOwnedShop" ) ) || ( hasPermission ( "editOtherShop" ) && ! owner . equalsIgnoreCase ( "SERVER" ) ) || hasPermission ( "*" ) ;

	}
	
	public boolean canCreateShop ( ) {
		
		return hasPermission ( "createShop" ) || hasPermission ( "*" ) ;

	}
	
	public boolean bannedShopCreate ( ) {
		
		return ! this . allowShopCreate ;
		
	}
	
	public boolean limitShopCreate ( ) {
		
		return MineDonate . cfg . maxShopsCount <= this . shopsCount ;
		
	}
	
 
	public boolean canViewOtherFreezText() {

		return hasPermission ( "canViewOtherFreezText" ) || hasPermission ( "*" ) ;
		
	}
	
	public void createAdminSession ( ) {
		
		this . adminSesson = new AdminSession ( ) ;
		
	}
	
	public class AdminSession {
		
	    public boolean pending = false ;
	    public String [ ] params ;

	    public AdminSession ( ) {

	    }
	    
	}

	public class ManageSession {
	
		public ItemStack currentItemStack ;
		
		public void setItemStack ( ItemStack _currentItemStack ) {
			
			currentItemStack = _currentItemStack ;
			
		}
		
	}
	
}

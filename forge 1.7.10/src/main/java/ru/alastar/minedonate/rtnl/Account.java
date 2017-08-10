package ru.alastar.minedonate.rtnl;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.item.ItemStack;

public class Account {

	public String name ;
	public List < String > permissions ;
	public ManageSession ms = new ManageSession ( ) ;
	
	@SideOnly(Side.SERVER)
	public AdminSession adminSesson ;
	
	public Account ( String _name, List < String > _permissions ) {
		
		name = _name ;
		permissions = _permissions ;
		
	}
	
	public boolean hasPermission ( String name ) {

		return true;//permissions . contains ( name . toLowerCase ( ) ) ;
		
	}
	
	public boolean canFreezeShop ( String owner ) {

		return ( hasPermission ( "freezeOtherShop" ) && ! owner . equalsIgnoreCase ( "SERVER" ) ) || hasPermission ( "*" ) ;
		
	}
	
	public boolean canDeleteShop ( String owner ) {
		
		return ( name . equalsIgnoreCase ( owner ) && hasPermission ( "removeOwnedShop" ) ) || ( hasPermission ( "removeOtherShop" ) && ! owner . equalsIgnoreCase ("SERVER" ) ) || hasPermission ( "*" ) ;

	}
	
	public boolean canRenameShop ( String owner ) {
		
		return ( name . equalsIgnoreCase ( owner ) && hasPermission ( "renameOwnedShop" ) ) || ( hasPermission ( "renameOtherShop" ) && ! owner . equalsIgnoreCase ("SERVER" ) ) || hasPermission ( "*" ) ;

	}
	
	public boolean canEditShop ( String owner ) {
		
		return ( name . equalsIgnoreCase ( owner ) && hasPermission ( "editOwnedShop" ) ) || ( hasPermission ( "editOtherShop" ) && ! owner . equalsIgnoreCase ( "SERVER" ) ) || hasPermission ( "*" ) ;

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

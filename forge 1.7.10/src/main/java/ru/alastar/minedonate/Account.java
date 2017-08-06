package ru.alastar.minedonate;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class Account {

	public String name ;
	public List < String > permissions ;
	
	public Account ( String _name, List < String > _permissions ) {
		
		name = _name ;
		permissions = _permissions ;
		
	}
	
	public boolean hasPermission ( String name ) {
	
		return permissions . contains ( name . toLowerCase ( ) ) ;
		
	}
	
	public boolean canFreezeShop ( String owner ) {
		
		return hasPermission ( "freezeOtherShop" ) || hasPermission ( "*" ) ;
		
	}
	
	public boolean canDeleteShop ( String owner ) {
		
		return ( name . equalsIgnoreCase ( owner ) && hasPermission ( "removeOwnedShop" ) ) || hasPermission ( "removeOtherShop" ) || hasPermission ( "*" ) ;

	}
	
	public boolean canRenameShop ( String owner ) {
		
		return ( name . equalsIgnoreCase ( owner ) && hasPermission ( "renameOwnedShop" ) ) || hasPermission ( "renameOtherShop" ) || hasPermission ( "*" ) ;

	}
	
	public boolean canEditShop ( String owner ) {
		
		return ( name . equalsIgnoreCase ( owner ) && hasPermission ( "editOwnedShop" ) ) || hasPermission ( "editOtherShop" ) || hasPermission ( "*" ) ;

	}
	
}

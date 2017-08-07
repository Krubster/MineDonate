package ru.alastar.minedonate;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

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
	
}

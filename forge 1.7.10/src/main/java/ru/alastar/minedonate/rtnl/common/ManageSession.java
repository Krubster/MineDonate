package ru.alastar.minedonate.rtnl.common;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;

public class ManageSession {
	
	public ItemStack currentItemStack ;
    public boolean mobSelect = false ;
	public Entity currentMob ;

	public void setItemStack ( ItemStack _currentItemStack ) {
		
		currentItemStack = _currentItemStack ;
		
	}
	
}
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
	
	public void setMobSelect ( boolean _mobSelect ) {
		
		mobSelect = _mobSelect ;
		
	}
	
	public void setCurrentMob ( Entity _currentMob ) {
		
		currentMob = _currentMob ;
		
	}
	
}
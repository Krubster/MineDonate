package ru.alastar.minedonate.plugin.money;

import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;

import net.milkbowl.vault.Vault;
import net.milkbowl.vault.economy.Economy;

public class VaultBukkitPlugin extends MoneyPlugin {

	boolean loaded = false ;
	Economy economy ;
	
	public VaultBukkitPlugin ( ) { }
	
	@Override
	public void load ( Map < String, Object > prop ) {
		
		if ( ! loaded ) {
		
			Object vaultPlugin = ( Vault ) Bukkit . getPluginManager ( ) . getPlugin ( "Vault" ) ;
			
			if ( vaultPlugin == null ) {
				
				System . err . println ( "[ERROR] VaultPlugin not found!" ) ;
					
			} else {
				
				economy = Bukkit . getServer ( ) . getServicesManager ( ) . load ( Economy . class ) ;
				
				loaded = true ;

			}
						
		}
		
	}
	
	@Override
	public int getBalanceForUser ( UUID player ) {
		
		return ( int ) economy . getBalance ( Bukkit . getOfflinePlayer ( player ) ) ;
		
	}
	
	@Override
	public void withdrawMoney ( UUID player, int amount ) {
		
		economy . withdrawPlayer ( Bukkit . getOfflinePlayer ( player ), amount ) ;
		
	}
	
	@Override
	public void depositPlayer ( UUID player, int amount ) {
		
		economy . depositPlayer ( Bukkit . getOfflinePlayer ( player ), amount ) ;
		
	}
	
	@Override
	public void setMoney ( UUID player, int money ) {

		( new UnsupportedOperationException ( ) ) . printStackTrace ( ) ;
		
		// economy . bankDeposit ( Bukkit . getOfflinePlayer ( player ) . getName ( ), money ) ;
		
	}
	
}

package ru.log_inil.mc.minedonate.gui.context;

import java.util.ArrayList;
import java.util.List;

import ru.log_inil.mc.minedonate.gui.MCGuiAccessable;

public class ContextMenuManager {

	static List < ContextMenu > menus = new ArrayList < > ( ) ;
	static boolean currentDrawMenu = false ;
	static ContextMenu currentMenu ;
	
	static MCGuiAccessable gs ;
	
	public static void draw ( MCGuiAccessable _gs, int mouseX, int mouseY ) {
		
		gs = _gs ;
		
		if ( currentDrawMenu && currentMenu != null ) {
			
			currentMenu . draw ( _gs, mouseX, mouseY ) ;
			
		}
		
	}

	public static void click ( MCGuiAccessable gs, int x, int y, int t ) {

		if ( t == 1 ) {
			
			// RMB
			currentDrawMenu = false ;
			currentMenu = null ;
			
			for ( ContextMenu cmm : menus ) {

				if ( cmm . coordContains ( x, y ) ) {

					currentDrawMenu = true ;
					
					cmm . drawPosX = x ;
					cmm . drawPosY = y ;
					
					cmm . updateInteractArea ( gs, cmm . activateCoordX, cmm . activateCoordY ) ;
					
					currentMenu = cmm ;
					
				}
				
			}
			
		} else if ( t == 0 ) {
			
			// LMB
			
			if ( currentDrawMenu ) {
				
				if ( currentMenu . coordContainsInteract ( x, y ) ) {
					
					ContextElement cme = currentMenu . getLine ( x, y ) ;
					
					if ( cme != null ) {
						
						cme . onClick ( currentMenu ) ;
						
					} else {
						
						currentDrawMenu = false ;
						currentMenu = null ;
						
					}
					
				} else {
					
					currentDrawMenu = false ;
					currentMenu = null ;
					
				}
				
			}
			
		} else {
			
			// AMB
			currentDrawMenu = false ;
			currentMenu = null ;
			
		}
		
	}
	
	public static void addNewMenu ( ContextMenu cmm ) {
		
		menus . add ( cmm ) ;
		
	}

	public static void removeMenu(ContextMenu cmm) {

		menus . remove ( cmm ) ;
		
	}
		
}

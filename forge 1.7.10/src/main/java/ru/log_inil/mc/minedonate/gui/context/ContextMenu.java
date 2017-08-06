package ru.log_inil.mc.minedonate.gui.context;

import java.util.List;

import ru.log_inil.mc.minedonate.gui.MCGuiAccessable;

public class ContextMenu {
	
	List < ContextElement > l ;
	
	int activateCoordX ;
	int activateCoordY ;
	int activateCoordXEnd ;
	int activateCoordYEnd ;
	
	int drawPosX ;
	int drawPosY ;
	
	int interactWidth ;
	int interactHeight ;
	
	public ContextMenu ( int w, int h, List < ContextElement > _l ) {
		
		interactWidth = w ;
		interactHeight = h ;
		l = _l ;
		
	}
	
	int i = 0 ;
	
	static int lineHeight = 10 ;
	ContextDrawType tmpDT ;
	int tmpHeight ;
	int maxWidth ;
	
	public void draw ( MCGuiAccessable g, int mouseX, int mouseY ) {
		
		tmpHeight = 0 ;
		
		for ( ContextElement cme : l ) {

			tmpDT = ContextDrawType . NORMAL ; 

			if ( drawPosX <= mouseX && mouseX <= drawPosX + cme . getLineWidth ( g ) && mouseY >= drawPosY + tmpHeight && mouseY <= drawPosY + tmpHeight  + cme.lineHeight   ) {
				
				tmpDT = ContextDrawType . HOVERED ; 

			}
			
			maxWidth = Math . max ( maxWidth, cme . getLineWidth ( g ) ) ;
			
			cme . draw ( g, i ++, l . size ( ), drawPosX, drawPosY, tmpDT ) ; 
			
			tmpHeight += cme . lineHeight ;

		}
		
		i = 0 ;
		
	}
	
	int tmp ;
	
	public ContextElement getLine ( int x, int y ) {
		
		if ( x < drawPosX || drawPosX + maxWidth < x ) { return null ; }
		
		tmp = ( drawPosY - y ) / lineHeight ;
		
		if ( tmp < 0 ) { tmp = Math.abs(tmp); }
		
		return l . size ( ) > tmp && tmp >= 0 ? l . get ( tmp ) : null ;
		
	}
	
	public void updateInteractArea ( int x, int y ) {
		
		activateCoordX = x ;
		activateCoordY = y ;
		
		activateCoordXEnd = x + interactWidth ;
		activateCoordYEnd = y + interactHeight ;
		
	}
	
	public void updateInteractAreaSizes ( int w, int h ) {
		
		interactWidth = w ;
		interactHeight = h ;
		
		updateInteractArea ( activateCoordX, activateCoordY ) ;
		
	}

	public boolean coordContains ( int x, int y ) {
		
		// System.err.println(x + "<=" + activateCoordXEnd);

		return ( activateCoordX <= x && x <= activateCoordXEnd ) && ( activateCoordY <= y && activateCoordYEnd >= y ) ;
		
	}
			
}
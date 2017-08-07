package ru.log_inil.mc.minedonate.gui.context;

import java.util.List;

import net.minecraft.client.gui.Gui;
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
	
	int activateCoordXEndInteract ;
	int activateCoordYEndInteract ;
	
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
	int maxHeight ;

	public void draw ( MCGuiAccessable g, int mouseX, int mouseY ) {
		
		Gui . drawRect ( drawPosX, drawPosY - 2, drawPosX + maxWidth + 7, drawPosY + tmpHeight + 2, 1258291200 ) ;

		tmpHeight = 0 ;
		
		for ( ContextElement cme : l ) {

			tmpDT = ContextDrawType . NORMAL ; 

			if ( drawPosX < mouseX && mouseX < drawPosX + cme . getLineWidth ( g ) && mouseY > drawPosY + tmpHeight && mouseY < drawPosY + tmpHeight + cme.lineHeight ) {
				
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
		
		// check out of bounds all menu
		if ( x < drawPosX || drawPosX + maxWidth < x ) { return null ; }
		if ( y < drawPosY || drawPosY + maxHeight < y ) { return null ; }

		tmp = ( drawPosY - y ) / ( lineHeight - 2 ) ;

		if ( tmp < 0 ) { tmp = Math . abs ( tmp ) ; }
		
		return l . size ( ) > tmp && tmp >= 0 ? l . get ( tmp ) : null ;
		
	}
	
	public void calcMaxs ( MCGuiAccessable g ) {
	
		maxHeight = 0 ;
		
		for ( ContextElement cme : l ) {

			maxWidth = Math . max ( maxWidth, cme . getLineWidth ( g ) ) ;
			maxHeight += cme . lineHeight ;

		}
		
	}
	
	public void updateInteractArea ( MCGuiAccessable g, int x, int y ) {
		// System.err.println(activateCoordX + "<=" + x);

		activateCoordX = x ;
		activateCoordY = y ;
		
		activateCoordXEnd = x + interactWidth ;
		activateCoordYEnd = y + interactHeight ;
		
		calcMaxs ( g ) ;
		
		activateCoordXEndInteract = drawPosX + maxWidth ;
		activateCoordYEndInteract = drawPosY + maxHeight ;

	}
	
	public void updateInteractAreaSizes ( MCGuiAccessable g, int w, int h ) {
		
		interactWidth = w ;
		interactHeight = h ;
		
		updateInteractArea ( g, activateCoordX, activateCoordY ) ;
		
	}

	// check interach triger object area
	public boolean coordContains ( int x, int y ) {

		return ( activateCoordX <= x && x <= activateCoordXEnd ) && ( activateCoordY <= y && activateCoordYEnd >= y ) ;
		
	}
	
	// check interact menu area
	public boolean coordContainsInteract ( int x, int y ) {

		return ( activateCoordX < x && x < activateCoordXEndInteract ) && ( activateCoordY < y && activateCoordYEndInteract > y ) ;
		
	}
			
}
package ru.log_inil.mc.minedonate.gui;

import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.Tessellator;
import ru.alastar.minedonate.gui.ShopGUI;

public class GuiItemsScrollArea extends GuiScrollingList {
	
    public ShopGUI parent;
    public List<GuiAbstractItemEntry> entrs;
    static int itemHeight = 30 ;
    
    public GuiItemsScrollArea(ScaledResolution sr, ShopGUI parent, List<GuiAbstractItemEntry> _entr, int listWidth)
    {
        super ( Minecraft . getMinecraft ( ), sr . getScaledWidth ( ) - 60, 100,  ( int ) ( sr . getScaledHeight ( ) * 0.1) + 15 + 24, (int) ( ( sr . getScaledHeight ( ) ) - ( sr . getScaledHeight ( ) * 0.1 ) ) - 5, 30, itemHeight ) ;
        this.parent=parent;
        this.entrs=_entr;
    }

    @Override
    protected int getSize() {
    	
        return entrs.size();
        
    }

    @Override
    protected void elementClicked(int var1, boolean var2) { }

    @Override
    protected boolean isSelected(int var1) {
    	
    	return false ;
    	
    }

    @Override
    protected void drawBackground() { }

    @Override
    protected int getContentHeight() {
    	
        return ( this . getSize ( ) ) * itemHeight ;
        
    }

    @Override
    protected void drawSlot(int listIndex, int var2, int var3, int var4, int mouseX, int mouseY, Tessellator var5) {

    	entrs . get ( listIndex ) . draw ( this, var4, var3, var2, mouseX, mouseY, var5, listIndex, entrs . size ( ) ) ;
  
    }
    
    
    @Override
    protected void unDrawSlot ( int listIndex ) {
    	
    	entrs . get ( listIndex ) . unDraw ( ) ;
  
    }
    
    public FontRenderer getFontRenderer ( ) {
    	
    	return this . parent . getFontRenderer ( ) ;
    	
    }

}
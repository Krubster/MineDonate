package ru.alastar.minedonate.gui;

import net.minecraft.client.gui.GuiButton;
import ru.log_inil.mc.minedonate.gui.DrawType;
import ru.log_inil.mc.minedonate.gui.GuiScrollingList;

/**
 * Created by Alastar on 19.07.2017.
 */
public interface ShopCategory {

    boolean getEnabled();

    int getSourceCount(int shopId);

    String getName();

    void draw(ShopGUI relative, int page, int mouseX, int mouseY, float partialTicks, DrawType dt);
    void undraw ( ) ;
    
    void updateButtons(ShopGUI relative, int page);

    int elements_per_page();

    void actionPerformed(GuiButton button);
    
    int getButtonWidth ( ) ;
    String getButtonText ( ) ;

    int getRowCount ( ) ;
    void setRowCount ( int i ) ;
    
    int getColCount ( ) ;
    void setColCount ( int i ) ;

    int getItemWidth ( ) ;
    int getItemHeight ( ) ;

	void init(ShopGUI shopGUI);

	void initGui();

	void search(String text);

	GuiScrollingList getScrollList();
    String getCatMoneyType ( ) ;

}

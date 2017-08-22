package ru.alastar.minedonate.merch.info;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import ru.alastar.minedonate.merch.Merch;
import ru.alastar.minedonate.plugin.PluginHelper;
import ru.alastar.minedonate.rtnl.Utils;

/**
 * Created by Alastar on 20.07.2017.
 */
public class RegionInfo extends Merch {

    public String world_name;
    public String name;

    public RegionInfo(int _shopId, int _catId, int mid, int cost, String name, String world_name) {
    	super(_shopId, _catId, mid, 0);
        this.cost = cost;
        this.name = name;
        this.world_name = world_name;
    }


    @Override
    public boolean canBuy(EntityPlayerMP serverPlayer, int amount) {
    	
    	if ( PluginHelper . wgMgr . checkRegionMaxOut ( this . world_name, serverPlayer . getDisplayName ( ) ) ) {
 		 
    		serverPlayer . addChatMessage ( new ChatComponentText ( "You can't have more regions!" ) ) ;

    		return false ;
    		
    	} else {
    				   
    		return true;
               
    	}

    }

    @Override
    public int getAmountToBuy() {
        return 1;
    }

    public RegionInfo() {
    	super();
    }

    @Override
    public int getCategory() {
        return 2;
    }

    @Override
    public void read(ByteBuf buf) {
    	super.read(buf);
        cost = buf.readInt();
        
        try {
			
        	name = Utils . netReadString ( buf ) ;
        	world_name = Utils . netReadString ( buf ) ;
			
		} catch ( Exception ex ) {
			
			ex . printStackTrace ( ) ;
			
		}
        
    }

    @Override
    public void write(ByteBuf buf) {
    	super.write(buf);
        buf.writeInt(cost);
        
        try {
			
        	Utils . netWriteString ( buf, name ) ;
        	Utils . netWriteString ( buf, world_name ) ;

		} catch ( Exception ex ) {
			
			ex . printStackTrace ( ) ;
			
		}
        
    }

    @Override
    public String getBoughtMessage() {
        return " bought region " + name + "=" + world_name;
    }
    
	@SideOnly(Side.CLIENT)
	@Override
	public String getSearchValue ( ) {
		
		return EnumChatFormatting . getTextWithoutFormattingCodes ( name + world_name ) ;
		
	}
	
}

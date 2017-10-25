package ru.alastar.minedonate.merch.info;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import ru.alastar.minedonate.Utils;
import ru.alastar.minedonate.merch.Merch;
import ru.alastar.minedonate.network.manage.packets.EditMerchFieldPacket;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by Alastar on 18.07.2017.
 */
public class ItemInfo extends Merch {

    public ItemStack m_stack;

    public int modified = 1; // multiplied count
    public int maxLimit = 10;

    @SideOnly(Side.SERVER)
    public String m_cmds;

    public NBTTagCompound stack_data;//stack data
    public String name;//name
    public int limit; //limited?


    public ItemInfo() {
    	
    	super();
    	
    }

    @Override
    public boolean canBuy ( EntityPlayerMP serverPlayer, int amount ) { 
    	
    	return ( limit != -1 ? limit > 0 && amount <= limit : amount > 0 ) && super . canBuy ( serverPlayer, amount ) ;
    	
    }

    @Override
    public int getAmountToBuy() {
        return modified;
    }

    public ItemInfo(int _shopId, int _catId, int mid, int _rating, int cos, String n, int lim, java.sql.Blob data) {
    	super(_shopId, _catId, mid, _rating);
        this.cost = cos;
        this.name = n;
        this.limit = lim;
        ByteBuf buf = Unpooled.buffer();
        try {
            buf.writeBytes(data.getBinaryStream(), (int)data.length());
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        stack_data = ByteBufUtils.readTag(buf);
        m_stack = ItemStack.loadItemStackFromNBT(stack_data);

    }
    public ItemInfo(int _shopId, int _catId, int mid, int _rating, int cos, String n, int lim, ItemStack data) {
    	super(_shopId, _catId, mid, _rating);
    	
        this.cost = cos;
        this.name = n;
        this.limit = lim;
        this.stack_data = new NBTTagCompound ( ) ;
        data.writeToNBT(stack_data);
        m_stack = data;

    }
    
    @Override
	public void updateField ( EditMerchFieldPacket . FieldType _type, EditMerchFieldPacket . FieldName _name, Object _data ) {	
		
    	super . updateField ( _type, _name, _data ) ;

		switch ( _type ) { 
		
			case INTEGER:
			
				switch ( _name ) { 
				
					case LIMIT:
						
						this . limit = (Integer) _data;

					break ;
				
					case COST:
						
						this . cost = (Integer) _data;

					break ;
					
					default: break ;
					
				}
				
			break ;
			
			case STRING:
			
				switch ( _name ) { 
				
					case NAME:
						
						this . name = (String) _data;
	
					break ;
					
					default: break ;
					
				}
				
			break ;
			
			default: break ;
			
		}

	}
    
    /*
    @Override
	public void updateNumber ( EditMerchNumberPacket . Type type, int number ) {	
		
    	super . updateNumber ( type, number ) ;

		switch ( type ) {
		
			case LIMIT :
				
				 limit = number ;
				
			break;
			
			case COST :
				
				cost = number ;
			
			break ;
			
			default :
			break ;
			
		}
			
	}
    
    @Override
	public void updateString ( EditMerchFieldPacket . Type type, String str ) {	
		
    	super . updateString ( type, str ) ;
    	
		switch ( type ) {
		
			case NAME :
				
				 name = str ;
				
			break;
				
			default :
			break ;
			
		}
			
	}*/
    
    @Override
    public String getBoughtMessage() {
        return "bought item - " + name + "=" + stack_data.getInteger("Id") + "=" + stack_data.getInteger("Count");
    }
    
    @Override
    public int getCategory() {
        return 0;
    }

    @Override
    public void read(ByteBuf buf) {
    	
    	super.read(buf);
    	
        this.cost = buf.readInt();
        
        try {
			
        	name = Utils . netReadString ( buf ) ;
			
		} catch ( Exception ex ) {
			
			ex . printStackTrace ( ) ;
			
		}
        
        limit = buf.readInt();
        stack_data = ByteBufUtils.readTag(buf);
        m_stack = ItemStack.loadItemStackFromNBT(stack_data);
        
        if ( name == null || name . isEmpty ( ) ) {
        	
        	name = m_stack . getDisplayName ( ) ;
        	
        }
        
        if ( limit < 0 ) {
        	
        	rating = -1 ;
        	
        }
        
    }
	
    @Override
    public void write(ByteBuf buf) {
    	
    	super.write(buf);
    	
        buf.writeInt(cost);
        
        try {
			
        	Utils . netWriteString ( buf, name ) ;
			
		} catch ( Exception ex ) {
			
			ex . printStackTrace ( ) ;
			
		}
        
        buf.writeInt(limit);
        ByteBufUtils.writeTag(buf, stack_data);
    }

	@SideOnly(Side.CLIENT)
	@Override
	public String getSearchValue ( ) {
		
		return EnumChatFormatting . getTextWithoutFormattingCodes ( name ) +  ( m_stack != null ? EnumChatFormatting . getTextWithoutFormattingCodes ( m_stack . getDisplayName ( ) ) : "" ) ;
		
	}
	
}

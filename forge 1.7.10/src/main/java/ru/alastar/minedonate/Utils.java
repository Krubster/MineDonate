package ru.alastar.minedonate;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

import java.awt.*;
import java.io.UnsupportedEncodingException;

public class Utils {

    public static String netReadString ( ByteBuf buf ) throws UnsupportedEncodingException {
    	
        return new String ( buf . readBytes ( buf . readInt ( ) ) . array ( ), "UTF-8" ) ;
        
    }
    
    public static void netWriteString ( ByteBuf buf, String str ) throws UnsupportedEncodingException {
    	
        buf . writeInt ( str . getBytes ( "UTF-8" ) . length ) ;
        buf . writeBytes ( str . getBytes ( "UTF-8" ) ) ;
        
    }
     
    public static void sendModMessage(EntityPlayerMP player, String msg) {
    	
        player.addChatMessage(new ChatComponentText(EnumChatFormatting.AQUA + " [MineDonate] " + EnumChatFormatting.RESET + msg));
        
    }
    
	public static int rgbaToInt ( Color c ) {
		
    	int r = c . getRed ( ) & 0xFF ;
		int g = c . getGreen ( ) & 0xFF ;
		int b = c . getBlue ( ) & 0xFF ;
		int a = c . getAlpha ( ) & 0xFF ;

		return ( r << 16 ) + ( g << 8 ) + ( b ) + ( a << 24 ) ;
		
	}

	public static boolean classExists ( String name ) {
		
		try { 
		
			Class . forName ( name ) ;
			
			return true ;
			
		} catch ( Exception ex ) { }
		
		return false ;
		
	}
	
}

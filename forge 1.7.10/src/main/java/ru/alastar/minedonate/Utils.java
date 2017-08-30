package ru.alastar.minedonate;

import io.netty.buffer.ByteBuf;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

import java.awt.*;
import java.io.File;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.UUID;

import com.mojang.authlib.GameProfile;


public class Utils {

    public static String netReadString ( ByteBuf buf ) throws UnsupportedEncodingException {
    	
        return new String ( buf . readBytes ( buf . readInt ( ) ) . array ( ), "UTF-8" ) ;
        
    }
    
    public static void netWriteString ( ByteBuf buf, String str ) throws UnsupportedEncodingException {
    	
        buf . writeInt ( str . getBytes ( "UTF-8" ) . length ) ;
        buf . writeBytes ( str . getBytes ( "UTF-8" ) ) ;
        
    }
     
    public static String getNameFromUUID ( UUID id ) {
      
    	GameProfile profile = MinecraftServer . getServer ( ) . func_152358_ax ( ) . func_152652_a ( id ) ;
      
        if ( profile != null ) {
        
        	return profile . getName ( ) ;
      
        } else {
        
        	MineDonate . logError ( "Null profile, for id[" + id + "]!" ) ;
        
        }
        
        return "";
        
    }

    public static UUID getUUIDFromName ( String name ) {
      
    	GameProfile profile = MinecraftServer . getServer ( ) . func_152358_ax ( ) . func_152655_a ( name ) ;
       
        if (profile != null) {
        
        	return profile . getId ( ) ;
        
        } else {
        
        	MineDonate . logError ( "Null profile for name[" + name + "]!" ) ;
        
        }
        
        return null ;
        
    }
    
    public static UUID getUUIDFromPlayer ( EntityPlayerMP serverPlayer ) {
    	
        GameProfile profile = MinecraftServer . getServer ( ) . func_152358_ax ( ) . func_152655_a ( serverPlayer . getDisplayName ( ) ) ;
       
        if ( profile != null ) {
        	
            return profile . getId ( ) ;
            
        } else {
        	
        	MineDonate . logError ( "Null profile, for player[" + serverPlayer + "]!" ) ;
            
        }
        
        return null ;
        
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

	public static boolean classExists ( String name, net . minecraft . launchwrapper . LaunchClassLoader cl ) {
		
		try { 

			return cl . findResource ( name . replace ( ".", "/" ) . concat ( ".class" ) ) != null ;
			
		} catch ( Throwable tw ) { tw . printStackTrace ( ) ; }
		
		return false ;
		
	}
	
    public static boolean loadLibs ( String [ ] [ ] _args, net . minecraft . launchwrapper . LaunchClassLoader _cl ) {
    	
    	for ( String [ ] args : _args ) {

    		if ( ! loadLib ( args, _cl ) ) {
    			
    			return false ;
    			
    		}
    		
    	}
	
    	return true ;
    	
	}
    
    public static boolean loadLib ( String [ ] args, net . minecraft . launchwrapper . LaunchClassLoader cl ) {

    	if ( ! Utils . classExists ( args [ 0 ], cl ) ) {

        	try {

        		MineDonate . logInfo ( "Try load library[" + args [ 1 ] + "] ..." ) ;

        		URL url ;
        		
        		InputStream is = Utils . class . getResourceAsStream ( args [ 1 ] ) ;
        		
        		if ( is == null ) {
        			
        			String p = Utils . class . getProtectionDomain ( ) . getCodeSource ( ) . getLocation ( ) . getPath ( ) ;
        			      			
        			p = p . split ( "!" ) [ 0 ] ;
        			p = p . substring ( p . indexOf ( ":/" ) + 2 ) ;

        			p = ( new File ( p ) . getParentFile ( ) . getAbsolutePath ( ) + File . separator + "libs" + File . separator + args [ 1 ]  ) ;

        			url = new File ( p ) . toURI ( ) . toURL ( ) ;

        		} else {
        			
        			url = Utils . class . getResource ( args [ 1 ] ) ;
        			
        			is . close ( ) ;
        			
        		}
        		        		
        		cl . addURL ( url ) ;

        	} catch ( Exception ex ) {
        		
        		MineDonate . logError ( "Error load library[" + args [ 1 ] + "]!" ) ;

        		ex . printStackTrace( ) ;
        		
        	}
        	        	
            if ( ! Utils . classExists ( args [ 0 ], cl ) ) {

            	MineDonate . logError ( "Class[" + args [ 0 ] + "] not found!" ) ;
	        		        		        	
        	} else {
        		
	        	return true ; 

        	}
        		
        	return false ;

        } else {
        	
        	return true ; 

        }
    	    	
    }
	
}

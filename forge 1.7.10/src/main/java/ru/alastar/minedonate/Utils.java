package ru.alastar.minedonate;

import java.io.UnsupportedEncodingException;

import io.netty.buffer.ByteBuf;

public class Utils {

    public static String netReadString ( ByteBuf buf ) throws UnsupportedEncodingException {
    	
        return new String ( buf . readBytes ( buf . readInt ( ) ) . array ( ), "UTF-8" ) ;
        
    }
     
    
    public static void netWriteString ( ByteBuf buf, String str ) throws UnsupportedEncodingException {
    	
        buf . writeInt ( str . getBytes ( "UTF-8" ) . length ) ;
        buf . writeBytes ( str . getBytes ( "UTF-8" ) ) ;
        
    }
    
}

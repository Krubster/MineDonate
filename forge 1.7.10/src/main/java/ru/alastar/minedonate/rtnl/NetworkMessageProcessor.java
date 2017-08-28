package ru.alastar.minedonate.rtnl;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import ru.alastar.minedonate.MineDonate;
import ru.alastar.minedonate.network.packets.CodePacket;

public class NetworkMessageProcessor extends Thread {

	static ExecutorService executor = Executors . newFixedThreadPool ( 4 ) ;

	public static void processTask ( final INetworkTask < IMessage, ? > task, final IMessage message, final MessageContext ctx ) {
		
		final String k = ctx . getServerHandler ( ) . playerEntity . getGameProfile ( ) . getId ( ) . toString ( ) ;
		
		if ( checkTaskLimit ( k ) ) {
			
    		ModNetwork . sendTo ( ctx . getServerHandler ( ) . playerEntity, new CodePacket ( CodePacket . Code . SERVER_ERROR_WAIT_OTHER_TASK ) ) ;
    		
    		return ;
    				
		}
		
		executor . submit ( new Runnable ( ) {
			
		    public void run() {
		    
		    	upTaskLimit ( k ) ;
		    	
		    	IMessage msg = task . onMessageProcess ( message, ctx ) ;
		    	
		    	downTaskLimit ( k ) ;

		    	if ( msg != null ) {
		    	
		    		ModNetwork . sendTo ( ctx . getServerHandler ( ) . playerEntity, msg ) ;
		    	
		    	}
		    			    	
		    }
		    
		} ) ;
		
	}

	
	static ConcurrentMap < String, Integer > stat = new ConcurrentHashMap < > ( ) ;
	
	public static int getTaskCount ( String k ) {
		
		if ( stat . containsKey ( k ) ) {
			
			return stat . get ( k ) ;
			
		}
		
		return 0 ;
		
	}
	
	public static boolean checkTaskLimit ( String k ) {
		
		return getTaskCount ( k ) > MineDonate . cfg . packetsMaxLimit ;
		
	}
	
	public static void upTaskLimit ( String k ) {
	
		stat . put ( k, getTaskCount ( k ) + 1 ) ;
		
	}
	
	public static void downTaskLimit ( String k ) {
		
		stat . put ( k, getTaskCount ( k ) - 1 ) ;

	}

}

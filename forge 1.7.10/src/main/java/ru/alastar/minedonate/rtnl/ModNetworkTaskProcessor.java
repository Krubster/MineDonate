package ru.alastar.minedonate.rtnl;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

import ru.alastar.minedonate.MineDonate;
import ru.alastar.minedonate.network.INetworkTask;
import ru.alastar.minedonate.network.packets.CodePacket;

/**
 * Лимитор и ?прокси?-процессинг всех сообщений адресованых серверу
 *
 */
public class ModNetworkTaskProcessor {

	static ExecutorService executor = Executors . newCachedThreadPool ( ) ;

	public static void processTask ( final INetworkTask < IMessage, ? > task, final IMessage message, final MessageContext ctx ) {
		
		if ( ! MineDonate . m_Enabled ) {
			
			return ; 
				
		}
		
		final String k = ctx . getServerHandler ( ) . playerEntity . getGameProfile ( ) . getId ( ) . toString ( ) ;

		if ( checkTaskLimit ( k ) ) {
			
    		ModNetworkRegistry . sendTo ( ctx . getServerHandler ( ) . playerEntity, new CodePacket ( CodePacket . Code . SERVER_ERROR_WAIT_OTHER_TASK ) ) ;
    		
    		return ;
    				
		}
    	
		executor . submit ( new Runnable ( ) {
			
		    public void run ( ) {	
					
				IMessage msg ;
		    		
			    upTaskLimit ( k ) ;
			    	
			   	msg = task . onMessageProcess ( message, ctx ) ;
    	
			    downTaskLimit ( k ) ;   
		    	
		    	if ( msg != null ) {
			    	
		    		ModNetworkRegistry . sendTo ( ctx . getServerHandler ( ) . playerEntity, msg ) ;
		    	
		    	}
		    	
		    }
		    
		} ) ;
				
	}

	final static Map < String, Integer > stat = Collections . synchronizedMap ( new HashMap < String, Integer> ( ) ) ;// new ConcurrentHashMap < > ( ) ;
	
	public static Integer getTaskCount ( String k ) {
		
		if ( stat . containsKey ( k ) ) {
			
			return stat . get ( k ) ;
			
		}
		
		stat . put ( k, 0 ) ;
		
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

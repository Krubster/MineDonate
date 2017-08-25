package ru.alastar.minedonate.rtnl;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class NetworkMessageProcessor extends Thread {

	static ExecutorService executor = Executors.newSingleThreadExecutor();

	public static void processTask ( final INetworkTask < IMessage, ? > task, final IMessage message, final MessageContext ctx ) {
		
		executor . submit ( new Runnable ( ) {
			
		    public void run() {
		    
		    	IMessage msg = task . onMessageProcess ( message, ctx ) ;
		    	
		    	if ( msg != null ) {
		    	
		    		ModNetwork . sendTo ( ctx . getServerHandler ( ) . playerEntity, msg ) ;
		    	
		    	}
		    	
		    }
		    
		} ) ;
		
	}
	

}

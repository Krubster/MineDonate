package ru.alastar.minedonate.plugin.server;

public interface IServerDetector {

	boolean detect ( ) throws Throwable ;
	String getServerPluginLoaderClassName ( ) ;
	
}

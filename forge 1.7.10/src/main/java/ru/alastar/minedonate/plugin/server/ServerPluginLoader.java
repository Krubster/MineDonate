package ru.alastar.minedonate.plugin.server;

public abstract class ServerPluginLoader {

	public void init ( ) throws Throwable { }
	public abstract boolean pluginExists ( String name ) throws Throwable ;
	public abstract Class < ? > defineClassFromBytes ( byte [ ] clazz, boolean loadClass ) throws Throwable ;
	
}

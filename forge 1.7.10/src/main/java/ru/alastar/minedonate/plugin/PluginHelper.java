package ru.alastar.minedonate.plugin;

import com.google.common.io.ByteStreams;

import ru.alastar.minedonate.MineDonate;
import ru.alastar.minedonate.plugin.server.*;
import ru.log_inil.mc.minedonate.localData.DataOfAccessorPlugin;

import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Загрузка и получение плагинов доступа к плагинам сервера (т.е. костыли)
 * */
public class PluginHelper {
	
	public static Map < String, AccessorPlugin > accessorPlugins = new HashMap < > ( ) ;
	
	public static ServerPluginLoader serverPL ;
		
	public static void loadPlugins ( ) throws Throwable {
		
		MineDonate . logInfo ( "Detect server plugin loader..." ) ;
			
		IServerDetector [ ] detectors = new IServerDetector [ ] { new BukkitServer . Detector ( ) } ;
		
		for ( IServerDetector isd : detectors ) {
		
			if ( isd . detect ( ) ) {
			
				MineDonate . logInfo ( "Detected server plugin loader[" + isd . getServerPluginLoaderClassName ( ) + "]!" ) ;

				serverPL = ( ServerPluginLoader ) PluginHelper . class . getClassLoader ( ) . loadClass ( isd . getServerPluginLoaderClassName ( ) ) . newInstance ( ) ;
				
				break ;
				
			}
			
		}
		
		// ClassLoader cl ;
		Object oServerSide;
		AccessorPlugin oModSide ;
		
		boolean abstractAccessorClassLoaded = false ;
		
		for ( DataOfAccessorPlugin doap : MineDonate . cfg . accessPlugins ) {
			
			if ( doap . load && serverPL . pluginExists ( doap . serverPluginName ) ) {
			
				MineDonate . logInfo ( "Try init AccessorPlugin[" + doap . modPluginName + "], server name: " + doap . serverPluginName ) ;

				try {
										
					// Проверяем и загружаем родительский класс плагинов доступа
					if ( ! abstractAccessorClassLoaded ) {
						
						serverPL . defineClassFromBytes ( getClassBytes ( AccessorPlugin . class . getName ( ) ), false ) ;

						abstractAccessorClassLoaded = true ;
						
					}
					
					if ( doap . cleanInterfaceClassName != null && doap . cleanInterfaceClassName . equals ( "" ) ) {
						
						// загружаем в класс лоадер "чистый" класс плагина доступа
						serverPL . defineClassFromBytes ( getClassBytes ( doap . cleanInterfaceClassName ), false ) ;

					}
					
					// загружаем дочернийх[cleanInterfaceClassName] класс с реализацией методов
					oServerSide = ( serverPL . defineClassFromBytes ( getClassBytes ( doap . serverInterfaceClassName ), true ) ) . newInstance ( )  ;

					// получаем конечный, дочерный класс с доступом к классу[serverInterfaceClassName] с реализацией методов
					oModSide = ( AccessorPlugin ) Class . forName ( doap . reflectionInterfaceClassName ) . newInstance ( ) ;
					
					oModSide . init ( oServerSide, doap ) ;
					
					accessorPlugins . put ( doap . modPluginName, oModSide ) ;
									
					MineDonate . logInfo ( "AccessorPlugin[" + doap . modPluginName + "] inited!" ) ;
					
				} catch ( Exception ex ) {
					
					ex . printStackTrace ( ) ;
					
				}
				
			}
			
		}
		
		for ( String k : accessorPlugins . keySet ( ) ) {
		
			MineDonate . logInfo ( "Try load AccessorPlugin[" + k + "]" ) ;
			
			accessorPlugins . get ( k ) . load ( accessorPlugins . get ( k ) . getConfigPluginData ( ) . xProperties ) ;
			
			MineDonate . logInfo ( "AccessorPlugin[" + k + "] loaded!" ) ;

		}

	}

	public static AccessorPlugin getPlugin ( String modPluginName ) {
		
		return accessorPlugins . get ( modPluginName ) ;
		
	}
     
	static Method mDefineClass = null ;

	public static Class<?> defineClassInClassLoader ( ClassLoader classLoader, byte [ ] clazz, boolean loadClass ) {
		
		try {
			
			if ( mDefineClass == null ) {
				
				mDefineClass = ClassLoader . class . getDeclaredMethod ( "defineClass", new Class [ ] { String . class, byte [ ] . class, int . class, int . class } ) ;
				
				mDefineClass . setAccessible ( true ) ;
				
			}
			
			Class < ? > cl = ( Class < ? > ) mDefineClass . invoke ( classLoader, null, clazz, 0, clazz . length ) ;
						
			if ( loadClass ) {
				
				return classLoader . loadClass ( cl . getName ( ) ) ;
				
			}
			
		} catch ( Exception ex ) {
			
			ex . printStackTrace ( ) ;
			
		}
		
		return null ;
		
	}

	static byte [ ] getClassBytes ( String cl ) throws Throwable {
		
		InputStream stream = PluginHelper . class . getClassLoader ( ) . getResourceAsStream ( cl . replace ( '.', '/' ) . concat ( ".class" ) ) ;
		byte [ ] b =  ByteStreams . toByteArray ( stream ) ;
		
		stream . close ( ) ;
		
		return b ;
		
	}
	
}

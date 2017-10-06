package ru.alastar.minedonate.plugin;

import com.google.common.io.ByteStreams;
import org.bukkit.Bukkit;

import ru.alastar.minedonate.MineDonate;
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
	
	public static boolean hasExistsOnServer ( String name ) {
		
		return Bukkit . getPluginManager ( ) . isPluginEnabled ( name ) ;
		
	}
	
	static Method mDefineClass = null ;
	
	public static void loadPlugins ( ) throws NoSuchMethodException, SecurityException {
		
		if ( mDefineClass == null ) {
			
			mDefineClass = ClassLoader . class . getDeclaredMethod ( "defineClass", new Class [ ] { String . class, byte [ ] . class, int . class, int . class } ) ;
			
			mDefineClass . setAccessible ( true ) ;
			
		}
		
		ClassLoader cl ;
		Object oServerSide;
		AccessorPlugin oModSide ;
		
		boolean abstractAccessorClassLoaded = false ;
		
		for ( DataOfAccessorPlugin doap : MineDonate . cfg . accessPlugins ) {
			
			if ( doap . load && hasExistsOnServer ( doap . serverPluginName ) ) {
			
				MineDonate . logInfo ( "[MineDonate] Try init AccessorPlugin[" + doap . modPluginName + "], server name: " + doap . serverPluginName ) ;

				try {
					
					cl = Bukkit . getPluginManager ( ) . getPlugin ( doap . serverPluginName ) . getClass ( ) . getClassLoader ( ) ;
					
					// Проверяем и загружаем родительский класс плагинов доступа
					if ( ! abstractAccessorClassLoaded ) {
						
						defineClassInClassLoader ( cl, AccessorPlugin . class . getName ( ), false ) ;

						abstractAccessorClassLoaded = true ;
						
					}
					
					if ( doap . cleanInterfaceClassName != null && doap . cleanInterfaceClassName . equals ( "" ) ) {
						
						// загружаем в класс лоадер "чистый" класс плагина доступа
						defineClassInClassLoader ( cl, doap . cleanInterfaceClassName, false ) ;
						
					}
					
					// загружаем дочернийх[cleanInterfaceClassName] класс с реализацией методов
					oServerSide = defineClassInClassLoader ( cl , doap . serverInterfaceClassName, true ) . newInstance ( ) ;
					
					// получаем конечный, дочерный класс с доступом к классу[serverInterfaceClassName] с реализацией методов
					oModSide = ( AccessorPlugin ) Class . forName ( doap . reflectionInterfaceClassName ) . newInstance ( ) ;
					
					oModSide . init ( oServerSide, doap ) ;
					
					accessorPlugins . put ( doap . modPluginName, oModSide ) ;
									
					MineDonate . logInfo ( "[MineDonate] AccessorPlugin[" + doap . modPluginName + "] inited!" ) ;
					
				} catch ( Exception ex ) {
					
					ex . printStackTrace ( ) ;
					
				}
				
			}
			
		}
		
		for ( String k : accessorPlugins . keySet ( ) ) {
		
			MineDonate . logInfo ( "[MineDonate] Try load AccessorPlugin[" + k + "]" ) ;
			
			accessorPlugins . get ( k ) . load ( accessorPlugins . get ( k ) . getConfigPluginData ( ) . xProperties ) ;
			
			MineDonate . logInfo ( "[MineDonate] AccessorPlugin[" + k + "] loaded!" ) ;

		}

	}

	public static AccessorPlugin getPlugin ( String modPluginName ) {
		
		return accessorPlugins . get ( modPluginName ) ;
		
	}
        
	private static Class<?> defineClassInClassLoader ( ClassLoader classLoader, String cl, boolean loadClass ) {
		
		try {
			
			InputStream stream = PluginHelper . class . getClassLoader ( ) . getResourceAsStream ( cl . replace ( '.', '/' ) . concat ( ".class" ) ) ;
			byte [ ] b =  ByteStreams . toByteArray ( stream ) ;
			
			mDefineClass . invoke ( classLoader, null, b, 0, b . length ) ;
						
			if ( loadClass ) {
				
				return classLoader . loadClass ( cl ) ;
				
			}
			
		} catch ( Exception ex ) {
			
			ex . printStackTrace ( ) ;
			
		}
		
		return null ;
		
	}

}

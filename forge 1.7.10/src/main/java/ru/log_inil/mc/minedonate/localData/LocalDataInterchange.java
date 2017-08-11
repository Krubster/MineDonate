package ru.log_inil.mc.minedonate.localData;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

public class LocalDataInterchange {

	static String configFolderName = "mineDonate" ;
	
	public static boolean exists ( File root, String _path ) throws IOException {
				
		if ( root != null ) {
			
			File f = new File ( root . getCanonicalPath ( ) + File . separator + "config" + File . separator + configFolderName + File . separator + _path ) ;

			return f . exists ( ) ;
			
		} else {
			
			return false ;
			
		}
		
	}
	
	public static Object read ( Class _where, File root, String _path ) throws Exception {
		
		Object r = null ;
		
		File f = new File ( root . getCanonicalPath ( ) + File . separator + "config" + File . separator + configFolderName + File . separator + _path + ".json" ) ;
		
		boolean read = f . exists ( ) ;

		if ( read ) {

			FileInputStream fis = new FileInputStream ( f ) ;
			InputStreamReader isr = new InputStreamReader ( fis, "UTF-8" ) ;
			
			r = readerToObject ( _where, isr ) ;
			
		}
		
		if ( r == null ) {
			 
			r = _where . getConstructor ( ) . newInstance ( ) ; 
			 
		}
		
		if ( ! read ) {
			
			if ( ! f . getParentFile ( ) . exists ( ) ) {
				
				f . getParentFile ( ) . mkdirs ( ) ;
				
			}
			
			save ( r, root, _path ) ;
			
		}

		return r ;
		
	}
	
	public static Object readerToObject ( Class _where, Reader rr ) {
		
		Object r = null ;
		
		try {
			
			JsonReader reader = new JsonReader ( rr ) ;
		    Gson gson = new GsonBuilder ( ) . setPrettyPrinting ( ) . create ( ) ;
            
		    r = gson . fromJson ( reader, _where );
            
		    try {
		    	
		    	reader . close ( ) ; 
		    
		    } catch ( Exception ex ) {
		    	
		    }
		    
		    reader = null ;
		    gson = null ;

		    
		} catch ( Exception ex ) {

			ex . printStackTrace ( ) ;
			
		}

		return r ;
		
	}
	
	public static void save ( Object _where, File root, String _path ) throws Exception {
			
		File f = new File ( root . getCanonicalPath ( ) + File . separator + "config" + File . separator + configFolderName + File . separator + _path + ".json" ) ;
	
		Gson gson = new GsonBuilder ( ) . setPrettyPrinting ( ) . create ( ) ;
		
		String json = gson . toJson ( _where ) ;
		
		FileOutputStream fos = new FileOutputStream ( f ) ;
		
		fos . write ( json . getBytes ( ) ) ;
		
		fos . flush ( ) ;
		fos . close ( ) ;
		
		fos = null ;
		
	}
	
}

package ru.alastar.minedonate.merch.info;

import io.netty.buffer.ByteBuf;
import ru.alastar.minedonate.Utils;
import ru.alastar.minedonate.merch.Merch;

/**
 * Created by Alastar on 19.07.2017.
 */
public class PrivilegieInfo extends Merch {
    public String picture_url;
    public String name;
    public String description;
    public String[] worlds;
    public long seconds;     //length = 0 - all worlds, otherwise 

    public PrivilegieInfo(int _shopId, int _catId, int merch_id, String n, String desc, String picture_url, int cost, long seconds, String worlds) {
        super(_shopId, _catId, merch_id, 0);
        this.name = n;
        this.description = desc;
        this.cost = cost;
        this.picture_url = picture_url;
        this.seconds = seconds;
        if (worlds != "*")
            this.worlds = worlds.split(";");
        else
            this.worlds = new String[0];
    }

    public PrivilegieInfo() {
        super();
    }

    @Override
    public int getAmountToBuy() {
        return 1;
    }

    @Override
    public String getBoughtMessage() {
        return "bought privelegie -" + name;
    }

    @Override
    public int getCategory() {
        return 1;
    }

    @Override
    public void read(ByteBuf buf) {
    	
        super.read(buf);
        
        cost = buf.readInt();
        
        try {
			
        	name = Utils . netReadString ( buf ) ;       
					
        	description = Utils . netReadString ( buf ) ;
        	picture_url = Utils . netReadString ( buf ) ;
        
	        int worlds_len = buf.readInt();
	        
	        if (worlds_len > 0) {
	        	
	            worlds = new String[worlds_len];
                for (int i = 0; i < worlds_len; ++i) {
                    worlds[i] = Utils . netReadString ( buf ) ;
                }

	        } else {
	            
	        	this.worlds = new String[0];
	            
	        }
        
		} catch ( Exception ex ) {
			
			ex . printStackTrace ( ) ;
			
		}
        
    }

    @Override
    public void write(ByteBuf buf) {
    	
        super.write(buf);
        
        buf.writeInt(cost);

        try {
			
        	Utils . netWriteString ( buf, name ) ;
        	Utils . netWriteString ( buf, description ) ;
        	Utils . netWriteString ( buf, picture_url ) ;

            buf.writeInt(worlds.length);
            for (int i = 0; i < worlds.length; ++i) {

            	Utils . netWriteString ( buf, worlds[i] ) ;

            }
            
		} catch ( Exception ex ) {
			
			ex . printStackTrace ( ) ;
			
		}

    }
    
    public long getTimeInSeconds() {
        return seconds;
    }
}
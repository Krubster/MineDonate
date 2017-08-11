package ru.alastar.minedonate.rtnl;

import cpw.mods.fml.common.FMLCommonHandler;
import net.minecraft.entity.player.EntityPlayerMP;
import ru.alastar.minedonate.MineDonate;

public class AdminSessionManager {

    public static void beginAdminSession ( EntityPlayerMP player ) {
    	
    	MineDonate . getAccount ( player . getDisplayName ( ) . toLowerCase ( ) ) . createAdminSession ( ) ; 

    }
    
    public static void endAdminSession ( EntityPlayerMP player ) {
    	
    	MineDonate . getAccount ( player . getDisplayName ( )  . toLowerCase ( )) . adminSesson = null ;
    	
    }
    
    public static Account . AdminSession getAdminSession ( EntityPlayerMP player ) {

    	return MineDonate . getAccount ( player . getDisplayName ( ) . toLowerCase ( ) ) . adminSesson ;
    	
    }

    public static boolean checkAdminSession ( EntityPlayerMP player ) {
    
    	return MineDonate . getAccount ( player . getDisplayName ( ) . toLowerCase ( ) ) . adminSesson != null && FMLCommonHandler.instance().getMinecraftServerInstance().getConfigurationManager().func_152596_g(player.getGameProfile());
    
    }
    
    public static boolean checkAdminSessionLogin ( String pass, EntityPlayerMP player ) {
        
    	return MineDonate.cfg.sessionPassword.equalsIgnoreCase(pass) && FMLCommonHandler.instance().getMinecraftServerInstance().getConfigurationManager().func_152596_g(player.getGameProfile());
  
    }
    
}

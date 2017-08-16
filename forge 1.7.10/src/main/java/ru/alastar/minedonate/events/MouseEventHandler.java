package ru.alastar.minedonate.events;

import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.event.entity.player.EntityInteractEvent;

import ru.alastar.minedonate.MineDonate;
import ru.alastar.minedonate.rtnl.Account;
import ru.alastar.minedonate.rtnl.AdminSessionManager;

/**
 * Created by Alastar on 03.08.2017.
 */
public class MouseEventHandler {
    @SideOnly(Side.SERVER)
    @SubscribeEvent(priority= EventPriority.NORMAL, receiveCanceled=true)
    public void onEvent(EntityInteractEvent event) {
       
    	if(event.entityPlayer instanceof EntityPlayerMP) {
         
        	if (AdminSessionManager.checkAdminSession((EntityPlayerMP) event.entityPlayer)) {
              
            	final Account.AdminSession session = AdminSessionManager.getAdminSession((EntityPlayerMP) event.entityPlayer);
              
                if(session.pending){
                	
                	if ( ! ( event.target instanceof EntityLivingBase ) ) {
                        
                		event.entityPlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "Error add entity! EntityLivingBase check error!"));
                        event.setCanceled(true);
                        session.pending = false ;
                        
                		return;
                        
                	}
                	
                	try {
                		
                		Class.forName(event.target.getClass().getName()).getDeclaredConstructor(net.minecraft.world.World.class).newInstance(event.entityPlayer.getEntityWorld());

                	} catch ( Exception ex ) {
                		
                		event.entityPlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "Error add entity! EntityLivingBase init test error!"));
                        event.setCanceled(true);
                        session.pending = false ;

                		return;
                		
                	} 
                	
                    MineDonate.AddEntityBy(session, event.target);
                    event.setCanceled(true);
                    event.entityPlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.GOLD + "Entity added! Check it shop now"));
             
                }
                
            }
        	
        }
    	
    }
    
}

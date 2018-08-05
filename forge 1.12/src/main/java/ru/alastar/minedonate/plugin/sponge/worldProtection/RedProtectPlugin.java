package ru.alastar.minedonate.plugin.sponge.worldProtection;

import br.net.fabiozumbi12.redprotect.API.RedProtectAPI;
import br.net.fabiozumbi12.redprotect.RedProtect;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;
import ru.alastar.minedonate.plugin.WorldProtectionPlugin;

import java.util.UUID;

/**
 * Created by Alastar on 28.11.2017.
 */
public class RedProtectPlugin extends WorldProtectionPlugin {
    boolean loaded = false;


    @Override
    public void addPlayerToRegion(String world, String rgName, UUID player) {
        RedProtectAPI.getRegion(rgName, Sponge.getServer().getWorld(world).get()).addLeader(Sponge.getServer().getPlayer(player).get().getName());
    }

    @Override
    public void removePlayerFromRegion(String world, String rgName, UUID player) {
        Player splayer = Sponge.getServer().getPlayer(player).get();
        RedProtectAPI.getRegion(rgName, Sponge.getServer().getWorld(world).get()).removeLeader(splayer.getName());
    }
    
    @Override
    public boolean checkRegionMaxOut(String world, UUID player) {
        Player splayer = Sponge.getServer().getPlayer(player).get();
        return (RedProtectAPI.getPlayerRegions(splayer.getName()).size() + 1) > RedProtect.ph.getPlayerClaimLimit(splayer);
    }
}

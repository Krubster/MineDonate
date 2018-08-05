package ru.alastar.minedonate.plugin.sponge.permissions;

import me.lucko.luckperms.api.LuckPermsApi;
import me.lucko.luckperms.api.Node;
import me.lucko.luckperms.api.User;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.service.ProviderRegistration;
import ru.alastar.minedonate.plugin.PermissionsPlugin;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class LuckPermsPlugin extends PermissionsPlugin {

    boolean loaded = false;
    LuckPermsApi api;

    public LuckPermsPlugin() {
    }

    @Override
    public void load(Map<String, Object> prop) {

        if (!loaded) {

            Optional<ProviderRegistration<LuckPermsApi>> provider = Sponge.getServiceManager().getRegistration(LuckPermsApi.class);
            if (provider.isPresent()) {
                api = provider.get().getProvider();

            }
            loaded = true;

        }

    }

    @Override
    public boolean hasPermission(UUID plr, String name) {
        Node node = api.getNodeFactory().makeGroupNode(name).build();
        User user = api.getUser(plr);
        if(user != null)
           return user.hasPermission(node).asBoolean();
        else
            return false;
    }

    @Override
    public void addGroup(UUID plr, String name, String world, Long time) {
        Node node = api.getNodeFactory().makeGroupNode(name).setExpiry(time).setWorld(world).build();
        User user = api.getUser(plr);
        if(user != null)
            user.setPermission(node);
    }

    @Override
    public void removeGroup(UUID plr, String name) {
        User user = api.getUser(plr);
        Node node = null;
        if(user != null){
            List<Node> set =  user.getOwnNodes();
            for(Node node1: set){
                if(node1.isGroupNode()){
                    if(node1.getGroupName() == name){
                      node = node1;
                      break;
                    }
                }
            }
            user.unsetPermission(node);
        }
    }

}

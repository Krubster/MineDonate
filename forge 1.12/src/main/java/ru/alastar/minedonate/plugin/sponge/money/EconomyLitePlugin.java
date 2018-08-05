package ru.alastar.minedonate.plugin.sponge.money;

import io.github.flibio.economylite.EconomyLite;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.cause.NamedCause;
import org.spongepowered.api.service.economy.Currency;
import ru.alastar.minedonate.plugin.MoneyPlugin;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Alastar on 28.11.2017.
 */
public class EconomyLitePlugin extends MoneyPlugin {
    boolean loaded = false;
    EconomyLite economy;
    Currency default_curr;

    public EconomyLitePlugin() {
    }

    @Override
    public void load(Map<String, Object> prop) {

        if (!loaded) {

            boolean vaultPlugin = Sponge.getPluginManager().getPlugin("EconomyLite").isPresent();

            if (vaultPlugin == false) {

                System.err.println("[ERROR] EconomyPlugin not found!");

            } else {

                economy = EconomyLite.getInstance();
                default_curr =  EconomyLite.getCurrencyService().getDefaultCurrency();
                loaded = true;

            }

        }

    }

    @Override
    public int getBalanceForUser(UUID player) {

        return  EconomyLite.getPlayerService().getBalance(player, default_curr, Cause.of(NamedCause.simulated(Sponge.getServer().getPlayer(player)))).intValue();

    }

    @Override
    public void withdrawMoney(UUID player, int amount) {
        EconomyLite.getPlayerService().withdraw(player, new BigDecimal(amount), default_curr, Cause.of(NamedCause.simulated(Sponge.getServer().getPlayer(player))));

    }

    @Override
    public void depositPlayer(UUID player, int amount) {
        EconomyLite.getPlayerService().deposit(player, new BigDecimal(amount), default_curr, Cause.of(NamedCause.simulated(Sponge.getServer().getPlayer(player))));

    }

    @Override
    public void setMoney(UUID player, int money) {
        EconomyLite.getPlayerService().setBalance(player, new BigDecimal(money), default_curr, Cause.of(NamedCause.simulated(Sponge.getServer().getPlayer(player))));
    }

}

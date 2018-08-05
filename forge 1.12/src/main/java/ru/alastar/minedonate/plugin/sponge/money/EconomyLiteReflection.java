package ru.alastar.minedonate.plugin.sponge.money;

import ru.alastar.minedonate.plugin.MoneyPlugin;
import ru.log_inil.mc.minedonate.localData.DataOfAccessorPlugin;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.UUID;

public class EconomyLiteReflection extends MoneyPlugin {

    MoneyPlugin o;

    Method mLoad, mGetBalanceForUser, mWithdrawMoney, mDepositPlayer, mSetMoney;

    @Override
    public void init(Object _o, DataOfAccessorPlugin _doap) {

        super.init(_o, _doap);

        try {

            o = (MoneyPlugin) _o;

            mLoad = o.getClass().getMethod("load", new Class[]{Map.class});

            mGetBalanceForUser = o.getClass().getMethod("getBalanceForUser", new Class[]{UUID.class});
            mWithdrawMoney = o.getClass().getMethod("withdrawMoney", new Class[]{UUID.class, int.class});
            mDepositPlayer = o.getClass().getMethod("depositPlayer", new Class[]{UUID.class, int.class});
            mSetMoney = o.getClass().getMethod("setMoney", new Class[]{UUID.class, int.class});

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

    @Override
    public void load(Map<String, Object> prop) {

        try {
            mLoad.invoke(o, prop);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getBalanceForUser(UUID player) {

        try {
            return (Integer) mGetBalanceForUser.invoke(o, new Object[]{player});
        } catch (Exception e) {
            e.printStackTrace();
        }

        return -1;

    }

    @Override
    public void withdrawMoney(UUID player, int amount) {

        try {
            mWithdrawMoney.invoke(o, new Object[]{player, amount});
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void depositPlayer(UUID player, int amount) {

        try {
            mDepositPlayer.invoke(o, new Object[]{player, amount});
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void setMoney(UUID player, int money) {

        try {
            mSetMoney.invoke(o, new Object[]{player, money});
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}

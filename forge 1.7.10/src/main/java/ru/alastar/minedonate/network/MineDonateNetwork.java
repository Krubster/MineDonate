package ru.alastar.minedonate.network;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import ru.alastar.minedonate.MineDonate;

/**
 * Created by Alastar on 18.07.2017.
 */
public class MineDonateNetwork {
    public static final SimpleNetworkWrapper INSTA0NCE = NetworkRegistry.INSTANCE.newSimpleChannel(MineDonate.MODID);

}
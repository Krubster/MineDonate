package ru.alastar.minedonate.rtnl;

import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import ru.alastar.minedonate.MineDonate;
import ru.alastar.minedonate.network.INetworkTask;
import ru.alastar.minedonate.network.packets.CodePacket;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Лимитор и ?прокси?-процессинг всех сообщений адресованых серверу
 */
public class ModNetworkTaskProcessor {

    final static Map<String, Integer> stat = Collections.synchronizedMap(new HashMap<String, Integer>());// new ConcurrentHashMap < > ( ) ;
    static ExecutorService executor = Executors.newCachedThreadPool();

    public static void processTask(final INetworkTask<IMessage, ?> task, final IMessage message, final MessageContext ctx) {

        if (!MineDonate.m_Enabled) {

            return;

        }

        final String k = ctx.getServerHandler().player.getGameProfile().getId().toString();

        if (checkTaskLimit(k)) {

            ModNetworkRegistry.sendTo(ctx.getServerHandler().player, new CodePacket(CodePacket.Code.SERVER_ERROR_WAIT_OTHER_TASK));

            return;

        }

        executor.submit(new Runnable() {

            public void run() {

                IMessage msg;

                upTaskLimit(k);

                msg = task.onMessageProcess(message, ctx);

                downTaskLimit(k);

                if (msg != null) {

                    ModNetworkRegistry.sendTo(ctx.getServerHandler().player, msg);

                }

            }

        });

    }

    public static Integer getTaskCount(String k) {

        if (stat.containsKey(k)) {

            return stat.get(k);

        }

        stat.put(k, 0);

        return 0;

    }

    public static boolean checkTaskLimit(String k) {

        return getTaskCount(k) > MineDonate.cfg.packetsMaxLimit;

    }

    public static void upTaskLimit(String k) {

        stat.put(k, getTaskCount(k) + 1);

    }

    public static void downTaskLimit(String k) {

        stat.put(k, getTaskCount(k) - 1);

    }

}

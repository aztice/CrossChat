package org.plugin.crosschat;

import org.plugin.crosschat.config.Config;
import org.plugin.crosschat.net.ServerThread;
import org.plugin.crosschat.net.ClientThread;
import org.plugin.crosschat.listener.ChatListener;
import org.bukkit.plugin.java.JavaPlugin;

public class CrossChat extends JavaPlugin {
    private Config config;
    private Thread netThread;
    private Object netInstance;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        config = new Config(getConfig());

        if ("server".equalsIgnoreCase(config.getMode())) {
            netInstance = new ServerThread(this, config.getListenPort());
            netThread   = (ServerThread) netInstance;
        } else {
            netInstance = new ClientThread(this, config.getRemoteHost(), config.getRemotePort());
            netThread   = (ClientThread) netInstance;
        }
        netThread.start();

        getServer().getPluginManager().registerEvents(new ChatListener(this), this);
        getLogger().info("CrossChat enabled as " + config.getMode());
    }

    @Override
    public void onDisable() {
        if (netThread != null) netThread.interrupt();
    }
    public void broadcastRaw(String raw) {
        getServer().broadcastMessage(raw);
    }

    public Config getConfigInst() { return config; }

    public Object getNetInstance() { return netInstance; }
}
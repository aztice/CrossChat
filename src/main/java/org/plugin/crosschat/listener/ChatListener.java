package org.plugin.crosschat.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.plugin.crosschat.CrossChat;
import org.plugin.crosschat.net.ServerThread;
import org.plugin.crosschat.net.ClientThread;

public class ChatListener implements Listener {
    private final CrossChat plugin;

    public ChatListener(CrossChat plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        String raw = "[" + plugin.getConfigInst().getServerName() + "] "
                   + e.getPlayer().getName() + ": " + e.getMessage();

        Object net = plugin.getNetInstance();
        if (net instanceof ServerThread) {
            ((ServerThread) net).send(raw);
        } else if (net instanceof ClientThread) {
            ((ClientThread) net).send(raw);
        }
    }
}
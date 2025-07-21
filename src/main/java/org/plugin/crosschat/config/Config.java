package org.plugin.crosschat.config;

import org.bukkit.configuration.file.FileConfiguration;

public final class Config {
    private final String serverName;
    private final String mode;
    private final int listenPort;
    private final String remoteHost;
    private final int remotePort;

    public Config(FileConfiguration cfg) {
        this.serverName = cfg.getString("server-name", "Unknown");
        this.mode       = cfg.getString("mode",       "server");
        this.listenPort = cfg.getInt   ("listen-port", 25566);
        this.remoteHost = cfg.getString("remote-host", "127.0.0.1");
        this.remotePort = cfg.getInt   ("remote-port", 25566);
    }

    public String getServerName() { return serverName; }
    public String getMode()       { return mode; }
    public int    getListenPort() { return listenPort; }
    public String getRemoteHost() { return remoteHost; }
    public int    getRemotePort() { return remotePort; }
}
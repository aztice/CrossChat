package org.plugin.crosschat.net;

import org.plugin.crosschat.CrossChat;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.CopyOnWriteArrayList;

public class ServerThread extends Thread {
    private final CrossChat plugin;
    private final int port;
    private final CopyOnWriteArrayList<Socket> clients = new CopyOnWriteArrayList<>();

    public ServerThread(CrossChat plugin, int port) {
        this.plugin = plugin;
        this.port   = port;
        setDaemon(true);
    }

    @Override
    public void run() {
        try (ServerSocket ss = new ServerSocket(port)) {
            while (!isInterrupted()) {
                Socket s = ss.accept();
                clients.add(s);
                new Thread(() -> handle(s)).start();
            }
        } catch (IOException ignored) {}
    }

    private void handle(Socket s) {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()))) {
            String line;
            while ((line = in.readLine()) != null) {
                plugin.broadcastRaw(line);
            }
        } catch (IOException ignored) {}
    }

    /** 消息广播给所有已连接的客户端 */
    public void send(String msg) {
        for (Socket s : clients) {
            try {
                new PrintWriter(s.getOutputStream(), true).println(msg);
            } catch (IOException ignored) {}
        }
    }
}
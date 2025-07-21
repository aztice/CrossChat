package org.plugin.crosschat.net;

import org.plugin.crosschat.CrossChat;

import java.io.*;
import java.net.Socket;

public class ClientThread extends Thread {
    private final CrossChat plugin;
    private final String host;
    private final int port;
    private Socket socket;
    private PrintWriter out;

    public ClientThread(CrossChat plugin, String host, int port) {
        this.plugin = plugin;
        this.host   = host;
        this.port   = port;
        setDaemon(true);
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            try {
                socket = new Socket(host, port);
                out    = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String line;
                while ((line = in.readLine()) != null) {
                    plugin.broadcastRaw(line);
                }
            } catch (IOException e) {
                try { sleep(5000); } catch (InterruptedException ignored) {}
            }
        }
    }

    public void send(String msg) {
        if (out != null) out.println(msg);
    }
}
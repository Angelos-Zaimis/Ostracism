package org.ostracismChain;

import org.ostracismChain.network.NetworkManager;
import org.ostracismChain.network.P2PServer;
import org.ostracismChain.web.WebServer;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class OstracismChainMain {

    public static void main(String[] args) throws IOException {
        ExecutorService executor = Executors.newFixedThreadPool(3); // Adjust the pool size as needed

        executor.submit(() -> {
            P2PServer p2pServer = new P2PServer();
            p2pServer.startServer();
        });

        executor.submit(() -> {
            NetworkManager networkManager = new NetworkManager();
            networkManager.startServer();
        });

        executor.submit(() -> {
            try {
                WebServer webServer = new WebServer();
                webServer.startServer();
            } catch (IOException e) {
                System.err.println("Failed to start Web Server:");
                e.printStackTrace();
            }
        });
    }
}

package org.ostracismChain;

import org.ostracismChain.network.NetworkManager;
import org.ostracismChain.web.WebServer;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class OstracismChainMain {

    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(2);

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

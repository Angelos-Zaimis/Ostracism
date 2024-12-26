package org.ostracismChain;

import org.ostracismChain.network.NetworkManager;
import org.ostracismChain.network.P2PServer;
import org.ostracismChain.web.WebServer;

import java.io.IOException;

public class OstracismChainMain {

    public static void main(String[] args) throws IOException {

        P2PServer p2pServer = new P2PServer();
        p2pServer.startServer();

        NetworkManager networkManager = new NetworkManager();
        networkManager.startServer();

        WebServer webServer = new WebServer();
        webServer.startServer();

    }
}

package org.ostracismChain;

import org.ostracismChain.network.NetworkManager;
import org.ostracismChain.network.P2PServer;

public class OstracismChainMain {

    public static void main(String[] args) {

        P2PServer p2pServer = new P2PServer();
        p2pServer.startServer();

        NetworkManager networkManager = new NetworkManager();
        networkManager.startServer();

    }
}

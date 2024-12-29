package org.ostracismChain.network;

import java.util.HashSet;
import java.util.Set;

public class NetworkManager {

    private Set<Node> peers;
    private P2PServer server;

    public NetworkManager() {
        peers = new HashSet<>();
        this.server = new P2PServer();
    }

    public void startServer() {
        new Thread(() -> server.startServer()).start();
    }

    public void addPeer(Node peer) {
        peers.add(peer);
    }

    public void removePeer(Node peer) {
        peers.remove(peer);
    }

    public void connectToPeer(String host, int port) {
        Node newPeer = new Node("Node-" + host + ":" + port);

        if (newPeer.connectToPeer(host, port)) {
            peers.add(newPeer);
        }
    }

    public void broadcastBlock(String block) {
        for (Node peer : peers) {
            peer.sendMessage(block);
        }
    }

    public void handleMessages() {
        for (Node peer : peers) {
            try {
                String message = peer.receiveMessage();
                System.out.println("Receive message from " + peer.getId() + ": " + message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

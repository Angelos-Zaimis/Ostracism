package org.ostracismChain.web;

import com.sun.net.httpserver.HttpServer;
import org.ostracismChain.web.controller.VoteController;

import java.io.IOException;
import java.net.InetSocketAddress;

public class WebServer {

    private static final int PORT = 8081;

    private final VoteController voteController;

    public WebServer(VoteController voteController) {
        this.voteController = voteController;
    }

    public void startServer() throws IOException {

        HttpServer server = HttpServer.create(new InetSocketAddress(PORT), 0);

        server.createContext("/api/vote", voteController);

        System.out.println("Web server started on port: " + PORT);
        server.start();
    }

}

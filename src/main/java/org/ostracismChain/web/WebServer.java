package org.ostracismChain.web;

import com.sun.net.httpserver.HttpServer;
import org.ostracismChain.web.controller.VoteController;
import org.ostracismChain.web.service.VoteService;

import java.io.IOException;
import java.net.InetSocketAddress;

public class WebServer {

    private static final int PORT = 8081;

    private final VoteController voteController;
    private final VoteService voteService;

    public WebServer() {
        this.voteService = new VoteService();
        this.voteController = new VoteController(voteService);
    }

    public void startServer() throws IOException {

        HttpServer server = HttpServer.create(new InetSocketAddress(PORT), 0);
        server.createContext("/api/vote", voteController);
        server.setExecutor(null);

        System.out.println("Web server started on port: " + PORT);
        server.start();
    }

}

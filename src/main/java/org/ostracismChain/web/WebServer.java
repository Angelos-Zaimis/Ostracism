package org.ostracismChain.web;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import org.ostracismChain.web.dto.VoteRequest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;

public class WebServer {

    private static final int PORT = 8081;

    public void startServer() throws IOException {

        HttpServer server = HttpServer.create(new InetSocketAddress(PORT), 0);

        server.createContext("/api/vote",);

        System.out.println("Web server started on port: " + PORT);
        server.start();
    }

}

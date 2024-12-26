package org.ostracismChain.web.controller;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.ostracismChain.web.dto.VoteRequest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class VoteController implements HttpHandler{

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        if (httpExchange.getRequestMethod().equals("POST")) {

            InputStreamReader reader = new InputStreamReader(httpExchange.getRequestBody());
            BufferedReader bufferedReader = new BufferedReader(reader);
            StringBuilder requestBody = new StringBuilder();

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                requestBody.append(line);
            }

            Gson gson = new Gson();
            VoteRequest voteRequest = gson.fromJson(requestBody.toString(), VoteRequest.class);

            String voterId = voteRequest.getVoterId();
            String candidateId = voteRequest.getCandidateId();

        }
    }
}

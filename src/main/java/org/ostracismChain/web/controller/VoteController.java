package org.ostracismChain.web.controller;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.ostracismChain.web.dto.VoteRequest;
import org.ostracismChain.web.service.VoteService;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;

public class VoteController implements HttpHandler {

    private static final Logger LOGGER = Logger.getLogger(VoteController.class.getName());
    private final VoteService voteService;
    private final Gson gson;

    public VoteController(VoteService voteService) {
        this.voteService = voteService;
        this.gson = new Gson();
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String requestMethod = httpExchange.getRequestMethod();

        if ("POST".equalsIgnoreCase(requestMethod)) {
            handlePostRequest(httpExchange);
        } else {
            handleUnsupportedMethod(httpExchange);
        }
    }

    private void handlePostRequest(HttpExchange httpExchange) throws IOException {
        String contentType = httpExchange.getRequestHeaders().getFirst("Content-Type");

        if (contentType == null || !contentType.equalsIgnoreCase("application/json")) {
            sendResponse(httpExchange, 400, "Invalid Content-Type. Expected application/json.");
            return;
        }

        try (InputStreamReader reader = new InputStreamReader(httpExchange.getRequestBody(), StandardCharsets.UTF_8);
             BufferedReader bufferedReader = new BufferedReader(reader)) {

            StringBuilder requestBody = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                requestBody.append(line);
            }

            VoteRequest voteRequest = gson.fromJson(requestBody.toString(), VoteRequest.class);

            if (voteRequest == null || !isValid(voteRequest)) {
                sendResponse(httpExchange, 400, "Invalid vote request data.");
                return;
            }

            boolean success = voteService.handleCreateVote(voteRequest.getVoterId(), voteRequest.getCandidateId());

            if (success) {
                sendResponse(httpExchange, 200, "Vote recorded successfully.");
            } else {
                sendResponse(httpExchange, 500, "Failed to record vote.");
            }

        } catch (JsonSyntaxException e) {
            LOGGER.log(Level.WARNING, "JSON parsing error: ", e);
            sendResponse(httpExchange, 400, "Malformed JSON.");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Internal server error: ", e);
            sendResponse(httpExchange, 500, "Internal server error.");
        }
    }

    private void handleUnsupportedMethod(HttpExchange httpExchange) throws IOException {
        sendResponse(httpExchange, 405, "Method Not Allowed");
    }

    private boolean isValid(VoteRequest voteRequest) {
        return voteRequest.getVoterId() != null && !voteRequest.getVoterId().isEmpty()
                && voteRequest.getCandidateId() != null && !voteRequest.getCandidateId().isEmpty();
    }

    private void sendResponse(HttpExchange httpExchange, int statusCode, String responseMessage) throws IOException {
        byte[] responseBytes = responseMessage.getBytes(StandardCharsets.UTF_8);
        httpExchange.getResponseHeaders().set("Content-Type", "text/plain; charset=utf-8");
        httpExchange.sendResponseHeaders(statusCode, responseBytes.length);
        try (OutputStream os = httpExchange.getResponseBody()) {
            os.write(responseBytes);
        }
    }
}


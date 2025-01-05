package org.ostracismChain.web;

import com.sun.net.httpserver.HttpServer;
import org.ostracismChain.blockchain.VotingBlockChain;
import org.ostracismChain.consensus.VoterRegistry;
import org.ostracismChain.network.NetworkManager;
import org.ostracismChain.storage.VotingBlockDAO;
import org.ostracismChain.web.controller.VoteController;
import org.ostracismChain.web.service.TransactionService;
import org.ostracismChain.web.service.VoteService;
import org.ostracismChain.web.service.validation.BlockValidationService;
import org.ostracismChain.web.service.validation.TransactionValidationService;

import java.io.IOException;
import java.net.InetSocketAddress;

public class WebServer {

    private static final int PORT = 8081;

    private final VoteController voteController;
    private final VoteService voteService;

    public WebServer() {
        VotingBlockDAO votingBlockDAO = new VotingBlockDAO();
        BlockValidationService blockValidationService = new BlockValidationService();
        VotingBlockChain votingBlockChain = new VotingBlockChain(votingBlockDAO);
        NetworkManager networkManager = new NetworkManager();
        VoterRegistry voterRegistry = new VoterRegistry();
        TransactionValidationService transactionValidationService = new TransactionValidationService(voterRegistry);
        TransactionService transactionService = new TransactionService();

        this.voteService = new VoteService(
                votingBlockChain,
                networkManager,
                blockValidationService,
                transactionValidationService,
                transactionService
        );

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

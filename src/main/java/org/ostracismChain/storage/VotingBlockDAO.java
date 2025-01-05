package org.ostracismChain.storage;

import com.mongodb.client.*;
import org.bson.Document;
import org.ostracismChain.blockchain.VotingBlock;

import java.util.ArrayList;
import java.util.List;

public class VotingBlockDAO {
    private final MongoCollection<Document> collection;

    public VotingBlockDAO() {
        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
        MongoDatabase database = mongoClient.getDatabase("blockchain_db");
        collection = database.getCollection("voting_blocks");
    }

    public void saveBlock(VotingBlock block) {
        Document doc = new Document("index", block.getIndex())
                .append("timestamp", block.getTimestamp())
                .append("votingTransactions", block.getVotingTransactions())
                .append("proposedByValidator", block.getProposedByValidator())
                .append("previousBlockHash", block.getPreviousBlockHash())
                .append("blockHash", block.getBlockHash());
        collection.insertOne(doc);
    }

    public List<VotingBlock> getAllBlocks() {
        List<VotingBlock> chain = new ArrayList<>();
        FindIterable<Document> docs = collection.find().sort(new Document("index", 1));
        for (Document doc : docs) {
            VotingBlock block = new VotingBlock(
                    doc.getInteger("index"),
                    doc.getLong("timestamp"),
                    doc.getString("data"),
                    doc.getString("previousHash")
            );
            block.setHash(doc.getString("hash"));
            chain.add(block);
        }
        return chain;
    }
}

package org.ostracismChain.core;

import java.util.List;

public class Block {

    private int previousBlockHash;
    private List<Trasaction> trasactions;

    public Block (int previousBlockHash, List<Trasaction> trasactions) {
        this.previousBlockHash = previousBlockHash;
        this.trasactions = trasactions;
    }
}

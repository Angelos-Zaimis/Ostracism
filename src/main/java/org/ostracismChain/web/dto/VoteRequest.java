package org.ostracismChain.web.dto;

public class VoteRequest {
    private String voterId;
    private String candidateId;

    public String getVoterId() {
        return voterId;
    }

    public String getCandidateId() {
        return candidateId;
    }
}

package models;

import java.util.HashMap;
import java.util.Map;

/*
Each buyer will have a name, and the bids that he/she is placing, the number of actions the buyer has participated,
and whether the buyer is a preferred buyer or not.
 */

public class Buyer {
    private String name;
    private Map<String, Integer> bids;
    private int auctionsParticipated;
    private boolean isPreferredBuyer;

    public boolean isPreferredBuyer() {
        return isPreferredBuyer;
    }

    public void setPreferredBuyer(boolean preferredBuyer) {
        isPreferredBuyer = preferredBuyer;
    }

    public int getAuctionsParticipated() {
        return auctionsParticipated;
    }

    public void setAuctionsParticipated(int auctionsParticipated) {
        this.auctionsParticipated = auctionsParticipated;
    }

    public Buyer(String name) {
        this.name = name;
        this.bids = new HashMap<>();

        //by default for each buyer the actionsParticipated should be 0 and preferredBuyer should be false because
        // the buyer has not participated in more than 2 auctions.
        this.auctionsParticipated = 0;
        this.isPreferredBuyer = false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, Integer> getBids() {
        return bids;
    }

    public void setBids(Map<String, Integer> bids) {
        this.bids = bids;
    }
}

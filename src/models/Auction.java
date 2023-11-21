package models;

import java.util.HashMap;
import java.util.Map;

/*
Each action can have a name, LowestBid for that auction, HighestBid for that auction, the seller for that auction,
and finally the bids that are taking place in that auction.
 */
public class Auction {

    private String name;
    private int lowestBidLimit;
    private int highestBidLimit;
    private String seller;
    private Map<String, Integer> bids;

    public Auction(String name, int lowestBidLimit, int highestBidLimit, String seller) {
        this.name = name;
        this.lowestBidLimit = lowestBidLimit;
        this.highestBidLimit = highestBidLimit;
        this.seller = seller;
        this.bids = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLowestBidLimit() {
        return lowestBidLimit;
    }

    public void setLowestBidLimit(int lowestBidLimit) {
        this.lowestBidLimit = lowestBidLimit;
    }

    public int getHighestBidLimit() {
        return highestBidLimit;
    }

    public void setHighestBidLimit(int highestBidLimit) {
        this.highestBidLimit = highestBidLimit;
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public Map<String, Integer> getBids() {
        return bids;
    }

    public void setBids(Map<String, Integer> bids) {
        this.bids = bids;
    }
}


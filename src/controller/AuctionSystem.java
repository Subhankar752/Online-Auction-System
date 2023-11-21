package controller;

import models.Auction;
import models.Buyer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
This class is the main controller for the auction system. It contains all the logic for adding buyer and seller,
logic for creating actions and bid,
logic for updating bid and updating to preferred buyer,
logic for withdrawing bid, closing auction, and finally finding the winner for the auction.
 */

public class AuctionSystem {
    private Map<String, Buyer> buyers;

    private Map<String, Auction> auctions;

    private List<String> sellers = new ArrayList<>();

    public Map<String, Buyer> getBuyers() {
        return buyers;
    }

    public void setBuyers(Map<String, Buyer> buyers) {
        this.buyers = buyers;
    }

    public Map<String, Auction> getAuctions() {
        return auctions;
    }

    public void setAuctions(Map<String, Auction> auctions) {
        this.auctions = auctions;
    }

    public List<String> getSellers() {
        return sellers;
    }

    public void setSellers(List<String> sellers) {
        this.sellers = sellers;
    }

    public AuctionSystem() {
        buyers = new HashMap<>();
        auctions = new HashMap<>();
    }

    public void addBuyer(String buyerName) {
        buyers.put(buyerName, new Buyer(buyerName));
    }

    public void createAuction(String auctionName, int lowestBidLimit, int highestBidLimit, String sellerName) {
        auctions.put(auctionName, new Auction(auctionName, lowestBidLimit, highestBidLimit, sellerName));
    }

    public void addSeller(String sellerName) {
        if (sellerName != null && !sellers.contains(sellerName)) {
            sellers.add(sellerName);
            System.out.println("Seller " + sellerName + " has been registered.");
        } else {
            System.out.println("Seller registration failed. Seller name is invalid or already exists with name " + sellerName);
        }
    }

    public void createBid(String buyerName, String auctionName, int bidAmount) {
        Buyer buyer = buyers.get(buyerName);
        Auction auction = auctions.get(auctionName);

        if (buyer != null && auction != null) {
            //if the bid amount satisfied the auction lowest and higest bid amount then only add.
            if (bidAmount >= auction.getLowestBidLimit() && bidAmount <= auction.getHighestBidLimit()) {
                buyer.getBids().put(auctionName, bidAmount);
                auction.getBids().put(buyerName, bidAmount);

                //increasing the number of auctions participated for the buyer.
                buyer.setAuctionsParticipated(buyer.getAuctionsParticipated() + 1);
            }
        }

        Buyer currentBuyer = buyers.get(buyerName);

        //if the currentBuyer has participated in more than 2 auctions then update the buyer to preferred buyer.
        if (currentBuyer != null && !isPreferredBuyer(currentBuyer) && currentBuyer.getAuctionsParticipated() > 2) {
            upgradeToPreferredBuyer(currentBuyer);
        }
    }

    private void upgradeToPreferredBuyer(Buyer buyer) {
        buyer.setPreferredBuyer(true);
    }

    private boolean isPreferredBuyer(Buyer buyer) {
        return buyer.isPreferredBuyer();
    }

    public void updateBid(String buyerName, String auctionName, int newBidAmount) {
        Buyer buyer = buyers.get(buyerName);
        Auction auction = auctions.get(auctionName);

        if (buyer != null && auction != null) {
            if (buyer.getBids().containsKey(auctionName)) {
                int currentBid = buyer.getBids().get(auctionName);

                // Check if the new bid is within the allowed range
                if (newBidAmount >= auction.getLowestBidLimit() && newBidAmount <= auction.getHighestBidLimit()) {
                    // Update the buyer's bid
                    buyer.getBids().put(auctionName, newBidAmount);

                    // Update the auction's bid
                    auction.getBids().put(buyerName, newBidAmount);

                    System.out.println(buyerName + " has updated the bid for " + auctionName + " to " + newBidAmount);
                } else {
                    System.out.println("Bid update failed. New bid amount is outside the allowed range.");
                }
            } else {
                System.out.println("Bid update failed. " + buyerName + " did not place a bid for " + auctionName);
            }
        } else {
            System.out.println("Bid update failed. Invalid buyer or auction.");
        }

    }

    public void withdrawBid(String buyerName, String auctionName) {
        Buyer buyer = buyers.get(buyerName);
        Auction auction = auctions.get(auctionName);

        if (buyer != null && auction != null) {
            if (buyer.getBids().containsKey(auctionName)) {
                buyer.getBids().remove(auctionName); // Remove the buyer's bid
                auction.getBids().remove(buyerName); // Remove the bid from the auction

                System.out.println(buyerName + " has withdrawn their bid for " + auctionName);
            } else {
                System.out.println("Bid withdrawal failed. " + buyerName + " did not place a bid for " + auctionName);
            }
        } else {
            System.out.println("Bid withdrawal failed. Invalid buyer or auction.");
        }
    }

    public void closeAuction(String auctionName) {
        Auction auction = auctions.get(auctionName);

        if (auction != null) {
            //find winner for that auction.
            String winner = findWinner(auction);

            if (winner != null) {
                System.out.println("Winner: " + winner);
            } else {
                System.out.println("No winner for " + auctionName);
            }
        }
    }

    public String findWinner(Auction auction) {
        //To store the frequency for each bid to avoid duplicates.
        Map<Integer, Integer> bidCounts = new HashMap<>();

        //To store the preferred buyers to find the winner.
        Map<String, Integer> preferredBuyerBids = new HashMap<>();

        int winningBid = -1;
        String winner = null;

        for (Map.Entry<String, Integer> entry : auction.getBids().entrySet()) {
            String buyerName = entry.getKey();
            int bid = entry.getValue();

            //for preferred buyers maintain a separate map for finding the winner.
            if (isPreferredBuyer(buyers.get(buyerName))) {
                preferredBuyerBids.put(buyerName, bid);
            }

            //updating the frequency for each bid done in the auction.
            bidCounts.put(bid, bidCounts.getOrDefault(bid, 0) + 1);
        }

        //In case we have preferred buyers find winner among them.
        for (Map.Entry<String, Integer> entry : preferredBuyerBids.entrySet()) {
            String buyerName = entry.getKey();
            int bid = entry.getValue();

            //frequency has to be 1 for winner.
            if (bidCounts.get(bid) == 1 && bid > winningBid) {
                winningBid = bid;
                winner = buyerName;
            }
        }

        //In case of no preferred Buyer we need to check for winner.
        if (winner == null) {
            for (Map.Entry<String, Integer> entry : auction.getBids().entrySet()) {
                String buyerName = entry.getKey();
                int bid = entry.getValue();

                if (bidCounts.get(bid) == 1 && bid > winningBid) {
                    winningBid = bid;
                    winner = buyerName;
                }
            }
        }

        return winner;
    }
}

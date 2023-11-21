import controller.AuctionSystem;

public class Main {
    public static void main(String[] args) {
        AuctionSystem system = new AuctionSystem();

        //Test case 1 -> Bonus functionality
        system.addBuyer("buyer1");
        system.addBuyer("buyer2");
        system.addBuyer("buyer3");
        system.addBuyer("buyer4");

        system.addSeller("seller3");
        system.createAuction("A3", 10, 50, "seller1");

        system.createBid("buyer1", "A3", 17);
        system.createBid("buyer2", "A3", 15);
        system.createBid("buyer3", "A3", 19);
        system.createBid("buyer4", "A3", 19); // buyer4 participates in 2 auctions
        system.createBid("buyer4", "A3", 25);
        system.createBid("buyer4", "A3", 27); // This will upgrade buyer4 to a preferred buyer

        System.out.println("For Auction A3");
        system.closeAuction("A3");

//        -------------------------------------------------------------------------------------------------------

        //Test Case 2
        system.addBuyer("buyer1");
        system.addBuyer("buyer2");
        system.addBuyer("buyer3");

        system.addSeller("seller1");

        system.createAuction("A1", 10, 50, "seller1");

        system.createBid("buyer1", "A1", 17);
        system.createBid("buyer2", "A1", 15);

        system.updateBid("buyer2", "A1", 19);
        system.createBid("buyer3", "A1", 19);

        System.out.println("For auction A1");
        system.closeAuction("A1");

//        --------------------------------------------------------------------------------------------------------

        //Test Case 3
        system.addSeller("seller2");

        system.createAuction("A2", 5, 20, "seller2");

        system.createBid("buyer3", "A2", 25);  // This should fail
        system.createBid("buyer2", "A2", 5);

        system.withdrawBid("buyer2", "A2");

        System.out.println("For Auction A2");
        system.closeAuction("A2");

//        ---------------------------------------------------------------------------------------------------------
    }
}
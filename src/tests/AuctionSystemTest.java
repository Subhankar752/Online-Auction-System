package tests;

import controller.AuctionSystem;
import models.Auction;
import models.Buyer;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class AuctionSystemTest {
    private AuctionSystem system;

    @Before
    public void setUp() {
        system = new AuctionSystem();
    }

    @Test
    public void testCreateBuyer() {
        system.addBuyer("buyer1");
        Buyer buyer = system.getBuyers().get("buyer1");
        assertEquals("buyer1", buyer.getName());
    }

    @Test
    public void testCreateAuction() {
        system.addSeller("seller1");
        system.createAuction("A1", 10, 50, "seller1");

        Auction auction = system.getAuctions().get("A1");
        assertEquals("A1", auction.getName());
    }

    @Test
    public void testUpgradeToPreferredBuyer() {
        system.addBuyer("buyer1");
        system.addBuyer("buyer2");
        system.addSeller("seller1");
        system.createAuction("A1", 10, 50, "seller1");

        // Buyer1 participates in 3 auctions and should be upgraded to a preferred buyer
        system.createBid("buyer1", "A1", 17);
        system.createBid("buyer1", "A1", 15);
        system.createBid("buyer1", "A1", 19);

        assertTrue(system.getBuyers().get("buyer1").isPreferredBuyer());
        assertFalse(system.getBuyers().get("buyer2").isPreferredBuyer());
    }

    @Test
    public void testFindWinner() {
        system.addBuyer("buyer1");
        system.addBuyer("buyer2");
        system.addBuyer("buyer3");
        system.addBuyer("buyer4");
        system.addSeller("seller1");

        system.createAuction("A1", 10, 50, "seller1");

        system.createBid("buyer1", "A1", 17);
        system.createBid("buyer2", "A1", 15);
        system.createBid("buyer3", "A1", 19);
        system.createBid("buyer4", "A1", 19); // buyer4 participates in 2 auctions

        // Upgrade a buyer to a preferred buyer after participating in more than 2 auctions
        system.createBid("buyer4", "A1", 25); // This will upgrade buyer4 to a preferred buyer

        assertEquals("buyer4", system.findWinner(system.getAuctions().get("A1")));
    }

    @Test
    public void testCloseAuctionNoWinner() {
        system.addSeller("seller2");
        system.createAuction("A2", 5, 20, "seller2");
        system.createBid("buyer3", "A2", 25); // This should fail as the highest bid limit is 20 for A2
        system.createBid("buyer2", "A2", 5);

        assertNull(system.findWinner(system.getAuctions().get("A2")));
    }
}


package game.gameitems;

import edu.monash.fit2099.engine.items.Item;

/**
 * A class that acts as a centralized entity that keeps tab of how many coins the player currently holds
 */
public class Wallet extends Item {
    // attribute
    /**
     * the total amount of coins the player has left
     * after picking up and spending on trades
     */
    private int coins;

    /***
     * Constructor.
     */
    public Wallet() {
        // ; is chosen as displayChar because it's not used elsewhere
        // starts in the player's inventory
        super("Wallet", ';', true);
    }

    /**
     * Method to retrieve the amount of coins in the wallet.
     *
     * @return the amount of coins in the wallet
     */
    public int getCoins() {
        return this.coins;
    }

    /**
     * Method to add coins into the wallet when coins are picked up
     *
     * @param coins the amount of coins picked up
     */
    public void addCoins(int coins) {
        this.coins += coins;
    }
}

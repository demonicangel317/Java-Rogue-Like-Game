package game.gameitems;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.items.PickUpItemAction;
import edu.monash.fit2099.engine.positions.Location;
import game.Resettable;
import game.gameitems.Wallet;

import java.util.List;

/**
 * A class that defines how the in-game currency is spawned and handled
 */
public class Coin extends Item implements Resettable {
    // attribute
    /**
     * the location the coin is spawned at
     */
    private Location location;
    /**
     * the value of the coin
     */
    private int value;

    /**
     * Constructor with the value of the coin
     *
     * @param value    is the integer value of the coin
     * @param location is the location at which the coin has spawned
     */
    public Coin(int value, Location location) {
        super("Coin", '$', true);
        this.value = value;
        this.location = location;
        // register as a resettable instance
        registerInstance();
    }

    /**
     * Defines what happens when a coin instance is picked up by an actor.
     *
     * @param actor the actor who has picked up a coin instance.
     * @return null
     */
    @Override
    public PickUpItemAction getPickUpAction(Actor actor) {
        // add into actor's wallet rather than inventory
        List<Item> inventory = actor.getInventory();
        Wallet wallet = new Wallet();
        for (int i = 0; i < inventory.size(); i++) {
            if (inventory.get(i) instanceof Wallet) {
                wallet = (Wallet) inventory.get(i);
            }
        }
        wallet.addCoins(this.value);
        return null;
    }

    /**
     * Remove all coins when the game is reset.
     */
    @Override
    public void resetInstance() {
        // remove all coins on the ground
        this.location.removeItem(this);
    }

    /**
     * Register the coin instance as a resettable instance.
     */
    @Override
    public void registerInstance() {
        Resettable.super.registerInstance();
    }
}

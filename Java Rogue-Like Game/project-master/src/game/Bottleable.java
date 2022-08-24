package game;

import game.gameitems.Bottle;

/**
 * Interface for classes that are able to be stored in the bottle
 */
public interface Bottleable {
    /**
     * A default interface method that register current instance to the singleton manager.
     * It allows corresponding classes to be stored in a Bottle.
     */
    default void pickUpInstance() {
        Bottle.getInstance().storeWater(this);
    }
}

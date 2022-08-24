package game;

import edu.monash.fit2099.engine.actors.Actor;

/**
 * Interface for classes that can be consumed by the actor.
 */
public interface Consumable {
    /**
     * Allows any classes of consumable items to be consumed.
     *
     * @param actor the actor consuming the item.
     */
    String consume(Actor actor);
}

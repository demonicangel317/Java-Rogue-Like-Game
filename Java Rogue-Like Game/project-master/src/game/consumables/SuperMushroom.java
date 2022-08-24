package game.consumables;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.Location;
import game.Consumable;
import game.Status;
import game.actions.ConsumeAction;

import java.util.List;

/**
 * A class that governs the role of the Super Mushroom
 */
public class SuperMushroom extends Item implements Consumable {
    // attributes
    /**
     * the location the super mushroom is spawned
     */
    private Location currentLocation;
    /**
     * the actor that has a hold of the super mushroom
     */
    private Actor actor;

    /**
     * Zero parameter constructor
     */
    public SuperMushroom() {
        super("Super Mushroom", '^', true);
        this.currentLocation = null;
        this.actor = null;
        // add allowable action
        this.addAction(new ConsumeAction(actor, this));
    }

    /**
     * Constructor for spawning a Super Mushroom
     */
    public SuperMushroom(Location location) {
        super("Super Mushroom", '^', true);
        this.currentLocation = location;
        this.actor = null;
        // spawn Super Mushroom
        location.addItem(this);
        // add allowable action
        this.addAction(new ConsumeAction(actor, this));
    }

    /**
     * Constructor for when a Super Mushroom is purchased
     *
     * @param location is the location at which the Super Mushroom is purchased
     * @param actor    is the actor who has the Super Mushroom added to their inventory
     */
    public SuperMushroom(Location location, Actor actor) {
        super("Super Mushroom", '^', true);
        this.currentLocation = location;
        this.actor = actor;
        // add allowable action
        this.addAction(new ConsumeAction(actor, this));
        // add to actor's inventory
        actor.addItemToInventory(this);
    }

    /**
     * Defines what the effects of consuming a super mushroom are.
     *
     * @param actor the actor who has consumed a super mushroom.
     * @return the console output.
     */
    @Override
    public String consume(Actor actor) {
        // remove item from actor's inventory
        actor.removeItemFromInventory(this);

        // increase max HP by 50
        actor.increaseMaxHp(50);
        // the display character evolves to the uppercase letter by adding TALL capability to status
        actor.addCapability(Status.TALL);
        // jumping with 100% success rate and no fall damage implemented through TALL capability

        // print description
        List<Item> inventory = actor.getInventory();
        int count = 0;
        for (Item item : inventory) {
            if (item instanceof SuperMushroom) {
                count += 1;
            }
        }
        String output = "Mario has consumed a Super Mushroom. " + count + " Super Mushroom(s) left in inventory.";
        return output;
    }
}

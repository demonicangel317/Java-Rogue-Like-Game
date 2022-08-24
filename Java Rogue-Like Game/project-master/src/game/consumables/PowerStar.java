package game.consumables;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.Location;
import game.Consumable;
import game.Player;
import game.Status;
import game.actions.ConsumeAction;

import java.util.List;

/**
 * A class that governs the role of the Power Star
 */
public class PowerStar extends Item implements Consumable {
    // attribute
    /**
     * the location the power star is spawned at
     */
    private Location currentLocation;
    /**
     * the actor that has a hold of the power star
     */
    private Actor actor;
    /**
     * a constant that defines the maximum duration a power star can remain on the map once it's created
     */
    private final int MAX_DURATION = 10;
    /**
     * the duration the power star has been on the map since its creation
     */
    private int duration;

    /**
     * Zero parameter constructor
     */
    public PowerStar() {
        super("Power Star", '*', true);
        this.currentLocation = null;
        this.actor = null;
        this.duration = 0;
        // add allowable action
        this.addAction(new ConsumeAction(null, this));
    }

    /**
     * Constructor for spawning a Power Star
     *
     * @param location is the location at which the Power Star is spawned
     */
    public PowerStar(Location location) {
        super("Power Star", '*', true);
        this.currentLocation = location;
        this.actor = null;
        this.duration = 0;
        // spawn power star
        location.addItem(this);
        // add allowable action
        this.addAction(new ConsumeAction(actor, this));
    }

    /**
     * Constructor for when a Power Star is purchased
     *
     * @param location is the location at which the Power Star is purchased
     * @param actor    is the actor who has the Power Star added to their inventory
     */
    public PowerStar(Location location, Actor actor) {
        super("Power Star", '*', true);
        this.currentLocation = location;
        this.actor = actor;
        this.duration = 0;
        // add allowable action
        this.addAction(new ConsumeAction(actor, this));
        // add to actor's inventory
        actor.addItemToInventory(this);
    }

    /**
     * Method to count down the duration the Power Star is allowed to remain on the game map.
     *
     * @param currentLocation The location of the ground on which we lie.
     */
    @Override
    public void tick(Location currentLocation) {
        if (actor != null) {
            super.tick(this.currentLocation, actor);
            duration += 1;

            if (duration > MAX_DURATION) {
                // remove Power Star if max duration has been reached
                actor.removeItemFromInventory(this);
            } else {
                System.out.printf("Power Star in inventory - " + remainingTurn() + " turns remaining.\n");
            }
        } else {
            super.tick(this.currentLocation);
            duration += 1;

            if (duration > MAX_DURATION) {
                // remove Power Star if max duration has been reached
                this.currentLocation.removeItem(this);
            } else {
                System.out.printf("Power Star spawned - " + remainingTurn() + " turns remaining.\n");
            }
        }
    }

    /**
     * Method to reset the fading duration of the Power Star
     */
    public void resetDuration() {
        this.duration = 0;
    }

    /**
     * Method to retrieve the remaining turns of a PowerStar instance
     *
     * @return the remaining turns of a PowerStar instance
     */
    public int remainingTurn() {
        return MAX_DURATION - duration;
    }

    /**
     * Defines what the effects of consuming a power star are.
     *
     * @param actor the actor who has consumed the power star
     * @return the console output.
     */
    @Override
    public String consume(Actor actor) {
        // remove item from actor's inventory
        actor.removeItemFromInventory(this);

        // heal 200 hit points
        actor.heal(200);
        // become invincible
        actor.addCapability(Status.INVINCIBLE);
        ((Player) actor).resetInvincible();
        // replace fading duration
        this.resetDuration();
        // higher grounds will be ignored if player has the capability of INVINCIBLE
        // immune to enemy attacks will be implemented through the INVINCIBLE capability
        // instantly kill any enemies implemented through the INVINCIBLE capability

        // print description
        List<Item> inventory = actor.getInventory();
        int count = 0;
        for (Item item : inventory) {
            if (item instanceof PowerStar) {
                count += 1;
            }
        }
        String output = "Mario has consumed a Power Star. " + count + " Power Star(s) left in inventory.\n";
        output += "Mario is INVINCIBLE! " + this.remainingTurn() + " turns remaining.";

        return output;
    }
}

package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.gameitems.Coin;
import game.Status;
import game.groundobjects.Dirt;

import java.util.Random;

/**
 * action that is returned when need to jump onto higher surfaces
 *
 * @author Ravindu Santhush Ratnayake
 */

public class JumpAction extends Action {

    /**
     * the success rate of the jump
     */
    private double successRate;

    /**
     * the fall damage to be given to the actor when the jump fails
     */
    private int fallDamage;

    /**
     * the location to move the actor to if the jump fails
     */
    private Location moveToLocation;

    /**
     * the name of the jump object
     */
    private String jumpObject;

    /**
     * the direction of the location to jump from the actor
     */
    private String direction;

    /**
     * Constructor
     *
     * @param moveToLocation the location to move the actor to if the jump fails
     * @param successRate the success rate of the jump
     * @param fallDamage the fall damage to be given to the actor when the jump fails
     * @param jumpObject the name of the jump object
     * @param direction the direction of the location to jump from the actor
     */
    public JumpAction(Location moveToLocation, double successRate, int fallDamage, String jumpObject, String direction) {
        this.fallDamage = fallDamage;
        this.successRate = successRate;
        this.moveToLocation = moveToLocation;
        this.jumpObject = jumpObject;
        this.direction = direction;
    }

    /**
     *this method executes the jump action
     * if the player has consumed super mushroom then the jump success rate will be set to 100 percent
     * if the no magical items have been consumed then this will calculate a random value and compare it to the success rate
     * and determine if the jump was a success
     * if the jump is a success then the actor will be moved to that location
     * else the actor's health will be reduced by the fall damage of this jump action
     *
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return returns the string result depending on whether the jump action succeeded or not
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        Random rand = new Random();
        // if actor is TALL, it means it has consumed a Super Mushroom
        // therefore it'll have a 100% success rate at jumping
        if (actor.hasCapability(Status.INVINCIBLE)) {
            // can walk normally
            map.moveActor(actor, moveToLocation);

            // destroy and replace higher ground with dirt
            Dirt dirt = new Dirt();
            moveToLocation.setGround(dirt);

            // drops a coin ($5)
            Coin coin = new Coin(5, moveToLocation);
            moveToLocation.addItem(coin);

            return null;

        } else {

            //compares success rate to randomly generated float value
            if (rand.nextFloat() < successRate) {
                // reset failed jump counter
                if (actor.hasCapability(Status.FAILED_ONCE)) {
                    actor.removeCapability(Status.FAILED_ONCE);
                }
                if (actor.hasCapability(Status.FAILED_TWICE)) {
                    actor.removeCapability(Status.FAILED_TWICE);
                }

                //if the if-condition is passed then the actor is moved to the required destination
                map.moveActor(actor, moveToLocation);
                return actor + " successfully jumped on " + jumpObject + "(" + moveToLocation.x() + "," + moveToLocation.y() + ")";

            } else {
                // failed jump counter
                if (!actor.hasCapability(Status.FAILED_ONCE) && !actor.hasCapability(Status.FAILED_TWICE)) {
                    // first fail
                    actor.addCapability(Status.FAILED_ONCE);
                } else if (actor.hasCapability(Status.FAILED_ONCE)) {
                    // second fail
                    actor.removeCapability(Status.FAILED_ONCE);
                    actor.addCapability(Status.FAILED_TWICE);
                } else {
                    // third fail
                    actor.removeCapability(Status.FAILED_TWICE);
                    actor.addCapability(Status.BROKEN_LEG);
                }
                //if the jump failed actors health is reduced
                actor.hurt(fallDamage);
                return "Jump failed " + actor + " lost " + fallDamage + " in health";
            }
        }
    }


    /**
     * returns description of this action to be displayed
     * @param actor The actor performing the action.
     * @return
     */
    @Override
    public String menuDescription(Actor actor) {
        if (actor.hasCapability(Status.INVINCIBLE)) {
            return actor + " walks on " + direction + " " + jumpObject;
        }
        return actor + " jumps on " + direction + " " + jumpObject;
    }
}

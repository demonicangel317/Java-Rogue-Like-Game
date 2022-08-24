package game.behaviours;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.MoveActorAction;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.Status;

/**
 * behaviour that implements the flying follow behaviour
 *
 * @author Ravindu Santhush Ratnayake
 */
public class FlyingFollowBehaviour implements Behaviour{
    private final Actor target;

    /**
     * Constructor.
     *
     * @param subject the Actor to follow
     */
    public FlyingFollowBehaviour(Actor subject) {
        this.target = subject;
    }


    /**
     *
     * Calculates the Manhattan distance between the current actor and the target to follow
     * then gets the exit locations of the current location.
     * after that this checks if the distance between the location and the exit is lower than
     * the distance between the target and the actor. if the condition is true we return a move action to that direction
     * even if it is a high ground
     *
     *
     * @param actor the Actor acting
     * @param map the GameMap containing the Actor
     * @return
     */
    @Override
    public Action getAction(Actor actor, GameMap map) {

        //checks if the map contains the actor and the target if any is false returns null
        if(!map.contains(target) || !map.contains(actor))
            return null;

        //gets the location of the current actor
        Location here = map.locationOf(actor);

        //gets the location of the target actor
        Location there = map.locationOf(target);

        //calculate th manhattan distance between actor and the target
        int currentDistance = distance(here, there);

        //iterate through the exits
        for (Exit exit : here.getExits()) {
            Location destination = exit.getDestination();

            //checks if the location doesn't contain an actor
            if (!destination.containsAnActor()) {
                //calculates new distance between here and the new exit location
                if(destination.canActorEnter(actor)){
                    int newDistance = distance(destination, there);
                    //if the new distance is lower than the distance between actor and target then return a move action
                    if (newDistance < currentDistance) {
                        return new MoveActorAction(destination, exit.getName());
                    }
                }else{
                    if(destination.getGround().hasCapability(Status.HIGH_GROUND)){
                        int newDistance = distance(destination, there);
                        //if the new distance is lower than the distance between actor and target then return a move action
                        if (newDistance < currentDistance) {
                            return new MoveActorAction(destination, exit.getName());
                        }
                    }
                }

            }
        }

        return null;
    }

    /**
     * Compute the Manhattan distance between two locations.
     *
     * @param a the first location
     * @param b the first location
     * @return the number of steps between a and b if you only move in the four cardinal directions.
     */
    private int distance(Location a, Location b) {
        return Math.abs(a.x() - b.x()) + Math.abs(a.y() - b.y());
    }
}
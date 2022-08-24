package game.behaviours;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.Status;

import java.util.ArrayList;
import java.util.Random;

/**
 * behaviour that implements the flying wander behaviour
 *
 * @author Ravindu Santhush Ratnayake
 */
public class FlyingWanderBehaviour extends Action implements Behaviour{

    private final Random random = new Random();

    /**
     * returns a MoveAction to wander to a random location, if possible.
     * if no movement is possible, returns null.
     * here the main difference is that flying Koopa will have the ability to wander to high ground
     *
     * @param actor the Actor enacting the behaviour
     * @param map the map that actor is currently on
     * @return an Action, or null if no MoveAction is possible
     */
    @Override
    public Action getAction(Actor actor, GameMap map) {
        ArrayList<Action> actions = new ArrayList<Action>();
        //get the exits of the current location
        for (Exit exit : map.locationOf(actor).getExits()) {
            Location destination = exit.getDestination();
            //checks if the current locaton contains an actor
            if (!destination.containsAnActor()){
                //checks if he actor can enter
                if(destination.canActorEnter(actor)){
                    //adds move action
                    actions.add(exit.getDestination().getMoveAction(actor, "around", exit.getHotKey()));
                }else{ // if the actor cannot enter it checks if the location is a high ground
                    if(destination.getGround().hasCapability(Status.HIGH_GROUND)){
                        //adds move action
                        actions.add(exit.getDestination().getMoveAction(actor, "around", exit.getHotKey()));
                    }
                }

            }
        }
        if (!actions.isEmpty()) {
            return actions.get(random.nextInt(actions.size()));
        }
        else {
            return null;
        }

    }

    /**
     * returns the menu description of this action
     *
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return the string from the menu description functon
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        return menuDescription(actor);
    }

    /**
     * returns a string representing this action
     *
     * @param actor The actor performing the action.
     * @return returns a string
     */
    @Override
    public String menuDescription(Actor actor) {
        return "Raagrh...";
    }
}

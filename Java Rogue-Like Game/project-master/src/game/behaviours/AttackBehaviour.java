package game.behaviours;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.Status;
import game.actions.AttackAction;

/**
 * behaviour that implements the attack action
 *
 * @author Ravindu Santhush Ratnayake
 */
public class AttackBehaviour implements Behaviour {

    /**
     * the target actor in which the attack should be directed against
     */
    private final Actor target;

    /**
     * constructor
     * @param subject the target actor in which the attack should be directed against
     */
    public AttackBehaviour(Actor subject) {this.target = subject; }

    /**
     *
     * checks and returns an attack action if player is present or null otherwise
     *
     * @param actor the Actor acting
     * @param map the GameMap containing the Actor
     * @return returns attack action or null value
     */
    @Override
    public Action getAction(Actor actor, GameMap map) {
        //ArrayList<Action> actions = new ArrayList<Action>();
        //gets all exits from current location
        if(this.target != null){
            for (Exit exit : map.locationOf(actor).getExits()) {
                //get the location of the exit
                Location location = exit.getDestination();

                //checks if the location ahs an actor
                if (location.containsAnActor()) {
                    // if this actor is hostile to this object
                    if(location.getActor().hasCapability(Status.HOSTILE_TO_ENEMY)){

                        //then we retun an attack ation
                        return new AttackAction(target, exit.getName());
                    }
                }
            }
        }

        //returns null if no player is found
        return null;
    }
}

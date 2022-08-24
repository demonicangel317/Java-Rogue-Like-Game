package game.behaviours;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.Status;
import game.actions.AttackAction;
import game.gameitems.Fire;


/**
 * behaviour that implements the attack action and also places a fireball at the location
 *
 * @author Ravindu Santhush Ratnayake
 */
public class AttackFireBehaviour implements Behaviour {

    /**
     * the target actor in which the attack should be directed against
     */
    private final Actor target;

    /**
     * constructor
     * @param subject the target actor in which the attack should be directed against
     */
    public AttackFireBehaviour(Actor subject) {this.target = subject; }

    /**
     *
     * checks and returns an attack action if player is present or null otherwise also
     * this places a fireball at the location where the actor is at
     *
     * @param actor the Actor acting
     * @param map the GameMap containing the Actor
     * @return returns attack action or null value
     */
    @Override
    public Action getAction(Actor actor, GameMap map) {
        //ArrayList<Action> actions = new ArrayList<Action>();
        //gets all exits from current location
        if (this.target != null) {
            for (Exit exit : map.locationOf(actor).getExits()) {
                //get the location of the exit
                Location location = exit.getDestination();

                //checks if the location ahs an actor
                if (location.containsAnActor()) {
                    // if this actor is hostile to this object
                    if (location.getActor().hasCapability(Status.HOSTILE_TO_ENEMY)) {

                        //places the Fireball at the attack location
                        location.addItem(new Fire());
                        //then we return an attack action
                        return new AttackAction(target, exit.getName());
                    }
                }
            }
        }

        //returns null if no player is found
        return null;
    }
}

package game.fountains;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;
import game.actions.FillAction;

/**
 * Class the defines how an actor interacts with the Health Fountain.
 */
public class HealthFountain extends Ground {

    /**
     * Constructor.
     */
    public HealthFountain() {
        super('H');
    }

    /**
     * Returns the allowable actions for the actor when on top or beside the fountain.
     *
     * @param actor     the Actor acting
     * @param location  the current Location
     * @param direction the direction of the Ground from the Actor
     * @return the allowable actions
     */
    @Override
    public ActionList allowableActions(Actor actor, Location location, String direction) {
        // allow actor to fill up bottle if standing atop
        if (location.containsAnActor()) {
            ActionList fountainAction = new ActionList();
            fountainAction.add(new FillAction(this));

            return fountainAction;
        } else {
            return super.allowableActions(actor, location, direction);
        }
    }
}

package game.hostiles;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.GameMap;
import game.Status;
import game.actions.AttackAction;
import game.behaviours.*;

/**
 * Koopa that can fly
 *
 * @author Ravindu Santhush Ratnayake
 */
public class FlyingKoopa extends Koopa {

    /**
     * Constructor for flying koopa
     */
    public FlyingKoopa() {
        super("Flying Koopa", 'F', 150);
    }

    /**
     *
     * returns allowable action for flying koopa
     *
     *this is responsible for replacing the the wander and follow behaviours of flying koopa
     *with FLying wander behaviour and flying Follow behaviour which gives this koopa the ability to
     *move to high grounds as well
     *
     * @param otherActor the Actor that might perform an action.
     * @param direction  String representing the direction of the other Actor
     * @param map        current GameMap
     * @return returns an action list
     */
    @Override
    public ActionList allowableActions(Actor otherActor, String direction, GameMap map) {
        //gets the action list from the super class
        ActionList actions = super.allowableActions(otherActor, direction, map);
        //replaces follow behaviour with flying go==folow behaviour
        this.getBehaviours().put(2, new FlyingFollowBehaviour(otherActor));

        //replaces wander behaviour with flying wander behaviour
        this.getBehaviours().put(3, new FlyingWanderBehaviour());

        //returns the action list
        return actions;

    }

}

package game.hostiles;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actions.DoNothingAction;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.weapons.IntrinsicWeapon;
import game.Status;
import game.actions.AttackAction;

/**
 * Evil plant that doesn't like mario teleporting
 *
 * @author Ravindu Santhush Ratnayake
 */
public class PiranhaPlant extends EnemyType{
    /**
     * Constructor for piranha plant
     */
    public PiranhaPlant(){
        super("Piranha Plant", 'Y', 150);
        this.addCapability(Status.PIRANHA_PLANT);
    }

    /**
     * function that return the intrinsic weapon for Piranha plant
     *
     * @return returns the intrinsic weapon of piranha plant
     */
    @Override
    protected IntrinsicWeapon getIntrinsicWeapon() {
        return new IntrinsicWeapon(90, "chomps");
    }

    /**
     *returns appropriate piranha action
     *
     * function removes wander behavior and follow behavior from piranha and returns attack
     * action if player is near
     *
     * @param actions    collection of possible Actions for this Actor
     * @param lastAction The Action this Actor took last turn. Can do interesting things in conjunction with Action.getNextAction()
     * @param map        the map containing the Actor
     * @param display    the I/O object to which messages may be written
     * @return returns appropriate action
     */
    @Override
    public Action playTurn(ActionList actions, Action lastAction, GameMap map, Display display) {
        //removes wander behaviour and follow behaviour from Piranha Plant
        this.getBehaviours().remove(2);
        this.getBehaviours().remove(3);

        //returns actions from super class
        return this.returnCurrentTurnAction(this, map);

    }
}

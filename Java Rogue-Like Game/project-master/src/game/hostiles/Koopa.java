package game.hostiles;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actions.DoNothingAction;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.weapons.IntrinsicWeapon;
import game.Resettable;
import game.actions.AttackAction;
import game.behaviours.AttackBehaviour;
import game.behaviours.Behaviour;
import game.Status;
import game.behaviours.FollowBehaviour;
import game.behaviours.WanderBehaviour;
import game.consumables.SuperMushroom;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Evil turtle thingy
 *
 * @author Ravindu Santhush Ratnayake
 */
public class Koopa extends EnemyType {

    /**
     * variable that checks if the Koopa is in an dormant state
     */
    boolean isDormant = false;

    /**
     * variable that checks if the koopa is dead
     */
    boolean isDead = false;

    /**
     * variable used to check if mushroom addded
     */
    boolean isMushRoomAdded = false;

    /**
     * Constructor.
     */
    public Koopa() {
        super("Koopa", 'K', 100);
        // register as a resettable instance
    }

    public Koopa(String name, char displayChar, int hitPoints) {
        super(name, displayChar, hitPoints);
        // register as a resettable instance
    }

    /**
     * creates and returns weapon for koopa
     * @return returns the intrinsic weapon
     */
    @Override
    protected IntrinsicWeapon getIntrinsicWeapon() {
        return new IntrinsicWeapon(30, "punches");
    }

    /**
     * returns the current action the player can make onto this enemy
     *
     * @param otherActor the Actor that might perform an action.
     * @param direction  String representing the direction of the other Actor
     * @param map        current GameMap
     * @return list of actions
     * @see Status#HOSTILE_TO_ENEMY
     */
    @Override
    public ActionList allowableActions(Actor otherActor, String direction, GameMap map) {
        ActionList actions = new ActionList();
        // it can be attacked only by the HOSTILE opponent, and this action will not attack the HOSTILE enemy back.
        if (otherActor.hasCapability(Status.HOSTILE_TO_ENEMY)) {
            //makes sure koopa is not dormant
            if (!isDormant) {
                actions.add(new AttackAction(this, direction));
                this.getBehaviours().put(1, new AttackBehaviour(otherActor));
                this.getBehaviours().put(2, new FollowBehaviour(otherActor));
                // if the koopa is dormant and the player has a wrench return attack action
            } else if (isDormant & otherActor.hasCapability(Status.HAS_WRENCH)) {
                actions.add(new AttackAction(this, direction));
            }
        }


        return actions;
    }

    /**
     * checks if Koopa is conscious
     * @return
     */
    @Override
    public boolean isConscious() {
        //checks if health is not zero
        if (super.isConscious()) {
            return true;
        } else { //if health is zero checks if shell is broken
            if (isMushRoomAdded) {
                //if shell is broken retutn true
                return false;
            }
            return true;
        }

    }

    /**
     * reduces health of koopa
     * @param points number of hitpoints to deduct.
     */
    @Override
    public void hurt(int points) {
        //checks if koopa is not dormant
        if (!isDormant) {
            super.hurt(points);
            // if the koopa health is below zero sets display character to D
            if (!super.isConscious()) {
                super.setDisplayChar('D');
                isDormant = true;
                //clears all behaviours from koopa behaviours so that he does nothing
                this.getBehaviours().clear();
            }
            //if koopa is currenly dormant and if attaacked then it dies
        } else if (isDormant) {
            isDead = true;

        }

    }


    /**
     *plays turn for koopa
     * @param actions    collection of possible Actions for this Actor
     * @param lastAction The Action this Actor took last turn. Can do interesting things in conjunction with Action.getNextAction()
     * @param map        the map containing the Actor
     * @param display    the I/O object to which messages may be written
     * @return returns appropriate action to execute
     */
    @Override
    public Action playTurn(ActionList actions, Action lastAction, GameMap map, Display display) {
        if(!isDead){
            return this.returnCurrentTurnAction(this, map);
        }
        else{
            map.locationOf(this).addItem(new SuperMushroom());
            map.removeActor(this);
            isMushRoomAdded = true;

            return new DoNothingAction();
        }

    }
}

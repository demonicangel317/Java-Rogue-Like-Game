package game.hostiles;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actions.DoNothingAction;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.items.DropItemAction;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import edu.monash.fit2099.engine.weapons.IntrinsicWeapon;
import game.Resettable;
import game.Status;
import game.actions.AttackAction;
import game.behaviours.AttackBehaviour;
import game.behaviours.AttackFireBehaviour;
import game.behaviours.FollowBehaviour;
import game.gameitems.EndGameKey;
import java.util.Random;

/**
 * Mad man that who kidnaps princess Peach with handcuff fetish
 *
 * @author Ravindu Santhush Ratnayake
 */
public class Bowser extends EnemyType{


    /**
     * flg that is used to check weather or not Bowser needs to start his behaviours
     */
    boolean startBehaviours = false;

    /**
     * boolean to check if bowser is dead
     */
    boolean isDead = false;

    /**
     * boolean to check if bowser has spawned key
     */
    boolean spawnedKey = false;

    /**
     * map where the browser exists
     */
    GameMap bowserMap;


    /**
     * Constructor
     *
     * @param bowserMap map that the browser is in
     */
    public Bowser(GameMap bowserMap) {

        super("Bowser", 'B', 500);
        this.bowserMap = bowserMap;
    }

    /**
     *
     * Overridden playTurn behaviour
     *
     * This checks if to the Bowser is alive
     * if he is alive we will check if he has encountered the player near him if so we will begin actions
     * else will return DoNothing action
     *
     * @param actions    collection of possible Actions for this Actor
     * @param lastAction The Action this Actor took last turn. Can do interesting things in conjunction with Action.getNextAction()
     * @param map        the map containing the Actor
     * @param display    the I/O object to which messages may be written
     * @return an appropriate action is returned
     */
    @Override
    public Action playTurn(ActionList actions, Action lastAction, GameMap map, Display display) {

        //checks if Bowser is alive
        if(!isDead) {
            //if the bowser has encountered player get the necessary actions
            if (startBehaviours) {
                Action returnAction = this.returnCurrentTurnAction(this, map);
                return returnAction;
            } else {
                //if bowser has not encountered player return nothing
                return new DoNothingAction();
            }
        }else{
            //if Bowser is dead we will spawn the key
            map.locationOf(this).addItem(new EndGameKey());
            //then we will remove Bowser from the map
            map.removeActor(this);
            //finally, return do nothing action
            return new DoNothingAction();
        }


    }

    /**
     * checks if bowser is alive
     *
     * checks if bowser has spawned key and returns false if not returns true
     *
     * @return boolean value that indicates if the Bowser is conscious
     */
    @Override
    public boolean isConscious() {
        //checks if health is not zero
        if (super.isConscious()) {
            return true;
        }else { //if health is zero checks if shell is broken
            if(spawnedKey) {
                //if shell is broken retutn true
                return false;
            }
            return true;
        }

    }

    /**
     * reduces health of Bowser
     *
     * @param points number of hitpoints to deduct.
     */
    @Override
    public void hurt(int points) {
        //checks if bowser is dead

        if(!isDead){
            //if not dead hurt bowser
            super.hurt(points);
            if (!super.isConscious()) {
                //if health is below zero sets is dead to true
                isDead = true;
                //clears all behaviours from bowser
                this.getBehaviours().clear();
            }

        }
    }


    /**
     * Returns the weapons that bowser uses
     * @return returns Bowsers intrinsic weapon(punch)
     */
    @Override
    protected IntrinsicWeapon getIntrinsicWeapon() {
        return new IntrinsicWeapon(80, "punches");
    }

    /**
     *used at every turn to see what actions other actors can perform on bowser
     *
     *
     * @param otherActor the Actor that might be performing attack
     * @param direction  String representing the direction of the other Actor
     * @param map        current GameMap
     * @return returns an action list which contains the appropriate actions
     */
    @Override
    public ActionList allowableActions(Actor otherActor, String direction, GameMap map) {

        //checks to see if boswe is still alive
        if(!isDead){
            //checks to see if the player is encounteres
            if (otherActor.hasCapability(Status.HOSTILE_TO_ENEMY)) {
                //if the player is encountered set the flag value to true so that the bowser will start his behaviours
                startBehaviours = true;
            }
            //gets the list of actions from Bowser super class
            ActionList bowserActions =  super.allowableActions(otherActor, direction, map);
            //replace the bowser attack behaviour with attackFire behaviour
            if (otherActor.hasCapability(Status.HOSTILE_TO_ENEMY)) {
                this.getBehaviours().put(1, new AttackFireBehaviour(otherActor));
            }
            //return bowser actions
            return bowserActions;
        }

        //if bowser is dead return empty action list
        return new ActionList();

    }

    /**
     * performs the reset behaviour which rests Bowser's health and resets his
     * position within the map
     */
    @Override
    public void resetInstance() {
        //checks to see if bowser is conscious
        if(super.isConscious()){
            //gets max hp
            int maxHp = this.getMaxHp();
            //resets max hp
            this.resetMaxHp(maxHp);
            //set this to false so bowser is dormant untill meeting player
            startBehaviours = false;

            //moves bowser back to original position
            if(!bowserMap.at(28,8).containsAnActor()){
                this.bowserMap.moveActor(this, bowserMap.at(28, 8));
            }else{ // if an actor is blocking the area
                Actor actor = this.bowserMap.at(28, 8).getActor();
                //checks if the actor we are about to remove is not the player
                if(!actor.hasCapability(Status.HOSTILE_TO_ENEMY)){
                    //removes the actor blocking bowser
                    bowserMap.removeActor(actor);
                    this.bowserMap.moveActor(this, bowserMap.at(28, 8));
                }

            }


        }



    }



}

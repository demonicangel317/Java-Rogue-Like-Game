package game;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actions.DoNothingAction;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.GameMap;
import game.actions.EndGameAction;

/**
 * Princess that Mario is trying to save
 *
 * @author Ravindu Santhush Ratnayake
 */
public class Peach extends Actor {

    /**
     * Constructor for Peach
     *
     */
    public Peach() {
        super("Peach", 'P', 100);

        // giving peach invincibility, so they can't be killed by a player
        this.addCapability(Status.INVINCIBLE);
    }

    /**
     * returns appropriate action list for peach
     *
     * checks if the player has the key in his inventory
     * if he has the key we return an action list with the endgame action
     * else we return an empty action list
     *
     * @param otherActor the Actor that might be performing attack
     * @param direction  String representing the direction of the other Actor
     * @param map        current GameMap
     * @return
     */
    @Override
    public ActionList allowableActions(Actor otherActor, String direction, GameMap map) {

        //creates an action list
        ActionList peachActionList = new ActionList();
        //checks if the player contains the end game key
        if(otherActor.hasCapability(Status.END_GAME_KEY)){
            //appends the end game action to the action list
            peachActionList.add(new EndGameAction(otherActor));
        }
        //returns the action list
        return peachActionList;
    }

    /**
     * plays princess Peach's turn
     *
     * always returns a do nothing action as princess peach is static
     *
     * @param actions    collection of possible Actions for this Actor
     * @param lastAction The Action this Actor took last turn. Can do interesting things in conjunction with Action.getNextAction()
     * @param map        the map containing the Actor
     * @param display    the I/O object to which messages may be written
     * @return returns a doNothing action
     */
    @Override
    public Action playTurn(ActionList actions, Action lastAction, GameMap map, Display display) {
        return new DoNothingAction();
    }
}

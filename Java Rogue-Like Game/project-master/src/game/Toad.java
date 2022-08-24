package game;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actions.DoNothingAction;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.GameMap;
import game.actions.TradeAction;
import game.consumables.PowerStar;
import game.consumables.SuperMushroom;
import game.gameitems.Wrench;

/**
 * Class the creates the friendly actor Toad
 */
public class Toad extends Actor {

    /**
     * Constructor
     */
    public Toad() {
        super("Toad", 'O', 10);
        // giving toad invincibility, so they can't be killed by a player
        this.addCapability(Status.INVINCIBLE);
    }

    /**
     * Defines the allowable actions for Toad when in the vicinity of the player.
     *
     * @param otherActor the Actor that might be engaged
     * @param direction  String representing the direction of the other Actor
     * @param map        current GameMap
     * @return the allowable actions.
     */
    @Override
    public ActionList allowableActions(Actor otherActor, String direction, GameMap map) {
        ActionList actions = new ActionList();
        actions.add(new TradeAction(otherActor, new Wrench()));
        actions.add(new TradeAction(otherActor, new SuperMushroom()));
        actions.add(new TradeAction(otherActor, new PowerStar()));

        return actions;
    }

    /**
     * Defines what Toad does for each turn of the game.
     *
     * @param actions    collection of possible Actions for this Actor
     * @param lastAction The Action this Actor took last turn. Can do interesting things in conjunction with Action.getNextAction()
     * @param map        the map containing the Actor
     * @param display    the I/O object to which messages may be written
     * @return null;
     */
    @Override
    public Action playTurn(ActionList actions, Action lastAction, GameMap map, Display display) {
        return new DoNothingAction();
    }

    /**
     * Defines how Toad is displayed in the game.
     *
     * @return the predetermined display character.
     */
    @Override
    public char getDisplayChar() {
        return super.getDisplayChar();
    }
}

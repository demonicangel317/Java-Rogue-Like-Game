package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import game.ResetManager;

/**
 * A class that implements the feature to reset the game
 */
public class ResetAction extends Action {
    // attributes
    /**
     * the instance of the ResetManager with a list of Resettable
     */
    private ResetManager resetManager;

    /**
     * Constructor
     */
    public ResetAction() {
        this.resetManager = ResetManager.getInstance();
    }

    /**
     * Executes the action.
     *
     * @param actor The actor performing the action.
     * @param map   The map the actor is on.
     * @return the console output.
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        resetManager.run();
        return "Game has been reset.";
    }

    /**
     * Menu description of the action to be performed.
     *
     * @param actor The actor performing the action.
     * @return the menu description.
     */
    @Override
    public String menuDescription(Actor actor) {
        return "Reset game.";
    }

    /**
     * Defines the hotkey used to perform the action.
     *
     * @return the predetermined hotkey.
     */
    @Override
    public String hotkey() {
        return "r";
    }
}

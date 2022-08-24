package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;

/**
 * Action used to end the game After unlocking Princess Peach's Handcuffs
 */
public class EndGameAction extends Action {

    /**
     * The player
     */
    Actor player;

    /**
     * End Game Message To Display after finishing the game
     */
    String endGameMessage = "" +
            "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~" +
            "\n\n\n" +
            "                Mario Saved Princess Peach                     " +
            "\n\n\n" +
            "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~" ;


    /**
     * Constructor.
     *
     * @param player the player actor object
     */
    public EndGameAction(Actor player){
        this.player = player;
    }

    /**
     *
     * removes player from the map which will end the game
     * @param actor the player too be removed
     * @param map the GameMap containing the player
     * @return returns the end game message
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        map.removeActor(player);
        return endGameMessage;
    }

    /**
     *
     * @param actor The actor performing the action.
     * @return the description of the action to be performed
     */
    @Override
    public String menuDescription(Actor actor) {
        return "Unlock Princess Peach's Bedroom Handcuffs ;)";
    }
}

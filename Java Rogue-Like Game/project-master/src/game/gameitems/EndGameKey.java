package game.gameitems;

import edu.monash.fit2099.engine.items.Item;
import game.Status;

/**
 * End Game Key used to unlock princess peach's handcuffs
 *
 * @author Ravindu Santhush Ratnayake
 */
public class EndGameKey extends Item {

    /**
     * Constructor
     */
    public EndGameKey() {
        super("End Game Key", 'k', true);
        //adds capabilty end game key
        this.addCapability(Status.END_GAME_KEY);
    }
}

package game.consumables;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import game.Bottleable;
import game.Consumable;
import game.Status;
import game.gameitems.Bottle;

/**
 * Class that defines how an actor interacts with the water retrieved from the power fountain.
 */
public class PowerWater extends Item implements Consumable, Bottleable {
    /***
     * Constructor.
     */
    public PowerWater() {
        // ; is chosen as displayChar because it's not used elsewhere and won't be seen on the ground
        super("Power Water", ';', true);
    }

    /**
     * Picks up and adds this power water instance into the bottle.
     */
    @Override
    public void pickUpInstance() {
        Bottleable.super.pickUpInstance();
    }

    /**
     * Defines what the effects of consuming the power water are.
     *
     * @param actor the actor who has consumed the power water.
     * @return the console output.
     */
    @Override
    public String consume(Actor actor) {
        // remove from bottle
        Bottle.getInstance().drinkWater();

        // increases the drinker's base/intrinsic attack damage by 15
        actor.addCapability(Status.POWER_UP);

        // description
        return "Mario has drank water from the Power Fountain. (Intrinsic damage +15)";
    }
}

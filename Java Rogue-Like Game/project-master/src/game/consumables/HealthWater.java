package game.consumables;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import game.Bottleable;
import game.Consumable;
import game.gameitems.Bottle;

/**
 * Class that defines how an actor can interact with the water retrieved from the health fountain.
 */
public class HealthWater extends Item implements Consumable, Bottleable {
    /***
     * Constructor.
     */
    public HealthWater() {
        // ; is chosen as displayChar because it's not used elsewhere and won't be seen on the ground
        super("Health Water", ';', true);
    }

    /**
     * Picks up and adds this health water instance into the bottle.
     */
    @Override
    public void pickUpInstance() {
        Bottleable.super.pickUpInstance();
    }

    /**
     * Defines what the effects of consuming the health water are.
     *
     * @param actor the actor carrying out the action
     * @return console output
     */
    @Override
    public String consume(Actor actor) {
        // remove from bottle
        Bottle.getInstance().drinkWater();

        // increase the drinker's hit points/healing by 50 hit points
        actor.heal(50);

        // description
        return "Mario has drank water from the Health Fountain. (HP +50)";
    }
}

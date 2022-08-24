package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.GameMap;
import game.Bottleable;
import game.consumables.HealthWater;
import game.consumables.PowerStar;
import game.consumables.PowerWater;
import game.consumables.SuperMushroom;
import game.gameitems.Bottle;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that implements the "consume item" action of the player
 */
public class ConsumeAction extends Action {
    // attributes
    /**
     * the actor that consumes the item
     */
    private Actor actor;
    /**
     * the item that is being consumed
     */
    private Item item;

    /**
     * Constructor
     *
     * @param actor is the actor who consumes the item
     * @param item  is the item about to be consumed
     */
    public ConsumeAction(Actor actor, Item item) {
        this.actor = actor;
        this.item = item;
    }

    /**
     * Executes the action.
     *
     * @param actor The actor performing the action.
     * @param map   The map the actor is on.
     * @return console output.
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        String result = null;

        if (item instanceof SuperMushroom) {
            // call consume method from SuperMushroom
            result = ((SuperMushroom) item).consume(actor);
        } else if (item instanceof PowerStar) {
            // call consume method from PowerStar
            result = ((PowerStar) item).consume(actor);
        } else if (item instanceof HealthWater) {
            // call consume method from HealthWater
            result = ((HealthWater) item).consume(actor);
        } else if (item instanceof PowerWater) {
            // call consume method from PowerWater
            result = ((PowerWater) item).consume(actor);
        }
        return result;
    }

    /**
     * Menu description of the action to be performed.
     *
     * @param actor The actor performing the action.
     * @return the menu description.
     */
    @Override
    public String menuDescription(Actor actor) {
        List<Item> inventory = actor.getInventory();
        int count = 0;

        if (item instanceof SuperMushroom) {
            for (Item value : inventory) {
                if (value instanceof SuperMushroom) {
                    count += 1;
                }
            }
            if (count > 0) {
                return "Mario consumes Super Mushroom. " + count + " Super Mushroom(s) left in inventory.";
            }
        } else if (item instanceof PowerStar) {
            for (Item value : inventory) {
                if (value instanceof PowerStar) {
                    value.tick(null, actor);
                    count += 1;
                }
            }
            if (count > 0) {
                return "Mario consumes Power Star. " + count + " Power Star(s) left in inventory. " + ((PowerStar) item).remainingTurn() + " turns remaining.";
            }
        } else if (item instanceof Bottleable) {
            StringBuilder bottleContent = new StringBuilder("[");

            Bottle bottle = Bottle.getInstance();
            ArrayList<String> water = bottle.iter();

            for (int i = water.size() - 1; i >= 0; i--) {
                if (i > 0) {
                    bottleContent.append(water.get(i)).append(", ");
                } else {
                    bottleContent.append(water.get(i));
                }
            }

            bottleContent.append("]");

            return "Mario consumes Bottle " + bottleContent;
        }

        return null;
    }
}

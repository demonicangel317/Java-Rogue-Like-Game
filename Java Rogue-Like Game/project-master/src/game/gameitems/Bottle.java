package game.gameitems;

import edu.monash.fit2099.engine.items.Item;
import game.Bottleable;
import game.consumables.HealthWater;
import game.consumables.PowerWater;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Stack;

/**
 * Class that defines the bottle that holds water instances picked up from the fountains.
 */
public class Bottle {
    /**
     * A list of fountain water that can be stored in a bottle
     */
    private Stack<Bottleable> waterList;

    /**
     * A singleton bottle instance
     */
    private static Bottle instance;

    /**
     * Get the singleton instance of bottle
     *
     * @return Bottle singleton instance
     */
    public static Bottle getInstance() {
        if (instance == null) {
            instance = new Bottle();
        }
        return instance;
    }

    /**
     * Constructor
     */
    private Bottle() {
        waterList = new Stack<Bottleable>();
    }

    /**
     * Method to read how many water instances are stored in the bottle instance
     *
     * @return the number of water is stored
     */
    public int size() {
        return waterList.size();
    }

    /**
     * Method to return the top element in the water list
     *
     * @return the top water to be consumed
     */
    public Item peek() {
        Bottleable water = waterList.peek();
        if (water instanceof HealthWater) {
            return ((HealthWater) water);
        } else if (water instanceof PowerWater) {
            return ((PowerWater) water);
        }
        return null;
    }

    /**
     * Method to iterate through the water instances in the bottle instance
     *
     * @return the name of the water in the bottle
     */
    public ArrayList<String> iter() {
        Iterator<Bottleable> water = this.waterList.iterator();

        ArrayList<String> name = new ArrayList<String>();

        while (water.hasNext()) {
            name.add(water.next().toString());
        }

        return name;
    }

    /**
     * Add Bottleable instances to the list
     *
     * @param water Bottleable instance
     */
    public void storeWater(Bottleable water) {
        this.waterList.push(water);
    }

    /**
     * Remove the top Bottleable instance from the list
     */
    public void drinkWater() {
        this.waterList.pop();
    }
}

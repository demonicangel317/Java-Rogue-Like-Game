package game.gameitems;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.Location;

/**
 * Dangerous Fire Object that damages actors
 *
 * @author Ravindu Santhush Ratnayake
 */
public class Fire extends Item {

    /**
     * tick counter used to check how many times tick function occurs
     */
    private int tickCounter = 0;

    /***
     * Constructor.
     *
     */
    public Fire() {
        super("Fire", 'F', false);
    }

    /**
     * tick function that will hurt any actor on the same location as this object
     * and removes this item from the map after three iterations
     *
     * @param currentLocation The location of the ground on which we lie.
     */
    @Override
    public void tick(Location currentLocation) {


        //checks if a actor is in the current location
        if(currentLocation.containsAnActor()){
            //get the actor in the location
            Actor locationActor = currentLocation.getActor();
            //hurt the actor
            locationActor.hurt(20);
            //print out message
            System.out.println("Fire hurts " + locationActor.toString());

        }

        //checks if the tick counter is greater than or equal to 3 and removes this item
        if(tickCounter >= 3){
            currentLocation.removeItem(this);
        }

        //increment tick counter
        this.tickCounter += 1;
    }

}

package game.groundobjects;

import edu.monash.fit2099.engine.positions.Location;
import game.gameitems.Coin;

import java.util.Random;

/**
 *a sprout object that has grown which matures into a tree and places a coin at 10 perent chance
 *
 * @author Ravindu Santhush Ratnayake
 */
public class Sapling extends Tree {

    /**
     * Constructor for the sapling object
     * creates tree object with display char:'t', name: ''sapling'', jumpSuccessRate: 80 percent, fallDamage: 20 hitpoints
     */
    public Sapling() {
        super('t', "Sapling", 0.8, 20);
    }

    /**
     * Overrides the tick function of the ground object
     * This is responsible for replacing the current ground with the Mature tree object when 10 turns are completed
     * This also spawns a coin at the current location with a valuation of $20 every turn at a 10 percent chance
     * @param location The location of the Ground
     */
    @Override
    public void tick(Location location){
        this.setLocation(location);
        //increment the tick counter and see if to spawn Mature Tree at the 10th turn
        if (incrementTick() == 10) {
            this.setGroundType(new Mature());
        }else {
            //Spawning Coin at 10% chance
            Random rand = new Random();
            if (rand.nextFloat() > 0.9) {
                this.addItemHere(new Coin(20, this.getLocation()));
            }
        }
    }

}



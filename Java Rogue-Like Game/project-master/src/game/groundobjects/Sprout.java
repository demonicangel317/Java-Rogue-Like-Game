package game.groundobjects;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.Status;
import game.actions.JumpAction;
import game.hostiles.FlyingKoopa;
import game.hostiles.Goomba;

import java.util.Random;
/**
 *a baby tree which matures into a sapling and while growing spawns Goomba's
 *
 * @author Ravindu Santhush Ratnayake
 */

public class Sprout extends Tree {

    /**
     * Constructor for the Sprout tree object
     * creates tree object with display char:'+', name: ''Sprout'', jumpSuccessRate: 90 percent, fallDamage: 10 hitpoints
     */
    public Sprout() {
        super('+', "Sprout", 0.9, 10);
    }

    /**
     * Overrides the tick function of the ground object
     * This is responsible for replacing the current ground with the Sapling tree object when 10 turns are completed
     * This also spawns Goomba at the current location at a 10 percent chance
     * @param location The location of the Ground
     */
    @Override
    public void tick(Location location) {

        this.setLocation(location);
        //increment the tick counter and see if to spawn sprout
        if (incrementTick() == 10) {
            setGroundType(new Sapling());
        } else {
            //Spawning Goomba at 10% chance
            Random rand = new Random();
            if (rand.nextFloat() > 0.9) {
                this.addActorHere(new Goomba());
            }
        }
    }


}

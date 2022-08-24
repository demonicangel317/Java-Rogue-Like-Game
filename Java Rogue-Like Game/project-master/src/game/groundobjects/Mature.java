package game.groundobjects;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;
import game.hostiles.FlyingKoopa;
import game.hostiles.Goomba;
import game.hostiles.Koopa;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *A fully grown Tree object which has a cance to die and convert to dirt but will also spwan dangeroud koompas before it passes away
 *
 * @author Ravindu Santhush Ratnayake
 */
public class Mature extends Tree {

    /**
     * Constructor for the Mature tree object
     * creates tree object with display char:'T', name: ''Mature'', jumpSuccessRate: 70 percent, fallDamage: 30 hitpoints
     */
    public Mature(){
        super('T', "Mature", 0.7, 30);
    }

    /**
     * Overrides the tick function of the ground object
     * This is responsible for turning the current ground into dirt ata 20 percent chance
     * This also spawns Koopa at the current location at a 15 percent chance
     * also creates new sprouts if fertile grounds are available every 5 turns
     * @param location The location where this tree is located
     */
    @Override//incomplete TO-DO: randomly select dirt to plant new sprout
    public void tick(Location location) {
        //Location currentLocation = location;
        this.setLocation(location);
        Random rand = new Random();

        //increment the tick counter and see if to spawn sprout every 5 turns
        if (incrementTick() % 5 == 0) {
            createNewSprouts();
        }

        //spawning koopa or flying koopa at 15 percent chance
        if (rand.nextFloat() >= 0.85) {
            //Req 2
            if(rand.nextFloat() > 0.50){
                this.addActorHere(new Koopa());
            }else{
                this.addActorHere(new FlyingKoopa());
            }

        }

        //turns into Dirt
        if (rand.nextFloat() > 0.8) {
            this.setGroundType(new Dirt());
        }


    }

    /**
     * This function randomly creates sprouts around if a fertile ground is available
     */
    public void createNewSprouts(){
        //get all the exists at the loaction of this mature tree
        List<Exit> exits = this.getLocation().getExits();

        //create a list to store fertile grounds
        List<Location> fertileGroundLocations = new ArrayList<Location>();

        //iterates through the exits
        for(Exit exit : exits){

            //gets the ground of the exit
            Ground exitGround = exit.getDestination().getGround();

            //checks if the ground is an instance of the dirt object as it is the only fertile ground currently
            if (exitGround instanceof Dirt) {
                fertileGroundLocations.add(exit.getDestination());
            }
        }

        //creates a rand variable
        Random rand = new Random();

        //if there are any fertile grounds randomly place a new sprout in one of the fertile grounds
        if(fertileGroundLocations.size() > 0){
            fertileGroundLocations.get(rand.nextInt(fertileGroundLocations.size())).setGround(new Sprout());
        }

    }

}



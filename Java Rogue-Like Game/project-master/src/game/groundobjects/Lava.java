package game.groundobjects;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;
import game.Status;

/**
 * Dangerous Lava ground that damages the player if he enters
 *
 * @author Ravindu Santhush Ratnayake
 */
public class Lava extends Ground {


    /**
     * Constructor
     */
    public Lava(){
        super('L');
    }

    /**
     * Overridden tick function
     * @param location The location of the Ground
     */
    @Override
    public void tick(Location location){
        //checks if an actor is present
        if(location.containsAnActor()){
            //gets the actor present within this location
            Actor locationActor = location.getActor();
            //deals 15 points of damage
            locationActor.hurt(15);
            // REQ4: Burned effect (-50 max HP)
            locationActor.increaseMaxHp(-50);
            //prints out damage message
            System.out.println("Lava damages " + locationActor.toString());
        }

    }

    /**
     * checks if the actor ahs a certain capability and allows him to enter
     *
     * @param actor the Actor to check
     * @return
     */
    @Override
    public boolean canActorEnter(Actor actor){
        if(actor.hasCapability(Status.HOSTILE_TO_ENEMY)){
            return true;
        }
        return false;
    }

}

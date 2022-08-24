package game.groundobjects;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actions.MoveActorAction;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;
import game.Resettable;
import game.Status;
import game.actions.JumpAction;
import game.actions.TeleportAction;
import game.hostiles.PiranhaPlant;

import java.util.List;

/**
 * Teleportation Warp pipe
 *
 * @author Ravindu Santhush Ratnayake
 */
public class WarpPipe extends Ground implements Resettable{

    /**
     * check used to see if we have to add a piranha plant
     */
    private boolean addPiranhaPlant = false;

    /**
     * location to teleport to
     */
    private Location teleportLocation;

    /**
     * Name of the location to teleport to
     */
    private String teleportLocationName;

    /**
     * current location of the warp pipe
     */
    Location currentLocation;


    /**
     *
     * Constructor
     *
     * @param teleportLocation location to teleport to
     * @param teleportLocationName name of the location to teleport to
     * @param currentLocation current location of the warp pipe
     * @param addPiranhaPlant check to see if we have to add a piranha plant
     */
    public WarpPipe(Location teleportLocation,String teleportLocationName, Location currentLocation, boolean addPiranhaPlant){
        super( 'C');
        this.teleportLocation = teleportLocation;
        this.teleportLocationName = teleportLocationName;
        this.currentLocation = currentLocation;
        this.registerInstance();
        this.addPiranhaPlant = addPiranhaPlant;
    }

    /**
     * doesnt allow actor to walk to this location
     *
     * @param actor the Actor to check
     * @return false boolean
     */
    @Override
    public boolean canActorEnter(Actor actor) {
        return false;
    }


    /**
     * defines how other actors can interact with this warp pipe
     *
     * It first checks if an actor has the jump capability when the actor is nar this warp pipe
     * then provides the jump action
     * It also checks if this location contains the player
     * then gives the player the teleport action
     *
     * @param actor the Actor acting
     * @param location the current Location
     * @param direction the direction of the Ground from the Actor
     * @return returns an action list of allowable actions
     */
    @Override
    public ActionList allowableActions(Actor actor, Location location, String direction){

        //the action list to append to
        ActionList warpPipeActionList = new ActionList();

        //checks if the location doesn't contain an actor and also if the other actor has capability to jump
        if(actor.hasCapability(Status.CAN_JUMP) && !location.containsAnActor()){

                //adds jump action
                warpPipeActionList.add(new JumpAction(location, 1.0, 0, "Warp Pipe", direction));
            }

        //checks if the location contains an actor
        if(location.containsAnActor()){
            //checks if the actor is player
            if(actor.hasCapability(Status.HOSTILE_TO_ENEMY)){
                //provides the teleport capability
                warpPipeActionList.add(new TeleportAction(this.teleportLocation, teleportLocationName, location));

            }
        }
        //if actor is player return warp pipe actions
        if (actor.hasCapability(Status.HOSTILE_TO_ENEMY)){
            return warpPipeActionList;
        }else{ //if not player return null action list2
            return new ActionList();
        }

    }

    /**
     * Overidden tick function that checks if we have to add a piranha plant     *
     *
     * @param location The location of the Ground
     */
    @Override
    public void tick(Location location){
        //checks whether to add a piranha blant
        if(addPiranhaPlant){
            //this.addAction(new JumpAction(location, 1.0, 0, "Warp Pipe", ));
            //checks if an actor is present in this location
            if(!location.containsAnActor()){
                // if false spawns a piranha plant
                location.addActor(new PiranhaPlant());
                //sets the flag to false
                addPiranhaPlant = false;
            }


        }

    }

    /**
     * RestInstance
     *
     * if a piranha plant exits in the location when reset is performed then increases its mx hp by 50
     * else adds another piranha plant
     */
    @Override
    public void resetInstance() {
        //checks if the location contains an actor
        if(currentLocation.containsAnActor()){
            //gets the actor in this location
            Actor currentLocationActor = currentLocation.getActor();
            if(currentLocationActor.hasCapability(Status.PIRANHA_PLANT)){
                //if this actor is a piranha plant increase hp by 5-
                currentLocationActor.resetMaxHp(200);
            }
        }
        else{
            //if no piranha plant is present spawan a new Piranha Plant
            if(!currentLocation.containsAnActor()){
                currentLocation.addActor(new PiranhaPlant());
            }

        }
    }

    /**
     * registers this with the registerInstance
     */
    @Override
    public void registerInstance() {
        Resettable.super.registerInstance();
    }

}

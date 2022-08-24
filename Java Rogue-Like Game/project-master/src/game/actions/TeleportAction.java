package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.Status;
import game.groundobjects.WarpPipe;

/**
 * Action that teleports an actor
 *
 * @author Ravindu Santhush Ratnayake
 */
public class TeleportAction extends Action {

    /**
     * Location to teleport to
     */
    private Location moveToLocation;
    /**
     * One of the 8-d navigation
     */
    private String mapName;

    /**
     * location that we need to teleport back to
     */
    private Location teleportBackLocation;


    /**
     * checks if the actor at the location contains an actor and removes it
     * then it will change the ground type at the teleport location to a warp pipe with switched
     * teleport forward and teleport back parameters
     * then it will move the actor to the desired location
     *
     *
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return a string containing the description of this teleport action
     */
    @Override
    public String execute(Actor actor, GameMap map) {


        //checks if the location that we need to move to contains an actor
        if(moveToLocation.containsAnActor()){
            //gets the actor at the location
            Actor locationActor = moveToLocation.getActor();

            //if there is an actor in the location we remove the actor (Piranha plant or Flying Koopa)
            moveToLocation.map().removeActor(locationActor);
            map.removeActor(locationActor);

        }

        //change the ground to warp pipe again here the moveToLocation and TeleportBack location are switched
        //to enable us to teleport back to the previous location
        moveToLocation.setGround(new WarpPipe(teleportBackLocation,"Lava Map", moveToLocation, false));

        //move the actor to the desired location
        map.moveActor(actor, moveToLocation);


        //menu description of the action performed
        return menuDescription(actor);
    }

    /**
     *
     * @param actor The actor performing the action.
     * @return the description of the action to be performed
     */
    @Override
    public String menuDescription(Actor actor) {
        return "Teleport "+ actor.toString()+ " to " + mapName;
    }

    /**
     * Constructor
     *
     * @param moveToLocation the location to teleport the actor
     * @param mapName the name of the map to be teleported to
     * @param teleportBackLocation the location to be teleported back to after teleporting to the location
     */
    public TeleportAction(Location moveToLocation, String mapName, Location teleportBackLocation){
        this.moveToLocation = moveToLocation;
        this.teleportBackLocation = teleportBackLocation;
        this.mapName = mapName;

    }
}

package game.groundobjects;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;
import game.Resettable;
import game.Status;
import game.actions.JumpAction;

import java.util.Random;


/**
 *An entity similar which simulates real trees and has a growth cycle
 *
 * Please see the {@link edu.monash.fit2099.engine.positions.Ground} class for true identity
 *
 * @author Ravindu Santhush Ratnayake
 */
public abstract class Tree extends Ground implements Resettable {
    /**
     * the location that the tree is in
     */
    private Location location;

    /**
     * the tick counter which indicates how many times the tick method has been called
     */
    private int tickCounter = 0;

    /**
     * the name of the growth stage of the tree
     */
    private final String treeType;

    /**
     * the fall damage given if a jump is to fail
     */
    private final int jumpFallDamage;

    /**
     * the success rate for a jump to succeed
     */
    private final double jumpSuccessRate;

    /**
     *
     */
    private boolean isGroundSet = false;

    /**
     * Constructor.
     *
     * @param displayChar the display character of the tree
     * @param treeType the name of the growth stage that the tree is in
     * @param jumpSuccessRate the success rate for this specific tree when performing a jump
     * @param jumpFallDamage the amount of health that should be deducted from the actor who failed the jump
     */
    public Tree(char displayChar, String treeType, double jumpSuccessRate, int jumpFallDamage) {
        super(displayChar);
        this.treeType = treeType;
        this.jumpFallDamage = jumpFallDamage;
        this.jumpSuccessRate = jumpSuccessRate;
        this.addCapability(Status.HIGH_GROUND);
        // register as a resettable instancee
        registerInstance();
    }

    /**
     * Increments the tickCounter variable and returns it     *
     * @return the current value of the tick counter after being incremented
     */
    public int incrementTick(){
        tickCounter++;
        return tickCounter;
    }

    /**
     * Sets the location of the tree object
     * @param location the location object that indicates the current location within the gamemap
     */
    public void setLocation(Location location) {

        this.location = location;
        this.isGroundSet = true;
    }

    /**
     * Sets the ground type of the location of the tree object     *
     * @param groundType the next ground type that should set in the current location
     */
    public void setGroundType(Ground groundType){
        this.location.setGround(groundType);
    }

    /**
     * Checks if an actor is in the current location if there is no actor the actor that is entered is placed in the location
     * @param actor the actor to be placed in this location
     */
    public void addActorHere(Actor actor) {
        boolean containsActor = this.location.containsAnActor();
        if (!containsActor) {
            this.location.addActor(actor);
        }

    }

    /**
     * Adds an item to the current location
     * @param item the item object to be added to the current location
     */
    public void addItemHere(Item item){this.location.addItem(item);}


    /**
     * returns the current location of the tree
     * @return
     */
    public Location getLocation() {
        return location;
    }

    @Override
    public void resetInstance() {
        // 50% chance to be converted back to dirt
        Random rand = new Random();
        if (rand.nextFloat() <= 0.5) {
            Dirt dirt = new Dirt();
            if (isGroundSet) {
                this.location.setGround(dirt);
            }
        }
    }

    @Override
    public void registerInstance() {
        Resettable.super.registerInstance();
    }

    /**
     * returns false so that actors can only jump to the location but not walk here
     * @param actor unused actor object
     * @return returns false value always
     */
    @Override
    public boolean canActorEnter(Actor actor){
        return false;
    }

    /**
     * returns an action list that contains the jump action to jump to the location depending on whether the actor has a jump capability
     * @param actor the Actor to be checked if a jump capability is present
     * @param moveToLocation the location the actor should jump to
     * @param direction the direction of the location from the Actor
     * @return returns allowable actions
     */
    @Override
    public ActionList allowableActions(Actor actor, Location moveToLocation, String direction){
        //creates action list
        ActionList treeActionList = new ActionList();

        //checks if the actor given can jump
        if(!this.hasCapability(Status.CAN_JUMP)){
            //checks if an actor is already present in the location
            if(!moveToLocation.containsAnActor()){
                //adds the jump action to jump onto this location into the ation list
                treeActionList.add(new JumpAction(moveToLocation,this.jumpSuccessRate, this.jumpFallDamage, this.treeType, direction));
            }

        }
        //returns the action list
        return treeActionList;

    }
}

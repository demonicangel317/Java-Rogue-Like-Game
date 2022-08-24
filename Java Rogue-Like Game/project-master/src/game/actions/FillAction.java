package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Ground;
import game.consumables.HealthWater;
import game.consumables.PowerWater;
import game.fountains.HealthFountain;
import game.fountains.PowerFountain;

/**
 * Class that defines what happens when an actor fills their bottle with water from the fountains
 */
public class FillAction extends Action {
    // attributes
    /**
     * denotes which fountain the actor is standing atop of
     */
    private Ground ground;

    /**
     * Constructor.
     *
     * @param ground the type of ground the actor is standing atop of
     */
    public FillAction(Ground ground) {
        this.ground = ground;
    }

    /**
     * Executes the action.
     *
     * @param actor The actor performing the action.
     * @param map   The map the actor is on.
     * @return the console output.
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        String description = null;

        if (this.ground instanceof HealthFountain) {
            HealthWater healthWater = new HealthWater();
            healthWater.pickUpInstance();

            description = "Mario filled up bottle with water from the Health Fountain.";
        } else if (this.ground instanceof PowerFountain) {
            PowerWater powerWater = new PowerWater();
            powerWater.pickUpInstance();

            description = "Mario filled up bottle with water from the Power Fountain.";
        }

        return description;
    }

    /**
     * Menu description of the action to be performed.
     *
     * @param actor The actor performing the action.
     * @return the menu description.
     */
    @Override
    public String menuDescription(Actor actor) {
        String description = null;

        if (this.ground instanceof HealthFountain) {
            description = "Fill up bottle with water from Health Fountain.";
        } else if (this.ground instanceof PowerFountain) {
            description = "Fill up bottle with water from Power Fountain.";
        }

        return description;
    }
}

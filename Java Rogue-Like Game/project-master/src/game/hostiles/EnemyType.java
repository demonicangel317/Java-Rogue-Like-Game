package game.hostiles;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actions.DoNothingAction;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import game.Resettable;
import game.Status;
import game.actions.AttackAction;
import game.behaviours.AttackBehaviour;
import game.behaviours.Behaviour;
import game.behaviours.FollowBehaviour;
import game.behaviours.WanderBehaviour;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Abstract enemy class type
 *
 * @author Ravindu Santhush Ratnayake
 */
public abstract class EnemyType extends Actor implements Resettable {

    /**
     * hashmap used to contain behaviours with priority key values
     */
    private final Map<Integer, Behaviour> behaviours = new HashMap<>();


    /**
     * Constructor
     * @param name name of enemy
     * @param displayChar display character of enemy
     * @param hitPoints health of the enemy
     */
    public EnemyType(String name, char displayChar, int hitPoints){
        super(name, displayChar, hitPoints);

        this.addCapability(Status.HOSTILE_TO_PLAYER);

        this.behaviours.put(1, new AttackBehaviour(null));
        this.behaviours.put(2, new FollowBehaviour(null));
        this.behaviours.put(3, new WanderBehaviour());

        // register as a resettable instance
        registerInstance();
    }

    /**
     *
     * @param actor actor to get behaiours from
     * @param map current game map
     * @return returns an available action
     */
    public Action returnCurrentTurnAction(Actor actor, GameMap map){
        List<Integer> prioritySorted = behaviours.keySet().stream().sorted().toList();
        for(int priority : prioritySorted){
            Action action = behaviours.get(priority).getAction(actor, map);
            if (action != null)
                return action;
        }
        return new DoNothingAction();
    }

    /**
     * functions that returns the hashmap of behaviours
     * @return the hashmap of behaviours
     */
    public Map<Integer, Behaviour> getBehaviours() {
        return behaviours;
    }


    /**
     *returns the current action the player can make onto this enemy
     *
     * @param otherActor the Actor that might be performing attack
     * @param direction  String representing the direction of the other Actor
     * @param map        current GameMap
     * @return returns appropriate action to take
     */
    @Override
    public ActionList allowableActions(Actor otherActor, String direction, GameMap map) {
        ActionList actions = new ActionList();
        // it can be attacked only by the HOSTILE opponent, and this action will not attack the HOSTILE enemy back.
        if(otherActor.hasCapability(Status.HOSTILE_TO_ENEMY)) {
            actions.add(new AttackAction(this,direction));
            this.behaviours.put(1, new AttackBehaviour(otherActor));
            this.behaviours.put(2, new FollowBehaviour(otherActor));

        }
        return actions;

    }

    /**
     * kills the enemy
     */
    @Override
    public void resetInstance() {
        int maxHp = this.getMaxHp();
        this.hurt(maxHp);
    }

    /**
     * registers this with the registerInstance
     */
    @Override
    public void registerInstance() {
        Resettable.super.registerInstance();
    }

}

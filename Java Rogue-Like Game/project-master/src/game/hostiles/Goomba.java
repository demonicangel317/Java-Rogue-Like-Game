package game.hostiles;


import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.actions.DoNothingAction;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.weapons.IntrinsicWeapon;
import game.Resettable;
import game.actions.AttackAction;
import game.behaviours.AttackBehaviour;
import game.behaviours.Behaviour;
import game.Status;
import game.behaviours.FollowBehaviour;
import game.behaviours.WanderBehaviour;

import java.lang.reflect.Array;
import java.util.*;

/**
 * A little fungus guy.
 *
 * @author Ravindu Santhush Ratnayake
 */
public class Goomba extends EnemyType {
	private final Map<Integer, Behaviour> behaviours = new HashMap<>(); // priority, behaviour

	/**
	 * Constructor.
	 */
	public Goomba() {
		super("Goomba", 'g', 20);
		// register as a resettable instance
	}

	/**
	 * creates and returns weapon for goomba
	 * @return returns the intrinsic weapon
	 */
	@Override
	protected IntrinsicWeapon getIntrinsicWeapon() {
		return new IntrinsicWeapon(10, "kicks");
	}


	/**
	 *
	 * plays the current turn of goomba
	 *
	 * @param actions    collection of possible Actions for this Actor
	 * @param lastAction The Action this Actor took last turn. Can do interesting things in conjunction with Action.getNextAction()
	 * @param map        the map containing the Actor
	 * @param display    the I/O object to which messages may be written
	 * @return returns the appropriate action to make in this turn
	 */

	@Override
	public Action playTurn(ActionList actions, Action lastAction, GameMap map, Display display) {

		//self destruct
		Random rand = new Random();

		if(rand.nextInt(10) > 8){
			this.hurt(this.getMaxHp());
			return new DoNothingAction();
		}else{
			return this.returnCurrentTurnAction(this, map);
		}



	}
}

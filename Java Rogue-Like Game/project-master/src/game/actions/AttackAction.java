package game.actions;

import java.util.Random;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.weapons.Weapon;
import game.Status;

/**
 * Special Action for attacking other Actors.
 */
public class AttackAction extends Action {

	/**
	 * The Actor that is to be attacked
	 */
	protected Actor target;

	/**
	 * The direction of incoming attack.
	 */
	protected String direction;

	/**
	 * Random number generator
	 */
	protected Random rand = new Random();

	/**
	 * Constructor.
	 * 
	 * @param target the Actor to attack
	 */
	public AttackAction(Actor target, String direction) {
		this.target = target;
		this.direction = direction;
	}

	/**
	 *
	 * the target actors health will be reduced by the actor that is passed into this
	 * @param actor the actor that will do the attacking
	 * @param map the GameMap containing the player
	 * @return returns message saying hit points have been deducted from the target
	 */
	@Override
	public String execute(Actor actor, GameMap map) {

		Weapon weapon = actor.getWeapon();

		if (!(rand.nextInt(100) <= weapon.chanceToHit())) {
			return actor + " misses " + target + ".";
		}

		int damage;
		// no damage dealt if target is invincible
		if (target.hasCapability(Status.INVINCIBLE)) {
			damage = 0;
		} else {
			if (actor.hasCapability(Status.INVINCIBLE)) {
				// max HP of an enemy is 100
				// dealing 100 damage will instantly kill any enemies
				damage = 100;
			} else {
				damage = weapon.damage();
			}
		}
		String result = actor + " " + weapon.verb() + " " + target + " for " + damage + " damage.";
		target.hurt(damage);
		// remove effects of super mushroom
		if (target.hasCapability(Status.TALL)) {
			target.removeCapability(Status.TALL);
		}
		if (!target.isConscious()) {
			ActionList dropActions = new ActionList();
			// drop all items
			for (Item item : target.getInventory())
				dropActions.add(item.getDropAction(actor));
			for (Action drop : dropActions)
				drop.execute(target, map);
			// remove actor
			map.removeActor(target);
			result += System.lineSeparator() + target + " is killed.";
		}

		return result;
	}

	/**
	 *
	 * @param actor The actor performing the action.
	 * @return the description of the action to be performed
	 */
	@Override
	public String menuDescription(Actor actor) {
		return actor + " attacks with " + actor.getWeapon().toString() +" at "+ target + " at " + direction;
	}
}

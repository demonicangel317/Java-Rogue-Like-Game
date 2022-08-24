package game.groundobjects;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Ground;
import game.Status;

/**
 * A class that represents the floor inside a building.
 *
 * @author Ravindu Santhush Ratnayake
 */
public class Floor extends Ground {
	public Floor() {
		super('_');
	}

	/**
	 * checks if the actor is hostile to player if the answer is yes then the actor is not allowed to enter
	 * @param actor the Actor to check
	 * @return false out if an enemy is detected else returns true
	 */
	@Override
	public boolean canActorEnter(Actor actor) {
		if(!actor.hasCapability(Status.HOSTILE_TO_PLAYER)){
			return true;
		}else{
			return false;
		}

	}

}

package game.groundobjects;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;
import game.Status;
import game.actions.JumpAction;

public class Wall extends Ground {

	public Wall() {
		super('#');
		this.addCapability(Status.HIGH_GROUND);
	}
	
	@Override
	public boolean canActorEnter(Actor actor) {
		return false;
	}
	
	@Override
	public boolean blocksThrownObjects() {
		return true;
	}

	@Override
	public ActionList allowableActions(Actor actor, Location location, String direction){
		ActionList wallActionList = new ActionList();
		if(!this.hasCapability(Status.ON_TOP)){
			if(!location.containsAnActor()){
				wallActionList.add(new JumpAction(location,0.8, 20, "Wall", direction));
			}
		}
		return wallActionList;
	}


}

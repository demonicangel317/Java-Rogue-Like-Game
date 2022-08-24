package game.gameitems;

import edu.monash.fit2099.engine.weapons.WeaponItem;
import game.Status;

/**
 * Wrench object
 *
 * @author Ravindu Santhush Ratnayake
 */
public class Wrench extends WeaponItem {

    /**
     * Constructor
     *
     * has 80 damage and 80 percent chance to hit
     * add capability HAS_WRENCH to player
     */
    public Wrench(){
        super("Wrench", 'W', 80, "Screws Over", 80);
        this.addCapability(Status.HAS_WRENCH);
    }
}

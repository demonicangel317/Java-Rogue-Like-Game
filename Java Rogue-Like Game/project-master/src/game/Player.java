package game;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.displays.Menu;
import edu.monash.fit2099.engine.weapons.IntrinsicWeapon;
import game.actions.ConsumeAction;
import game.actions.JumpAction;
import game.actions.ResetAction;
import game.gameitems.Bottle;
import game.gameitems.Wrench;

/**
 * Class representing the Player.
 */
public class Player extends Actor implements Resettable {

    private final Menu menu = new Menu();
    private int invincible;
    private boolean reset = false;
    /**
     * Intrinsic damage of Mario
     */
    private int baseDamage = 5;
    /**
     * Used to count consecutive failed jumps
     */
    private int noJumps;

    /**
     * Constructor.
     *
     * @param name        Name to call the player in the UI
     * @param displayChar Character to represent the player in the UI
     * @param hitPoints   Player's starting number of hitpoints
     */
    public Player(String name, char displayChar, int hitPoints) {
        super(name, displayChar, hitPoints);
        this.addCapability(Status.HOSTILE_TO_ENEMY);
        this.addCapability(Status.CAN_JUMP);
        //this.addItemToInventory(new Wrench());
        // register as a resettable instance
        registerInstance();
        // 0 jump ban initially
        this.noJumps = 0;
    }

    @Override
    public Action playTurn(ActionList actions, Action lastAction, GameMap map, Display display) {
        // add ability to reset game only once
        if (!reset) {
            actions.add(new ResetAction());
        }
        // REQ4: Broken leg effect
        if (this.hasCapability(Status.BROKEN_LEG)) {
            this.noJumps = 5;
            this.removeCapability(Status.BROKEN_LEG);
        }
        if (this.noJumps != 0) {
            for (Action action: actions) {
                if (action instanceof JumpAction) {
                    actions.remove(action);
                }
            }
            System.out.println("Mario has a broken leg and can't perform jump! " + noJumps + 1 + " turns left.");
            this.noJumps -= 1;
        }
        // if bottle is not empty
        Bottle bottle = Bottle.getInstance();
        if (bottle.size() > 0) {
            actions.add(new ConsumeAction(this, bottle.peek()));
        }
        // if PowerWater has been consumed, increase intrinsic damage by 15
        if (this.hasCapability(Status.POWER_UP)) {
            this.baseDamage += 15;
            this.removeCapability(Status.POWER_UP);
        }
        // Handle multi-turn Actions
        if (lastAction.getNextAction() != null)
            return lastAction.getNextAction();

        // return/print the console menu
        // if mario is invincible
        if (this.hasCapability(Status.INVINCIBLE)) {
            this.reduceInvincible();
            String output = "Mario is INVINCIBLE! " + Integer.toString(this.invincible) + " turns remaining.";
            System.out.println(output);
            if (this.invincible == 0) {
                this.removeCapability(Status.INVINCIBLE);
            }
        }
        return menu.showMenu(this, actions, display);
    }

    @Override
    public char getDisplayChar() {
        return this.hasCapability(Status.TALL) ? Character.toUpperCase(super.getDisplayChar()) : super.getDisplayChar();
    }

    @Override
    public void resetInstance() {
        // reset player status
        if (this.hasCapability(Status.TALL)) {
            this.removeCapability(Status.TALL);
        } else if (this.hasCapability(Status.INVINCIBLE)) {
            this.removeCapability(Status.INVINCIBLE);
        }

        // heal player to maximum
        int maxHp = this.getMaxHp();
        this.heal(maxHp);

        // set reset to true so it cannot be done again
        this.reset = true;
    }

    @Override
    protected IntrinsicWeapon getIntrinsicWeapon() {
        return new IntrinsicWeapon(this.baseDamage, "punches");
    }

    @Override
    public void registerInstance() {
        Resettable.super.registerInstance();
    }

    public void resetInvincible() {
        this.invincible = 11;
    }

    public void reduceInvincible() {
        this.invincible -= 1;
    }

}

package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.GameMap;
import game.gameitems.Wallet;
import game.consumables.PowerStar;
import game.consumables.SuperMushroom;
import game.gameitems.Wrench;

import java.util.ArrayList;
import java.util.List;

/**
 * A class that defines trading actions
 */
public class TradeAction extends Action {
    // attributes
    /**
     * the actor that is trying to trade with Toad
     */
    private Actor actor;
    /**
     * the item that is sought after
     */
    private Item item;
    /**
     * the prices of the items sold by Toad
     */
    private final ArrayList<Integer> prices = new ArrayList<Integer>(3);

    /**
     * Constructor
     *
     * @param actor is the actor that is trying to make a trade
     * @param item  is the item sought after
     */
    public TradeAction(Actor actor, Item item) {
        this.actor = actor;
        this.item = item;
        this.prices.add(200);
        this.prices.add(400);
        this.prices.add(600);
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
        // prices depending on what is to be bought
        int price = 0;
        if (this.item instanceof Wrench) {
            price = prices.get(0);
            if (tryToTrade(actor, price)) {
                return trade(actor, price, new Wrench());
            }
        } else if (this.item instanceof SuperMushroom) {
            price = prices.get(1);
            if (tryToTrade(actor, price)) {
                return trade(actor, price, new SuperMushroom());
            }
        } else if (this.item instanceof PowerStar) {
            price = prices.get(2);
            if (tryToTrade(actor, price)) {
                return trade(actor, price, new PowerStar(null, actor));
            }
        }
        return "Not enough coins. ";
    }

    /**
     * Menu description of the action to be performed.
     *
     * @param actor The actor performing the action.
     * @return the menu description.
     */
    @Override
    public String menuDescription(Actor actor) {
        if (this.item instanceof Wrench) {
            return "Mario buys Wrench ($200).";
        } else if (this.item instanceof SuperMushroom) {
            return "Mario buys Super Mushroom ($400).";
        } else if (this.item instanceof PowerStar) {
            return "Mario buys Power Star ($600).";
        }
        return null;
    }

    /**
     * Method to check if actor has enough coins to make the trade
     *
     * @param actor actor that is trying to make a trade
     * @param price price of the item to be traded
     * @return boolean based on whether the purchase can be made
     */
    public boolean tryToTrade(Actor actor, int price) {
        // retrieve actor's wallet
        List<Item> inventory = actor.getInventory();
        Wallet wallet = new Wallet();

        for (Item value : inventory) {
            if (value instanceof Wallet) {
                wallet = (Wallet) value;
            }
        }

        // amount of coins in the wallet
        int coins = wallet.getCoins();

        return (coins > price);
    }

    /**
     * Method to make the trade,
     * adds item to the actor's inventory
     * while deducting coins from their wallet
     *
     * @param actor is the actor trying to make the trade
     * @param price is the price of the item sought after
     * @param item
     * @return description after a successful trade
     */
    public String trade(Actor actor, int price, Item item) {
        // add item to actor's inventory
        actor.addItemToInventory(item);

        // retrieve actor's wallet
        List<Item> inventory = actor.getInventory();
        Wallet wallet = new Wallet();

        for (Item value : inventory) {
            if (value instanceof Wallet) {
                wallet = (Wallet) value;
            }
        }

        // deduct coins from the wallet
        wallet.addCoins(-1 * price);

        return this.item.toString() + " added to inventory, coins left in wallet: " + Integer.toString(wallet.getCoins());
    }
}

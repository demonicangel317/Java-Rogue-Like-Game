package game;

import java.util.Arrays;
import java.util.List;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.FancyGroundFactory;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import edu.monash.fit2099.engine.positions.World;
import game.consumables.PowerStar;
import game.consumables.SuperMushroom;
import game.fountains.HealthFountain;
import game.fountains.PowerFountain;
import game.groundobjects.*;
import game.hostiles.Bowser;
import game.hostiles.FlyingKoopa;
import game.hostiles.Koopa;

/**
 * The main class for the Mario World game.
 */
public class Application {

    public static void main(String[] args) {

        World world = new World(new Display());

        FancyGroundFactory groundFactory = new FancyGroundFactory(new Dirt(), new Wall(), new Floor(), new Sprout());

        List<String> map = Arrays.asList(
                "..........................................##..........+.........................",
                "............+............+..................#...................................",
                "............................................#...................................",
                ".............................................##......................+..........",
                "...............................................#................................",
                "................................................#...............................",
                ".................+................................#.............................",
                ".................................................##.............................",
                "................................................##..............................",
                ".........+..............................+#____####.................+............",
                ".......................................+#_____###++.............................",
                ".......................................+#______###..............................",
                "........................................+#_____###..............................",
                "........................+........................##.............+...............",
                "...................................................#............................",
                "....................................................#...........................",
                "...................+.................................#..........................",
                "......................................................#.........................",
                ".......................................................##.......................");


        FancyGroundFactory lavaZoneGroundFactory = new FancyGroundFactory(new Dirt(), new Wall(), new Lava(), new Sprout());

        List<String> lavaZone = Arrays.asList(
                ".................LLLLLLLL..................LLLLLLLLLLLLLLLLLLLLLLLLLLLLL",
                "........................LLLLLLLLL..................LLLLLLLLLLLLLLL......",
                "........................................................................",
                ".................................................................+......",
                "............LLLLLLLLLL..................................................",
                "LLLLLLLL.........LLLLLLLLLLL.............................LLLLLLLLLL.....",
                ".....................................LLLLLLLLLLLLL......................",
                ".........................LL.....LLLL....................................",
                ".........................LLL...LLLLL....................................",
                "...........+.............LLL.LLLLLLL...........LLLLLLLLL................",
                "..................LLLLLLLLLLLLLLLLLL....................................",
                "..............................LLLLLLLLLL..........................+.....",
                "LLLLLLLLLLL............LLLLLLL............LLLLLLLLL.....................",
                "LLLLLLLLLLLLLL..............LLLLLLLLLLLL.....................LLLLLLL....");

        GameMap gameMap = new GameMap(groundFactory, map);
        GameMap lavaGameMap = new GameMap(lavaZoneGroundFactory, lavaZone);
        world.addGameMap(gameMap);
        world.addGameMap(lavaGameMap);

        Actor mario = new Player("Player", 'm', 100);
        world.addPlayer(mario, gameMap.at(42, 10));
        //world.addPlayer(mario, lavaGameMap.at(1, 0));


        // adding Toad to the map
        gameMap.at(42, 11).addActor(new Toad());
        Location location = new Location(gameMap, 0, 0);
        new PowerStar(location);
        gameMap.at(42, 9).addItem(new SuperMushroom());

//			gameMap.at(42, 9).addItem(new PowerStar(location));

        //adds new warp pipes at main map
        gameMap.at(40, 9).setGround(new WarpPipe(lavaGameMap.at(0, 0), "Lava Map", gameMap.at(40, 9), true));
        gameMap.at(30, 9).setGround(new WarpPipe(lavaGameMap.at(0, 0), "Lava Map", gameMap.at(30, 9), true));
        gameMap.at(50, 11).setGround(new WarpPipe(lavaGameMap.at(0, 0), "Lava Map", gameMap.at(50, 11), true));
        gameMap.at(27, 14).setGround(new WarpPipe(lavaGameMap.at(0, 0), "Lava Map", gameMap.at(27, 14), true));

        //adds new warp pipe at lava map
        lavaGameMap.at(0, 0).setGround(new WarpPipe(gameMap.at(40, 9), "Main Map", lavaGameMap.at(0, 0), true));

        //adds bowser and peach at lava map
        lavaGameMap.at(28, 8).addActor(new Bowser(lavaGameMap));
        lavaGameMap.at(28, 9).addActor(new Peach());

        // add fountains near starting location
		gameMap.at(44, 10).setGround(new HealthFountain());
		gameMap.at(45, 10).setGround(new PowerFountain());

        world.run();

    }
}

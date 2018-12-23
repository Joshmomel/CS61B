package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;

import java.util.Random;

public class Game {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 30;
    public static Random rand;


    /**
     * Method used for playing a fresh game. The game should start from the main menu.
     */
    public void playWithKeyboard() {
    }

    /**
     * Method used for autograding and testing the game code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The game should
     * behave exactly as if the user typed these characters into the game after playing
     * playWithKeyboard. If the string ends in ":q", the same world should be returned as if the
     * string did not end with q. For example "n123sss" and "n123sss:q" should return the same
     * world. However, the behavior is slightly different. After playing with "n123sss:q", the game
     * should save, and thus if we then called playWithInputString with the string "l", we'dir expect
     * to get the exact same world back again, since this corresponds to loading the saved game.
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] playWithInputString(String input) {
        // TODO: Fill out this method to run the game using the input passed in,
        // and return a 2D tile representation of the world that would have been
        // drawn if the same inputs had been given to playWithKeyboard().
        TETile[][] world = WorldCreator.createEmptyWorld();
        Game.rand = Data.setGameSeed(input);
        int numRooms = rand.nextInt(15) + 5;
        Room[] rooms = new Room[numRooms];
        MapGenerator.generateRooms(world, numRooms, rooms);

//        PathGenerator.drawPath(world, new Room(5, 5, WIDTH / 2, HEIGHT / 2), rooms[0]);
        for (int i = 0; i < numRooms - 1; i++) {
            PathGenerator.drawPath(world,rooms[i],rooms[i + 1]);
        }
//        PathGenerator.drawPath(world, rooms[numRooms - 1], new Room(5, 5, 1, 1));

        MapGenerator.fillWalls(world);



        System.out.println(TETile.toString(world));
        WorldCreator.display(world);


        return world;
    }
}

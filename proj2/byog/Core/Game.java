package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.*;
import java.util.Random;

public class Game {
    /* Feel free to change the width and height. */
    public static Random rand;
    public static Player player1;
    public static Player player2;
    public static TETile[][] world;


    /**
     * Method used for playing a fresh game. The game should start from the main menu.
     */
    public void playWithKeyboard() {
        UI.drawMainMenu();
        char command = waitCommand();
        while (true) {
            if (command == 'n') {
                long seed = UI.askForSeed();
                System.out.println(seed);
                world = playWithInputString(Long.toString(seed));
                WorldCreator.renderWorld(world);
                play(world);
            }
            command = waitCommand();
        }
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
     *
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] playWithInputString(String input) {
        TETile[][] world = WorldCreator.createEmptyWorld();
        Game.rand = Data.setGameSeed(input);
        int numRooms = rand.nextInt(15) + 5;
        Room[] rooms = new Room[numRooms];
        MapGenerator.generateRooms(world, numRooms, rooms);
        MapGenerator.generateFlowers(world, rooms);
        player1 = new Player(PathGenerator.randomPoint(rooms[0]), Tileset.PLAYER, "Player1");
        player2 = new Player(PathGenerator.randomPoint(rooms[rooms.length - 1]), Tileset.PLAYER2, "player2");
        player1.placePlayerToWorld(world);
        player2.placePlayerToWorld(world);
        PathGenerator.drawAllPaths(world, numRooms, rooms);
        MapGenerator.fillWalls(world);
        System.out.println(TETile.toString(world));
        return world;
    }

    public static char waitCommand() {
        while (!StdDraw.hasNextKeyTyped()) {
            StdDraw.pause(10);
//            mouseTile();
        }
        return StdDraw.nextKeyTyped();
    }

    public static char waitForControlKey() {
        while (!StdDraw.hasNextKeyTyped()) {
            StdDraw.pause(10);
            mouseTile();
        }
        return StdDraw.nextKeyTyped();
    }



    public static void mouseTile() {
        double x = StdDraw.mouseX();
        double y = StdDraw.mouseY();
        int w = (int) Math.floorDiv((long) x, 1);
        int h = (int) Math.floorDiv((long) y, 1);
        if (h >= Data.HEIGHT) {
            h = Data.HEIGHT - 1;
        }
        if (w >= Data.WIDTH) {
            w = Data.WIDTH - 1;
        }
        TETile tile = world[w][h];
        StdDraw.setPenColor(Color.BLACK);
        StdDraw.filledRectangle(Data.WIDTH / 2, Data.HEIGHT - 1, Data.WIDTH / 2, 1);
        StdDraw.setPenColor(Color.PINK);
        StdDraw.textLeft(1, Data.HEIGHT - 1, tile.description());
        StdDraw.show(10);

    }

    private void play(TETile[][] world) {
        while (true) {
            char k = waitForControlKey();
            if (k == 'q') {
                playWithKeyboard();
            }
            if (k == 'w') {
                player1.move(world, Direction.TOP);
            }
            if (k == 'a') {
                player1.move(world, Direction.LEFT);
            }
            if (k == 's') {
                player1.move(world, Direction.DOWN);
            }
            if (k == 'd') {
                player1.move(world, Direction.RIGHT);
            }
            if (k == 'i') {
                player2.move(world, Direction.TOP);
            }
            if (k == 'j') {
                player2.move(world, Direction.LEFT);
            }
            if (k == 'k') {
                player2.move(world, Direction.DOWN);

            }
            if (k == 'l') {
                player2.move(world, Direction.RIGHT);

            }
        }
    }


}

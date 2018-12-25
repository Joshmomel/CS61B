package byog.Core;

import byog.SaveDemo.World;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.*;
import java.io.*;
import java.util.Random;

public class Game implements Serializable {
    /* Feel free to change the width and height. */
    public static Random rand;
    public static Player player1;
    public static Player player2;
    public static TETile[][] world;
    public static int flowersTotal = 0;


    public static void setPlayer1(Player player1) {
        Game.player1.playerTile = player1.playerTile;
        Game.player1.flowers = player1.flowers;
        Game.player1.position = player1.position;
        Game.player1.playerName = player1.playerName;
    }


    public static void setPlayer2(Player player2) {
        Game.player2 = player2;
    }

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
                MapGenerator.countFlowers(world);
                play(world);
            } else if (command == 'l') {
                OutputObject o = loadWorld();
                world = o.world;
                setPlayer1(o.player1);
                setPlayer2(o.player2);
                System.out.println("the flower is " + o.player1.flowers);
                System.out.println("the flower is " + o.player2.flowers);

                WorldCreator.renderWorld(world);
                MapGenerator.countFlowers(world);
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
//        System.out.println(TETile.toString(world));

        String moveStr = precessString(input);
        moveNoDraw(world, moveStr);

        return world;
    }

    private String precessString(String input) {
        String moveStr = input;
        if ((moveStr.charAt(0) == 'n')) {
            moveStr = moveStr.substring(1);
        } else if ((moveStr.charAt(0) == 'l')) {
            OutputObject w = loadWorld();
            Game.world = w.world;
            Game.player1 = w.player1;
            Game.player2 = w.player2;
            moveStr = moveStr.substring(1);
        } else if ((moveStr.charAt(0) == 'q')) {
            saveGame(new OutputObject(Game.player1, Game.player2, world));
        }
        moveStr = moveStr.replaceAll("\\d", "");
        return moveStr;
    }

    private void moveNoDraw(TETile[][] world, String str) {
        for (int i = 0; i < str.length(); i += 1) {
            char c = str.charAt(i);
            if (c == 'w') {
                player1.moveNoDraw(world, Direction.TOP);
            } else if (c == 'a') {
                player1.moveNoDraw(world, Direction.LEFT);
            } else if (c == 's') {
                player1.moveNoDraw(world, Direction.DOWN);
            } else if (c == 'd') {
                player1.moveNoDraw(world, Direction.RIGHT);
            } else if (c == 'i') {
                player2.moveNoDraw(world, Direction.TOP);
            } else if (c == 'j') {
                player2.moveNoDraw(world, Direction.LEFT);
            } else if (c == 'k') {
                player2.moveNoDraw(world, Direction.DOWN);
            } else if (c == 'l') {
                player2.moveNoDraw(world, Direction.RIGHT);
            } else if (c == 'q') {
                saveGame(new OutputObject(player1, player2, world));
            }
        }
    }



    public static char waitCommand() {
        while (!StdDraw.hasNextKeyTyped()) {
            StdDraw.pause(10);
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
        StdDraw.textRight(Data.WIDTH - 1, Data.HEIGHT - 1,
                "flowersTotal: " + flowersTotal + "   Player 1: " + Game.player1.flowers
                        + "   Player 2: " + Game.player2.flowers);
        StdDraw.show(10);

    }

    public static boolean isWin() {
        if (player1.flowers + player2.flowers == flowersTotal) {
            return true;
        }
        return false;
    }

    private void play(TETile[][] world) {
        while (true) {
            char k = waitForControlKey();
            if (k == 'q') {
                saveGame(new OutputObject(Game.player1, Game.player2, world));
                System.out.println("player1 has " + player1.flowers);
                System.out.println("player2 has " + player2.flowers);

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
            if (isWin()) {
                endGame();
            }
        }
    }

    private void saveGame(OutputObject outputObject) {
        File f = new File("./world.txt");
        try {
            FileOutputStream fs = new FileOutputStream(f);
            ObjectOutputStream os = new ObjectOutputStream(fs);
            os.writeObject(outputObject);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private OutputObject loadWorld() {
        File f = new File("./world.txt");
        if (f.exists()) {
            try {
                FileInputStream fs = new FileInputStream(f);
                ObjectInputStream os = new ObjectInputStream(fs);
                return (OutputObject) os.readObject();
            } catch (FileNotFoundException e) {
                System.out.println("file not found");
                System.exit(0);
            } catch (IOException e) {
                System.out.println(e);
                System.exit(0);
            } catch (ClassNotFoundException e) {
                System.out.println("class not found");
                System.exit(0);
            }
        }
        throw new RuntimeException("no file exists");
    }

    private void endGame() {
        StdDraw.clear(StdDraw.PRINCETON_ORANGE);
        StdDraw.setPenColor(Color.PINK);
        StdDraw.setFont(new Font("Times New Roman", Font.BOLD, 60));
        StdDraw.text(Data.WIDTH / 2, Data.HEIGHT / 2 + 10, "Final score:");
        StdDraw.text(Data.WIDTH / 2, Data.HEIGHT / 2 + 5, "Player 1 - " + Game.player1.flowers);
        StdDraw.text(Data.WIDTH / 2, Data.HEIGHT / 2, "Player 2 - " + Game.player2.flowers);
        if (player1.flowers > player2.flowers) {
            StdDraw.text(Data.WIDTH / 2, Data.HEIGHT / 2 - 5, "Player 1 wins!");
        } else if (player2.flowers > player1.flowers) {
            StdDraw.text(Data.WIDTH / 2, Data.HEIGHT / 2 - 5, "Player 2 wins!");
        } else {
            StdDraw.text(Data.WIDTH / 2, Data.HEIGHT / 2 - 5, "It's a tie!");
        }
        StdDraw.show();
        while (true) {
            StdDraw.pause(100);
            if (StdDraw.hasNextKeyTyped()) {
                char k = StdDraw.nextKeyTyped();
                if ((k == 'n') || (k == 'q')) {
                    break;
                }
            }
        }
        StdDraw.clear(StdDraw.PRINCETON_ORANGE);
        StdDraw.text(Data.WIDTH / 2, Data.HEIGHT / 2 + 5, "Play again? ");
        StdDraw.text(Data.WIDTH / 2, Data.HEIGHT / 2, "(Q)uit or (N)ew game?");
        StdDraw.show();
        char k = waitCommand();
        while (true) {
            if (k == 'q') {
                System.exit(0);
            } else if (k == 'n') {
                playWithKeyboard();
            }
        }
    }


}

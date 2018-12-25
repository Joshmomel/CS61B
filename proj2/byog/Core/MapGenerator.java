package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.io.Serializable;


public class MapGenerator implements Serializable {


    public static void generateRooms(TETile[][] world, int numRooms, Room[] rooms) {
        Room.createRooms(rooms);
        Room.drawRooms(world, numRooms, rooms);
        Room.fillAll(world, numRooms, rooms);
    }

    public static void fillWalls(TETile[][] world) {
        for (int i = 1; i < world.length - 1; i++) {
            for (int j = 1; j < world[i].length - 1; j++) {
                if (world[i][j] == Tileset.FLOOR){
                    if (world[i][j + 1] == Tileset.NOTHING) {
                        world[i][j + 1] = Tileset.WALL;
                    }
                    if (world[i][j - 1] == Tileset.NOTHING) {
                        world[i][j - 1] = Tileset.WALL;
                    }
                    if (world[i + 1][j] == Tileset.NOTHING) {
                        world[i + 1][j] = Tileset.WALL;
                    }
                    if (world[i - 1][j] == Tileset.NOTHING) {
                        world[i - 1][j] = Tileset.WALL;
                    }
                }
            }
        }
    }

    public static void generateFlowers(TETile[][] world, Room[] rooms) {
        for (int i = 0; i < rooms.length; i++) {
            int numbers = Game.rand.nextInt(rooms[i].roomWidth * rooms[i].roomHeight / 2);
            for (int j = 0; j < numbers; j++) {
                Position p = PathGenerator.randomPoint(rooms[i]);
                world[p.x][p.y] = Tileset.FLOWER;
            }

        }
    }

    public static void countFlowers(TETile[][] world) {
        for (int i = 0; i < world.length; i++) {
            for (int j = 0; j < world[i].length; j++) {
                if (world[i][j] == Tileset.FLOWER) {
                    Game.flowersTotal++;
                }
            }
        }
    }

    public static void moveTop(TETile[][] world, Player player) {
        int x = player.position.x;
        int y = player.position.y;
        Position checkWallPosition = new Position(x, y + 1);
        if (isClear(world, checkWallPosition)) {
            System.out.println("tile is " + world[x][y + 1].description());
            if (world[x][y + 1].description().equals("flower")) {
                player.flowers++;
            }
            world[x][y] = Tileset.FLOOR;
            world[x][y + 1] = player.playerTile;
            world[x][y].draw(x, y);
            y += 1;
            world[x][y].draw(x, y);
            player.position.x = x;
            player.position.y = y;
            System.out.println("move top");
        }
    }


    public static void moveLeft(TETile[][] world, Player player) {
        int x = player.position.x;
        int y = player.position.y;
        Position checkWallPosition = new Position(x- 1, y);
        if (isClear(world, checkWallPosition)) {
            System.out.println("tile is " + world[x - 1][y].description());

            if (world[x - 1][y].description().equals("flower")) {
                player.flowers++;
            }
            Game.world[x][y] = Tileset.FLOOR;
            Game.world[x - 1][y] = player.playerTile;
            Game.world[x][y].draw(x, y);
            x -= 1;
            Game.world[x][y].draw(x, y);
            player.position.x = x;
            player.position.y = y;
            System.out.println("move left");
        }
    }

    public static void moveDown(TETile[][] world, Player player) {
        int x = player.position.x;
        int y = player.position.y;
        Position checkWallPosition = new Position(x, y - 1);
        if (isClear(world, checkWallPosition)) {
            System.out.println("tile is " + world[x][y - 1].description());
            if (world[x][y - 1].description().equals("flower")) {
                player.flowers++;
            }
            world[x][y] = Tileset.FLOOR;
            world[x][y - 1] = player.playerTile;
            world[x][y].draw(x, y);
            y -= 1;
            world[x][y].draw(x, y);
            player.position.x = x;
            player.position.y = y;
            System.out.println("move down");
        }
    }

    public static void moveRight(TETile[][] world, Player player) {
        int x = player.position.x;
        int y = player.position.y;
        Position checkWallPosition = new Position(x + 1, y);
        if (isClear(world, checkWallPosition)) {
            System.out.println("tile is " + world[x + 1][y].description());
            if (world[x + 1][y].description().equals("flower")) {
                player.flowers++;
            }
            world[x][y] = Tileset.FLOOR;
            world[x + 1][y] = player.playerTile;
            world[x][y].draw(x, y);
            x += 1;
            world[x][y].draw(x, y);
            player.position.x = x;
            player.position.y = y;
            System.out.println("move right");
        }
    }

    public static void moveNoDrawTop(TETile[][] world, Player player) {
        int x = player.position.x;
        int y = player.position.y;
        Position checkWallPosition = new Position(x, y + 1);
        if (isClear(world, checkWallPosition)) {
            if (world[x][y + 1].description().equals("flower")) {
                player.flowers++;
            }
            world[x][y] = Tileset.FLOOR;
            world[x][y + 1] = player.playerTile;
            y += 1;
            player.position.x = x;
            player.position.y = y;
        }
    }
    public static void moveNoDrawLeft(TETile[][] world, Player player) {
        int x = player.position.x;
        int y = player.position.y;
        Position checkWallPosition = new Position(x- 1, y);
        if (isClear(world, checkWallPosition)) {

            if (world[x - 1][y].description().equals("flower")) {
                player.flowers++;
            }
            world[x][y] = Tileset.FLOOR;
            world[x - 1][y] = player.playerTile;
            x -= 1;
            player.position.x = x;
            player.position.y = y;
        }
    }
    public static void moveNoDrawDown(TETile[][] world, Player player) {
        int x = player.position.x;
        int y = player.position.y;
        Position checkWallPosition = new Position(x, y - 1);
        if (isClear(world, checkWallPosition)) {
            if (world[x][y - 1].description().equals("flower")) {
                player.flowers++;
            }
            world[x][y] = Tileset.FLOOR;
            world[x][y - 1] = player.playerTile;
            y -= 1;
            player.position.x = x;
            player.position.y = y;
        }
    }
    public static void moveNoDrawRight(TETile[][] world, Player player) {
        int x = player.position.x;
        int y = player.position.y;
        Position checkWallPosition = new Position(x + 1, y);
        if (isClear(world, checkWallPosition)) {
            if (world[x + 1][y].description().equals("flower")) {
                player.flowers++;
            }
            world[x][y] = Tileset.FLOOR;
            world[x + 1][y] = player.playerTile;
            x += 1;
            player.position.x = x;
            player.position.y = y;
        }
    }

    private static boolean isClear(TETile[][] world, Position p) {
        if ((world[p.x][p.y].description().equals("wall"))) {
            return false;
        }
        return true;
    }




}

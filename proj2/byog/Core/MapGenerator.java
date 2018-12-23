package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;



public class MapGenerator {
    public static TETile[][] world;


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


}

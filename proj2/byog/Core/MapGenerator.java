package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;



public class MapGenerator {
    public static TETile[][] world;

    /* initialize a world with nothing */


    public static void main(String[] args) {
        world = WorldCreator.createEmptyWorld();

        /* create room not randomly*/
        Room r0 = new Room(5, 5, 20, 20);

        /* top left*/
        Room r1 = new Room(10, 10, 0, 30);
        /* bottom left*/
        Room r2 = new Room(10, 10, 0, 0);
        /* top right*/
        Room r3 = new Room(10, 10, 30, 30);
        /*bottom right*/
        Room r4 = new Room(10, 10, 30, 0);
        /* top */
        Room r5 = new Room(10, 10, 24, 40);
        /*right*/
        Room r6 = new Room(10, 10, 9, 25);
        /*left*/
        Room r7 = new Room(10, 10, 0, 25);






        Room[] rs = new Room[2];
        rs[0] = r0;
        rs[1] = r2;
//        rs[2] = r2;
        Game.rand = Data.setGameSeed("N123S");
        Room.drawRooms(world, 2, rs);
        Room.fillAll(world, 2, rs);


        /* calculate path */
//        for (int i = 0; i < 2; i++) {
//            PathGenerator.drawPath(world,rs[i],rs[i + 1]);
//        }
        PathGenerator.drawPath(world,rs[0],rs[1]);
//        MapGenerator.fillWalls(world);
//        Room.fillAll(world, numRooms, rooms);


        /* print */
        System.out.println(TETile.toString(world));
        WorldCreator.display(world);
    }

    private boolean intersection(Room r) {
        if ((r.roomX <= 7) && (r.roomY <= 7)) {
            return true;
        } else if ((r.roomX + r.roomWidth >= Data.WIDTH - 7) && (r.roomY + r.roomHeight >= Data.HEIGHT - 9)) {
            return true;
        }
        return false;
    }


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

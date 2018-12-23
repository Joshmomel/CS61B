package byog.Core.test;

import byog.Core.*;
import byog.TileEngine.TETile;

public class mapGeneratorTest {
    public static TETile[][] world;

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
        rs[1] = r7;
        Game.rand = Data.setGameSeed("N123S");
        Room.drawRooms(world, 2, rs);
        Room.fillAll(world, 2, rs);



        PathGenerator.drawPath(world,rs[0],rs[1]);


        /* print */
        System.out.println(TETile.toString(world));
        WorldCreator.display(world);
    }
}

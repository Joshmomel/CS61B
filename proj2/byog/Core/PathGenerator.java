package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.io.Serializable;

public class PathGenerator implements Serializable {
    public static void drawPath(TETile[][] world, Room r1, Room r2) {
        Position p1 = PathGenerator.randomPoint(r1);
        Position p2 = PathGenerator.randomPoint(r2);

        int deltaX = Math.abs(p1.x - p2.x);
        int deltaY = Math.abs(p1.y - p2.y);
        int xIncrement = 0;
        int yIncrement = 0;

        if (!(deltaX == 0)) {
            xIncrement = (p2.x - p1.x) / deltaX;
        }

        if (!(deltaY == 0)) {
            yIncrement = (p2.y - p1.y) / deltaY;
        }

        int a = p1.x;
        for (int i = 0; i < deltaX; i++) {
            a = a + xIncrement;
            world[a][p1.y] = Tileset.FLOOR;
        }

        int b = p1.y;
        for (int i = 0; i < deltaY; i++) {
            b = b + yIncrement;
            world[p1.x + xIncrement * deltaX][b] = Tileset.FLOOR;
        }

    }

    public static Position randomPoint(Room r) {
        return new Position(Game.rand.nextInt(r.roomWidth - 2) + r.roomX + 1,
                Game.rand.nextInt(r.roomHeight -2 ) + r.roomY + 1);
    }

    public static void drawAllPaths(TETile[][] world, int numRooms, Room[] rooms) {
        for (int i = 0; i < numRooms - 1; i++) {
            PathGenerator.drawPath(world,rooms[i],rooms[i + 1]);
        }
    }


}

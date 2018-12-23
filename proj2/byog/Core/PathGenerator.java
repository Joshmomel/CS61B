package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

public class PathGenerator {


    public static void drawPath2(TETile[][] world, Room r1, Room r2) {
        Direction.dir direction = Room.relativeDirection(r1, r2);
        if (direction == Direction.dir.Top) {
            Position p = getTopPosition(r1, r2);
            for (int i = -2; i <= r2.roomY - r1.roomY - r1.roomHeight + 3; i++) {
                world[p.x][p.y - 1 + i] = Tileset.FLOOR;
            }
            for (int i = -1; i < p.x - r1.roomX - r1.roomWidth; i++) {
                world[r1.roomX + r1.roomWidth + i][p.y + r2.roomY - r1.roomY - r1.roomHeight - 3] = Tileset.FLOOR;
            }

        } else if (direction == Direction.dir.Bottom) {
            Position p = new Position(Game.rand.nextInt(r2.roomWidth) + r2.roomX, r2.roomY + r2.roomHeight);
            for (int i = -2; i < r1.roomY - r2.roomY - r2.roomHeight + 3; i++) {
                world[p.x][p.y + i] = Tileset.FLOOR;
            }
        } else if (direction == Direction.dir.BottomLeft) {
            Position p1 = new Position(Game.rand.nextInt(r1.roomWidth) + r1.roomX, r1.roomY);
            Position p2 = new Position(r2.roomX + r2.roomWidth, Game.rand.nextInt(r2.roomHeight - 1) + r2.roomY + 1);
            for (int i = -1; i < p1.y - p2.y; i++) {
                world[p1.x][p1.y - i] = Tileset.FLOOR;
            }
            for (int i = -1; i < p1.x - p2.x + 1; i++) {
                world[p2.x + i][p2.y] = Tileset.FLOOR;
            }
        } else if (direction == Direction.dir.BottomRight) {
            Position p1 = new Position(Game.rand.nextInt(r1.roomWidth) + r1.roomX, r1.roomY);
            Position p2 = new Position(r2.roomX, Game.rand.nextInt(r2.roomHeight - 1) + r2.roomY + 1);
            for (int i = -1; i <= p1.y - p2.y; i++) {
                world[p1.x][p1.y - i] = Tileset.FLOOR;
            }
            for (int i = -1; i < p2.x - p1.x; i++) {
                world[p2.x - i][p2.y] = Tileset.FLOOR;
            }
        } else if (direction == Direction.dir.Right) {
            Position p1 = new Position(r1.roomX + r1.roomWidth, Game.rand.nextInt(r1.roomHeight) + r1.roomY);
            for (int i = -1; i <= r2.roomX - r1.roomX - r1.roomWidth + 1; i++) {
                world[p1.x + i][p1.y] = Tileset.FLOOR;
            }
            for (int i = 0; i < r2.roomY - p1.y + 2; i++) {
                world[p1.x + r2.roomX - r1.roomX - r1.roomWidth + 1][p1.y + i] = Tileset.FLOOR;
            }

        } else if (direction == Direction.dir.Left) {
            Position p1 = new Position(r1.roomX, Game.rand.nextInt(r1.roomHeight) + r1.roomY);
            for (int i = -1; i <= r1.roomX - r2.roomX - r2.roomWidth + 1; i++) {
                world[p1.x - i][p1.y] = Tileset.FLOOR;
            }
        } else if (direction == Direction.dir.TopLeft) {
            Position p1 = new Position(Game.rand.nextInt(r1.roomWidth) + r1.roomX, r1.roomY + r1.roomHeight);
            Position p2 = new Position(r2.roomX + r2.roomWidth, Game.rand.nextInt(r2.roomHeight) + r2.roomY + 1);
            for (int i = -1; i <= p2.y - p1.y; i++) {
                world[p1.x][p1.y + i] = Tileset.FLOOR;
            }
            for (int i = -1; i <= p1.x - p2.x; i++) {
                world[p2.x + i][p2.y] = Tileset.FLOOR;
            }
        } else if (direction == Direction.dir.TopRight) {
            Position p1 = new Position(Game.rand.nextInt(r1.roomWidth) + r1.roomX, r1.roomY + r1.roomHeight);
            Position p2 = new Position(r2.roomX, Game.rand.nextInt(r2.roomHeight - 1) + r2.roomY + 1);
            world[p1.x][p1.y + 1] = Tileset.FLOOR;
            for (int i = -1; i < p2.y - p1.y + 1; i++) {
                world[p1.x][p1.y + i] = Tileset.FLOOR;
            }
            for (int i = 0; i < p2.x - p1.x + 1; i++) {
                world[p2.x - i][p2.y] = Tileset.FLOOR;
            }
        }
    }

    public static void drawPath(TETile[][] world, Room r1, Room r2) {
        Position p1 = PathGenerator.randomPoint(r1);
        Position p2 = PathGenerator.randomPoint(r2);

        int deltaX = Math.abs(p1.x - p2.x);
        int deltaY = Math.abs(p1.y - p2.y);
        int xIncrement = (p2.x - p1.x) / deltaX;
        int yIncrement = (p2.y - p1.y) / deltaY;
        int a =p1.x;
        for (int i = 0; i < deltaX; i++) {
            a = a + xIncrement;
            world[a][p1.y] = Tileset.FLOOR;
        }
        int b = p1.y;
        for (int i = 0; i < deltaY; i++) {
            b = b + yIncrement;
            world[p1.x + xIncrement*deltaX][b] = Tileset.FLOOR;
        }


    }

    private static Position randomPoint(Room r) {
        return new Position( Game.rand.nextInt(r.roomWidth) + r.roomX,
                Game.rand.nextInt(r.roomHeight) + r.roomY);
    }


    public static Position getTopPosition(Room r1, Room r2) {
        int xp1 = Math.max(r1.roomX, r2.roomX);
        int xp2 = Math.min(r1.roomX + r1.roomWidth, r2.roomX + r2.roomWidth);
        int range = xp2 - xp1 - 1;
        if (range < 1){
            range = 1;
        }
        int x = Game.rand.nextInt(range) + xp1;
        return new Position(x, r1.roomY + r1.roomHeight);
    }

}

package byog.Core;


import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Date;
import java.util.List;

public class Room {
    public int roomWidth;
    public int roomHeight;
    public int roomX;
    public int roomY;

    public Room() {
    }

    public Room(int roomWidth, int roomHeigh, int roomX, int roomY) {
        this.roomWidth = roomWidth;
        this.roomHeight = roomHeigh;
        this.roomX = roomX;
        this.roomY = roomY;
    }

    public static void createRooms(Room[] rooms) {
        for (int i = 0; i < rooms.length; i++) {
            int roomWidth = Game.rand.nextInt(Math.min(Data.HEIGHT, Data.WIDTH) / 5) + 2;
            if (roomWidth < 3) {
                roomWidth += 4;
            }
            int roomHeight = Game.rand.nextInt(Math.min(Data.HEIGHT, Data.WIDTH) / 5) + 2;
            if (roomHeight < 3) {
                roomHeight += 3;
            }
            int roomX = Game.rand.nextInt(Data.WIDTH - roomWidth - 2) + 1;
            int roomY = Game.rand.nextInt(Data.HEIGHT - roomHeight - 5) + 1;
            rooms[i] = new Room(roomWidth, roomHeight, roomX, roomY);
        }
    }

    public static void drawRoom(TETile[][] world, Room room) {
        for (int i = 0; i < room.roomWidth; i++) {
            for (int j = 0; j < room.roomHeight; j++) {
                if (world[i][j] != Tileset.FLOOR) {
                    world[room.roomX][room.roomY + j] = Tileset.WALL;
                }
                if (world[room.roomX + room.roomWidth - 1][room.roomY + j] != Tileset.FLOOR) {
                    world[room.roomX + room.roomWidth - 1][room.roomY + j] = Tileset.WALL;
                }
            }
            if (world[room.roomX + i][room.roomY] != Tileset.FLOOR) {
                world[room.roomX + i][room.roomY] = Tileset.WALL;
            }
            if (world[room.roomX + i][room.roomY + room.roomHeight - 1] != Tileset.FLOOR) {
                world[room.roomX + i][room.roomY + room.roomHeight - 1] = Tileset.WALL;
            }
        }
    }

    public static void fillRooms(TETile[][] world, Room room) {
        for (int i = 1; i < room.roomWidth - 1; i++) {
            for (int j = 1; j < room.roomHeight - 1; j++) {
                world[room.roomX + i][room.roomY + j] = Tileset.FLOOR;
            }
        }
    }

    public static void fillAll(TETile[][] world, int numRooms, Room[] rooms) {
        for (int count = 0; count < numRooms; count += 1) {
            Room.fillRooms(world, rooms[count]);
        }
        for (int count = 0; count < numRooms; count += 1) {
            Room.fillRooms(world, rooms[count]);
        }
    }


    public static void drawRooms(TETile[][] world, int numRooms, Room[] rooms) {
        for (int count = 0; count < numRooms; count += 1) {
            Room.drawRoom(world, rooms[count]);
        }
    }

    public static Position randomPosition(Room r) {
        return new Position (Game.rand.nextInt(r.roomWidth) + r.roomX,
                Game.rand.nextInt(r.roomHeight) + r.roomY);
    }

    public static Position central(Room room) {
        int centralX = room.roomX + room.roomWidth / 2;
        int centralY = room.roomY + room.roomHeight / 2;
        return new Position(centralX, centralY);
    }

    public static Direction.dir compareRoom(Room r1, Room r2){
        int x = central(r1).x - central(r2).x;
        int y = central(r1).y - central(r2).y;
        if (x < 0 && y < 0){
            return Direction.dir.TopRight;
        } else if (x < 0 && y > 0) {
            return Direction.dir.BottomRight;
        } else if (x > 0 && y > 0) {
            return Direction.dir.TopLeft;
        } else if(x > 0 && y < 0) {
            return Direction.dir.BottomLeft;
        } else if (x ==0 && y == 0){
            return Direction.dir.Central;
        } else if (x == 0 && y > 0){
            return Direction.dir.Bottom;
        } else if (x == 0 && y < 0){
            return Direction.dir.Top;
        } else if (x < 0 && y == 0) {
            return Direction.dir.Right;
        } else if (x > 0 && y == 0){
            return Direction.dir.Left;
        }
        throw new RuntimeException("check direction");
    }

    public static Direction.dir relativeDirection(Room r1, Room r2) {
        if (r1.roomX <= r2.roomX + r2.roomWidth
                && r2.roomX <= r1.roomX + r1.roomWidth) {
            if (r2.roomY >= r1.roomY + r1.roomHeight) {
                return Direction.dir.Top;
            } else if (r2.roomY + r2.roomHeight < r1.roomY) {
                return Direction.dir.Bottom;
            }
        } else if (r1.roomY <= r2.roomY + r2.roomHeight
                && r2.roomY <= r1.roomY + r1.roomHeight) {
            if (r1.roomX + r1.roomWidth < r2.roomX) {
                return Direction.dir.Right;
            } else if (r2.roomX + r2.roomWidth <= r1.roomX) {
                return Direction.dir.Left;
            }
        } else if (r1.roomX >= r2.roomX + r2.roomWidth) {
            if (r2.roomY >= r1.roomY + r1.roomHeight) {
                return Direction.dir.TopLeft;
            } else {
                return Direction.dir.BottomLeft;
            }
        } else if (r1.roomX + r1.roomWidth <= r2.roomX) {
            if (r2.roomY >= r1.roomY + r1.roomHeight) {
                return Direction.dir.TopRight;
            } else {
                return Direction.dir.BottomRight;
            }
        }

        return Direction.dir.Interaction;
    }



}

package byog.Core;


import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.io.Serializable;


public class Room implements Serializable {
    public int roomWidth;
    public int roomHeight;
    public int roomX;
    public int roomY;

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

    private static void drawRoom(TETile[][] world, Room room) {
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

    private static void fillRooms(TETile[][] world, Room room) {
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





}

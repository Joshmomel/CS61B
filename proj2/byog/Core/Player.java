package byog.Core;

import byog.TileEngine.TETile;

import java.io.Serializable;

public class Player implements Serializable {
    public Position position;
    public TETile playerTile;
    public String playerName;
    public int flowers = 0;


    public Player(Position p, TETile playerTile, String playerName) {
        this.position = p;
        this.playerTile = playerTile;
        this.playerName = playerName;
    }

    public void placePlayerToWorld(TETile[][] world) {
        world[this.position.x][this.position.y] = this.playerTile;

    }

    public void move(TETile[][] world, Direction direction) {
        if (direction.equals(Direction.TOP)) {
            MapGenerator.moveTop(world, this);
        }
        if (direction.equals(Direction.LEFT)) {
            MapGenerator.moveLeft(world, this);
        }
        if (direction.equals(Direction.DOWN)) {
            MapGenerator.moveDown(world, this);
        }
        if (direction.equals(Direction.RIGHT)) {
            MapGenerator.moveRight(world, this);

        }

    }



}

package byog.Core;

import byog.TileEngine.TETile;

import java.io.Serializable;

public class Player implements Serializable {
    public Position position;
    public TETile playerTile;
    public String playerName;
    public int flowers = 0;

    public Player() {
    }

    public Player(Position p, TETile playerTile, String playerName) {
        this.position = p;
        this.playerTile = playerTile;
        this.playerName = playerName;
    }

    public Player(Position p, TETile playerTile, String playerName, int flowers) {
        this.position = p;
        this.playerTile = playerTile;
        this.playerName = playerName;
        this.flowers = flowers;
    }

    public void placePlayerToWorld(TETile[][] world) {
        world[this.position.x][this.position.y] = this.playerTile;

    }

    public void move(TETile[][] world, Direction direction) {
        if (direction.equals(Direction.TOP)) {
            MapGenerator.moveTop(world, this);
            System.out.println("playerObj " + this.flowers);
        }
        if (direction.equals(Direction.LEFT)) {
            MapGenerator.moveLeft(world, this);
            System.out.println("playerObj " + this.flowers);

        }
        if (direction.equals(Direction.DOWN)) {
            MapGenerator.moveDown(world, this);
            System.out.println("playerObj " + this.flowers);

        }
        if (direction.equals(Direction.RIGHT)) {
            MapGenerator.moveRight(world, this);
            System.out.println("playerObj " + this.flowers);
        }
    }

    public void moveNoDraw(TETile[][] world, Direction direction) {
        if (direction.equals(Direction.TOP)) {
            MapGenerator.moveNoDrawTop(world, this);
        }
        if (direction.equals(Direction.LEFT)) {
            MapGenerator.moveNoDrawLeft(world, this);

        }
        if (direction.equals(Direction.DOWN)) {
            MapGenerator.moveNoDrawDown(world, this);

        }
        if (direction.equals(Direction.RIGHT)) {
            MapGenerator.moveNoDrawRight(world, this);
        }
    }



}

package byog.Core;

import byog.TileEngine.TETile;

public class Player {
    public Position position;
    public TETile playerTile;
    public String playerName;


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

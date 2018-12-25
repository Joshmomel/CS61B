package byog.Core;

import byog.TileEngine.TETile;

import java.io.Serializable;

public class OutputObject implements Serializable {
    public Player player1 = new Player();
    public Player player2 = new Player();
    public TETile[][] world;

    public OutputObject(Player player1, Player player2, TETile[][] world) {
        this.player1.flowers = player1.flowers;
        this.player1.position = player1.position;
        this.player1.playerName = player1.playerName;
        this.player1.playerTile = player1.playerTile;
        this.player2.flowers = player2.flowers;
        this.player2.position = player2.position;
        this.player2.playerName = player2.playerName;
        this.player2.playerTile = player2.playerTile;

        this.world = world;
    }
}

package byog.Core;

import byog.TileEngine.TETile;

import java.io.Serializable;

public class OutputObject implements Serializable {
    public Player player1;
    public Player player2;
    public TETile[][] world;

    public OutputObject(Player player1, Player player2, TETile[][] world) {
        this.player1 = player1;
        this.player2 = player2;
        this.world = world;
    }
}

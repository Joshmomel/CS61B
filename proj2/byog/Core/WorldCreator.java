package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

public class WorldCreator {
    public static TETile[][] world;

    public static TETile[][] createEmptyWorld() {
        world = new TETile[Data.WIDTH][Data.HEIGHT];
        for (int x = 0; x < Data.WIDTH; x += 1) {
            for (int y = 0; y < Data.HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }
        return world;
    }

    public static void display(TETile[][] world) {
        TERenderer ter = new TERenderer();
        ter.initialize(Data.WIDTH, Data.HEIGHT);
        ter.renderFrame(world);
    }

}

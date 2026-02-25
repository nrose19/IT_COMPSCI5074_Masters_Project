package structures.basic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Grid represents the 9x5 board and stores all 45 tiles.
 */
public final class Grid {

    public static final int WIDTH = 9;
    public static final int HEIGHT = 5;
    public static final int SIZE = WIDTH * HEIGHT;

    private static final int TILE_WIDTH = 100;
    private static final int TILE_HEIGHT = 100;
    private static final int ORIGIN_X = 0;
    private static final int ORIGIN_Y = 0;

    // Change this if your project uses a different texture
    private static final String DEFAULT_TEXTURE = "assets/images/tile.png";

    private final Tile[] tiles;

    public Grid() {
        tiles = new Tile[SIZE];

        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {

                int index = toIndex(x, y);

                int xpos = ORIGIN_X + x * TILE_WIDTH;
                int ypos = ORIGIN_Y + y * TILE_HEIGHT;

                tiles[index] = new Tile(
                        DEFAULT_TEXTURE,
                        xpos,
                        ypos,
                        TILE_WIDTH,
                        TILE_HEIGHT,
                        x,
                        y
                );
            }
        }
    }

    public boolean inBounds(int x, int y) {
        return x >= 0 && x < WIDTH && y >= 0 && y < HEIGHT;
    }

    public int toIndex(int x, int y) {
        if (!inBounds(x, y)) {
            throw new IllegalArgumentException("Out of bounds: " + x + "," + y);
        }
        return y * WIDTH + x;
    }

    public Position fromIndex(int index) {
        if (index < 0 || index >= SIZE) {
            throw new IllegalArgumentException("Invalid index: " + index);
        }
        int y = index / WIDTH;
        int x = index % WIDTH;
        return new Position(x, y);
    }

    public Tile getTile(int x, int y) {
        return tiles[toIndex(x, y)];
    }

    public Tile getTile(int index) {
        return tiles[index];
    }

    public List<Tile> getAllTiles() {
        List<Tile> list = new ArrayList<>(SIZE);
        Collections.addAll(list, tiles);
        return Collections.unmodifiableList(list);
    }

    public List<Position> neighbours4(int x, int y) {
        List<Position> result = new ArrayList<>();

        addIfValid(result, x, y - 1);
        addIfValid(result, x, y + 1);
        addIfValid(result, x - 1, y);
        addIfValid(result, x + 1, y);

        return result;
    }

    private void addIfValid(List<Position> list, int x, int y) {
        if (inBounds(x, y)) {
            list.add(new Position(x, y));
        }
    }
}

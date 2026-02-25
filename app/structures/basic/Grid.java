package structures.basic;
package structures.basic;

import java.util.ArrayList;
import java.util.List;

public final class Grid {

    public static final int WIDTH = 9;
    public static final int HEIGHT = 5;

    public boolean inBounds(int x, int y) {
        return x >= 0 && x < WIDTH && y >= 0 && y < HEIGHT;
    }

    public int toIndex(int x, int y) {
        if (!inBounds(x, y))
            throw new IllegalArgumentException("Out of bounds: " + x + "," + y);
        return y * WIDTH + x;
    }

    public Position fromIndex(int index) {
        if (index < 0 || index >= WIDTH * HEIGHT)
            throw new IllegalArgumentException("Invalid index: " + index);

        int y = index / WIDTH;
        int x = index % WIDTH;
        return new Position(x, y);
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
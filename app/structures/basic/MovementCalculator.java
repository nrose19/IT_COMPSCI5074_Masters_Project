package structures.basic;


import java.util.ArrayList;

public class MovementCalculator {
    private Grid grid;

    public MovementCalculator(Grid grid){
        this.grid = grid;

    }

    public ArrayList<Tile> getValidMovementTiles(int x, int y){
       //goal of class - locate unit's position and get valid surrounding tiles

        //local list of valid tiles
        ArrayList<Tile> validTiles = new ArrayList<>();

        //need units position = rows and cols
       Tile tile = grid.getTile(x, y);

        //verify valid tiles in cardinal directions/diagonally
        // four cardinal +x ++x, -y --y (etc.), diagonally +x -y, +x +y (etc.)
        int [][] directions = {
                {-1,-1},{-1,1},{1,-1},{-1,0},{0,-1},{1,0},{0,1},{1,1}
        };
        // List of lists -- list of coordinates, then coordinates themselves are a list
        // OR massive if statement
        for (int [] dir:directions){
            int newX = x + dir[0];
            int newY = y + dir[1];

            //verify if cols/rows are in bounds
            if (grid.inBounds(newX , newY)){
                tile = grid.getTile(newX,newY);

                validTiles.add(tile);
            }

            //may need to add later logic to prove if the unit is being blocked by anything
        }

        //return valid tiles
        return validTiles;
    }


}

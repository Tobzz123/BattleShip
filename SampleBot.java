
import battleship.BattleShip;
import battleship.CellState;

import java.awt.Point;
import java.util.Random;

/**
 * A Sample random shooter - Takes no precaution on double shooting and has no strategy once
 * a ship is hit.
 *
 * @author Mitchell Aninyang - 000796709
 * @author Anthony-Egorp, Olaoluwa - 000776467
 */
public class SampleBot {
    private int gameSize;
    private BattleShip battleShip;
    //private Random random;
    private CellState[][] map;        //Array to save state for each cell
    private int shipsSunk = 0;              //Number of ships sunk
    private int xHunt = 0;                  //X-coordinate used in the hunt method
    private int yHunt = 0;                  //Y-coordinate used in the hunt method

    /**
     * Constructor keeps a copy of the BattleShip instance
     *
     * @param b previously created battleship instance - should be a new game
     */
    public SampleBot(BattleShip b) {
        battleShip = b;
        gameSize = b.boardSize;
        //random = new Random();   // Needed for random shooter - not required for more systematic approaches

        map = new CellState[gameSize][gameSize];  // Array of cell state
        // Initialize cell states as empty
        for (int r = 0; r < gameSize; r++) {
            for (int c = 0; c < gameSize; c++) {
                map[r][c] = CellState.Empty;
            }
        }

    }



    /**
     * This method methodically shoots at cells, calling the sink method when a there is a hit.
     */
    public void hunt() {
        boolean hit;    // Whether or not a shot is a hit

        // Shoot at every third cell, avoiding non-empty cells
        while (map[xHunt][yHunt] != CellState.Empty) {
            if (xHunt < gameSize - 3) {
                xHunt += 3;
            } else {
                if (yHunt < gameSize - 1) {
                    yHunt += 1;
                }
                if (xHunt == gameSize - 3) {
                    xHunt = 0;
                } else if (xHunt == gameSize - 2) {
                    xHunt = 1;
                } else {
                    xHunt = 2;
                }
            }

            // Shift pattern one cell over
            if (xHunt == gameSize-1 && yHunt == gameSize-1 && battleShip.allSunk() == false) {
                hit = fireShot(xHunt, yHunt);

                if (hit) {
                    sink(xHunt, yHunt);
                }

                xHunt = 1;
                yHunt = 0;
            }

            // Shift pattern one cell over again
            if (xHunt == gameSize-3 && yHunt == gameSize-1 && battleShip.allSunk() == false) {
                hit = fireShot(xHunt, yHunt);

                if (hit) {
                    sink(xHunt, yHunt);
                }

                xHunt = 2;
                yHunt = 0;
            }
        }

        // Fire a shot
        hit = fireShot(xHunt, yHunt);

        // If the shot returns a hit, call the sink method
        if (hit) {
            sink(xHunt, yHunt);
        }
    }



    /**
     * This method shoots at all surrounding cells of cell that is hit
     * @param x X coordinate of original hit
     * @param y Y coordinate of original hit
     */
    public void sink(int x, int y) {
        boolean hit;    // Whether or not a shot is a hit

        // Check cell to left of hit
        if (x > 0 && map[x - 1][y] == CellState.Empty && battleShip.numberOfShipsSunk() == shipsSunk) {
            hit = fireShot(x - 1, y);
            if (hit) {
                sink(x - 1, y);
            }
        }
        // Check cell to right of hit
        if (x < gameSize - 1 && map[x + 1][y] == CellState.Empty && battleShip.numberOfShipsSunk() == shipsSunk) {
            hit = fireShot(x + 1, y);
            if (hit) {
                sink(x + 1, y);
            }
        }
        // Check cell below hit
        if (y > 0 && map[x][y - 1] == CellState.Empty && battleShip.numberOfShipsSunk() == shipsSunk) {
            hit = fireShot(x, y - 1);
            if (hit) {
                sink(x, y - 1);
            }
        }
        // Check cell above hit
        if (y < gameSize - 1 && map[x][y + 1] == CellState.Empty && battleShip.numberOfShipsSunk() == shipsSunk) {
            hit = fireShot(x, y + 1);
            if (hit) {
                sink(x, y + 1);
            }
        }

        // If ship is sunk, update number of ships sunk
        if (battleShip.numberOfShipsSunk() == (shipsSunk + 1)) {
            shipsSunk++;
        }
    }




    /**
     * Create a random shot and calls the battleship shoot method
     *
     * @return true if a Ship is hit, false otherwise
     */

    public boolean fireShot(int x, int y) {
        Point shot = new Point(x, y);
        boolean hit = battleShip.shoot(shot);

        if (hit) {
            map[x][y] = CellState.Hit;
        } else {
            map[x][y] = CellState.Miss;
        }

        return hit;
    }
}

import java.util.LinkedList;
import java.util.List;

public class Board {
    private final int[][] tiles;
    private final int hamming;
    private final int manhattan;
    private List<Board> neighbors;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        this.tiles = new int[tiles.length][tiles.length];

        int hammingDist = 0;
        int manhattanDist = 0;

        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
                int tile = tiles[i][j];
                this.tiles[i][j] = tile;

                if (tile == 0) continue;

                if (tile != i * dimension() + j + 1) hammingDist++;

                tile--;
                int tileI = tile / dimension();
                int tileJ = tile - tileI * dimension();
                int dist = Math.abs(i - tileI) + Math.abs(j - tileJ);
                manhattanDist += dist;
            }
        }
        this.hamming = hammingDist;
        this.manhattan = manhattanDist;
    }
                                           
    // string representation of this board
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(dimension());
        
        for (int i = 0; i < dimension(); i++) {
            stringBuilder.append("\n");
            for (int j = 0; j < dimension(); j++) {
                stringBuilder.append(" " + tiles[i][j]);
            }
        }

        return stringBuilder.toString();
    }

    // board dimension n
    public int dimension() {
        return tiles[0].length;
    }

    // number of tiles out of place
    public int hamming() {
        return hamming;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        return manhattan;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return hamming() == 0;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == null) return false;
        if (this.getClass() != y.getClass()) return false;

        Board board = (Board) y;

        if (dimension() != board.dimension()) return false;

        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
                if (tiles[i][j] != board.tiles[i][j]) return false;
            }
        }
        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        if (neighbors != null) return neighbors;

        neighbors = new LinkedList<Board>();
        int zeroI = 0;
        int zeroJ = 0;

        outerLoop:
        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
                if (tiles[i][j] == 0) {
                    zeroI = i;
                    zeroJ = j;
                    break outerLoop;
                }
            }
        }

        for (int k = 0; k < 4; k++) {
            int tileI = zeroI;
            int tileJ = zeroJ;

            switch(k) {
                case 0: tileI++;
                        break;
                case 1: tileI--;
                        break;
                case 3: tileJ++;
                        break;
                case 4: tileJ--;
                        break;
            }

            if (tileI < 0 || tileI >= dimension() || tileJ < 0 || tileJ >= dimension()) continue;
            int[][] neighborsTiles = tilesCopy();
            neighborsTiles[zeroI][zeroJ] = neighborsTiles[tileI][tileJ];
            neighborsTiles[tileI][tileJ] = 0;

            Board neighbor = new Board(neighborsTiles);
            neighbors.add(neighbor);
        }

        return neighbors;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        int[][] twin = tilesCopy();

        if (tiles[0][0] != 0 && tiles[0][1] != 0){
            twin[0][0] = tiles[0][1];
            twin[0][1] = tiles[0][0];
        } else {
            twin[1][0] = tiles[1][1];
            twin[1][1] = tiles[1][0];
        }

        return new Board(twin);
    }

    private int[][] tilesCopy() {
        int[][] copy = new int[dimension()][dimension()];

        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
                copy[i][j] = tiles[i][j];
            }
        }

        return copy;
    }
}

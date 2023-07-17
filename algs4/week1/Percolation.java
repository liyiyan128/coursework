import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private int dimension;
    private WeightedQuickUnionUF UF;
    private WeightedQuickUnionUF fullSites;
    private boolean[][] sites;
    private int topSite = 0;
    private int bottomSite = 0;
    private int openSites = 0;

    // Create n by n grid with all sites initially blocked.
    // 0: blocked.
    public Percolation(int n) {
        if (n < 1) {throw new IllegalArgumentException();}  // Corner case.
        
        dimension = n;
        UF = new WeightedQuickUnionUF(n * n + 2);  // Introduce two virtual sites (top/bottom).
        fullSites = new WeightedQuickUnionUF(n * n + 1);
        topSite = n * n;
        bottomSite = n * n + 1;
        sites = new boolean[n][n];
    } 

    // open site(row, col)
    public void open(int row, int col) {
        _check(row, col);

        if (isOpen(row, col)) return;

        int index = _index(row, col);

        // top adjacent site
        if (row == 1) {
            UF.union(index, topSite);
            fullSites.union(index, topSite);
        }else{
            int topRow = row - 1;
            unionIfOpen(topRow, col, index);
        }

        // bottom adjacent site
        if (row == dimension) {
            UF.union(index, bottomSite);
        }else {
            int bottomRow = row + 1;
            unionIfOpen(bottomRow, col, index);
        }

        // right adjacent site
        if (col < dimension) {
            int rightCol = col + 1;
            unionIfOpen(row, rightCol, index);
        }

        // left adjacent site
        if  (col > 1) {
            int leftCol = col - 1;
            unionIfOpen(row, leftCol, index);
        }

        sites[row - 1][col - 1] = true;
        openSites++;
    }


    // Check if site(row, col) is open.
    public boolean isOpen(int row, int col) {
        _check(row, col);

        return sites[row - 1][col - 1];
    }


    // Is site(row, col) full?
    // A site is full if connected to topSite.
    public boolean isFull(int row, int col) {
        _check(row, col);

        int index = _index(row, col);
        // return fullSites.connected(index, topSite);
        return fullSites.find(index) == fullSites.find(topSite);
    }


    // return number of open sites
    public int numberOfOpenSites() {
        return openSites;
    }


    // does the system percolate?
    public boolean percolates() {
        // return UF.connected(topSite, bottomSite);
        return UF.find(topSite) == UF.find(bottomSite);
    }
    

    private int _index(int row, int col) {
        return dimension * (row - 1) + (col - 1);
    }


    private void _check(int row, int col) {
        if (row < 1 || row > dimension || col < 1 || col > dimension) {
            throw new IllegalArgumentException();
        }
    }

    
    private void unionIfOpen(int row, int col, int unionIndex) {
        if (isOpen(row, col)) {
            int index = _index(row, col);
            UF.union(index, unionIndex);
            fullSites.union(index, unionIndex);
        }
    }
}

import java.util.Comparator;
import edu.princeton.cs.algs4.StdDraw;


/**
 *  This is a Point data type that represents a point in the plane.
 */
public class Point implements Comparable<Point> {
    private int x;  // x-coord
    private int y;  // y-coord
    
    /**
     * Initialise a new point.
     * @param x the x-coordinate of the point
     * @param y the y-coordinate of the point
     */
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // draws this point
    public void draw() {
        StdDraw.point(x, y);
    }

    // draws the line segment from this point to that point
    public void drawTo(Point that) {
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    // string representation
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    /**
     * Compare two points by y-coordinate, breaking ties by x-coordinate.
     * (x0, y0) <= (x1, y1) iff (y0 < y1) or (y0 = y1 ^ x0 < x1)
     * 
     * @return 0 if equal
     *         a negative integer if this < that
     *         a positive integer if this > that
     */
    public int compareTo(Point that) {
        if (this.y == that.y) {
            return this.x - that.x;
        } else {
            return this.y - that.y;
        }
    }

    /**
     * Return the slope between (x0, y0) and (x1, y1).
     * slope = (y1 - y0) / (x1 - x0)
     */
    public double slopeTo(Point that) {
        if (this.compareTo(that) == 0) {
            return Double.NEGATIVE_INFINITY;
        } else if (this.y == that.y) {  // horizontal
            return 0.0;
        } else if (this.x == that.y) { // vertical
            return Double.POSITIVE_INFINITY;
        } else {
        return (that.y - this.y) / (double) (that.x - this.x);
        }
    }

    /**
     * Return a comparator that compares two argument points
     * by the slopes they make with the invoking point.
     */
    public Comparator<Point> slopeOrder() {
        return new Comparator<Point>() {
            @Override
            public int compare(Point point0, Point point1) {
                double slope0 = slopeTo(point0);
                double slope1 = slopeTo(point1);

                return Double.compare(slope0, slope1);
            }
        };
    }

}

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;


public class BruteCollinearPoints {
    private LineSegment[] segments = null;  // segments containning 4 points

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException();
        }

        Point[] pointsCopy = new Point[points.length];
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) {
                throw new IllegalArgumentException();
            }
        }

        Arrays.sort(pointsCopy);
        List<LineSegment> lineSegments = new LinkedList<>();
        Point previousPoint = null;

        for (int i = 0; i < points.length; i++) {
            if (previousPoint != null && pointsCopy[i].compareTo(previousPoint) == 0) {
                throw new IllegalArgumentException();
            } else {
                previousPoint = pointsCopy[i];
            }

            for (int j = i + 1; j < points.length; j++) {
                for (int k = j + 1; k < points.length; k++) {
                    for (int l = k + 1; l < pointsCopy.length; l++) {
                        if (Double.compare(pointsCopy[i], pointsCopy[j]) == 0
                            && Double.compare(pointsCopy[k], pointsCopy[l]) == 0) {
                                LineSegment segment = new LineSegment(pointsCopy[i], pointsCopy[l]);
                                lineSegments.add(segment);
                            }
                    }
                }   
            }
        }

        segments = lineSegments.toArray(new LineSegment[lineSegments.size()]);
    }

    // the number of line segments
    public int numberOfSegments() {
        return segments.length;
    }

    // the line segments
    public LineSegment[] segments() {
        return segments.clone();
    }

    // sample client
    public static void main(String[] args) {

    // read the n points from a file
    In in = new In(args[0]);
    int n = in.readInt();
    Point[] points = new Point[n];
    for (int i = 0; i < n; i++) {
        int x = in.readInt();
        int y = in.readInt();
        points[i] = new Point(x, y);
    }

    // draw the points
    StdDraw.enableDoubleBuffering();
    StdDraw.setXscale(0, 32768);
    StdDraw.setYscale(0, 32768);
    for (Point p : points) {
        p.draw();
    }
    StdDraw.show();

    // print and draw the line segments
    BruteCollinearPoints collinear = new BruteCollinearPoints(points);
    for (LineSegment segment : collinear.segments()) {
        StdOut.println(segment);
        segment.draw();
    }
    StdDraw.show();
    }

}

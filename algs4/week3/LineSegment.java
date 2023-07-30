public class LineSegment {
    private Point p;
    private Point q;

    /**
     * Initialise a new line segment between points p and q.
     * 
     * @param p one endpoint of the line segment
     * @param q the other endpoint of the line segment
     */
    public LineSegment(Point p, Point q) {
        if (p == null || q == null) {
            throw new NullPointerException();
        }

        this.p = p;
        this.q = q;
    }

    // draws this line segment
    public   void draw() {
        p.drawTo(q);
    }

    // string representation
    public String toString() {
        return p + " -> " + q;
    }

}

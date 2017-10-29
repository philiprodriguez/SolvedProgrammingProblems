/*
Line intersection...
*/

import java.util.*;

public class mySolution
{
    public static void main(String[] args)
    {
        Scanner scan = new Scanner(System.in);
        int numPhotos = scan.nextInt();
        for(int p = 0; p < numPhotos; p++)
        {
            //Read points in...
            Point b, s, p1, p2;
            b = new Point(scan.nextInt(), scan.nextInt());
            s = new Point(scan.nextInt(), scan.nextInt());
            p1 = new Point(scan.nextInt(), scan.nextInt());
            p2 = new Point(scan.nextInt(), scan.nextInt());

            //determine if lines intersect...
            if (new LineSegment(b, s).intersects(new LineSegment(p1, p2)))
            {
                System.out.println("Move to the left or right!");
            }
            else
            {
                System.out.println("Good picture, Birdman!");
            }
        }
    }

    public static final double eps = 0.000000001;
    public static boolean doubleEquals(double d1, double d2)
    {
        return Math.abs(d2-d1) <= eps;
    }

    private static class Point
    {
        double x, y;
        public Point(double x, double y)
        {
            this.x = x;
            this.y = y;
        }

        public double distance(Point p)
        {
            return Math.sqrt(Math.pow(p.x-x,2)+Math.pow(p.y-y,2));
        }
    }

    private static class LineSegment
    {
        Point p1, p2;
        public LineSegment(Point p1, Point p2)
        {
            this.p1 = p1;
            this.p2 = p2;
        }

        public double length()
        {
            return p1.distance(p2);
        }

        public boolean intersects(LineSegment lineSegment)
        {
            Line line1 = new Line(this);
            Line line2 = new Line(lineSegment);
            if (!line1.intersects(line2))
            {
                return false;
            }
            else
            {
                Point ip = line1.intersectionPoint(line2);
                if (ip == null)
                {
                    //All points are colinear
                    //Check for point intersection(s)...
                    if (pointOnLineSegment(lineSegment.p1) || pointOnLineSegment(lineSegment.p2) || lineSegment.pointOnLineSegment(p1) || lineSegment.pointOnLineSegment(p2))
                        return true;
                    return false;
                }
                else
                {
                    //is ip on our line segments?
                    return pointOnLineSegment(ip) && lineSegment.pointOnLineSegment(ip);
                }
            }
        }

        public boolean pointOnLineSegment(Point p)
        {
            if (doubleEquals(this.length(), p1.distance(p)+p2.distance(p)))
            {
                return true;
            }
            else
            {
                return false;
            }
        }
    }

    private static class Line
    {
        double a, b, c;

        //Constructor for going from line segment (2 points) to infinite line
        public Line(LineSegment lineSegment)
        {
            //A is y2-y1
            this.a = lineSegment.p2.y-lineSegment.p1.y;

            //B is x1-x2
            this.b = lineSegment.p1.x-lineSegment.p2.x;

            //C is Ax+By
            this.c = this.a*lineSegment.p1.x + this.b*lineSegment.p1.y;
        }

        //Returns true iff line intersects this...
        public boolean intersects(Line line)
        {
            if (intersectionPoint(line) != null)
            {
                return true;
            }
            else
            {
                //Either infinite intersection (same line) or no intersection...
                if (equivalent(line))
                {
                    return true;
                }
                else
                {
                    return false;
                }
            }
        }

        //Returns null if no intersection or infinite intersections
        public Point intersectionPoint(Line line)
        {
            double det = a*line.b - line.a*b;
            if (det == 0)
            {
                //Parallel lines
                return null;
            }
            else
            {
                double x = (line.b*c - b*line.c)/det;
                double y = (a*line.c-line.a*c)/det;
                return new Point(x, y);
            }
        }

        //Returns true if point is on the line...
        public boolean pointOnLine(Point p)
        {
            //Well, our point (x,y) is on our line if Ax+By=C
            return a*p.x+b*p.y == c;
        }

        //Returns true if line is equivalent to this...
        //Note this is different from equals, since our member variables may
        //still be different...
        public boolean equivalent(Line line)
        {
            //Two lines are equivalent if they are parallel and share either
            //their x intercept or y intercept or both...
            if (intersectionPoint(line) != null)
            {
                //Not Parallel
                return false;
            }
            else if (a != 0 && c/a == line.c/line.a)
            {
                //Same x intercept
                return true;
            }
            else if (b != 0 && c/b == line.c/line.b)
            {
                //Same y intercept
                return true;
            }
            else
            {
                return false;
            }
        }
    }
}

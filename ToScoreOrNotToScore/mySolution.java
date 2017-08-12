import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class k {
	public static void main(String[] args)
	{
		Scanner scan = new Scanner(System.in);
		
		int numCases = scan.nextInt();
		for(int curCase = 0; curCase < numCases; curCase++)
		{
			HashMap<Vector, ArrayList<Vector>> adjList = new HashMap<>();
			int numPlayersPerTeam = scan.nextInt();
			ArrayList<Vector> team1 = new ArrayList<>();
			for(int p = 0; p < numPlayersPerTeam; p++)
			{
				Vector point = new Vector(scan.nextDouble(), scan.nextDouble());
				team1.add(point);
			}
			ArrayList<Vector> team2 = new ArrayList<>();
			for(int p = 0; p < numPlayersPerTeam; p++)
			{
				Vector point = new Vector(scan.nextDouble(), scan.nextDouble());
				team2.add(point);
			}
			
			//Last member of team 1 is the goal
			team1.add(new Vector(scan.nextDouble(), scan.nextDouble()));
			
			for(int i = 0; i < team1.size(); i++)
			{
				for(int j = i+1; j < team1.size(); j++)
				{
					if (canAddEdge(i, j, team1, team2))
					{
						adjList.putIfAbsent(team1.get(i), new ArrayList<Vector>());
						adjList.putIfAbsent(team1.get(j), new ArrayList<Vector>());
						
						adjList.get(team1.get(i)).add(team1.get(j));
					}
				}
			}
			
			boolean cando = true;
			
			//nobody removed?
			if (!isPath(team1.get(0), team1.get(team1.size()-1), null, adjList))
			{
				cando = false;
			}
			
			for(int r = 1; r < team1.size()-1; r++)
			{
				//Try removing r
				if (!isPath(team1.get(0), team1.get(team1.size()-1), team1.get(r), adjList))
				{
					cando = false;
					break;
				}
			}
			if (cando)
			{
				System.out.println("Goal");
			}
			else
			{
				System.out.println("No goal");
			}
		}
	}
	
	public static boolean isPath(Vector start, Vector end, Vector disallowed, HashMap<Vector, ArrayList<Vector>> adjList)
	{
		HashSet<Vector> visited = new HashSet<Vector>();
		Queue<Vector> q = new LinkedList<>();
		q.add(start);
		
		while(!q.isEmpty())
		{
			Vector cur = q.poll();
			if (cur.equals(end))
			{
				return true;
			}
			
			for(Vector dest : adjList.get(cur))
			{
				if (!visited.contains(dest))
				{
					if (!dest.equals(disallowed))
					{
						q.add(dest);
						visited.add(dest);
					}
				}
			}
		}
		return false;
	}
	
	public static boolean canAddEdge(int i, int j, ArrayList<Vector> team1, ArrayList<Vector> team2)
	{
		Line passLine = new Line(team1.get(i), team1.get(j));
		for(int t2 = 0; t2 < team2.size(); t2++)
		{
			Vector cpol = passLine.closestPointOnLineToPoint(team2.get(t2));
			if (cpol == null)
				continue;
			double theirDist = team2.get(t2).distanceTo(cpol);
			double idist = team1.get(i).distanceTo(cpol);
			if (theirDist <= (1.0/3.0)*idist)
			{
				return false;
			}
		}
		return true;
	}
	
	
	public static double epsilon = 1e-9;
	public static boolean doubleEquals(double d1, double d2)
	{
		return Math.abs(d2-d1) <= epsilon;
	}
	
	private static class Vector
	{
		double x,y;
		
		public Vector(double x, double y)
		{
			this.x = x;
			this.y = y;
		}
		
		public double getMagnitude()
		{
			return Math.sqrt(Math.pow(x, 2)+Math.pow(y, 2));
		}
		
		public double dot(Vector b)
		{
			return this.x*b.x + this.y*b.y;
		}
		
		public double cross(Vector b)
		{
			return this.x*b.y - b.x*this.y;
		}
		
		public double distanceTo(Vector point)
		{
			return Math.sqrt(Math.pow(point.x-x, 2)+Math.pow(point.y-y, 2));
		}
		
		public double getDirection()
		{
			double dir = Math.atan2(y, x);
			if (dir < 0)
				dir = Math.PI*2+dir;
			return dir;
		}
		
		public Vector toUnit()
		{
			return this.multiply(1/getMagnitude());
		}
		
		public Vector getRotation(double angleRadians)
		{
			double newDirection = getDirection()+angleRadians;
			Vector rotatedVector = new Vector(Math.cos(newDirection), Math.sin(newDirection));
			rotatedVector = rotatedVector.multiply(getMagnitude());
			return rotatedVector;
		}
		
		public Vector add(Vector v)
		{
			return new Vector(x+v.x, y+v.y);
		}
		
		public Vector subtract(Vector v)
		{
			return new Vector(x-v.x, y-v.y);
		}
		
		public Vector multiply(double scalar)
		{
			return new Vector(x*scalar, y*scalar);
		}
		
		public String toString()
		{
			return "<" + x + ", " + y + ">";
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			long temp;
			temp = Double.doubleToLongBits(x);
			result = prime * result + (int) (temp ^ (temp >>> 32));
			temp = Double.doubleToLongBits(y);
			result = prime * result + (int) (temp ^ (temp >>> 32));
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Vector other = (Vector) obj;
			if (Double.doubleToLongBits(x) != Double.doubleToLongBits(other.x))
				return false;
			if (Double.doubleToLongBits(y) != Double.doubleToLongBits(other.y))
				return false;
			return true;
		}
		
		
	}
	
	public static class Line
	{
		Vector start, end;
		public Line(Vector start, Vector end)
		{
			this.start = start;
			this.end = end;
		}
		
		public double length()
		{
			return start.distanceTo(end);
		}
		
		public boolean pointInBoundingBox(Vector v)
		{
			double minx = Math.min(start.x, end.x);
			double miny = Math.min(start.y, end.y);
			double maxx = Math.max(start.x, end.x);
			double maxy = Math.max(start.y, end.y);
			return v.x+epsilon >= minx && v.x-epsilon <= maxx && v.y+epsilon >= miny && v.y-epsilon <= maxy;
		}
		
		public Vector closestPointOnLineToPoint(Vector point)
		{
			Vector lineRep = this.end.subtract(this.start);
			point = point.subtract(this.start);
			
			double adj = lineRep.dot(point)/lineRep.getMagnitude();
			
			
			Vector aolPoint = new Vector(lineRep.x, lineRep.y).toUnit().multiply(adj);
			
			Vector ans = aolPoint.add(this.start);
			if (!pointInBoundingBox(ans))
				return null;
			return ans;
		}
		
		
	}
}

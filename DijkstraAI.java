import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.*;
import java.util.HashMap; 
import java.util.Map; 

/// A sample AI that takes a very suboptimal path.
/**
 * This is a sample AI that moves as far horizontally as necessary to reach the target,
 * then as far vertically as necessary to reach the target.  It is intended primarily as
 * a demonstration of the various pieces of the program.
 * 
 */

/*
class Node implements Comparable <Node> {
    private final Point current;
    private Point parent;
    private double score;
    private double estimatedScore; 



    Node(Point current){
        this(current,null,Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
    }

    Node(Point current,Point parent, double score, double estimatedScore){
        this.current = current;
        this.parent = parent;
        this.score = score;
        this.estimatedScore = estimatedScore;
    }

    public Point getCurrent(){
        return this.current;
    }

    public Point getParent(){
        return this.parent;
    }

    public double getScore(){
        return this.score;
    }

    public double getEstimatedScore(){
        return this.estimatedScore;
    }

    public void setParent(Point parent){
        this.parent = parent;
    }

    public void setScore(double score){
        this.score = score;
    }

    public void setEstimatedScore(double estimatedScore){
        this.estimatedScore = estimatedScore;
    }

    @Override
    public int compareTo(Node otherNode){
        if (this.estimatedScore > otherNode.estimatedScore){
            return 1;
        } else if (this.estimatedScore < otherNode.estimatedScore){
            return -1;
        } else {
            return 0;
        }
    }
}
*/


public class DijkstraAI implements AIModule
{
    /// Creates the path to the goal.
    public List<Point> createPath(final TerrainMap map)
    {
        // Holds the resulting path
        final ArrayList<Point> path = new ArrayList<Point>();
        // Keep track of where we are and add the start point.
        final Point CurrentPoint = map.getStartPoint();

        Queue<Node> openSet = new PriorityQueue<>();
        Map<Point,Node> allNodes = new HashMap();

        Node start = new Node(CurrentPoint,null,0d,0d,0d);
        openSet.add(start);
        allNodes.put(CurrentPoint,start);
        boolean[][] processed = new boolean[map.getWidth()][map.getHeight()];



        while(!openSet.isEmpty())
        {
            Node next = openSet.poll();
            processed[next.getCurrent().x][next.getCurrent().y] = true;

            if(next.getCurrent().equals(map.getEndPoint())){
                 Node current = next;
                 do {
                     path.add (0,current.getCurrent());
                     current = allNodes.get(current.getParent());
                 } while (current != null);

                break;
            }

            Point neighbors[] = map.getNeighbors(next.getCurrent());
            
            for (Point neighbor : neighbors) {
                if (processed[neighbor.x][neighbor.y]){
                    continue;
                }
                Node nextNeighbor = allNodes.getOrDefault(neighbor, new Node(neighbor));
                allNodes.put(neighbor, nextNeighbor);

                double gCost = next.getCost() + map.getCost(next.getCurrent(),neighbor);
                double hCost = 0;
                double fCost = gCost + hCost;

                if(fCost < nextNeighbor.getScore()) {
                    nextNeighbor.setParent(next.getCurrent());
                    nextNeighbor.setCost(gCost);
                    nextNeighbor.setScore(fCost);
                    nextNeighbor.setEstimateCost(hCost);
                    openSet.add(nextNeighbor);
                }
            
            };
        }

        return path;

        // We're done!  Hand it back.

    }
}

/*
private double getHeuristic(final TerrainMap map, final Point pt1, final Point pt2)

 {

  ...

 }
 */
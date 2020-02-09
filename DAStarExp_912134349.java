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


public class DAStarExp_912134349 implements AIModule
{
    /// Creates the path to the goal.
    private double getHeuristic(final TerrainMap map, final Point pt1, final Point pt2){
        double diagonalDist = Math.max(Math.abs(pt1.x-pt2.x),Math.abs(pt1.y-pt2.y));
        double heightCurr = map.getTile(pt1);
        double heightGoal = map.getTile(pt2);
        double heightDiff = heightGoal - heightCurr;
        double singleStep = (heightGoal)/(heightGoal + 1 - heightDiff/(diagonalDist));
        double estimate;
        if(diagonalDist > 1){
            estimate = diagonalDist*singleStep;
        }
        else {
            estimate = (heightGoal)/(heightCurr+1);
        }
        return estimate;
    }


    public List<Point> createPath(final TerrainMap map)
    {
        // Holds the resulting path
        final ArrayList<Point> path = new ArrayList<Point>();
        // Keep track of where we are and add the start point.
        final Point currentPoint = map.getStartPoint();
        final Point endPoint = map.getEndPoint();

        Queue<Node> openSet = new PriorityQueue<>();
        Map<Point,Node> allNodes = new HashMap();

        Node start = new Node(currentPoint,null,0d,0d,0d);
        openSet.add(start);
        allNodes.put(currentPoint,start);
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
                
                double gCost = next.getCost() + map.getCost(neighbor,next.getCurrent());
                double hCost = getHeuristic(map,nextNeighbor.getCurrent(),endPoint);
                double fCost = gCost + hCost;

                if(fCost < nextNeighbor.getScore()) {
                    nextNeighbor.setParent(next.getCurrent());
                    nextNeighbor.setCost(gCost);
                    nextNeighbor.setEstimateCost(hCost);
                    nextNeighbor.setScore(fCost);
                    openSet.add(nextNeighbor);
                }
            
            }
        }

        return path;

        // We're done!  Hand it back.

    }
}



 
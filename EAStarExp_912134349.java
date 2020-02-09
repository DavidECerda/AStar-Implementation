import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.*;
import java.util.HashMap; 
import java.util.Map;
import java.util.Random;  

/// A sample AI that takes a very suboptimal path.
/**
 * This is a sample AI that moves as far horizontally as necessary to reach the target,
 * then as far vertically as necessary to reach the target.  It is intended primarily as
 * a demonstration of the various pieces of the program.
 * 
 */


public class EAStarExp_912134349 implements AIModule
{
    /// Heuristic for exp cost

    private double getHeuristic(final TerrainMap map, final Point pt1, final Point pt2)
    {
        double diagonalDist = Math.max(Math.abs(pt1.x-pt2.x),Math.abs(pt1.y-pt2.y));
        double heightCurr = map.getTile(pt1);
        double heightGoal = map.getTile(pt2);
        double heightDiff = heightGoal - heightCurr;
        double estimate;

        if(heightDiff > 0){
            if(heightDiff > 2 * diagonalDist){
            estimate = heightDiff*2;
            }
            else{
                estimate = diagonalDist * Math.pow(2.0,(heightDiff/diagonalDist));
            }
        }
        else if (heightCurr == heightGoal){
            estimate = diagonalDist;
        }
        else{
            estimate = diagonalDist*Math.pow(2,(heightDiff/diagonalDist));
        }

        return estimate;
    }
    

    public List<Point> createPath(final TerrainMap map)
    {
        // Holds the resulting path
        final ArrayList<Point> path = new ArrayList<Point>();
        // Keep track of where we are and add the start point.
        final Point startPoint = map.getStartPoint();
        final Point endPoint = map.getEndPoint();

        Queue<Node> openStartSet = new PriorityQueue<>();
        Map<Point,Node> allStartNodes = new HashMap();

        Node start = new Node(startPoint,null,0d,0d,0d);
        openStartSet.add(start);
        allStartNodes.put(startPoint,start);
        boolean[][] processed = new boolean[map.getWidth()][map.getHeight()];

        
        Node nextStart = new Node(null);

        while(!openStartSet.isEmpty())
        {
            nextStart = openStartSet.poll();
            processed[nextStart.getCurrent().x][nextStart.getCurrent().y] = true;

            if(nextStart.getCurrent().equals(endPoint)){
                break;
            }

            Point neighbors[] = map.getNeighbors(nextStart.getCurrent());
            
            for (Point neighbor : neighbors) {
                if (processed[neighbor.x][neighbor.y]){
                    continue;
                }
                Node nextStartNeighbor = allStartNodes.getOrDefault(neighbor, new Node(neighbor));
                allStartNodes.put(neighbor, nextStartNeighbor);
            
                
                double gCost = nextStart.getCost() + map.getCost(nextStart.getCurrent(),neighbor);
                double hCost = getHeuristic(map,nextStartNeighbor.getCurrent(),endPoint);
                double fCost = gCost + hCost;

                if(fCost < nextStartNeighbor.getScore()) {
                    nextStartNeighbor.setParent(nextStart.getCurrent());
                    nextStartNeighbor.setCost(gCost);
                    nextStartNeighbor.setEstimateCost(hCost);
                    nextStartNeighbor.setScore(fCost);
                    openStartSet.add(nextStartNeighbor);
                }
            }
        }

        Node current = nextStart;
        do {
            path.add (0,current.getCurrent());
            current = allStartNodes.get(current.getParent());
        } while (current != null);

        return path;
        // We're done!  Hand it back.
    }
}



 
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


public class BasicAI implements AIModule
{
    /// Creates the path to the goal.
    private double getHeuristic(final TerrainMap map, final Point pt1, final Point pt2){
        double diagonalDist = Math.max(Math.abs(pt1.x-pt2.x),Math.abs(pt1.y-pt2.y));
        double manhDist = Math.abs(pt1.x-pt2.x) + Math.abs(pt1.y-pt2.y);
        double heightCurr = map.getTile(pt1);
        double heightGoal = map.getTile(pt2);
        double heightDiff = heightGoal - heightCurr;
        //double euclDist = Math.sqrt(Math.pow((pt1.x-pt2.x),2.0) + Math.pow((pt1.y-pt2.y),2.0)) + heightCurr-heightGoal;
        //double max = Math.max(heightCurr,heightGoal);
        //double min = Math.min(heightCurr,heightGoal); 
        //double estimate = Math.pow(2.0,(heightGoal-heightCurr)/diagonalDist)*diagonalDist;
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
        /*
        if(heightGoal > heightCurr){
            estimate = 1 + Math.pow(2.0,heightGoal)*(1/Math.pow(2.0,max))*diagonalDist; //- 2*heightGoal*Math.abs(heightGoal-heightCurr);
        }
        else if(heightGoal == heightCurr){
            estimate = diagonalDist+1;
        }
        else{
            estimate = 1 + Math.pow(2.0,heightGoal)*(1/Math.pow(2.0,max))*diagonalDist;
        }
        */
        //System.out.println(estimate + " cost");
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

                return path;
            }

            Point neighbors[] = map.getNeighbors(next.getCurrent());
            
            for (Point neighbor : neighbors) {
                if (processed[neighbor.x][neighbor.y]){
                    continue;
                }
                Node nextNeighbor = allNodes.getOrDefault(neighbor, new Node(neighbor));
                allNodes.put(neighbor, nextNeighbor);
                
                double gCost = next.getCost() + map.getCost(next.getCurrent(),neighbor);
                double hCost = getHeuristic(map,nextNeighbor.getCurrent(),endPoint);
                double fCost = gCost + hCost;

                if(fCost < nextNeighbor.getScore()) {
                    nextNeighbor.setParent(next.getCurrent());
                    nextNeighbor.setCost(gCost);
                    nextNeighbor.setEstimateCost(hCost);
                    nextNeighbor.setScore(fCost);
                    openSet.add(nextNeighbor);
                }
            
            };
        }

        return path;

        // We're done!  Hand it back.

    }
}



 
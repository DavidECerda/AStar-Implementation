
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;
import javax.swing.*;

public class Node implements Comparable <Node> {
    private final Point current;
    private Point parent;
    private double cost;
    private double estimateCost;
    private double score;


    Node(Point current){
        this(current,null,Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
    }

    Node(Point current,Point parent, double cost, double estimateCost, double score){
        this.current = current;
        this.parent = parent;
        this.cost = cost;
        this.estimateCost = estimateCost;
        this.score = score;
    }

    public Point getCurrent(){
        return this.current;
    }

    public Point getParent(){
        return this.parent;
    }

    public double getCost(){
        return this.cost;
    }

    public double getEstimateCost(){
        return this.estimateCost;
    }

    public double getScore(){
        return this.score;
    }

    public void setParent(Point parent){
        this.parent = parent;
    }

    public void setCost(double cost){
        this.cost = cost;
    }

    public void setEstimateCost(double estimateCost){
        this.estimateCost = estimateCost;
    }

    public void setScore(double score){
        this.score = score;
    }

    @Override
    public int compareTo(Node otherNode){
        if (this.score > otherNode.score){
            return 1;
        } else if (this.score < otherNode.score){
            return -1;
        } else {
            return 0;
        }
    }
}


package algorithms;

import java.awt.Point;
import java.util.ArrayList;

public class DefaultTeam {

  public ArrayList<Point> calculTSP(ArrayList<Point> points) {
    if (points.size()<4) {
      return points;
    }

    ArrayList<Point> p=new ArrayList<Point>();
    p.add(points.get(0));
    p.add(points.get(1));
    p.add(points.get(2));

    /*******************
     * PARTIE A ECRIRE *
     *******************/
    return p;
  }

  public ArrayList<ArrayList<Point>> calculCinqVoyageursAvecBudget(Point maison, ArrayList<Point> points) {
    ArrayList<Point> alice = new ArrayList<Point>(); alice.add(maison);
    ArrayList<Point> bob = new ArrayList<Point>(); bob.add(maison);
    ArrayList<Point> cindy = new ArrayList<Point>(); cindy.add(maison);
    ArrayList<Point> dave = new ArrayList<Point>(); dave.add(maison);
    ArrayList<Point> eddy = new ArrayList<Point>(); eddy.add(maison);

    ArrayList<Point> clone = (ArrayList<Point>) points.clone();
    clone.remove(maison);

    /*******************
     * PARTIE A ECRIRE *
     *******************/

    if (points.size()<101) {
      alice.addAll(clone);
    } else {
      alice.add(clone.get((int)(Math.random()*100)));
      bob.add(clone.get((int)(Math.random()*100)));
      cindy.add(clone.get((int)(Math.random()*100)));
      dave.add(clone.get((int)(Math.random()*100)));
      eddy.add(clone.get((int)(Math.random()*100)));
    }

    ArrayList<ArrayList<Point>> result = new ArrayList<ArrayList<Point>>();
    result.add(alice);
    result.add(bob);
    result.add(cindy);
    result.add(dave);
    result.add(eddy);

    return result;
  }
}

package algorithms;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class DefaultTeam {

  private double score(ArrayList<Point> points) {
    double score = 0;
    for (int i = 0; i < points.size() - 1; i++) score += points.get(i).distance(points.get(i + 1));
    score += (points.get(0)).distance(points.get(points.size() - 1));
    return score;
  }

  public ArrayList<Point> improveSwap(ArrayList<Point> points) {
    ArrayList<Point> result = new ArrayList<>();
    for (int i = 0; i < points.size(); i++) {
      for (int j = i + 2; j < points.size(); j++) {
        int n = points.size();
        double a = points.get(i % n).distance(points.get((i + 1) % n));
        double b = points.get(j % n).distance(points.get((j + 1) % n));
        double c = points.get(i % n).distance(points.get((j) % n));
        double d = points.get((i + 1) % n).distance(points.get((j + 1) % n));
        if ((a + b) > (c + d)) {
          for (int k = 0; k <= 0; k++) result.add(points.get(k));
          for (int k = j; k > i; k--) result.add(points.get(k));
          for (int k = j + 1; k < n; k++) result.add(points.get(k));
          return result;
        }
      }
      return points;
    }
    return null;
  }

  private Point plusProche(Point dummy, ArrayList<Point> points) {
    if (points.isEmpty())
      return null;
    Point p = points.get(0);
    for (Point s : points) {
      if (dummy.distance(s) < dummy.distance(p)) p = s;
    }
    return p;
  }

  public ArrayList<Point> calculTSP(ArrayList<Point> points) {
    if (points.size() < 4) {
      return points;
    }
    ArrayList<Point> result = new ArrayList<>();
    ArrayList<Point> rest = (ArrayList<Point>) points.clone();
    Point dummy = rest.get(0);
    result.add(dummy);
    rest.remove(dummy);
    while (!rest.isEmpty()) {
      Point p = plusProche(dummy, rest);
      result.add(p);
      rest.remove(p);
      dummy = p;
    }
    while (score(improveSwap(result)) < score(result)) {
      result = improveSwap(result);
    }
    return result;
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

      int citiesToVisit = clone.size();
      int sizePart = citiesToVisit/5;
      ArrayList<Point> one = new ArrayList(clone.subList(0, sizePart).subList(0, 99));
      ArrayList<Point> two = new ArrayList(clone.subList(sizePart, 2*sizePart).subList(0, 99));
      ArrayList<Point> three = new ArrayList(clone.subList(2*sizePart, 3*sizePart).subList(0, 99));
      ArrayList<Point> four = new ArrayList(clone.subList(3*sizePart, 4*sizePart).subList(0, 99));
      ArrayList<Point> five = new ArrayList(clone.subList(4*sizePart, citiesToVisit).subList(0, 99));

      alice.addAll(calculTSP(one));
      bob.addAll(calculTSP(two));
      cindy.addAll(calculTSP(three));
      dave.addAll(calculTSP(four));
      eddy.addAll(calculTSP(five));
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

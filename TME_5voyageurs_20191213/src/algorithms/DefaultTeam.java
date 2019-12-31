package algorithms;

import java.awt.*;
import java.util.ArrayList;

public class DefaultTeam {

    public ArrayList<Point> alternate(ArrayList<Point> points) {
        ArrayList<Point> newTour;
        double bestDist = score(points);
        double newDist;
        int swaps = 1;

        while (swaps != 0) { //loop until no improvements are made.
            swaps = 0;
            //initialise inner/outer loops avoiding adjacent calculations and making use of problem symmetry to half total comparisons.
            for (int i = 1; i < points.size() - 2; i++) {
                for (int j = i + 1; j < points.size() - 1; j++) {
                    //check distance of line A,B + line C,D against A,C + B,D if there is improvement, call swap method.
                    if ((points.get(i).distance(points.get(i - 1)) + points.get(j + 1).distance(points.get(j))) >=
                            (points.get(i).distance(points.get(j + 1)) + points.get(i - 1).distance(points.get(j)))) {
                        newTour = swap(points, i, j); //pass arraylist and 2 points to be swapped.
                        newDist = score(newTour);
                        if (newDist < bestDist) { //if the swap results in an improved distance, increment counters and update distance/tour
                            points = newTour;
                            bestDist = newDist;
                            swaps++;
                        }
                    }
                }
            }
        }
        return points;
    }

    private static ArrayList<Point> swap(ArrayList<Point> points, int i, int j) {
        //conducts a 2 opt swap by inverting the order of the points between i and j
        ArrayList<Point> newTour = new ArrayList<>();
        //take array up to first point i and add to newTour
        int size = points.size();
        for (int c = 0; c <= i - 1; c++) {
            newTour.add(points.get(c));
        }
        //invert order between 2 passed points i and j and add to newTour
        int dec = 0;
        for (int c = i; c <= j; c++) {
            newTour.add(points.get(j - dec));
            dec++;
        }
        //append array from point j to end to newTour
        for (int c = j + 1; c < size; c++) {
            newTour.add(points.get(c));
        }
        return newTour;
    }

    private double score(ArrayList<Point> points) {
        double score = 0;
        if (points.size() >= 2) {
            for (int i = 0; i < points.size() - 1; i++) score += points.get(i).distance(points.get(i + 1));
            score += (points.get(0)).distance(points.get(points.size() - 1));
        }
        return score;
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

    public ArrayList<Point> calculTSP(ArrayList<Point> points, Point maison) {
        if (points.size() < 4) {
            return points;
        }
        ArrayList<Point> result = new ArrayList<>();
        ArrayList<Point> rest = (ArrayList<Point>) points.clone();
        Point dummy = maison;
        while (!rest.isEmpty()) {
            Point p = plusProche(dummy, rest);
            result.add(p);
            rest.remove(p);
            dummy = p;
        }
        return alternate(result);
    }

    public Circle calculCercleCouvrant(ArrayList<Point> points, Point maison) {
        if (points.size() < 1) return null;
        Point p = maison;
        for (Point s : points) if (maison.distance(s) > maison.distance(p)) p = s;
        // p = point le plus éloigné de maison
        double cX = maison.x;
        double cY = maison.y;
        double cRadius = maison.distance(p);
        // cercle de centre m=maison (CToS) et de rayon |mp|
        return new Circle(new Point((int) cX, (int) cY), (int) cRadius);
    }

    public double agl(Point center, Point current, Point previous) {
        return Math.toDegrees(Math.atan2(current.x - center.x, current.y - center.y) -
                Math.atan2(previous.x - center.x, previous.y - center.y));
    }


    public ArrayList<ArrayList<Point>> calculCinqVoyageursAvecBudget(Point maison, ArrayList<Point> points) {
        ArrayList<Point> alice = new ArrayList<Point>();
        alice.add(maison);
        ArrayList<Point> bob = new ArrayList<Point>();
        bob.add(maison);
        ArrayList<Point> cindy = new ArrayList<Point>();
        cindy.add(maison);
        ArrayList<Point> dave = new ArrayList<Point>();
        dave.add(maison);
        ArrayList<Point> eddy = new ArrayList<Point>();
        eddy.add(maison);

        ArrayList<Point> clone = (ArrayList<Point>) points.clone();
        clone.remove(maison);

        /*******************
         * PARTIE A ECRIRE *
         *******************/


        if (points.size() < 101) {
            alice.addAll(clone);
        }

        ArrayList<Point> aliceRegion = new ArrayList<Point>();
        ArrayList<Point> bobRegion = new ArrayList<Point>();
        ArrayList<Point> cindyRegion = new ArrayList<Point>();
        ArrayList<Point> daveRegion = new ArrayList<Point>();
        ArrayList<Point> eddyRegion = new ArrayList<Point>();
        int radius = calculCercleCouvrant(clone, maison).getRadius();
        Point o = maison;
        Point a = new Point();
        a.setLocation(o.x + radius,
                o.y);
        double regionAngle = 72;
        for (int i = 0; i < clone.size(); i++) {
            Point c = clone.get(i);
            double angle = agl(o, a, c);
            if (angle < 0) {
                angle += 360;
            }
            // Decoupage trigonometrique en 5 regions
//            if ((angle > 0) && (angle <= regionAngle)) {
//                aliceRegion.add(c); //Rouge
//            } else if ((angle > regionAngle) && (angle <= 2 * regionAngle)) {
//                bobRegion.add(c); //vert
//            } else if ((angle > 2 * regionAngle) && (angle <= 3 * regionAngle)) {
//                cindyRegion.add(c); //violet
//            } else if ((angle > 3 * regionAngle) && (angle <= 4 * regionAngle)) {
//                daveRegion.add(c); //Jaune
//            } else if ((angle > 4 * regionAngle) && (angle <= 360)) {
//                eddyRegion.add(c); //Noir
//            }

            // Ajustement des 5 regions
            double tmp = (360 + (4 * regionAngle - 7)) / 2;
            if ((angle > 0) && (angle <= regionAngle)) {
                aliceRegion.add(c); //Rouge
            } else if (((angle > regionAngle) && (angle <= 2 * regionAngle)) ||
                    ((angle > 2 * regionAngle) && (angle <= 3 * regionAngle))) {
                bobRegion.add(c); //vert
            } else if ((angle > 3 * regionAngle) && (angle <= (4 * regionAngle - 7))) {
                cindyRegion.add(c); //violet
            } else if ((angle > (4 * regionAngle - 7)) && (angle <= tmp)) {
                daveRegion.add(c); //Jaune
            } else if ((angle > tmp) && (angle <= 360)) {
                eddyRegion.add(c); //Noir
            }
        }
        alice.addAll(calculTSP(aliceRegion, maison));
        bob.addAll(calculTSP(bobRegion, maison));
        cindy.addAll(calculTSP(cindyRegion, maison));
        dave.addAll(calculTSP(daveRegion, maison));
        eddy.addAll(calculTSP(eddyRegion, maison));

        System.out.println("alice____" + alice.size());
        System.out.println("bob____" + bob.size());
        System.out.println("cindy____" + cindy.size());
        System.out.println("dave____" + dave.size());
        System.out.println("eddy____" + eddy.size());


        ArrayList<ArrayList<Point>> result = new ArrayList<ArrayList<Point>>();
        result.add(alice);
        result.add(bob);
        result.add(cindy);
        result.add(dave);
        result.add(eddy);

//        for (ArrayList<Point> salesman : result) {
//            while ((score(salesman) > 1664)) {
//                salesman.remove(salesman.size() - 1);
//            }
//        }
        return result;
    }
}

package algorithms;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

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

    // calculTSP: ArrayList<Point> --> ArrayList<Point>
    //   renvoie une permutation P de points telle que la visite
    //   de ces points selon l'ordre défini par P est de distance
    //   totale minimum.
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

    public ArrayList<Point> extractTour(ArrayList<Point> rest) {
        ArrayList<Point> part = new ArrayList<>();
        int i = 0;
        while ((i < 100)) {
            part.add(rest.get(i));
            i++;
        }
        return part;
    }

    private static <E> ArrayList<E> pickNRandomElements(ArrayList<E> list, int tournament) {
        Random r = new Random();
        int length = list.size();

        if (length < tournament) return null;

        for (int i = length - 1; i >= length - tournament; --i) {
            Collections.swap(list, i, r.nextInt(i + 1));
        }
        return new ArrayList<>(list.subList(length - tournament, length));
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

//        for (int i = 0; i < clone.size(); i++) {
//            Point point = clone.get(i);
//            double angle = Math.atan2(point.getX(), point.getY());
//            if (angle < 0) {
//                angle += 2 * Math.PI;
//            }
//            if ((angle > 0) && (angle <= 2 * Math.PI / 5)) {
//                alice.add(point);
//            } else if ((angle > 2 * Math.PI / 5) && (angle <= 4 * Math.PI / 5)) {
//                bob.add(point);
//            } else if ((angle > 4 * Math.PI / 5) && (angle <= 6 * Math.PI / 5)) {
//                cindy.add(point);
//            } else if ((angle > 6 * Math.PI / 5) && (angle <= 8 * Math.PI / 5)) {
//                dave.add(point);
//            } else if ((angle > 8 * Math.PI / 5) && (angle <= 2 * Math.PI)) {
//                eddy.add(point);
//            }
//        }
        ArrayList<ArrayList<Point>> result = new ArrayList<ArrayList<Point>>();
        result.add(alice);
        result.add(bob);
        result.add(cindy);
        result.add(dave);
        result.add(eddy);
        for (ArrayList<Point> salesman : result) {
            ArrayList<Point> opt = calculTSP(clone, maison);
            ArrayList<Point> tmp = new ArrayList<>(opt.subList(0, 199));
            salesman.addAll(tmp);

            ArrayList<Point> rest = new ArrayList<>();
            while ((score(salesman) > 1664)) {
                salesman.remove(salesman.size() - 1);
                rest.add(salesman.get(salesman.size() - 1));
            }
            clone.removeAll(tmp);
            clone.addAll(rest);
            System.out.println(score(salesman));
        }
        return result;
    }
}

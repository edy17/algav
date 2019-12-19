package algorithms;

import supportGUI.Circle;
import supportGUI.Line;

import java.awt.*;
import java.util.ArrayList;

public class DefaultTeam {

    public ArrayList<Point> alternate(ArrayList<Point> points) {
        ArrayList<Point> newTour;
        double bestDist = score(points);
        double newDist;
        int swaps = 1;
        int improve = 0;
        int iterations = 0;
        long comparisons = 0;

        while (swaps != 0) { //loop until no improvements are made.
            swaps = 0;

            //initialise inner/outer loops avoiding adjacent calculations and making use of problem symmetry to half total comparisons.
            for (int i = 1; i < points.size() - 2; i++) {
                for (int j = i + 1; j < points.size() - 1; j++) {
                    comparisons++;
                    //check distance of line A,B + line C,D against A,C + B,D if there is improvement, call swap method.
                    if ((points.get(i).distance(points.get(i - 1)) + points.get(j + 1).distance(points.get(j))) >=
                            (points.get(i).distance(points.get(j + 1)) + points.get(i - 1).distance(points.get(j)))) {

                        newTour = swap(points, i, j); //pass arraylist and 2 points to be swapped.

                        newDist = score(newTour);

                        if (newDist < bestDist) { //if the swap results in an improved distance, increment counters and update distance/tour
                            points = newTour;
                            bestDist = newDist;
                            swaps++;
                            improve++;
                        }
                    }
                }
            }
            iterations++;
        }
        return points;
    }

    private static ArrayList<Point> swap(ArrayList<Point> cities, int i, int j) {
        //conducts a 2 opt swap by inverting the order of the points between i and j
        ArrayList<Point> newTour = new ArrayList<>();
        //take array up to first point i and add to newTour
        int size = cities.size();
        for (int c = 0; c <= i - 1; c++) {
            newTour.add(cities.get(c));
        }
        //invert order between 2 passed points i and j and add to newTour
        int dec = 0;
        for (int c = i; c <= j; c++) {
            newTour.add(cities.get(j - dec));
            dec++;
        }
        //append array from point j to end to newTour
        for (int c = j + 1; c < size; c++) {
            newTour.add(cities.get(c));
        }
        return newTour;
    }

    private double score(ArrayList<Point> points) {
        double score = 0;
        for (int i = 0; i < points.size() - 1; i++) score += points.get(i).distance(points.get(i + 1));
        score += (points.get(0)).distance(points.get(points.size() - 1));
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
        return alternate(result);
    }


    // calculTSPOpt: ArrayList<Point> --> ArrayList<Point>
    //   renvoie une permutation P de points telle que la visite
    //   de ces points selon l'ordre défini par P est de distance
    //   totale minimum.
    public ArrayList<Point> calculTSPOpt(ArrayList<Point> points) {
        if (points.size() < 4) {
            return points;
        }
        ArrayList<Point> result = calculTSP(points);
        Genome globalBestGenome = new Genome(result);
        Genetic geneticAlgorithm = new Genetic(SelectionType.TOURNAMENT, 0, points);
        Genome resultGenome = geneticAlgorithm.optimize(globalBestGenome);
        return resultGenome.points;
    }

    // calculDiametre: ArrayList<Point> --> Line
    //   renvoie une paire de points de la liste, de distance maximum.
    public Line calculDiametre(ArrayList<Point> points) {
        if (points.size() < 3) {
            return null;
        }

        Point p = points.get(0);
        Point q = points.get(1);

        /*******************
         * PARTIE A ECRIRE *
         *******************/
        return new Line(p, q);
    }

    // calculDiametreOptimise: ArrayList<Point> --> Line
    //   renvoie une paire de points de la liste, de distance maximum.
    public Line calculDiametreOptimise(ArrayList<Point> points) {
        if (points.size() < 3) {
            return null;
        }

        Point p = points.get(1);
        Point q = points.get(2);

        /*******************
         * PARTIE A ECRIRE *
         *******************/
        return new Line(p, q);
    }

    // calculCercleMin: ArrayList<Point> --> Circle
    //   renvoie un cercle couvrant tout point de la liste, de rayon minimum.
    public Circle calculCercleMin(ArrayList<Point> points) {
        if (points.isEmpty()) {
            return null;
        }

        Point center = points.get(0);
        int radius = 100;

        /*******************
         * PARTIE A ECRIRE *
         *******************/
        return new Circle(center, radius);
    }

    // enveloppeConvexe: ArrayList<Point> --> ArrayList<Point>
    //   renvoie l'enveloppe convexe de la liste.
    public ArrayList<Point> enveloppeConvexe(ArrayList<Point> points) {
        if (points.size() < 3) {
            return null;
        }

        ArrayList<Point> enveloppe = new ArrayList<Point>();

        enveloppe.add(points.get(0));
        enveloppe.add(points.get(1));
        enveloppe.add(points.get(2));


        /*******************
         * PARTIE A ECRIRE *
         *******************/
        return points;
    }
}

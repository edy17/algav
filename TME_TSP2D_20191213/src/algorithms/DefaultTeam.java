package algorithms;

import supportGUI.Circle;
import supportGUI.Line;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

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

    public ArrayList<ArrayList<Point> > generatePerm(ArrayList<Point>  original) {
        if (original.isEmpty()) {
            ArrayList<ArrayList<Point> > result = new ArrayList<>();
            result.add(new ArrayList<>());
            return result;
        }
        Point firstElement = original.remove(0);
        ArrayList<ArrayList<Point> >returnValue = new ArrayList<>();
        ArrayList<ArrayList<Point> > permutations = generatePerm(original);
        for (ArrayList<Point> smallerPermutated : permutations) {
            for (int index=0; index <= smallerPermutated.size(); index++) {
                ArrayList<Point> temp = new ArrayList<>(smallerPermutated);
                temp.add(index, firstElement);
                returnValue.add(temp);
            }
        }
        return returnValue;
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

        final ArrayList<Point>[] clone = new ArrayList[]{(ArrayList<Point>) result.clone()};
        generatePerm(points).forEach(permuts -> {
            if (score(permuts) < score(clone[0])) {
                clone[0] = permuts;
            }
        });
        return clone[0];
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


    // calculTSPOpt: ArrayList<Point> --> ArrayList<Point>
    //   renvoie une permutation P de points telle que la visite
    //   de ces points selon l'ordre défini par P est de distance
    //   totale minimum.
    public ArrayList<Point> calculTSPOpt(ArrayList<Point> points) {
        if (points.size() < 4) {
            return points;
        }

        ArrayList<Point> p = new ArrayList<Point>();
        p.addAll(points);

        /*******************
         * PARTIE A ECRIRE *
         *******************/
        return p;
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

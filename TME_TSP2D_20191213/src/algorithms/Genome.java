package algorithms;

import java.awt.*;
import java.util.ArrayList;

public class Genome implements Comparable {

    ArrayList<Point> points;
    private int fitness;

    public Genome(ArrayList<Point> permutationOfPoints) {
        points = (ArrayList<Point>) permutationOfPoints.clone();
        fitness = this.calculateFitness();
    }

    public int calculateFitness() {
        int fitness = 0;
        for (int i = 0; i < points.size() - 1; i++) fitness += points.get(i).distance(points.get(i + 1));
        fitness += (points.get(0)).distance(points.get(points.size() - 1));
        return fitness;
    }

    public ArrayList<Point> getPoints() {
        return points;
    }

    public int getFitness() {
        return fitness;
    }

    @Override
    public int compareTo(Object o) {
        Genome genome = (Genome) o;
        if (this.fitness > genome.getFitness())
            return 1;
        else if (this.fitness < genome.getFitness())
            return -1;
        else
            return 0;
    }
}

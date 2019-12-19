package algorithms;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Genetic {
    private int generationSize;
    private int reproductionSize;
    private int maxIterations;
    private float mutationRate;
    private int tournamentSize;
    private SelectionType selectionType;
    private int targetFitness;
    private ArrayList<Point> inputPoints;

    Genetic(SelectionType selectionType, int targetFitness, ArrayList<Point> points) {
        this.selectionType = selectionType;
        this.targetFitness = targetFitness;
        this.inputPoints = points;

        generationSize = 500;
        reproductionSize = 500;
        maxIterations = 500;
        mutationRate = 0.3f;
        tournamentSize = 300;
    }

    private ArrayList<Genome> initialPopulation(Genome globalBestGenome) {
        ArrayList<Genome> population = new ArrayList<>();
        population.add(globalBestGenome);
        ArrayList<Point> tmp = (ArrayList<Point>) globalBestGenome.points.clone();
        for (int i = 0; i < generationSize-1; i++) {
            Collections.shuffle(tmp);
            population.add(new Genome(tmp));
        }
        return population;
    }

    private ArrayList<Genome> selection(ArrayList<Genome> population) {
        ArrayList<Genome> selected = new ArrayList<>();
        for (int i = 0; i < reproductionSize; i++) {
            if (selectionType == SelectionType.ROULETTE) {
                selected.add(rouletteSelection(population));
            } else if (selectionType == SelectionType.TOURNAMENT) {
                selected.add(tournamentSelection(population));
            }
        }

        return selected;
    }

    private Genome rouletteSelection(ArrayList<Genome> population) {
        int totalFitness = population.stream().map(Genome::getFitness).mapToInt(Integer::intValue).sum();
        Random random = new Random();
        int selectedValue = random.nextInt(totalFitness);
        float recValue = (float) 1 / selectedValue;
        float currentSum = 0;
        for (Genome genome : population) {
            currentSum += (float) 1 / genome.getFitness();
            if (currentSum >= recValue) {
                return genome;
            }
        }
        int selectRandom = random.nextInt(generationSize);
        return population.get(selectRandom);
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

    private Genome tournamentSelection(ArrayList<Genome> population) {
        ArrayList<Genome> selected = pickNRandomElements(population, tournamentSize);
        return Collections.min(selected);
    }

    private Genome mutate(Genome salesman) {
        Random random = new Random();
        float mutate = random.nextFloat();
        if (mutate < mutationRate) {
            ArrayList<Point> genome = salesman.getPoints();
            Collections.swap(genome, random.nextInt(inputPoints.size()), random.nextInt(inputPoints.size()));
            return new Genome(genome);
        }
        return salesman;
    }

    private ArrayList<Genome> createGeneration(ArrayList<Genome> population) {
        ArrayList<Genome> generation = new ArrayList<>();
        int currentGenerationSize = 0;
        while (currentGenerationSize < generationSize) {
            ArrayList<Genome> parents = new ArrayList<>();
            Genome tmp = Collections.min(population);
            parents.add(tmp);
            population.remove(tmp);
            tmp = Collections.min(population);
            parents.add(tmp);
            ArrayList<Genome> children = crossover(parents);
            children.set(0, mutate(children.get(0)));
            children.set(1, mutate(children.get(1)));
            generation.addAll(children);
            currentGenerationSize += 2;
        }
        return generation;
    }

    private ArrayList<Genome> crossover(ArrayList<Genome> parents) {
        // housekeeping
        Random random = new Random();
        int breakpoint = random.nextInt(inputPoints.size());
        ArrayList<Genome> children = new ArrayList<>();

        // copy parental genomes - we copy so we wouldn't modify in case they were
        // chosen to participate in crossover multiple times
        ArrayList<Point> parent1Genome = new ArrayList<>(parents.get(0).getPoints());
        ArrayList<Point> parent2Genome = new ArrayList<>(parents.get(1).getPoints());

        // creating child 1
        for (int i = 0; i < breakpoint; i++) {
            Point newVal;
            newVal = parent2Genome.get(i);
            Collections.swap(parent1Genome, parent1Genome.indexOf(newVal), i);
        }
        children.add(new Genome(parent1Genome));
        parent1Genome = parents.get(0).getPoints(); // reseting the edited parent

        // creating child 2
        for (int i = breakpoint; i < inputPoints.size(); i++) {
            Point newVal = parent1Genome.get(i);
            Collections.swap(parent2Genome, parent2Genome.indexOf(newVal), i);
        }
        children.add(new Genome(parent2Genome));

        return children;
    }

    Genome optimize(Genome globalBestGenome) {
        ArrayList<Genome> population = initialPopulation(globalBestGenome);
        for (int i = 0; i < maxIterations; i++) {
            ArrayList<Genome> selected = selection(population);
            population = createGeneration(selected);
            Genome tmp = Collections.min(population);
            if(globalBestGenome!=tmp) {
                while (improveSwap(globalBestGenome).getFitness() < globalBestGenome.getFitness()) {
                    globalBestGenome = improveSwap(globalBestGenome);
                }
            }
            if (globalBestGenome.getFitness() < targetFitness)
                break;
        }
        return globalBestGenome;
    }

    private Genome improveSwap(Genome genome) {
        ArrayList<Point> result = new ArrayList<>();
        for (int i = 0; i < genome.points.size(); i++) {
            for (int j = i + 2; j < genome.points.size(); j++) {
                int n = genome.points.size();
                double a = genome.points.get(i % n).distance(genome.points.get((i + 1) % n));
                double b = genome.points.get(j % n).distance(genome.points.get((j + 1) % n));
                double c = genome.points.get(i % n).distance(genome.points.get((j) % n));
                double d = genome.points.get((i + 1) % n).distance(genome.points.get((j + 1) % n));
                if ((a + b) > (c + d)) {
                    for (int k = 0; k <= 0; k++) result.add(genome.points.get(k));
                    for (int k = j; k > i; k--) result.add(genome.points.get(k));
                    for (int k = j + 1; k < n; k++) result.add(genome.points.get(k));
                    return new Genome(result);
                }
            }
            return genome;
        }
        return null;
    }
}

package com.interview.task;

public class Main {

    public static void main(String[] args) {
        String message = "As an inpute use a single quoted string like: \"AB5, BC4, CD8, DC8, DE6, AD5, CE2, EB3, AE7";

        if (args.length != 1) {
            throw new IllegalArgumentException("Wrong input. " + message);
        }

        String delimiters = "[ ,]+";
        String[] tokens = args[0].split(delimiters);

        for (String item : tokens) {
            if (!item.substring(2).matches("-?\\d+")) {
                throw new IllegalArgumentException("Wrong input. " + message);
            }
        }

        PathGeneration path = new PathGeneration(args[0]);

        System.out.println("Output #1: " + path.distanceForExactRoute(new String[]{"A", "B", "C"}));
        System.out.println("Output #2: " + path.distanceForExactRoute(new String[]{"A", "D"}));
        System.out.println("Output #3: " + path.distanceForExactRoute(new String[]{"A", "D", "C"}));
        System.out.println("Output #4: " + path.distanceForExactRoute(new String[]{"A", "E", "B", "C", "D"}));
        System.out.println("Output #5: " + path.distanceForExactRoute(new String[]{"A", "E", "D"}));
        System.out.println("Output #6: " + path.numberOfTripsByStops(1, 3, "C", "C"));
        System.out.println("Output #7: " + path.numberOfTripsByStops(4, 4, "A", "C"));
        System.out.println("Output #8: " + path.shortestPathBetweenNodes("A", "C"));
        System.out.println("Output #9: " + path.shortestPathBetweenNodes("B", "B"));
        System.out.println("Output #10: " + path.numberOfTripsByDistance(30, "C", "C"));
    }
}

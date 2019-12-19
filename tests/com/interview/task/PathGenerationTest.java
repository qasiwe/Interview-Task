package com.interview.task;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PathGenerationTest {

    @Test
    void shortestPathBetweenNodes() {
        PathGeneration path = new PathGeneration("AB5, BC4, CD8, DC8, DE6, AD5, CE2, EB3, AE7");
        assertEquals(5,path.shortestPathBetweenNodes("C","B"));
        assertEquals(5,path.shortestPathBetweenNodes("A","D"));
    }

    @Test
    void bfsNumberOfTripsByDistance() {
        PathGeneration path = new PathGeneration("AB5, BC4, CD8, DC8, DE6, AD5, CE2, EB3, AE7");
        assertEquals(6,path.numberOfTripsByDistance(30,"A","D"));
        assertEquals(7,path.numberOfTripsByDistance(30,"C","C"));
    }

    @Test
    void bfsNumberOfTripsByStops() {
        PathGeneration path = new PathGeneration("AB5, BC4, CD8, DC8, DE6, AD5, CE2, EB3, AE7");
        assertEquals(59,path.numberOfTripsByStops(1,10,"D","E"));
        assertEquals(3,path.numberOfTripsByStops(1,5,"B","D"));
    }

    @Test
    void distanceForExactRoute() {
        PathGeneration path = new PathGeneration("AB5, BC4, CD8, DC8, DE6, AD5, CE2, EB3, AE7");
        assertEquals("17",path.distanceForExactRoute(new String[]{"A", "B", "C","D"}));
    }
}
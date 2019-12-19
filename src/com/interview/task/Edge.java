package com.interview.task;

public class Edge implements Comparable<Edge> {

    private String departure;
    private String destination;
    private int distance;

    public Edge(String departure, String destination, int distance) {
        if (distance < 0) {
            throw new IllegalArgumentException("Distance between cities must be positive");
        }
        this.departure = departure;
        this.destination = destination;
        this.distance = distance;
    }

    public String getDeparture() {
        return departure;
    }

    public String getDestination() {
        return destination;
    }

    public Integer getDistance() {
        return distance;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }

    /*comparable method is implemented in order to use Edge class in the priority queue comparisons.
    Priority queue is used in the shortestPathBetweenNodes method.
     */
    @Override
    public int compareTo(Edge edge) {
        if (this.getDistance() > edge.getDistance()) {
            return 1;
        } else if (this.getDistance() < edge.getDistance()) {
            return -1;
        } else {
            return 0;
        }
    }
}

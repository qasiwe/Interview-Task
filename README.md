# TRAINS and TOWNS problem

Path counting tasks were implemented using DFS + dynamic programming traversing over the adjacency list.
Firstly, I used BFS instead of DFS+dp, but it had worse both time and space complexities. BFS time and space complexity were O((E + V)^N) where N is a distance or number of stops depending on the problem. It was reduced to the O((E + V)*N) with DFS + dp.

Optimal distance tasks were implemented using Dijkstra algorithm with priority queue. It is possibly the most efficient solution to this kind of problem and it has a complexity of O(E + V*log(V)).

## In order to run the code, inside src/com/interview/task folder run:
```no-highlight
javac -d . *.java
```
and then run this line with the graph as a command line argument in quotes:

```no-highlight
java com.interview.task.Main "AB5, BC4, CD8, DC8, DE6, AD5, CE2, EB3, AE7"
```
## Or you can use Docker:
```no-highlight
docker run -i -t qasiwe/digitallab
```
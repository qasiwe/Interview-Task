## TRAINS and TOWNS problem

Path counting tasks were implemented using dfs + dynamic programming over the adjecency matrix.

Optimal distance tasks were implemented using Dijkstra algorithm with priority queue.

In order to run the code, inside src/com/interview/task folder run:
```no-highlight
javac -d . *.java
```
and then run this line with the graph as a command line argument in quotes:

```no-highlight
java com.interview.task.Main "AB5, BC4, CD8, DC8, DE6, AD5, CE2, EB3, AE7"
```
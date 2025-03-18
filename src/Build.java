import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;


public class Build {

  /**
   * Prints words that are reachable from the given vertex and are strictly shorter than k characters.
   * If the vertex is null or no reachable words meet the criteria, prints nothing.
   *
   * @param vertex the starting vertex
   * @param k the maximum word length (exclusive)
   */
  public static void printShortWords(Vertex<String> vertex, int k) {
    if (vertex == null || k <= 0) return;
    
    Set<Vertex<String>> visited = new HashSet<>();
    Queue<Vertex<String>> queue = new LinkedList<>();
    
    queue.add(vertex);
    visited.add(vertex);
    
    while (!queue.isEmpty()){
      Vertex<String> current = queue.poll();
      if (current.data.length() < k){
        System.out.println(current.data);
      }

      for (Vertex<String> neighbor : current.neighbors){
        if (!visited.contains(neighbor)){
          visited.add(neighbor);
          queue.add(neighbor);
        }
      }
    }
  }

  /**
   * Returns the longest word reachable from the given vertex, including its own value.
   *
   * @param vertex the starting vertex
   * @return the longest reachable word, or an empty string if the vertex is null
   */
  public static String longestWord(Vertex<String> vertex) {
    if (vertex == null) return "";
    Set<Vertex<String>> visited = new HashSet<>();
    return LongestWordHelper(vertex, visited);
  }

  private static String LongestWordHelper(Vertex<String> vertex, Set<Vertex<String>> visited) {
    visited.add(vertex);
    String longest = vertex.data;

    for (Vertex<String> neighbor : vertex.neighbors){
      if (!visited.contains(neighbor)){
        String candidate = LongestWordHelper(neighbor, visited);
        if (candidate.length() > longest.length()){
          longest = candidate;
        }
      }
    }
    return longest;
  }

  /**
   * Prints the values of all vertices that are reachable from the given vertex and 
   * have themself as a neighbor.
   *
   * @param vertex the starting vertex
   * @param <T> the type of values stored in the vertices
   */
  public static <T> void printSelfLoopers(Vertex<T> vertex) {
    if (vertex == null) return;

    Set<Vertex<T>> visited = new HashSet<>();
    Queue<Vertex<T>> queue = new LinkedList<>();
    
    queue.add(vertex);
    visited.add(vertex);
    
    while (!queue.isEmpty()) {
      Vertex<T> current = queue.poll();
      if (current.neighbors.contains(current)){
        System.out.println(current.data);
      }
      for (Vertex<T> neighbor : current.neighbors){
        if (!visited.contains(neighbor)) {
          visited.add(neighbor);
          queue.add(neighbor);
        }
      }
    }
  }

  /**
   * Determines whether it is possible to reach the destination airport through a series of flights
   * starting from the given airport. If the start and destination airports are the same, returns true.
   *
   * @param start the starting airport
   * @param destination the destination airport
   * @return true if the destination is reachable from the start, false otherwise
   */
  public static boolean canReach(Airport start, Airport destination) {
    if (start == null || destination == null) return false;
    if (start == destination) return true;

    Set<Airport> visited = new HashSet<>();
    Queue<Airport> queue = new LinkedList<>();
    
    queue.add(start);
    visited.add(start);
    
    while (!queue.isEmpty()){
      Airport current = queue.poll();
      if (current == destination) return true;
      
      for (Airport next : current.getOutboundFlights()){
        if (!visited.contains(next)) {
          visited.add(next);
          queue.add(next);
        }
      }
    }
    return false;
  }

  /**
   * Returns the set of all values in the graph that cannot be reached from the given starting value.
   * The graph is represented as a map where each vertex is associated with a list of its neighboring values.
   *
   * @param graph the graph represented as a map of vertices to neighbors
   * @param starting the starting value
   * @param <T> the type of values stored in the graph
   * @return a set of values that cannot be reached from the starting value
   */
  public static <T> Set<T> unreachable(Map<T, List<T>> graph, T starting) {
    Set<T> reachable = new HashSet<>();
    Set<T> allNodes = new HashSet<>(graph.keySet());

    dfsUnreachable(graph, starting, reachable);
    allNodes.removeAll(reachable);
    return allNodes;
  }

  private static <T> void dfsUnreachable(Map<T, List<T>> graph, T current, Set<T> reachable){
    if (!graph.containsKey(current) || reachable.contains(current)) return;
    
    reachable.add(current);
    for (T neighbor : graph.get(current)){
      dfsUnreachable(graph, neighbor, reachable);
    }
  }
}

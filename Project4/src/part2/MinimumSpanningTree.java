package part2;

import java.util.LinkedList;

public class MinimumSpanningTree {
    private LinkedList<Integer> child = new LinkedList<>();
    private ResultSet[] resultSet;

    static class ResultSet{
        // will store the vertex(parent) from which the current vertex will reached
        int parent;
        // will store the weight for printing the MST weight
        double weight;
    }

    /**
     * Method to get the vertex with the minimum weight
     * @param marked: an array to record if a vertex is marked or not
     * @param weight: an array to store the weight of vertices
     */
    public int getMinVertex(boolean[] marked, double[] weight){
        double minWeight = Double.MAX_VALUE;
        int vertex = -1;
        for (int i = 0; i < marked.length; i++){
            if((!marked[i]) && (weight[i] < minWeight)){
                minWeight = weight[i];
                vertex = i;
            }
        }
        return vertex;
    }

    /**
     * Method to generate minimum spanning tree using prim's algorithm
     * @param graph: the adjacencyMatrix of all the vertices
     * @param vertices: the number of vertices
     */
    public void prim(double[][] graph, int vertices){
        boolean[] marked = new boolean[vertices];
        resultSet = new ResultSet[vertices];
        double [] weight = new double[vertices];

        //Initialize all the weights to infinity and
        //initialize resultSet for all the vertices
        for (int i = 0; i <vertices; i++) {
            weight[i] = Double.MAX_VALUE;
            resultSet[i] = new ResultSet();
        }

        //start from the vertex 0
        weight[0] = 0;
        resultSet[0] = new ResultSet();
        resultSet[0].parent = -1;

        //create MST
        for (int i = 0; i <vertices ; i++) {
            //get the vertex with the minimum weight
            int vertex = getMinVertex(marked, weight);
            //update the child list
            child.add(vertex);
            //include this vertex in MST
            marked[vertex] = true;

            //iterate through all the adjacent vertices of above vertex and update the weights
            for (int j = 0; j <vertices ; j++) {
                //check of the edge
                if(graph[vertex][j] >= 0){
                    //check if this vertex 'j' already in mst and
                    //if no then check if weight needs an update or not
                    if(!marked[j] && graph[vertex][j] <= weight[j]){
                        //update the weight
                        weight[j] = graph[vertex][j];
                        //update the result set
                        resultSet[j].parent = vertex;
                        resultSet[j].weight = weight[j];
                    }
                }
            }
        }
        printMST(graph);
    }


    /**
     * Method to print out the Hamiltonian Cycle and calculate its length
     * @param graph: the adjacencyMatrix of all the vertices
     */
    public void printMST(double[][] graph){
        double length = 0.0;
        for(int i = 0;i < child.size();i++){
            System.out.print(child.get(i) + " ");
            length += resultSet[i].weight;
        }
        length += graph[child.getLast()][child.getFirst()];
        System.out.println(child.getFirst());
        System.out.println("Length Of cycle: " + length + " miles");
    }
}

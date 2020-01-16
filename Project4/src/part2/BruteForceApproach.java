package part2;

import java.util.ArrayList;

public class BruteForceApproach {
    private double[][] graph;
    private ArrayList<ArrayList<Integer>> path_list = new ArrayList<ArrayList<Integer>>();

    /**
     * Constructor
     * @param matrix: the adjacencyMatrix of all the vertices
     **/
    public BruteForceApproach(double[][] matrix){
        this.graph = matrix;
        ArrayList<Integer> tmp = new ArrayList<Integer>();
        permutation(graph.length, tmp);

    }

    /**
     * Method to permute the node number to get all possible paths
     * @param n: the adjacencyMatrix of all the vertices
     * @param tmp: an arraylist to store each possible path
     **/
    public void permutation(int n, ArrayList<Integer> tmp){
        if(tmp.size() == n){
            path_list.add(new ArrayList<>(tmp));
            return;
        }
        for(int i=0; i<n; i++){
            if(tmp.contains(i)){
                continue;
            }
            tmp.add(i);
            permutation(n, tmp);
            tmp.remove(tmp.size() - 1);
        }
    }

    /**
     * Method to check and get minimum path as result
     */
    public void find_optimal_path(){
        for(int i = 0;i < path_list.size();i++){
            ArrayList<Integer> path = path_list.get(i);
            path.add(path.get(0));
        }

        //calculate and find out the minimum length
        double min_length = Double.MAX_VALUE;
        int idx = 0;
        for(int i = 0; i < path_list.size(); i++){
            double dist = 0.0;
            for(int j = 1; j < path_list.get(i).size(); j++){
                double weight = graph[path_list.get(i).get(j-1)][path_list.get(i).get(j)];
                dist += weight;
            }
            if (dist < min_length){
                min_length = dist;
                idx = i;
            }
        }

        //print out the optimal path
        ArrayList<Integer> min_path = path_list.get(idx);
        StringBuilder result = new StringBuilder();
        for(int i = 0; i< min_path.size();i++){
            result.append(min_path.get(i));
            result.append(" ");
        }
        System.out.println(result.toString());
        System.out.println("Optimal Cycle length = " + min_length + " miles");
    }

}

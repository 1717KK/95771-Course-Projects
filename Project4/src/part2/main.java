package part2;

import com.csvreader.CsvReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class main {
    private static int start;
    private static int end;
    private static double[][] adjacencyMatrix;

    public main(){
    }

    /**
     * Method to calculate the distance between two vertices
     * @param x1: x coordinate of one vertex
     * @param x2: x coordinate of the other vertex
     * @param y1: y coordinate of the other vertex
     * @param y2: y coordinate of the other vertex
     */
    public static double distance(double x1, double x2, double y1, double y2){
        double dist = Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
        dist = dist * 0.00018939; //convert feet to miles
        return dist;
    }

    public static void main(String[] args) throws IOException {
        FileReader file = new FileReader("src/CrimeLatLonXY1990.csv");
        CsvReader reader = new CsvReader(file);
        ArrayList<String[]> csvList = new ArrayList<String[]>();
        reader.readHeaders(); //skip the header
        while(reader.readRecord()){
            csvList.add(reader.getValues()); //read per line and add it into list
        }
        reader.close();

        Scanner scan = new Scanner(System.in);
        try{
            System.out.println("Enter start date");
            String start_date = scan.next();

            System.out.println("Enter end date");
            String end_date = scan.next();

            System.out.println();
            System.out.println("Crime records between " + start_date + " and " + end_date);

            //find out the start index and end index of the date
            for(int row = 0; row < csvList.size(); row++) {
                if (start_date.equals(csvList.get(row)[5])){
                    start = row;
                    break;
                }
            }
            for(int row = 0; row < csvList.size(); row++) {
                if (end_date.equals(csvList.get(row)[5])){
                    end = row;
                }
            }

            //print out each line
            for (int row = start; row <= end; row ++){
                System.out.print(csvList.get(row)[0] + ",");
                System.out.print(csvList.get(row)[1] + ",");
                System.out.print(csvList.get(row)[2] + ",");
                System.out.print(csvList.get(row)[3] + ",");
                System.out.print(csvList.get(row)[4] + ",");
                System.out.print(csvList.get(row)[5] + ",");
                System.out.print(csvList.get(row)[6] + ",");
                System.out.print(csvList.get(row)[7] + ",");
                System.out.println(csvList.get(row)[8]);
            }
            System.out.println();

            //generate the adjacencyMatrix
            int vertices = end - start + 1;
            adjacencyMatrix = new double[vertices][vertices];
            for(int i = 0; i < vertices; i++){
                double x1 = Double.parseDouble(csvList.get(start + i)[0]);
                double y1 = Double.parseDouble(csvList.get(start + i)[1]);
                for(int j = 0; j < vertices; j++) {
                    double x2 = Double.parseDouble(csvList.get(start + j)[0]);
                    double y2 = Double.parseDouble(csvList.get(start + j)[1]);
                    adjacencyMatrix[i][j] = distance(x1, x2, y1, y2);
                }
            }

            System.out.println("Hamiltonian Cycle(not necessarily optimum):");
            MinimumSpanningTree mst = new MinimumSpanningTree();
            mst.prim(adjacencyMatrix, vertices);

            System.out.println();
            System.out.println("Looking at every permutation to find the optimal solution");
            System.out.println("The best permutation");
            BruteForceApproach bfa = new BruteForceApproach(adjacencyMatrix);
            bfa.find_optimal_path();

        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}

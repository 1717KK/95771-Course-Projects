package part3;

import com.csvreader.CsvReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class main {
    private static int start;
    private static int end;
    private static double[][] adjacencyMatrix;
    private static ArrayList<ArrayList<Double>> coordinates = new ArrayList<>();

    public main(){
    }

    /**
     * Method to calculate the distance between two vertices
     * @param x1: x coordinate of one vertex
     * @param x2: x coordinate of the other vertex
     * @param y1: y coordinate of one vertex
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
                //String coor = csvList.get(row)[8] + "," + csvList.get(row)[7]+",0.000000";
                ArrayList<Double> coor = new ArrayList<>();
                coor.add(Double.parseDouble(csvList.get(row)[8]));
                coor.add(Double.parseDouble(csvList.get(row)[7]));
                coordinates.add(coor);
            }

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

            //generate TSP path
            MinimumSpanningTree t1 = new MinimumSpanningTree();
            t1.prim(adjacencyMatrix, vertices);
            ArrayList<Integer> path1 = t1.getPath(); //01230
            ArrayList<ArrayList<String>> cycle1 = new ArrayList<>();
            for(int i = 0; i < path1.size();i++){
                ArrayList<String> point = new ArrayList<>();
                ArrayList<Double> coordinate = coordinates.get(path1.get(i));
                point.add(Double.toString(coordinate.get(0) - 0.0005));
                point.add(",");
                point.add(Double.toString(coordinate.get(1) + 0.0005));
                point.add(",0.000000");
                cycle1.add(point);
            }

            //generate optimal path
            BruteForceApproach t2 = new BruteForceApproach(adjacencyMatrix);
            t2.find_optimal_path();
            ArrayList<Integer> path2 = t2.getPath();
            ArrayList<ArrayList<String>> cycle2 = new ArrayList<>();
            for(int i = 0; i < path2.size();i++){
                ArrayList<String> point = new ArrayList<>();
                ArrayList<Double> coordinate = coordinates.get(path2.get(i));
                point.add(Double.toString(coordinate.get(0)));
                point.add(",");
                point.add(Double.toString(coordinate.get(1)));
                point.add(",0.000000");
                cycle2.add(point);
            }

            //generate kml file
            writeKML kml = new writeKML();
            kml.write_out(cycle1, cycle2);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

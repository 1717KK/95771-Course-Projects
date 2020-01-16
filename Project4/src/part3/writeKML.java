package part3;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


public class writeKML {

    /**
     * Method to write out KML file
     * @param cycle1: an arraylist to store the coordinates of TSP path
     * @param cycle2: an arraylist to store the coordinates of the optimal path
     */
    public void write_out(ArrayList<ArrayList<String>> cycle1, ArrayList<ArrayList<String>> cycle2) throws IOException {

        String part1 = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n" +
                "<kml xmlns=\"http://earth.google.com/kml/2.2\">\n" +
                "    <Document>\n" +
                "        <name>Pittsburgh TSP</name>\n" +
                "        <description>TSP on Crime</description>\n" +
                "        <Style id=\"style6\">\n" +
                "            <LineStyle>\n" +
                "                <color>73FF0000</color>\n" +
                "                <width>5</width>\n" +
                "            </LineStyle>\n" +
                "        </Style>\n" +
                "        <Style id=\"style5\">\n" +
                "            <LineStyle>\n" +
                "                <color>507800F0</color>\n" +
                "                <width>5</width>\n" +
                "            </LineStyle>\n" +
                "        </Style>\n" +
                "        <Placemark>\n" +
                "            <name>TSP Path</name>\n" +
                "            <description>TSP Path</description>\n" +
                "            <styleUrl>#style6</styleUrl>\n" +
                "            <LineString>\n" +
                "                <tessellate>1</tessellate>\n" +
                "                <coordinates>\n";

        for(ArrayList coor: cycle1){
            for(int i = 0; i< coor.size();i++){
                part1 += coor.get(i);
            }
            part1 +="\n";
        }

        String part2 = "</coordinates>\n" +
                "            </LineString>\n" +
                "        </Placemark>\n" +
                "        <Placemark>\n" +
                "            <name>Optimal Path</name>\n" +
                "            <description>Optimal Path</description>\n" +
                "            <styleUrl>#style5</styleUrl>\n" +
                "            <LineString>\n" +
                "                <tessellate>1</tessellate>\n" +
                "                <coordinates>\n";

        part1 += part2;

        for(ArrayList coor: cycle2){
            for(int i = 0; i< coor.size();i++){
                part1 += coor.get(i);
            }
            part1 +="\n";
        }

        String part3 = "</coordinates>\n" +
                "            </LineString>\n" +
                "        </Placemark>\n" +
                "    </Document>\n" +
                "</kml>\n";

        part1 += part3;

        File file =new File("PGHCrimes.kml");
        if(!file.exists()){
            file.createNewFile();
        }

        //true = append file
        FileWriter fileWritter = new FileWriter(file.getName(),true);
        BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
        bufferWritter.write(part1);
        bufferWritter.close();

        System.out.println("Written!");
    }
}

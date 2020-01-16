package WorldSeriesOddsProject;

import static java.lang.System.currentTimeMillis;

public class DynamicProgramming {
    private int i;
    private int j;

    DynamicProgramming(){
    }
    
    public double slow_calculate(int i, int j){
        double prob = 0;
        if((i==0) && (j>0)){
            return 1;
        }else if((i>0) && (j==0)){
            return 0;
        }
        else if((i>0)&&(j>0)){
            prob = (slow_calculate(i-1, j) + slow_calculate(i, j-1))/2;
        }
        return prob;
    }

    public double fast_calculate(int i, int j){
        double[][] table = new double[i+1][j+1];
        for(int row = 0;row <= i;row++) table[row][0] = 0;
        for(int col = 0;col <= j;col++) table[0][col] = 1;

        for(int row = 1;row <= i;row++){
            for(int col = 1;col <= j;col++){
                table[row][col] = (table[row - 1][col] + table[row][col - 1]) / 2;
            }
        }
        return table[i][j];
    }

    public static void main(String[] args) {
        DynamicProgramming prob = new DynamicProgramming();

        long startTime1 = currentTimeMillis();
        double answer1 = prob.slow_calculate(7, 6);
        long endTime1 = currentTimeMillis();
        System.out.println("PROB: " + answer1);
        System.out.println("slow running time " + (endTime1 - startTime1) + "ms");

        long startTime2 = currentTimeMillis();
        double answer2 = prob.fast_calculate(50, 40);
        long endTime2 = currentTimeMillis();
        System.out.println("PROB: " + answer2);
        System.out.println("fast running time " + (endTime2 - startTime2) + "ms");
    }
}
